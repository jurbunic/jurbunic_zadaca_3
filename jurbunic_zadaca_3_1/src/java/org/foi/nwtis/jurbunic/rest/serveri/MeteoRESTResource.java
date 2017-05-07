/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.jurbunic.rest.serveri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.jurbunic.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.jurbunic.web.podaci.MeteoPodaci;
import javax.servlet.ServletContext;

/**
 * REST Web Service
 *
 * @author grupa_1
 */
public class MeteoRESTResource {

    private String id;
    private ServletContext sc;
    
    /**
     * Creates a new instance of MeteoRESTResource
     */
    private MeteoRESTResource(String id) {
        this.id = id;
    }

    private MeteoRESTResource(String id, ServletContext sc){
        this.id = id;
        this.sc = sc;
    }
    /**
     * Get instance of the MeteoRESTResource
     */
    public static MeteoRESTResource getInstance(String id) {      
        // The user may use some kind of persistence mechanism
        // to store and restore instances of MeteoRESTResource class.
        return new MeteoRESTResource(id);
    }
    
    public static MeteoRESTResource getInstance(String id, ServletContext sc){
        return new MeteoRESTResource(id, sc);
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.jurbunic.rest.serveri.MeteoRESTResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() throws ClassNotFoundException {
        //TODO return proper representation object
        JsonArrayBuilder jab = Json.createArrayBuilder();
        JsonObjectBuilder jo = Json.createObjectBuilder();      
        String sql = "SELECT * FROM METEO WHERE ID=" + Integer.valueOf(id);
        BP_Konfiguracija bp = (BP_Konfiguracija) sc.getAttribute("BP_Konfig");
        List<MeteoPodaci> mp = new ArrayList<>();
        Class.forName(bp.getDriverDatabase());
        try (Connection con = DriverManager.getConnection(bp.getServerDatabase() + bp.getUserDatabase(),
                bp.getUserUsername(), bp.getUserPassword())) {
            Statement naredba = con.createStatement();
            ResultSet odgovor = naredba.executeQuery(sql);
            while (odgovor.next()) {
                mp.add(new MeteoPodaci(new Date(), new Date(), odgovor.getFloat(8), odgovor.getFloat(9), odgovor.getFloat(10),
                        "celsius", odgovor.getFloat(11), "vlaznost", odgovor.getFloat(12), "hPa", odgovor.getFloat(13), "sjeverac",
                        odgovor.getFloat(14), "", "", 1, "-", "-", 2.2f, "", "", odgovor.getInt(6),odgovor.getString(7) , "", odgovor.getDate(15)));

            }
            for (MeteoPodaci m : mp) {
                jo.add("sunRise", m.getSunRise().toString());
                jo.add("sunSet", m.getSunSet().toString());
                jo.add("temperatureValue", m.getTemperatureValue());
                jo.add("temperatureMin", m.getTemperatureMin());
                jo.add("temperatureMax", m.getTemperatureMax());
                jo.add("temperatureUnit", m.getTemperatureUnit());
                jo.add("humidityValue", m.getHumidityValue());
                jo.add("humidityUnit", m.getHumidityUnit());
                jo.add("pressureValue", m.getPressureValue());
                jo.add("pressureUnit", m.getPrecipitationUnit());
                jo.add("windSpeedValue", m.getWindSpeedValue());
                jo.add("windSpeedName", m.getWindSpeedName());
                jo.add("windDirectionValue", m.getWindDirectionValue());
                jo.add("windDirectionCode", m.getWindDirectionCode());
                jo.add("windDirectionName", m.getWindDirectionName());
                jo.add("cloudsValue", m.getCloudsValue());
                jo.add("cloudsName", m.getCloudsName());
                jo.add("visibility", m.getVisibility());
                jo.add("precipitationValue", m.getPrecipitationValue());
                jo.add("precipitationMode", m.getPrecipitationMode());
                jo.add("precipitationUnit", m.getPrecipitationUnit());
                jo.add("weatherNumber", m.getWeatherNumber());
                jo.add("weatherValue", m.getWeatherValue());
                jo.add("weatherIcon", m.getWeatherIcon());
                jo.add("lastUpdate", m.getLastUpdate().toString());
                jab.add(jo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(MeteoRESTResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jab.build().toString();
    }

    /**
     * PUT method for updating or creating an instance of MeteoRESTResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    /**
     * DELETE method for resource MeteoRESTResource
     */
    @DELETE
    public void delete() {
    }
}
