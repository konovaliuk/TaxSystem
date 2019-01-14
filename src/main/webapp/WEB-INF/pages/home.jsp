<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/navbar.jsp" %>
<fmt:setLocale value="${fn:escapeXml(language)}"/>
<fmt:setBundle basename="internationalization.taxapp"/>
<c:if test="${not empty redirect}">
   <c:redirect url="../index.jsp"></c:redirect>
</c:if>
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>
         <fmt:message key="home.title"/>
      </title>
   </head>
   <body>
      <div class="container" align="center">
         <br><br><br><br><br>
         <c:if test="${ empty sessionScope.user}">
            <form action="${pageContext.request.contextPath}/main/login" method="POST">
               <div class="row">
                  <div class="col-sm-4 col-sm-offset-4">
                     <label for="username">
                        <fmt:message key="user.username"/>:
                     </label><br>
                     <input type="text" class="form-control" name="username" id="username"  placeholder="<fmt:message key="login.placeholder.username" />" required>
                  </div>
                  <div class="col-sm-4 col-sm-offset-4">
                     <label for="password">
                        <fmt:message key="user.password"/>:
                     </label><br>
                     <input type="password" class="form-control" name="password" id="password" placeholder="<fmt:message key="login.placeholder.password" />" required>
                     <c:if test="${not empty exception}">
                        <small id="exception" class="form-text text-danger">
                           <fmt:message key="${exception}"/>
                        </small>
                     </c:if>
                  </div>
               </div>
               <br>
               <button type="submit" class="btn btn-primary">
                  <fmt:message key="login.button.login"/>
               </button>
            </form>
         </c:if>
      </div>
   </body>
</html>