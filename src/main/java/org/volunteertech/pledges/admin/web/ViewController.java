package org.volunteertech.pledges.admin.web;
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
import org.volunteertech.pledges.admin.dao.View;
import org.volunteertech.pledges.admin.dao.ViewImpl;
import org.volunteertech.pledges.admin.service.ViewService;
import org.volunteertech.pledges.admin.dao.ViewLoadException;
import org.volunteertech.pledges.admin.dao.ViewSaveException;
import org.volunteertech.pledges.admin.validator.ViewFormValidator;
import org.volunteertech.pledges.admin.view.ViewTranslationBackingBean;
import org.volunteertech.pledges.admin.view.ViewTranslationBackingBeanImpl;
import org.volunteertech.pledges.main.web.BaseController;
import org.volunteertech.pledges.main.constants.Constants;
import org.volunteertech.pledges.localisation.dao.MessageResource;
import org.volunteertech.pledges.localisation.dao.MessageResourceImpl;
import org.volunteertech.pledges.localisation.service.MessageResourceService;
import org.volunteertech.pledges.address.view.AddressTranslationBackingBean;	
import org.volunteertech.pledges.address.view.AddressTranslationBackingBeanImpl; 	
import org.volunteertech.pledges.pledge.view.RegisterOfPledgesTranslationBackingBean;	
import org.volunteertech.pledges.pledge.view.RegisterOfPledgesTranslationBackingBeanImpl; 	
import org.volunteertech.pledges.accommodationpledge.view.AccommodationPledgeTranslationBackingBean;	
import org.volunteertech.pledges.accommodationpledge.view.AccommodationPledgeTranslationBackingBeanImpl; 	
import org.volunteertech.pledges.servicepledge.view.ServicePledgeTranslationBackingBean;	
import org.volunteertech.pledges.servicepledge.view.ServicePledgeTranslationBackingBeanImpl; 	
import org.volunteertech.pledges.goodspledge.view.GoodsPledgeTranslationBackingBean;	
import org.volunteertech.pledges.goodspledge.view.GoodsPledgeTranslationBackingBeanImpl; 	
import org.volunteertech.pledges.reference.view.ReferenceCategoryTranslationBackingBean;	
import org.volunteertech.pledges.reference.view.ReferenceCategoryTranslationBackingBeanImpl; 	
import org.volunteertech.pledges.reference.view.ReferenceTranslationBackingBean;	
import org.volunteertech.pledges.reference.view.ReferenceTranslationBackingBeanImpl; 	
import org.volunteertech.pledges.startup.view.LandingTranslationBackingBean;	
import org.volunteertech.pledges.startup.view.LandingTranslationBackingBeanImpl; 	
import org.volunteertech.pledges.admin.view.ViewTranslationBackingBean;	
import org.volunteertech.pledges.admin.view.ViewTranslationBackingBeanImpl; 	
import org.volunteertech.pledges.localisation.view.MessageResourceTranslationBackingBean;	
import org.volunteertech.pledges.localisation.view.MessageResourceTranslationBackingBeanImpl; 	
import org.volunteertech.pledges.users.view.ApplicationUserTranslationBackingBean;	
import org.volunteertech.pledges.users.view.ApplicationUserTranslationBackingBeanImpl; 	
import org.volunteertech.pledges.users.view.ApplicationUserDetailsTranslationBackingBean;	
import org.volunteertech.pledges.users.view.ApplicationUserDetailsTranslationBackingBeanImpl; 	


import org.volunteertech.pledges.reference.ReferenceStore;

