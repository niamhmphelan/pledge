package org.volunteertech.pledges.accommodationpledge.web;
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
import org.volunteertech.pledges.accommodationpledge.dao.AccommodationPledge;
import org.volunteertech.pledges.accommodationpledge.dao.AccommodationPledgeImpl;
import org.volunteertech.pledges.accommodationpledge.service.AccommodationPledgeService;
import org.volunteertech.pledges.accommodationpledge.dao.AccommodationPledgeLoadException;
import org.volunteertech.pledges.accommodationpledge.dao.AccommodationPledgeSaveException;
import org.volunteertech.pledges.accommodationpledge.validator.AccommodationPledgeFormValidator;
import org.volunteertech.pledges.accommodationpledge.view.AccommodationPledgeTranslationBackingBean;
import org.volunteertech.pledges.accommodationpledge.view.AccommodationPledgeTranslationBackingBeanImpl;
import org.volunteertech.pledges.main.web.BaseController;
import org.volunteertech.pledges.main.constants.Constants;
import org.volunteertech.pledges.localisation.dao.MessageResource;
import org.volunteertech.pledges.localisation.dao.MessageResourceImpl;
import org.volunteertech.pledges.localisation.service.MessageResourceService;

import org.volunteertech.pledges.pledge.dao.RegisterOfPledges;


import org.volunteertech.pledges.reference.ReferenceStore;

