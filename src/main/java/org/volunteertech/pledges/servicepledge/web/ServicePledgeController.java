package org.volunteertech.pledges.servicepledge.web;
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
import org.volunteertech.pledges.servicepledge.dao.ServicePledge;
import org.volunteertech.pledges.servicepledge.dao.ServicePledgeImpl;
import org.volunteertech.pledges.servicepledge.service.ServicePledgeService;
import org.volunteertech.pledges.servicepledge.dao.ServicePledgeLoadException;
import org.volunteertech.pledges.servicepledge.dao.ServicePledgeSaveException;
import org.volunteertech.pledges.servicepledge.validator.ServicePledgeFormValidator;
import org.volunteertech.pledges.servicepledge.view.ServicePledgeTranslationBackingBean;
import org.volunteertech.pledges.servicepledge.view.ServicePledgeTranslationBackingBeanImpl;
import org.volunteertech.pledges.main.web.BaseController;
import org.volunteertech.pledges.main.constants.Constants;
import org.volunteertech.pledges.localisation.dao.MessageResource;
import org.volunteertech.pledges.localisation.dao.MessageResourceImpl;
import org.volunteertech.pledges.localisation.service.MessageResourceService;

import org.volunteertech.pledges.pledge.dao.RegisterOfPledges;


import org.volunteertech.pledges.reference.ReferenceStore;

