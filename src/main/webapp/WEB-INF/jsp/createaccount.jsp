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
        <!--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css"></link>-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css"/>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

        <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans" />
        <link rel="stylesheet" href="css/${css}">
        <link rel="icon"
              type="image/x-icon"
              href="favicon.ico">
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


        <%@ include file="/WEB-INF/jsp/header.jsp" %>

        <main>
            <div class="container">


                <div class="row  mainContent">

                    <form class="col s12 l10">
                        <div class="container" style="width:90%">

                            <div class="row breadCrumbs">
                                <div class="col s12" style="font-size: larger;">
                                    STEP | <b>GO!</b>
                                </div>

                            </div>
                            <div class="col s12">
                                <div class="row instructions">
                                    <div id="mobile" class="col s12 flow-text hide-on-large-only">
                                        <p>
                                            <b>Identify with eIDAS eID:</b> You will now be directed to the eIDAS Network to securely identify and trustly 
                                            provide us your main identity attributes. Review the identification attributes that will be requested  <a class="modal-trigger" href="#modal1">HERE</a>
                                        </p>
                                        <p>
                                            The eIDAS Network will provide us with those attributes from the attribute provider you will suggest (eID-EU)

                                            The eIDAS Network will request your consent before sending us any information 
                                            After successful authentication you will be redirected to our service
                                        </p>
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

                                            <div class="row" style="margin-bottom: 0px;">
                                                <div class="input-field inline col s12">
                                                    <input id="email" type="email" class="validate" style="width:98%;margin-left:1%;">
                                                    <label for="email" data-error="wrong" data-success="right">Email</label>
                                                </div>


                                                <div class="input-field col s12" style="width:98%;margin-left:1%;">
                                                    <input id="email2" type="email" class="validate">
                                                    <label for="email2">Retype Email</label>
                                                </div>

                                                <div class="input-field col s12"  style="display:none">
                                                    <select id="typeOfLogin" class="icons" >
                                                        <option value="eIDAS"  >eID_EU Network</option>
                                                        <option value="peps" selected>PEPS  </option>    
                                                    </select>
                                                    <label>Select means of identification </label>
                                                </div>

                                            </div>


                                            <div class="col s12 m12 l6" style="padding-top: 1rem;">
                                                <a class="waves-effect waves-light btn swell-btn cancel-btn" onclick="onCancelClick()">Cancel</a>
                                            </div>
                                            <div class="col s12 m12 l6" style="padding-top: 1rem;">
                                                <a id="next" class="waves-effect waves-light btn swell-btn next-btn" onclick="onNextClick()">Next</a>
                                            </div>
                                        </div>

                                        <c:if test = "${linkedIn == true}">
                                            <!--<span style="color:#446eff;font-weight: bold;"></span>-->
                                            <b>Identify with LinkedIn:</b> 
                                            <img  onclick="linkedInClick()" 
                                                  src='img/linkedIn.png'
                                                  style="
                                                  cursor: pointer;
                                                  width: 14rem;
                                                  margin-left: 1rem;
                                                  vertical-align: bottom;"/>
                                        </c:if>


                                        <p> 
                                            <span style="color:#446eff;font-weight: bold;">Identify with UAegean credentials </span> students | faculty | staff:  
                                            <a  style="cursor: pointer;margin-left:1rem" onclick="onNextClickUAegean()">Sign in with UAegean Attribute Provider</a>
                                        </p>
                                    </div>
                                    <div class="col s12 hide-on-med-and-down ">
                                        <p>
                                            <b>Identify with eIDAS eID:</b> You will now be directed to the eIDAS Network to securely identify and trustly 
                                            provide us your main identity attributes. Review the identification attributes that will be requested  <a class="modal-trigger" href="#modal1">HERE</a>
                                        </p>
                                        <p>
                                            The eIDAS Network will provide us with those attributes from the attribute provider you will suggest (eID-EU)

                                            The eIDAS Network will request your consent before sending us any information 
                                            After successful authentication you will be redirected to our service
                                        </p>
                                        <div class="row">

                                            <div class="input-field col s12">
                                                <select id="countrySelection2" class="icons">
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

                                            <div class="row" style="margin-bottom: 0px;">
                                                <div class="input-field inline col s12" style="width:98%;margin-left:1%;">
                                                    <input id="emailLarge" type="email" class="validate">
                                                    <label for="email" data-error="wrong" data-success="right">Email</label>
                                                </div>


                                                <div class="input-field col s12" style="width:98%;margin-left:1%;">
                                                    <input id="email2Large" type="email" class="validate">
                                                    <label for="email2">Retype Email</label>
                                                </div>

                                                <div class="input-field col s12"  style="display:none">
                                                    <select id="typeOfLogin" class="icons" >
                                                        <option value="eIDAS"  >eID_EU Network</option>
                                                        <option value="peps" selected>PEPS  </option>    
                                                    </select>
                                                    <label>Select means of identification </label>
                                                </div>

                                            </div>


                                            <div class="col s12 m12 l6" style="padding-top: 1rem;">
                                                <a class="waves-effect waves-light btn swell-btn cancel-btn" onclick="onCancelClick()">Cancel</a>
                                            </div>
                                            <div class="col s12 m12 l6" style="padding-top: 1rem;">
                                                <a id="next" class="waves-effect waves-light btn swell-btn next-btn" onclick="onNextClick()">Next</a>
                                            </div>
                                        </div>

                                        <c:if test = "${linkedIn == true}">
                                            <!--<span style="color:#446eff;font-weight: bold;"></span>-->
                                            <b>Identify with LinkedIn:</b> 
                                            <img  onclick="linkedInClick()" 
                                                  src='img/linkedIn.png'
                                                  style="
                                                  cursor: pointer;
                                                  width: 14rem;
                                                  margin-left: 1rem;
                                                  vertical-align: bottom;"/>
                                        </c:if>


                                        <p> 
                                            <span style="color:#446eff;font-weight: bold;">Identify with UAegean credentials </span> students | faculty | staff:  
                                            <a  style="cursor: pointer;margin-left:1rem" onclick="onNextClickUAegean()">Sign in with UAegean Attribute Provider</a>
                                        </p>

                                    </div>
                                </div>
                                <!--                                <div class="row" id="countrySelection-row">
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

                    </div>-->


                                <!--                                <div class="row">
                                                                    <div class="col s12 m12 l6" >
                                                                        <a class="waves-effect waves-light btn swell-btn cancel-btn" onclick="onCancelClick()">Cancel</a>
                                                                    </div>
                                                                    <div class="col s12 m12 l6" >
                                                                        <a id="next" class="waves-effect waves-light btn swell-btn next-btn" onclick="onNextClick()">Next</a>
                                                                    </div>
                                                                </div>-->
                                <!--                                <div class="col s12">
                                                                    <div id ="element-container">
                                                                        <div id="element"></div>
                                                                    </div>
                                                                </div>-->
                                <!--</div>-->
                            </div>
                        </div>
                    </form>
                    <div class="col s12 m12 l2">
                        <%@ include file="/WEB-INF/jsp/sidebar.jsp" %>
                    </div>
                    <%@ include file="/WEB-INF/jsp/modalProps.jsp" %>

                </div>


            </div>
        </main>

        <div class="row">
            <%@ include file="/WEB-INF/jsp/footer.jsp" %>
        </div>






        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <!-- Compiled and minified JavaScript -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>
        <script>


                                                $(document).ready(function () {
                                                    $('select').formSelect();
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

                                                    })
                                                });


                                                function onNextClick() {
                                                    let token = "${token}";
                                                    let emailSelector1 = $("#mobile").is(":visible") ? $("#email") : $("#emailLarge");
                                                    let emailSelector2 = $("#mobile").is(":visible") ? $("#email2") : $("#email2Large");
                                                    let email = emailSelector1.val();
                                                    let email2 = emailSelector2.val();

                                                    let countrySelector = $("#mobile").is(":visible") ? $("#countrySelection") : $("#countrySelection2");
                                                    let country = countrySelector.val();
                                                    let typeOfId = $('#typeOfLogin').val();
                                                    let location = "";
                                                    let iss = "${nodePre}";
                                                    if (country === "GR") {
                                                        iss = "${node}";
                                                    }
                                                    if (typeOfId === "eIDAS") {
                                                        //                                            location = "http://84.205.248.180/ISSPlus/ValidateToken?t=${token}"
                                                        location = iss + "?t=${token}" + "/" + email
                                                                + "&sp=${sp}"
                                                                + "&cc=" + country + "&saml=${samlType}";
                                                    } else {

                                                        //                                        location = "http://84.205.248.180/ISSPlus/ValidateToken?t=${token}" + "/"+ email
                                                        location = iss + "?t=${token}" + "/" + email
                                                                + "&sp=${sp}"
                                                                + "&cc=" + country + "&saml=${samlType}";
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
                                                    window.location = "authfail?reason=disagree";
                                                }

                                                function validateEmail(email) {
                                                    var atpos = email.indexOf("@");
                                                    var dotpos = email.lastIndexOf(".");
                                                    if (atpos < 1 || dotpos < atpos + 2 || dotpos + 2 >= email.length) {
                                                        return false;
                                                    }
                                                    return true;
                                                }

                                                function onNextClickUAegean() {
                                                    let token = "${token}";
                                                    let email = $("#email").val();
                                                    let email2 = $("#email2").val();

                                                    let location = "https://eidasiss.aegean.gr:8081/ISS2/ldap.jsp?t=${token}" + "/" + email + "&sp=${sp}";
                                                    window.location = location;
                                                }

                                                function linkedInClick() {
                                                    let clientId = "${clientID}";
                                                    let redirectURI = "${redirectURI}";
                                                    let responseType = "${responseType}";
                                                    let state = "${state}";
                                                    let location = "https://www.linkedin.com/oauth/v2/authorization?"
                                                            + "response_type=" + responseType
                                                            + "&client_id=" + clientId
                                                            + "&redirect_uri=" + redirectURI
                                                            + "&state=" + state;
                                                    window.location = location;
                                                }

        </script>

    </body>
</html>
