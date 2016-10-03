package org.volunteertech.pledges.reference.dao;
import org.hibernate.SessionFactory;
import com.netgrains.security.Authorisation;
import com.netgrains.security.AuthorisationException;
import com.netgrains.security.InvalidUserIDException;
import org.volunteertech.pledges.reference.dao.Reference;
import org.volunteertech.pledges.reference.dao.ReferenceHistory;
import org.volunteertech.pledges.reference.dao.ReferenceLoadException;
import org.volunteertech.pledges.reference.dao.ReferenceSaveException;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigDecimal;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


import org.volunteertech.pledges.reference.dao.ReferenceCategory;




/**
 * The reference screen is used to add/edit new/existing reference entries. The reference entries are used to populate dropdowns where a group of reference entries with a common column-value can be associated with a dropdown.
 * This class has been generated by the XSLT processor from the metadata and represents the
 * implementation of the data access object for the Reference entity.
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
public class ReferenceDaoImpl implements ReferenceDao
{
    /**
     * The Hibernate SessionFactory instance
     */
    private SessionFactory sessionFactory;
    
    
    

    /**
     * Default Constructor for the Reference entity data manager
     */
    public ReferenceDaoImpl()
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
     * Loads a Reference object based on the referenceId column
     * @param ReferenceId the primary key for the underlying record.
     * @ return a Reference object matching the parameter or null if none can be found. The
     * method also returns null if the parameter is null.
     */
    public Reference load(Long referenceId, Long loggedInUserId) throws AuthorisationException, InvalidUserIDException
    {
        if (Authorisation.isAuthorisedView("Reference", loggedInUserId, referenceId) == false)
        {
        	throw new AuthorisationException();
        }
    
    	Reference hibernateEntity = null;
    	
        if (referenceId != null)
        {
			hibernateEntity = (Reference)this.sessionFactory.getCurrentSession().get(ReferenceImpl.class, referenceId);

            // Load all related singular objects
            
        }
    	
        return hibernateEntity;
    }
    
    
    

    /**
     * Load all existing Reference objects from the database. Hibernate will generate the appropriate SQL,
     * send it to the database and populate Reference objects with the data.
     * @return List a List of Reference objects.
     */
    public List<Reference> listReference()
    {
        @SuppressWarnings("unchecked")
        List<Reference> result = this.sessionFactory.getCurrentSession().createQuery("from ReferenceImpl as reference order by reference.id asc").list();

        return result;
    }

    
    /**
     * Load existing Reference objects from the database that have a refType column
     * that matches the refType parameter.
     * @return List a List of Reference objects that match the refType parameter.
     */
    public List<Reference> listReferenceByRefType(String refType)
    {
        @SuppressWarnings("unchecked")
        List<Reference> result = this.sessionFactory.getCurrentSession().createQuery("from ReferenceImpl as reference where reference.refType = :reftype order by reference.id asc").setParameter("reftype", refType).list();

        return result;
    }

    /**
     * Load existing Reference objects from the database that have a refDesc column
     * that matches the refDesc parameter.
     * @return List a List of Reference objects that match the refDesc parameter.
     */
    public List<Reference> listReferenceByRefDesc(String refDesc)
    {
        @SuppressWarnings("unchecked")
        List<Reference> result = this.sessionFactory.getCurrentSession().createQuery("from ReferenceImpl as reference where reference.refDesc = :refdesc order by reference.id asc").setParameter("refdesc", refDesc).list();

        return result;
    }

    /**
     * Load existing Reference objects from the database that have a parentId column
     * that matches the parentId parameter.
     * @return List a List of Reference objects that match the parentId parameter.
     */
    public List<Reference> listReferenceByParentId(Long parentId)
    {
        @SuppressWarnings("unchecked")
        List<Reference> result = this.sessionFactory.getCurrentSession().createQuery("from ReferenceImpl as reference where reference.parentId = :parentid order by reference.id asc").setParameter("parentid", parentId).list();

        return result;
    }

    /**
     * Load existing Reference objects from the database that have a refIndex column
     * that matches the refIndex parameter.
     * @return List a List of Reference objects that match the refIndex parameter.
     */
    public List<Reference> listReferenceByRefIndex(Long refIndex)
    {
        @SuppressWarnings("unchecked")
        List<Reference> result = this.sessionFactory.getCurrentSession().createQuery("from ReferenceImpl as reference where reference.refIndex = :refindex order by reference.id asc").setParameter("refindex", refIndex).list();

        return result;
    }

    /**
     * Load existing Reference objects from the database that have a description column
     * that matches the description parameter.
     * @return List a List of Reference objects that match the description parameter.
     */
    public List<Reference> listReferenceByDescription(String description)
    {
        @SuppressWarnings("unchecked")
        List<Reference> result = this.sessionFactory.getCurrentSession().createQuery("from ReferenceImpl as reference where reference.description = :description order by reference.id asc").setParameter("description", description).list();

        return result;
    }

    
    /**
     * Load existing Reference objects from the database that have a createdByID column
     * that matches the createdById parameter.
     * @return List a List of Reference objects that match the createdById parameter.
     */
    public List<Reference> listReferenceByCreatedById(Long userId)
    {
        @SuppressWarnings("unchecked")
        List<Reference> result = this.sessionFactory.getCurrentSession().createQuery("from ReferenceImpl as reference where reference.createdByID = :userId order by reference.id asc").setParameter("userId", userId).list();

        return result;
    }
    
    
    /**
     * Load all existing ReferenceHistory objects for a particular Reference from the database.
     * Hibernate will generate the appropriate SQL,
     * send it to the database and populate a List of ReferenceHistory objects with the data.
     * @return List a List of ReferenceHistory objects.
     */
    public List<ReferenceHistory> listReferenceHistory(Long reference)
    {
        
        @SuppressWarnings("unchecked")
        List<ReferenceHistory> result = this.sessionFactory.getCurrentSession().createQuery("from ReferenceHistory as referenceHistory where referenceHistory.referenceId = ? order by referenceHistory.id asc").setLong(0, reference).list();

        return result;
    }
    
    
    /**
     * Load a <code>List</code> of Reference objects from the database that match the
     * referenceCategoryId parameter, the ReferenceCategory and the Reference defined as the many-to-many association in ReferenceCategory.references in ApplicationDef.xml.
     * @return a <code>List</code> of Reference objects that match the referenceCategoryId parameter.
     */
    public List<Reference> listReferenceByReferenceCategoryId(Long  referenceCategoryId)
    {
        @SuppressWarnings("unchecked")
        
        List<Reference> result = this.sessionFactory.getCurrentSession().createQuery("select distinct reference from ReferenceCategoryImpl referenceCategory join referenceCategory.referenceCategory referenceCategory where referenceCategory.id = :referenceCategoryId").setParameter("referenceCategoryId", referenceCategoryId).list();

        return result;
    }
  
    
    /**
     * Creates a new entry in the REFERENCE table containing the parameters passed here.
     *
     * @param userID the userID of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @return the database generated Id of the newly created record.
     * @throws ReferenceSaveException on failure to save.  
     */
    public Long createAndStoreReference(Reference reference, Long userId)
    {
    	Long returnValue = Long.valueOf(0);

        reference.setCreatedByID(userId);
        reference.setUpdatedByID(userId);
        reference.setDateCreated(new Date());
        reference.setDateUpdated(new Date());
        
        this.sessionFactory.getCurrentSession().save(reference);
            
        returnValue = reference.getId();
		
		return returnValue;
    }


    /**
     * Updates an existing entry in the REFERENCE table containing the parameters passed here.
     * Only non-null parameters will be applied to the underlying table so preserving any existing entries.
     *
     * @param userId the userId of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @param the reference the Reference object to update in the database.
     * @param userId the userId of the currently logged in user. 
     * @throws AuthorisationException if the user is not authorised to update the object
     * @throws InvalidUserIDException if the userId passed as parameter is not valid
     */
    public void updateReference(Reference reference, Long userId) throws AuthorisationException, InvalidUserIDException
    {
        if (Authorisation.isAuthorisedUpdate("Reference", userId, reference.getId()) == false)
        {
        	throw new AuthorisationException();
        }
    	
        reference.setUpdatedByID(userId);
        reference.setDateUpdated(new Date());
        this.sessionFactory.getCurrentSession().update(reference);
    }
    
    
    /**
     * Loads a ReferenceFilter object based on the participantID of the currently logged in user
     * @param userID the userID is checked for authorisation to view the record. It is also the key to load a against the createdByID
     * column of the underlying record. There should only ever be one or zero filter records per Reference.
     * @ return a ReferenceFilter object matching the parameter or a newly constructed, empty object if none can be found
     */
    public ReferenceFilter loadReferenceFilter(Long userID) throws ReferenceLoadException, AuthorisationException, InvalidUserIDException
    {
    	ReferenceFilter hibernateEntity = null;
    	
        /*if (Authorisation.isAuthorisedView("Reference", userID, REFERENCEID) == false)
        {
        	throw new AuthorisationException();
        }*/
    	

        if (userID != null)
        {
        	
        	try
        	{
				hibernateEntity = (ReferenceFilter)this.sessionFactory.getCurrentSession().load(ReferenceFilter.class, userID);
        	}
			catch (Exception ex)
			{
			     throw new ReferenceLoadException("ReferenceEntityDataManager::loadReferenceFilter", ex);
			}		
    		
            // Load all related singular objects
            
        }
    	
        return hibernateEntity;
    }
    
    /**
     * Creates a new entry in the REFERENCEFILTER table containing the parameters passed here.
     *
     * @param userId the userId of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @return the userID which is also the assigned Id for the newly created record.
     * @throws ReferenceSaveException on failure to save.  
     */
    public Long createAndStoreReferenceFilter(ReferenceFilter referenceFilter,  Long userId)
        throws ReferenceSaveException
    {
    	Long returnValue = Long.valueOf(0);
        

		try
		{
            referenceFilter.setId(userId);
            referenceFilter.setCreatedByID(userId);
            referenceFilter.setUpdatedByID(userId);
            referenceFilter.setDateCreated(new Date());
            referenceFilter.setDateUpdated(new Date());

            this.sessionFactory.getCurrentSession().save(referenceFilter);
            
            returnValue = referenceFilter.getId();
		}
		catch (Exception ex)
		{
		     throw new ReferenceSaveException("ReferenceEntityDataManager::createAndStoreReferenceFilter", ex);
		}		
		
		return returnValue;
    }
    
    
    /**
     * Updates an existing entry in the REFERENCEFILTER table and associated tables 
     * containing the parameters passed here. If the entry denoted by the userID does not exist then one is created.
     *	
     * @param userId the userId of the currently logged in user, this can be retrieved from the session.
     * This value must be set for auditing purposes.
     * @throws ReferenceSaveException if there is an issue populating or saving the updated object
     * @throws AuthorisationException if the user is not authorised to update the object
     * @throws InvalidUserIDException if the userID passed as parameter is not valid
     */
    public Long updateReferenceFilter(ReferenceFilter referenceFilter, Long userId)
        throws ReferenceLoadException, ReferenceSaveException, AuthorisationException, InvalidUserIDException
    {
    	Long returnValue = Long.valueOf(0);
    	
        //if (Authorisation.isAuthorisedUpdate("Reference", userID, REFERENCEID) == false)
        //{
        //	throw new AuthorisationException();
        //}
    	
		try
		{
			referenceFilter.setId(userId);
            referenceFilter.setUpdatedByID(userId);
            referenceFilter.setDateUpdated(new Date());
            this.sessionFactory.getCurrentSession().update(referenceFilter);
            returnValue = referenceFilter.getId();
        }
		catch (Exception ex)
		{
		     throw new ReferenceSaveException("ReferenceDaoImpl::updateReferenceFilter, userId: " + userId, ex);
		}	
		
		return returnValue;	
        
    }
    
	
	/**
	 * Returns a List of the ReferenceCategory that own the Reference identified by the
	 * id given as a parameter.
	 * @param referenceId the id of the Reference for which the ReferenceCategory should be resolved.
	 * @param userId the userId of the currently logged in user.
	 */
	public List<ReferenceCategory> listReferenceCategoryByReferenceId(Long referenceId, Long userId){
		@SuppressWarnings("unchecked")
		List<ReferenceCategory> result = this.sessionFactory.getCurrentSession().createQuery("select distinct referenceCategory from ReferenceCategoryImpl as referenceCategory inner join referenceCategory.references as reference where reference.id = :referenceId order by referenceCategory.id asc").setParameter("referenceId", referenceId).list();
    	return result;
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
    
