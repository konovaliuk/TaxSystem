<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="currentPage"
   value="${pageContext.request.contextPath}/main/displayCreateFeedbackForm"/>
<%@include file="/WEB-INF/pages/navbar.jsp" %>
<fmt:setLocale value="${fn:escapeXml(language)}"/>
<fmt:setBundle basename="internationalization.taxapp"/>
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>
         <fmt:message key="createFeedbackForm.title"/>
      </title>
   </head>
   <body>
      <div class="container">
         <h2>
            <fmt:message key="createFeedbackForm.introduction"/>
         </h2>
         <hr>
      </div>
      <div class="row col-md-offset-3">
         <div class="col-md-8 col-xl-11">
            <form
               action="${pageContext.request.contextPath}/main/createFeedbackForm"
               method="POST">
               <div class="row">
                  <div class="col-md-6">
                     <div class="sm-form">
                        <label for="taxpayer">
                           <fmt:message key="taxpayer.name"/>
                           :
                        </label>
                        <input type="text" class="form-control" name="taxpayer"
                           value="${fn:escapeXml(taxpayer.name)}" readonly>
                     </div>
                  </div>
                  <div class="col-md-6">
                     <div class="sm-form">
                        <label for="taxpayer.registrationNumber">
                           <fmt:message
                              key="taxpayer.registrationNumber"/>
                           :
                        </label>
                        <input type="text" class="form-control"
                           name="taxpayer.registrationNumber"
                           value="${fn:escapeXml(taxpayer.registrationNumber)}" readonly>
                     </div>
                  </div>
               </div>
               <div class="row">
                  <div class="col-md-12">
                     <div class="sm-form">
                        <label for="initiator">
                           <fmt:message
                              key="taxpayer.employee"/>
                           :
                        </label>
                        <input type="text" class="form-control" name="initiator"
                           value="${fn:escapeXml(sessionScope.user.email)}" readonly>
                     </div>
                  </div>
               </div>
               <div class="row">
                  <div class="col-md-12">
                     <c:if test="${empty descriptionException}">
                        <div class="sm-form">
                           <label for="description">
                              <fmt:message
                                 key="feedbackForm.description"/>
                              :
                           </label>
                           <textarea id="description" name="description" rows="4"
                              class="form-control sm-textarea"
                              placeholder="<fmt:message key="feedbackForm.placeholder.description" />">${fn:escapeXml(descriptionProvided)}</textarea>
                        </div>
                     </c:if>
                     <c:if test="${not empty descriptionException}">
                        <div class="sm-form has-error has-feedback">
                           <label for="description">
                              <fmt:message
                                 key="feedbackForm.description"/>
                              :
                           </label>
                           <textarea id="description" name="description" rows="4"
                              class="form-control sm-textarea"
                              placeholder="<fmt:message key="feedbackForm.placeholder.description" />"></textarea>
                           <small id="exception" class="form-text text-danger">
                              <fmt:message key="${fn:escapeXml(descriptionException)}"/>
                           </small>
                        </div>
                     </c:if>
                  </div>
               </div>
               <div class="row">
                  <div class="form-group text-center">
                     <br>
                     <button type="submit" class="btn btn-default">
                        <fmt:message key="button.submit"/>
                     </button>
                  </div>
               </div>
            </form>
         </div>
      </div>
   </body>
</html>