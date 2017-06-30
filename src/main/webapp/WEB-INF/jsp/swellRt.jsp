<%-- 
    Document   : swellRt
    Created on : Feb 24, 2017, 7:29:56 PM
    Author     : nikos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Swellrt Enabled Page</title>

        
        <!-- SwellRT -->
        
        <!-- old
            <script src="http://demo.swellrt.org/swellrt.js"></script>
            <script src="https://demo.swellrt.org/swellrt-beta.js"></script>
        -->
        
        <script src="//localhost:9898/swellrt.js"></script>
               
        <!-- static content -->
        <c:url value="/js/jquery-3.1.1.min.js" var="jquery" />
        <%--<c:url value="/js/login_beta.js" var="loginJS" />--%>
        <c:url value="/js/login.js" var="loginJS" />
        <%--<c:url value="/img/preloader.gif" var="preloader" />--%>    
        <spring:url value="/img/preloader.gif" var="preloader"/>    
        
        
        
        <script src="${jquery}"></script>
    </head>

    <body>
        <h1>Hello from the Swellrt Enabled Page!</h1>
        <input type="hidden" id="token" value=${token}>

        <div id="mainContent" style="display:none">
            <div id="editor" style="border:1px solid black;"/></div>
        <br>
        Share With eid:
        <input type="text" id="shareEid">
        <button id="shareBtn">Share</button>
        <br>
        My collaborative documents:
        <ul id="itemsList"> </ul>
    </div>

    <div id="preloader" style="margin-left:50%">
        <img src="img/preloader.gif" width="128" height="128">
        <br>
        Initialising app please wait...
    </div>



    
</body>
</html>
