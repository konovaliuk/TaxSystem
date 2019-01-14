<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="currentPage"
   value="${pageContext.request.contextPath}/main/displayCreateUser"/>
<%@include file="/WEB-INF/pages/navbar.jsp" %>
<fmt:setLocale value="${fn:escapeXml(language)}"/>
<fmt:setBundle basename="internationalization.taxapp"/>
<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>
         <fmt:message key="createUser.title"/>
      </title>
   </head>
   <body>
      <div class="container">
         <div>
            <h4>
               <fmt:message key="createUser.introduction"/>
            </h4>
            <hr>
         </div>
         <form role="form" class="form-horizontal"
            action="${pageContext.request.contextPath}/main/createUser"
            method="POST">
            <div class="form-group">
               <label class="control-label col-md-4" for="name">
                  <fmt:message
                     key="user.name"/>
                  :
               </label>
               <c:if test="${empty nameException}">
                  <div class="col-md-3">
                     <input type="text" class="form-control" id="name" name="name"
                        value="<c:out value="${nameProvided}"/>"
                        placeholder="<fmt:message key="createUser.placeholder.name" />"
                     required>
                  </div>
               </c:if>
               <c:if test="${not empty nameException}">
                  <div class="col-md-3  has-error has-feedback">
                     <input type="text" class="form-control" id="name" name="name"
                        placeholder="<fmt:message key="createUser.placeholder.name" />"
                     required>
                     <small id="exception" class="form-text text-danger">
                        <fmt:message key="${nameException}"/>
                     </small>
                  </div>
               </c:if>
            </div>
            <div class="form-group">
               <label class="control-label col-md-4" for="surname">
                  <fmt:message
                     key="user.surname"/>
                  :
               </label>
               <c:if test="${empty surnameException}">
                  <div class="col-md-3">
                     <input type="text" class="form-control" name="surname"
                      value="<c:out value="${surnameProvided}"/>"
                        placeholder="<fmt:message key="createUser.placeholder.surname" />"
                     required>
                  </div>
               </c:if>
               <c:if test="${not empty surnameException}">
                  <div class="col-md-3 has-error has-feedback">
                     <input type="text" class="form-control" name="surname"
                        placeholder="<fmt:message key="createUser.placeholder.surname" />"
                     required>
                     <small id="exception" class="form-text text-danger">
                        <fmt:message key="${surnameException}"/>
                     </small>
                  </div>
               </c:if>
            </div>
            <div class="form-group">
               <label class="control-label col-md-4" for="patronymic">
                  <fmt:message
                     key="user.patronymic"/>:
               </label>
               <c:if test="${empty patronymicException}">
                  <div class="col-md-3">
                     <input type="text" class="form-control" name="patronymic"
                        value="<c:out value="${patronymicProvided}"/>"
                        placeholder="<fmt:message key="createUser.placeholder.patronymic" />">
                  </div>
               </c:if>
               <c:if test="${not empty patronymicException}">
                  <div class="col-md-3 has-error has-feedback">
                     <input type="text" class="form-control" name="patronymic"
                        placeholder="<fmt:message key="createUser.placeholder.patronymic" />">
                     <small id="exception" class="form-text text-danger">
                        <fmt:message key="${patronymicException}"/>
                     </small>
                  </div>
               </c:if>
            </div>
            <div class="form-group">
               <label class="control-label col-md-4" for="email">
                  <fmt:message
                     key="user.email"/>
                  :
               </label>
               <c:if test="${empty emailException}">
                  <div class="col-md-3">
                     <input type="text" class="form-control" name="email"
                        value="<c:out value="${emailProvided}"/>"
                        placeholder="<fmt:message key="createUser.placeholder.email" />"
                     required>
                  </div>
               </c:if>
               <c:if test="${not empty emailException}">
                  <div class="col-md-3  has-error has-feedback">
                     <input type="text" class="form-control" name="email"
                        placeholder="<fmt:message key="createUser.placeholder.email" />"
                     required>
                     <small id="exception" class="form-text text-danger">
                        <fmt:message key="${emailException}"/>
                     </small>
                  </div>
               </c:if>
            </div>
            <div class="form-group">
               <label class="control-label col-md-4" for="username">
                  <fmt:message
                     key="user.username"/>
                  :
               </label>
               <c:if test="${empty usernameException}">
                  <div class="col-md-3">
                     <input type="text" class="form-control" name="username"
                        value="<c:out value="${usernameProvided}"/>"
                        placeholder="<fmt:message key="createUser.placeholder.username" />"
                     required>
                  </div>
               </c:if>
               <c:if test="${not empty usernameException}">
                  <div class="col-md-3  has-error has-feedback">
                     <input type="text" class="form-control" name="username"
                        placeholder="<fmt:message key="createUser.placeholder.username" />"
                     required>
                     <small id="exception" class="form-text text-danger">
                        <fmt:message key="${usernameException}"/>
                     </small>
                  </div>
               </c:if>
            </div>
            <div class="form-group">
               <label class="control-label col-md-4 " for="password">
                  <fmt:message
                     key="user.password"/>
                  :
               </label>
               <c:if test="${empty passwordException}">
                  <div class="col-md-3">
                     <input type="password" class="form-control" name="password"
                        placeholder="<fmt:message key="createUser.placeholder.password" />"
                     required>
                  </div>
               </c:if>
               <c:if test="${not empty passwordException}">
                  <div class="col-md-3 has-error has-feedback">
                     <input type="password" class="form-control" name="password"
                        placeholder="<fmt:message key="createUser.placeholder.password" />"
                     required>
                     <small id="exception" class="form-text text-danger">
                        <fmt:message key="${passwordException}"/>
                     </small>
                  </div>
               </c:if>
            </div>
            <div class="form-group">
               <label class="control-label col-md-4" for="confirmedPassword">
                  <fmt:message key="createUser.confirmedPassword"/>
                  :
               </label>
               <c:if test="${empty confirmedPasswordException}">
                  <div class="col-md-3">
                     <input type="password" class="form-control"
                        name="confirmedPassword"
                        placeholder="<fmt:message key="createUser.placeholder.confirmedPassword" />"
                     required>
                  </div>
               </c:if>
               <c:if test="${not empty confirmedPasswordException}">
                  <div class="col-md-3 has-error has-feedback">
                     <input type="password" class="form-control"
                        name="confirmedPassword"
                        placeholder="<fmt:message key="createUser.placeholder.confirmedPassword" />"
                     required>
                     <small id="exception" class="form-text text-danger">
                        <fmt:message key="${confirmedPasswordException}"/>
                     </small>
                  </div>
               </c:if>
            </div>
            <div class="form-group">
               <div class="col-md-offset-5 col-md-4">
                  <button type="submit" class="btn btn-default">
                     <fmt:message key="createUser.register"/>
                  </button>
               </div>
            </div>
         </form>
      </div>
   </body>
</html>