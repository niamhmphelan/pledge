<%@ page session="false"%><%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%><%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%><!DOCTYPE html>
<html lang="en">
<c:choose>
	<c:when test="${accommodationPledgeFormModel['currentMode'] == 'ADD'}">
		<spring:message code="accommodationpledge.form.header" var="headerText"/>
	</c:when>
	<c:when test="${accommodationPledgeFormModel['currentMode'] == 'UPDATE'}">
		<spring:message code="accommodationpledge.form.header" var="headerText"/>
	</c:when>
	<c:when test="${accommodationPledgeFormModel['currentMode'] == 'LOCALIZE'}">
		<spring:message code="accommodationpledge.form.header" var="headerText"/>
	</c:when>
	<c:otherwise>
		<spring:message code="accommodationpledge.form.header" var="headerText"/>	
	</c:otherwise>
</c:choose>
<spring:message code="accommodationpledge.form.title" var="title"/>	
<jsp:include page="../jsp/includes/header.jsp">
	<jsp:param name="title" value="${title}" />
	<jsp:param name="beanName" value="accommodationPledge" />
</jsp:include>
<body>

<spring:url value="/landingwebpage" var="urlHome"/>
<spring:url value="/entitylist" var="urlAppHome"/>
<spring:url value="/resources/images/redcross-brand-logo.png" var="headerLogo" />
    <nav class="navbar navbar-fixed-top navbar-default">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="${urlHome}"><img src="${headerLogo}" alt="Irish Red Cross"/></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li><a href="javascript:logoutFormSubmit()">Logout</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
	
	<spring:message code="frmAccommodationPledgeCountryPlaceHolder" var="unselectedCountry"/>
    
	<spring:message code="frmAccommodationPledgeOwnerOccupierPlaceHolder" var="unselectedOwnerOccupier"/>
    
	<spring:message code="frmAccommodationPledgeAccommodationTypePlaceHolder" var="unselectedAccommodationType"/>
    
	<spring:message code="frmAccommodationPledgeAccommodationConditionPlaceHolder" var="unselectedAccommodationCondition"/>
    
	<spring:message code="frmAccommodationPledgeNumberOfBedsPlaceHolder" var="unselectedNumberOfBeds"/>
    
	<spring:message code="frmAccommodationPledgeVacantOrSharedPlaceHolder" var="unselectedVacantOrShared"/>
    <%-- 
	<spring:message code="frmAccommodationPledgeCanYouAccommodatePlaceHolder" var="unselectedCanYouAccommodate"/>
     --%>
	<spring:url value="/accommodationpledge/post" var="accommodationPledgeActionUrl" />
	  
	<form:form id="frmAccommodationPledge" role="form" class="form-horizontal" method="post" accept-charset="utf-8"
                modelAttribute="accommodationPledgeFormModel" action="${accommodationPledgeActionUrl}">

		<form:hidden id="frmAccommodationPledgeAccommodationPledgeId" path="id" />
		
		<form:hidden id="frmAccommodationPledgeMode" path="currentMode" />
		
		<input type="hidden" id="frmAccommodationPledgeLoadedFeedbackMessage" value="${msg}" />
		<input type="hidden" id="frmAccommodationPledgeLoadedFeedbackCss" value="${css}" />
    
    
    <section id="accommodationPledgeFormHeaderSection" class="">  
		<div class="container">
			<div class="page-header">
				<h1><spring:message code="accommodationpledge.form.pageheader.accommodationpledgeformheader.headertext"/></h1>
			</div>
		</div>
	</section> <!-- id="accommodationPledgeFormHeaderSection --> 
    
	<div class="container">
		<div class="panel panel-default">
	    
	    	<div class="panel-heading"><h2><spring:message code="accommodationpledge.form.panel.accommodationpledgeinputspanel.headertext"/></h2></div>
	    
			<div class="panel-body">
		    
		<spring:bind path="addressOne">
      
		  <div id="frmAccommodationPledgeAddressOneFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
			<label id="frmAccommodationPledgeAddressOneLabel" for="frmAccommodationPledgeAddressOne" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgeAddressOneLabel"/></label>
		  
			<div class="col-sm-10">
				
				<spring:message code="frmAccommodationPledgeAddressOnePlaceHolder" var="frmAccommodationPledgeAddressOnePlaceHolder"/>    
    
				<form:input path="addressOne" type="text" data-display-name="Address Line 1" data-minlength="5" data-required="true" maxlength="50" class="form-control" id="frmAccommodationPledgeAddressOne" placeholder="${frmAccommodationPledgeAddressOnePlaceHolder}" aria-describedby="frmAccommodationPledgeAddressOneHelpBlock" data-validate-key-press="true"/>
    
				<span id="frmAccommodationPledgeAddressOneFeedbackIcon" class="glyphicon form-control-feedback" aria-hidden="true"></span>
				<div id="frmAccommodationPledgeAddressOneAlertBlock" class="alert alert-dismissible hidden" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  					<span id="frmAccommodationPledgeAddressOneAlertBlockText"></span>
				</div>
			</div>
				
				<c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgeAddressOneHelpBlock" path="addressOne" class="help-block col-sm-offset-2 col-sm-10" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgeAddressOneHelpBlock" class="help-block col-sm-offset-2 col-sm-10"><spring:message code="frmAccommodationPledgeAddressOneHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose>
		  </div>
      
		</spring:bind>
      
		<spring:bind path="addressTwo">
      
		  <div id="frmAccommodationPledgeAddressTwoFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
			<label id="frmAccommodationPledgeAddressTwoLabel" for="frmAccommodationPledgeAddressTwo" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgeAddressTwoLabel"/></label>
		  
			<div class="col-sm-10">
				
				<spring:message code="frmAccommodationPledgeAddressTwoPlaceHolder" var="frmAccommodationPledgeAddressTwoPlaceHolder"/>    
    
				<form:input path="addressTwo" type="text" data-display-name="Address Line 2" data-minlength="5" data-required="true" maxlength="50" class="form-control" id="frmAccommodationPledgeAddressTwo" placeholder="${frmAccommodationPledgeAddressTwoPlaceHolder}" aria-describedby="frmAccommodationPledgeAddressTwoHelpBlock" data-validate-key-press="true"/>
    
				<span id="frmAccommodationPledgeAddressTwoFeedbackIcon" class="glyphicon form-control-feedback" aria-hidden="true"></span>
				<div id="frmAccommodationPledgeAddressTwoAlertBlock" class="alert alert-dismissible hidden" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  					<span id="frmAccommodationPledgeAddressTwoAlertBlockText"></span>
				</div>
			</div>
				
				<c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgeAddressTwoHelpBlock" path="addressTwo" class="help-block col-sm-offset-2 col-sm-10" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgeAddressTwoHelpBlock" class="help-block col-sm-offset-2 col-sm-10"><spring:message code="frmAccommodationPledgeAddressTwoHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose>
		  </div>
      
		</spring:bind>
      
		<spring:bind path="city">
      
		  <div id="frmAccommodationPledgeCityFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
			<label id="frmAccommodationPledgeCityLabel" for="frmAccommodationPledgeCity" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgeCityLabel"/></label>
		  
			<div class="col-sm-10">
				
				<spring:message code="frmAccommodationPledgeCityPlaceHolder" var="frmAccommodationPledgeCityPlaceHolder"/>    
    
				<form:input path="city" type="text" data-display-name="Townland/Town/City" data-minlength="2" data-required="false" maxlength="50" class="form-control" id="frmAccommodationPledgeCity" placeholder="${frmAccommodationPledgeCityPlaceHolder}" aria-describedby="frmAccommodationPledgeCityHelpBlock" data-validate-key-press="true"/>
    
				<span id="frmAccommodationPledgeCityFeedbackIcon" class="glyphicon form-control-feedback" aria-hidden="true"></span>
				<div id="frmAccommodationPledgeCityAlertBlock" class="alert alert-dismissible hidden" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  					<span id="frmAccommodationPledgeCityAlertBlockText"></span>
				</div>
			</div>
				
				<c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgeCityHelpBlock" path="city" class="help-block col-sm-offset-2 col-sm-10" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgeCityHelpBlock" class="help-block col-sm-offset-2 col-sm-10"><spring:message code="frmAccommodationPledgeCityHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose>
		  </div>
      
		</spring:bind>
      
		<spring:bind path="stateProvinceRegion">
      
		  <div id="frmAccommodationPledgeStateProvinceRegionFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
			<label id="frmAccommodationPledgeStateProvinceRegionLabel" for="frmAccommodationPledgeStateProvinceRegion" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgeStateProvinceRegionLabel"/></label>
		  
			<div class="col-sm-10">
				
				<spring:message code="frmAccommodationPledgeStateProvinceRegionPlaceHolder" var="frmAccommodationPledgeStateProvinceRegionPlaceHolder"/>    
    
				<form:input path="stateProvinceRegion" type="text" data-display-name="State/Province/Region" data-minlength="2" data-required="false" maxlength="50" class="form-control" id="frmAccommodationPledgeStateProvinceRegion" placeholder="${frmAccommodationPledgeStateProvinceRegionPlaceHolder}" aria-describedby="frmAccommodationPledgeStateProvinceRegionHelpBlock" data-validate-key-press="true"/>
    
				<span id="frmAccommodationPledgeStateProvinceRegionFeedbackIcon" class="glyphicon form-control-feedback" aria-hidden="true"></span>
				<div id="frmAccommodationPledgeStateProvinceRegionAlertBlock" class="alert alert-dismissible hidden" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  					<span id="frmAccommodationPledgeStateProvinceRegionAlertBlockText"></span>
				</div>
			</div>
				
				<c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgeStateProvinceRegionHelpBlock" path="stateProvinceRegion" class="help-block col-sm-offset-2 col-sm-10" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgeStateProvinceRegionHelpBlock" class="help-block col-sm-offset-2 col-sm-10"><spring:message code="frmAccommodationPledgeStateProvinceRegionHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose>
		  </div>
      
		</spring:bind>
      
		<spring:bind path="postCode">
      
		  <div id="frmAccommodationPledgePostCodeFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
			<label id="frmAccommodationPledgePostCodeLabel" for="frmAccommodationPledgePostCode" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgePostCodeLabel"/></label>
		  
			<div class="col-sm-10">
				
				<spring:message code="frmAccommodationPledgePostCodePlaceHolder" var="frmAccommodationPledgePostCodePlaceHolder"/>    
    
				<form:input path="postCode" type="text" data-display-name="Post Code" data-minlength="0" data-required="false" maxlength="10" class="form-control" id="frmAccommodationPledgePostCode" placeholder="${frmAccommodationPledgePostCodePlaceHolder}" aria-describedby="frmAccommodationPledgePostCodeHelpBlock" data-validate-key-press="true"/>
    
				<span id="frmAccommodationPledgePostCodeFeedbackIcon" class="glyphicon form-control-feedback" aria-hidden="true"></span>
				<div id="frmAccommodationPledgePostCodeAlertBlock" class="alert alert-dismissible hidden" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  					<span id="frmAccommodationPledgePostCodeAlertBlockText"></span>
				</div>
			</div>
				
				<c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgePostCodeHelpBlock" path="postCode" class="help-block col-sm-offset-2 col-sm-10" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgePostCodeHelpBlock" class="help-block col-sm-offset-2 col-sm-10"><spring:message code="frmAccommodationPledgePostCodeHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose>
		  </div>
      
		</spring:bind>
      
		<spring:bind path="country">
      
		  <div id="frmAccommodationPledgeCountryFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
			<label id="frmAccommodationPledgeCountryLabel" for="frmAccommodationPledgeCountry" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgeCountryLabel"/></label>
		  
			<div class="col-sm-5 select-container">
				
	<spring:message code="frmAccommodationPledgeCountryPlaceHolder" var="unselectedCountry"/>
    	
				<form:select path="country" data-display-name="Country" data-reference-type="EuropeCountry" data-select-type="standalone" data-required="true" class="form-control" id="frmAccommodationPledgeCountry" aria-describedby="frmAccommodationPledgeCountryHelpBlock" onchange="loadChildSelectAndSelectValue(this, document.getElementById('frmAccommodationPledge'), frmAccommodationPledgeBuffer, '', 'Select placeholder')"
