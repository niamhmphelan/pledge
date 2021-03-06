package org.volunteertech.pledges.users.dao;
import org.hibernate.SessionFactory;
import com.netgrains.security.AuthorisationException;
import com.netgrains.security.InvalidUserIDException;
import org.volunteertech.pledges.users.dao.ApplicationUser;
import org.volunteertech.pledges.users.dao.ApplicationUserHistory;
import org.volunteertech.pledges.users.dao.ApplicationUserLoadException;
import org.volunteertech.pledges.users.dao.ApplicationUserSaveException;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.math.BigDecimal;




/**
 * The User Model for controlling user access
 * This Interface has been generated by the XSLT processor from the meta data and represents the
 * interface of the data access object(DAO) for the ApplicationUser entity.
 * <P> 
 * It is essential that methods added to this class are given JavaDoc comments to allow
 * documentation to be generated. For a description of JavaDoc refer to The JavaDoc documentation.
 * A link is provided below.
 * <P>
 * @author Michael O'Connor
 * @version $Revision$
 * Date: * $Date$
 * @see <a href="http://java.sun.com/j2se/javadoc/writingdoccomments/index.html">>JavaDoc Documentation</a> 
 * Change Log
 * ----------
 * $Log$
 *
 */
public interface ApplicationUserDao
{

    /**
     * Allows Spring to set the container managed SessionFactory instance
     * @param a Spring managed SessionFactory instance
     */    
    public void setSessionFactory(SessionFactory sessionFactory);


    /**
     * Loads a ApplicationUser object based on the applicationUserId column
     * @param applicationUserId the primary key for the underlying record.
     * @ return a ApplicationUser object matching the parameter or null if none can be found. The
     * method also returns null if the parameter is null.
     */
    public ApplicationUser load(Long applicationUserId, Long loggedInUserId) throws AuthorisationException, InvalidUserIDException;
    

    /**
     * Load all existing ApplicationUser objects from the database. Hibernate will generate the appropriate SQL,
     * send it to the database and populate a List of ApplicationUser objects with the data.
     * @return List a List of ApplicationUser objects.
     */
    public List<ApplicationUser> listApplicationUser();
    
    
    /**
     * Load existing ApplicationUser objects from the database that have a username column
     * that matches the username parameter.
     * @return List a List of ApplicationUser objects that match the username parameter.
     */
    public List<ApplicationUser> listApplicationUserByUsername(String username);

    /**
     * Load existing ApplicationUser objects from the database that have a password column
     * that matches the password parameter.
     * @return List a List of ApplicationUser objects that match the password parameter.
     */
    public List<ApplicationUser> listApplicationUserByPassword(String password);

    /**
     * Load existing ApplicationUser objects from the database that have a enabled column
     * that matches the enabled parameter.
     * @return List a List of ApplicationUser objects that match the enabled parameter.
     */
    public List<ApplicationUser> listApplicationUserByEnabled(Boolean enabled);

    /**
     * Load existing ApplicationUser objects from the database that have a userRoles column
     * that matches the userRoles parameter.
     * @return List a List of ApplicationUser objects that match the userRoles parameter.
     */
    public List<ApplicationUser> listApplicationUserByUserRoles(Long userRoles);

    
    /**
     * Load existing ApplicationUser objects from the database that have a createdByID column
     * that matches the userId parameter.
     * @return List a List of ApplicationUser objects that match the userId parameter.
     */
    public List<ApplicationUser> listApplicationUserByCreatedById(Long userId);
    
    
    /**
     * Load all existing ApplicationUserHistory objects for a particular ApplicationUser from the database.
     * Hibernate will generate the appropriate SQL,
     * send it to the database and populate ApplicationUserHistory objects with the data.
     * @return List a List of ApplicationUserHistory objects.
     */
    public List<ApplicationUserHistory> listApplicationUserHistory(Long applicationUser);
    
    
    
    
    /**
     * Creates a new entry in the APPLICATIONUSER table containing the parameters passed here.
     *
     * @param userID the userId of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @return the database generated Id of the newly created record.
     */
    public Long createAndStoreApplicationUser(ApplicationUser applicationUser, Long userId);


    /**
     * Updates an existing entry in the APPLICATIONUSER table containing the parameters passed here.
     * Only non-null parameters will be applied to the underlying table so preserving any existing entries.
     * @param the applicationUser the ApplicationUser object to update in the database.
     * @param userId the userId of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     */
    public void updateApplicationUser(ApplicationUser applicationUser, Long userId) throws AuthorisationException, InvalidUserIDException;
    
    
    /**
     * Loads a ApplicationUserFilter object based on the participantID of the currently logged in user
     * @param userID the userID is checked for authorisation to view the record. It is also the key to load a against the createdByID
     * column of the underlying record. There should only ever be one or zero filter records per ApplicationUser.
     * @ return a ApplicationUserFilter object matching the parameter or a newly constructed, empty object if none can be found
     */
    public ApplicationUserFilter loadApplicationUserFilter(Long userID) throws ApplicationUserLoadException, AuthorisationException, InvalidUserIDException;


    
    /**
     * Creates a new entry in the APPLICATIONUSERFILTER table containing the parameters passed here.
     *
     * @param userID the userID of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @return the userId which is also the assigned Id for the newly created record.
     * @throws ApplicationUserSaveException on failure to save.  
     */
    public Long createAndStoreApplicationUserFilter(ApplicationUserFilter applicationUserFilter, Long userId)
        throws ApplicationUserSaveException;
    
    
    /**
     * Updates an existing entry in the APPLICATIONUSERFILTER table and associated tables 
     * containing the parameters passed here. If the entry denoted by the userID does not exist then one is created.
     *	
     * @param userId the userId of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @throws ApplicationUserSaveException if there is an issue populating or saving the updated object
     * @throws AuthorisationException if the user is not authorised to update the object
     * @throws InvalidUserIDException if the userID passed as parameter is not valid
     */
    public Long updateApplicationUserFilter(ApplicationUserFilter applicationUserFilter, Long userId)
        throws ApplicationUserLoadException, ApplicationUserSaveException, AuthorisationException, InvalidUserIDException;
    
    /**
     * @param details
     * @return
     */
    public ApplicationUser getApplicationUserByDetails(ApplicationUserDetails details);
    
	
}
    
