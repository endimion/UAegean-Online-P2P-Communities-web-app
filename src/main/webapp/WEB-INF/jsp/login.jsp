<%-- 
    Document   : createaccount
    Created on : Mar 14, 2017, 3:10:53 PM
    Author     : nikos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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


        <!- Overide the sidebar css -->
        <style>
            .sideBarClass{
                margin-top: 0;
            }

            .breadCrumbs{
                font-size: 18px;
                color:  #00be9f;
                padding-left: 0px;
            }

        </style>

    </head>
    <body>


        <%@ include file="/WEB-INF/jsp/header.jsp" %>

        <main>

            <div class="container">
                <div class="row  mainContent">
                    <div class="col s12 l10" style="
                         padding-left: 1.7%;
                         ">
                        <div class="container" style="width:90%">

                            <div class="row breadCrumbs">
                                <div class="col s12">
                                    STEP | <b>GO!</b>
                                </div>

                            </div>

                            <div class="row instructions">
                                <div  class="col s12 flow-text hide-on-large-only">
                                    <p>By clicking next you will be transfered to the, eID_EU Network  to securely authenticate with this application. <br> 
                                    </p>
                                    <p>
                                        Please recall, that using the eID_EU Network  you trustly provide us your identity attributes such as name, address,
                                        etc. <br>
                                        The eID_EU Network will provide us with those attributes from the attribute providers you suggest. <br>
                                        The eID_EU Network will request your consent before sending us any information. <br></p>

                                    After authorization you will be redirected to our service.<br>
                                    <p>To review the identification attributes that will be requested by eID_EU click 
                                        <a  href="#modal1">HERE</a>
                                    </p>
                                </div>
                                <div  class="col s12  hide-on-med-and-down ">
                                    <p>By clicking next you will be transfered to the eID_EU Network  to securely authenticate with this application. <br> 
                                    </p>
                                    <p>
                                        Please recall, that using the eID_EU Network you trustly provide us your identity attributes such as name, address,
                                        etc. <br>
                                        The eID_EU Network will provide us with those attributes from the attribute providers you suggest. <br>
                                        The eID_EU Network will request your consent before sending us any information. <br></p>

                                    After authorization you will be redirected to our service.<br>
                                    <p>To review the identification attributes that will be requested by eID_EU click 
                                        <a  href="#modal1">HERE</a>
                                    </p>
                                </div>
                            </div>


                            <div class="row">

                                <div class="input-field col s12">
                                    <select id="countrySelection" class="icons">
                                        <!--<option value="" disabled selected></option>-->
                                        <c:forEach var="country" items="${countries}">
                                            <c:if test = "${country.code == 'GR'}">
                                                <option selected value="${country.code}" data-icon="img/flags/${country.name}_flag.gif" >${fn:toUpperCase(country.name)}</option>
                                            </c:if>
                                            <c:if test = "${country.code != 'GR'}">
                                                <option value="${country.code}" data-icon="img/flags/${country.name}_flag.gif" >${fn:toUpperCase(country.name)}</option>
                                            </c:if>
                                        </c:forEach>

                                    </select>
                                    <label>Select Your Country of Origin</label>
                                </div>

                                <div class="input-field col s12" style="display:none">
                                    <select id="typeOfLogin" class="icons" >
                                        <option value="eIDAS"  >eID_EU Network</option>
                                        <option value="peps" selected>PEPS</option>    
                                    </select>
                                    <label>Select means of identification </label>
                                </div>


                                <div class="col s12 m12 l6" style="padding-top: 1rem;">
                                    <a class="waves-effect waves-light btn swell-btn cancel-btn" onclick="onCancelClick()">Cancel</a>
                                </div>
                                <div class="col s12 m12 l6" style="padding-top: 1rem;">
                                    <a id="next" class="waves-effect waves-light btn swell-btn next-btn" onclick="onNextClick()">Next</a>
                                </div>
                            </div>


                        </div>
                    </div>



                    <div class="col s12 m12 l2">
                        <%@ include file="/WEB-INF/jsp/sidebar.jsp" %>
                    </div>

                </div>

                <%@ include file="/WEB-INF/jsp/modalProps.jsp" %>



            </div>



        </main>

        <div class="row">
            <%@ include file="/WEB-INF/jsp/footer.jsp" %>
        </div>
        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <!-- Compiled and minified JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/js/materialize.min.js"></script>
        <script>

                                        $(document).ready(function () {
                                            $('select').material_select();
                                            // the "href" attribute of the modal trigger must specify the modal ID that wants to be triggered
                                            $('.modal').modal();

                                            if (!$('#countrySelection').val()) {
                                                $('#next').removeClass("waves-effect waves-light submit").addClass('disabled');
                                            }

                                            $('#countrySelection').change(function () {
                                                if (this.vaue !== "") {
                                                    $('#next').removeClass("disabled").addClass('waves-effect waves-light submit');
                                                } else {
                                                    $('#next').removeClass("waves-effect waves-light submit").addClass('disabled');
                                                }

                                            });
                                        });


                                        function onNextClick() {
                                            let country = $("#countrySelection").val();
                                            let typeOfId = $('#typeOfLogin').val();
                                            let location = "";
                                            if (typeOfId === "eIDAS") {
                                                //                                            location = "http://84.205.248.180/ISSPlus/ValidateToken?t=${token}"
                                                location = "${node}" + "?t=${token}"
                                                        + "&sp=${sp}"
                                                        + "&cc=" + country + "&saml=${samlType}";
                                            } else {
                                                location = "${node}" + "?t=${token}"
                                                        + "&sp=${sp}"
                                                        + "&cc=" + country + "&saml=${samlType}";
                                            }
                                            window.location = location;


                                        }

                                        function onCancelClick() {
                                            let token = "${token}";
                                            window.location = "authfail";
                                        }
        </script>
    </body>
</html>
