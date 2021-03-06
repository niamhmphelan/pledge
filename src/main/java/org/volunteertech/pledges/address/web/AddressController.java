package org.volunteertech.pledges.address.web;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

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
import org.volunteertech.pledges.address.dao.Address;
import org.volunteertech.pledges.address.dao.AddressImpl;
import org.volunteertech.pledges.address.service.AddressService;
import org.volunteertech.pledges.address.validator.AddressFormValidator;
import org.volunteertech.pledges.address.view.AddressTranslationBackingBean;
import org.volunteertech.pledges.address.view.AddressTranslationBackingBeanImpl;
import org.volunteertech.pledges.main.web.BaseController;
import org.volunteertech.pledges.main.constants.Constants;
import org.volunteertech.pledges.localisation.dao.MessageResource;
import org.volunteertech.pledges.localisation.service.MessageResourceService;


import org.volunteertech.pledges.reference.ReferenceStore;

/**
 * The Address Details
 * GUIHandler is attached to a form on JSP and contains all getters and setters for values displayed on the form.
 * GUIHandler accesses the business layer for loading/saving data. 
 * This class has been generated by the XSLT processor from the metadata and represents the
 * the GUI layer javabean for the Address Form.
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
public class AddressController extends BaseController
{

    /**
     * userId used for development. This should be taken from the session.
     */
	private Long userId = Long.valueOf(0);
	 
	final Logger logger = LoggerFactory.getLogger(AddressController.class);
	
	@Autowired
	private ReferenceStore referenceStore;
	
	@Autowired
	private AddressService addressService;

	@Autowired
	private AddressFormValidator addressFormValidator;
	
    @Autowired
    private DatabaseDrivenMessageSource messageSource;
    


    @Autowired
    private MessageResourceService messageResourceService;
  
    
	
	//Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(addressFormValidator);
	}
	
	
	/**
	 * Open the list page
	 */
	@RequestMapping(value = "/address/all", method = RequestMethod.GET)
	public String showAllAddress(Model model, Locale locale) {

		logger.debug("showAllAddress()");
			
		return "addresses/address_table";

	}
	
	/**
	 * Open the localize page
	 */
	@RequestMapping(value = "/address/localize", method = RequestMethod.GET)
	public String localizeAddress(Model model, Locale locale) {

		logger.debug("localizeAddress()");

		AddressTranslationBackingBean addressTranslationBackingBean = new AddressTranslationBackingBeanImpl();
		addressTranslationBackingBean.setCurrentMode(Address.CurrentMode.LOCALIZE);
		model.addAttribute("addressTranslationFormModel", addressTranslationBackingBean);
		Long defaultLocale = Long.valueOf(Constants.REFERENCE_LOCALE__EN);
		setTranslationDropDownContents(model, locale);
		setDropDownContents(model, null, locale);		
		model.addAttribute("defaultLocale", defaultLocale);
		
		return "addresses/address_localize";

	}
	
	
	

	// save or update Address
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/address/post", method = RequestMethod.POST)
	public String saveOrUpdateAddress(Authentication authentication, @ModelAttribute("addressFormModel") @Validated AddressImpl address,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateAddress() : {}", address);
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		if (result.hasErrors()) {
			setDropDownContents(model, address, locale);
			String updateIssueMessage = messageSource.getMessage("addressUpdateIssueMessage", new String[0], locale);
			model.addAttribute("msg", updateIssueMessage);
			model.addAttribute("css", "alert-danger");
			
			return "addresses/address";
		} else {

			// Add message to flash scope
			redirectAttributes.addFlashAttribute("css", "success");
			if(address.isNew()){
				String addedSuccessMessage = messageSource.getMessage("addressAddedSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", addedSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}else{
				String updateSuccessMessage = messageSource.getMessage("addressUpdateSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", updateSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}


			try{
				// TODO: Needs exception handling policy
			    	addressService.storeAddress(address, userId);
			}
			catch (Exception ex){
				logger.error("Exception caught !!!!!!!!!!!!!!", ex);
			}
			
	
			

			
			// POST/REDIRECT/GET
			return "redirect:/address/" + address.getId() + "/update";
		}

	}

	// show add user form
	@RequestMapping(value = "/address/add", method = RequestMethod.GET)
	public String showAddAddressForm(Model model, Locale locale) {

		logger.debug("showAddAddressForm()");

		Address address = new AddressImpl();
		
		address.setCurrentMode(Address.CurrentMode.ADD);

		model.addAttribute("addressFormModel", address);

		setDropDownContents(model, address, locale);

		return "addresses/address";

	}
	
	// support access to the supporting webpage by creating a new instance and returning 
	@RequestMapping(value = "/addresswebpage", method = RequestMethod.GET)
	public String createAddressForWebPageView(Model model, HttpServletRequest request, Locale locale) {

		logger.debug("createAddressForWebPageView()");

		Address address = new AddressImpl();
		
		try{
			// TODO: Needs exception handling policy
	    	addressService.storeAddress(address, userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
		

		model.addAttribute("addressFormModel", address);

		setDropDownContents(model, address, locale);

		return "addresses/addresswebpage";

	}
	

	// show update form
	@RequestMapping(value = "/address/{id}/update", method = RequestMethod.GET)
	public String showUpdateAddressForm(@PathVariable("id") int id, Model model, Locale locale) {

		logger.debug("showUpdateAddressForm() : {}", id);
		Address address = null;
		try{
			// TODO: Needs exception handling policy
			address = addressService.load(Long.valueOf(id), userId);
			address.setCurrentMode(Address.CurrentMode.UPDATE);
			this.addressService.translateReferenceValues(address, locale);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		model.addAttribute("addressFormModel", address);
		
		setDropDownContents(model, address, locale);
		
		return "addresses/address";

	}

	// delete address
	@RequestMapping(value = "/address/{id}/delete", method = RequestMethod.POST)
	public String deleteAddress(@PathVariable("id") int id, 
		final RedirectAttributes redirectAttributes) {

		logger.debug("deleteUser() : {}", id);

		//addressService.delete(id);
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "User is deleted!");
		
		return "redirect:/address/all";

	}

	// show user
	@RequestMapping(value = "/address/{id}", method = RequestMethod.GET)
	public String showAddress(@PathVariable("id") int id, Model model, Locale locale) {

		logger.debug("showAddress() id: {}", id);
		Address address = null;
		try{
			// TODO: Needs exception handling policy
			address = addressService.load(Long.valueOf(id), userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		if (address == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "User not found");
		}
		model.addAttribute("address", address);
		
		setDropDownContents(model, address, locale);

		return "addresses/showaddress";

	}
	
	
	// save or update AddressTranslation
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/address/translate", method = RequestMethod.POST)
	public String saveOrUpdateAddressTranslation(Authentication authentication, @ModelAttribute("addressTranslationFormModel") AddressTranslationBackingBeanImpl addressTranslationBackingBean,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateAddressTranslation() : {}", addressTranslationBackingBean);
		Long translationLocaleReferenceId = addressTranslationBackingBean.getNewLocale();
		String translationLocale = messageSource.getMessage(referenceStore.getRefDesc(translationLocaleReferenceId), new String[0], new Locale("en"));
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		try{
			
			// TODO: Needs exception handling policy
			List<MessageResource> messageResourceList  = new ArrayList<MessageResource>();
			
			MessageResource messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.ADDRESS_HEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getViewTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.ADDRESS_TITLE_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getFormTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.ADDRESS_SUBHEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getFormSubHeader());
			messageResourceList.add(messageResource);
			
	messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.ADDRESSONE_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getAddressOneLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.ADDRESSONE_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getAddressOnePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.ADDRESSONE_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getAddressOneHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.ADDRESSTWO_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getAddressTwoLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.ADDRESSTWO_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getAddressTwoPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.ADDRESSTWO_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getAddressTwoHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.CITY_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getCityLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.CITY_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getCityPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.CITY_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getCityHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.STATEPROVINCEREGION_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getStateProvinceRegionLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.STATEPROVINCEREGION_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getStateProvinceRegionPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.STATEPROVINCEREGION_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getStateProvinceRegionHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.POSTCODE_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getPostCodeLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.POSTCODE_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getPostCodePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.POSTCODE_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getPostCodeHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.COUNTRY_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getCountryLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.COUNTRY_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getCountryPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.COUNTRY_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getCountryHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.SAVEBUTTON_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getSaveButtonLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.SAVEBUTTON_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getSaveButtonPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(AddressTranslationBackingBeanImpl.SAVEBUTTON_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, addressTranslationBackingBean.getSaveButtonHelpText());
  			messageResourceList.add(messageResource);
  		

			

			this.messageSource.updateTexts(messageResourceList, userId);			
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
			
		// POST/REDIRECT/GET
		return "redirect:/address/add?language=" + translationLocale;

	}

	private void setDropDownContents(Model model, Address address, Locale locale) {
		
		Map<Long, String> countryMap = referenceStore.getEuropeCountry();
		SortedMap<Long, String> localizedcountryMap = new TreeMap<Long, String>(countryMap);
		for (Map.Entry<Long, String> entry : countryMap.entrySet()) {
			localizedcountryMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("countryMap", localizedcountryMap);
	      
		
		Map<Long, String> localeMap = referenceStore.getLocale();
		SortedMap<Long, String> localizedLocaleMap = new TreeMap<Long, String>(localeMap);
		for (Map.Entry<Long, String> entry : localeMap.entrySet()) {
			localizedLocaleMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		
		model.addAttribute("localeMap", localizedLocaleMap);
	}
	


}