>
					<form:option value="-1" label="${unselectedCountry}" />
					
					<form:options items="${countryMap}" />
					
				</form:select>
	  
				<span id="frmAccommodationPledgeCountryFeedbackIcon" class="glyphicon form-control-feedback" aria-hidden="true"></span>
				<div id="frmAccommodationPledgeCountryAlertBlock" class="alert alert-dismissible hidden" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  					<span id="frmAccommodationPledgeCountryAlertBlockText"></span>
				</div>
			</div>
				
				<c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgeCountryHelpBlock" path="country" class="help-block col-sm-offset-2 col-sm-10" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgeCountryHelpBlock" class="help-block col-sm-offset-2 col-sm-10"><spring:message code="frmAccommodationPledgeCountryHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose>
		  </div>
      
		</spring:bind>
      
		<spring:bind path="ownerOccupier">
      
		  <div id="frmAccommodationPledgeOwnerOccupierFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
			<label id="frmAccommodationPledgeOwnerOccupierLabel" for="frmAccommodationPledgeOwnerOccupier" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgeOwnerOccupierLabel"/></label>
		  
			<div class="col-sm-5 select-container">
				
	<spring:message code="frmAccommodationPledgeOwnerOccupierPlaceHolder" var="unselectedOwnerOccupier"/>
    	
				<form:select path="ownerOccupier" data-display-name="Who owns the accommodation?" data-reference-type="OwnerOccupierType" data-select-type="standalone" data-required="false" class="form-control" id="frmAccommodationPledgeOwnerOccupier" aria-describedby="frmAccommodationPledgeOwnerOccupierHelpBlock" onchange="loadChildSelectAndSelectValue(this, document.getElementById('frmAccommodationPledge'), frmAccommodationPledgeBuffer, '', 'Select placeholder')"
