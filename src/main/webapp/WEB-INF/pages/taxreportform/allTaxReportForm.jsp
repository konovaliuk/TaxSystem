<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/navbar.jsp" %>   
<fmt:setLocale value="${fn:escapeXml(language)}"/>
<fmt:setBundle basename="internationalization.taxapp"/>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>
         <fmt:message key="allTaxReportForm.title"/>
      </title>
   </head>
   <body>
      <div class="container">
         <div>
            <h2>
               <fmt:message key="allTaxReportForm.introduction"/>
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
                     <fmt:message key="taxReportForm.reviewer"/>
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
                  <c:forEach items="${requestScope.taxReportFormList}"
                     var="taxReportForm">
               <tr>
               <td>${fn:escapeXml(taxReportForm.id)}</td>
               <td>${fn:escapeXml(taxReportForm.taxpayer.name)}</td>
               <td>${fn:escapeXml(taxReportForm.initiator.email)}</td>
               <td>${fn:escapeXml(taxReportForm.reviewer.email)}</td>
               <td>${fn:escapeXml(taxReportForm.creationDate)}</td>
               <td><fmt:message key="form.status.${fn:escapeXml(taxReportForm.status)}"/></td>
               <td><c:if
                  test="${(sessionScope.user.id == taxReportForm.initiator.id) || (sessionScope.user.id == taxReportForm.reviewer.id)}">
               <form class="text-center"
                  action="${pageContext.request.contextPath}/main/displayTaxReportFormDetails${sessionScope.user.type == 'INSPECTOR' ? 'ByReviewer' : 'ByInitiator'}">
               <input type="hidden" name="taxReportForm"
                  value="${fn:escapeXml(taxReportForm.id)}"/>
               <button type="submit" class="btn btn-default">
               <fmt:message key="button.edit"/>
               </button>
               </form>
               </c:if></td>
               <td><c:if
                  test="${(sessionScope.user.type == 'INSPECTOR') && (empty taxReportForm.reviewer.id)}">
               <form class="text-center"
                  action="${pageContext.request.contextPath}/main/changeTaxReportFormReviewer"
                  method="POST">
               <input type="hidden" name="user"
                  value="${fn:escapeXml(sessionScope.user.id)}"/> <input type="hidden"
                  name="taxReportForm" value="${fn:escapeXml(taxReportForm.id)}"/>
               <button type="submit" name="submit" class="btn btn-default">
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
               <c:set var="offset" value="${fn:escapeXml(requestScope.offset)}"/>
            </c:when>
            <c:otherwise>
               <c:set var="offset" value="0"/>
            </c:otherwise>
         </c:choose>
         <form class="text-center"
            action="${pageContext.request.contextPath}/main/display${fn:escapeXml(requestScope.command)}">
            <input type="hidden" name="offset" value="${fn:escapeXml(offset)}"/>
            <button type="submit" class="btn btn-default" name="submit"
               value=<c:out value="previous"/>>
               <fmt:message key="button.previous"/>
            </button>
            <button type="submit" class="btn btn-default" name="submit"
               value=<c:out value="next"/>>
               <fmt:message key="button.next"/>
            </button>
         </form>
      </div>
   </body>
</html>