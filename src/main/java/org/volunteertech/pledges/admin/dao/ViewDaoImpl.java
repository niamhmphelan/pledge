package org.volunteertech.pledges.admin.dao;
import org.hibernate.SessionFactory;
import com.netgrains.security.Authorisation;
import com.netgrains.security.AuthorisationException;
import com.netgrains.security.InvalidUserIDException;
import org.volunteertech.pledges.admin.dao.View;
import org.volunteertech.pledges.admin.dao.ViewHistory;
import org.volunteertech.pledges.admin.dao.ViewLoadException;
import org.volunteertech.pledges.admin.dao.ViewSaveException;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


import org.volunteertech.pledges.localisation.dao.MessageResource;




/**
 * The Views that are contained in the application
 * This class has been generated by the XSLT processor from the metadata and represents the
 * implementation of the data access object for the View entity.
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
@Repository 
public class ViewDaoImpl implements ViewDao
{
    /**
     * The Hibernate SessionFactory instance
     */
    private SessionFactory sessionFactory;
    
    
    

    /**
     * Default Constructor for the View entity data manager
     */
    public ViewDaoImpl()
    {
    	//Initialise the related Object stores
        
    }
    

    /**
     * Allows Spring to set the container managed SessionFactory instance
     * @param a Spring managed SessionFactory instance
     */    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    /**
     * Loads a View object based on the viewId column
     * @param ViewId the primary key for the underlying record.
     * @ return a View object matching the parameter or null if none can be found. The
     * method also returns null if the parameter is null.
     */
    public View load(Long viewId, Long loggedInUserId) throws AuthorisationException, InvalidUserIDException
    {
        if (Authorisation.isAuthorisedView("View", loggedInUserId, viewId) == false)
        {
        	throw new AuthorisationException();
        }
    
    	View hibernateEntity = null;
    	
        if (viewId != null)
        {
			hibernateEntity = (View)this.sessionFactory.getCurrentSession().get(ViewImpl.class, viewId);

            // Load all related singular objects
            
        }
    	
        return hibernateEntity;
    }
    
    
    

    /**
     * Load all existing View objects from the database. Hibernate will generate the appropriate SQL,
     * send it to the database and populate View objects with the data.
     * @return List a List of View objects.
     */
    public List<View> listView()
    {
        @SuppressWarnings("unchecked")
        List<View> result = this.sessionFactory.getCurrentSession().createQuery("from ViewImpl as view order by view.id asc").list();

        return result;
    }

    
    /**
     * Load existing View objects from the database that have a viewName column
     * that matches the viewName parameter.
     * @return List a List of View objects that match the viewName parameter.
     */
    public List<View> listViewByViewName(String viewName)
    {
        @SuppressWarnings("unchecked")
        List<View> result = this.sessionFactory.getCurrentSession().createQuery("from ViewImpl as view where view.viewName = :viewname order by view.id asc").setParameter("viewname", viewName).list();

        return result;
    }

    
    /**
     * Load existing View objects from the database that have a createdByID column
     * that matches the createdById parameter.
     * @return List a List of View objects that match the createdById parameter.
     */
    public List<View> listViewByCreatedById(Long userId)
    {
        @SuppressWarnings("unchecked")
        List<View> result = this.sessionFactory.getCurrentSession().createQuery("from ViewImpl as view where view.createdByID = :userId order by view.id asc").setParameter("userId", userId).list();

        return result;
    }
    
    
    /**
     * Load all existing ViewHistory objects for a particular View from the database.
     * Hibernate will generate the appropriate SQL,
     * send it to the database and populate a List of ViewHistory objects with the data.
     * @return List a List of ViewHistory objects.
     */
    public List<ViewHistory> listViewHistory(Long view)
    {
        
        @SuppressWarnings("unchecked")
        List<ViewHistory> result = this.sessionFactory.getCurrentSession().createQuery("from ViewHistory as viewHistory where viewHistory.viewId = ? order by viewHistory.id asc").setLong(0, view).list();

        return result;
    }
    
    
    /**
     * Adds a MessageResource to the Set of associated MessageResource objects that represent the messageResource property
     * and that are defined by the one-to-many relationship defined as View.messageResource.
     * @return the updated <code>List</code> of the associated MessageResource objects that represent the messageResource property.
     */
    public List<MessageResource> addMessageResourceToMessageResource(MessageResource messageResource, Long viewId, Long userId) throws ViewSaveException, ViewLoadException, AuthorisationException, InvalidUserIDException{
    	messageResource.setCreatedByID(userId);
    	messageResource.setUpdatedByID(userId);
    	View view = load(viewId, userId);
    	messageResource.setView(view);
    	  view.getMessageResource().add(messageResource);
    	updateView(view, userId);
    	return getMessageResource(viewId, userId);
	}
      
    /**
     * Returns a List representing the Set of associated MessageResource objects that represent the messageResource property
     * and that are defined by the one-to-many relationship defined as View.messageResource.
     * @return the <code>List</code> of the associated MessageResource objects that represent the messageResource property.
     */
    public List<MessageResource> getMessageResource(Long viewId, Long userId) throws ViewLoadException, AuthorisationException, InvalidUserIDException{
    	@SuppressWarnings("unchecked")
		List<MessageResource> result = this.sessionFactory.getCurrentSession().createQuery("select messageResource from MessageResourceImpl as messageResource inner join messageResource.view as view where view.id = :viewId order by messageResource.id asc").setParameter("viewId", viewId).list();
    	return result;
    }
      
    
    /**
     * Updates an MessageResource in the messageResource property and returns a List representing the Set of associated MessageResource objects that represent the messageResource property
     * and that are defined by the one-to-many relationship defined as View.messageResource.
     * @return the <code>List</code> of the associated MessageResource objects that represent the messageResource property.
     */
    public List<MessageResource> updateMessageResource(Long viewId, MessageResource messageResource, Long userId) throws ViewLoadException, AuthorisationException, InvalidUserIDException{
    View view = load(viewId, userId);
    	messageResource.setView(view);
    	  messageResource.setUpdatedByID(userId);
        messageResource.setDateUpdated(new Date());
        this.sessionFactory.getCurrentSession().update(messageResource);
    	
    	return getMessageResource(viewId, userId);
    }
  
    
    /**
     * Creates a new entry in the VIEW table containing the parameters passed here.
     *
     * @param userID the userID of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @return the database generated Id of the newly created record.
     * @throws ViewSaveException on failure to save.  
     */
    public Long createAndStoreView(View view, Long userId)
    {
    	Long returnValue = Long.valueOf(0);

        view.setCreatedByID(userId);
        view.setUpdatedByID(userId);
        view.setDateCreated(new Date());
        view.setDateUpdated(new Date());
        
        this.sessionFactory.getCurrentSession().save(view);
            
        returnValue = view.getId();
		
		return returnValue;
    }


    /**
     * Updates an existing entry in the VIEW table containing the parameters passed here.
     * Only non-null parameters will be applied to the underlying table so preserving any existing entries.
     *
     * @param userId the userId of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @param the view the View object to update in the database.
     * @param userId the userId of the currently logged in user. 
     * @throws AuthorisationException if the user is not authorised to update the object
     * @throws InvalidUserIDException if the userId passed as parameter is not valid
     */
    public void updateView(View view, Long userId) throws AuthorisationException, InvalidUserIDException
    {
        if (Authorisation.isAuthorisedUpdate("View", userId, view.getId()) == false)
        {
        	throw new AuthorisationException();
        }
    	
        view.setUpdatedByID(userId);
        view.setDateUpdated(new Date());
        this.sessionFactory.getCurrentSession().update(view);
    }
    
    
    /**
     * Loads a ViewFilter object based on the participantID of the currently logged in user
     * @param userID the userID is checked for authorisation to view the record. It is also the key to load a against the createdByID
     * column of the underlying record. There should only ever be one or zero filter records per View.
     * @ return a ViewFilter object matching the parameter or a newly constructed, empty object if none can be found
     */
    public ViewFilter loadViewFilter(Long userID) throws ViewLoadException, AuthorisationException, InvalidUserIDException
    {
    	ViewFilter hibernateEntity = null;
    	
        /*if (Authorisation.isAuthorisedView("View", userID, VIEWID) == false)
        {
        	throw new AuthorisationException();
        }*/
    	

        if (userID != null)
        {
        	
        	try
        	{
				hibernateEntity = (ViewFilter)this.sessionFactory.getCurrentSession().load(ViewFilter.class, userID);
        	}
			catch (Exception ex)
			{
			     throw new ViewLoadException("ViewEntityDataManager::loadViewFilter", ex);
			}		
    		
            // Load all related singular objects
            
        }
    	
        return hibernateEntity;
    }
    
    /**
     * Creates a new entry in the VIEWFILTER table containing the parameters passed here.
     *
     * @param userId the userId of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @return the userID which is also the assigned Id for the newly created record.
     * @throws ViewSaveException on failure to save.  
     */
    public Long createAndStoreViewFilter(ViewFilter viewFilter,  Long userId)
        throws ViewSaveException
    {
    	Long returnValue = Long.valueOf(0);
        

		try
		{
            viewFilter.setId(userId);
            viewFilter.setCreatedByID(userId);
            viewFilter.setUpdatedByID(userId);
            viewFilter.setDateCreated(new Date());
            viewFilter.setDateUpdated(new Date());

            this.sessionFactory.getCurrentSession().save(viewFilter);
            
            returnValue = viewFilter.getId();
		}
		catch (Exception ex)
		{
		     throw new ViewSaveException("ViewEntityDataManager::createAndStoreViewFilter", ex);
		}		
		
		return returnValue;
    }
    
    
    /**
     * Updates an existing entry in the VIEWFILTER table and associated tables 
     * containing the parameters passed here. If the entry denoted by the userID does not exist then one is created.
     *	
     * @param userId the userId of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @throws ViewSaveException if there is an issue populating or saving the updated object
     * @throws AuthorisationException if the user is not authorised to update the object
     * @throws InvalidUserIDException if the userID passed as parameter is not valid
     */
    public Long updateViewFilter(ViewFilter viewFilter, Long userId)
        throws ViewLoadException, ViewSaveException, AuthorisationException, InvalidUserIDException
    {
    	Long returnValue = Long.valueOf(0);
    	
        //if (Authorisation.isAuthorisedUpdate("View", userID, VIEWID) == false)
        //{
        //	throw new AuthorisationException();
        //}
    	
		try
		{
			viewFilter.setId(userId);
            viewFilter.setUpdatedByID(userId);
            viewFilter.setDateUpdated(new Date());
            this.sessionFactory.getCurrentSession().update(viewFilter);
            returnValue = viewFilter.getId();
        }
		catch (Exception ex)
		{
		     throw new ViewSaveException("ViewDaoImpl::updateViewFilter, userId: " + userId, ex);
		}	
		
		return returnValue;	
        
    }
    
	
    
    /**
     * Converts a Comma Seperated Volume (delimited by ",") to a <code>List<code/> of type <code>String<code/>
     */
	private static List<String> convertCommaDelimitedStringToList(String delimitedString) {

		List<String> result = new ArrayList<String>();

		if (!StringUtils.isEmpty(delimitedString)) {
			result = Arrays.asList(StringUtils.delimitedListToStringArray(delimitedString, ","));
		}
		return result;

	}

    /**
     * Converts a List of type String to a Comma Seperated Volume (delimited by ",") 
     */
	private String convertListToCommaDelimitedString(List<String> list) {

		String result = "";
		if (list != null) {
			result = StringUtils.arrayToCommaDelimitedString(list.toArray());
		}
		return result;

	}

    /**
     * Cleans up any Collection Objects created by the Class instance
     */
    protected void finalize() throws Throwable
    {
        super.finalize();
        
    }    
    
}
    
