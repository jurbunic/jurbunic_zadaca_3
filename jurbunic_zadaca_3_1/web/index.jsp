<%-- 
    Document   : index
    Created on : Apr 25, 2017, 4:19:14 PM
    Author     : grupa_1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Unos</title>
    </head>
    <body>
        <h1>Unos IoT uređaja</h1>
        <form action="./DodajUredjaj" method="POST">
            <label for="naziv">naziv i adresa:</label>
            <input id="naziv" name="naziv" />
            <input id="adresa" name="adresa" />
            <input type="submit" name="geolokacija" value="Geo Lokacija"/><br/>
            <input type="submit" name="spremi" value="Spremi"/><br/>
            <input type="submit" name="podaci" value="Meteo podaci"/><br/>
        </form>
    </body>
</html>
