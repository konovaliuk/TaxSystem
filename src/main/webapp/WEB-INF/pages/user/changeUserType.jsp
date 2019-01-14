<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/navbar.jsp" %>         
<fmt:setLocale value="${fn:escapeXml(language)}"/>
<fmt:setBundle basename="internationalization.taxapp"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="changeUserType.title"/></title>
</head>
<body>
    <div class="container">
    <div>
    <h4 class="text-left">
        <fmt:message
                key="changeUserType.introduction"/>
    </h4>
    <hr>
</div>
       <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <form role="form" method="POST"
                      action="${pageContext.request.contextPath}/main/changeUserType">
                    <div class="form-group">
                       <label for="user" ><fmt:message key="changeUserType.user"/></label> 
				           <select class="form-control" id="user" name="user">
					        <c:forEach items="${requestScope.userList}"
					                   var="user">
					            <option value="${fn:escapeXml(user.id)}">
					                  ${fn:escapeXml(user.name)} ${fn:escapeXml(user.surname)} ${fn:escapeXml(user.patronymic)}, <fmt:message key="user.type.${fn:escapeXml(user.type)}"/>, ${fn:escapeXml(user.email)}</option>
					        </c:forEach>
				    		</select>
                    </div>
                   <div class="form-group">
                        <label for="type"> <fmt:message key="changeUserType.options"/>
                        </label> <select class="form-control" id="type" name="type">
                        <c:forEach items="${requestScope.typeList}" var="type">
                            <option value="${fn:escapeXml(type)}">
                                     <fmt:message key="user.type.${type}"/></option>
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