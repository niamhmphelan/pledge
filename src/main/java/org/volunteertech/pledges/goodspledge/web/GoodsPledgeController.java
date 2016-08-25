package org.volunteertech.pledges.goodspledge.web;
import java.util.ArrayList;
import java.util.Arrays; 
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.volunteertech.pledges.users.security.SecurityUser;

import org.volunteertech.pledges.main.localisation.DatabaseDrivenMessageSource;
import org.volunteertech.pledges.goodspledge.dao.GoodsPledge;
import org.volunteertech.pledges.goodspledge.dao.GoodsPledgeImpl;
import org.volunteertech.pledges.goodspledge.service.GoodsPledgeService;
import org.volunteertech.pledges.goodspledge.dao.GoodsPledgeLoadException;
import org.volunteertech.pledges.goodspledge.dao.GoodsPledgeSaveException;
import org.volunteertech.pledges.goodspledge.validator.GoodsPledgeFormValidator;
import org.volunteertech.pledges.goodspledge.view.GoodsPledgeTranslationBackingBean;
import org.volunteertech.pledges.goodspledge.view.GoodsPledgeTranslationBackingBeanImpl;
import org.volunteertech.pledges.main.web.BaseController;
import org.volunteertech.pledges.main.constants.Constants;
import org.volunteertech.pledges.localisation.dao.MessageResource;
import org.volunteertech.pledges.localisation.dao.MessageResourceImpl;
import org.volunteertech.pledges.localisation.service.MessageResourceService;

import org.volunteertech.pledges.pledge.dao.RegisterOfPledges;


import org.volunteertech.pledges.reference.ReferenceStore;

/**
 * The goods pledge screen is used to add/edit a new/existing pledge of services
 * GUIHandler is attached to a form on JSP and contains all getters and setters for values displayed on the form.
 * GUIHandler accesses the business layer for loading/saving data. 
 * This class has been generated by the XSLT processor from the metadata and represents the
 * the GUI layer javabean for the GoodsPledge Form.
 * <P> 
 * It is essential that methods added to this class are given JavaDoc comments to allow
 * documentation to be generated. For a description of JavaDoc refer to The JavaDoc documentation.
 * A link is provided below.
 * <P>
 * @author Michael O'Connor
 * @version $Revision$
 * Date: $Date$
 * @see <a href="http://java.sun.com/j2se/javadoc/writingdoccomments/index.html">>JavaDoc Documentation</a> 
 * Change Log
 * ----------
 * $Log$
 *
 */
@Controller
public class GoodsPledgeController extends BaseController
{

    /**
     * userId used for development. This should be taken from the session.
     */
	private Long userId = new Long(0);
	 
	final Logger logger = LoggerFactory.getLogger(GoodsPledgeController.class);
	
	@Autowired
	private ReferenceStore referenceStore;
	
	@Autowired
	private GoodsPledgeService goodsPledgeService;

	@Autowired
	private GoodsPledgeFormValidator goodsPledgeFormValidator;
	
    @Autowired
    private DatabaseDrivenMessageSource messageSource;
    


    @Autowired
    private MessageResourceService messageResourceService;
  
    
	
