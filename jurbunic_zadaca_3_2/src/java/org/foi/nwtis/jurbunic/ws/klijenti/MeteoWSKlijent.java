/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.jurbunic.ws.klijenti;

import org.foi.nwtis.jurbunic.ws.serveri.ClassNotFoundException_Exception;

/**
 *
 * @author grupa_1
 */
public class MeteoWSKlijent {

    public static java.util.List<org.foi.nwtis.jurbunic.ws.serveri.Uredjaj> dajSveUredjaje() throws ClassNotFoundException_Exception {
        org.foi.nwtis.jurbunic.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.jurbunic.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.jurbunic.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveUredjaje();
    }

    public static Boolean dodajUredaj(org.foi.nwtis.jurbunic.ws.serveri.Uredjaj uredaj) throws ClassNotFoundException_Exception {
        org.foi.nwtis.jurbunic.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.jurbunic.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.jurbunic.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dodajUredaj(uredaj);
    }

    public static java.util.List<org.foi.nwtis.jurbunic.ws.serveri.MeteoPodaci> dajSveMeteoPodatkeZaUredjaj(int id, long od, long _do) throws ClassNotFoundException_Exception {
        org.foi.nwtis.jurbunic.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.jurbunic.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.jurbunic.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveMeteoPodatkeZaUredjaj(id, od, _do);
    }
    
    

    
}