/**
 * The Views that are contained in the application
 * GUIHandler is attached to a form on JSP and contains all getters and setters for values displayed on the form.
 * GUIHandler accesses the business layer for loading/saving data. 
 * This class has been generated by the XSLT processor from the metadata and represents the
 * the GUI layer javabean for the View Form.
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
public class ViewController extends BaseController
{

    /**
     * userId used for development. This should be taken from the session.
     */
	private Long userId = new Long(0);
	 
	final Logger logger = LoggerFactory.getLogger(ViewController.class);
	
	@Autowired
	private ReferenceStore referenceStore;
	
	@Autowired
	private ViewService viewService;

	@Autowired
	private ViewFormValidator viewFormValidator;
	
    @Autowired
    private DatabaseDrivenMessageSource messageSource;
    


    @Autowired
    private MessageResourceService messageResourceService;
  
    
	
	//Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(viewFormValidator);
	}
	
	
	/**
	 * Open the list page
	 */
	@RequestMapping(value = "/view/all", method = RequestMethod.GET)
	public String showAllView(Model model, Locale locale) {

		logger.debug("showAllView()");
			
		return "view_table";

	}
	
	/**
	 * Open the localize page
	 */
	@RequestMapping(value = "/view/localize", method = RequestMethod.GET)
	public String localizeView(Model model, Locale locale) {

		logger.debug("localizeView()");

		ViewTranslationBackingBean viewTranslationBackingBean = new ViewTranslationBackingBeanImpl();
		viewTranslationBackingBean.setCurrentMode(View.CurrentMode.LOCALIZE);
		model.addAttribute("viewTranslationFormModel", viewTranslationBackingBean);
		Long defaultLocale = new Long(Constants.REFERENCE_LOCALE__EN);
		setTranslationDropDownContents(model, locale);
		setDropDownContents(model, null, locale);		
		model.addAttribute("defaultLocale", defaultLocale);
		
		return "view_localize";

	}
	
	
	

	// save or update View
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/view/post", method = RequestMethod.POST)
	public String saveOrUpdateView(Authentication authentication, @ModelAttribute("viewFormModel") @Validated ViewImpl view,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateView() : {}", view);
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		if (result.hasErrors()) {
			setDropDownContents(model, view, locale);
			String updateIssueMessage = messageSource.getMessage("viewUpdateIssueMessage", new String[0], locale);
			model.addAttribute("msg", updateIssueMessage);
			model.addAttribute("css", "alert-danger");
			
			return "view";
		} else {

			// Add message to flash scope
			redirectAttributes.addFlashAttribute("css", "success");
			if(view.isNew()){
				String addedSuccessMessage = messageSource.getMessage("viewAddedSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", addedSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}else{
				String updateSuccessMessage = messageSource.getMessage("viewUpdateSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", updateSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}


			try{
				// TODO: Needs exception handling policy
			    	viewService.storeView(view, userId);
			}
			catch (Exception ex){
				logger.error("Exception caught !!!!!!!!!!!!!!", ex);
			}
			
	
			

			
			// POST/REDIRECT/GET
			return "redirect:/view/" + view.getId() + "/update";
		}

	}

	// show add user form
	@RequestMapping(value = "/view/add", method = RequestMethod.GET)
	public String showAddViewForm(Model model, Locale locale) {

		logger.debug("showAddViewForm()");

		View view = new ViewImpl();
		
		view.setCurrentMode(View.CurrentMode.ADD);

		model.addAttribute("viewFormModel", view);

		setDropDownContents(model, view, locale);

		return "view";

	}
	
	// support access to the supporting webpage by creating a new instance and returning 
	@RequestMapping(value = "/viewwebpage", method = RequestMethod.GET)
	public String createViewForWebPageView(Model model, HttpServletRequest request, Locale locale) {

		logger.debug("createViewForWebPageView()");

		View view = new ViewImpl();
		
		try{
			// TODO: Needs exception handling policy
	    	viewService.storeView(view, userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
		

		model.addAttribute("viewFormModel", view);

		setDropDownContents(model, view, locale);

		return "viewwebpage";

	}
	

	// show update form
	@RequestMapping(value = "/view/{id}/update", method = RequestMethod.GET)
	public String showUpdateViewForm(@PathVariable("id") int id, Model model, Locale locale) {

		logger.debug("showUpdateViewForm() : {}", id);
		View view = null;
		try{
			// TODO: Needs exception handling policy
			view = viewService.load(new Long(id), userId);
			view.setCurrentMode(View.CurrentMode.UPDATE);
			this.viewService.translateReferenceValues(view, locale);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		model.addAttribute("viewFormModel", view);
		
		setDropDownContents(model, view, locale);
		
		return "view";

	}

	// delete view
	@RequestMapping(value = "/view/{id}/delete", method = RequestMethod.POST)
	public String deleteView(@PathVariable("id") int id, 
		final RedirectAttributes redirectAttributes) {

		logger.debug("deleteUser() : {}", id);

		//viewService.delete(id);
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "User is deleted!");
		
		return "redirect:/view/all";

	}

	// show user
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String showView(@PathVariable("id") int id, Model model, Locale locale) {

		logger.debug("showView() id: {}", id);
		View view = null;
		try{
			// TODO: Needs exception handling policy
			view = viewService.load(new Long(id), userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		if (view == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "User not found");
		}
		model.addAttribute("view", view);
		
		setDropDownContents(model, view, locale);

		return "showview";

	}
	
	
	// save or update ViewTranslation
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/view/translate", method = RequestMethod.POST)
	public String saveOrUpdateViewTranslation(Authentication authentication, @ModelAttribute("viewTranslationFormModel") ViewTranslationBackingBeanImpl viewTranslationBackingBean,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateViewTranslation() : {}", viewTranslationBackingBean);
		Long translationLocaleReferenceId = viewTranslationBackingBean.getNewLocale();
		String translationLocale = messageSource.getMessage(referenceStore.getRefDesc(translationLocaleReferenceId), new String[0], new Locale("en"));
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		try{
			
			// TODO: Needs exception handling policy
			List<MessageResource> messageResourceList  = new ArrayList<MessageResource>();
			
			MessageResource messageResource = populateMessageResource(ViewTranslationBackingBeanImpl.VIEW_HEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, viewTranslationBackingBean.getViewTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(ViewTranslationBackingBeanImpl.VIEW_TITLE_MAPPING_KEY, translationLocale, translationLocaleReferenceId, viewTranslationBackingBean.getFormTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(ViewTranslationBackingBeanImpl.VIEW_SUBHEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, viewTranslationBackingBean.getFormSubHeader());
			messageResourceList.add(messageResource);
			
	messageResource = populateMessageResource(ViewTranslationBackingBeanImpl.VIEWNAME_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, viewTranslationBackingBean.getViewNameLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ViewTranslationBackingBeanImpl.VIEWNAME_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, viewTranslationBackingBean.getViewNamePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ViewTranslationBackingBeanImpl.VIEWNAME_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, viewTranslationBackingBean.getViewNameHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(ViewTranslationBackingBeanImpl.SAVEBUTTON_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, viewTranslationBackingBean.getSaveButtonLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ViewTranslationBackingBeanImpl.SAVEBUTTON_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, viewTranslationBackingBean.getSaveButtonPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ViewTranslationBackingBeanImpl.SAVEBUTTON_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, viewTranslationBackingBean.getSaveButtonHelpText());
  			messageResourceList.add(messageResource);
  		

			

			this.messageSource.updateTexts(messageResourceList, userId);			
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
			
		// POST/REDIRECT/GET
		return "redirect:/view/add?language=" + translationLocale;

	}

	private void setDropDownContents(Model model, View view, Locale locale) {
		
		
		Map<Long, String> localeMap = referenceStore.getLocale();
		SortedMap<Long, String> localizedLocaleMap = new TreeMap<Long, String>(localeMap);
		for (Map.Entry<Long, String> entry : localeMap.entrySet()) {
			localizedLocaleMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		
		model.addAttribute("localeMap", localizedLocaleMap);
	}


}
