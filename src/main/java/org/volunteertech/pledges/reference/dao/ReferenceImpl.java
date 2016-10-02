package org.volunteertech.pledges.reference.dao;

import org.volunteertech.pledges.reference.dao.Reference;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


import org.volunteertech.pledges.reference.dao.ReferenceCategory;




/**
 * The reference screen is used to add/edit new/existing reference entries. The reference entries are used to populate dropdowns where a group of reference entries with a common column-value can be associated with a dropdown.
 * This class has been generated by the XSLT processor from the metadata and represents the
 * DataBase access handler for the Reference entity.
 * <P> 
 * It is essential that methods added to this class are given JavaDoc comments to allow
 * documentation to be generated. For a description of JavaDoc refer to The JavaDoc documentation.
 * A link is provided below.
 * <P>
 * @author Michael O'Connor
 * @version $Revision$
 * Date: $Date$
 * @see <a href="http://java.sun.com/j2se/javadoc/writingdoccomments/index.html">JavaDoc Documentation</a> 
 * Change Log
 * ----------
 * $Log$
 *
 */
public class ReferenceImpl implements Reference, Serializable { 

    private static final long serialVersionUID = 1L;
    
	/** Entity Id */
	private Long id;
	
    /** id of a parent object used when adding objects via JSON enabled restful web services */
    private Long parentObjectId;
    
    /**
     * Used to store the current processing mode during which the object was loaded
     */
    private CurrentMode currentMode;

    
    /** the reference type grouping for the entity */
    private String refType;
	
    /** the unique reference description within the reference type group */
    private String refDesc;
	
    /** the column-value that allows an entry to be linked to a parent entity */
    private Long parentId;
      
    /** the order in which the reference entity is placed in its column-value group */
    private Long refIndex;
      
    /** a user friendly description of the reference item */
    private String description;
	
	/** the reference to the foreign associated ReferenceCategory defined as ReferenceCategory.references in ApplicationDef.xml **/
	@JsonBackReference(value="referencecategory-reference")
	private Set<ReferenceCategory> referenceCategory = new HashSet<ReferenceCategory>();
  

    /** The user ID of the person that originally created the underlying record **/
    private Long createdByID;

    /** The user ID of the person that last updated the underlying record **/
    private Long updatedByID;

    /** The date that the underlying record was created DD/MM/YYYY-HH:MM format **/
    private Date dateCreated;

    /** The date that the underlying record was last updated DD/MM/YYYY-HH:MM format **/
    private Date dateUpdated;
    

    /**
     * Default Constructor for the Reference bean
     */
    public ReferenceImpl()
    {
    	
		this.refType = "";
		
		this.refDesc = "";
		
		this.parentId = new Long("0");
		
		this.refIndex = new Long("0");
		
		this.description = "";
		
    	this.dateCreated = new Date();
    	this.dateUpdated = new Date();
    }
    
    /**
     * Returns the Id for the underlying database table record
     * @return the Id for the underlying database table record
     */
	public Long getId()
	{
        return id;
    }

    /**
     * Sets the Id. This allows front-end forms to bind the id property.
     * @param id the Id for the underlying database table record.
     */
	public void setId(Long id)
	{
        this.id = id;
    }

    /**
     * Returns the parentObjectId when this object has been populated via a restful webservice JSON mapping
     * @return the parentObjectId when this object has been populated via a restful webservice JSON mapping
     */
	public Long getParentObjectId()
	{
        return parentObjectId;
    }

    /**
     * Sets the parentObjectId. This allows restful web services to bind the parentId property when adding a new record.
     * @param parentObjectId allows restful web services to bind the parentId property when adding a new record.
     */
	public void setParentObjectId(Long parentObjectId)
	{
        this.parentObjectId = parentObjectId;
    }

    
    
    /**
     * Sets the reference type grouping for the entity
     * @param refType is the reference type grouping for the entity
     */
    public void setRefType(String refType)
    {
      this.refType = refType;
    }

    /**
     * Returns the reference type grouping for the entity
     * @return the reference type grouping for the entity
     */
    public String getRefType()
    {
      return refType;
    }
    
    
    
    /**
     * Sets the unique reference description within the reference type group
     * @param refDesc is the unique reference description within the reference type group
     */
    public void setRefDesc(String refDesc)
    {
      this.refDesc = refDesc;
    }

    /**
     * Returns the unique reference description within the reference type group
     * @return the unique reference description within the reference type group
     */
    public String getRefDesc()
    {
      return refDesc;
    }
    
    
    
