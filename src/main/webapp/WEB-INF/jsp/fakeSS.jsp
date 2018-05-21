<%-- 
    Document   : fakeSSRedirect
    Created on : Feb 23, 2017, 7:23:59 PM
    Author     : nikos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fake Supporting Service</title>
    </head>
    <body>
        
        <H1>Fake supporting service</H1>
        <form method="POST" action="save">
            Token:<br>
            <input type="text" name="token" value=${token}><br>
            eid:<br>
            <input type="text" name="eid" value="123"><br><br>
            Status:<br>
            <input type="text" name="status" value="ok"><br><br>
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
