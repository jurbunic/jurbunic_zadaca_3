/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.jurbunic.ws.klijenti;

/**
 *
 * @author grupa_1
 */
public class MeteoWSKlijent {

    public static java.util.List<org.foi.nwtis.jurbunic.ws.serveri.Uredjaj> dajSveUredjaje() {
        org.foi.nwtis.jurbunic.ws.serveri.GeoMeteoWS_Service service = new org.foi.nwtis.jurbunic.ws.serveri.GeoMeteoWS_Service();
        org.foi.nwtis.jurbunic.ws.serveri.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.dajSveUredjaje();
    }
    
    
}
