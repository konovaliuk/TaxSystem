<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/navbar.jsp" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="internationalization.taxapp"/>
<!DOCTYPE html>
<html>
   <head>
      <title>
         <fmt:message key="login.title"/>
      </title>
   </head>
   <body>
      <div>
         <hr>
      </div>
      <div class="inner-wrapper">
         <div class="container">
            <div class="row">
               <div class="col-md-4 col-md-offset-4">
                  <form role="form" method="POST"
                     action="${pageContext.request.contextPath}/main/login">
                     <div class="form-group">
                        <label for="username">
                           <fmt:message key="login.username"/> :
                        </label>
                        <input type="text" class="form-control" name="username"
                           id="username"
                           placeholder="<fmt:message key="login.placeholder.username" />"
                        required>
                     </div>
                     <div class="form-group">
                        <label for="password">
                           <fmt:message key="login.password"/>:
                        </label>
                        <input type="password" class="form-control" name="password"
                           id="password"
                           placeholder="<fmt:message key="login.placeholder.password" />"
                        required>
                     </div>
                     <button type="submit" class="btn btn-default col-md-offset-4">
                        <fmt:message key="login.button.login"/>
                     </button>
                     <span>
                        <a
                           href="${pageContext.request.contextPath}/main/displayCreateUser">
                           <fmt:message
                              key="navbar.registration"/>
                        </a>
                     </span>
                  </form>
               </div>
               <c:if test="${not empty exception}">
                  <div class="col-md-8 col-md-offset-4 text-danger">
                     <fmt:message key="${exception}"/>
                  </div>
               </c:if>
            </div>
         </div>
      </div>
   </body>
</html>