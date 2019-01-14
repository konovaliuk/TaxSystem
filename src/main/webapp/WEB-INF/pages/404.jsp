<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/navbar.jsp" %>   
<fmt:setLocale value="${fn:escapeXml(language)}"/>
<fmt:setBundle basename="internationalization.taxapp"/>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>
         <fmt:message key="404.title"/>
      </title>
   </head>
   <body>
      <div class="container">
         <div class="row pad-top text-center">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
               <h1>
                  <strong>${error == '404.resourceAccessDenied' ? '403' : '404'}! </strong>
                  <fmt:message key="404.errorIntro"/>
               </h1>
               <h3>
                  <fmt:message key="${error}"/>
               </h3>
               <a href="${pageContext.request.contextPath}/index.jsp"
                  class="btn btn-default btn-lg">
                  <strong>
                     <fmt:message
                        key="404.backHome"/>
                  </strong>
               </a>
            </div>
         </div>
      </div>
   </body>
</html>