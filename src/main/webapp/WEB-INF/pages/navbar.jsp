<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<c:set var="language"
   value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
   scope="session"/>
<fmt:setLocale value="${fn:escapeXml(language)}"/>
<fmt:setBundle basename="internationalization.taxapp"/>
<!DOCTYPE html>
<html lang=<c:out value="${language}"/>>
   <head>
      <title>
         <fmt:message key="navbar.title"/>
      </title>
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet"
         href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <script
         src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
      <script
         src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   </head>
   <body>
      <nav class="navbar-light navbar-expand-md static-top" style="background-color: #e3f2fd;">
         <div class="container-fluid">
            <div class="navbar-header">
               <a class="navbar-brand"
                  href="${pageContext.request.contextPath}/index.jsp">
                  <fmt:message
                     key="navbar.home"/>
               </a>
            </div>
            <c:if test="${not empty sessionScope.user}">
               <ul class="nav navbar-nav">
                  <c:if test="${(sessionScope.user.type == 'CLIENT') || (sessionScope.user.type == 'SUPERVISOR')}">
                     <li class="dropdown">
                        <a class="dropdown-toggle"
                           data-toggle="dropdown" href="#">
                           <fmt:message
                              key="navbar.feedbackForm"/>
                           <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                           <c:if test="${sessionScope.user.type == 'CLIENT'}">
                              <li>
                                 <a
                                    href="${pageContext.request.contextPath}/main/displayAllFeedbackFormByInitiator">
                                    <fmt:message key="navbar.allFeedbackFormByInitiator"/>
                                 </a>
                              </li>
                              <li>
                                 <a
                                    href="${pageContext.request.contextPath}/main/displayCreateFeedbackForm">
                                    <fmt:message key="navbar.createFeedbackForm"/>
                                 </a>
                              </li>
                           </c:if>
                           <c:if test="${sessionScope.user.type == 'SUPERVISOR'}">
                              <li>
                                 <a
                                    href="${pageContext.request.contextPath}/main/displayAllFeedbackFormByReviewer">
                                    <fmt:message key="navbar.allFeedbackFormByReviewer"/>
                                 </a>
                              </li>
                              <li>
                                 <a
                                    href="${pageContext.request.contextPath}/main/displayAllFeedbackFormTodo">
                                    <fmt:message key="navbar.allFeedbackFormTodo"/>
                                 </a>
                              </li>
                           </c:if>
                        </ul>
                     </li>
                  </c:if>
                  <c:if test="${(sessionScope.user.type == 'CLIENT') || (sessionScope.user.type == 'INSPECTOR') || (sessionScope.user.type == 'SUPERVISOR')}">
                     <li class="dropdown">
                        <a class="dropdown-toggle"
                           data-toggle="dropdown" href="#">
                           <fmt:message
                              key="navbar.taxReportForm"/>
                           <span class="caret"></span>
                        </a>	
                        <ul class="dropdown-menu">
                           <c:if test="${sessionScope.user.type == 'SUPERVISOR'}">
                              <li>
                                 <a
                                    href="${pageContext.request.contextPath}/main/displayChangeTaxReportFormReviewer">
                                    <fmt:message
                                       key="navbar.changeTaxReportFormReviewer"/>
                                 </a>
                              </li>
                           </c:if>
                           <c:if test="${sessionScope.user.type == 'CLIENT'}">
                              <li>
                                 <a
                                    href="${pageContext.request.contextPath}/main/displayAllTaxReportFormByInitiator">
                                    <fmt:message key="navbar.allTaxReportFormByInitiator"/>
                                 </a>
                              </li>
                              <li>
                                 <a
                                    href="${pageContext.request.contextPath}/main/displayCreateTaxReportForm">
                                    <fmt:message key="navbar.createTaxReportForm"/>
                                 </a>
                              </li>
                           </c:if>
                           <c:if test="${sessionScope.user.type == 'INSPECTOR'}">
                              <li>
                                 <a
                                    href="${pageContext.request.contextPath}/main/displayAllTaxReportFormByReviewer">
                                    <fmt:message key="navbar.allTaxReportFormByReviewer"/>
                                 </a>
                              </li>
                              <li>
                                 <a
                                    href="${pageContext.request.contextPath}/main/displayAllTaxReportFormTodo">
                                    <fmt:message key="navbar.allTaxReportFormTodo"/>
                                 </a>
                              </li>
                           </c:if>
                        </ul>
                     </li>
                  </c:if>
                  <c:if test="${sessionScope.user.type == 'ADMIN'}">
                     <li class="dropdown">
                        <a class="dropdown-toggle"
                           data-toggle="dropdown" href="#">
                           <fmt:message
                              key="navbar.admin"/>
                           <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                           <li>
                           <li>
                              <a href="${pageContext.request.contextPath}/main/displayChangeUserDeletionStatus">
                                 <fmt:message key="navbar.admin.changeUserDeletionStatus"/>
                              </a>
                           </li>
                           <li>
                              <a href="${pageContext.request.contextPath}/main/displayChangeUserType">
                                 <fmt:message key="navbar.admin.changeUserType"/>
                              </a>
                           </li>
                        </ul>
                     </li>
                  </c:if>
                  <c:if test="${sessionScope.user.type == 'CLIENT'}">
                     <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/main/displayTaxpayerDetails">
                           <fmt:message key="navbar.taxpayer"/>
                        </a>
                     </li>
                  </c:if>
               </ul>
            </c:if>
            <ul class="nav navbar-nav navbar-right">
               <li>
                  <c:if test="${empty sessionScope.user}">
                     <a class="navbar-brand"
                        href="${pageContext.request.contextPath}/main/displayCreateUser">
                        <fmt:message key="navbar.registration"/>
                     </a>
                  </c:if>
               </li>
               <li>
                  <c:if test="${not empty sessionScope.user}">
                     <a class="navbar-brand"
                        href="${pageContext.request.contextPath}/main/logout">
                        <fmt:message key="login.button.logout"/>
                     </a>
                  </c:if>
               <li>
                  <form class="navbar-brand" id="languageForm"
                     action="${fn:escapeXml(pageScope.currentPage)}">
                     <select class="my-2 my-sm-0" id="language" name="language"
                        onchange="submit()">
                        <option value=<c:out value="en_US"/> ${language == 'en_US' ? 'selected' : ''}>
                        <fmt:message key="language.en"/>
                        </option>
                        <option value=<c:out value="ru_RU"/> ${language == 'ru_RU' ? 'selected' : ''}>
                        <fmt:message key="language.ru"/>
                        </option>
                     </select>
                  </form>
               </li>
            </ul>
         </div>
      </nav>
      <footer class="navbar-fixed-bottom row-fluid" style="background-color: #e3f2fd;">
         <div class="container">
            <p class="text-muted" align="center">
               <fmt:message key="navbar.footer.contacts"/>: <c:out value="taxsystem.support@mail.ru"/>
            </p>
         </div>
      </footer>
   </body>
</html>