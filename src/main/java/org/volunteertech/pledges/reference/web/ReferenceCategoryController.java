package org.volunteertech.pledges.reference.web;
import java.util.ArrayList;
import java.util.Arrays; 
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.volunteertech.pledges.users.security.SecurityUser;

import org.volunteertech.pledges.main.localisation.DatabaseDrivenMessageSource;
import org.volunteertech.pledges.reference.dao.ReferenceCategory;
import org.volunteertech.pledges.reference.dao.ReferenceCategoryImpl;
import org.volunteertech.pledges.reference.service.ReferenceCategoryService;
import org.volunteertech.pledges.reference.dao.ReferenceCategoryLoadException;
import org.volunteertech.pledges.reference.dao.ReferenceCategorySaveException;
import org.volunteertech.pledges.reference.validator.ReferenceCategoryFormValidator;
import org.volunteertech.pledges.reference.view.ReferenceCategoryTranslationBackingBean;
import org.volunteertech.pledges.reference.view.ReferenceCategoryTranslationBackingBeanImpl;
import org.volunteertech.pledges.main.web.BaseController;
import org.volunteertech.pledges.main.constants.Constants;
import org.volunteertech.pledges.localisation.dao.MessageResource;
import org.volunteertech.pledges.localisation.dao.MessageResourceImpl;
import org.volunteertech.pledges.localisation.service.MessageResourceService;


import org.volunteertech.pledges.reference.ReferenceStore;

/**
 * The reference category screen is used to add/edit new/existing reference category entries. The reference entries are used to populate dropdowns where a group of reference entries with a common column-value can be associated with a dropdown.
 * GUIHandler is attached to a form on JSP and contains all getters and setters for values displayed on the form.
 * GUIHandler accesses the business layer for loading/saving data. 
 * This class has been generated by the XSLT processor from the metadata and represents the
 * the GUI layer javabean for the ReferenceCategory Form.
 * <P> 
 * It is essential that methods added to this class are given JavaDoc comments to allow
 * documentation to be generated. For a description of JavaDoc refer to The JavaDoc documentation.
 * A link is provided below.
 * <P>
 * @author Michael O'Connor
 * @version $Revision$
 * Date: $Date$
 * @see <a href="http://java.sun.com/j2se/javadoc/writingdoccomments/index.html">>JavaDoc Documentation</a> 
 * Change Log
 * ----------
 * $Log$
 *
 */
@Controller
public class ReferenceCategoryController extends BaseController
{

	public static final String TEMPLATE_PREFIX = "references/categories/";
    /**
     * userId used for development. This should be taken from the session.
     */
	private Long userId = Long.valueOf(0);
	 
	final Logger logger = LoggerFactory.getLogger(ReferenceCategoryController.class);
	
	@Autowired
	private ReferenceStore referenceStore;
	
	@Autowired
	private ReferenceCategoryService referenceCategoryService;

	@Autowired
	private ReferenceCategoryFormValidator referenceCategoryFormValidator;
	
    @Autowired
    private DatabaseDrivenMessageSource messageSource;
    


    @Autowired
    private MessageResourceService messageResourceService;
  
    
	
