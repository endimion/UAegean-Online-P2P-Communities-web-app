<!-- Modal Trigger -->
<!--<a class="waves-effect waves-light btn modal-trigger" href="#modal1">Modal</a>-->

<div id="modal1" class="modal modal-fixed-footer">
    <div class="modal-content" style="padding-bottom: 0">

        <div class="container">
            <div class="row">
                <div class="col s12">
                    <h5>Which attributes will be requested</h5>
                </div>

            </div>
            <div class="row">
                <div class="col s12">
                    By agreeing to proceed the system will request the following identification attributes from the eID_EU network. 
                    These attributes will not be propagated to third parties and will only be used within the premises of this application.
                </div>
            </div>
            <div class="row">
                <c:set var = "size" scope = "session" value = "s12"/>
                <c:if test = "${legal.size() > 0 && natural.size() > 0}">
                    <c:set var = "size" scope = "session" value = "s6"/>
                </c:if>

                <c:if test = "${natural.size() > 0}">
                    <div class="col ${size}">
                        <table class="centered striped responsive">
                            <thead>
                                <tr>
                                    <th style="background: #5455ca;
                                        color: white;">Natural Person</th>
                                </tr>
                            </thead>

                            <tbody>
                            <c:forEach var="attr" items="${natural}">
                                <tr>
                                    <td>${attr}</td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                    </div>

                </c:if>
                <c:if test = "${legal.size() > 0}">
                    <div class="col ${size}">
                        <table class="centered striped">
                            <thead>
                                <tr>
                                    <th style="background: #5455ca;
                                        color: white;">Legal Person</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="attr" items="${legal}">
                                <tr>
                                    <td>${attr}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:if>

            </div>


        </div>


    </div>
    <div class="modal-footer">
        <div class="container">
            <div class="row"style="margin-bottom: 0;">
                <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Agree</a>
                <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat" onclick="onCancelClick()"> Disagree</a>
            </div>
        </div>
    </div>
</div>