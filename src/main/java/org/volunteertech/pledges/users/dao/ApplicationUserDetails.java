package org.volunteertech.pledges.users.dao;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.math.BigDecimal;


import org.volunteertech.pledges.pledge.dao.RegisterOfPledges;




/**
 * The contact information
 * This Interface has been generated by the XSLT processor from the metadata and represents the
 * DataBase access handler interface for the ApplicationUserDetails entity.
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
public interface ApplicationUserDetails extends Serializable { 

    public enum CurrentMode{
    	ADD,UPDATE,LOCALIZE;
    	
    	public String toString(){
    		switch(this){
    			case ADD: return "ADD";
    			case UPDATE: return "UPDATE";
    			case LOCALIZE: return "LOCALIZE";
    			default: return "UNKNOWN";
    		}	
    	}
    }
    	
    /**
     * Returns the Id for the underlying database table record
     * @return the Id for the underlying database table record
     */
	public Long getId();
	
    /**
     * Sets the Id. This allows front-end forms to bind the id property.
     * @param id the Id for the underlying database table record.
     */
	public void setId(Long id);
	
    /**
     * Returns the parentObjectId when this object has been populated via a restful webservice JSON mapping
     * @return the parentObjectId when this object has been populated via a restful webservice JSON mapping
     */
	public Long getParentObjectId();
	
    /**
     * Sets the parentObjectId. This allows restful web services to bind the parentId property when adding a new record.
     * @param parentObjectId allows restful web services to bind the parentId property when adding a new record.
     */
	public void setParentObjectId(Long parentObjectId);
    
    
    /**
     * Sets the contact Name of the pledger
     * @param contactName is the contact Name of the pledger
     */
    public void setContactName(String contactName);

    /**
     * Returns the contact Name of the pledger
     * @return the contact Name of the pledger
     */
    public String getContactName();
    
    
    /**
     * Sets the pledge is from an organisation that has been chosen by the user
     * @param representOrganisation is the pledge is from an organisation
     */
    public void setRepresentOrganisation(Long representOrganisation);

    /**
     * Returns the pledge is from an organisation that has been chosen by the user or saved in the database
     * @return the pledge is from an organisation
     */
    public Long getRepresentOrganisation();
    
    /**
     * Sets the translated reference mapping for the pledge is from an organisation that has been chosen by the user
     * @param the translated reference mapping for representOrganisation is the pledge is from an organisation
     */
    public void setRepresentOrganisationReferenceTranslation(String representOrganisationReferenceTranslation);

    /**
     * Returns the translated reference mapping for the pledge is from an organisation that has been chosen by the user or saved in the database
     * @return the translated reference mapping for the pledge is from an organisation
     */
    public String getRepresentOrganisationReferenceTranslation();
      
    /**
     * blah blah who needs comments
     * @return
     */
	public String getOrganisationName();
	
	   
    /**
     * blah blah who needs comments
     * @return
     */
	public void setOrganisationName(String organisationName);
    
    /**
     * Sets An example of a Telephone Number
     * @param telephoneNumber is An example of a Telephone Number
     */
    public void setTelephoneNumber(String telephoneNumber);

    /**
     * Returns An example of a Telephone Number
     * @return An example of a Telephone Number
     */
    public String getTelephoneNumber();
    
    
    /**
     * Sets the street or house address
     * @param addressOne is the street or house address
     */
    public void setAddressOne(String addressOne);

    /**
     * Returns the street or house address
     * @return the street or house address
     */
    public String getAddressOne();
    
    
    /**
     * Sets the town or city address
     * @param addressTwo is the town or city address
     */
    public void setAddressTwo(String addressTwo);

    /**
     * Returns the town or city address
     * @return the town or city address
     */
    public String getAddressTwo();
    
    
    /**
     * Sets the post code
     * @param city is the post code
     */
    public void setCity(String city);

    /**
     * Returns the post code
     * @return the post code
     */
    public String getCity();
    
    
    /**
     * Sets the post code
     * @param stateProvinceRegion is the post code
     */
    public void setStateProvinceRegion(String stateProvinceRegion);

    /**
     * Returns the post code
     * @return the post code
     */
    public String getStateProvinceRegion();
    
    
    /**
     * Sets the post code
     * @param postCode is the post code
     */
    public void setPostCode(String postCode);

    /**
     * Returns the post code
     * @return the post code
     */
    public String getPostCode();
    
    
    /**
     * Sets the address type that has been chosen by the user
     * @param country is the address type
     */
    public void setCountry(Long country);

    /**
     * Returns the address type that has been chosen by the user or saved in the database
     * @return the address type
     */
    public Long getCountry();
    
    /**
     * Sets the translated reference mapping for the address type that has been chosen by the user
     * @param the translated reference mapping for country is the address type
     */
    public void setCountryReferenceTranslation(String countryReferenceTranslation);

    /**
     * Returns the translated reference mapping for the address type that has been chosen by the user or saved in the database
     * @return the translated reference mapping for the address type
     */
    public String getCountryReferenceTranslation();
      
    
    /**
     * Sets the contact email address where the contact type is email.
     * @param emailAddress is the contact email address where the contact type is email.
     */
    public void setEmailAddress(String emailAddress);

    /**
     * Returns the contact email address where the contact type is email.
     * @return the contact email address where the contact type is email.
     */
    public String getEmailAddress();
    
    /**
     * Sets the associated RegisterOfPledges mapped as the registerOfPledges property
     * @param the associated RegisterOfPledges mapped as the registerOfPledges property
     */
    public void setRegisterOfPledges(RegisterOfPledges registerOfPledges);

    /**
     * Returns the associated RegisterOfPledges mapped as the registerOfPledges property
     * @return the associated RegisterOfPledges mapped as the registerOfPledges property
     */
    public RegisterOfPledges getRegisterOfPledges();
  

    /**
     * sets the Id of the user that created the underlying record
     * @param createdByID the Id of the user that created the underlying record
     */
    public void setCreatedByID(Long createdByID);
    
    /**
     * gets the Id of the user that created the underlying record
     * @return the Id of the user that created the underlying record
     */
    public Long getCreatedByID();
         
   	/**
     * sets the Id of the user that last updated the underlying record
     * @param updatedByID the Id of the user that last updated the underlying record
     */
    public void setUpdatedByID(Long updatedByID);
     
    /**
     * gets the Id of the user that last updated the underlying record
     * @return the Id of the user that last updated the underlying record
     */
    public Long getUpdatedByID();

    /**
     * sets the date that the underlying record was first created as a <code>java.util.Date</code> object
     * @param dateCreated the date that the underlying record was first created
     */
    public void setDateCreated(Date dateCreated);

    /**
     * Gets the date that the underlying record was first created as a <code>java.util.Date</code> object
     * @return the date that the underlying record was first created
     */
    public Date getDateCreated();

    /**
     * Sets the date that the underlying record was last updated as a <code>java.util.Date</code> object
     * @param dateUpdated the date that the underlying record was last updated
     */
    public void setDateUpdated(Date dateUpdated);
    
    /**
     * Gets the date that the underlying record was last updated as a <code>java.util.Date</code> object
     * @return the date that the underlying record was last updated
     */
    public Date getDateUpdated();
    
    /**
     * Checks for a newly created entity object
     * @return true if this is a newly created entity object meaning that it has not yet been persisted, otherwise false
     */
	public boolean isNew();
	
	/**
	 * Sets the current mode of use for the entity object;
	 * @param mode a member of the CurrentMode enumeration
	 */
	public void setCurrentMode(CurrentMode mode);

    /**
     * Returns the current mode under which the class instance was loaded
     * @return A member of the CurrentMode Enumeration
     */
	public CurrentMode getCurrentMode();
	

}
    
    
    

