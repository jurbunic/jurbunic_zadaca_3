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
            <label for="naziv">Naziv i adresa:</label>
            <input id="naziv" name="naziv" />
            <input id="adresa" name="adresa" />
            <input type="submit" name="btn_geolokacija" value="Geo Lokacija"/><br/>
            <label for="">Geolokacija: </label>
            <input id="geolokacija" name="geolokacija" />
            <input type="submit" name="btn_spremi" value="Spremi"/><br/>
            <input type="submit" name="btn_podaci" value="Meteo podaci"/><br/>
            <label for="temperatura">Temp: </label>
            <output id="temperatura" name="temperatura" /><br/>
            <label for="vlaga">Vlaga: </label>
            <output id="vlaga" name="vlaga" /><br/>
            <label for="tlak">Tlak: </label>
            <output id="tlak" name="tlak" /><br/>
        </form>
    </body>
</html>