	//Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(referenceCategoryFormValidator);
	}
	
	
	/**
	 * Open the list page
	 */
	@RequestMapping(value = "/referencecategory/all", method = RequestMethod.GET)
	public String showAllReferenceCategory(Model model, Locale locale) {

		logger.debug("showAllReferenceCategory()");
			
		return TEMPLATE_PREFIX+"referencecategory_table";

	}
	
	/**
	 * Open the localize page
	 */
	@RequestMapping(value = "/referencecategory/localize", method = RequestMethod.GET)
	public String localizeReferenceCategory(Model model, Locale locale) {

		logger.debug("localizeReferenceCategory()");

		ReferenceCategoryTranslationBackingBean referenceCategoryTranslationBackingBean = new ReferenceCategoryTranslationBackingBeanImpl();
		referenceCategoryTranslationBackingBean.setCurrentMode(ReferenceCategory.CurrentMode.LOCALIZE);
		model.addAttribute("referenceCategoryTranslationFormModel", referenceCategoryTranslationBackingBean);
		Long defaultLocale = Long.valueOf(Constants.REFERENCE_LOCALE__EN);
		setTranslationDropDownContents(model, locale);
		setDropDownContents(model, null, locale);		
		model.addAttribute("defaultLocale", defaultLocale);
		
		return TEMPLATE_PREFIX+"referencecategory_localize";

	}
	
	
	

	// save or update ReferenceCategory
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/referencecategory/post", method = RequestMethod.POST)
	public String saveOrUpdateReferenceCategory(Authentication authentication, @ModelAttribute("referenceCategoryFormModel") @Validated ReferenceCategoryImpl referenceCategory,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateReferenceCategory() : {}", referenceCategory);
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		if (result.hasErrors()) {
			setDropDownContents(model, referenceCategory, locale);
			String updateIssueMessage = messageSource.getMessage("referenceCategoryUpdateIssueMessage", new String[0], locale);
			model.addAttribute("msg", updateIssueMessage);
			model.addAttribute("css", "alert-danger");
			
			return TEMPLATE_PREFIX+"referencecategory";
		} else {

			// Add message to flash scope
			redirectAttributes.addFlashAttribute("css", "success");
			if(referenceCategory.isNew()){
				String addedSuccessMessage = messageSource.getMessage("referenceCategoryAddedSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", addedSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}else{
				String updateSuccessMessage = messageSource.getMessage("referenceCategoryUpdateSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", updateSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}


			try{
				// TODO: Needs exception handling policy
			    	referenceCategoryService.storeReferenceCategory(referenceCategory, userId);
			}
			catch (Exception ex){
				logger.error("Exception caught !!!!!!!!!!!!!!", ex);
			}
			
	
			

			
			// POST/REDIRECT/GET
			return "redirect:/referencecategory/" + referenceCategory.getId() + "/update";
		}

	}

	// show add user form
	@RequestMapping(value = "/referencecategory/add", method = RequestMethod.GET)
	public String showAddReferenceCategoryForm(Model model, Locale locale) {

		logger.debug("showAddReferenceCategoryForm()");

		ReferenceCategory referenceCategory = new ReferenceCategoryImpl();
		
		referenceCategory.setCurrentMode(ReferenceCategory.CurrentMode.ADD);

		model.addAttribute("referenceCategoryFormModel", referenceCategory);

		setDropDownContents(model, referenceCategory, locale);

		return TEMPLATE_PREFIX+"referencecategory";

	}
	
	// support access to the supporting webpage by creating a new instance and returning 
	@RequestMapping(value = "/referencecategorywebpage", method = RequestMethod.GET)
	public String createReferenceCategoryForWebPageView(Model model, HttpServletRequest request, Locale locale) {

		logger.debug("createReferenceCategoryForWebPageView()");

		ReferenceCategory referenceCategory = new ReferenceCategoryImpl();
		
		try{
			// TODO: Needs exception handling policy
	    	referenceCategoryService.storeReferenceCategory(referenceCategory, userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
		

		model.addAttribute("referenceCategoryFormModel", referenceCategory);

		setDropDownContents(model, referenceCategory, locale);

		return TEMPLATE_PREFIX+"referencecategorywebpage";

	}
	

	// show update form
	@RequestMapping(value = "/referencecategory/{id}/update", method = RequestMethod.GET)
	public String showUpdateReferenceCategoryForm(@PathVariable("id") int id, Model model, Locale locale) {

		logger.debug("showUpdateReferenceCategoryForm() : {}", id);
		ReferenceCategory referenceCategory = null;
		try{
			// TODO: Needs exception handling policy
			referenceCategory = referenceCategoryService.load(Long.valueOf(id), userId);
			referenceCategory.setCurrentMode(ReferenceCategory.CurrentMode.UPDATE);
			this.referenceCategoryService.translateReferenceValues(referenceCategory, locale);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		model.addAttribute("referenceCategoryFormModel", referenceCategory);
		
		setDropDownContents(model, referenceCategory, locale);
		
		return TEMPLATE_PREFIX+"referencecategory";

	}

	// delete referenceCategory
	@RequestMapping(value = "/referencecategory/{id}/delete", method = RequestMethod.POST)
	public String deleteReferenceCategory(@PathVariable("id") int id, 
		final RedirectAttributes redirectAttributes) {

		logger.debug("deleteUser() : {}", id);

		//referenceCategoryService.delete(id);
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "User is deleted!");
		
		return "redirect:/referencecategory/all";

	}

	// show user
	@RequestMapping(value = "/referencecategory/{id}", method = RequestMethod.GET)
	public String showReferenceCategory(@PathVariable("id") int id, Model model, Locale locale) {

		logger.debug("showReferenceCategory() id: {}", id);
		ReferenceCategory referenceCategory = null;
		try{
			// TODO: Needs exception handling policy
			referenceCategory = referenceCategoryService.load(Long.valueOf(id), userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		if (referenceCategory == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "User not found");
		}
		model.addAttribute("referenceCategory", referenceCategory);
		
		setDropDownContents(model, referenceCategory, locale);

		return TEMPLATE_PREFIX+"showreferencecategory";

	}
	
	
	// save or update ReferenceCategoryTranslation
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/referencecategory/translate", method = RequestMethod.POST)
	public String saveOrUpdateReferenceCategoryTranslation(Authentication authentication, @ModelAttribute("referenceCategoryTranslationFormModel") ReferenceCategoryTranslationBackingBeanImpl referenceCategoryTranslationBackingBean,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateReferenceCategoryTranslation() : {}", referenceCategoryTranslationBackingBean);
		Long translationLocaleReferenceId = referenceCategoryTranslationBackingBean.getNewLocale();
		String translationLocale = messageSource.getMessage(referenceStore.getRefDesc(translationLocaleReferenceId), new String[0], new Locale("en"));
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		try{
			
			// TODO: Needs exception handling policy
			List<MessageResource> messageResourceList  = new ArrayList<MessageResource>();
			
			MessageResource messageResource = populateMessageResource(ReferenceCategoryTranslationBackingBeanImpl.REFERENCECATEGORY_HEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, referenceCategoryTranslationBackingBean.getViewTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(ReferenceCategoryTranslationBackingBeanImpl.REFERENCECATEGORY_TITLE_MAPPING_KEY, translationLocale, translationLocaleReferenceId, referenceCategoryTranslationBackingBean.getFormTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(ReferenceCategoryTranslationBackingBeanImpl.REFERENCECATEGORY_SUBHEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, referenceCategoryTranslationBackingBean.getFormSubHeader());
			messageResourceList.add(messageResource);
			
	messageResource = populateMessageResource(ReferenceCategoryTranslationBackingBeanImpl.REFERENCECATEGORYDESC_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, referenceCategoryTranslationBackingBean.getReferenceCategoryDescLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ReferenceCategoryTranslationBackingBeanImpl.REFERENCECATEGORYDESC_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, referenceCategoryTranslationBackingBean.getReferenceCategoryDescPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ReferenceCategoryTranslationBackingBeanImpl.REFERENCECATEGORYDESC_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, referenceCategoryTranslationBackingBean.getReferenceCategoryDescHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(ReferenceCategoryTranslationBackingBeanImpl.PARENTCATEGORYID_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, referenceCategoryTranslationBackingBean.getParentCategoryIdLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ReferenceCategoryTranslationBackingBeanImpl.PARENTCATEGORYID_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, referenceCategoryTranslationBackingBean.getParentCategoryIdPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ReferenceCategoryTranslationBackingBeanImpl.PARENTCATEGORYID_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, referenceCategoryTranslationBackingBean.getParentCategoryIdHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(ReferenceCategoryTranslationBackingBeanImpl.DESCRIPTION_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, referenceCategoryTranslationBackingBean.getDescriptionLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ReferenceCategoryTranslationBackingBeanImpl.DESCRIPTION_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, referenceCategoryTranslationBackingBean.getDescriptionPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ReferenceCategoryTranslationBackingBeanImpl.DESCRIPTION_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, referenceCategoryTranslationBackingBean.getDescriptionHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(ReferenceCategoryTranslationBackingBeanImpl.SAVEBUTTON_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, referenceCategoryTranslationBackingBean.getSaveButtonLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ReferenceCategoryTranslationBackingBeanImpl.SAVEBUTTON_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, referenceCategoryTranslationBackingBean.getSaveButtonPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ReferenceCategoryTranslationBackingBeanImpl.SAVEBUTTON_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, referenceCategoryTranslationBackingBean.getSaveButtonHelpText());
  			messageResourceList.add(messageResource);
  		

			

			this.messageSource.updateTexts(messageResourceList, userId);			
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
			
		// POST/REDIRECT/GET
		return "redirect:/referencecategory/add?language=" + translationLocale;

	}
	
	private void setDropDownContents(Model model, ReferenceCategory referenceCategory, Locale locale) {
		
		
		Map<Long, String> localeMap = referenceStore.getLocale();
		SortedMap<Long, String> localizedLocaleMap = new TreeMap<Long, String>(localeMap);
		for (Map.Entry<Long, String> entry : localeMap.entrySet()) {
			localizedLocaleMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		
		model.addAttribute("localeMap", localizedLocaleMap);
	}
	


}
