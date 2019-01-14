<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="numTag" uri="/WEB-INF/number.tld" %>
<c:choose>
   <c:when test="${sessionScope.user.type == 'CLIENT'}">
      <c:set var="currentPage"
         value="${pageContext.request.contextPath}/main/displayTaxReportFormDetailsByInitiator"/>
   </c:when>
   <c:otherwise>
      <c:set var="currentPage"
         value="${pageContext.request.contextPath}/main/displayTaxReportFormDetailsByReviewer"/>
   </c:otherwise>
</c:choose>
<%@include file="/WEB-INF/pages/navbar.jsp" %>
<fmt:setLocale value="${fn:escapeXml(language)}"/>
<fmt:setBundle basename="internationalization.taxapp"/>
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>
         <fmt:message key="taxReportFormDetails.title"/>
      </title>
   </head>
   <body>
      <div class="container">
         <div>
            <h4>
               <fmt:message key="taxReportFormDetails.introduction"/>
            </h4>
            <hr>
         </div>
         <input type="hidden" name="taxReportForm" value="${fn:escapeXml(taxReportForm.id)}"
            form="languageForm">
         <form class="form-horizontal"
            action="${pageContext.request.contextPath}/main/updateTaxReportForm${sessionScope.user.type == 'INSPECTOR' ? 'ByReviewer' : 'ByInitiator' }"
            method="POST">
            <div class="container" style="margin-top: 20px;">
         <div class="row">
                  <div class="form-group col-md-6">
                  <a href="#demo" class="btn btn-info" data-toggle="collapse"> <fmt:message key="taxReportFormDetails.requestInfo"/></a>
                  </div>
                  </div>
         <div id="demo" class="collapse">
         <br>
               <div class="row">
                  <div class="form-group col-md-6">
                     <label for="creationDate" class="col-md-4 control-label">
                        <fmt:message
                           key="creationDate"/>
                        :
                     </label>
                     <div class="col-md-8">
                        <input type="text" class="form-control" name="creationDate"
                           id="creationDate" value="${fn:escapeXml(taxReportForm.creationDate)}" readonly>
                     </div>
                  </div>
                  <div class="form-group col-md-6">
                     <label for="lastModifiedDate" class="col-md-4 control-label">
                        <fmt:message key="lastModifiedDate"/>
                        :
                     </label>
                     <div class="col-md-8">
                        <input type="text" class="form-control" name="lastModifiedDate"
                           id="lastModifiedDate" value="${fn:escapeXml(taxReportForm.lastModifiedDate)}"
                           readonly>
                     </div>
                  </div>
               </div>
               <div class="row">
                  <div class="form-group col-md-6">
                     <label for="taxpayerName" class="col-md-4 control-label">
                        <fmt:message
                           key="taxpayer.name"/>
                        :
                     </label>
                     <div class="col-md-8">
                        <input type="text" class="form-control" name="taxpayerName"
                           id="taxpayerName" value="${fn:escapeXml(taxReportForm.taxpayer.name)}" readonly>
                     </div>
                  </div>
                  <div class="form-group col-md-6">
                     <label for="registrationNumber" class="col-md-4 control-label">
                        <fmt:message key="taxpayer.registrationNumber"/>
                        :
                     </label>
                     <div class="col-md-8">
                        <input type="text" class="form-control" name="registrationNumber"
                           id="registrationNumber"
                           value="${fn:escapeXml(taxReportForm.taxpayer.registrationNumber)}" readonly>
                     </div>
                  </div>
               </div>
              <div class="row">
                  <div class="form-group col-md-6">
                     <label for="initiatorEmail" class="col-md-4 control-label">
                        <fmt:message key="taxpayer.employee"/>
                        :
                     </label>
                     <div class="col-md-8">
                        <input type="text" class="form-control" name="initiatorEmail"
                           id="initiatorEmail" value="${fn:escapeXml(taxReportForm.initiator.email)}"
                           readonly>
                     </div>
                  </div>
                  <div class="form-group col-md-6">
                     <label for="inspectorEmail" class="col-md-4 control-label">
                        <fmt:message key="taxReportForm.reviewer"/>
                        :
                     </label>
                     <div class="col-md-8">
                        <input type="text" class="form-control" name="inspectorEmail"
                           id="inspectorEmail" value="${fn:escapeXml(taxReportForm.reviewer.email)}"
                           readonly>
                     </div>
                  </div>
               </div>
              </div>
               <div class="row">
                  <div class="form-group col-md-6">
                     <label for="year" class="col-md-4 control-label">
                        <fmt:message
                           key="taxReportForm.year"/>
                        :
                     </label>
                     <c:if test="${empty yearException}">
                        <div class="col-md-8">
                           <input type="text" class="form-control" name="year" id="year"
                           value="${fn:escapeXml(not empty yearProvided ? yearProvided : taxReportForm.year)}"
                           ${(sessionScope.user.type == 'CLIENT') && (taxReportForm.status == 'RETURNED') ? 'required' : 'readonly'}>
                        </div>
                     </c:if>
                     <c:if test="${not empty yearException}">
                        <div class="col-md-8 has-error has-feedback">
                           <input type="text" class="form-control" name="year" id="year"
                           ${(sessionScope.user.type == 'CLIENT') && (taxReportForm.status == 'RETURNED') ? 'required' : 'readonly'}>
                        </div>
                        <small id="exception" class="form-text text-danger">
                           <fmt:message key="${nameException}"/>
                        </small>
                     </c:if>
                  </div>
                  <div class="form-group col-md-6">
                     <label for="quarter" class="col-md-4 control-label">
                        <fmt:message
                           key="taxReportForm.quarter"/>
                        :
                     </label>
                     <c:if test="${empty quarterException}">
                        <div class="col-md-8">
                           <select class="form-control" id="quarter" name="quarter"
                           ${(sessionScope.user.type == 'CLIENT') && (taxReportForm.status == 'RETURNED') ? '' : 'disabled'}>
                           <option value=<c:out value="1"/>
                           ${quarterProvided == '1' ? 'selected' : taxReportForm.quarter == 1 ? 'selected' : ''}><c:out value="1"/>
                           </option>
                           <option value=<c:out value="2"/>
                           ${quarterProvided == '2' ? 'selected' : taxReportForm.quarter == 2 ? 'selected' : ''}><c:out value="2"/>
                           </option>
                           <option value=<c:out value="3"/>
                           ${quarterProvided == '3' ? 'selected' : taxReportForm.quarter == 3 ? 'selected' : ''}><c:out value="3"/>
                           </option>
                           <option value=<c:out value="4"/>
                           ${quarterProvided == '4' ? 'selected' : taxReportForm.quarter == 4 ? 'selected' : ''}><c:out value="4"/>
                           </option>
                           </select>
                        </div>
                     </c:if>
                     <c:if test="${not empty quarterException}">
                        <div class="col-md-8 has-error has-feedback">
                           <select class="form-control" id="quarter" name="quarter"
                           ${sessionScope.user.type == 'CLIENT' ? '' : 'disabled'}>
                           <option value=<c:out value="1"/>
                           ${quarterProvided == '1' ? 'selected' : taxReportForm.quarter == 1 ? 'selected' : ''}><c:out value="1"/>
                           </option>
                           <option value=<c:out value="2"/>
                           ${quarterProvided == '2' ? 'selected' : taxReportForm.quarter == 2 ? 'selected' : ''}><c:out value="2"/>
                           </option>
                           <option value=<c:out value="3"/>
                           ${quarterProvided == '3' ? 'selected' : taxReportForm.quarter == 3 ? 'selected' : ''}><c:out value="3"/>
                           </option>
                           <option value=<c:out value="4"/>
                           ${quarterProvided == '4' ? 'selected' : taxReportForm.quarter == 4 ? 'selected' : ''}><c:out value="4"/>
                           </option>
                           </select>
                           <small id="exception" class="form-text text-danger">
                              <fmt:message key="${quarterException}"/>
                           </small>
                        </div>
                     </c:if>
                  </div>
               </div>
               <div class="row">
                  <div class="form-group col-md-6">
                     <label for="mainActivityIncome" class="col-md-4 control-label">
                        <fmt:message key="taxReportForm.mainActivityIncome"/>
                        :
                     </label>
                     <c:if test="${empty mainActivityIncomeException}">
                        <div class="col-md-8">
                           <input type="text" class="form-control" name="mainActivityIncome"
                           id="mainActivityIncome"
                           value=
                           <numTag:numberFormatter
                              number="${fn:escapeXml(not empty mainActivityIncomeProvided ? mainActivityIncomeProvided : taxReportForm.mainActivityIncome)}"/>
                           ${(sessionScope.user.type == 'CLIENT') && (taxReportForm.status == 'RETURNED') ? 'required' : 'readonly'}>
                        </div>
                     </c:if>
                     <c:if test="${not empty mainActivityIncomeException}">
                        <div class="col-md-8  has-error has-feedback">
                           <input type="text" class="form-control" name="mainActivityIncome"
                           id="mainActivityIncome"
                           ${sessionScope.user.type == 'CLIENT' ? 'required' : 'readonly'}>
                           <small id="exception" class="form-text text-danger">
                              <fmt:message key="${mainActivityIncomeException}"/>
                           </small>
                        </div>
                     </c:if>
                  </div>
                  <div class="form-group col-md-6">
                     <label for="mainActivityExpenses" class="col-md-4 control-label">
                        <fmt:message key="taxReportForm.mainActivityExpenses"/>
                        :
                     </label>
                     <c:if test="${empty mainActivityExpensesException}">
                        <div class="col-md-8">
                           <input type="text" class="form-control"
                           name="mainActivityExpenses" id="mainActivityExpenses"
                           value=
                           <numTag:numberFormatter
                              number="${fn:escapeXml(not empty mainActivityExpensesProvided ? mainActivityExpensesProvided : taxReportForm.mainActivityExpenses)}"/>
                           ${(sessionScope.user.type == 'CLIENT') && (taxReportForm.status == 'RETURNED') ? 'required' : 'readonly'}>
                        </div>
                     </c:if>
                     <c:if test="${not empty mainActivityExpensesException}">
                        <div class="col-md-8  has-error has-feedback">
                           <input type="text" class="form-control"
                           name="mainActivityExpenses" id="mainActivityExpenses"
                           ${(sessionScope.user.type == 'CLIENT') && (taxReportForm.status == 'RETURNED') ? 'required' : 'readonly'}>
                           <small id="exception" class="form-text text-danger">
                              <fmt:message key="${mainActivityExpensesException}"/>
                           </small>
                        </div>
                     </c:if>
                  </div>
               </div>
               <div class="row">
                  <div class="form-group col-md-6">
                     <label for="investmentIncome" class="col-md-4 control-label">
                        <fmt:message key="taxReportForm.investmentIncome"/>
                        :
                     </label>
                     <c:if test="${empty investmentIncomeException}">
                        <div class="col-md-8">
                           <input type="text" class="form-control" name="investmentIncome"
                           id="investmentIncome"
                           value=
                           <numTag:numberFormatter
                              number="${fn:escapeXml(not empty investmentIncomeProvided ? investmentIncomeProvided : taxReportForm.investmentIncome)}"/>
                           ${(sessionScope.user.type == 'CLIENT') && (taxReportForm.status == 'RETURNED') ? 'required' : 'readonly'}>
                        </div>
                     </c:if>
                     <c:if test="${not empty investmentIncomeException}">
                        <div class="col-md-8  has-error has-feedback">
                           <input type="text" class="form-control" name="investmentIncome"
                           id="investmentIncome"
                           ${(sessionScope.user.type == 'CLIENT') && (taxReportForm.status == 'RETURNED') ? 'required' : 'readonly'}>
                           <small id="exception" class="form-text text-danger">
                              <fmt:message key="${investmentIncomeException}"/>
                           </small>
                        </div>
                     </c:if>
                  </div>
                  <div class="form-group col-md-6">
                     <label for="investmentExpenses" class="col-md-4 control-label">
                        <fmt:message key="taxReportForm.investmentExpenses"/>
                        :
                     </label>
                     <c:if test="${empty investmentExpensesException}">
                        <div class="col-md-8">
                           <input type="text" class="form-control" name="investmentExpenses"
                           id="investmentExpenses"
                           value=
                           <numTag:numberFormatter
                              number="${fn:escapeXml(not empty investmentExpensesProvided ? investmentExpensesProvided : taxReportForm.investmentExpenses)}"/>
                           ${(sessionScope.user.type == 'CLIENT') && (taxReportForm.status == 'RETURNED') ? 'required' : 'readonly'}>
                        </div>
                     </c:if>
                     <c:if test="${not empty investmentExpensesException}">
                        <div class="col-md-8 has-error has-feedback">
                           <input type="text" class="form-control" name="investmentExpenses"
                           id="investmentExpenses"
                           ${(sessionScope.user.type == 'CLIENT') && (taxReportForm.status == 'RETURNED') ? 'required' : 'readonly'}>
                           <small id="exception" class="form-text text-danger">
                              <fmt:message key="${investmentExpensesException}"/>
                           </small>
                        </div>
                     </c:if>
                  </div>
               </div>
               <div class="row">
                  <div class="form-group col-md-6">
                     <label for="propertyIncome" class="col-md-4 control-label">
                        <fmt:message key="taxReportForm.propertyIncome"/>
                        :
                     </label>
                     <c:if test="${empty propertyIncomeException}">
                        <div class="col-md-8">
                           <input type="text" class="form-control" name="propertyIncome"
                           id="propertyIncome"
                           value=
                           <numTag:numberFormatter
                              number="${fn:escapeXml(not empty propertyIncomeProvided ? propertyIncomeProvided : taxReportForm.propertyIncome)}"/>
                           ${(sessionScope.user.type == 'CLIENT') && (taxReportForm.status == 'RETURNED') ? 'required' : 'readonly'}>
                        </div>
                     </c:if>
                     <c:if test="${not empty propertyIncomeException}">
                        <div class="col-md-8 has-error has-feedback">
                           <input type="text" class="form-control" name="propertyIncome"
                           id="propertyIncome"
                           ${(sessionScope.user.type == 'CLIENT') && (taxReportForm.status == 'RETURNED') ? 'required' : 'readonly'}>
                           <small id="exception" class="form-text text-danger">
                              <fmt:message key="${propertyIncomeException}"/>
                           </small>
                        </div>
                     </c:if>
                  </div>
                  <div class="form-group col-md-6">
                     <label for="propertyExpenses" class="col-md-4 control-label">
                        <fmt:message key="taxReportForm.propertyExpenses"/>
                        :
                     </label>
                     <c:if test="${empty propertyExpensesException}">
                        <div class="col-md-8">
                           <input type="text" class="form-control" name="propertyExpenses"
                           id="propertyExpenses"
                           value=
                           <numTag:numberFormatter
                              number="${fn:escapeXml(not empty propertyExpensesProvided ? propertyExpensesProvided : taxReportForm.propertyExpenses)}"/>
                           ${(sessionScope.user.type == 'CLIENT') && (taxReportForm.status == 'RETURNED') ? 'required' : 'readonly'}>
                        </div>
                     </c:if>
                     <c:if test="${not empty propertyExpensesException}">
                        <div class="col-md-8 has-error has-feedback">
                           <input type="text" class="form-control" name="propertyExpenses"
                           id="propertyExpenses"
                           ${(sessionScope.user.type == 'CLIENT') && (taxReportForm.status == 'RETURNED') ? 'required' : 'readonly'}>
                           <small id="exception" class="form-text text-danger">
                              <fmt:message key="${propertyExpensesException}"/>
                           </small>
                        </div>
                     </c:if>
                  </div>
               </div>
               
               <div class="row">
                  <div class="form-group col-md-6">
                     <label for="reviewerComment" class="col-md-4 control-label">
                        <fmt:message key="taxReportForm.reviewerComment"/>
                        :
                     </label>
                     <c:if test="${empty reviewerCommentException}">
                        <div class="col-md-8">
                           <textarea rows="4" class="form-control" name="reviewerComment"
                           id="reviewerComment"
                           ${(sessionScope.user.type == 'INSPECTOR') && (taxReportForm.status == 'IN_PROGRESS') ? 'required' : 'readonly'}>${fn:escapeXml(not empty reviewerCommentProvided ? reviewerCommentProvided : taxReportForm.reviewerComment)}</textarea>
                        </div>
                     </c:if>
                     <c:if test="${not empty reviewerCommentException}">
                        <div class="col-md-8 has-error has-feedback">
                           <textarea rows="4" class="form-control" name="reviewerComment"
                           id="reviewerComment"
                           ${(sessionScope.user.type == 'INSPECTOR') && (taxReportForm.status == 'IN_PROGRESS') ? 'required' : 'readonly'}></textarea>
                           <small id="exception" class="form-text text-danger">
                              <fmt:message key="${reviewerCommentException}"/>
                           </small>
                        </div>
                     </c:if>
                  </div>
                  <c:if
                     test="${(sessionScope.user.type == 'INSPECTOR') && (taxReportForm.status == 'IN_PROGRESS')}">
                     <div class="form-group col-md-6">
                        <div class="radio">
                           <label>
                              <input type="radio" name="status" value="DONE"
                              ${taxReportForm.status == 'DONE' ? 'checked' : taxReportForm.status != 'RETURNED' ? 'checked' : ''}>
                              <fmt:message key="button.done"/>
                           </label>
                        </div>
                        <div class="radio">
                           <label>
                              <input type="radio" name="status"
                              value="RETURNED"
                              ${taxReportForm.status == 'RETURNED' ? 'checked' : ''}>
                              <fmt:message key="button.reject"/>
                           </label>
                        </div>
                     </div>
                  </c:if>
               </div>
               <c:if
                  test="${((sessionScope.user.type == 'INSPECTOR') && (taxReportForm.status == 'IN_PROGRESS')) || ((sessionScope.user.type == 'CLIENT') && (taxReportForm.status == 'RETURNED'))}">
                  <div class="row">
                     <div class="form-group text-center">
                        <input type="hidden" name="taxReportForm"
                           value="${fn:escapeXml(taxReportForm.id)}">
                        <button type="submit" class="btn btn-default">
                           <fmt:message key="button.submit"/>
                        </button>
                     </div>
                  </div>
               </c:if>
            </div>
         </form>
      </div>
      <br><br>
   </body>
</html>