    /**
     * Sets the column-value that allows an entry to be linked to a parent entity
     * @param parentId is the column-value that allows an entry to be linked to a parent entity
     */
    public void setParentId(Long parentId)
    {
      this.parentId = parentId;
    }

    /**
     * Returns the column-value that allows an entry to be linked to a parent entity
     * @return the column-value that allows an entry to be linked to a parent entity
     */
    public Long getParentId()
    {
      return parentId;
    }
    
    
    
    /**
     * Sets the order in which the reference entity is placed in its column-value group
     * @param refIndex is the order in which the reference entity is placed in its column-value group
     */
    public void setRefIndex(Long refIndex)
    {
      this.refIndex = refIndex;
    }

    /**
     * Returns the order in which the reference entity is placed in its column-value group
     * @return the order in which the reference entity is placed in its column-value group
     */
    public Long getRefIndex()
    {
      return refIndex;
    }
    
    
    
    /**
     * Sets a user friendly description of the reference item
     * @param description is a user friendly description of the reference item
     */
    public void setDescription(String description)
    {
      this.description = description;
    }

    /**
     * Returns a user friendly description of the reference item
     * @return a user friendly description of the reference item
     */
    public String getDescription()
    {
      return description;
    }
    
    
    /**
     * Sets the <code>Set</code> of associated ReferenceCategory mapped as the referenceCategory property
     * @param the <code>Set</code> of associated ReferenceCategory mapped as the referenceCategory property
     */
    public void setReferenceCategory(Set<ReferenceCategory> referenceCategory)
    {
      this.referenceCategory = referenceCategory;
    }

    /**
     * Returns the <code>Set</code> of associated ReferenceCategory mapped as the referenceCategory property
     * @return the <code>Set</code> of associated ReferenceCategory mapped as the referenceCategory property
     */
    public Set<ReferenceCategory>  getReferenceCategory()
    {
      return this.referenceCategory;
    }


  

    /**
     * sets the Id of the user that created the underlying record
     * @param createdByID the Id of the user that created the underlying record
     */
    public void setCreatedByID(Long createdByID)
    {
        this.createdByID = createdByID;
    }

    /**
     * gets the Id of the user that created the underlying record
     * @return the Id of the user that created the underlying record
     */
    public Long getCreatedByID()
    {
        return createdByID;
    }
     
   	/**
     * sets the Id of the user that last updated the underlying record
     * @param updatedByID the Id of the user that last updated the underlying record
     */
    public void setUpdatedByID(Long updatedByID)
    {
        this.updatedByID = updatedByID;
    }
     
    /**
     * gets the Id of the user that last updated the underlying record
     * @return the Id of the user that last updated the underlying record
     */
    public Long getUpdatedByID()
    {
        return updatedByID;
    }

    /**
     * sets the date that the underlying record was first created as a <code>java.util.Date</code> object
     * @param dateCreated the date that the underlying record was first created
     */
    public void setDateCreated(Date dateCreated)
    {
        this.dateCreated = dateCreated;
    }

    /**
     * Gets the date that the underlying record was first created as a <code>java.util.Date</code> object
     * @return the date that the underlying record was first created
     */
    public Date getDateCreated()
    {
        return dateCreated;
    }

    /**
     * Sets the date that the underlying record was last updated as a <code>java.util.Date</code> object
     * @param dateUpdated the date that the underlying record was last updated
     */
    public void setDateUpdated(Date dateUpdated)
    {
        this.dateUpdated = dateUpdated;
    }

    /**
     * Gets the date that the underlying record was last updated as a <code>java.util.Date</code> object
     * @return the date that the underlying record was last updated
     */
    public Date getDateUpdated()
    {
        return dateUpdated;
    }
    
    /**
     * Checks for a newly created entity object
     * @return true if this is a newly created entity object meaning that it has not yet been persisted, otherwise false
     */
	public boolean isNew() {
		return (this.id == null);
	}
	
	/**
	 * Sets the current mode of use for the entity object;
	 * @param mode a member of the CurrentMode enumeration
	 */
	public void setCurrentMode(CurrentMode mode){
		this.currentMode = mode;
	}	

    /**
     * Returns the current mode under which the class instance was loaded
     * @return A member of the CurrentMode Enumeration
     */
	public CurrentMode getCurrentMode() {
		return this.currentMode;
	}
    
    
     @Override
     public String toString(){
    	return "Reference [id=" + id + ", " + "refType=" + refType + ", " + "refDesc=" + refDesc + ", " + "parentId=" + parentId + ", " + "refIndex=" + refIndex + ", " + "description=" + description + "]";
     }
}
    
    
    

