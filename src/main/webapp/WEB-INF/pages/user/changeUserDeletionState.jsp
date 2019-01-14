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
         <fmt:message key="changeUserDeletionStatus.title"/>
      </title>
   </head>
   <body>
      <div class="container">
         <div>
            <h4 class="text-left">
               <fmt:message key="changeUserDeletionStatus.introduction"/>
            </h4>
            <hr>
         </div>
         <div class="row">
            <div class="col-md-8 col-md-offset-2">
               <form role="form" method="POST"
                  action="${pageContext.request.contextPath}/main/changeUserDeletionStatus">
                  <div class="form-group">
                     <label for="user" >
                        <fmt:message key="changeUserDeletionStatus.user"/>
                     </label>
                     <select class="form-control" id="user" name="user">
                        <c:forEach items="${requestScope.userList}"
                           var="user">
                           <option value="${fn:escapeXml(user.id)}">
                              ${fn:escapeXml(user.name)} ${fn:escapeXml(user.surname)} ${fn:escapeXml(user.patronymic)}, ${fn:escapeXml(user.email)},
                              <fmt:message key="user.status"/>:
                              <fmt:message key="user.status.${fn:escapeXml(user.deleted)}"/>
                           </option>
                        </c:forEach>
                     </select>
                  </div>
                  <div class="form-group">
                     <label for="deleted">
                        <fmt:message key="changeUserDeletionStatus.options"/>
                     </label>
                     <select class="form-control" id="user" name="deleted">
                        <option value="false">
                           <fmt:message key="user.status.false"/>
                        </option>
                        <option value="true">
                           <fmt:message key="user.status.true"/>
                        </option>
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