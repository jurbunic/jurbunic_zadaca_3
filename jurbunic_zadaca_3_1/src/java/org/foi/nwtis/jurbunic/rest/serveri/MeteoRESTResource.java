/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.jurbunic.rest.serveri;

import java.math.BigDecimal;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.MediaType;
import org.foi.nwtis.jurbunic.web.podaci.Lokacija;
import org.foi.nwtis.jurbunic.web.podaci.Uredjaj;

/**
 * REST Web Service
 *
 * @author grupa_1
 */
public class MeteoRESTResource {

    private String id;

    /**
     * Creates a new instance of MeteoRESTResource
     */
    private MeteoRESTResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the MeteoRESTResource
     */
    public static MeteoRESTResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of MeteoRESTResource class.
        return new MeteoRESTResource(id);
    }

    /**
     * Retrieves representation of an instance of
     * org.foi.nwtis.jurbunic.rest.serveri.MeteoRESTResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        int i = 0;
        ArrayList<Uredjaj> uredjaji = new ArrayList<>();
        uredjaji.add(new Uredjaj(i++, "Samsung", new Lokacija("2.3", "3.4")));
        uredjaji.add(new Uredjaj(i++, "IoT", new Lokacija("7.3", "11.2")));
        JsonObjectBuilder jo = Json.createObjectBuilder();
        for (Uredjaj u : uredjaji) {
            if (u.getId() == Long.parseLong(this.id)) {
                jo.add("id", u.getId());
                jo.add("naziv", u.getNaziv());
                jo.add("lat", u.getGeoloc().getLatitude());
                jo.add("log", u.getGeoloc().getLongitude());
                break;
            }
        }
        return jo.build().toString();
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
