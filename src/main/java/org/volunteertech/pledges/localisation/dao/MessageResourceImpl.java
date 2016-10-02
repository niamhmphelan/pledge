package org.volunteertech.pledges.localisation.dao;

import org.volunteertech.pledges.localisation.dao.MessageResource;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


import org.volunteertech.pledges.admin.dao.View;




/**
 * The model to hold localisation messages
 * This class has been generated by the XSLT processor from the metadata and represents the
 * DataBase access handler for the MessageResource entity.
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
public class MessageResourceImpl implements MessageResource, Serializable { 

    private static final long serialVersionUID = 1L;
    
	/** Entity Id */
	private Long id;
	
    /** id of a parent object used when adding objects via JSON enabled restful web services */
    private Long parentObjectId;
    
    /**
     * Used to store the current processing mode during which the object was loaded
     */
    private CurrentMode currentMode;

    
    /** the message key used to lookup the message */
    private String messageKey;
	
    /** the Locale */
    private String locale;
	
    /** the Locale */
    private Long localeReferenceId;
      
    /** the Message in this locale */
    private String message;
	
	/** the reference to the foreign associated View **/
	@JsonBackReference(value="view-messageresource")
	private View view;
  

    /** The user ID of the person that originally created the underlying record **/
    private Long createdByID;

    /** The user ID of the person that last updated the underlying record **/
    private Long updatedByID;

    /** The date that the underlying record was created DD/MM/YYYY-HH:MM format **/
    private Date dateCreated;

    /** The date that the underlying record was last updated DD/MM/YYYY-HH:MM format **/
    private Date dateUpdated;
    

    /**
     * Default Constructor for the MessageResource bean
     */
    public MessageResourceImpl()
    {
    	
		this.messageKey = "";
		
		this.locale = "";
		
		this.localeReferenceId = new Long("0");
		
		this.message = "";
		
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
     * Sets the message key used to lookup the message
     * @param messageKey is the message key used to lookup the message
     */
    public void setMessageKey(String messageKey)
    {
      this.messageKey = messageKey;
    }

    /**
     * Returns the message key used to lookup the message
     * @return the message key used to lookup the message
     */
    public String getMessageKey()
    {
      return messageKey;
    }
    
    
    
    /**
     * Sets the Locale
     * @param locale is the Locale
     */
    public void setLocale(String locale)
    {
      this.locale = locale;
    }

    /**
     * Returns the Locale
     * @return the Locale
     */
    public String getLocale()
    {
      return locale;
    }
    
    
    
    /**
     * Sets the Locale
     * @param localeReferenceId is the Locale
     */
    public void setLocaleReferenceId(Long localeReferenceId)
    {
      this.localeReferenceId = localeReferenceId;
    }

    /**
     * Returns the Locale
     * @return the Locale
     */
    public Long getLocaleReferenceId()
    {
      return localeReferenceId;
    }
    
    
    
    /**
     * Sets the Message in this locale
     * @param message is the Message in this locale
     */
    public void setMessage(String message)
    {
      this.message = message;
    }

    /**
     * Returns the Message in this locale
     * @return the Message in this locale
     */
    public String getMessage()
    {
      return message;
    }
    
    
    /**
     * Sets the associated View mapped as the view property
     * @param the associated View mapped as the view property
     */
    public void setView(View view)
    {
      this.view = view;
    }

    /**
     * Returns the associated View mapped as the view property
     * @return the associated View mapped as the view property
     */
    public View getView()
    {
      return view;
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
    	return "MessageResource [id=" + id + ", " + "messageKey=" + messageKey + ", " + "locale=" + locale + ", " + "localeReferenceId=" + localeReferenceId + ", " + "message=" + message + "]";
     }
}
    
    
    

