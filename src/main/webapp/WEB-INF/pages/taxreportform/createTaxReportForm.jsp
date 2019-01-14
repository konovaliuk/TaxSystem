<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="currentPage"
   value="${pageContext.request.contextPath}/main/displayCreateTaxReportForm"/>
<%@include file="/WEB-INF/pages/navbar.jsp" %>
<%@taglib prefix="numTag" uri="/WEB-INF/number.tld" %>
<fmt:setLocale value="${fn:escapeXml(language)}"/>
<fmt:setBundle basename="internationalization.taxapp"/>
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>
         <fmt:message key="createTaxReportForm.title"/>
      </title>
   </head>
   <body>
      <div class="container">
         <div>
            <h4>
               <fmt:message key="createTaxReportForm.introduction"/>
            </h4>
            <hr>
         </div>
         <br>
         <a href="#demo" class="btn btn-info" data-toggle="collapse"> <fmt:message key="createTaxReportForm.fileUpload"/></a>
         <div id="demo" class="collapse">
         <form action ="${pageContext.request.contextPath}/main/displayJsonTaxReportFormInput" method="POST" enctype="multipart/form-data" >
            <div class="form-group">
               <input type="file" class="form-control-file" name="fileUpload" id="fileUpload" onchange="submit()">
            </div>
         </form>
         </div>
      </div>
      <div>
         <form class="form-horizontal"
            action="${pageContext.request.contextPath}/main/createTaxReportForm"
            method="POST" >
            <div class="container" style="margin-top: 20px;">
               <div class="row">
                  <div class="form-group col-md-6">
                     <label for="taxpayerName" class="col-md-4 control-label">
                        <fmt:message
                           key="taxpayer.name"/>
                        :
                     </label>
                     <div class="col-md-8">
                        <input type="text" class="form-control" name="taxpayerName"
                           id="taxpayerName" value="${fn:escapeXml(sessionScope.taxpayer.name)}" readonly>
                     </div>
                  </div>
                  <div class="form-group col-md-6">
                     <label for="registrationNumber" class="col-md-4 control-label">
                        <fmt:message
                           key="taxpayer.registrationNumber"/>
                        :
                     </label>
                     <div class="col-md-8">
                        <input type="text" class="form-control"
                           name="taxpayer.registrationNumber"
                           id="taxpayer.registrationNumber"
                           value="${fn:escapeXml(sessionScope.taxpayer.registrationNumber)}" readonly>
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
                              value="${fn:escapeXml(yearProvided)}"
                              placeholder="<fmt:message key="createTaxReportForm.placeholder.year" />"
                           required>
                        </div>
                     </c:if>
                     <c:if test="${not empty yearException}">
                        <div class="col-md-8  has-error has-feedback">
                           <input type="text" class="form-control" name="year" id="year"
                              placeholder="<fmt:message key="createTaxReportForm.placeholder.year" />"
                           required>
                           <small id="exception" class="form-text text-danger">
                              <fmt:message key="${yearException}"/>
                           </small>
                        </div>
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
                           <select class="form-control" id="quarter" name="quarter">
                           <option value="1" ${(quarterProvided == '1') ? 'selected' : ''}><c:out value="1"/></option>
                           <option value="2" ${(quarterProvided == '2') ? 'selected' : ''}><c:out value="2"/></option>
                           <option value="3" ${(quarterProvided == '3') ? 'selected' : ''}><c:out value="3"/></option>
                           <option value="4" ${(quarterProvided == '4') ? 'selected' : ''}><c:out value="4"/></option>
                           </select>
                        </div>
                     </c:if>
                     <c:if test="${not empty quarterException}">
                        <div class="col-md-8 has-error has-feedback">
                           <select class="form-control" id="quarter" name="quarter">
                              <option value="1" selected>1</option>
                              <option value="2">2</option>
                              <option value="3">3</option>
                              <option value="4">4</option>
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
                        <fmt:message
                           key="taxReportForm.mainActivityIncome"/>
                        :
                     </label>
                     <c:if test="${empty mainActivityIncomeException}">
                        <div class="col-md-8">
                           <input type="text" class="form-control" name="mainActivityIncome"
                           id="mainActivityIncome"
                           value=
                           <numTag:numberFormatter number="${fn:escapeXml(mainActivityIncomeProvided)}"/>
                           placeholder="
                           <fmt:message key="createTaxReportForm.placeholder.mainActivityIncome" />
                           "
                           required>
                        </div>
                     </c:if>
                     <c:if test="${not empty mainActivityIncomeException}">
                        <div class="col-md-8  has-error has-feedback">
                           <input type="text" class="form-control" name="mainActivityIncome"
                           id="mainActivityIncome" value="0.00"
                           placeholder=
                           <fmt:message key="createTaxReportForm.placeholder.mainActivityIncome"/>
                           required>
                           <small id="exception" class="form-text text-danger">
                              <fmt:message key="${mainActivityIncomeException}"/>
                           </small>
                        </div>
                     </c:if>
                  </div>
                  <div class="form-group col-md-6">
                     <label for="mainActivityExpenses" class="col-md-4 control-label">
                        <fmt:message
                           key="taxReportForm.mainActivityExpenses"/>
                        :
                     </label>
                     <c:if test="${empty mainActivityExpensesException}">
                        <div class="col-md-8">
                           <input type="text" class="form-control"
                           name="mainActivityExpenses" id="mainActivityExpenses"
                           value=
                           <numTag:numberFormatter number="${fn:escapeXml(mainActivityExpensesProvided)}"/>
                           placeholder="
                           <fmt:message key="createTaxReportForm.placeholder.mainActivityExpenses" />
                           "
                           required>
                        </div>
                     </c:if>
                     <c:if test="${not empty mainActivityExpensesException}">
                        <div class="col-md-8  has-error has-feedback">
                           <input type="text" class="form-control"
                              name="mainActivityExpenses" id="mainActivityExpenses"
                              value="0.00"
                              placeholder="<fmt:message key="createTaxReportForm.placeholder.mainActivityExpenses" />"
                           required>
                           <small id="exception" class="form-text text-danger">
                              <fmt:message key="${fn:escapeXml(mainActivityExpensesException)}"/>
                           </small>
                        </div>
                     </c:if>
                  </div>
               </div>
               <div class="row">
                  <div class="form-group col-md-6">
                     <label for="investmentIncome" class="col-md-4 control-label">
                        <fmt:message
                           key="taxReportForm.investmentIncome"/>
                        :
                     </label>
                     <c:if test="${empty investmentIncomeException}">
                        <div class="col-md-8">
                           <input type="text" class="form-control" name="investmentIncome"
                           id="investmentIncome"
                           value=
                           <numTag:numberFormatter number="${fn:escapeXml(investmentIncomeProvided)}"/>
                           placeholder="
                           <fmt:message key="createTaxReportForm.olaceholder.investmentIncome" />
                           "
                           required>
                        </div>
                     </c:if>
                     <c:if test="${not empty investmentIncomeException}">
                        <div class="col-md-8  has-error has-feedback">
                           <input type="text" class="form-control" name="investmentIncome"
                              id="investmentIncome" value="0.00"
                              placeholder="<fmt:message key="createTaxReportForm.olaceholder.investmentIncome" />"
                           required>
                           <small id="exception" class="form-text text-danger">
                              <fmt:message key="${investmentIncomeException}"/>
                           </small>
                        </div>
                     </c:if>
                  </div>
                  <div class="form-group col-md-6">
                     <label for="investmentExpenses" class="col-md-4 control-label">
                        <fmt:message
                           key="taxReportForm.investmentExpenses"/>
                        :
                     </label>
                     <c:if test="${empty investmentExpensesException}">
                        <div class="col-md-8">
                           <input type="text" class="form-control" name="investmentExpenses"
                           id="investmentExpenses"
                           value=
                           <numTag:numberFormatter number="${fn:escapeXml(investmentExpensesProvided)}"/>
                           placeholder="
                           <fmt:message key="createTaxReportForm.placeholder.investmentExpenses" />
                           "
                           required>
                        </div>
                     </c:if>
                     <c:if test="${not empty investmentExpensesException}">
                        <div class="col-md-8  has-error has-feedback">
                           <input type="text" class="form-control" name="investmentExpenses"
                              id="investmentExpenses" value="0.00"
                              placeholder="<fmt:message key="createTaxReportForm.placeholder.investmentExpenses" />"
                           required>
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
                        <fmt:message
                           key="taxReportForm.propertyIncome"/>
                        :
                     </label>
                     <c:if test="${empty propertyIncomeException}">
                        <div class="col-md-8">
                           <input type="text" class="form-control" name="propertyIncome"
                           id="propertyIncome"
                           value=
                           <numTag:numberFormatter number="${fn:escapeXml(propertyIncomeProvided)}"/>
                           placeholder="
                           <fmt:message key="createTaxReportForm.placeholder.propertyIncome" />
                           "
                           required>
                        </div>
                     </c:if>
                     <c:if test="${not empty propertyIncomeException}">
                        <div class="col-md-8  has-error has-feedback">
                           <input type="text" class="form-control" name="propertyIncome"
                              id="propertyIncome" value="0.00"
                              placeholder="<fmt:message key="createTaxReportForm.placeholder.propertyIncome" />"
                           required>
                           <small id="exception" class="form-text text-danger">
                              <fmt:message key="${propertyIncomeException}"/>
                           </small>
                        </div>
                     </c:if>
                  </div>
                  <div class="form-group col-md-6">
                     <label for="propertyExpenses" class="col-md-4 control-label">
                        <fmt:message
                           key="taxReportForm.propertyExpenses"/>
                        :
                     </label>
                     <c:if test="${empty propertyExpensesException}">
                        <div class="col-md-8">
                           <input type="text" class="form-control" name="propertyExpenses"
                           id="propertyExpenses"
                           value=
                           <numTag:numberFormatter number="${fn:escapeXml(propertyExpensesProvided)}"/>
                           placeholder="
                           <fmt:message key="createTaxReportForm.placeholder.propertyExpenses" />
                           "
                           required>
                        </div>
                     </c:if>
                     <c:if test="${not empty propertyExpensesException}">
                        <div class="col-md-8  has-error has-feedback">
                           <input type="text" class="form-control" name="propertyExpenses"
                              id="propertyExpenses" value="0.00"
                              placeholder="<fmt:message key="createTaxReportForm.placeholder.propertyExpenses" />"
                           required>
                           <small id="exception" class="form-text text-danger">
                              <fmt:message key="${propertyExpensesException}"/>
                           </small>
                        </div>
                     </c:if>
                  </div>
               </div>
               <div class="col-md-offset-5 col-md-2">
                  <button type="submit" class="btn btn-default">
                     <fmt:message key="button.submit"/>
                  </button>
               </div>
            </div>
         </form>
      </div>
   </body>
</html>