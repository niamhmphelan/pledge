package org.volunteertech.pledges.users.dao;

import org.volunteertech.pledges.users.dao.ApplicationUser;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;





/**
 * The User Model for controlling user access
 * This class has been generated by the XSLT processor from the metadata and represents the
 * DataBase access handler for the ApplicationUser entity.
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
public class ApplicationUserImpl implements ApplicationUser, Serializable { 

    private static final long serialVersionUID = 1L;
    
	/** Entity Id */
	private Long id;
	
    /** id of a parent object used when adding objects via JSON enabled restful web services */
    private Long parentObjectId;
    
    /**
     * Used to store the current processing mode during which the object was loaded
     */
    private CurrentMode currentMode;

    
    /** the user name */
    private String username;
	
    /** the user password */
    private String password;
	
    /** The flag denotes that the user is currently enabled. */
    private Boolean enabled;
	
    /** The flag denotes that the user is currently enabled. */
    private Set<Long> userRoles = new HashSet<Long>();
	

    /** The user ID of the person that originally created the underlying record **/
    private Long createdByID;

    /** The user ID of the person that last updated the underlying record **/
    private Long updatedByID;

    /** The date that the underlying record was created DD/MM/YYYY-HH:MM format **/
    private Date dateCreated;

    /** The date that the underlying record was last updated DD/MM/YYYY-HH:MM format **/
    private Date dateUpdated;
    
    private ApplicationUserDetails applicationUserDetails;
    

    /**
     * Default Constructor for the ApplicationUser bean
     */
    public ApplicationUserImpl()
    {
    	
		this.username = "";
		
		this.password = "";
		
		this.enabled = new Boolean(Boolean.FALSE);
		
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
     * Sets the user name
     * @param username is the user name
     */
    public void setUsername(String username)
    {
      this.username=username;
      if (this.applicationUserDetails !=null){
    	  applicationUserDetails.setEmailAddress(username);
      }
    }

    /**
     * Returns the user name
     * @return the user name
     */
    public String getUsername()
    {
    	return this.username;
    }
    
    
    
    /**
     * Sets the user password
     * @param password is the user password
     */
    public void setPassword(String password)
    {
      this.password = password;
    }

    /**
     * Returns the user password
     * @return the user password
     */
    public String getPassword()
    {
      return password;
    }
    
    
    
    /**
     * Sets The flag denotes that the user is currently enabled.
     * @param enabled is The flag denotes that the user is currently enabled.
     */
    public void setEnabled(Boolean enabled)
    {
      this.enabled = enabled;
    }

    /**
     * Returns The flag denotes that the user is currently enabled.
     * @return The flag denotes that the user is currently enabled.
     */
    public Boolean getEnabled()
    {
      return enabled;
    }
    
    
    
    /**
     * Sets The flag denotes that the user is currently enabled. that has been chosen by the user
     * @param userRoles is The flag denotes that the user is currently enabled.
     */
    public void setUserRoles(Set<Long> userRoles)
    {
      this.userRoles = userRoles;
    }

    /**
     * Returns The flag denotes that the user is currently enabled. that has been chosen by the user or saved in the database
     * @return The flag denotes that the user is currently enabled.
     */
    public Set<Long> getUserRoles()
    {
      return userRoles;
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
	public ApplicationUserDetails getApplicationUserDetails() {
		return this.applicationUserDetails;
	}

	

	@Override
	public void setApplicationUserDetails(ApplicationUserDetails userDetails) {
		this.applicationUserDetails = userDetails;
	}

	@Override
	public String toString() {
		return "ApplicationUserImpl [id=" + id + ", parentObjectId=" + parentObjectId + ", currentMode=" + currentMode
				+ ", username=" + username + ", password=" + password + ", enabled=" + enabled + ", userRoles="
				+ userRoles + ", createdByID=" + createdByID + ", updatedByID=" + updatedByID + ", dateCreated="
				+ dateCreated + ", dateUpdated=" + dateUpdated + "]";
	}

	
	
}
    
    
    

