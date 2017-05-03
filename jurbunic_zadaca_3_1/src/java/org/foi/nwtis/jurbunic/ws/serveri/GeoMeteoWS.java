/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.jurbunic.ws.serveri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.foi.nwtis.dkermek.web.podaci.MeteoPodaci;
import org.foi.nwtis.jurbunic.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.jurbunic.web.podaci.Lokacija;
import org.foi.nwtis.jurbunic.web.podaci.Uredjaj;

/**
 *
 * @author grupa_1
 */
@WebService(serviceName = "GeoMeteoWS")
public class GeoMeteoWS {
    @Resource
    private WebServiceContext context;
    
    @WebMethod(operationName = "dajSveUredjaje")
    public List<Uredjaj> dajSveUredjaje() throws ClassNotFoundException{
        //TODO dohvati uredaje iz baze;
        //http://localhost:8084/jurbunic_zadaca_3_1/GeoMeteoWS?WSDL
        List<Uredjaj> uredjaji = new ArrayList<>();
        
        ServletContext sc = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        BP_Konfiguracija bp = (BP_Konfiguracija) sc.getAttribute("BP_Konfig");
        Class.forName(bp.getDriverDatabase());
        try(Connection con = DriverManager.getConnection(bp.getServerDatabase()+bp.getUserDatabase(),
                bp.getUserUsername(), bp.getUserPassword())){
            String sql = "SELECT * FROM UREDAJI";
            Statement naredba = con.createStatement();
            ResultSet odgovor = naredba.executeQuery(sql);
            while(odgovor.next()){
                //TODO popraviti dohvat;
                uredjaji.add(new Uredjaj(odgovor.getInt(1), odgovor.getString(2), new Lokacija(String.valueOf(odgovor.getLong(3)), String.valueOf(odgovor.getLong(4)))));
            }
        }catch(SQLException e){
            return uredjaji;
        }
        
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
