/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.jurbunic.rest.serveri;

import java.io.StringReader;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
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

    /**
     * Creates a new instance of MeteoRESTResourceContainer
     */
    public MeteoRESTResourceContainer() {
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.jurbunic.rest.serveri.MeteoRESTResourceContainer
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //todo dovrsiti da se preuzmu uredaji iz baze podataka
        int i = 0;
        ArrayList<Uredjaj> uredjaji = new ArrayList<>();
        uredjaji.add(new Uredjaj(i++, "Samsung", new Lokacija("2.3", "3.4")));
        uredjaji.add(new Uredjaj(i++, "IoT", new Lokacija("7.3", "11.2")));
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (int j = 0; j < i; j++) {
            JsonObjectBuilder jo = Json.createObjectBuilder();
            jo.add("id", uredjaji.get(j).getId());
            jo.add("naziv", uredjaji.get(j).getNaziv());
            jo.add("lat", uredjaji.get(j).getGeoloc().getLatitude());
            jo.add("log", uredjaji.get(j).getGeoloc().getLongitude());
            jab.add(jo);
        }
        return jab.build().toString();
    }

    /**
     * POST method for creating an instance of MeteoRESTResource
     *
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String content) {        
        JsonReader reader = Json.createReader(new StringReader(content));
        JsonObject jo = reader.readObject();
        int id = jo.getInt("id");
        String naziv = jo.getString("naziv");
        String lat = jo.getString("lat");
        String log = jo.getString("log");
        
        //todo upisi u uredaj u bazi podataka!
        return Response.created(context.getAbsolutePath()).build();
    }

    /**
     * Sub-resource locator method for {id}
     */
    @Path("{id}")
    public MeteoRESTResource getMeteoRESTResource(@PathParam("id") String id) {
        return MeteoRESTResource.getInstance(id);
    }
}
