<%-- 
    Document   : createaccount
    Created on : Mar 14, 2017, 3:10:53 PM
    Author     : nikos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Create a new account</title>
        <!-- Compiled and minified CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="css/${css}"> 
    </head>
    <body>

        <%@ include file="/WEB-INF/jsp/header.jsp" %>
        <main>
            <div class="container">
                <div class="row  mainContent">

                    <div class="col s8 m8 l10" style="margin-top: 13%;">
                        <div class="container">
                            <div class="row ">
                                <div class="progress">
                                    <div class="indeterminate"></div>
                                </div>

                                <div id="message">

                                </div>
                            </div>






                        </div>
                    </div>
                    <!--                <div class="col s12 m2 l2">
                    <%--<%@ include file="/WEB-INF/jsp/sidebar.jsp" %>--%>
                </div>-->

                </div>

            </div>
        </main>
        <div class="row">
            <%@ include file="/WEB-INF/jsp/footer.jsp" %>
        </div>

        <input type="hidden" id="username" value=${username}>
        <input type="hidden" id="password" value=${password}>
        <input type="hidden" id="email" value=${email}>
        <input type="hidden" id="eid" value=${eid}>


    </div>

    <!--
        <script src="//mastihawonder-vm.aegean.gr:9898/swellrt.js"></script>-->
    <!--<script src="//localhost:9898/swellrt.js"></script>-->
    <!--Import jQuery before materialize.js-->
    <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <!-- Compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/js/materialize.min.js"></script>

    <script>
        $(document).ready(function () {
            $("#message").html("connecting to service...");
//            SwellRT.ready(function () {
            $("#message").html("processing request...");
            window.location = "//${server}/eID/"
                    + getParameterByName("t");
//            });
        });


        function getParameterByName(name, url) {
            if (!url) {
                url = window.location.href;
            }
            name = name.replace(/[\[\]]/g, "\\$&");
            var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
                    results = regex.exec(url);
            if (!results)
                return null;
            if (!results[2])
                return '';
            return decodeURIComponent(results[2].replace(/\+/g, " "));
        }


    </script>


</body>
</html>
