<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/navbar.jsp" %>
<fmt:setLocale value="${fn:escapeXml(language)}"/>
<fmt:setBundle basename="internationalization.taxapp"/>
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>
         <fmt:message key="allFeedbackForm.title"/>
      </title>
   </head>
   <body>
      <body>
         <div class="container">
            <div>
               <h2>
                  <fmt:message key="allFeedbackForm.introduction"/>
               </h2>
               <hr>
            </div>
            <table class="table">
               <thead>
                  <tr>
                     <th scope="col">#</th>
                     <th scope="col">
                        <fmt:message key="taxpayer.taxpayer"/>
                     </th>
                     <th scope="col">
                        <fmt:message key="taxpayer.employee"/>
                     </th>
                     <th scope="col">
                        <fmt:message key="feedbackForm.reviewer"/>
                     </th>
                     <th scope="col">
                        <fmt:message key="creationDate"/>
                     </th>
                     <th scope="col">
                        <fmt:message key="form.status"/>
                     </th>
                     <th colspan="2" class="text-center">
                        <fmt:message key="action"/>
                     </th>
                  </tr>
               </thead>
               <tbody>
                  <tr>
                     <c:forEach items="${requestScope.feedbackFormList}"
                        var="feedbackForm">
                  <tr>
                  <td>${fn:escapeXml(feedbackForm.id)}</td>
                  <td>${fn:escapeXml(feedbackForm.taxpayer.name)}</td>
                  <td>${fn:escapeXml(feedbackForm.initiator.email)}</td>
                  <td>${fn:escapeXml(feedbackForm.reviewer.email)}</td>
                  <td>${fn:escapeXml(feedbackForm.creationDate)}</td>
                  <td><fmt:message key="form.status.${fn:escapeXml(feedbackForm.status)}"/></td>
                  <td><c:if
	                     test="${(sessionScope.user.id == feedbackForm.initiator.id) || (sessionScope.user.id == feedbackForm.reviewer.id)}">
	                  <form class="text-center"
		                     action="${pageContext.request.contextPath}/main/displayFeedbackFormDetails${sessionScope.user.type == 'SUPERVISOR' ? 'ByReviewer' : 'ByInitiator'}">
		                  <input type="hidden" name="feedbackForm"
		                     value="${feedbackForm.id}"/>
		                  <button type="submit" class="btn btn-default">
		                  <fmt:message key="button.edit"/>
		                  </button>
	                  </form>
                  </c:if></td>
                  <td><c:if
                     test="${(sessionScope.user.type == 'SUPERVISOR') && (empty feedbackForm.reviewer.id)}">
                  <form class="text-center"
                     action="${pageContext.request.contextPath}/main/changeFeedbackFormReviewer"
                     method="POST">
                  <input type="hidden" name="user"
                     value="${sessionScope.user.id}"/> <input type="hidden"
                     name="feedbackForm" value="${feedbackForm.id}"/>
                  <button type="submit" name="submit" class="btn btn-default"
                     value="${feedbackForm.id}">
                  <fmt:message key="button.assignMe"/>
                  </button>
                  </form>
                  </c:if></td>
                  </tr>
                  </c:forEach>
                  </tr>
               </tbody>
            </table>
            <c:choose>
               <c:when test="${not empty requestScope.offset}">
                  <c:set var="offset" value="${requestScope.offset}"/>
               </c:when>
               <c:otherwise>
                  <c:set var="offset" value="0"/>
               </c:otherwise>
            </c:choose>
            <form class="text-center"
               action="${pageContext.request.contextPath}/main/display${requestScope.command}"
               method="GET">
               <input type="hidden" name="offset" value="${offset}"/>
               <button type="submit" class="btn btn-default" name="submit"
                  value="previous">
                  <fmt:message key="button.previous"/>
               </button>
               <button type="submit" class="btn btn-default" name="submit"
                  value="next">
                  <fmt:message key="button.next"/>
               </button>
            </form>
         </div>
   </body>
</html>