/**
 * The Accommodation pledge screen is used to add/edit a new/existing pledge of accommodation
 * GUIHandler is attached to a form on JSP and contains all getters and setters for values displayed on the form.
 * GUIHandler accesses the business layer for loading/saving data. 
 * This class has been generated by the XSLT processor from the metadata and represents the
 * the GUI layer javabean for the AccommodationPledge Form.
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
public class AccommodationPledgeController extends BaseController
{

    /**
     * userId used for development. This should be taken from the session.
     */
	private Long userId = new Long(0);
	 
	final Logger logger = LoggerFactory.getLogger(AccommodationPledgeController.class);
	
	@Autowired
	private ReferenceStore referenceStore;
	
	@Autowired
	private AccommodationPledgeService accommodationPledgeService;

	@Autowired
	private AccommodationPledgeFormValidator accommodationPledgeFormValidator;
	
    @Autowired
    private DatabaseDrivenMessageSource messageSource;
    


    @Autowired
    private MessageResourceService messageResourceService;
  
    
	
	//Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(accommodationPledgeFormValidator);
	}
	
	
	/**
	 * Open the list page
	 */
	@RequestMapping(value = "/accommodationpledge/all", method = RequestMethod.GET)
	public String showAllAccommodationPledge(Model model, Locale locale) {

		logger.debug("showAllAccommodationPledge()");
			
		return "accommodationpledge_table";

	}
	
	/**
	 * Open the localize page
	 */
	@RequestMapping(value = "/accommodationpledge/localize", method = RequestMethod.GET)
	public String localizeAccommodationPledge(Model model, Locale locale) {

		logger.debug("localizeAccommodationPledge()");

		AccommodationPledgeTranslationBackingBean accommodationPledgeTranslationBackingBean = new AccommodationPledgeTranslationBackingBeanImpl();
		accommodationPledgeTranslationBackingBean.setCurrentMode(AccommodationPledge.CurrentMode.LOCALIZE);
		model.addAttribute("accommodationPledgeTranslationFormModel", accommodationPledgeTranslationBackingBean);
		Long defaultLocale = new Long(Constants.REFERENCE_LOCALE__EN);
		setTranslationDropDownContents(model, locale);
		setDropDownContents(model, null, locale);		
		model.addAttribute("defaultLocale", defaultLocale);
		
		return "accommodationpledge_localize";

	}
	
	
	

	// save or update AccommodationPledge
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/accommodationpledge/post", method = RequestMethod.POST)
	public String saveOrUpdateAccommodationPledge(Authentication authentication, @ModelAttribute("accommodationPledgeFormModel") @Validated AccommodationPledgeImpl accommodationPledge,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateAccommodationPledge() : {}", accommodationPledge);
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		if (result.hasErrors()) {
			setDropDownContents(model, accommodationPledge, locale);
			String updateIssueMessage = messageSource.getMessage("accommodationPledgeUpdateIssueMessage", new String[0], locale);
			model.addAttribute("msg", updateIssueMessage);
			model.addAttribute("css", "alert-danger");
			
			return "accommodationpledge";
		} else {

			// Add message to flash scope
			redirectAttributes.addFlashAttribute("css", "success");
			if(accommodationPledge.isNew()){
				String addedSuccessMessage = messageSource.getMessage("accommodationPledgeAddedSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", addedSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}else{
				String updateSuccessMessage = messageSource.getMessage("accommodationPledgeUpdateSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", updateSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}


			try{
				// TODO: Needs exception handling policy
			    	accommodationPledgeService.storeAccommodationPledge(accommodationPledge, userId);
			}
			catch (Exception ex){
				logger.error("Exception caught !!!!!!!!!!!!!!", ex);
			}
			
	
			

			
			// POST/REDIRECT/GET
//			return "redirect:/accommodationpledge/" + accommodationPledge.getId() + "/update";
			return "redirect:/entitylist/";
		}

	}

	// show add user form
	@RequestMapping(value = "/accommodationpledge/add", method = RequestMethod.GET)
	public String showAddAccommodationPledgeForm(Model model, Locale locale) {

		logger.debug("showAddAccommodationPledgeForm()");

		AccommodationPledge accommodationPledge = new AccommodationPledgeImpl();
		
		accommodationPledge.setCurrentMode(AccommodationPledge.CurrentMode.ADD);

		model.addAttribute("accommodationPledgeFormModel", accommodationPledge);

		setDropDownContents(model, accommodationPledge, locale);

		return "accommodationpledge";

	}
	
	// support access to the supporting webpage by creating a new instance and returning 
	@RequestMapping(value = "/accommodationpledgewebpage", method = RequestMethod.GET)
	public String createAccommodationPledgeForWebPageView(Model model, HttpServletRequest request, Locale locale) {

		logger.debug("createAccommodationPledgeForWebPageView()");

		AccommodationPledge accommodationPledge = new AccommodationPledgeImpl();
		
		try{
			// TODO: Needs exception handling policy
	    	accommodationPledgeService.storeAccommodationPledge(accommodationPledge, userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
		

		model.addAttribute("accommodationPledgeFormModel", accommodationPledge);

		setDropDownContents(model, accommodationPledge, locale);

		return "accommodationpledgewebpage";

	}
	

	// show update form
	@RequestMapping(value = "/accommodationpledge/{id}/update", method = RequestMethod.GET)
	public String showUpdateAccommodationPledgeForm(@PathVariable("id") int id, Model model, Locale locale) {

		logger.debug("showUpdateAccommodationPledgeForm() : {}", id);
		AccommodationPledge accommodationPledge = null;
		try{
			// TODO: Needs exception handling policy
			accommodationPledge = accommodationPledgeService.load(new Long(id), userId);
			accommodationPledge.setCurrentMode(AccommodationPledge.CurrentMode.UPDATE);
			this.accommodationPledgeService.translateReferenceValues(accommodationPledge, locale);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		model.addAttribute("accommodationPledgeFormModel", accommodationPledge);
		
		setDropDownContents(model, accommodationPledge, locale);
		
		return "accommodationpledge";

	}

	// delete accommodationPledge
	@RequestMapping(value = "/accommodationpledge/{id}/delete", method = RequestMethod.POST)
	public String deleteAccommodationPledge(@PathVariable("id") int id, 
		final RedirectAttributes redirectAttributes) {

		logger.debug("deleteUser() : {}", id);

		//accommodationPledgeService.delete(id);
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "User is deleted!");
		
		return "redirect:/accommodationpledge/all";

	}

	// show user
	@RequestMapping(value = "/accommodationpledge/{id}", method = RequestMethod.GET)
	public String showAccommodationPledge(@PathVariable("id") int id, Model model, Locale locale) {

		logger.debug("showAccommodationPledge() id: {}", id);
		AccommodationPledge accommodationPledge = null;
		try{
			// TODO: Needs exception handling policy
			accommodationPledge = accommodationPledgeService.load(new Long(id), userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		if (accommodationPledge == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "User not found");
		}
		model.addAttribute("accommodationPledge", accommodationPledge);
		
		setDropDownContents(model, accommodationPledge, locale);

		return "showaccommodationpledge";

	}
	
	
	// save or update AccommodationPledgeTranslation
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/accommodationpledge/translate", method = RequestMethod.POST)
	public String saveOrUpdateAccommodationPledgeTranslation(Authentication authentication, @ModelAttribute("accommodationPledgeTranslationFormModel") AccommodationPledgeTranslationBackingBeanImpl accommodationPledgeTranslationBackingBean,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateAccommodationPledgeTranslation() : {}", accommodationPledgeTranslationBackingBean);
		Long translationLocaleReferenceId = accommodationPledgeTranslationBackingBean.getNewLocale();
		String translationLocale = messageSource.getMessage(referenceStore.getRefDesc(translationLocaleReferenceId), new String[0], new Locale("en"));
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		try{
			
			// TODO: Needs exception handling policy
			List<MessageResource> messageResourceList  = new ArrayList<MessageResource>();
			
			MessageResource messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ACCOMMODATIONPLEDGE_HEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getViewTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ACCOMMODATIONPLEDGE_TITLE_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getFormTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ACCOMMODATIONPLEDGE_SUBHEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getFormSubHeader());
			messageResourceList.add(messageResource);
			
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ADDRESSONE_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAddressOneLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ADDRESSONE_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAddressOnePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ADDRESSONE_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAddressOneHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ADDRESSTWO_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAddressTwoLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ADDRESSTWO_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAddressTwoPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ADDRESSTWO_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAddressTwoHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.CITY_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getCityLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.CITY_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getCityPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.CITY_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getCityHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.STATEPROVINCEREGION_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getStateProvinceRegionLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.STATEPROVINCEREGION_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getStateProvinceRegionPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.STATEPROVINCEREGION_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getStateProvinceRegionHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.POSTCODE_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getPostCodeLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.POSTCODE_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getPostCodePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.POSTCODE_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getPostCodeHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.COUNTRY_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getCountryLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.COUNTRY_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getCountryPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.COUNTRY_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getCountryHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.OWNEROCCUPIER_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getOwnerOccupierLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.OWNEROCCUPIER_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getOwnerOccupierPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.OWNEROCCUPIER_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getOwnerOccupierHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ACCOMMODATIONDATEFROM_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAccommodationDateFromLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ACCOMMODATIONDATEFROM_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAccommodationDateFromPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ACCOMMODATIONDATEFROM_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAccommodationDateFromHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ACCOMMODATIONDATETO_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAccommodationDateToLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ACCOMMODATIONDATETO_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAccommodationDateToPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ACCOMMODATIONDATETO_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAccommodationDateToHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ACCOMMODATIONTYPE_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAccommodationTypeLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ACCOMMODATIONTYPE_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAccommodationTypePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ACCOMMODATIONTYPE_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAccommodationTypeHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ACCOMMODATIONCONDITION_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAccommodationConditionLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ACCOMMODATIONCONDITION_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAccommodationConditionPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ACCOMMODATIONCONDITION_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAccommodationConditionHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.NUMBEROFBEDS_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getNumberOfBedsLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.NUMBEROFBEDS_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getNumberOfBedsPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.NUMBEROFBEDS_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getNumberOfBedsHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.VACANTORSHARED_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getVacantOrSharedLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.VACANTORSHARED_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getVacantOrSharedPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.VACANTORSHARED_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getVacantOrSharedHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.OTHERAMENITIES_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getOtherAmenitiesLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.OTHERAMENITIES_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getOtherAmenitiesPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.OTHERAMENITIES_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getOtherAmenitiesHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.CANYOUACCOMMODATE_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getCanYouAccommodateLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.CANYOUACCOMMODATE_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getCanYouAccommodatePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.CANYOUACCOMMODATE_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getCanYouAccommodateHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ADDITIONALINFORMATION_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAdditionalInformationLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ADDITIONALINFORMATION_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAdditionalInformationPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.ADDITIONALINFORMATION_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getAdditionalInformationHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.SAVEBUTTON_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getSaveButtonLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.SAVEBUTTON_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getSaveButtonPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AccommodationPledgeTranslationBackingBeanImpl.SAVEBUTTON_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, accommodationPledgeTranslationBackingBean.getSaveButtonHelpText());
  			messageResourceList.add(messageResource);
  		

			

			this.messageSource.updateTexts(messageResourceList, userId);			
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
			
		// POST/REDIRECT/GET
		return "redirect:/accommodationpledge/add?language=" + translationLocale;

	}

	/**
	 * Opens the details of the RegisterOfPledges that owns the AccommodationPledge identified by the
	 * id given as a parameter.
	 * @param id the id of the AccommodationPledge for which the RegisterOfPledges should be resolved.
	 */
	@RequestMapping(value = "/accommodationpledge/{id}/registerofpledges", method = RequestMethod.GET)
	public String showRegisterOfPledges(Authentication authentication, @PathVariable("id") int id, 
		final RedirectAttributes redirectAttributes) {
		String returnPath = null;

		logger.debug("showRegisterOfPledges() : {}", id);
		
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		//TODO: Needs exception handling
		
		List<RegisterOfPledges> registerOfPledgesList = this.accommodationPledgeService.getAccommodationPledgeBo().getAccommodationPledgeDao().listRegisterOfPledgesByAccommodationPledgeId(new Long(id), userId);

		if (registerOfPledgesList.size() == 1){
			returnPath = "forward:/registerofpledges/" + registerOfPledgesList.get(0).getId() + "/update";
		}
		else{
			returnPath = "forward:/registerofpledges/all";
		}
		
		return returnPath;

	}

	

	private void setDropDownContents(Model model, AccommodationPledge accommodationPledge, Locale locale) {
		
		Map<Long, String> countryMap = referenceStore.getEuropeCountry();
		SortedMap<Long, String> localizedcountryMap = new TreeMap<Long, String>(countryMap);
		for (Map.Entry<Long, String> entry : countryMap.entrySet()) {
			localizedcountryMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("countryMap", localizedcountryMap);
	      
		Map<Long, String> ownerOccupierMap = referenceStore.getOwnerOccupierType();
		SortedMap<Long, String> localizedownerOccupierMap = new TreeMap<Long, String>(ownerOccupierMap);
		for (Map.Entry<Long, String> entry : ownerOccupierMap.entrySet()) {
			localizedownerOccupierMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("ownerOccupierMap", localizedownerOccupierMap);
	      
		Map<Long, String> accommodationTypeMap = referenceStore.getAccommodationType();
		SortedMap<Long, String> localizedaccommodationTypeMap = new TreeMap<Long, String>(accommodationTypeMap);
		for (Map.Entry<Long, String> entry : accommodationTypeMap.entrySet()) {
			localizedaccommodationTypeMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("accommodationTypeMap", localizedaccommodationTypeMap);
	      
		Map<Long, String> accommodationConditionMap = referenceStore.getAccommodationCondition();
		SortedMap<Long, String> localizedaccommodationConditionMap = new TreeMap<Long, String>(accommodationConditionMap);
		for (Map.Entry<Long, String> entry : accommodationConditionMap.entrySet()) {
			localizedaccommodationConditionMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("accommodationConditionMap", localizedaccommodationConditionMap);
	      
		Map<Long, String> numberOfBedsMap = referenceStore.getNumberOfBeds();
		SortedMap<Long, String> localizednumberOfBedsMap = new TreeMap<Long, String>(numberOfBedsMap);
		for (Map.Entry<Long, String> entry : numberOfBedsMap.entrySet()) {
			localizednumberOfBedsMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("numberOfBedsMap", localizednumberOfBedsMap);
	      
		Map<Long, String> vacantOrSharedMap = referenceStore.getVacantOrShared();
		SortedMap<Long, String> localizedvacantOrSharedMap = new TreeMap<Long, String>(vacantOrSharedMap);
		for (Map.Entry<Long, String> entry : vacantOrSharedMap.entrySet()) {
			localizedvacantOrSharedMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("vacantOrSharedMap", localizedvacantOrSharedMap);
	      
		Map<Long, String> canYouAccommodateMap = referenceStore.getYouCanAccommodate();
		SortedMap<Long, String> localizedcanYouAccommodateMap = new TreeMap<Long, String>(canYouAccommodateMap);
		for (Map.Entry<Long, String> entry : canYouAccommodateMap.entrySet()) {
			localizedcanYouAccommodateMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("canYouAccommodateMap", localizedcanYouAccommodateMap);
	      
		
		Map<Long, String> localeMap = referenceStore.getLocale();
		SortedMap<Long, String> localizedLocaleMap = new TreeMap<Long, String>(localeMap);
		for (Map.Entry<Long, String> entry : localeMap.entrySet()) {
			localizedLocaleMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		
		model.addAttribute("localeMap", localizedLocaleMap);
	}
	

}
