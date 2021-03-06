package org.volunteertech.pledges.users.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.SortedMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

import org.volunteertech.pledges.users.dao.ApplicationUser;
import org.volunteertech.pledges.users.dao.ApplicationUserImpl;
import org.volunteertech.pledges.users.service.ApplicationUserService;
import org.volunteertech.pledges.users.dao.ApplicationUserLoadException;
import org.volunteertech.pledges.users.dao.ApplicationUserSaveException;
import org.volunteertech.pledges.users.validator.ApplicationUserFormValidator;
import org.volunteertech.pledges.main.web.BaseController;
import org.volunteertech.pledges.users.security.SecurityUser;

import org.volunteertech.pledges.reference.ReferenceStore;





/**
 * The User Model for controlling user access
 * The Spring ApplicationUserRestController to handle restful web service requests.
 * This class has been generated by the XSLT processor from the metadata and represents the
 * the Spring ApplicationUserRestController to handle restful web service requests.
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
@RestController
public class ApplicationUserRestController extends BaseController
{
	final Logger logger = LoggerFactory.getLogger(ApplicationUserRestController.class);
	
	@Autowired
	private ReferenceStore referenceStore;
	
	@Autowired
	private ApplicationUserService applicationUserService;
	
	


	/**
	 * Returns a JSON representation of all ApplicationUser records
	 * @return the JSON representation of all ApplicationUser.
	 */
	@RequestMapping(value = "/restful/applicationuser/list", method = RequestMethod.GET)
	@ResponseBody
    public List<ApplicationUser> showAllApplicationUser(Authentication authentication, Locale locale) {
		logger.info("Inside restful getApplicationUser method...");
		List <ApplicationUser> applicationUserList = null;
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();		
		
		try{
			// TODO: Needs exception handling policy
	    	applicationUserList = applicationUserService.listApplicationUser();
	    	applicationUserList = applicationUserService.translateReferenceValues(applicationUserList, locale);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}		
		
		
    	return applicationUserList;
    }


	
	/**
	 * Returns a JSON representation of the ApplicationUser record that matches the id parameter
	 * @param id the primary key by which to search
	 * @return the ApplicationUser with the relevant primary key.
	 */
	@RequestMapping(value = "/restful/applicationuser", method = RequestMethod.GET)
	@ResponseBody
    public ApplicationUser getApplicationUser(Authentication authentication, @RequestParam(value="id") String id) {
		logger.info("Inside restful getApplicationUser method...");
		ApplicationUser applicationUser = null;
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();		
		
		try{
			// TODO: Needs exception handling policy
	    	applicationUser = applicationUserService.load(Long.valueOf(id), userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}		
		
		
    	return applicationUser;
    }
    
    @RequestMapping(value = "/restful/applicationuser/update", method = RequestMethod.POST)
    @ResponseBody
    public List<ApplicationUser> updateApplicationUser(Authentication authentication, @RequestBody ApplicationUser applicationUser) {
        logger.debug("In the updateApplicationUser controller and got Id: " + applicationUser.getId());

		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
        
		try{
			// TODO needs security update
			applicationUserService.storeApplicationUser(applicationUser, userId);
		}
		catch (Exception ex){
		// TODO needs custom exception handling
		logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
    	List<ApplicationUser> applicationUserList = applicationUserService.listApplicationUser();
        
        return applicationUserList;
    }
    
    
    @RequestMapping(value = "/restful/applicationuser/new", method = RequestMethod.POST)
    @ResponseBody
    public List<ApplicationUser> addApplicationUser(Authentication authentication, @RequestBody ApplicationUser applicationUser) {
        logger.debug("In the addApplicationUser controller and got Id: " + applicationUser.getId());
        SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();

		try{
			// TODO needs security update
			applicationUserService.storeApplicationUser(applicationUser, userId);
		}
		catch (Exception ex){
		// TODO needs custom exception handling
		logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
    	List<ApplicationUser> applicationUserList = applicationUserService.listApplicationUser();
        
        return applicationUserList;
    }
    
    
    
}
