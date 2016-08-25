package org.volunteertech.pledges.pledge.web;
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

import com.netgrains.security.Authorisation;
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
import org.volunteertech.pledges.pledge.dao.RegisterOfPledges;
import org.volunteertech.pledges.pledge.dao.RegisterOfPledgesImpl;
import org.volunteertech.pledges.pledge.service.RegisterOfPledgesService;
import org.volunteertech.pledges.pledge.dao.RegisterOfPledgesLoadException;
import org.volunteertech.pledges.pledge.dao.RegisterOfPledgesSaveException;
import org.volunteertech.pledges.pledge.validator.RegisterOfPledgesFormValidator;
import org.volunteertech.pledges.pledge.view.RegisterOfPledgesTranslationBackingBean;
import org.volunteertech.pledges.pledge.view.RegisterOfPledgesTranslationBackingBeanImpl;
import org.volunteertech.pledges.main.web.BaseController;
import org.volunteertech.pledges.main.constants.Constants;
import org.volunteertech.pledges.localisation.dao.MessageResource;
import org.volunteertech.pledges.localisation.dao.MessageResourceImpl;
import org.volunteertech.pledges.localisation.service.MessageResourceService;


import org.volunteertech.pledges.reference.ReferenceStore;

/**
 * The register of pledges screen is used to add/edit new/existing pledges
 * GUIHandler is attached to a form on JSP and contains all getters and setters for values displayed on the form.
 * GUIHandler accesses the business layer for loading/saving data. 
 * This class has been generated by the XSLT processor from the metadata and represents the
 * the GUI layer javabean for the RegisterOfPledges Form.
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
public class RegisterOfPledgesController extends BaseController
{

    /**
     * userId used for development. This should be taken from the session.
     */
	private Long userId = new Long(0);
	 
	final Logger logger = LoggerFactory.getLogger(RegisterOfPledgesController.class);
	
	@Autowired
	private ReferenceStore referenceStore;
	
	@Autowired
	private RegisterOfPledgesService registerOfPledgesService;

	@Autowired
	private RegisterOfPledgesFormValidator registerOfPledgesFormValidator;
	
    @Autowired
    private DatabaseDrivenMessageSource messageSource;
    


    @Autowired
    private MessageResourceService messageResourceService;
  
    
	
	//Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(registerOfPledgesFormValidator);
	}
	
	
	/**
	 * Open the list page
	 */
	@RequestMapping(value = "/registerofpledges/all", method = RequestMethod.GET)
	public String showAllRegisterOfPledges(Model model, Locale locale) {

		logger.debug("showAllRegisterOfPledges()");
			
		return "registerofpledges_table";

	}
	
	/**
	 * Open the localize page
	 */
	@RequestMapping(value = "/registerofpledges/localize", method = RequestMethod.GET)
	public String localizeRegisterOfPledges(Model model, Locale locale) {

		logger.debug("localizeRegisterOfPledges()");

		RegisterOfPledgesTranslationBackingBean registerOfPledgesTranslationBackingBean = new RegisterOfPledgesTranslationBackingBeanImpl();
		registerOfPledgesTranslationBackingBean.setCurrentMode(RegisterOfPledges.CurrentMode.LOCALIZE);
		model.addAttribute("registerOfPledgesTranslationFormModel", registerOfPledgesTranslationBackingBean);
		Long defaultLocale = new Long(Constants.REFERENCE_LOCALE__EN);
		setTranslationDropDownContents(model, locale);
		setDropDownContents(model, null, locale);		
		model.addAttribute("defaultLocale", defaultLocale);
		
		return "registerofpledges_localize";

	}
	
	
	

	// save or update RegisterOfPledges
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/registerofpledges/post", method = RequestMethod.POST)
	public String saveOrUpdateRegisterOfPledges(Authentication authentication, @ModelAttribute("registerOfPledgesFormModel") @Validated RegisterOfPledgesImpl registerOfPledges,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateRegisterOfPledges() : {}", registerOfPledges);
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		if (result.hasErrors()) {
			setDropDownContents(model, registerOfPledges, locale);
			String updateIssueMessage = messageSource.getMessage("registerOfPledgesUpdateIssueMessage", new String[0], locale);
			model.addAttribute("msg", updateIssueMessage);
			model.addAttribute("css", "alert-danger");
			
			return "registerofpledges";
		} else {

			// Add message to flash scope
			redirectAttributes.addFlashAttribute("css", "success");
			if(registerOfPledges.isNew()){
				String addedSuccessMessage = messageSource.getMessage("registerOfPledgesAddedSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", addedSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}else{
				String updateSuccessMessage = messageSource.getMessage("registerOfPledgesUpdateSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", updateSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}


			try{
				// TODO: Needs exception handling policy
			    	registerOfPledgesService.storeRegisterOfPledges(registerOfPledges, userId);
			}
			catch (Exception ex){
				logger.error("Exception caught !!!!!!!!!!!!!!", ex);
			}
			
	
			

			
			// POST/REDIRECT/GET
			return "redirect:/registerofpledges/" + registerOfPledges.getId() + "/update";
		}

	}

	// show add user form
	@RequestMapping(value = "/registerofpledges/add", method = RequestMethod.GET)
	public String showAddRegisterOfPledgesForm(Model model, Locale locale) {

		logger.debug("showAddRegisterOfPledgesForm()");

		RegisterOfPledges registerOfPledges = new RegisterOfPledgesImpl();
		
		registerOfPledges.setCurrentMode(RegisterOfPledges.CurrentMode.ADD);

		model.addAttribute("registerOfPledgesFormModel", registerOfPledges);

		setDropDownContents(model, registerOfPledges, locale);

		return "registerofpledges";

	}
	
	// support access to the supporting webpage by creating a new instance and returning 
	@RequestMapping(value = "/registerofpledgeswebpage", method = RequestMethod.GET)
	public String createRegisterOfPledgesForWebPageView(Model model, HttpServletRequest request, Locale locale) {

		logger.debug("createRegisterOfPledgesForWebPageView()");

		RegisterOfPledges registerOfPledges = new RegisterOfPledgesImpl();
		
		try{
			// TODO: Needs exception handling policy
	    	registerOfPledgesService.storeRegisterOfPledges(registerOfPledges, userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
		

		model.addAttribute("registerOfPledgesFormModel", registerOfPledges);

		setDropDownContents(model, registerOfPledges, locale);

		return "registerofpledgeswebpage";

	}
	

	// show update form
	@RequestMapping(value = "/registerofpledges/{id}/update", method = RequestMethod.GET)
	public String showUpdateRegisterOfPledgesForm(Authentication authentication, @PathVariable("id") int id, Model model, Locale locale) {
		logger.debug("showUpdateRegisterOfPledgesForm() : {}", id);
		Authorisation.checkAccess(authentication, id);
		RegisterOfPledges registerOfPledges = null;
		try{
			// TODO: Needs exception handling policy
			registerOfPledges = registerOfPledgesService.load(new Long(id), userId);
			registerOfPledges.setCurrentMode(RegisterOfPledges.CurrentMode.UPDATE);
			this.registerOfPledgesService.translateReferenceValues(registerOfPledges, locale);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		model.addAttribute("registerOfPledgesFormModel", registerOfPledges);
		
		setDropDownContents(model, registerOfPledges, locale);
		
		return "registerofpledges";

	}

	// delete registerOfPledges
	@RequestMapping(value = "/registerofpledges/{id}/delete", method = RequestMethod.POST)
	public String deleteRegisterOfPledges(@PathVariable("id") int id, 
		final RedirectAttributes redirectAttributes) {

		logger.debug("deleteUser() : {}", id);

		//registerOfPledgesService.delete(id);
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "User is deleted!");
		
		return "redirect:/registerofpledges/all";

	}

	// show user
	@RequestMapping(value = "/registerofpledges/{id}", method = RequestMethod.GET)
	public String showRegisterOfPledges(@PathVariable("id") int id, Model model, Locale locale) {

		logger.debug("showRegisterOfPledges() id: {}", id);
		RegisterOfPledges registerOfPledges = null;
		try{
			// TODO: Needs exception handling policy
			registerOfPledges = registerOfPledgesService.load(new Long(id), userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		if (registerOfPledges == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "User not found");
		}
		model.addAttribute("registerOfPledges", registerOfPledges);
		
		setDropDownContents(model, registerOfPledges, locale);

		return "showregisterofpledges";

	}
	
	
	// save or update RegisterOfPledgesTranslation
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/registerofpledges/translate", method = RequestMethod.POST)
	public String saveOrUpdateRegisterOfPledgesTranslation(Authentication authentication, @ModelAttribute("registerOfPledgesTranslationFormModel") RegisterOfPledgesTranslationBackingBeanImpl registerOfPledgesTranslationBackingBean,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateRegisterOfPledgesTranslation() : {}", registerOfPledgesTranslationBackingBean);
		Long translationLocaleReferenceId = registerOfPledgesTranslationBackingBean.getNewLocale();
		String translationLocale = messageSource.getMessage(referenceStore.getRefDesc(translationLocaleReferenceId), new String[0], new Locale("en"));
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		try{
			
			// TODO: Needs exception handling policy
			List<MessageResource> messageResourceList  = new ArrayList<MessageResource>();
			
			MessageResource messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.REGISTEROFPLEDGES_HEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getViewTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.REGISTEROFPLEDGES_TITLE_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getFormTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.REGISTEROFPLEDGES_SUBHEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getFormSubHeader());
			messageResourceList.add(messageResource);
			
			messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.CONTACTINFORMATIONINSTRUCTIONS_MESSAGE_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getContactInformationInstructionsMessage());
  			messageResourceList.add(messageResource);
  		messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.CONTACTINFORMATIONINSTRUCTIONS_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getContactInformationInstructionsLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.CONTACTINFORMATIONINSTRUCTIONS_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getContactInformationInstructionsPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.CONTACTINFORMATIONINSTRUCTIONS_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getContactInformationInstructionsHelpText());
  			messageResourceList.add(messageResource);
  		
			messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.ACCOMMODATIONPLEDGEINSTRUCTIONS_MESSAGE_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getAccommodationPledgeInstructionsMessage());
  			messageResourceList.add(messageResource);
  		messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.ACCOMMODATIONPLEDGEINSTRUCTIONS_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getAccommodationPledgeInstructionsLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.ACCOMMODATIONPLEDGEINSTRUCTIONS_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getAccommodationPledgeInstructionsPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.ACCOMMODATIONPLEDGEINSTRUCTIONS_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getAccommodationPledgeInstructionsHelpText());
  			messageResourceList.add(messageResource);
  		
			messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.SERVICEPLEDGEINSTRUCTIONS_MESSAGE_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getServicePledgeInstructionsMessage());
  			messageResourceList.add(messageResource);
  		messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.SERVICEPLEDGEINSTRUCTIONS_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getServicePledgeInstructionsLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.SERVICEPLEDGEINSTRUCTIONS_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getServicePledgeInstructionsPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.SERVICEPLEDGEINSTRUCTIONS_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getServicePledgeInstructionsHelpText());
  			messageResourceList.add(messageResource);
  		
			messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.GOODSPLEDGEINSTRUCTIONS_MESSAGE_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getGoodsPledgeInstructionsMessage());
  			messageResourceList.add(messageResource);
  		messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.GOODSPLEDGEINSTRUCTIONS_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getGoodsPledgeInstructionsLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.GOODSPLEDGEINSTRUCTIONS_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getGoodsPledgeInstructionsPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(RegisterOfPledgesTranslationBackingBeanImpl.GOODSPLEDGEINSTRUCTIONS_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, registerOfPledgesTranslationBackingBean.getGoodsPledgeInstructionsHelpText());
  			messageResourceList.add(messageResource);
  		

			

			this.messageSource.updateTexts(messageResourceList, userId);			
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
			
		// POST/REDIRECT/GET
		return "redirect:/registerofpledges/add?language=" + translationLocale;

	}
	
	private void setDropDownContents(Model model, RegisterOfPledges registerOfPledges, Locale locale) {
		
		
		Map<Long, String> localeMap = referenceStore.getLocale();
		SortedMap<Long, String> localizedLocaleMap = new TreeMap<Long, String>(localeMap);
		for (Map.Entry<Long, String> entry : localeMap.entrySet()) {
			localizedLocaleMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		
		model.addAttribute("localeMap", localizedLocaleMap);
	}




}