>
					<form:option value="-1" label="${unselectedOwnerOccupier}" />
					
					<form:options items="${ownerOccupierMap}" />
					
				</form:select>
	  
				<span id="frmAccommodationPledgeOwnerOccupierFeedbackIcon" class="glyphicon form-control-feedback" aria-hidden="true"></span>
				<div id="frmAccommodationPledgeOwnerOccupierAlertBlock" class="alert alert-dismissible hidden" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  					<span id="frmAccommodationPledgeOwnerOccupierAlertBlockText"></span>
				</div>
			</div>
				
				<c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgeOwnerOccupierHelpBlock" path="ownerOccupier" class="help-block col-sm-offset-2 col-sm-10" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgeOwnerOccupierHelpBlock" class="help-block col-sm-offset-2 col-sm-10"><spring:message code="frmAccommodationPledgeOwnerOccupierHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose>
		  </div>
      
		</spring:bind>
      
		<spring:bind path="accommodationDateFrom">
      
		  <div id="frmAccommodationPledgeAccommodationDateFromFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
			<label id="frmAccommodationPledgeAccommodationDateFromLabel" for="frmAccommodationPledgeAccommodationDateFrom" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgeAccommodationDateFromLabel"/></label>
		  
			<div class="col-sm-10">
				
				<spring:message code="frmAccommodationPledgeAccommodationDateFromPlaceHolder" var="frmAccommodationPledgeAccommodationDateFromPlaceHolder"/>    
    
				<form:input path="accommodationDateFrom" type="text" data-display-name="When is the accommodation available from?" data-minlength="10" data-required="false" maxlength="10" class="form-control datepicker" id="frmAccommodationPledgeAccommodationDateFrom" placeholder="${frmAccommodationPledgeAccommodationDateFromPlaceHolder}" aria-describedby="frmAccommodationPledgeAccommodationDateFromHelpBlock" data-data-type="date" data-validate-key-press="true" />
    
				<span id="frmAccommodationPledgeAccommodationDateFromFeedbackIcon" class="glyphicon form-control-feedback" aria-hidden="true"></span>
				<div id="frmAccommodationPledgeAccommodationDateFromAlertBlock" class="alert alert-dismissible hidden" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  					<span id="frmAccommodationPledgeAccommodationDateFromAlertBlockText"></span>
				</div>
			</div>
				
				<c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgeAccommodationDateFromHelpBlock" path="accommodationDateFrom" class="help-block col-sm-offset-2 col-sm-10" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgeAccommodationDateFromHelpBlock" class="help-block col-sm-offset-2 col-sm-10"><spring:message code="frmAccommodationPledgeAccommodationDateFromHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose>
		  </div>
      
		</spring:bind>
      
		<spring:bind path="accommodationDateTo">
      
		  <div id="frmAccommodationPledgeAccommodationDateToFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
			<label id="frmAccommodationPledgeAccommodationDateToLabel" for="frmAccommodationPledgeAccommodationDateTo" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgeAccommodationDateToLabel"/></label>
		  
			<div class="col-sm-10">
				
				<spring:message code="frmAccommodationPledgeAccommodationDateToPlaceHolder" var="frmAccommodationPledgeAccommodationDateToPlaceHolder"/>    
    
				<form:input path="accommodationDateTo" type="text" data-display-name="When is the accommodation available until?" data-minlength="10" data-required="false" maxlength="10" class="form-control datepicker" id="frmAccommodationPledgeAccommodationDateTo" placeholder="${frmAccommodationPledgeAccommodationDateToPlaceHolder}" aria-describedby="frmAccommodationPledgeAccommodationDateToHelpBlock" data-data-type="date" data-validate-key-press="true" />
    
				<span id="frmAccommodationPledgeAccommodationDateToFeedbackIcon" class="glyphicon form-control-feedback" aria-hidden="true"></span>
				<div id="frmAccommodationPledgeAccommodationDateToAlertBlock" class="alert alert-dismissible hidden" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  					<span id="frmAccommodationPledgeAccommodationDateToAlertBlockText"></span>
				</div>
			</div>
				
				<c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgeAccommodationDateToHelpBlock" path="accommodationDateTo" class="help-block col-sm-offset-2 col-sm-10" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgeAccommodationDateToHelpBlock" class="help-block col-sm-offset-2 col-sm-10"><spring:message code="frmAccommodationPledgeAccommodationDateToHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose>
		  </div>
      
		</spring:bind>
      
		<spring:bind path="accommodationType">
      
		  <div id="frmAccommodationPledgeAccommodationTypeFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
			<label id="frmAccommodationPledgeAccommodationTypeLabel" for="frmAccommodationPledgeAccommodationType" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgeAccommodationTypeLabel"/></label>
		  
			<div class="col-sm-5 select-container">
				
	<spring:message code="frmAccommodationPledgeAccommodationTypePlaceHolder" var="unselectedAccommodationType"/>
    	
				<form:select path="accommodationType" data-display-name="What kind of accommodation are you pledging?" data-reference-type="AccommodationType" data-select-type="standalone" data-required="false" class="form-control" id="frmAccommodationPledgeAccommodationType" aria-describedby="frmAccommodationPledgeAccommodationTypeHelpBlock" onchange="loadChildSelectAndSelectValue(this, document.getElementById('frmAccommodationPledge'), frmAccommodationPledgeBuffer, '', 'Select placeholder')"
