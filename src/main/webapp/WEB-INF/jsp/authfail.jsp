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
        <title>Authentication failed</title>
        <!-- Compiled and minified CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="css/${css}">
    
        <!- Overide the sidebar css -->
        <style>
            .sideBarClass{
                   margin-top: 0;
            }
            
        </style>
    
    
    </head>
    <body>

        
            <div  >
                <%@ include file="/WEB-INF/jsp/header.jsp" %>
            </div>
            <div class="container"> 
            <div class="row breadCrumbs">
                STEP | <b>RETURN</b>
            </div>


            <div class="row  mainContent">
                <div class="col s12 m12 l8">
                    <div class="container">
                        <div class="row instructions">
                            <div class="col s12 flow-text hide-on-large-only">
                                Error processing request. <br>
                                The token you provided is not valid!<br>

                                Please go to the starting page and reauthorize the application, using the eIDAS system
                            </div>
                            <div class="col s12 hide-on-med-and-down ">
                                <h4>Error processing request.</h4>
                                An error occurred during the authentication process!<br>
                                Please, return to the home page and reauthorize the application, using the eIDAS system.
                            </div>
                        </div>
                        <div class="row">
                            <div class="col s12 m12 l6">
                                <a class="waves-effect waves-light btn swell-btn next-btn" onclick="onHomeClick()">Home</a>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="col s4 m4 l4">
                    <%@ include file="/WEB-INF/jsp/sidebar.jsp" %>
                </div>

            </div>

            <div class="row">
                <%@ include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
            </div>
       
        <script>
            function onHomeClick() {
                let token = "${token}";
                window.location = "http://${server}"; // "http://community.mastihawonder.com/";
            }//
        </script>
        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <!-- Compiled and minified JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/js/materialize.min.js"></script>
    </body>
</html>
