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
         <fmt:message key="changeTaxReportFormReviewer.title"/>
      </title>
   </head>
   <body>
      <div class="container">
         <h2 class="text-left">
            <fmt:message key="changeTaxReportFormReviewer.introduction"/>
         </h2>
         <hr>
      </div>
      <div class="container">
         <div class="row">
            <div class="col-md-8 col-md-offset-2">
               <form role="form" method="POST"
                  action="${pageContext.request.contextPath}/main/changeTaxReportFormReviewer">
                  <div class="form-group">
                     <label for="taxReportForm">
                        <fmt:message
                           key="changeTaxReportForm.taxReportForm"/>
                     </label>
                     <select class="form-control" id="taxReportForm"
                        name="taxReportForm">
                        <c:forEach items="${requestScope.taxReportFormList}"
                           var="taxReportForm">
                           <option value="${fn:escapeXml(taxReportForm.id)}">
                           		  ${fn:escapeXml(taxReportForm.taxpayer.registrationNumber)}, 
                           	 	  ${fn:escapeXml(taxReportForm.id)},
	                              ${fn:escapeXml(taxReportForm.creationDate)},
	                              ${fn:escapeXml(taxReportForm.taxpayer.name)},
	                              ${fn:escapeXml(taxReportForm.reviewer.name)},
	                              ${fn:escapeXml(taxReportForm.reviewer.email)}
                           </option>
                        </c:forEach>
                     </select>
                  </div>
                  <div class="form-group">
                     <label for="password">
                        <fmt:message
                           key="changeTaxReportForm.reviewer"/>
                     </label>
                     <select class="form-control" id="user" name="user">
                        <c:forEach items="${requestScope.userList}" var="user">
                           <option value="${fn:escapeXml(user.id)}">ID:${fn:escapeXml(user.id)},
                              ${fn:escapeXml(user.surname)} ${fn:escapeXml(user.name)}, ${fn:escapeXml(user.email)}
                           </option>
                        </c:forEach>
                     </select>
                  </div>
                  <button type="submit" class="btn btn-default col-md-offset-5">
                     <fmt:message key="button.submit"/>
                  </button>
               </form>
            </div>
         </div>
      </div>
   </body>
</html>