	//Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(goodsPledgeFormValidator);
	}
	
	
	/**
	 * Open the list page
	 */
	@RequestMapping(value = "/goodspledge/all", method = RequestMethod.GET)
	public String showAllGoodsPledge(Model model, Locale locale) {

		logger.debug("showAllGoodsPledge()");
			
		return "goodspledge_table";

	}
	
	/**
	 * Open the localize page
	 */
	@RequestMapping(value = "/goodspledge/localize", method = RequestMethod.GET)
	public String localizeGoodsPledge(Model model, Locale locale) {

		logger.debug("localizeGoodsPledge()");

		GoodsPledgeTranslationBackingBean goodsPledgeTranslationBackingBean = new GoodsPledgeTranslationBackingBeanImpl();
		goodsPledgeTranslationBackingBean.setCurrentMode(GoodsPledge.CurrentMode.LOCALIZE);
		model.addAttribute("goodsPledgeTranslationFormModel", goodsPledgeTranslationBackingBean);
		Long defaultLocale = new Long(Constants.REFERENCE_LOCALE__EN);
		setTranslationDropDownContents(model, locale);
		setDropDownContents(model, null, locale);		
		model.addAttribute("defaultLocale", defaultLocale);
		
		return "goodspledge_localize";

	}
	
	
	

	// save or update GoodsPledge
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/goodspledge/post", method = RequestMethod.POST)
	public String saveOrUpdateGoodsPledge(Authentication authentication, @ModelAttribute("goodsPledgeFormModel") @Validated GoodsPledgeImpl goodsPledge,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateGoodsPledge() : {}", goodsPledge);
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		if (result.hasErrors()) {
			setDropDownContents(model, goodsPledge, locale);
			String updateIssueMessage = messageSource.getMessage("goodsPledgeUpdateIssueMessage", new String[0], locale);
			model.addAttribute("msg", updateIssueMessage);
			model.addAttribute("css", "alert-danger");
			
			return "goodspledge";
		} else {

			// Add message to flash scope
			redirectAttributes.addFlashAttribute("css", "success");
			if(goodsPledge.isNew()){
				String addedSuccessMessage = messageSource.getMessage("goodsPledgeAddedSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", addedSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}else{
				String updateSuccessMessage = messageSource.getMessage("goodsPledgeUpdateSuccessMessage", new String[0], locale);
				// Add message to flash scope
				redirectAttributes.addFlashAttribute("msg", updateSuccessMessage);
				redirectAttributes.addFlashAttribute("css", "alert-success");
			}


			try{
				// TODO: Needs exception handling policy
			    	goodsPledgeService.storeGoodsPledge(goodsPledge, userId);
			}
			catch (Exception ex){
				logger.error("Exception caught !!!!!!!!!!!!!!", ex);
			}
			
	
			

			
			// POST/REDIRECT/GET
			return "redirect:/goodspledge/" + goodsPledge.getId() + "/update";
		}

	}

	// show add user form
	@RequestMapping(value = "/goodspledge/add", method = RequestMethod.GET)
	public String showAddGoodsPledgeForm(Model model, Locale locale) {

		logger.debug("showAddGoodsPledgeForm()");

		GoodsPledge goodsPledge = new GoodsPledgeImpl();
		
		goodsPledge.setCurrentMode(GoodsPledge.CurrentMode.ADD);

		model.addAttribute("goodsPledgeFormModel", goodsPledge);

		setDropDownContents(model, goodsPledge, locale);

		return "goodspledge";

	}
	
	// support access to the supporting webpage by creating a new instance and returning 
	@RequestMapping(value = "/goodspledgewebpage", method = RequestMethod.GET)
	public String createGoodsPledgeForWebPageView(Model model, HttpServletRequest request, Locale locale) {

		logger.debug("createGoodsPledgeForWebPageView()");

		GoodsPledge goodsPledge = new GoodsPledgeImpl();
		
		try{
			// TODO: Needs exception handling policy
	    	goodsPledgeService.storeGoodsPledge(goodsPledge, userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
		

		model.addAttribute("goodsPledgeFormModel", goodsPledge);

		setDropDownContents(model, goodsPledge, locale);

		return "goodspledgewebpage";

	}
	

	// show update form
	@RequestMapping(value = "/goodspledge/{id}/update", method = RequestMethod.GET)
	public String showUpdateGoodsPledgeForm(@PathVariable("id") int id, Model model, Locale locale) {

		logger.debug("showUpdateGoodsPledgeForm() : {}", id);
		GoodsPledge goodsPledge = null;
		try{
			// TODO: Needs exception handling policy
			goodsPledge = goodsPledgeService.load(new Long(id), userId);
			goodsPledge.setCurrentMode(GoodsPledge.CurrentMode.UPDATE);
			this.goodsPledgeService.translateReferenceValues(goodsPledge, locale);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		model.addAttribute("goodsPledgeFormModel", goodsPledge);
		
		setDropDownContents(model, goodsPledge, locale);
		
		return "goodspledge";

	}

	// delete goodsPledge
	@RequestMapping(value = "/goodspledge/{id}/delete", method = RequestMethod.POST)
	public String deleteGoodsPledge(@PathVariable("id") int id, 
		final RedirectAttributes redirectAttributes) {

		logger.debug("deleteUser() : {}", id);

		//goodsPledgeService.delete(id);
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "User is deleted!");
		
		return "redirect:/goodspledge/all";

	}

	// show user
	@RequestMapping(value = "/goodspledge/{id}", method = RequestMethod.GET)
	public String showGoodsPledge(@PathVariable("id") int id, Model model, Locale locale) {

		logger.debug("showGoodsPledge() id: {}", id);
		GoodsPledge goodsPledge = null;
		try{
			// TODO: Needs exception handling policy
			goodsPledge = goodsPledgeService.load(new Long(id), userId);
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}

		
		if (goodsPledge == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "User not found");
		}
		model.addAttribute("goodsPledge", goodsPledge);
		
		setDropDownContents(model, goodsPledge, locale);

		return "showgoodspledge";

	}
	
	
	// save or update GoodsPledgeTranslation
	// 1. @ModelAttribute bind form value
	// 2. @Validated form validator
	// 3. RedirectAttributes for flash value
	@RequestMapping(value = "/goodspledge/translate", method = RequestMethod.POST)
	public String saveOrUpdateGoodsPledgeTranslation(Authentication authentication, @ModelAttribute("goodsPledgeTranslationFormModel") GoodsPledgeTranslationBackingBeanImpl goodsPledgeTranslationBackingBean,
			BindingResult result, Model model, Locale locale,
			final RedirectAttributes redirectAttributes) {

		logger.debug("saveOrUpdateGoodsPledgeTranslation() : {}", goodsPledgeTranslationBackingBean);
		Long translationLocaleReferenceId = goodsPledgeTranslationBackingBean.getNewLocale();
		String translationLocale = messageSource.getMessage(referenceStore.getRefDesc(translationLocaleReferenceId), new String[0], new Locale("en"));
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		

		try{
			
			// TODO: Needs exception handling policy
			List<MessageResource> messageResourceList  = new ArrayList<MessageResource>();
			
			MessageResource messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSPLEDGE_HEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getViewTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSPLEDGE_TITLE_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getFormTitle());
			messageResourceList.add(messageResource);
			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSPLEDGE_SUBHEADER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getFormSubHeader());
			messageResourceList.add(messageResource);
			
	messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSCATEGORYONE_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsCategoryOneLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSCATEGORYONE_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsCategoryOnePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSCATEGORYONE_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsCategoryOneHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSCATEGORYTWO_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsCategoryTwoLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSCATEGORYTWO_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsCategoryTwoPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSCATEGORYTWO_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsCategoryTwoHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSCATEGORYTHREE_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsCategoryThreeLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSCATEGORYTHREE_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsCategoryThreePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSCATEGORYTHREE_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsCategoryThreeHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSSIZE_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsSizeLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSSIZE_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsSizePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSSIZE_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsSizeHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSNEWORUSED_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsNewOrUsedLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSNEWORUSED_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsNewOrUsedPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSNEWORUSED_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsNewOrUsedHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSCONDITION_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsConditionLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSCONDITION_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsConditionPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSCONDITION_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsConditionHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSQUANTITY_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsQuantityLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSQUANTITY_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsQuantityPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.GOODSQUANTITY_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getGoodsQuantityHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.ADDITIONALINFORMATION_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getAdditionalInformationLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.ADDITIONALINFORMATION_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getAdditionalInformationPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.ADDITIONALINFORMATION_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getAdditionalInformationHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.ITEMSIZE_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getItemSizeLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.ITEMSIZE_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getItemSizePlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.ITEMSIZE_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getItemSizeHelpText());
  			messageResourceList.add(messageResource);
  		
	messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.SAVEBUTTON_LABEL_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getSaveButtonLabel());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.SAVEBUTTON_PLACEHOLDER_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getSaveButtonPlaceHolder());
  			messageResourceList.add(messageResource);
  			messageResource = populateMessageResource(GoodsPledgeTranslationBackingBeanImpl.SAVEBUTTON_HELPBLOCK_MAPPING_KEY, translationLocale, translationLocaleReferenceId, goodsPledgeTranslationBackingBean.getSaveButtonHelpText());
  			messageResourceList.add(messageResource);
  		

			

			this.messageSource.updateTexts(messageResourceList, userId);			
		}
		catch (Exception ex){
			logger.error("Exception caught !!!!!!!!!!!!!!", ex);
		}
			
		// POST/REDIRECT/GET
		return "redirect:/goodspledge/add?language=" + translationLocale;

	}
	
	/**
	 * Opens the details of the RegisterOfPledges that owns the GoodsPledge identified by the
	 * id given as a parameter.
	 * @param id the id of the GoodsPledge for which the RegisterOfPledges should be resolved.
	 */
	@RequestMapping(value = "/goodspledge/{id}/registerofpledges", method = RequestMethod.GET)
	public String showRegisterOfPledges(Authentication authentication, @PathVariable("id") int id, 
		final RedirectAttributes redirectAttributes) {
		String returnPath = null;

		logger.debug("showRegisterOfPledges() : {}", id);
		
		SecurityUser user = (SecurityUser)authentication.getPrincipal();
        Long userId = user.getApplicationUser().getId();
		//TODO: Needs exception handling
		
		List<RegisterOfPledges> registerOfPledgesList = this.goodsPledgeService.getGoodsPledgeBo().getGoodsPledgeDao().listRegisterOfPledgesByGoodsPledgeId(new Long(id), userId);

		if (registerOfPledgesList.size() == 1){
			returnPath = "forward:/registerofpledges/" + registerOfPledgesList.get(0).getId() + "/update";
		}
		else{
			returnPath = "forward:/registerofpledges/all";
		}
		
		return returnPath;

	}

	

	private void setDropDownContents(Model model, GoodsPledge goodsPledge, Locale locale) {
		
		Map<Long, String> goodsCategoryOneMap = referenceStore.getGoodsCategoryOne();
		SortedMap<Long, String> localizedgoodsCategoryOneMap = new TreeMap<Long, String>(goodsCategoryOneMap);
		for (Map.Entry<Long, String> entry : goodsCategoryOneMap.entrySet()) {
			localizedgoodsCategoryOneMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("goodsCategoryOneMap", localizedgoodsCategoryOneMap);
	      
		Map<Long, String> goodsCategoryTwoMap = referenceStore.getGoodsCategoryTwo();
		SortedMap<Long, String> localizedgoodsCategoryTwoMap = new TreeMap<Long, String>(goodsCategoryTwoMap);
		for (Map.Entry<Long, String> entry : goodsCategoryTwoMap.entrySet()) {
			localizedgoodsCategoryTwoMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("goodsCategoryTwoMap", localizedgoodsCategoryTwoMap);
	      
		Map<Long, String> goodsCategoryThreeMap = referenceStore.getGoodsCategoryThree();
		SortedMap<Long, String> localizedgoodsCategoryThreeMap = new TreeMap<Long, String>(goodsCategoryThreeMap);
		for (Map.Entry<Long, String> entry : goodsCategoryThreeMap.entrySet()) {
			localizedgoodsCategoryThreeMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("goodsCategoryThreeMap", localizedgoodsCategoryThreeMap);
	      
		Map<Long, String> goodsSizeMap = referenceStore.getGoodsSize();
		SortedMap<Long, String> localizedgoodsSizeMap = new TreeMap<Long, String>(goodsSizeMap);
		for (Map.Entry<Long, String> entry : goodsSizeMap.entrySet()) {
			localizedgoodsSizeMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("goodsSizeMap", localizedgoodsSizeMap);
	      
		Map<Long, String> goodsNewOrUsedMap = referenceStore.getNewOrUsed();
		SortedMap<Long, String> localizedgoodsNewOrUsedMap = new TreeMap<Long, String>(goodsNewOrUsedMap);
		for (Map.Entry<Long, String> entry : goodsNewOrUsedMap.entrySet()) {
			localizedgoodsNewOrUsedMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("goodsNewOrUsedMap", localizedgoodsNewOrUsedMap);
	      
		Map<Long, String> goodsConditionMap = referenceStore.getGoodsCondition();
		SortedMap<Long, String> localizedgoodsConditionMap = new TreeMap<Long, String>(goodsConditionMap);
		for (Map.Entry<Long, String> entry : goodsConditionMap.entrySet()) {
			localizedgoodsConditionMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("goodsConditionMap", localizedgoodsConditionMap);
	      
		Map<Long, String> goodsQuantityMap = referenceStore.getGoodsQuantity();
		SortedMap<Long, String> localizedgoodsQuantityMap = new TreeMap<Long, String>(goodsQuantityMap);
		for (Map.Entry<Long, String> entry : goodsQuantityMap.entrySet()) {
			localizedgoodsQuantityMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		model.addAttribute("goodsQuantityMap", localizedgoodsQuantityMap);
	      
		
		Map<Long, String> localeMap = referenceStore.getLocale();
		SortedMap<Long, String> localizedLocaleMap = new TreeMap<Long, String>(localeMap);
		for (Map.Entry<Long, String> entry : localeMap.entrySet()) {
			localizedLocaleMap.replace(entry.getKey(), messageSource.getMessage(entry.getValue(), new String[0], locale));
		}
		
		model.addAttribute("localeMap", localizedLocaleMap);
	}
	


}
