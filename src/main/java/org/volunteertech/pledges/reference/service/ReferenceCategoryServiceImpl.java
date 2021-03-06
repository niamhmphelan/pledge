package org.volunteertech.pledges.reference.service;
import com.netgrains.security.Authorisation;
import com.netgrains.security.AuthorisationException;
import com.netgrains.security.InvalidUserIDException;
import org.volunteertech.pledges.reference.dao.ReferenceCategory;
import org.volunteertech.pledges.reference.business.ReferenceCategoryBo;
import org.volunteertech.pledges.reference.dao.ReferenceCategoryFilter;
import org.volunteertech.pledges.reference.dao.ReferenceCategoryHistory;
import org.volunteertech.pledges.reference.dao.ReferenceCategoryLoadException;
import org.volunteertech.pledges.reference.dao.ReferenceCategorySaveException;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Locale;

import org.volunteertech.pledges.main.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import org.volunteertech.pledges.reference.dao.Reference;
import org.volunteertech.pledges.reference.dao.ReferenceImpl;
import org.volunteertech.pledges.reference.service.ReferenceService;

import org.volunteertech.pledges.reference.service.ReferenceService;
  
import org.volunteertech.pledges.localisation.service.MessageResourceService;
  



/**
 * The reference category screen is used to add/edit new/existing reference category entries. The reference entries are used to populate dropdowns where a group of reference entries with a common column-value can be associated with a dropdown.
 * This Class has been generated by the XSLT processor from the meta data and represents the
 * implementation of the Service  Layer Object for the ReferenceCategory entity. A Business Object
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
public class ReferenceCategoryServiceImpl implements ReferenceCategoryService
{
	/**
	 * The Business Object
	 */
	ReferenceCategoryBo  referenceCategoryBo;
	
	/**
	 * Reference service for lookups of drop down contents
	 */
    ReferenceService referenceService;	  
	  
	/**
	 * The message resource service that allows access to locale specific messages
	 */
    MessageResourceService messageResourceService;	  
	  
    
    /**
     * Default Constructor for the ReferenceCategory Business Object.
     */
    public ReferenceCategoryServiceImpl()
    {
    	//Initialise the related Object stores
        
    }
    
	
	/**
	 * Sets the reference Service to perform a user roles lookup
	 */
	public void setReferenceService(ReferenceService referenceService){
		this.referenceService = referenceService;
	}
	
	/**
	 * Returns a reference to reference Service.
	 */
	public ReferenceService getReferenceService(){
		return this.referenceService;
	}	
	  
	/**
	 * Sets the message resource service to perform localization lookups
	 */
	public void setMessageResourceService(MessageResourceService messageResourceService){
		this.messageResourceService = messageResourceService;
	}
	
	/**
	 * Returns a reference to the message resource service.
	 */
	public MessageResourceService getMessageResourceService(){
		return this.messageResourceService;
	}	
      
    
    /**
     * Sets the required Business Object (BO)
     * @param referenceCategoryBo the referenceCategoryBo used to access the referenceCategory entity
     * and it's associations.
     */
    public void setReferenceCategoryBo(ReferenceCategoryBo referenceCategoryBo){
    	this.referenceCategoryBo = referenceCategoryBo;
    }

    /**
     * Returns the required Business Object (BO)
     * @return the ReferenceCategoryBO used to access the referenceCategory entity
     * and it's associations.     
     */
    public ReferenceCategoryBo getReferenceCategoryBo(){
    	return this.referenceCategoryBo;
    }

    /**
     * Loads a ReferenceCategory object based on the REFERENCECATEGORYID column
     * @param REFERENCECATEGORYID the primary key for the underlying record.
     * @param userID the userID is checked for authorisation to view the record
     * @ return a ReferenceCategory object matching the parameter or null if none can be found. The
     * method also returns null if the parameter is null.
     */
    public ReferenceCategory load(Long referenceCategoryId, Long loggedInUserId) throws ReferenceCategoryLoadException, AuthorisationException, InvalidUserIDException
    {
    	ReferenceCategory referenceCategory = null;
    	
        if (Authorisation.isAuthorisedView("ReferenceCategory", loggedInUserId, referenceCategoryId) == false)
        {
        	throw new AuthorisationException();
        }
    	

        if (referenceCategoryId != null)
        {
        	try
        	{
				referenceCategory = (ReferenceCategory)this.getReferenceCategoryBo().load(referenceCategoryId, loggedInUserId);
        	}
			catch (Exception ex)
			{
			     throw new ReferenceCategoryLoadException("ReferenceCategoryServiceImpl::load(?, ?)", ex);
			}		
    		
            // Load all related singular objects
            
        }
    	
        return referenceCategory;
    }
    
    


    /**
     * Load all existing ReferenceCategory objects. 
     * @return List a List of ReferenceCategory objects.
     */
    public List<ReferenceCategory> listReferenceCategory()
    {
        List<ReferenceCategory> result = this.getReferenceCategoryBo().listReferenceCategory();

        return result;
    }
    
    
    /**
     * Returns a <code>List</code> of ReferenceCategory objects that have a matching referenceCategoryDesc
     * @return List a List of ReferenceCategory objects that match the referenceCategoryDesc property given as parameter.
     * @param referenceCategoryDesc the referenceCategoryDesc on which to match the required records.
     */
    public List<ReferenceCategory> listReferenceCategoryByReferenceCategoryDesc(String referenceCategoryDesc)
    {
        List<ReferenceCategory> result = this.getReferenceCategoryBo().listReferenceCategoryByReferenceCategoryDesc(referenceCategoryDesc);

        return result;
    }

    /**
     * Returns a <code>List</code> of ReferenceCategory objects that have a matching parentCategoryId
     * @return List a List of ReferenceCategory objects that match the parentCategoryId property given as parameter.
     * @param parentCategoryId the parentCategoryId on which to match the required records.
     */
    public List<ReferenceCategory> listReferenceCategoryByParentCategoryId(Long parentCategoryId)
    {
        List<ReferenceCategory> result = this.getReferenceCategoryBo().listReferenceCategoryByParentCategoryId(parentCategoryId);

        return result;
    }

    /**
     * Returns a <code>List</code> of ReferenceCategory objects that have a matching description
     * @return List a List of ReferenceCategory objects that match the description property given as parameter.
     * @param description the description on which to match the required records.
     */
    public List<ReferenceCategory> listReferenceCategoryByDescription(String description)
    {
        List<ReferenceCategory> result = this.getReferenceCategoryBo().listReferenceCategoryByDescription(description);

        return result;
    }

    
    /**
     * Load existing ReferenceCategory objects from the database that have a createdByID column
     * that matches the userId parameter.
     * @return List a List of ReferenceCategory objects that match the userId parameter.
     */
    public List<ReferenceCategory> listReferenceCategoryByCreatedById(Long userId)
    {
        List<ReferenceCategory> result = this.getReferenceCategoryBo().listReferenceCategory();

        return result;
    }
    
    
    /**
     * Load all existing ReferenceCategoryHistory objects for a particular ReferenceCategory.
     * The <code>List</code> of ReferenceCategoryHistory objects contains all of the changes that have been made to the
     * ReferenceCategory object identified by the referenceCategory parameter'
     * @return List a List of ReferenceCategoryHistory objects.
     */
    public List<ReferenceCategoryHistory> listReferenceCategoryHistory(Long referenceCategory) throws Exception
    {
        List<ReferenceCategoryHistory> result = this.getReferenceCategoryBo().listReferenceCategoryHistory(referenceCategory);

        return result;
    }
    
    
    /**
     * Returns a List representing the Set of associated Reference objects that represent the references property
     * and that are defined by the many-to-many relationship defined as ReferenceCategory.references.
     * @return the <code>List</code> of the associated Reference objects that represent the references property.
     */
    public List<Reference> getReferences(Long referenceCategoryId, Long userId) throws ReferenceCategoryLoadException, AuthorisationException, InvalidUserIDException{
    	return this.getReferenceCategoryBo().getReferences(referenceCategoryId, userId);
    }
    
    /**
     * Adds a Reference to the Set of associated Reference objects that represent the references property
     * and that are defined by the many-to-many relationship defined as ReferenceCategory.references.
     * @return the updated <code>List</code> of the associated Reference objects that represent the references property.
     */
    public List<Reference> addReferenceToReferences(Reference references, Long referenceCategoryId, Long userId) throws ReferenceCategorySaveException, ReferenceCategoryLoadException, AuthorisationException, InvalidUserIDException{
    	return this.getReferenceCategoryBo().addReferenceToReferences(references, referenceCategoryId, userId);
	}
  
    
    /**
     * Either updates an existing entry in the REFERENCECATEGORY table or adds a new entry if one does not exist.
     *
     * @param userID the userID of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @throws ReferenceCategoryLoadException if the existing record cannot be loaded
     * @throws ReferenceCategorySaveException if there is an issue populating or saving the updated object
     * @throws AuthorisationException if the user is not authorised to update the object
     * @throws InvalidUserIDException if the userID passed as parameter is not valid
     */
    public void storeReferenceCategory(ReferenceCategory referenceCategory, Long userId)
        throws ReferenceCategorySaveException, ReferenceCategoryLoadException, AuthorisationException, InvalidUserIDException
    {
    	
        if (Authorisation.isAuthorisedUpdate("ReferenceCategory", userId, referenceCategory.getId()) == false)
        {
        	throw new AuthorisationException();
        }
    	
		try
		{
			if(referenceCategory.isNew()){
		    	this.getReferenceCategoryBo().createAndStoreReferenceCategory(referenceCategory, userId);
			}else{
		    	this.getReferenceCategoryBo().updateReferenceCategory(referenceCategory, userId);
			}
        }
		catch (Exception ex)
		{
		     throw new ReferenceCategorySaveException("ReferenceCategoryEntityDataManager::updateReferenceCategory, ReferenceCategoryID: " + referenceCategory.getId(), ex);
		}		
        
    }
    
    
    /**
     * Loads a ReferenceCategoryFilter object based on the participantID of the currently logged in user
     * @param userID the userID is checked for authorisation to view the record. It is also the key to load a against the createdByID
     * column of the underlying record. There should only ever be one or zero filter records per ReferenceCategory.
     * @ return a ReferenceCategoryFilter object matching the parameter or a newly constructed, empty object if none can be found
     */
    public ReferenceCategoryFilter loadReferenceCategoryFilter(Long userID) throws ReferenceCategoryLoadException, AuthorisationException, InvalidUserIDException
    {
    	ReferenceCategoryFilter referenceCategoryFilter = null;
    	
        /*if (Authorisation.isAuthorisedView("ReferenceCategory", userID, REFERENCECATEGORYID) == false)
        {
        	throw new AuthorisationException();
        }*/
    	

        if (userID != null)
        {
        	
        	try
        	{
				referenceCategoryFilter = (ReferenceCategoryFilter)this.getReferenceCategoryBo().loadReferenceCategoryFilter(userID);
        	}
			catch (Exception ex)
			{
			     throw new ReferenceCategoryLoadException("ReferenceCategoryEntityDataManager::loadReferenceCategoryFilter", ex);
			}		
    		
            // Load all related singular objects
            
        }
    	
        return referenceCategoryFilter;
    }
    
    /**
     * Creates a new entry in the REFERENCECATEGORYFILTER table containing the parameters passed here.
     *
     * @param userId the userId of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @return the userId which is also the assigned Id for the newly created record.
     * @throws ReferenceCategorySaveException on failure to save.  
     */
    public Long createAndStoreReferenceCategoryFilter(ReferenceCategoryFilter referenceCategoryFilter, Long userId)
        throws ReferenceCategorySaveException
    {
    	Long returnValue = Long.valueOf(0);
        

		try
		{
            referenceCategoryFilter.setId(userId);
			referenceCategoryFilter.setCreatedByID(userId);
            referenceCategoryFilter.setUpdatedByID(userId);
            referenceCategoryFilter.setDateCreated(new Date());
            referenceCategoryFilter.setDateUpdated(new Date());

            returnValue = this.getReferenceCategoryBo().createAndStoreReferenceCategoryFilter(referenceCategoryFilter, userId);
		}
		catch (Exception ex)
		{
		     throw new ReferenceCategorySaveException("ReferenceCategoryServiceImpl::createAndStoreReferenceCategoryFilter", ex);
		}		
		
		return returnValue;
    }
    
    
    /**
     * Updates an existing entry in the REFERENCECATEGORYFILTER table and associated tables 
     * containing the parameters passed here. If the entry denoted by the userID does not exist then one is created.
     *	
     * @param userId the userId of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @throws ReferenceCategorySaveException if there is an issue populating or saving the updated object
     * @throws AuthorisationException if the user is not authorised to update the object
     * @throws InvalidUserIDException if the userID passed as parameter is not valid
     */
    public Long updateReferenceCategoryFilter(ReferenceCategoryFilter referenceCategoryFilter, Long userId)
        throws ReferenceCategoryLoadException, ReferenceCategorySaveException, AuthorisationException, InvalidUserIDException
    {
    	Long returnValue = Long.valueOf(0);
    	
        //if (Authorisation.isAuthorisedUpdate("ReferenceCategory", userId, REFERENCECATEGORYID) == false)
        //{
        //	throw new AuthorisationException();
        //}
    	
		try
		{
        
			referenceCategoryFilter.setUpdatedByID(userId);
            referenceCategoryFilter.setDateUpdated(new Date());
            returnValue = this.getReferenceCategoryBo().updateReferenceCategoryFilter(referenceCategoryFilter, userId);
        }
		catch (Exception ex)
		{
		     throw new ReferenceCategorySaveException("ReferenceCategoryServiceImpl::updateReferenceCategoryFilter, userId: " + userId, ex);
		}	
		
		return returnValue;	
        
    }

	/**
	 * Translates the referenceId type properties into the locale specific text for display on the front-end.
	 * @param referenceCategory the dto object to be updated with the locale specific translations
	 * @param locale the Locale to be displayed
	 */    
    public ReferenceCategory translateReferenceValues(ReferenceCategory referenceCategory, Locale locale){
    	
    	return referenceCategory;
    }
    
	/**
	 * Translates the referenceId type properties into the locale specific text for display on the front-end.
	 * @param referenceCategoryList the dto object to be updated with the locale specific translations
	 * @param locale the Locale to be displayed
	 */    
    public List<ReferenceCategory> translateReferenceValues(List<ReferenceCategory> referenceCategoryList, Locale locale){
    	for (ReferenceCategory referenceCategory :  referenceCategoryList){
    		referenceCategory = translateReferenceValues(referenceCategory, locale);
    	}
    	
    	return referenceCategoryList;
    }
    
	
	/**
	 * Translates the referenceId type properties into the locale specific text for display on the front-end.
	 * @param reference the dto object to be updated with the locale specific translations
	 * @param locale the Locale to be displayed
	 */    
    public Reference translateReferenceReferenceValues(Reference reference, Locale locale){
    	return this.referenceService.translateReferenceValues(reference, locale);
    }
    
	/**
	 * Translates the referenceId type properties into the locale specific text for display on the front-end.
	 * @param referenceList the dto object to be updated with the locale specific translations
	 * @param locale the Locale to be displayed
	 */    
    public List<Reference> translateReferenceReferenceValues(List<Reference> referenceList, Locale locale){
    	return this.referenceService.translateReferenceValues(referenceList, locale);
    }


    
    /**
     * Cleans up any Collection Objects created by the Class instance
     */
    protected void finalize() throws Throwable
    {
        super.finalize();
        
    }    
    
    
}
    