>
					<form:option value="-1" label="${unselectedAccommodationType}" />
					
					<form:options items="${accommodationTypeMap}" />
					
				</form:select>
	  
				<span id="frmAccommodationPledgeAccommodationTypeFeedbackIcon" class="glyphicon form-control-feedback" aria-hidden="true"></span>
				<div id="frmAccommodationPledgeAccommodationTypeAlertBlock" class="alert alert-dismissible hidden" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  					<span id="frmAccommodationPledgeAccommodationTypeAlertBlockText"></span>
				</div>
			</div>
				
				<c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgeAccommodationTypeHelpBlock" path="accommodationType" class="help-block col-sm-offset-2 col-sm-10" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgeAccommodationTypeHelpBlock" class="help-block col-sm-offset-2 col-sm-10"><spring:message code="frmAccommodationPledgeAccommodationTypeHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose>
		  </div>
      
		</spring:bind>
      
		<spring:bind path="accommodationCondition">
      
		  <div id="frmAccommodationPledgeAccommodationConditionFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
			<label id="frmAccommodationPledgeAccommodationConditionLabel" for="frmAccommodationPledgeAccommodationCondition" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgeAccommodationConditionLabel"/></label>
		  
			<div class="col-sm-5 select-container">
				
	<spring:message code="frmAccommodationPledgeAccommodationConditionPlaceHolder" var="unselectedAccommodationCondition"/>
    	
				<form:select path="accommodationCondition" data-display-name="Condition" data-reference-type="AccommodationCondition" data-select-type="standalone" data-required="false" class="form-control" id="frmAccommodationPledgeAccommodationCondition" aria-describedby="frmAccommodationPledgeAccommodationConditionHelpBlock" onchange="loadChildSelectAndSelectValue(this, document.getElementById('frmAccommodationPledge'), frmAccommodationPledgeBuffer, '', 'Select placeholder')"
