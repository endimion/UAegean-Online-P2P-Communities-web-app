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

        <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans" />
        <link rel="stylesheet" href="css/main.css">

        <style>
            #toast-container {
                top: auto !important;
                right: auto !important;
                bottom: 60%;
                left:30%;  
            }
        </style>


    </head>
    <body  > 


        <div  >
            <%@ include file="/WEB-INF/jsp/header.jsp" %>
        </div>

        <div class="container">


            <div class="row  mainContent">

                <form class="col s12 l8">


                    <div class="row breadCrumbs">
                        STEP | <b>GO</b>
                    </div>

                    <div class="row instructions">
                        <div class="col s12 flow-text hide-on-large-only">
                            Thank you for applying with us! <br>
                            You will now be directed to the eIDAS to securely identify 
                            and trustly provide us your identity attributes such as name, address,
                            etc. <br>
                            eIDAS will provide us with those attributes from the attribute providers you suggest
                            eIDAS will request your consent before sending us any information <br>
                            After authorization you will be redirected to our service<br>

                            First however, please your email in the following field to proceed with the
                            registration to the service. 

                        </div>
                        <div class="col s12 hide-on-med-and-down ">
                            Thank you for applying with us! <br>
                            You will now be directed to the eIDAS to securely identify 
                            and trustly provide us your identity attributes such as name, address,
                            etc. <br>
                            eIDAS will provide us with those attributes from the attribute providers you suggest
                            eIDAS will request your consent before sending us any information <br>
                            After authorization you will be redirected to our service<br>

                            First however, please your email in the following field to proceed with the
                            registration to the service. 

                        </div>
                    </div>
                    <div class="row" id="countrySelection-row">
                        <div class="input-field col s12 ">
                            <select id="countrySelection" class="icons">
                                <option value="" disabled selected></option>
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

                    </div>

                    <div class="row">
                        <div class="input-field inline col s12">
                            <input id="email" type="email" class="validate">
                            <label for="email" data-error="wrong" data-success="right">Email</label>
                        </div>


                        <div class="input-field col s12">
                            <input id="email2" type="email" class="validate">
                            <label for="email2">Retype Email</label>
                        </div>

                        <div class="input-field col s12">
                            <select id="typeOfLogin" class="icons">
                                <option value="eIDAS"  selected>eIDAS</option>
                                <option value="peps">PEPS</option>    
                            </select>
                            <label>Select means of identification </label>
                        </div>

                    </div>
                    <div class="row">
                        <div class="col s12 m12 l6">
                            <a class="waves-effect waves-light btn-large swell-btn cancel-btn" onclick="onCancelClick()">Cancel</a>
                        </div>
                        <div class="col s12 m12 l6">
                            <a id="next" class="waves-effect waves-light btn-large swell-btn next-btn" onclick="onNextClick()">Next</a>
                        </div>
                    </div>
                    <div class="row">
                        <div id ="element-container">
                            <div id="element"></div>
                        </div>
                    </div>
                </form>
                <div class="col s12 m12 l4">
                    <%@ include file="/WEB-INF/jsp/sidebar.jsp" %>
                </div>

            </div>



            <div class="row">
                <%@ include file="/WEB-INF/jsp/footer.jsp" %>
            </div>
        </div>






        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <!-- Compiled and minified JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/js/materialize.min.js"></script>

        <script>


                                $(document).ready(function () {
                                    $('select').material_select();
                                    if (!$('#countrySelection').val()) {
                                        $('#next').removeClass("waves-effect waves-light submit").addClass('disabled');
                                    }
                                    $('#countrySelection').change(function () {
                                        if (this.vaue !== "") {
                                            $('#next').removeClass("disabled").addClass('waves-effect waves-light submit');
                                        } else {
                                            $('#next').removeClass("waves-effect waves-light submit").addClass('disabled');
                                        }

                                    })
                                });


                                function onNextClick() {
                                    let token = "${token}";
                                    let email = $("#email").val();
                                    let email2 = $("#email2").val();

                                    let country = $("#countrySelection").val();
                                    let typeOfId = $('#typeOfLogin').val();
                                    let location = "";
                                    if (typeOfId === "eIDAS") {
                                        location = "https://stork-ap.aegean.gr:8443/ISSPlus/ValidateToken?t=${token}"
                                                + "&sp=${sp}&cc="
                                                + country + "&saml=eIDAS"
                                    } else {

                                        location = "https://stork-ap.aegean.gr:8443/ISSPlus/ValidateToken?t=${token}"
                                                + "&sp=${sp}"
                                                + "&cc=" + country;
                                    }

                                    if (email === email2) {
                                        if (email && validateEmail(email)) {
                                            window.location = location;
                                        } else {
                                            let $toastContent = $('<span>A valid email address is required!</span>');
                                            Materialize.toast($toastContent, 2000);
                                        }
                                    } else {
                                        let $toastContent = $('<span>Email addresses do not match!</span>');
                                        Materialize.toast($toastContent, 2000);
                                    }


                                }

                                function onCancelClick() {
                                    let token = "${token}";
                                    window.location = "authfail";
                                }

                                function validateEmail(email) {
                                    var atpos = email.indexOf("@");
                                    var dotpos = email.lastIndexOf(".");
                                    if (atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= email.length) {
                                        return false;
                                    }
                                    return true;
                                }


        </script>

    </body>
</html>
