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
        <title>Create a new account</title>
        <!-- Compiled and minified CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
          <link rel="stylesheet" href="css/main.css">
    </head>
    <body>

        <div class="container"> 
            <div class="row">
                <%@ include file="/WEB-INF/jsp/header.jsp" %>
            </div>

            <div class="row  mainContent">
                <div class="col s12 m12 l8">
                    <div class="container">
                        <div class="row instructions">
                            <div class="col s12">
                                Information about this service
                            </div>
                        </div>

                        <div class="row">
                            <div class="col s12 m12 l6">
                                <a class="waves-effect waves-light btn swell-btn" onclick="onCancelClick()">Home</a>
                            </div>
                        </div>


                    </div>
                </div>
            </div>
            <div class="row">
                <%@ include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
        </div>
        <script>
           function onCancelClick() {
                 window.location = "http://community.mastihawonder.com/";
            }
        </script>
        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <!-- Compiled and minified JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/js/materialize.min.js"></script>
    </body>
</html>
