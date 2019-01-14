<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/pages/navbar.jsp" %>
<fmt:setLocale value="${fn:escapeXml(language)}"/>
<fmt:setBundle basename="internationalization.taxapp"/>
<!DOCTYPE html>
<html>
   <head>
     <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>
         <fmt:message key="taxpayer.title"/>
      </title>
   </head>
   <body>
      <div class="container">
         <h2 class="text-left">
            <fmt:message key="taxpayer.introduction"/>
         </h2>
         <hr>
      </div>
      <form class="form-horizontal">
         <div class="container" style="margin-top: 20px;">
            <div class="row">
               <div class="form-group col-md-6">
                  <label for="name" class="col-md-4 control-label">
                     <fmt:message
                        key="taxpayer.name"/>
                     :
                  </label>
                  <div class="col-md-8">
                     <input type="text" class="form-control" name="name" id="name"
                        value="${fn:escapeXml(taxpayer.name)}" readonly>
                  </div>
               </div>
               <div class="form-group col-md-6">
                  <label for="registrationNumber" class="col-md-4 control-label">
                     <fmt:message key="taxpayer.registrationNumber"/>
                     :
                  </label>
                  <div class="col-md-8">
                     <input type="text" class="form-control" name="registrationNumber"
                        id="registrationNumber" value="${fn:escapeXml(taxpayer.registrationNumber)}"
                        readonly>
                  </div>
               </div>
            </div>
            <div class="row">
               <div class="form-group col-md-6">
                  <label for="email" class="col-md-4 control-label">
                     <fmt:message
                        key="taxpayer.email"/>
                     :
                  </label>
                  <div class="col-md-8">
                     <input type="text" class="form-control" name="email" id="email"
                        value="${fn:escapeXml(taxpayer.email)}" readonly>
                  </div>
               </div>
               <div class="form-group col-md-6">
                  <label for="postcode" class="col-md-4 control-label">
                     <fmt:message
                        key="taxpayer.postcode"/>
                     :
                  </label>
                  <div class="col-md-8">
                     <input type="text" class="form-control" name="postcode"
                        id="postcode" value="${fn:escapeXml(taxpayer.postcode)}" readonly>
                  </div>
               </div>
            </div>
            <div class="row">
               <div class="form-group col-md-6">
                  <label for="ownershipType" class="col-md-4 control-label">
                     <fmt:message
                        key="ownershipType"/>
                     :
                  </label>
                  <div class="col-md-8">
                     <input type="text" class="form-control" name="ownershipType"
                     id="ownershipType" value="<fmt:message key="ownershipType.${not empty taxpayer.ownershipType ? fn:escapeXml(taxpayer.ownershipType) : 'default'}"/>"
                     readonly>
                  </div>
               </div>
            </div>
         </div>
      </form>
   </body>
</html>