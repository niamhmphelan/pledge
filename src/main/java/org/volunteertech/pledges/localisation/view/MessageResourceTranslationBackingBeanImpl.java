package org.volunteertech.pledges.localisation.view;

import org.volunteertech.pledges.localisation.dao.MessageResourceImpl;
import java.io.Serializable;


import org.volunteertech.pledges.admin.dao.View;




/**
 * The model to hold localisation messages
 * The MessageResourceTranslationBacking Bean supports the localisation of the MessageResource Screen
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
public class MessageResourceTranslationBackingBeanImpl extends MessageResourceImpl implements MessageResourceTranslationBackingBean, Serializable { 

    private static final long serialVersionUID = 1L;
    
    /** The identifier for the Messages view */
    public static final Long VIEW_ID = new Long(10);
    
    /** The mapping key for the page title as displayed in the browser tab */
    public static String MESSAGERESOURCE_TITLE_MAPPING_KEY = "messageresource.form.title";
    
    /** The mapping key for the page header text as displayed at the top of the page */
    public static String MESSAGERESOURCE_HEADER_MAPPING_KEY = "messageresource.form.header";
    
    /** The mapping key for the form sub header */
    public static String MESSAGERESOURCE_SUBHEADER_MAPPING_KEY = "messageresource.form.subheader";
    
    
	/** The message resource mapping for the Label attached to the messageKey property */  
	public static final String MESSAGEKEY_LABEL_MAPPING_KEY = "frmMessageResourceMessageKeyLabel";
	
	/** The message resource mapping for the place holder attached to the messageKey property */  
	public static final String MESSAGEKEY_PLACEHOLDER_MAPPING_KEY = "frmMessageResourceMessageKeyPlaceHolder";

	/** The message resource mapping for the help text attached to the messageKey property */  
	public static final String MESSAGEKEY_HELPBLOCK_MAPPING_KEY = "frmMessageResourceMessageKeyHelpBlock";

	/** The message resource mapping for the Label attached to the locale property */  
	public static final String LOCALE_LABEL_MAPPING_KEY = "frmMessageResourceLocaleLabel";
	
	/** The message resource mapping for the place holder attached to the locale property */  
	public static final String LOCALE_PLACEHOLDER_MAPPING_KEY = "frmMessageResourceLocalePlaceHolder";

	/** The message resource mapping for the help text attached to the locale property */  
	public static final String LOCALE_HELPBLOCK_MAPPING_KEY = "frmMessageResourceLocaleHelpBlock";

	/** The message resource mapping for the Label attached to the localeReferenceId property */  
	public static final String LOCALEREFERENCEID_LABEL_MAPPING_KEY = "frmMessageResourceLocaleReferenceIdLabel";
	
	/** The message resource mapping for the place holder attached to the localeReferenceId property */  
	public static final String LOCALEREFERENCEID_PLACEHOLDER_MAPPING_KEY = "frmMessageResourceLocaleReferenceIdPlaceHolder";

	/** The message resource mapping for the help text attached to the localeReferenceId property */  
	public static final String LOCALEREFERENCEID_HELPBLOCK_MAPPING_KEY = "frmMessageResourceLocaleReferenceIdHelpBlock";

	/** The message resource mapping for the Label attached to the message property */  
	public static final String MESSAGE_LABEL_MAPPING_KEY = "frmMessageResourceMessageLabel";
	
	/** The message resource mapping for the place holder attached to the message property */  
	public static final String MESSAGE_PLACEHOLDER_MAPPING_KEY = "frmMessageResourceMessagePlaceHolder";

	/** The message resource mapping for the help text attached to the message property */  
	public static final String MESSAGE_HELPBLOCK_MAPPING_KEY = "frmMessageResourceMessageHelpBlock";

	/** The message resource mapping for the Label attached to the saveButton property */  
	public static final String SAVEBUTTON_LABEL_MAPPING_KEY = "frmMessageResourceSaveButtonLabel";
	
	/** The message resource mapping for the place holder attached to the saveButton property */  
	public static final String SAVEBUTTON_PLACEHOLDER_MAPPING_KEY = "frmMessageResourceSaveButtonPlaceHolder";

	/** The message resource mapping for the help text attached to the saveButton property */  
	public static final String SAVEBUTTON_HELPBLOCK_MAPPING_KEY = "frmMessageResourceSaveButtonHelpBlock";

    
	/** Entity Id */
	private Long id;

	/** localization messages are bound to a particular view allowing the messages for a distinct view to be loaded*/
	private Long viewId;

	
    /** id of a parent object used when adding objects via JSON enabled restful web services */
    private Long parentObjectId;
    
    
    /** the the newLocale setting which is the locale currently being updated */
    private Long newLocale;

    /** the title as displayed in the browser tab */
    private String formTitle;

    /** the page title as displayed at the top of the page */
    private String viewTitle;
    
    /** the form sub header as displayed below the form header */
	private String formSubHeader;
    
    
    
	/** the reference to the foreign associated View **/
	private View view;
    
    private String messageKeyLabelMessageKey;
    private String messageKeyLabel;

    private String messageKeyPlaceHolderMessageKey;
    private String messageKeyPlaceHolder;
    
    private String messageKeyHelpTextMessageKey;
    private String messageKeyHelpText;
    
    private String messageKeyTooltipMessageKey;
    private String messageKeyTooltip;
    
    private String localeLabelMessageKey;
    private String localeLabel;

    private String localePlaceHolderMessageKey;
    private String localePlaceHolder;
    
    private String localeHelpTextMessageKey;
    private String localeHelpText;
    
    private String localeTooltipMessageKey;
    private String localeTooltip;
    
    private String localeReferenceIdLabelMessageKey;
    private String localeReferenceIdLabel;

    private String localeReferenceIdPlaceHolderMessageKey;
    private String localeReferenceIdPlaceHolder;
    
    private String localeReferenceIdHelpTextMessageKey;
    private String localeReferenceIdHelpText;
    
    private String localeReferenceIdTooltipMessageKey;
    private String localeReferenceIdTooltip;
    
    private String messageLabelMessageKey;
    private String messageLabel;

    private String messagePlaceHolderMessageKey;
    private String messagePlaceHolder;
    
    private String messageHelpTextMessageKey;
    private String messageHelpText;
    
    private String messageTooltipMessageKey;
    private String messageTooltip;
    
    private String saveButtonLabelMessageKey;
    private String saveButtonLabel;

    private String saveButtonPlaceHolderMessageKey;
    private String saveButtonPlaceHolder;
    
    private String saveButtonHelpTextMessageKey;
    private String saveButtonHelpText;
    
    private String saveButtonTooltipMessageKey;
    private String saveButtonTooltip;

    
    /**
     * Default Constructor for the MessageResourceTranslationBackingBean
     */
    public MessageResourceTranslationBackingBeanImpl()
    {
    	setViewId(MessageResourceTranslationBackingBeanImpl.VIEW_ID);
		messageKeyLabelMessageKey = "";
		messageKeyLabel = "";
		messageKeyPlaceHolderMessageKey = "";
		messageKeyPlaceHolder = "";
		messageKeyHelpTextMessageKey = "";
		messageKeyHelpText = "";
		messageKeyTooltipMessageKey = "";
		messageKeyTooltip = "";
		localeLabelMessageKey = "";
		localeLabel = "";
		localePlaceHolderMessageKey = "";
		localePlaceHolder = "";
		localeHelpTextMessageKey = "";
		localeHelpText = "";
		localeTooltipMessageKey = "";
		localeTooltip = "";
		localeReferenceIdLabelMessageKey = "";
		localeReferenceIdLabel = "";
		localeReferenceIdPlaceHolderMessageKey = "";
		localeReferenceIdPlaceHolder = "";
		localeReferenceIdHelpTextMessageKey = "";
		localeReferenceIdHelpText = "";
		localeReferenceIdTooltipMessageKey = "";
		localeReferenceIdTooltip = "";
		messageLabelMessageKey = "";
		messageLabel = "";
		messagePlaceHolderMessageKey = "";
		messagePlaceHolder = "";
		messageHelpTextMessageKey = "";
		messageHelpText = "";
		messageTooltipMessageKey = "";
		messageTooltip = "";
		saveButtonLabelMessageKey = "";
		saveButtonLabel = "";
		saveButtonPlaceHolderMessageKey = "";
		saveButtonPlaceHolder = "";
		saveButtonHelpTextMessageKey = "";
		saveButtonHelpText = "";
		saveButtonTooltipMessageKey = "";
		saveButtonTooltip = "";

    }
    
    /*
     * Returns the Id for the underlying database table record
     */
	public Long getId()
	{
        return id;
    }

    /*
     * Sets the Id. This allows front-end forms to bind the id property
     */
	public void setId(Long id)
	{
        this.id = id;
    }
    
    /*
     * Returns the viewId to which the current translations are bound
     */
	public Long getViewId()
	{
        return viewId;
    }

    /*
     * Sets the viewId. This allows the page translations to be bound to a view
     */
	public void setViewId(Long viewId)
	{
        this.viewId = viewId;
    }
    

    /*
     * Returns the parentObjectId when this object has been populated via a restful webservice JSON mapping
     */
	public Long getParentObjectId()
	{
        return parentObjectId;
    }

    /*
     * Sets the parentObjectId. This allows restful web services to bind the parentId property when adding a new record.
     */
	public void setParentObjectId(Long parentObjectId)
	{
        this.parentObjectId = parentObjectId;
    }
    
    /*
     * Returns the newLocale setting which is the locale currently being updated
     */
	public Long getNewLocale()
	{
        return newLocale;
    }

    /*
     * Sets the the newLocale setting which is the locale currently being updated
     */
	public void setNewLocale(Long newLocale)
	{
        this.newLocale = newLocale;
    }

    /*
     * Returns the page title as displayed in the browser tab
     */
	public String getFormTitle()
	{
        return formTitle;
    }

    /*
     * Sets the page title as displayed in the browser tab
     */
	public void setFormTitle(String formTitle)
	{
        this.formTitle = formTitle;
    }
    
    
    /*
     * Returns the page title as displayed at the top of the page
     */
	public String getViewTitle()
	{
        return viewTitle;
    }

    /*
     * Sets the page title as displayed at the top of the page
     */
	public void setViewTitle(String viewTitle)
	{
        this.viewTitle = viewTitle;
    }
    

    /*
     * Returns the form sub header as displayed below the form header
     */
	public String getFormSubHeader()
	{
        return formSubHeader;
    }

    /*
     * Sets the form sub header as displayed below the form header
     */
	public void setFormSubHeader(String formSubHeader)
	{
        this.formSubHeader = formSubHeader;
    }
    

	
    
    
    /**
     * Sets the messageKeyLabelMessageKey
     * @param messageKeyLabelMessageKey
     */
    public void setMessageKeyLabelMessageKey(String messageKeyLabelMessageKey)
    {
      this.messageKeyLabelMessageKey = messageKeyLabelMessageKey;
    }

    /**
     * Returns the messageKeyLabelMessageKey
     * @return the messageKeyLabelMessageKey
     */
    public String getMessageKeyLabelMessageKey()
    {
      return messageKeyLabelMessageKey;
    }

    /**
     * Sets the messageKeyLabel
     * @param messageKeyLabel
     */
    public void setMessageKeyLabel(String messageKeyLabel)
    {
      this.messageKeyLabel = messageKeyLabel;
    }

    /**
     * Returns the messageKeyLabel
     * @return the messageKeyLabel
     */
    public String getMessageKeyLabel()
    {
      return messageKeyLabel;
    }
    
    
    /**
     * Sets the messageKeyPlaceHolderMessageKey
     * @param messageKeyPlaceHolderMessageKey
     */
    public void setMessageKeyPlaceHolderMessageKey(String messageKeyPlaceHolderMessageKey)
    {
      this.messageKeyPlaceHolderMessageKey = messageKeyPlaceHolderMessageKey;
    }

    /**
     * Returns the messageKeyPlaceHolderMessageKey
     * @return the messageKeyPlaceHolderMessageKey
     */
    public String getMessageKeyPlaceHolderMessageKey()
    {
      return messageKeyPlaceHolderMessageKey;
    }

    /**
     * Sets the messageKeyPlaceHolder
     * @param messageKeyPlaceHolder
     */
    public void setMessageKeyPlaceHolder(String messageKeyPlaceHolder)
    {
      this.messageKeyPlaceHolder = messageKeyPlaceHolder;
    }

    /**
     * Returns the messageKeyPlaceHolder
     * @return the messageKeyPlaceHolder
     */
    public String getMessageKeyPlaceHolder()
    {
      return messageKeyPlaceHolder;
    }    
    
    /**
     * Sets the messageKeyHelpTextMessageKey
     * @param messageKeyHelpTextMessageKey
     */
    public void setMessageKeyHelpTextMessageKey(String messageKeyHelpTextMessageKey)
    {
      this.messageKeyHelpTextMessageKey = messageKeyHelpTextMessageKey;
    }

    /**
     * Returns the messageKeyHelpTextMessageKey
     * @return the messageKeyHelpTextMessageKey
     */
    public String getMessageKeyHelpTextMessageKey()
    {
      return messageKeyHelpTextMessageKey;
    }

    /**
     * Sets the messageKeyHelpText
     * @param messageKeyHelpText
     */
    public void setMessageKeyHelpText(String messageKeyHelpText)
    {
      this.messageKeyHelpText = messageKeyHelpText;
    }

    /**
     * Returns the messageKeyHelpText
     * @return the messageKeyHelpText
     */
    public String getMessageKeyHelpText()
    {
      return messageKeyHelpText;
    }    
    
    /**
     * Sets the messageKeyTooltipMessageKey
     * @param messageKeyTooltipMessageKey
     */
    public void setMessageKeyTooltipMessageKey(String messageKeyTooltipMessageKey)
    {
      this.messageKeyTooltipMessageKey = messageKeyTooltipMessageKey;
    }

    /**
     * Returns the messageKeyTooltipMessageKey
     * @return the messageKeyTooltipMessageKey
     */
    public String getMessageKeyTooltipMessageKey()
    {
      return messageKeyTooltipMessageKey;
    }

    /**
     * Sets the messageKeyTooltip
     * @param messageKeyTooltip
     */
    public void setMessageKeyTooltip(String messageKeyTooltip)
    {
      this.messageKeyTooltip = messageKeyTooltip;
    }

    /**
     * Returns the messageKeyTooltip
     * @return the messageKeyTooltip
     */
    public String getMessageKeyTooltip()
    {
      return messageKeyTooltip;
    }    

    
    /**
     * Sets the localeLabelMessageKey
     * @param localeLabelMessageKey
     */
    public void setLocaleLabelMessageKey(String localeLabelMessageKey)
    {
      this.localeLabelMessageKey = localeLabelMessageKey;
    }

    /**
     * Returns the localeLabelMessageKey
     * @return the localeLabelMessageKey
     */
    public String getLocaleLabelMessageKey()
    {
      return localeLabelMessageKey;
    }

    /**
     * Sets the localeLabel
     * @param localeLabel
     */
    public void setLocaleLabel(String localeLabel)
    {
      this.localeLabel = localeLabel;
    }

    /**
     * Returns the localeLabel
     * @return the localeLabel
     */
    public String getLocaleLabel()
    {
      return localeLabel;
    }
    
    
    /**
     * Sets the localePlaceHolderMessageKey
     * @param localePlaceHolderMessageKey
     */
    public void setLocalePlaceHolderMessageKey(String localePlaceHolderMessageKey)
    {
      this.localePlaceHolderMessageKey = localePlaceHolderMessageKey;
    }

    /**
     * Returns the localePlaceHolderMessageKey
     * @return the localePlaceHolderMessageKey
     */
    public String getLocalePlaceHolderMessageKey()
    {
      return localePlaceHolderMessageKey;
    }

    /**
     * Sets the localePlaceHolder
     * @param localePlaceHolder
     */
    public void setLocalePlaceHolder(String localePlaceHolder)
    {
      this.localePlaceHolder = localePlaceHolder;
    }

    /**
     * Returns the localePlaceHolder
     * @return the localePlaceHolder
     */
    public String getLocalePlaceHolder()
    {
      return localePlaceHolder;
    }    
    
    /**
     * Sets the localeHelpTextMessageKey
     * @param localeHelpTextMessageKey
     */
    public void setLocaleHelpTextMessageKey(String localeHelpTextMessageKey)
    {
      this.localeHelpTextMessageKey = localeHelpTextMessageKey;
    }

    /**
     * Returns the localeHelpTextMessageKey
     * @return the localeHelpTextMessageKey
     */
    public String getLocaleHelpTextMessageKey()
    {
      return localeHelpTextMessageKey;
    }

    /**
     * Sets the localeHelpText
     * @param localeHelpText
     */
    public void setLocaleHelpText(String localeHelpText)
    {
      this.localeHelpText = localeHelpText;
    }

    /**
     * Returns the localeHelpText
     * @return the localeHelpText
     */
    public String getLocaleHelpText()
    {
      return localeHelpText;
    }    
    
    /**
     * Sets the localeTooltipMessageKey
     * @param localeTooltipMessageKey
     */
    public void setLocaleTooltipMessageKey(String localeTooltipMessageKey)
    {
      this.localeTooltipMessageKey = localeTooltipMessageKey;
    }

    /**
     * Returns the localeTooltipMessageKey
     * @return the localeTooltipMessageKey
     */
    public String getLocaleTooltipMessageKey()
    {
      return localeTooltipMessageKey;
    }

    /**
     * Sets the localeTooltip
     * @param localeTooltip
     */
    public void setLocaleTooltip(String localeTooltip)
    {
      this.localeTooltip = localeTooltip;
    }

    /**
     * Returns the localeTooltip
     * @return the localeTooltip
     */
    public String getLocaleTooltip()
    {
      return localeTooltip;
    }    

    
    /**
     * Sets the localeReferenceIdLabelMessageKey
     * @param localeReferenceIdLabelMessageKey
     */
    public void setLocaleReferenceIdLabelMessageKey(String localeReferenceIdLabelMessageKey)
    {
      this.localeReferenceIdLabelMessageKey = localeReferenceIdLabelMessageKey;
    }

    /**
     * Returns the localeReferenceIdLabelMessageKey
     * @return the localeReferenceIdLabelMessageKey
     */
    public String getLocaleReferenceIdLabelMessageKey()
    {
      return localeReferenceIdLabelMessageKey;
    }

    /**
     * Sets the localeReferenceIdLabel
     * @param localeReferenceIdLabel
     */
    public void setLocaleReferenceIdLabel(String localeReferenceIdLabel)
    {
      this.localeReferenceIdLabel = localeReferenceIdLabel;
    }

    /**
     * Returns the localeReferenceIdLabel
     * @return the localeReferenceIdLabel
     */
    public String getLocaleReferenceIdLabel()
    {
      return localeReferenceIdLabel;
    }
    
    
    /**
     * Sets the localeReferenceIdPlaceHolderMessageKey
     * @param localeReferenceIdPlaceHolderMessageKey
     */
    public void setLocaleReferenceIdPlaceHolderMessageKey(String localeReferenceIdPlaceHolderMessageKey)
    {
      this.localeReferenceIdPlaceHolderMessageKey = localeReferenceIdPlaceHolderMessageKey;
    }

    /**
     * Returns the localeReferenceIdPlaceHolderMessageKey
     * @return the localeReferenceIdPlaceHolderMessageKey
     */
    public String getLocaleReferenceIdPlaceHolderMessageKey()
    {
      return localeReferenceIdPlaceHolderMessageKey;
    }

    /**
     * Sets the localeReferenceIdPlaceHolder
     * @param localeReferenceIdPlaceHolder
     */
    public void setLocaleReferenceIdPlaceHolder(String localeReferenceIdPlaceHolder)
    {
      this.localeReferenceIdPlaceHolder = localeReferenceIdPlaceHolder;
    }

    /**
     * Returns the localeReferenceIdPlaceHolder
     * @return the localeReferenceIdPlaceHolder
     */
    public String getLocaleReferenceIdPlaceHolder()
    {
      return localeReferenceIdPlaceHolder;
    }    
    
    /**
     * Sets the localeReferenceIdHelpTextMessageKey
     * @param localeReferenceIdHelpTextMessageKey
     */
    public void setLocaleReferenceIdHelpTextMessageKey(String localeReferenceIdHelpTextMessageKey)
    {
      this.localeReferenceIdHelpTextMessageKey = localeReferenceIdHelpTextMessageKey;
    }

    /**
     * Returns the localeReferenceIdHelpTextMessageKey
     * @return the localeReferenceIdHelpTextMessageKey
     */
    public String getLocaleReferenceIdHelpTextMessageKey()
    {
      return localeReferenceIdHelpTextMessageKey;
    }

    /**
     * Sets the localeReferenceIdHelpText
     * @param localeReferenceIdHelpText
     */
    public void setLocaleReferenceIdHelpText(String localeReferenceIdHelpText)
    {
      this.localeReferenceIdHelpText = localeReferenceIdHelpText;
    }

    /**
     * Returns the localeReferenceIdHelpText
     * @return the localeReferenceIdHelpText
     */
    public String getLocaleReferenceIdHelpText()
    {
      return localeReferenceIdHelpText;
    }    
    
    /**
     * Sets the localeReferenceIdTooltipMessageKey
     * @param localeReferenceIdTooltipMessageKey
     */
    public void setLocaleReferenceIdTooltipMessageKey(String localeReferenceIdTooltipMessageKey)
    {
      this.localeReferenceIdTooltipMessageKey = localeReferenceIdTooltipMessageKey;
    }

    /**
     * Returns the localeReferenceIdTooltipMessageKey
     * @return the localeReferenceIdTooltipMessageKey
     */
    public String getLocaleReferenceIdTooltipMessageKey()
    {
      return localeReferenceIdTooltipMessageKey;
    }

    /**
     * Sets the localeReferenceIdTooltip
     * @param localeReferenceIdTooltip
     */
    public void setLocaleReferenceIdTooltip(String localeReferenceIdTooltip)
    {
      this.localeReferenceIdTooltip = localeReferenceIdTooltip;
    }

    /**
     * Returns the localeReferenceIdTooltip
     * @return the localeReferenceIdTooltip
     */
    public String getLocaleReferenceIdTooltip()
    {
      return localeReferenceIdTooltip;
    }    

    
    /**
     * Sets the messageLabelMessageKey
     * @param messageLabelMessageKey
     */
    public void setMessageLabelMessageKey(String messageLabelMessageKey)
    {
      this.messageLabelMessageKey = messageLabelMessageKey;
    }

    /**
     * Returns the messageLabelMessageKey
     * @return the messageLabelMessageKey
     */
    public String getMessageLabelMessageKey()
    {
      return messageLabelMessageKey;
    }

    /**
     * Sets the messageLabel
     * @param messageLabel
     */
    public void setMessageLabel(String messageLabel)
    {
      this.messageLabel = messageLabel;
    }

    /**
     * Returns the messageLabel
     * @return the messageLabel
     */
    public String getMessageLabel()
    {
      return messageLabel;
    }
    
    
    /**
     * Sets the messagePlaceHolderMessageKey
     * @param messagePlaceHolderMessageKey
     */
    public void setMessagePlaceHolderMessageKey(String messagePlaceHolderMessageKey)
    {
      this.messagePlaceHolderMessageKey = messagePlaceHolderMessageKey;
    }

    /**
     * Returns the messagePlaceHolderMessageKey
     * @return the messagePlaceHolderMessageKey
     */
    public String getMessagePlaceHolderMessageKey()
    {
      return messagePlaceHolderMessageKey;
    }

    /**
     * Sets the messagePlaceHolder
     * @param messagePlaceHolder
     */
    public void setMessagePlaceHolder(String messagePlaceHolder)
    {
      this.messagePlaceHolder = messagePlaceHolder;
    }

    /**
     * Returns the messagePlaceHolder
     * @return the messagePlaceHolder
     */
    public String getMessagePlaceHolder()
    {
      return messagePlaceHolder;
    }    
    
    /**
     * Sets the messageHelpTextMessageKey
     * @param messageHelpTextMessageKey
     */
    public void setMessageHelpTextMessageKey(String messageHelpTextMessageKey)
    {
      this.messageHelpTextMessageKey = messageHelpTextMessageKey;
    }

    /**
     * Returns the messageHelpTextMessageKey
     * @return the messageHelpTextMessageKey
     */
    public String getMessageHelpTextMessageKey()
    {
      return messageHelpTextMessageKey;
    }

    /**
     * Sets the messageHelpText
     * @param messageHelpText
     */
    public void setMessageHelpText(String messageHelpText)
    {
      this.messageHelpText = messageHelpText;
    }

    /**
     * Returns the messageHelpText
     * @return the messageHelpText
     */
    public String getMessageHelpText()
    {
      return messageHelpText;
    }    
    
    /**
     * Sets the messageTooltipMessageKey
     * @param messageTooltipMessageKey
     */
    public void setMessageTooltipMessageKey(String messageTooltipMessageKey)
    {
      this.messageTooltipMessageKey = messageTooltipMessageKey;
    }

    /**
     * Returns the messageTooltipMessageKey
     * @return the messageTooltipMessageKey
     */
    public String getMessageTooltipMessageKey()
    {
      return messageTooltipMessageKey;
    }

    /**
     * Sets the messageTooltip
     * @param messageTooltip
     */
    public void setMessageTooltip(String messageTooltip)
    {
      this.messageTooltip = messageTooltip;
    }

    /**
     * Returns the messageTooltip
     * @return the messageTooltip
     */
    public String getMessageTooltip()
    {
      return messageTooltip;
    }    

    
    /**
     * Sets the saveButtonLabelMessageKey
     * @param saveButtonLabelMessageKey
     */
    public void setSaveButtonLabelMessageKey(String saveButtonLabelMessageKey)
    {
      this.saveButtonLabelMessageKey = saveButtonLabelMessageKey;
    }

    /**
     * Returns the saveButtonLabelMessageKey
     * @return the saveButtonLabelMessageKey
     */
    public String getSaveButtonLabelMessageKey()
    {
      return saveButtonLabelMessageKey;
    }

    /**
     * Sets the saveButtonLabel
     * @param saveButtonLabel
     */
    public void setSaveButtonLabel(String saveButtonLabel)
    {
      this.saveButtonLabel = saveButtonLabel;
    }

    /**
     * Returns the saveButtonLabel
     * @return the saveButtonLabel
     */
    public String getSaveButtonLabel()
    {
      return saveButtonLabel;
    }
    
    
    /**
     * Sets the saveButtonPlaceHolderMessageKey
     * @param saveButtonPlaceHolderMessageKey
     */
    public void setSaveButtonPlaceHolderMessageKey(String saveButtonPlaceHolderMessageKey)
    {
      this.saveButtonPlaceHolderMessageKey = saveButtonPlaceHolderMessageKey;
    }

    /**
     * Returns the saveButtonPlaceHolderMessageKey
     * @return the saveButtonPlaceHolderMessageKey
     */
    public String getSaveButtonPlaceHolderMessageKey()
    {
      return saveButtonPlaceHolderMessageKey;
    }

    /**
     * Sets the saveButtonPlaceHolder
     * @param saveButtonPlaceHolder
     */
    public void setSaveButtonPlaceHolder(String saveButtonPlaceHolder)
    {
      this.saveButtonPlaceHolder = saveButtonPlaceHolder;
    }

    /**
     * Returns the saveButtonPlaceHolder
     * @return the saveButtonPlaceHolder
     */
    public String getSaveButtonPlaceHolder()
    {
      return saveButtonPlaceHolder;
    }    
    
    /**
     * Sets the saveButtonHelpTextMessageKey
     * @param saveButtonHelpTextMessageKey
     */
    public void setSaveButtonHelpTextMessageKey(String saveButtonHelpTextMessageKey)
    {
      this.saveButtonHelpTextMessageKey = saveButtonHelpTextMessageKey;
    }

    /**
     * Returns the saveButtonHelpTextMessageKey
     * @return the saveButtonHelpTextMessageKey
     */
    public String getSaveButtonHelpTextMessageKey()
    {
      return saveButtonHelpTextMessageKey;
    }

    /**
     * Sets the saveButtonHelpText
     * @param saveButtonHelpText
     */
    public void setSaveButtonHelpText(String saveButtonHelpText)
    {
      this.saveButtonHelpText = saveButtonHelpText;
    }

    /**
     * Returns the saveButtonHelpText
     * @return the saveButtonHelpText
     */
    public String getSaveButtonHelpText()
    {
      return saveButtonHelpText;
    }    
    
    /**
     * Sets the saveButtonTooltipMessageKey
     * @param saveButtonTooltipMessageKey
     */
    public void setSaveButtonTooltipMessageKey(String saveButtonTooltipMessageKey)
    {
      this.saveButtonTooltipMessageKey = saveButtonTooltipMessageKey;
    }

    /**
     * Returns the saveButtonTooltipMessageKey
     * @return the saveButtonTooltipMessageKey
     */
    public String getSaveButtonTooltipMessageKey()
    {
      return saveButtonTooltipMessageKey;
    }

    /**
     * Sets the saveButtonTooltip
     * @param saveButtonTooltip
     */
    public void setSaveButtonTooltip(String saveButtonTooltip)
    {
      this.saveButtonTooltip = saveButtonTooltip;
    }

    /**
     * Returns the saveButtonTooltip
     * @return the saveButtonTooltip
     */
    public String getSaveButtonTooltip()
    {
      return saveButtonTooltip;
    }    


    /**
     * Checks for a newly created entity object
     * @return true if this is a newly created entity object meaning that it has not yet been persisted, otherwise false
     */
	public boolean isNew() {
		return (this.id == null);
	}
	
    
    
/*     @Override
     public String toString(){
     // TODO: To be implemented
    	return "TODO: to be done";
     }
*/     
}
    
    
    

