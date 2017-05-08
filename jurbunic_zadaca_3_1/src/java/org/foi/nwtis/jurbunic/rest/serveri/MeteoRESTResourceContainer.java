/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.jurbunic.rest.serveri;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.foi.nwtis.jurbunic.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.jurbunic.rest.klijenti.GMKlijent;
import org.foi.nwtis.jurbunic.web.podaci.Lokacija;
import org.foi.nwtis.jurbunic.web.podaci.Uredjaj;

/**
 * REST Web Service
 *
 * @author grupa_1
 */
@Path("/meteoREST")
public class MeteoRESTResourceContainer {

    @Context
    private UriInfo context;

    @Context
    private ServletContext sc;

    /**
     * Creates a new instance of MeteoRESTResourceContainer
     */
    public MeteoRESTResourceContainer() {
    }

    /**
     * Metoda dohvaća sve uređaje iz baze podataka uređaja te ih vraća u json obliku
     * Retrieves representation of an instance of
     * org.foi.nwtis.jurbunic.rest.serveri.MeteoRESTResourceContainer
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() throws ClassNotFoundException {
        ArrayList<Uredjaj> uredjaji = new ArrayList<>();
        String sql = "SELECT * FROM UREDAJI";
        BP_Konfiguracija bp = (BP_Konfiguracija) sc.getAttribute("BP_Konfig");
        Class.forName(bp.getDriverDatabase());
        try (Connection con = DriverManager.getConnection(bp.getServerDatabase() + bp.getUserDatabase(),
                bp.getUserUsername(), bp.getUserPassword())) {
            Statement naredba = con.createStatement();
            ResultSet odgovor = naredba.executeQuery(sql);
            while (odgovor.next()) {
                uredjaji.add(new Uredjaj(odgovor.getInt(1), odgovor.getString(2), new Lokacija(String.valueOf(odgovor.getLong(3)), String.valueOf(odgovor.getLong(4)))));
            }
            JsonArrayBuilder jab = Json.createArrayBuilder();
            for (int j = 0; j < uredjaji.size(); j++) {
                JsonObjectBuilder jo = Json.createObjectBuilder();
                jo.add("id", uredjaji.get(j).getId());
                jo.add("naziv", uredjaji.get(j).getNaziv());
                jo.add("lat", uredjaji.get(j).getGeoloc().getLatitude());
                jo.add("log", uredjaji.get(j).getGeoloc().getLongitude());
                jab.add(jo);
            }
            return jab.build().toString();
        } catch (SQLException e) {
            return e.toString();
        }
    }

    /**
     * Metoda dobivene vrijednosti(u json formatu) parsira i zapisuje u 
     * tablicu UREDAJI
     * POST method for creating an instance of MeteoRESTResource
     *
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String content) throws ClassNotFoundException {
        JsonReader reader = Json.createReader(new StringReader(content));
        JsonObject jo = reader.readObject();
        int id;
        String naziv = jo.getString("naziv");
        String adresa = jo.getString("adresa");
        GMKlijent gm = new GMKlijent();
        Lokacija l = gm.getGeoLocation(adresa);
       
        String lat = String.valueOf(l.getLatitude());
        String log = String.valueOf(l.getLongitude());
        String sql = "SELECT MAX(ID) FROM UREDAJI";
        BP_Konfiguracija bp = (BP_Konfiguracija) sc.getAttribute("BP_Konfig");
        Class.forName(bp.getDriverDatabase());
        try (Connection con = DriverManager.getConnection(bp.getServerDatabase() + bp.getUserDatabase(),
                bp.getUserUsername(), bp.getUserPassword())) {
            Statement naredba = con.createStatement();
            ResultSet odgovor = naredba.executeQuery(sql);
            odgovor.next();
            id = odgovor.getInt(1)+1;
            String insert = "INSERT INTO UREDAJI VALUES("+id+",'"+naziv+"',"
                    +Float.parseFloat(lat)+","+Float.parseFloat(log)+",1,'"
                    +new Timestamp(System.currentTimeMillis())+"','"+new Timestamp(System.currentTimeMillis())+"')";
            naredba = con.createStatement();
            naredba.executeUpdate(insert);
        } catch (SQLException ex) {
            Logger.getLogger(MeteoRESTResourceContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //todo upisi u uredaj u bazi podataka!
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Sub-resource locator method for {id}
     */
    @Path("{id}")
    public MeteoRESTResource getMeteoRESTResource(@PathParam("id") String id) {
        return MeteoRESTResource.getInstance(id, sc);
    }
}