>
					<form:option value="-1" label="${unselectedAccommodationCondition}" />
					
					<form:options items="${accommodationConditionMap}" />
					
				</form:select>
	  
				<span id="frmAccommodationPledgeAccommodationConditionFeedbackIcon" class="glyphicon form-control-feedback" aria-hidden="true"></span>
				<div id="frmAccommodationPledgeAccommodationConditionAlertBlock" class="alert alert-dismissible hidden" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  					<span id="frmAccommodationPledgeAccommodationConditionAlertBlockText"></span>
				</div>
			</div>
				
				<c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgeAccommodationConditionHelpBlock" path="accommodationCondition" class="help-block col-sm-offset-2 col-sm-10" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgeAccommodationConditionHelpBlock" class="help-block col-sm-offset-2 col-sm-10"><spring:message code="frmAccommodationPledgeAccommodationConditionHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose>
		  </div>
      
		</spring:bind>
      
		<spring:bind path="numberOfBeds">
      
		  <div id="frmAccommodationPledgeNumberOfBedsFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
			<label id="frmAccommodationPledgeNumberOfBedsLabel" for="frmAccommodationPledgeNumberOfBeds" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgeNumberOfBedsLabel"/></label>
		  
			<div class="col-sm-5 select-container">
				
	<spring:message code="frmAccommodationPledgeNumberOfBedsPlaceHolder" var="unselectedNumberOfBeds"/>
    	
				<form:select path="numberOfBeds" data-display-name="Number of beds" data-reference-type="NumberOfBeds" data-select-type="standalone" data-required="false" class="form-control" id="frmAccommodationPledgeNumberOfBeds" aria-describedby="frmAccommodationPledgeNumberOfBedsHelpBlock" onchange="loadChildSelectAndSelectValue(this, document.getElementById('frmAccommodationPledge'), frmAccommodationPledgeBuffer, '', 'Select placeholder')"
