package org.volunteertech.pledges.servicepledge.business;
import com.netgrains.security.Authorisation;
import com.netgrains.security.AuthorisationException;
import com.netgrains.security.InvalidUserIDException;
import org.volunteertech.pledges.servicepledge.dao.ServicePledge;
import org.volunteertech.pledges.servicepledge.dao.ServicePledgeDao;
import org.volunteertech.pledges.servicepledge.dao.ServicePledgeFilter;
import org.volunteertech.pledges.servicepledge.dao.ServicePledgeHistory;
import org.volunteertech.pledges.servicepledge.dao.ServicePledgeLoadException;
import org.volunteertech.pledges.servicepledge.dao.ServicePledgeSaveException;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.math.BigDecimal;
import java.util.ArrayList;




/**
 * The pledge services screen is used to add/edit a new/existing pledge of services
 * This Class has been generated by the XSLT processor from the meta data and represents the
 * implementation of the Business Object(BO) for the ServicePledge entity. A Business Object
 * is provided in order to provide a seperation from the Data Access implementation. 
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
public class ServicePledgeBoImpl implements ServicePledgeBo
{
	/**
	 * The Data Access Object
	 */
	ServicePledgeDao  servicePledgeDao;
	    
    
    

    /**
     * Default Constructor for the ServicePledge Business Object.
     */
    public ServicePledgeBoImpl()
    {
    	//Initialise the related Object stores
        
    }
    
    /**
     * Sets the required Data Access Object (DAO)
     * @param servicePledgeDao the servicePledgeDao used to access the servicePledge entity.
     */
    public void setServicePledgeDao(ServicePledgeDao servicePledgeDao){
    	this.servicePledgeDao = servicePledgeDao;
    }

    /**
     * Returns the required Data Access Object (DAO)
     * @return the ServicePledgeDao used to access the servicePledge entity.
     */
    public ServicePledgeDao getServicePledgeDao(){
    	return this.servicePledgeDao;
    }
    

    /**
     * Loads a ServicePledge object based on the SERVICEPLEDGEID column
     * @param SERVICEPLEDGEId the primary key for the underlying record.
     * @param userId the userId is checked for authorisation to view the record
     * @ return a ServicePledge object matching the parameter or null if none can be found. The
     * method also returns null if the parameter is null.
     */
    public ServicePledge load(Long servicePledgeId, Long loggedInUserId) throws ServicePledgeLoadException, AuthorisationException, InvalidUserIDException
    {
    	ServicePledge servicePledge = null;
    	
        if (Authorisation.isAuthorisedView("ServicePledge", loggedInUserId, servicePledgeId) == false)
        {
        	throw new AuthorisationException();
        }
    	

        if (servicePledgeId != null)
        {
        	try
        	{
				servicePledge = (ServicePledge)this.getServicePledgeDao().load(servicePledgeId, loggedInUserId);
        	}
			catch (Exception ex)
			{
			     throw new ServicePledgeLoadException("ServicePledgeDaoImpl::load", ex);
			}		
    		
            // Load all related singular objects
            
        }
    	
        return servicePledge;
    }
    
    
    


    /**
     * Load all existing ServicePledge objects. 
     * @return List a List of ServicePledge objects.
     */
    public List<ServicePledge> listServicePledge()
    {
        List<ServicePledge> result = this.getServicePledgeDao().listServicePledge();

        return result;
    }
    
    
    /**
     * Returns a <code>List</code> of ServicePledge objects that have a matching pledgeServiceLevelOne
     * @return List a List of ServicePledge objects that match the pledgeServiceLevelOne property given as parameter.
     * @param pledgeServiceLevelOne the pledgeServiceLevelOne on which to match the required records.
     */
    public List<ServicePledge> listServicePledgeByPledgeServiceLevelOne(Long pledgeServiceLevelOne)
    {
        List<ServicePledge> result = this.getServicePledgeDao().listServicePledgeByPledgeServiceLevelOne(pledgeServiceLevelOne);

        return result;
    }

    /**
     * Returns a <code>List</code> of ServicePledge objects that have a matching pledgeServiceLevelTwo
     * @return List a List of ServicePledge objects that match the pledgeServiceLevelTwo property given as parameter.
     * @param pledgeServiceLevelTwo the pledgeServiceLevelTwo on which to match the required records.
     */
    public List<ServicePledge> listServicePledgeByPledgeServiceLevelTwo(Long pledgeServiceLevelTwo)
    {
        List<ServicePledge> result = this.getServicePledgeDao().listServicePledgeByPledgeServiceLevelTwo(pledgeServiceLevelTwo);

        return result;
    }

    /**
     * Returns a <code>List</code> of ServicePledge objects that have a matching pledgeServiceLevelThree
     * @return List a List of ServicePledge objects that match the pledgeServiceLevelThree property given as parameter.
     * @param pledgeServiceLevelThree the pledgeServiceLevelThree on which to match the required records.
     */
    public List<ServicePledge> listServicePledgeByPledgeServiceLevelThree(Long pledgeServiceLevelThree)
    {
        List<ServicePledge> result = this.getServicePledgeDao().listServicePledgeByPledgeServiceLevelThree(pledgeServiceLevelThree);

        return result;
    }

    /**
     * Returns a <code>List</code> of ServicePledge objects that have a matching additionalInformation
     * @return List a List of ServicePledge objects that match the additionalInformation property given as parameter.
     * @param additionalInformation the additionalInformation on which to match the required records.
     */
    public List<ServicePledge> listServicePledgeByAdditionalInformation(String additionalInformation)
    {
        List<ServicePledge> result = this.getServicePledgeDao().listServicePledgeByAdditionalInformation(additionalInformation);

        return result;
    }

    /**
     * Returns a <code>List</code> of ServicePledge objects that have a matching pledgeServiceQualification
     * @return List a List of ServicePledge objects that match the pledgeServiceQualification property given as parameter.
     * @param pledgeServiceQualification the pledgeServiceQualification on which to match the required records.
     */
    public List<ServicePledge> listServicePledgeByPledgeServiceQualification(String pledgeServiceQualification)
    {
        List<ServicePledge> result = this.getServicePledgeDao().listServicePledgeByPledgeServiceQualification(pledgeServiceQualification);

        return result;
    }

    /**
     * Returns a <code>List</code> of ServicePledge objects that have a matching pledgeServiceDateAvailable
     * @return List a List of ServicePledge objects that match the pledgeServiceDateAvailable property given as parameter.
     * @param pledgeServiceDateAvailable the pledgeServiceDateAvailable on which to match the required records.
     */
    public List<ServicePledge> listServicePledgeByPledgeServiceDateAvailable(Date pledgeServiceDateAvailable)
    {
        List<ServicePledge> result = this.getServicePledgeDao().listServicePledgeByPledgeServiceDateAvailable(pledgeServiceDateAvailable);

        return result;
    }

    /**
     * Returns a <code>List</code> of ServicePledge objects that have a matching pledgeServiceHoursPerWeek
     * @return List a List of ServicePledge objects that match the pledgeServiceHoursPerWeek property given as parameter.
     * @param pledgeServiceHoursPerWeek the pledgeServiceHoursPerWeek on which to match the required records.
     */
    public List<ServicePledge> listServicePledgeByPledgeServiceHoursPerWeek(Long pledgeServiceHoursPerWeek)
    {
        List<ServicePledge> result = this.getServicePledgeDao().listServicePledgeByPledgeServiceHoursPerWeek(pledgeServiceHoursPerWeek);

        return result;
    }

    
	/**
     * Load existing ServicePledge objects from the database that have a createdByID column
     * that matches the userId parameter.
     * @return List a List of ServicePledge objects that match the userId parameter.
     */
    public List<ServicePledge> listServicePledgeByCreatedById(Long userId)
    {
        List<ServicePledge> result = this.getServicePledgeDao().listServicePledgeByCreatedById(userId);

        return result;
    }
    
    
    /**
     * Load all existing ServicePledgeHistory objects for a particular ServicePledge.
     * The <code>List</code> of ServicePledgeHistory objects contains all of the changes that have been made to the
     * ServicePledge object identified by the servicePledge parameter'
     * @return List a List of ServicePledgeHistory objects.
     */
    public List<ServicePledgeHistory> listServicePledgeHistory(Long servicePledge)
    {
        List<ServicePledgeHistory> result = this.getServicePledgeDao().listServicePledgeHistory(servicePledge);

        return result;
    }
    
    
    /**
     * Load a <code>List</code> of ServicePledge objects from the database that match the
     * servicePledgeId parameter, the RegisterOfPledges and the ServicePledge being related by an association.
     * @return a <code>List</code> of ServicePledge objects that match the servicePledgeId parameter.
     */
    public List<ServicePledge> listServicePledgeByRegisterOfPledgesId(Long registerOfPledgesId)
    {
        List<ServicePledge> result = this.getServicePledgeDao().listServicePledgeByRegisterOfPledgesId(registerOfPledgesId);
        
        return result;
    }
  
    
    
    /**
     * Creates a new entry in the SERVICEPLEDGE table containing the parameters passed here.
     *
     * @param userID the userID of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @return the database generated Id of the newly created record.
     * @throws ServicePledgeSaveException on failure to save.  
     */
    public Long createAndStoreServicePledge(ServicePledge servicePledge, Long userID)
        throws ServicePledgeSaveException
    {
    	Long returnValue = Long.valueOf(0);
        
		try
		{
            returnValue = this.getServicePledgeDao().createAndStoreServicePledge(servicePledge, userID);
		}
		catch (Exception ex)
		{
		     throw new ServicePledgeSaveException("ServicePledgeEntityDataManager::createAndStoreServicePledge", ex);
		}		
		
		return returnValue;
    }


    /**
     * Updates an existing entry in the SERVICEPLEDGE table containing the parameters passed here.
     * Only non-null parameters will be applied to the underlying table so preserving any existing entries.
     *
     * @param userID the userID of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @throws ServicePledgeLoadException if the existing record cannot be loaded
     * @throws ServicePledgeSaveException if there is an issue populating or saving the updated object
     * @throws AuthorisationException if the user is not authorised to update the object
     * @throws InvalidUserIDException if the userID passed as parameter is not valid
     */
    public void updateServicePledge(ServicePledge servicePledge, Long userID)
        throws ServicePledgeSaveException, ServicePledgeLoadException, AuthorisationException, InvalidUserIDException
    {
    	
        if (Authorisation.isAuthorisedUpdate("ServicePledge", userID, servicePledge.getId()) == false)
        {
        	throw new AuthorisationException();
        }
    	
		try
		{
            this.getServicePledgeDao().updateServicePledge(servicePledge, userID);
        }
		catch (Exception ex)
		{
		     throw new ServicePledgeSaveException("ServicePledgeEntityDataManager::updateServicePledge, ServicePledgeID: " + servicePledge.getId(), ex);
		}		
        
    }
    
    
    /**
     * Loads a ServicePledgeFilter object based on the participantID of the currently logged in user
     * @param userId the userId is checked for authorisation to view the record. It is also the key to load a against the createdByID
     * column of the underlying record. There should only ever be one or zero filter records per ServicePledge.
     * @ return a ServicePledgeFilter object matching the parameter or a newly constructed, empty object if none can be found
     */
    public ServicePledgeFilter loadServicePledgeFilter(Long userId) throws ServicePledgeLoadException, AuthorisationException, InvalidUserIDException
    {
    	ServicePledgeFilter servicePledgeFilter = null;
    	
        /*if (Authorisation.isAuthorisedView("ServicePledge", userId, SERVICEPLEDGEID) == false)
        {
        	throw new AuthorisationException();
        }*/
    	

        if (userId != null)
        {
        	
        	try
        	{
				servicePledgeFilter = (ServicePledgeFilter)this.getServicePledgeDao().loadServicePledgeFilter(userId);
        	}
			catch (Exception ex)
			{
			     throw new ServicePledgeLoadException("ServicePledgeBoImpl::loadServicePledgeFilter", ex);
			}		
    		
            // Load all related singular objects
            
        }
    	
        return servicePledgeFilter;
    }
    
    /**
     * Creates a new entry in the SERVICEPLEDGEFILTER table containing the parameters passed here.
     *
     * @param userId the userId of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @return the userId which is also the assigned Id for the newly created record.
     * @throws ServicePledgeSaveException on failure to save.  
     */
    public Long createAndStoreServicePledgeFilter(ServicePledgeFilter servicePledgeFilter,  Long userId)
        throws ServicePledgeSaveException
    {
    	Long returnValue = Long.valueOf(0);
        
		try
		{
            servicePledgeFilter.setId(userId);
			servicePledgeFilter.setCreatedByID(userId);
            servicePledgeFilter.setUpdatedByID(userId);
            servicePledgeFilter.setDateCreated(new Date());
            servicePledgeFilter.setDateUpdated(new Date());

            returnValue = this.getServicePledgeDao().createAndStoreServicePledgeFilter(servicePledgeFilter, userId);
		}
		catch (Exception ex)
		{
		     throw new ServicePledgeSaveException("ServicePledgeBoImpl::createAndStoreServicePledgeFilter", ex);
		}		
		
		return returnValue;
    }
    
    
    /**
     * Updates an existing entry in the SERVICEPLEDGEFILTER table and associated tables 
     * containing the parameters passed here. If the entry denoted by the userID does not exist then one is created.
     *	
     * @param userId the userId of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @throws ServicePledgeSaveException if there is an issue populating or saving the updated object
     * @throws AuthorisationException if the user is not authorised to update the object
     * @throws InvalidUserIDException if the userID passed as parameter is not valid
     */
    public Long updateServicePledgeFilter(ServicePledgeFilter servicePledgeFilter, Long userId)
        throws ServicePledgeLoadException, ServicePledgeSaveException, AuthorisationException, InvalidUserIDException
    {
    	Long returnValue = Long.valueOf(0);
    	
        //if (Authorisation.isAuthorisedUpdate("ServicePledge", userId, SERVICEPLEDGEID) == false)
        //{
        //	throw new AuthorisationException();
        //}
    	
		try
		{
			servicePledgeFilter.setId(userId);
			servicePledgeFilter.setUpdatedByID(userId);
            servicePledgeFilter.setDateUpdated(new Date());
            returnValue = this.getServicePledgeDao().updateServicePledgeFilter(servicePledgeFilter, userId);
        }
		catch (Exception ex)
		{
		     throw new ServicePledgeSaveException("ServicePledgeBoImpl::updateServicePledgeFilter, userId: " + userId, ex);
		}	
		
		return returnValue;	
        
    }
    

    
    /**
     * Cleans up any Collection Objects created by the Class instance
     */
    protected void finalize() throws Throwable
    {
        super.finalize();
        
    }    
    
    
}
    
