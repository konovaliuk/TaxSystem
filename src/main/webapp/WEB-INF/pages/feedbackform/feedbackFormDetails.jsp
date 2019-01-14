<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
   <c:when test="${sessionScope.user.type == 'CLIENT'}">
      <c:set var="currentPage"
         value="${pageContext.request.contextPath}/main/displayFeedbackFormDetailsByInitiator"/>
   </c:when>
   <c:otherwise>
      <c:set var="currentPage"
         value="${pageContext.request.contextPath}/main/displayFeedbackFormDetailsByReviewer"/>
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
         <fmt:message key="feedbackFormDetails.title"/>
      </title>
   </head>
   <body>
      <input type="hidden" name="feedbackForm" value="${feedbackForm.id}"
         form="languageForm">
      <div class="container">
         <div>
            <h2 class="text-left">
               <fmt:message key="feedbackFormDetails.introduction"/>
               :${fn:escapeXml(feedbackForm.id)}
            </h2>
            <hr>
         </div>
         <div class="row col-md-offset-3">
            <div class="col-md-8 col-xl-11">
               <form
                  action="${pageContext.request.contextPath}/main/updateFeedbackForm${sessionScope.user.type == 'SUPERVISOR' ? 'ByReviewer' : 'ByInitiator' }"
                  method="POST">
                  <div class="row">
                     <div class="col-md-6">
                        <div class="md-form">
                           <label for="taxpayer.name" class="">
                              <fmt:message
                                 key="taxpayer.name"/>
                              :
                           </label>
                           <input type="text" class="form-control" name="taxpayer.name"
                              value="${fn:escapeXml(feedbackForm.taxpayer.name)}" readonly>
                        </div>
                     </div>
                     <div class="col-md-6">
                        <div class="md-form">
                           <label for="taxpayer.registrationNumber" class="">
                              <fmt:message
                                 key="taxpayer.registrationNumber"/>
                              :
                           </label>
                           <input type="text" class="form-control"
                              name="taxpayer.registrationNumber"
                              value="${fn:escapeXml(feedbackForm.taxpayer.registrationNumber)}" readonly>
                        </div>
                     </div>
                  </div>
                  <div class="row">
                     <div class="col-md-6">
                        <div class="md-form">
                           <label for="creationDate" class="">
                              <fmt:message
                                 key="creationDate"/>
                              :
                           </label>
                           <input type="text" class="form-control" name="creationDate"
                              value="${fn:escapeXml(feedbackForm.creationDate)}" readonly>
                        </div>
                     </div>
                     <div class="col-md-6">
                        <div class="md-form">
                           <label for="lastModifiedDate" class="">
                              <fmt:message
                                 key="lastModifiedDate"/>
                              :
                           </label>
                           <input type="text" class="form-control" name="lastModifiedDate"
                              value="${fn:escapeXml(feedbackForm.lastModifiedDate)}" readonly>
                        </div>
                     </div>
                  </div>
                  <div class="row">
                     <div class="col-md-6">
                        <div class="md-form">
                           <label for="initiator" class="">
                              <fmt:message
                                 key="taxpayer.employee"/>:
                           </label>
                           <input type="text" class="form-control" name="initiator"
                              value="${fn:escapeXml(feedbackForm.initiator.email)}" readonly>
                        </div>
                     </div>
                     <div class="col-md-6">
                        <div class="md-form">
                           <label for="reviewer" class="">
                              <fmt:message
                                 key="feedbackForm.reviewer"/>
                              :
                           </label>
                           <input type="text" class="form-control" name="reviewer"
                              value="${fn:escapeXml(feedbackForm.reviewer.email)}" readonly>
                        </div>
                     </div>
                  </div>
                  <div class="row">
                     <div class="col-md-12">
                        <div class="md-form">
                           <label for="description">
                              <fmt:message
                                 key="feedbackForm.description"/>
                              :
                           </label>
                           <c:if test="${empty descriptionException}">
                              <textarea id="description" name="description" rows="4"
                              class="form-control md-textarea"
                              ${(sessionScope.user.type == 'CLIENT') && (feedbackForm.status == 'RETURNED') ? 'required' : 'readonly'}>${fn:escapeXml(not empty descriptionProvided ? descriptionProvided : feedbackForm.description)}</textarea>
                           </c:if>
                           <c:if test="${not empty descriptionException}">
                              <textarea id="description" name="description" rows="4"
                              class="form-control md-textarea"
                              ${(sessionScope.user.type == 'CLIENT') && (feedbackForm.status == 'RETURNED') ? 'required' : 'readonly'}>${fn:escapeXml(descriptionProvided)}</textarea>
                              <small id="exception" class="form-text text-danger">
                                 <fmt:message key="${descriptionException}"/>
                              </small>
                           </c:if>
                        </div>
                     </div>
                     <div class="col-md-12">
                        <div class="md-form">
                           <label for="reviewerComment">
                              <fmt:message
                                 key="feedbackForm.reviewerComment"/>
                              :
                           </label>
                           <c:if test="${empty reviewerCommentException}">
                              <textarea id="reviewerComment" name="reviewerComment" rows="4"
                              class="form-control md-textarea"
                              ${(sessionScope.user.type == 'SUPERVISOR') && (feedbackForm.status == 'IN_PROGRESS') ? 'required' : 'readonly'}>${fn:escapeXml(not empty reviewerCommentProvided ? reviewerCommentProvided : feedbackForm.reviewerComment)}</textarea>
                           </c:if>
                           <c:if test="${not empty reviewerCommentException}">
                              <textarea id="reviewerComment" name="reviewerComment" rows="4"
                              class="form-control md-textarea"
                              ${(sessionScope.user.type == 'SUPERVISOR') && (feedbackForm.status == 'IN_PROGRESS') ? 'required' : 'readonly'}></textarea>
                              <small id="exception" class="form-text text-danger">
                                 <fmt:message key="${reviewerCommentException}"/>
                              </small>
                           </c:if>
                        </div>
                     </div>
                  </div>
                  <div class="row">
                     <c:if
                        test="${(sessionScope.user.type == 'SUPERVISOR') && (feedbackForm.status == 'IN_PROGRESS')}">
                        <div class="form-group col-md-6">
                           <div class="radio">
                              <label><input type="radio" name="status" value="DONE"
                              ${feedbackForm.status == 'DONE' ? 'checked' : feedbackForm.status != 'RETURNED' ? 'checked' : ''}><fmt:message key="button.done"/></label>
                           </div>
                           <div class="radio">
                              <label><input type="radio" name="status"
                              value="RETURNED"
                              ${feedbackForm.status == 'RETURNED' ? 'checked' : ''}><fmt:message key="button.reject"/></label>
                           </div>
                        </div>
                     </c:if>
                     <c:if
                        test="${((sessionScope.user.type == 'SUPERVISOR') && (feedbackForm.status == 'IN_PROGRESS')) || ((sessionScope.user.type == 'CLIENT') && (feedbackForm.status == 'RETURNED'))}">
                        <div class="form-group col-md-6 text-center">
                           <input type="hidden" name="feedbackForm"
                              value="${feedbackForm.id}">
                           <br>
                           <button type="submit" class="btn btn-default">
                              <fmt:message key="button.submit"/>
                           </button><br><br>
                        </div>
                     </c:if>
                  </div>
               </form>
            </div>
         </div>
      </div>
   </body>
</html>