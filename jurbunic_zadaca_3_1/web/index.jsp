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
            <input id="naziv" name="naziv" value="<%
                if(request.getAttribute("naziv")!=null){
                    out.print(request.getAttribute("naziv"));
                }%>" />
            <input id="adresa" name="adresa" value="<%
                   if(request.getAttribute("adresa")!=null){
                       out.print(request.getAttribute("adresa"));
                   }%>" />
            <input type="submit" name="btn_geolokacija" value="Geo Lokacija"/><br/>
            <label for="">Geolokacija: </label>
            <input readonly="true" id="geolokacija" name="geolokacija" value="<%
                if(request.getAttribute("lokacija")!=null){
                    out.print(request.getAttribute("lokacija"));
                }%>"/>
            <input type="submit" name="btn_spremi" value="Spremi"/><br/>
            <input type="submit" name="btn_podaci" value="Meteo podaci"/><br/>
            <label for="temperatura">Temp: </label>
            <%                
                if (request.getAttribute("temp") == null) {
                    out.println("-");
                } else {
                    out.println(request.getAttribute("temp"));
                }
            %>
            <br/>
            <label for="vlaga">Vlaga: </label>
            <%                
                if (request.getAttribute("vlaga") == null) {
                    out.println("-");
                } else {
                    out.println(request.getAttribute("vlaga"));
                }
            %>
            <br/>
            <label for="tlak">Tlak: </label>
            <%                
                if (request.getAttribute("tlak") == null) {
                    out.println("-");
                } else {
                    out.println(request.getAttribute("tlak"));
                }
            %>
            <br/>
        </form>
    </body>
</html>