>
					<form:option value="-1" label="${unselectedNumberOfBeds}" />
					
					<form:options items="${numberOfBedsMap}" />
					
				</form:select>
	  
				<span id="frmAccommodationPledgeNumberOfBedsFeedbackIcon" class="glyphicon form-control-feedback" aria-hidden="true"></span>
				<div id="frmAccommodationPledgeNumberOfBedsAlertBlock" class="alert alert-dismissible hidden" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  					<span id="frmAccommodationPledgeNumberOfBedsAlertBlockText"></span>
				</div>
			</div>
				
				<c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgeNumberOfBedsHelpBlock" path="numberOfBeds" class="help-block col-sm-offset-2 col-sm-10" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgeNumberOfBedsHelpBlock" class="help-block col-sm-offset-2 col-sm-10"><spring:message code="frmAccommodationPledgeNumberOfBedsHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose>
		  </div>
      
		</spring:bind>
      
		<spring:bind path="vacantOrShared">
      
		  <div id="frmAccommodationPledgeVacantOrSharedFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
			<label id="frmAccommodationPledgeVacantOrSharedLabel" for="frmAccommodationPledgeVacantOrShared" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgeVacantOrSharedLabel"/></label>
		  
			<div class="col-sm-5 select-container">
				
	<spring:message code="frmAccommodationPledgeVacantOrSharedPlaceHolder" var="unselectedVacantOrShared"/>
    	
				<form:select path="vacantOrShared" data-display-name="Vacant or Shared" data-reference-type="VacantOrShared" data-select-type="standalone" data-required="false" class="form-control" id="frmAccommodationPledgeVacantOrShared" aria-describedby="frmAccommodationPledgeVacantOrSharedHelpBlock" onchange="loadChildSelectAndSelectValue(this, document.getElementById('frmAccommodationPledge'), frmAccommodationPledgeBuffer, '', 'Select placeholder')"
