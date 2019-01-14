<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="currentPage"
   value="${pageContext.request.contextPath}/main/displayCreateTaxpayer"/>
<%@include file="/WEB-INF/pages/navbar.jsp" %>
<fmt:setLocale value="${fn:escapeXml(language)}"/>
<fmt:setBundle basename="internationalization.taxapp"/>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>
         <fmt:message key="createTaxpayer.title"/>
      </title>
   </head>
   <body>
      <div class="container">
         <h4>
            <fmt:message key="createTaxpayer.introduction"/>
         </h4>
         <hr>
      </div>
      <form role="form" class="form-horizontal"
         action="${pageContext.request.contextPath}/main/createTaxpayer"
         method="POST">
         <div class="form-group">
            <label class="control-label col-md-4" for="name">
               <fmt:message
                  key="taxpayer.name"/>
               :
            </label>
            <c:if test="${empty nameException}">
               <div class="col-md-3">
                  <input type="text" class="form-control" id="name" name="name"
                     value="${fn:escapeXml(nameProvided)}"
                     placeholder="<fmt:message key="createTaxpayer.placeholder.name" />"
                  required>
               </div>
            </c:if>
            <c:if test="${not empty nameException}">
               <div class="col-md-3  has-error has-feedback">
                  <input type="text" class="form-control" id="name" name="name"
                     placeholder="<fmt:message key="createTaxpayer.placeholder.name" />"
                  required>
                  <small id="exception" class="form-text text-danger">
                     <fmt:message key="${nameException}"/>
                  </small>
               </div>
            </c:if>
         </div>
         <div class="form-group">
            <label class="control-label col-md-4" for="registrationNumber">
               <fmt:message key="taxpayer.registrationNumber"/>
               :
            </label>
            <c:if test="${empty registrationNumberException}">
               <div class="col-md-3">
                  <input type="text" class="form-control" name="registrationNumber"
                     value="${fn:escapeXml(registrationNumberProvided)}"
                     placeholder="<fmt:message key="createTaxpayer.placeholder.registrationNumber" />"
                  required>
               </div>
            </c:if>
            <c:if test="${not empty registrationNumberException}">
               <div class="col-md-3 has-error has-feedback">
                  <input type="text" class="form-control" name="registrationNumber"
                     placeholder="<fmt:message key="createTaxpayer.placeholder.registrationNumber" />"
                  required>
                  <small id="exception" class="form-text text-danger">
                     <fmt:message key="${registrationNumberException}"/>
                  </small>
               </div>
            </c:if>
         </div>
         <div class="form-group">
            <label class="control-label col-md-4" for="email">
               <fmt:message
                  key="taxpayer.email"/>
               :
            </label>
            <c:if test="${empty emailException}">
               <div class="col-md-3">
                  <input type="text" class="form-control" name="email"
                     value="${fn:escapeXml(emailProvided)}"
                     placeholder="<fmt:message key="createTaxpayer.placeholder.email" />">
               </div>
            </c:if>
            <c:if test="${not empty emailException}">
               <div class="col-md-3 has-error has-feedback">
                  <input type="text" class="form-control" name="email"
                     placeholder="<fmt:message key="createTaxpayer.placeholder.email" />">
                  <small id="exception" class="form-text text-danger">
                     <fmt:message key="${emailException}"/>
                  </small>
               </div>
            </c:if>
         </div>
         <div class="form-group">
            <label class="control-label col-md-4" for="postcode">
               <fmt:message
                  key="taxpayer.postcode"/>
               :
            </label>
            <c:if test="${empty postcodeException}">
               <div class="col-md-3">
                  <input type="text" class="form-control" name="postcode"
                     value="${fn:escapeXml(postcodeProvided)}"
                     placeholder="<fmt:message key="createTaxpayer.placeholder.postcode" />"
                  required>
               </div>
            </c:if>
            <c:if test="${not empty postcodeException}">
               <div class="col-md-3">
                  <input type="text" class="form-control" name="postcode"
                     placeholder="<fmt:message key="createTaxpayer.placeholder.postcode" />"
                  required>
                  <small id="exception" class="form-text text-danger">
                     <fmt:message key="${postcodeException}"/>
                  </small>
               </div>
            </c:if>
         </div>
         <div class="form-group">
            <label for="ownershipType" class="control-label col-md-4">
               <fmt:message
                  key="ownershipType"/>
               :
            </label>
            <div class="col-md-3">
               <select class="form-control" id="ownershipType" name="ownershipType">
                  <c:forEach items="${requestScope.ownershipTypeList}" var="ownershipType">
                     <option value="${fn:escapeXml(ownershipType.toString())}"
                     ${ownershipType.toString() == ownershipTypeProvided ? 'selected' : ''}>
                     <fmt:message
                        key="ownershipType.${fn:escapeXml(ownershipType.toString())}"/>
                     </option>
                  </c:forEach>
               </select>
            </div>
         </div>
         <div class="form-group">
            <div class="col-md-11 text-center">
               <button type="submit" class="btn btn-default">
                  <fmt:message key="createUser.register"/>
               </button>
            </div>
         </div>
      </form>
   </body>
</html>