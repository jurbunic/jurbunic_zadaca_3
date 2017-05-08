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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.servlet.ServletContext;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import org.foi.nwtis.jurbunic.web.podaci.MeteoPodaci;
import org.foi.nwtis.jurbunic.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.jurbunic.rest.klijenti.GMKlijent;
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

    /**
     * Servis koji dohvaća sve uređaje iz baze i vraća ih u obliku List<Uredjaj>
     * 
     * @return lista uređaja
     * @throws ClassNotFoundException 
     */
    @WebMethod(operationName = "dajSveUredjaje")
    public List<Uredjaj> dajSveUredjaje() throws ClassNotFoundException {
        //TODO dohvati uredaje iz baze;
        //http://localhost:8084/jurbunic_zadaca_3_1/GeoMeteoWS?WSDL
        List<Uredjaj> uredjaji = new ArrayList<>();
        ServletContext sc = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        BP_Konfiguracija bp = (BP_Konfiguracija) sc.getAttribute("BP_Konfig");
        Class.forName(bp.getDriverDatabase());
        try (Connection con = DriverManager.getConnection(bp.getServerDatabase() + bp.getUserDatabase(),
                bp.getUserUsername(), bp.getUserPassword())) {
            String sql = "SELECT * FROM UREDAJI";
            Statement naredba = con.createStatement();
            ResultSet odgovor = naredba.executeQuery(sql);
            while (odgovor.next()) {
                uredjaji.add(new Uredjaj(odgovor.getInt(1), odgovor.getString(2), new Lokacija(String.valueOf(odgovor.getLong(3)), String.valueOf(odgovor.getLong(4)))));
            }
        } catch (SQLException e) {
            return uredjaji;
        }

        return uredjaji;
    }

    /**
     * Metoda služi za dodavanje uređaja u bazu podataka. Id se zapisuje na način da se uzme
     * posljednji najveći Id u bazi te se uveca za jedan. Vraca true ako je sve ispravno inace false
     * @param uredjaj
     * @return true / false
     * @throws ClassNotFoundException 
     */
    @WebMethod(operationName = "dodajUredaj")
    public Boolean dodajUredaj(@WebParam(name = "uredaj") Uredjaj uredjaj) throws ClassNotFoundException {
        ServletContext sc = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        BP_Konfiguracija bp = (BP_Konfiguracija) sc.getAttribute("BP_Konfig");
        Class.forName(bp.getDriverDatabase());
        try (Connection con = DriverManager.getConnection(bp.getServerDatabase() + bp.getUserDatabase(),
                bp.getUserUsername(), bp.getUserPassword())) {
            String sql = "SELECT MAX(ID) FROM UREDAJI";
            Statement naredba = con.createStatement();
            ResultSet odgovor = naredba.executeQuery(sql);
            odgovor.next();
            int id = odgovor.getInt(1)+1;
            String[] inf = uredjaj.getNaziv().split("-");
            GMKlijent gmk = new GMKlijent();
            Lokacija l = gmk.getGeoLocation(inf[1]);
            sql = "INSERT INTO UREDAJI VALUES "
                    + "(" + id + ",'" + inf[0] + "',"
                    + l.getLatitude() + ","
                    + l.getLongitude() + ","
                    + "1,'" + new Timestamp(System.currentTimeMillis()) + "',"
                    + "'" + new Timestamp(System.currentTimeMillis()) + "')";
            naredba = con.createStatement();
            naredba.executeUpdate(sql);
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Dohavaća sve meteo podatke za zadani Id uređaja u zadanom vremenskom rasponu
     * Web service operation
     */
    @WebMethod(operationName = "dajSveMeteoPodatkeZaUredjaj")
    public List<MeteoPodaci> dajSveMeteoPodatkeZaUredjaj(@WebParam(name = "id") int id, @WebParam(name = "od") long od, @WebParam(name = "do") long parameter1) throws ClassNotFoundException, ParseException {
        //INSERT INTO METEO (ID,ADRESASTANICE,LONGITUDE,LATITUDE,VRIJEME,VRIJEMEOPIS,TEMP,TEMPMIN,TEMPMAX,VLAGA,TLAK,VJETAR,VJETARSMJER,PREUZETO) VALUES 
        //(2, 'Izmisljena adresa', 33.2, 17.5, 'Dobro', 'Veoma dobro', 22, 8, 25, 33, 1023, 2, 4, '2017-05-04 17:38:27.732' )
        List<MeteoPodaci> mp = new ArrayList<MeteoPodaci>();
        ServletContext sc = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        BP_Konfiguracija bp = (BP_Konfiguracija) sc.getAttribute("BP_Konfig");
        String a = bp.getDriverDatabase();
        Class.forName(bp.getDriverDatabase());
        try (Connection con = DriverManager.getConnection(bp.getServerDatabase() + bp.getUserDatabase(),
                bp.getUserUsername(), bp.getUserPassword())) {
            String sql = "SELECT * FROM METEO WHERE ID=" + id + " AND PREUZETO BETWEEN '" + new Timestamp(od) + "' AND '" + new Timestamp(parameter1) + "'";
            Statement naredba = con.createStatement();
            ResultSet odgovor = naredba.executeQuery(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            while (odgovor.next()) {
                String ts = odgovor.getTimestamp(15).toString();
                
                mp.add(new MeteoPodaci(new Date(), new Date(), odgovor.getFloat(8), odgovor.getFloat(9), odgovor.getFloat(10),
                        "C", odgovor.getFloat(11), "vlaznost", odgovor.getFloat(12), "hPa", odgovor.getFloat(13), "sjeverac",
                        odgovor.getFloat(14), "", "", 1, "", "", 2.2f, "", "", Integer.parseInt(odgovor.getString(6)), odgovor.getString(7), "", sdf.parse(ts)));
            }
            return mp;
        } catch (SQLException e) {
            return mp;
        }
    }

    /**
     * Dohvaća posljenje meteo podatke za zadani Id uređaja
     * Web service operation
     */
    @WebMethod(operationName = "dajZadnjeMeteoPodatkeZaUredjaj")
    public MeteoPodaci dajZadnjeMeteoPodatkeZaUredjaj(@WebParam(name = "id") int id) throws ClassNotFoundException {
        MeteoPodaci mp = null;
        ServletContext sc = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        BP_Konfiguracija bp = (BP_Konfiguracija) sc.getAttribute("BP_Konfig");
        Class.forName(bp.getDriverDatabase());
        try (Connection con = DriverManager.getConnection(bp.getServerDatabase() + bp.getUserDatabase(),
                bp.getUserUsername(), bp.getUserPassword())) {
            String sql = "SELECT * FROM METEO WHERE ID =" + id + " AND PREUZETO = (SELECT MAX(PREUZETO) FROM METEO WHERE ID = " + id + ")";
            Statement naredba = con.createStatement();
            ResultSet odgovor = naredba.executeQuery(sql);
            while (odgovor.next()) {
                mp = new MeteoPodaci(new Date(), new Date(), odgovor.getFloat(8), odgovor.getFloat(9), odgovor.getFloat(10),
                        "C", odgovor.getFloat(11), "vlaznost", odgovor.getFloat(12), "hPa", odgovor.getFloat(13), "sjeverac",
                        odgovor.getFloat(14), "", "", 1, "", "", 2.2f, "", "", 3, "", "", odgovor.getDate(15));
            }
            return mp;
        } catch (SQLException e) {
            return mp;
        }
    }

    /**
     * Vraća najmanju i najveću temperaturu izmjerenu na uređaju sa zadanim Id-om.
     * Web service operation
     */
    @WebMethod(operationName = "dajMinMaxTempZaUredjaj")
    public float[] dajMinMaxTempZaUredjaj(@WebParam(name = "id") int id, @WebParam(name = "od") long od, @WebParam(name = "parameter1") long parameter1) throws ClassNotFoundException {
        float[] minMax = new float[2];
        //1483806002312
        ServletContext sc = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        BP_Konfiguracija bp = (BP_Konfiguracija) sc.getAttribute("BP_Konfig");
        Class.forName(bp.getDriverDatabase());
        try (Connection con = DriverManager.getConnection(bp.getServerDatabase() + bp.getUserDatabase(),
                bp.getUserUsername(), bp.getUserPassword())) {
            String sql = "SELECT MIN(TEMP),MAX(TEMP) FROM METEO WHERE ID=" + id + " AND PREUZETO BETWEEN '" + new Timestamp(od) + "' AND '" + new Timestamp(parameter1) + "'";
            System.out.println(new Timestamp(od));
            System.out.println(new Timestamp(parameter1));
            Statement naredba = con.createStatement();
            ResultSet odgovor = naredba.executeQuery(sql);
            odgovor.next();
            minMax[0] = odgovor.getFloat(1);
            minMax[1] = odgovor.getFloat(2);
            return minMax;
        } catch (SQLException e) {
            System.out.println(e);
            return minMax;
        }
    }

    /**
     * Vraća najmanju i najveću vlagu izmjerenu na uređaju sa zadanim Id-om.
     * Web service operation
     */
    @WebMethod(operationName = "dajMinMaxVlagaZaUredjaj")
    public float[] dajMinMaxVlagaZaUredjaj(@WebParam(name = "id") int id, @WebParam(name = "od") long od, @WebParam(name = "parameter1") long parameter1) throws ClassNotFoundException {
        float[] minMax = new float[2];
        ServletContext sc = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        BP_Konfiguracija bp = (BP_Konfiguracija) sc.getAttribute("BP_Konfig");
        Class.forName(bp.getDriverDatabase());
        try (Connection con = DriverManager.getConnection(bp.getServerDatabase() + bp.getUserDatabase(),
                bp.getUserUsername(), bp.getUserPassword())) {
            String sql = "SELECT MIN(VLAGA),MAX(VLAGA) FROM METEO WHERE ID=" + id + " AND PREUZETO BETWEEN '" + new Timestamp(od) + "' AND '" + new Timestamp(parameter1) + "'";
            Statement naredba = con.createStatement();
            ResultSet odgovor = naredba.executeQuery(sql);
            odgovor.next();
            minMax[0] = odgovor.getFloat(1);
            minMax[1] = odgovor.getFloat(2);
            return minMax;
        } catch (SQLException e) {
            return minMax;
        }
    }

    /**
     * Vraća najmanji i najveći tlak izmjeren na uređaju sa zadanim Id-om.
     * Web service operation
     */
    @WebMethod(operationName = "dajMinMaxTlakZaUredjaj")
    public float[] dajMinMaxTlakZaUredjaj(@WebParam(name = "id") int id, @WebParam(name = "od") long od, @WebParam(name = "do") long parameter1) throws ClassNotFoundException {
        float[] minMax = new float[2];
        ServletContext sc = (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        BP_Konfiguracija bp = (BP_Konfiguracija) sc.getAttribute("BP_Konfig");
        Class.forName(bp.getDriverDatabase());
        try (Connection con = DriverManager.getConnection(bp.getServerDatabase() + bp.getUserDatabase(),
                bp.getUserUsername(), bp.getUserPassword())) {
            String sql = "SELECT MIN(TLAK),MAX(TLAK) FROM METEO WHERE ID=" + id + " AND PREUZETO BETWEEN '" + new Timestamp(od) + "' AND '" + new Timestamp(parameter1) + "'";
            Statement naredba = con.createStatement();
            ResultSet odgovor = naredba.executeQuery(sql);
            odgovor.next();
            minMax[0] = odgovor.getFloat(1);
            minMax[1] = odgovor.getFloat(2);
            return minMax;
        } catch (SQLException e) {
            return minMax;
        }
    }
}
