package org.volunteertech.pledges.users.web;
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
import org.volunteertech.pledges.users.dao.ApplicationUser;
import org.volunteertech.pledges.users.dao.ApplicationUserImpl;
import org.volunteertech.pledges.users.service.ApplicationUserService;
import org.volunteertech.pledges.users.dao.ApplicationUserLoadException;
import org.volunteertech.pledges.users.dao.ApplicationUserSaveException;
import org.volunteertech.pledges.users.validator.ApplicationUserFormValidator;
import org.volunteertech.pledges.users.view.ApplicationUserTranslationBackingBean;
import org.volunteertech.pledges.users.view.ApplicationUserTranslationBackingBeanImpl;
import org.volunteertech.pledges.main.web.BaseController;
import org.volunteertech.pledges.main.constants.Constants;
import org.volunteertech.pledges.localisation.dao.MessageResource;
import org.volunteertech.pledges.localisation.dao.MessageResourceImpl;
import org.volunteertech.pledges.localisation.service.MessageResourceService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;


import org.volunteertech.pledges.reference.ReferenceStore;

/**
 * The User Model for controlling user access
 * GUIHandler is attached to a form on JSP and contains all getters and setters for values displayed on the form.
 * GUIHandler accesses the business layer for loading/saving data. 
 * This class has been generated by the XSLT processor from the metadata and represents the
 * the GUI layer javabean for the ApplicationUser Form.
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
public class ApplicationUserController extends BaseController
{

    /**
     * userId used for development. This should be taken from the session.
     */
	private Long userId = new Long(0);
	 
	final Logger logger = LoggerFactory.getLogger(ApplicationUserController.class);
	
	@Autowired
	private ReferenceStore referenceStore;
	
	@Autowired
	private ApplicationUserService applicationUserService;

	@Autowired
	private ApplicationUserFormValidator applicationUserFormValidator;
	
    @Autowired
    private DatabaseDrivenMessageSource messageSource;
    

    @Autowired
    private MessageResourceService messageResourceService;
  
    
	
	//Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(applicationUserFormValidator);
	}
	
	
	/**
	 * Open the list page
	 */
	@RequestMapping(value = "/applicationuser/all", method = RequestMethod.GET)
	public String showAllApplicationUser(Model model, Locale locale) {

		logger.debug("showAllApplicationUser()");
			
		return "applicationuser_table";

	}
	
	/**
	 * Open the localize page
	 */
	@RequestMapping(value = "/applicationuser/localize", method = RequestMethod.GET)
	public String localizeApplicationUser(Model model, Locale locale) {

		logger.debug("localizeApplicationUser()");

		ApplicationUserTranslationBackingBean applicationUserTranslationBackingBean = new ApplicationUserTranslationBackingBeanImpl();
		applicationUserTranslationBackingBean.setCurrentMode(ApplicationUser.CurrentMode.LOCALIZE);
		model.addAttribute("applicationUserTranslationFormModel", applicationUserTranslationBackingBean);
		Long defaultLocale = new Long(Constants.REFERENCE_LOCALE__EN);
		setTranslationDropDownContents(model, locale);
		setDropDownContents(model, null, locale);		
		model.addAttribute("defaultLocale", defaultLocale);
		
		return "applicationuser_localize";

	}
	
	
	// show login form in create new user mode
	@RequestMapping(value = "/applicationuser/createuser", method = RequestMethod.GET)
	public String showCreateApplicationUserForm(Model model) {

		logger.debug("showCreateApplicationUserForm()");

		ApplicationUser applicationUser = new ApplicationUserImpl();
		
		applicationUser.setCurrentMode(ApplicationUser.CurrentMode.ADD);
		
		model.addAttribute("applicationUserFormModel", applicationUser);

		return "login";

	}
	
	// process login form and create new user
	@RequestMapping(value = "/applicationuser/createuser", method = RequestMethod.POST)
	public String registerApplicationUser(@ModelAttribute("applicationUserFormModel") @Validated  ApplicationUserImpl applicationUser,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		
 		if (!result.hasErrors()){
 			ApplicationUser registered = createUserAccount(applicationUser, result);
			if (registered == null) {
				applicationUser.setCurrentMode(ApplicationUser.CurrentMode.UPDATE);
	        	result.rejectValue("username", "userActionCreateAccountAccountAlreadyExists");
	    	}
		}
		
    	if (result.hasErrors()) {
        	return "login";
    	} 
    	
		applicationUserService.autoLoadUser(applicationUser);
		
		return "redirect:/entitylist";

	}
	
	
	
	
	private ApplicationUser createUserAccount(ApplicationUser applicationUser, BindingResult result) {
	    ApplicationUser registered = null;
	    try {
	        registered = applicationUserService.registerNewUserAccount(applicationUser);
	    } catch (Exception e) {
	        return null;
	    }
	    return registered;
	}
	
	
	

	
	/**
	 * Request mapping for the login page
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public final String displayLoginform(Model model) {
    	logger.debug("displayLoginForm()");
		ApplicationUser applicationUser = new ApplicationUserImpl();
		
		applicationUser.setCurrentMode(ApplicationUser.CurrentMode.UPDATE);
		
		model.addAttribute("applicationUserFormModel", applicationUser);
		
		return "login";
	}
	
	
	/**
	 * Request mapping for the login page after an error
	 */
	@RequestMapping(value = "/login/{error}", method = RequestMethod.GET)
	public final String displayLoginformWithError(@ModelAttribute("applicationUserFormModel") ApplicationUserImpl applicationUser,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes, @PathVariable final String error){
    	logger.debug("displayLoginFormWithError()");
    	if (error.equals("sessiontimedout")){
    		model.addAttribute("msg", "Your session timed out.");
    	}
    	else{
    		String loginIssueMessage = messageSource.getMessage("login.invalid.credentials", new String[0], locale);
    		model.addAttribute("msg", loginIssueMessage);
    	}

    	model.addAttribute("css", "danger");
		applicationUser.setCurrentMode(ApplicationUser.CurrentMode.UPDATE);
		
		model.addAttribute("applicationUserFormModel", applicationUser);    	
    	return "login";
	}		
	
	
	/**
	 * Request mapping for the login page after an error
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public final String displayLoginformSuccess(HttpServletRequest request, HttpServletResponse response, Model model) {
    	logger.debug("displayLoginFormWithSuccess()");
    	model.addAttribute("msg", "Log out successful");
    	model.addAttribute("css", "success");
    	
    	
    	
    	ApplicationUser applicationUser = new ApplicationUserImpl();
		applicationUser.setCurrentMode(ApplicationUser.CurrentMode.UPDATE);
		
		model.addAttribute("applicationUserFormModel", applicationUser);  
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
		    new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}		
	
	
	
	
	

	// save or update ApplicationUser
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/applicationuser/post", method = RequestMethod.POST)
	public String saveOrUpdateApplicationUser(Authentication authentication, @ModelAttribute("applicationUserFormModel") @Validated ApplicationUserImpl applicationUser,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateApplicationUser() : {}", applicationUser);
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		if (result.hasErrors()) {
			setDropDownContents(model, applicationUser, locale);
			String updateIssueMessage = messageSource.getMessage("applicationUserUpdateIssueMessage", new String[0], locale);
			model.addAttribute("msg", updateIssueMessage);
			model.addAttribute("css", "alert-danger");
			
			return "applicationuser";
		} else {

			// Add message to flash scope
			redirectAttributes.addFlashAttribute("css", "success");
			if(applicationUser.isNew()){
				String addedSuccessMessage = messageSource.getMessage("applicationUserAddedSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", addedSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}else{
				String updateSuccessMessage = messageSource.getMessage("applicationUserUpdateSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", updateSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}


			try{
				// TODO: Needs exception handling policy
			    	applicationUserService.storeApplicationUser(applicationUser, userId);
			}
			catch (Exception ex){
				logger.error("Exception caught !!!!!!!!!!!!!!", ex);
			}
			
	
			

			
			// POST/REDIRECT/GET
			return "redirect:/applicationuser/" + applicationUser.getId() + "/update";
		}

	}

	// show add user form
	@RequestMapping(value = "/applicationuser/add", method = RequestMethod.GET)
	public String showAddApplicationUserForm(Model model, Locale locale) {

		logger.debug("showAddApplicationUserForm()");

		ApplicationUser applicationUser = new ApplicationUserImpl();
		
		applicationUser.setCurrentMode(ApplicationUser.CurrentMode.ADD);

		model.addAttribute("applicationUserFormModel", applicationUser);

		setDropDownContents(model, applicationUser, locale);

		return "applicationuser";

	}
	
	// support access to the supporting webpage by creating a new instance and returning 
	@RequestMapping(value = "/applicationuserwebpage", method = RequestMethod.GET)
	public String createApplicationUserForWebPageView(Model model, HttpServletRequest request, Locale locale) {

		logger.debug("createApplicationUserForWebPageView()");

		ApplicationUser applicationUser = new ApplicationUserImpl();
		
		try{
			// TODO: Needs exception handling policy
	    	applicationUserService.storeApplicationUser(applicationUser, userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
		

		model.addAttribute("applicationUserFormModel", applicationUser);

		setDropDownContents(model, applicationUser, locale);

		return "applicationuserwebpage";

	}
	

	// show update form
	@RequestMapping(value = "/applicationuser/{id}/update", method = RequestMethod.GET)
	public String showUpdateApplicationUserForm(@PathVariable("id") int id, Model model, Locale locale) {

		logger.debug("showUpdateApplicationUserForm() : {}", id);
		ApplicationUser applicationUser = null;
		try{
			// TODO: Needs exception handling policy
			applicationUser = applicationUserService.load(new Long(id), userId);
			applicationUser.setCurrentMode(ApplicationUser.CurrentMode.UPDATE);
			this.applicationUserService.translateReferenceValues(applicationUser, locale);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		model.addAttribute("applicationUserFormModel", applicationUser);
		
		setDropDownContents(model, applicationUser, locale);
		
		return "applicationuser";

	}

	// delete applicationUser
	@RequestMapping(value = "/applicationuser/{id}/delete", method = RequestMethod.POST)
	public String deleteApplicationUser(@PathVariable("id") int id, 
		final RedirectAttributes redirectAttributes) {

		logger.debug("deleteUser() : {}", id);

		//applicationUserService.delete(id);
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "User is deleted!");
		
		return "redirect:/applicationuser/all";

	}

	// show user
	@RequestMapping(value = "/applicationuser/{id}", method = RequestMethod.GET)
	public String showApplicationUser(@PathVariable("id") int id, Model model, Locale locale) {

		logger.debug("showApplicationUser() id: {}", id);
		ApplicationUser applicationUser = null;
		try{
			// TODO: Needs exception handling policy
			applicationUser = applicationUserService.load(new Long(id), userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		if (applicationUser == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "User not found");
		}
		model.addAttribute("applicationUser", applicationUser);
		
		setDropDownContents(model, applicationUser, locale);

		return "showapplicationuser";

	}
	
	
	// save or update ApplicationUserTranslation
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/applicationuser/translate", method = RequestMethod.POST)
	public String saveOrUpdateApplicationUserTranslation(Authentication authentication, @ModelAttribute("applicationUserTranslationFormModel") ApplicationUserTranslationBackingBeanImpl applicationUserTranslationBackingBean,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateApplicationUserTranslation() : {}", applicationUserTranslationBackingBean);
		Long translationLocaleReferenceId = applicationUserTranslationBackingBean.getNewLocale();
		String translationLocale = messageSource.getMessage(referenceStore.getRefDesc(translationLocaleReferenceId), new String[0], new Locale("en"));
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		try{
			
			// TODO: Needs exception handling policy
			List<MessageResource> messageResourceList  = new ArrayList<MessageResource>();
			
			MessageResource messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.APPLICATIONUSER_HEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getViewTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.APPLICATIONUSER_TITLE_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getFormTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.APPLICATIONUSER_SUBHEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getFormSubHeader());
			messageResourceList.add(messageResource);
			
	messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.USERNAME_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getUsernameLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.USERNAME_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getUsernamePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.USERNAME_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getUsernameHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.PASSWORD_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getPasswordLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.PASSWORD_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getPasswordPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.PASSWORD_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getPasswordHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.ENABLED_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getEnabledLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.ENABLED_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getEnabledPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.ENABLED_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getEnabledHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.USERROLES_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getUserRolesLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.USERROLES_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getUserRolesPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.USERROLES_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getUserRolesHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.SAVEBUTTON_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getSaveButtonLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.SAVEBUTTON_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getSaveButtonPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(ApplicationUserTranslationBackingBeanImpl.SAVEBUTTON_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, applicationUserTranslationBackingBean.getSaveButtonHelpText());
  			messageResourceList.add(messageResource);
  		

			

			this.messageSource.updateTexts(messageResourceList, userId);			
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
			
		// POST/REDIRECT/GET
		return "redirect:/applicationuser/add?language=" + translationLocale;

	}
	
	private void setDropDownContents(Model model, ApplicationUser applicationUser, Locale locale) {
		
		Map<Long, String> userRolesMap = referenceStore.getUserRole();
		SortedMap<Long, String> localizeduserRolesMap = new TreeMap<Long, String>(userRolesMap);
		for (Map.Entry<Long, String> entry : userRolesMap.entrySet()) {
			localizeduserRolesMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("userRolesMap", localizeduserRolesMap);
	      
		
		Map<Long, String> localeMap = referenceStore.getLocale();
		SortedMap<Long, String> localizedLocaleMap = new TreeMap<Long, String>(localeMap);
		for (Map.Entry<Long, String> entry : localeMap.entrySet()) {
			localizedLocaleMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		
		model.addAttribute("localeMap", localizedLocaleMap);
	}
	

	

}