/**
 * The pledge services screen is used to add/edit a new/existing pledge of services
 * GUIHandler is attached to a form on JSP and contains all getters and setters for values displayed on the form.
 * GUIHandler accesses the business layer for loading/saving data. 
 * This class has been generated by the XSLT processor from the metadata and represents the
 * the GUI layer javabean for the ServicePledge Form.
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
public class ServicePledgeController extends BaseController
{

    /**
     * userId used for development. This should be taken from the session.
     */
	private Long userId = new Long(0);
	 
	final Logger logger = LoggerFactory.getLogger(ServicePledgeController.class);
	
	@Autowired
	private ReferenceStore referenceStore;
	
	@Autowired
	private ServicePledgeService servicePledgeService;

	@Autowired
	private ServicePledgeFormValidator servicePledgeFormValidator;
	
    @Autowired
    private DatabaseDrivenMessageSource messageSource;
    


    @Autowired
    private MessageResourceService messageResourceService;
  
    
	
	//Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(servicePledgeFormValidator);
	}
	
	
	/**
	 * Open the list page
	 */
	@RequestMapping(value = "/servicepledge/all", method = RequestMethod.GET)
	public String showAllServicePledge(Model model, Locale locale) {

		logger.debug("showAllServicePledge()");
			
		return "servicepledge_table";

	}
	
	/**
	 * Open the localize page
	 */
	@RequestMapping(value = "/servicepledge/localize", method = RequestMethod.GET)
	public String localizeServicePledge(Model model, Locale locale) {

		logger.debug("localizeServicePledge()");

		ServicePledgeTranslationBackingBean servicePledgeTranslationBackingBean = new ServicePledgeTranslationBackingBeanImpl();
		servicePledgeTranslationBackingBean.setCurrentMode(ServicePledge.CurrentMode.LOCALIZE);
		model.addAttribute("servicePledgeTranslationFormModel", servicePledgeTranslationBackingBean);
		Long defaultLocale = new Long(Constants.REFERENCE_LOCALE__EN);
		setTranslationDropDownContents(model, locale);
		setDropDownContents(model, null, locale);		
		model.addAttribute("defaultLocale", defaultLocale);
		
		return "servicepledge_localize";

	}
	
	
	

	// save or update ServicePledge
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/servicepledge/post", method = RequestMethod.POST)
	public String saveOrUpdateServicePledge(Authentication authentication, @ModelAttribute("servicePledgeFormModel") @Validated ServicePledgeImpl servicePledge,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateServicePledge() : {}", servicePledge);
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		if (result.hasErrors()) {
			setDropDownContents(model, servicePledge, locale);
			String updateIssueMessage = messageSource.getMessage("servicePledgeUpdateIssueMessage", new String[0], locale);
			model.addAttribute("msg", updateIssueMessage);
			model.addAttribute("css", "alert-danger");
			
			return "servicepledge";
		} else {

			// Add message to flash scope
			redirectAttributes.addFlashAttribute("css", "success");
			if(servicePledge.isNew()){
				String addedSuccessMessage = messageSource.getMessage("servicePledgeAddedSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", addedSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}else{
				String updateSuccessMessage = messageSource.getMessage("servicePledgeUpdateSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", updateSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}


			try{
				// TODO: Needs exception handling policy
			    	servicePledgeService.storeServicePledge(servicePledge, userId);
			}
			catch (Exception ex){
				logger.error("Exception caught !!!!!!!!!!!!!!", ex);
			}
			
	
			

			
			// POST/REDIRECT/GET
			return "redirect:/servicepledge/" + servicePledge.getId() + "/update";
		}

	}

	// show add user form
	@RequestMapping(value = "/servicepledge/add", method = RequestMethod.GET)
	public String showAddServicePledgeForm(Model model, Locale locale) {

		logger.debug("showAddServicePledgeForm()");

		ServicePledge servicePledge = new ServicePledgeImpl();
		
		servicePledge.setCurrentMode(ServicePledge.CurrentMode.ADD);

		model.addAttribute("servicePledgeFormModel", servicePledge);

		setDropDownContents(model, servicePledge, locale);

		return "servicepledge";

	}
	
	// support access to the supporting webpage by creating a new instance and returning 
	@RequestMapping(value = "/servicepledgewebpage", method = RequestMethod.GET)
	public String createServicePledgeForWebPageView(Model model, HttpServletRequest request, Locale locale) {

		logger.debug("createServicePledgeForWebPageView()");

		ServicePledge servicePledge = new ServicePledgeImpl();
		
		try{
			// TODO: Needs exception handling policy
	    	servicePledgeService.storeServicePledge(servicePledge, userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
		

		model.addAttribute("servicePledgeFormModel", servicePledge);

		setDropDownContents(model, servicePledge, locale);

		return "servicepledgewebpage";

	}
	

	// show update form
	@RequestMapping(value = "/servicepledge/{id}/update", method = RequestMethod.GET)
	public String showUpdateServicePledgeForm(@PathVariable("id") int id, Model model, Locale locale) {

		logger.debug("showUpdateServicePledgeForm() : {}", id);
		ServicePledge servicePledge = null;
		try{
			// TODO: Needs exception handling policy
			servicePledge = servicePledgeService.load(new Long(id), userId);
			servicePledge.setCurrentMode(ServicePledge.CurrentMode.UPDATE);
			this.servicePledgeService.translateReferenceValues(servicePledge, locale);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		model.addAttribute("servicePledgeFormModel", servicePledge);
		
		setDropDownContents(model, servicePledge, locale);
		
		return "servicepledge";

	}

	// delete servicePledge
	@RequestMapping(value = "/servicepledge/{id}/delete", method = RequestMethod.POST)
	public String deleteServicePledge(@PathVariable("id") int id, 
		final RedirectAttributes redirectAttributes) {

		logger.debug("deleteUser() : {}", id);

		//servicePledgeService.delete(id);
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "User is deleted!");
		
		return "redirect:/servicepledge/all";

	}

	// show user
	@RequestMapping(value = "/servicepledge/{id}", method = RequestMethod.GET)
	public String showServicePledge(@PathVariable("id") int id, Model model, Locale locale) {

		logger.debug("showServicePledge() id: {}", id);
		ServicePledge servicePledge = null;
		try{
			// TODO: Needs exception handling policy
			servicePledge = servicePledgeService.load(new Long(id), userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		if (servicePledge == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "User not found");
		}
		model.addAttribute("servicePledge", servicePledge);
		
		setDropDownContents(model, servicePledge, locale);

		return "showservicepledge";

	}
	
	
	// save or update ServicePledgeTranslation
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/servicepledge/translate", method = RequestMethod.POST)
	public String saveOrUpdateServicePledgeTranslation(Authentication authentication, @ModelAttribute("servicePledgeTranslationFormModel") ServicePledgeTranslationBackingBeanImpl servicePledgeTranslationBackingBean,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateServicePledgeTranslation() : {}", servicePledgeTranslationBackingBean);
		Long translationLocaleReferenceId = servicePledgeTranslationBackingBean.getNewLocale();
		String translationLocale = messageSource.getMessage(referenceStore.getRefDesc(translationLocaleReferenceId), new String[0], new Locale("en"));
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		try{
			
			// TODO: Needs exception handling policy
			List<MessageResource> messageResourceList  = new ArrayList<MessageResource>();
			
			MessageResource messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.SERVICEPLEDGE_HEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getViewTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.SERVICEPLEDGE_TITLE_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getFormTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.SERVICEPLEDGE_SUBHEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getFormSubHeader());
			messageResourceList.add(messageResource);
			
	messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICELEVELONE_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceLevelOneLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICELEVELONE_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceLevelOnePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICELEVELONE_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceLevelOneHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICELEVELTWO_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceLevelTwoLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICELEVELTWO_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceLevelTwoPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICELEVELTWO_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceLevelTwoHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICELEVELTHREE_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceLevelThreeLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICELEVELTHREE_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceLevelThreePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICELEVELTHREE_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceLevelThreeHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.ADDITIONALINFORMATION_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getAdditionalInformationLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.ADDITIONALINFORMATION_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getAdditionalInformationPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.ADDITIONALINFORMATION_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getAdditionalInformationHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICEQUALIFICATION_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceQualificationLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICEQUALIFICATION_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceQualificationPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICEQUALIFICATION_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceQualificationHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICEDATEAVAILABLE_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceDateAvailableLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICEDATEAVAILABLE_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceDateAvailablePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICEDATEAVAILABLE_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceDateAvailableHelpText());
  			messageResourceList.add(messageResource);
  		
  			
  	messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICEDATEAVAILABLETO_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceDateAvailableLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICEDATEAVAILABLETO_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceDateAvailablePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICEDATEAVAILABLETO_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceDateAvailableHelpText());
  			messageResourceList.add(messageResource);
  			
	messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICEHOURSPERWEEK_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceHoursPerWeekLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICEHOURSPERWEEK_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceHoursPerWeekPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.PLEDGESERVICEHOURSPERWEEK_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getPledgeServiceHoursPerWeekHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.SAVEBUTTON_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getSaveButtonLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.SAVEBUTTON_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getSaveButtonPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ServicePledgeTranslationBackingBeanImpl.SAVEBUTTON_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, servicePledgeTranslationBackingBean.getSaveButtonHelpText());
  			messageResourceList.add(messageResource);
  		

			

			this.messageSource.updateTexts(messageResourceList, userId);			
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
			
		// POST/REDIRECT/GET
		return "redirect:/servicepledge/add?language=" + translationLocale;

	}
	
	private MessageResource populateMessageResource(String messageKey, String locale, Long localeReferenceId, String message){
		MessageResource messageResource = new MessageResourceImpl();
		messageResource.setMessageKey(messageKey);
		messageResource.setLocale(locale);
		messageResource.setLocaleReferenceId(localeReferenceId);
		messageResource.setMessage(message);

		
		return messageResource;
	
	}
	

	/**
	 * Opens the details of the RegisterOfPledges that owns the ServicePledge identified by the
	 * id given as a parameter.
	 * @param id the id of the ServicePledge for which the RegisterOfPledges should be resolved.
	 */
	@RequestMapping(value = "/servicepledge/{id}/registerofpledges", method = RequestMethod.GET)
	public String showRegisterOfPledges(Authentication authentication, @PathVariable("id") int id, 
		final RedirectAttributes redirectAttributes) {
		String returnPath = null;

		logger.debug("showRegisterOfPledges() : {}", id);
		
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		//TODO: Needs exception handling
		
		List<RegisterOfPledges> registerOfPledgesList = this.servicePledgeService.getServicePledgeBo().getServicePledgeDao().listRegisterOfPledgesByServicePledgeId(new Long(id), userId);

		if (registerOfPledgesList.size() == 1){
			returnPath = "forward:/registerofpledges/" + registerOfPledgesList.get(0).getId() + "/update";
		}
		else{
			returnPath = "forward:/registerofpledges/all";
		}
		
		return returnPath;

	}

	

	private void setDropDownContents(Model model, ServicePledge servicePledge, Locale locale) {
		
		Map<Long, String> pledgeServiceLevelOneMap = referenceStore.getPledgeServiceLevelOne();
		SortedMap<Long, String> localizedpledgeServiceLevelOneMap = new TreeMap<Long, String>(pledgeServiceLevelOneMap);
		for (Map.Entry<Long, String> entry : pledgeServiceLevelOneMap.entrySet()) {
			localizedpledgeServiceLevelOneMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("pledgeServiceLevelOneMap", localizedpledgeServiceLevelOneMap);
	      
		Map<Long, String> pledgeServiceLevelTwoMap = referenceStore.getPledgeServiceLevelTwo();
		SortedMap<Long, String> localizedpledgeServiceLevelTwoMap = new TreeMap<Long, String>(pledgeServiceLevelTwoMap);
		for (Map.Entry<Long, String> entry : pledgeServiceLevelTwoMap.entrySet()) {
			localizedpledgeServiceLevelTwoMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("pledgeServiceLevelTwoMap", localizedpledgeServiceLevelTwoMap);
	      
		Map<Long, String> pledgeServiceLevelThreeMap = referenceStore.getPledgeServiceLevelThree();
		SortedMap<Long, String> localizedpledgeServiceLevelThreeMap = new TreeMap<Long, String>(pledgeServiceLevelThreeMap);
		for (Map.Entry<Long, String> entry : pledgeServiceLevelThreeMap.entrySet()) {
			localizedpledgeServiceLevelThreeMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("pledgeServiceLevelThreeMap", localizedpledgeServiceLevelThreeMap);
	      
		Map<Long, String> pledgeServiceHoursPerWeekMap = referenceStore.getIntegerCount1to40();
		SortedMap<Long, String> localizedpledgeServiceHoursPerWeekMap = new TreeMap<Long, String>(pledgeServiceHoursPerWeekMap);
		for (Map.Entry<Long, String> entry : pledgeServiceHoursPerWeekMap.entrySet()) {
			localizedpledgeServiceHoursPerWeekMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("pledgeServiceHoursPerWeekMap", localizedpledgeServiceHoursPerWeekMap);
	      
		
		Map<Long, String> localeMap = referenceStore.getLocale();
		SortedMap<Long, String> localizedLocaleMap = new TreeMap<Long, String>(localeMap);
		for (Map.Entry<Long, String> entry : localeMap.entrySet()) {
			localizedLocaleMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		
		model.addAttribute("localeMap", localizedLocaleMap);
	}
	
	private void setTranslationDropDownContents(Model model, Locale locale) {
		Map<Long, String> localeMap = referenceStore.getLocale();
		SortedMap<Long, String> localizedLocaleMap = new TreeMap<Long, String>(localeMap);
		for (Map.Entry<Long, String> entry : localeMap.entrySet()) {
			localizedLocaleMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("localeMap", localizedLocaleMap);
	}
	

}
