<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/navbar.jsp" %>
<fmt:setLocale value="${fn:escapeXml(language)}" />
<fmt:setBundle basename="internationalization.taxapp" />
<!DOCTYPE html">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   </head>
   <body>
      <div class="container">
         <div class="row pad-top text-center">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
               <h1>
                  <strong>
                     <fmt:message key="exceptionPage.begin" />
                     !
                  </strong>
               </h1>
               <h3>
                  <fmt:message key="exceptionPage.exceptionOccured" />
                  ...
               </h3>
               <br><br>
               <c:choose>
                  <c:when test="${not empty requestScope.exception}">
                     <h4>
                        <fmt:message key="${exception}" />
                     </h4>
                  </c:when>
                  <c:otherwise>
                     <fmt:message key="${exception.generalException}" />
                     <br><br><br>
                     <h4>
                        <fmt:message key="exceptionPage.response" />
                        : <kbd><c:out value="taxsystem.support@mail.ru"/></kbd>
                     </h4>
                  </c:otherwise>
               </c:choose>
               <br>
               <a href="${pageContext.request.contextPath}/index.jsp"
                  class="btn btn-default btn-lg">
                  <strong>
                     <fmt:message
                        key="404.backHome" />
                  </strong>
               </a>
            </div>
         </div>
      </div>
   </body>
   </body>
</html>