>
					<form:option value="-1" label="${unselectedVacantOrShared}" />
					
					<form:options items="${vacantOrSharedMap}" />
					
				</form:select>
	  
				<span id="frmAccommodationPledgeVacantOrSharedFeedbackIcon" class="glyphicon form-control-feedback" aria-hidden="true"></span>
				<div id="frmAccommodationPledgeVacantOrSharedAlertBlock" class="alert alert-dismissible hidden" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  					<span id="frmAccommodationPledgeVacantOrSharedAlertBlockText"></span>
				</div>
			</div>
				
				<c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgeVacantOrSharedHelpBlock" path="vacantOrShared" class="help-block col-sm-offset-2 col-sm-10" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgeVacantOrSharedHelpBlock" class="help-block col-sm-offset-2 col-sm-10"><spring:message code="frmAccommodationPledgeVacantOrSharedHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose>
		  </div>
      
		</spring:bind>
      
		<spring:bind path="otherAmenities">
      
		  <div id="frmAccommodationPledgeOtherAmenitiesFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
			<label id="frmAccommodationPledgeOtherAmenitiesLabel" for="frmAccommodationPledgeOtherAmenities" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgeOtherAmenitiesLabel"/></label>
		  
			<div class="col-sm-10">
				
				<spring:message code="frmAccommodationPledgeOtherAmenitiesPlaceHolder" var="frmAccommodationPledgeOtherAmenitiesPlaceHolder"/>    
    
				<form:textarea path="otherAmenities" data-display-name="Description of other Amenities" data-minlength="0" data-required="false" rows="5" maxlength="500" class="form-control" id="frmAccommodationPledgeOtherAmenities" placeholder="${frmAccommodationPledgeOtherAmenitiesPlaceHolder}" aria-describedby="frmAccommodationPledgeOtherAmenitiesHelpBlock" data-validate-key-press="true" />
    
				<span id="frmAccommodationPledgeOtherAmenitiesFeedbackIcon" class="glyphicon form-control-feedback" aria-hidden="true"></span>
				<div id="frmAccommodationPledgeOtherAmenitiesAlertBlock" class="alert alert-dismissible hidden" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  					<span id="frmAccommodationPledgeOtherAmenitiesAlertBlockText"></span>
				</div>
			</div>
				<span id="frmAccommodationPledgeOtherAmenitiesCountBlock" class="help-block col-sm-offset-2 col-sm-1"></span>
				<c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgeOtherAmenitiesHelpBlock" path="otherAmenities" class="help-block col-sm-9" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgeOtherAmenitiesHelpBlock" class="help-block col-sm-9"><spring:message code="frmAccommodationPledgeOtherAmenitiesHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose>
		  </div>
      
		</spring:bind>
      
		
      
		  <div id="frmAccommodationPledgeAccommodateFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
		  <label id="frmAccommodationPledgeCanYouAccommodateLabel" for="frmAccommodationPledgeCanYouAccommodate" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgeCanYouAccommodateLabel"/></label>
		 		
				<%-- <c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgeCanYouAccommodateHelpBlock" path="canYouAccommodate" class="help-block col-sm-offset-2 col-sm-10" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgeCanYouAccommodateHelpBlock" class="help-block col-sm-offset-2 col-sm-10"><spring:message code="frmAccommodationPledgeCanYouAccommodateHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose> --%>
		  </div>
      
		
      
		<spring:bind path="additionalInformation">
      
		  <div id="frmAccommodationPledgeAdditionalInformationFormGroup" class="form-group has-feedback ${status.error ? 'has-error' : ''}">
		  
			<label id="frmAccommodationPledgeAdditionalInformationLabel" for="frmAccommodationPledgeAdditionalInformation" class="col-sm-2 control-label"><spring:message code="frmAccommodationPledgeAdditionalInformationLabel"/></label>
		  
			<div class="col-sm-10">
				
				<spring:message code="frmAccommodationPledgeAdditionalInformationPlaceHolder" var="frmAccommodationPledgeAdditionalInformationPlaceHolder"/>    
    
				<form:textarea path="additionalInformation" data-display-name="Is there anything else that you would like to say about the accommodation?" data-minlength="0" data-required="false" rows="5" maxlength="500" class="form-control" id="frmAccommodationPledgeAdditionalInformation" placeholder="${frmAccommodationPledgeAdditionalInformationPlaceHolder}" aria-describedby="frmAccommodationPledgeAdditionalInformationHelpBlock" data-validate-key-press="true" />
    
				<span id="frmAccommodationPledgeAdditionalInformationFeedbackIcon" class="glyphicon form-control-feedback" aria-hidden="true"></span>
				<div id="frmAccommodationPledgeAdditionalInformationAlertBlock" class="alert alert-dismissible hidden" role="alert">
  					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
  					<span id="frmAccommodationPledgeAdditionalInformationAlertBlockText"></span>
				</div>
			</div>
				<span id="frmAccommodationPledgeAdditionalInformationCountBlock" class="help-block col-sm-offset-2 col-sm-1"></span>
				<c:choose>
				  <c:when test="${status.error}">
				<form:errors id="frmAccommodationPledgeAdditionalInformationHelpBlock" path="additionalInformation" class="help-block col-sm-9" />
				  </c:when>
				  <c:otherwise>
				<span id="frmAccommodationPledgeAdditionalInformationHelpBlock" class="help-block col-sm-9"><spring:message code="frmAccommodationPledgeAdditionalInformationHelpBlock"/></span>				  
				  </c:otherwise>
				</c:choose>
		  </div>
      
		</spring:bind>
      
			</div> <!-- class="panel-body" -->
		</div> <!-- class="panel panel-default" -->
	</div> <!-- class="container" -->
    
	<div class="container">
		<div class="panel panel-default">
	    
			<div class="panel-body">
		    
	    <button type="submit" class="btn-lg btn-primary"><spring:message code="frmAccommodationPledgeSaveButtonLabel"/></button>

			</div> <!-- class="panel-body" -->
		</div> <!-- class="panel panel-default" -->
	</div> <!-- class="container" -->
    
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	</form:form>
<jsp:include page="../jsp/includes/redcrossfooter.jsp" />	

<script>  
var rootContext = "${pageContext.request.contextPath}";
</script>

  
<spring:url value="/wro/accommodationPledgeJs.js" var="accommodationPledgeJs" />
  
<script src="${accommodationPledgeJs}"></script>

</body>
</html>

