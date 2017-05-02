/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.jurbunic.ws.serveri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.foi.nwtis.dkermek.web.podaci.MeteoPodaci;
import org.foi.nwtis.jurbunic.web.podaci.Lokacija;
import org.foi.nwtis.jurbunic.web.podaci.Uredjaj;

/**
 *
 * @author grupa_1
 */
@WebService(serviceName = "GeoMeteoWS")
public class GeoMeteoWS {
    @WebMethod(operationName = "dajSveUredjaje")
    public List<Uredjaj> dajSveUredjaje(){
        //TODO dohvati uredaje iz baze;
        //http://localhost:8084/jurbunic_zadaca_3_1/GeoMeteoWS?WSDL
        int i=0;
        ArrayList<Uredjaj> uredjaji = new ArrayList<>();
        uredjaji.add(new Uredjaj(i++, "Samsung", new Lokacija("2.3", "3.4")));
        uredjaji.add(new Uredjaj(i++, "IoT", new Lokacija("7.3", "11.2")));
        return uredjaji;
    }
    @WebMethod(operationName = "dodajUredaj")
    public Boolean dodajUredaj(@WebParam(name="uredaj") Uredjaj uredjaj){
        // todo dovrsiti upis u bazu podataka
        return false;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "dajSveMeteoPodatkeZaUredjaj")
    public List<MeteoPodaci> dajSveMeteoPodatkeZaUredjaj(@WebParam(name = "id") int id, @WebParam(name = "od") long od, @WebParam(name = "do") long parameter1) {
        // todo dovršiti preuzimanje meteo podatak iz baze podataka
        List<MeteoPodaci> mp = new ArrayList<>();
        mp.add(new MeteoPodaci(new Date(), new Date(), 19.7f, 10.1f, 22.1f, "C", 55.0f, "%",
                1221.2f, "hPa",
                0.0f, "kmph",
                0.0f, "smjer",
                "", 5,
                "kumulus nimbus", "", 
                3f, "kumulus nimbus",
                "", 3, "mm/2", "", 
                new Date()));
        return mp;
    }
}
