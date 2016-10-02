package org.volunteertech.pledges.address.dao;

import org.volunteertech.pledges.address.dao.Address;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;





/**
 * The Address Details
 * This class has been generated by the XSLT processor from the metadata and represents the
 * DataBase access handler for the Address entity.
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
public class AddressImpl implements Address, Serializable { 

    private static final long serialVersionUID = 1L;
    
	/** Entity Id */
	private Long id;
	
    /** id of a parent object used when adding objects via JSON enabled restful web services */
    private Long parentObjectId;
    
    /**
     * Used to store the current processing mode during which the object was loaded
     */
    private CurrentMode currentMode;

    
    /** the street or house address */
    private String addressOne;
	
    /** the town or city address */
    private String addressTwo;
	
    /** the post code */
    private String city;
	
    /** the post code */
    private String stateProvinceRegion;
	
    /** the post code */
    private String postCode;
	
    /** the address type */
    private Long country;
    
    /** the address type */
    private String countryReferenceTranslation;
    
	

    /** The user ID of the person that originally created the underlying record **/
    private Long createdByID;

    /** The user ID of the person that last updated the underlying record **/
    private Long updatedByID;

    /** The date that the underlying record was created DD/MM/YYYY-HH:MM format **/
    private Date dateCreated;

    /** The date that the underlying record was last updated DD/MM/YYYY-HH:MM format **/
    private Date dateUpdated;
    
    private int status=0;
    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
     * Default Constructor for the Address bean
     */
    public AddressImpl()
    {
    	
		this.addressOne = "";
		
		this.addressTwo = "";
		
		this.city = "";
		
		this.stateProvinceRegion = "";
		
		this.postCode = "";
		
		this.country = new Long("0");
		
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
     * Sets the street or house address
     * @param addressOne is the street or house address
     */
    public void setAddressOne(String addressOne)
    {
      this.addressOne = addressOne;
    }

    /**
     * Returns the street or house address
     * @return the street or house address
     */
    public String getAddressOne()
    {
      return addressOne;
    }
    
    
    
    /**
     * Sets the town or city address
     * @param addressTwo is the town or city address
     */
    public void setAddressTwo(String addressTwo)
    {
      this.addressTwo = addressTwo;
    }

    /**
     * Returns the town or city address
     * @return the town or city address
     */
    public String getAddressTwo()
    {
      return addressTwo;
    }
    
    
    
    /**
     * Sets the post code
     * @param city is the post code
     */
    public void setCity(String city)
    {
      this.city = city;
    }

    /**
     * Returns the post code
     * @return the post code
     */
    public String getCity()
    {
      return city;
    }
    
    
    
    /**
     * Sets the post code
     * @param stateProvinceRegion is the post code
     */
    public void setStateProvinceRegion(String stateProvinceRegion)
    {
      this.stateProvinceRegion = stateProvinceRegion;
    }

    /**
     * Returns the post code
     * @return the post code
     */
    public String getStateProvinceRegion()
    {
      return stateProvinceRegion;
    }
    
    
    
    /**
     * Sets the post code
     * @param postCode is the post code
     */
    public void setPostCode(String postCode)
    {
      this.postCode = postCode;
    }

    /**
     * Returns the post code
     * @return the post code
     */
    public String getPostCode()
    {
      return postCode;
    }
    
    
    
    /**
     * Sets the address type that has been chosen by the user
     * @param country is the address type
     */
    public void setCountry(Long country)
    {
      this.country = country;
    }

    /**
     * Returns the address type that has been chosen by the user or saved in the database
     * @return the address type
     */
    public Long getCountry()
    {
      return country;
    }
    
    
    /**
     * Sets the translated reference mapping for the address type that has been chosen by the user
     * @param the translated reference mapping for country is the address type
     */
    public void setCountryReferenceTranslation(String countryReferenceTranslation)
    {
      this.countryReferenceTranslation = countryReferenceTranslation;
    }

    /**
     * Returns the translated reference mapping for the address type that has been chosen by the user or saved in the database
     * @return the translated reference mapping for the address type
     */
    public String getCountryReferenceTranslation()
    {
      return countryReferenceTranslation;
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
    	return "Address [id=" + id + ", " + "addressOne=" + addressOne + ", " + "addressTwo=" + addressTwo + ", " + "city=" + city + ", " + "stateProvinceRegion=" + stateProvinceRegion + ", " + "postCode=" + postCode + ", " + "country=" + country + "]";
     }
}
    
    
    

