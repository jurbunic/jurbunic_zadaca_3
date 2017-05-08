/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.jurbunic.web;

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
import javax.servlet.ServletContext;
import org.foi.nwtis.jurbunic.konfiguracije.Konfiguracija;
import org.foi.nwtis.jurbunic.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.jurbunic.rest.klijenti.OWMKlijent;
import org.foi.nwtis.jurbunic.web.podaci.Lokacija;
import org.foi.nwtis.jurbunic.web.podaci.MeteoPodaci;
import org.foi.nwtis.jurbunic.web.podaci.Uredjaj;

/**
 *
 * @author Jurica Bunić
 */
public class PreuzmiMeteoPodatke extends Thread{
    BP_Konfiguracija bpkonf;
    Konfiguracija konf;
    ServletContext sc;
    boolean prekid=false;
    
    @Override
    public void interrupt() {
        prekid=true;
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        OWMKlijent owm = new OWMKlijent("8a137f80e58000b8bd42ee309da78b11");
        List<Uredjaj> uredaji = new ArrayList<Uredjaj>();
        konf = (Konfiguracija) sc.getAttribute("konf");
        bpkonf = (BP_Konfiguracija) sc.getAttribute("BP_Konfig");
        while(!prekid){
            try {
                long pocetak = System.currentTimeMillis();
                uredaji = dohvatiUredaje();
                for(Uredjaj uredjaj : uredaji){
                    Lokacija l = uredjaj.getGeoloc();
                    MeteoPodaci mp = owm.getRealTimeWeather(l.getLatitude(), l.getLongitude());
                    String sql = "INSERT INTO METEO (ID,ADRESASTANICE,LATITUDE,LONGITUDE,VRIJEME,VRIJEMEOPIS,TEMP,TEMPMIN,TEMPMAX,VLAGA,TLAK,VJETAR,VJETARSMJER,PREUZETO) " +
                                 "VALUES ("+uredjaj.getId()+", 'jurbunic', "+l.getLatitude()+", "+l.getLatitude()+", '"+mp.getWeatherNumber()+"', "
                               + "'"+mp.getWeatherValue()+"', "+mp.getTemperatureValue()+", "+mp.getTemperatureMin()+", "+mp.getTemperatureMax()+", "
                               + mp.getHumidityValue()+", "+mp.getPressureValue()+", "+mp.getWindSpeedValue()+", "+mp.getWindDirectionCode()+1+", "
                               + "'"+new Timestamp(System.currentTimeMillis())+"' ) ";
                    upisMeteoBaza(sql);
                    sql = "UPDATE UREDAJI SET VRIJEME_PROMJENE ='"+new Timestamp(System.currentTimeMillis())+"' WHERE ID="+uredjaj.getId();
                    upisMeteoBaza(sql);
                }
                long kraj = System.currentTimeMillis();
                long ukupno = kraj-pocetak;
                long spavanje = Long.valueOf(konf.dajPostavku("intervalDretveZaMeteoPodatke"));
                sleep(spavanje*1000-ukupno);
            } catch (InterruptedException ex) {
                Logger.getLogger(PreuzmiMeteoPodatke.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Metoda dohvaća sve uređaje (SELECT) iz baze podataka te ih sprema u listu
     * @return lista svih uređaja
     */
    private List<Uredjaj> dohvatiUredaje(){
        List<Uredjaj> uredaji = new ArrayList<>();
        try {           
            Class.forName(bpkonf.getDriverDatabase());
            try (Connection con = DriverManager.getConnection(bpkonf.getServerDatabase() + bpkonf.getUserDatabase(),
                    bpkonf.getUserUsername(), bpkonf.getUserPassword())) {
                String sql = "SELECT * FROM UREDAJI";
                Statement naredba = con.createStatement();
                ResultSet odgovor = naredba.executeQuery(sql);
                while (odgovor.next()) {
                    uredaji.add(new Uredjaj(odgovor.getInt(1), odgovor.getString(2), new Lokacija(String.valueOf(odgovor.getLong(3)), String.valueOf(odgovor.getLong(4)))));
                }
            } catch (SQLException e) {
                return uredaji;
            }            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PreuzmiMeteoPodatke.class.getName()).log(Level.SEVERE, null, ex);
        }
        return uredaji;
    }
    /**
     * Metoda se koristi za upis u bazu podataka (INSERT / UPDATE)
     * @param sql sql upit
     */
    private void upisMeteoBaza(String sql){
        try {           
            Class.forName(bpkonf.getDriverDatabase());
            try (Connection con = DriverManager.getConnection(bpkonf.getServerDatabase() + bpkonf.getUserDatabase(),
                    bpkonf.getUserUsername(), bpkonf.getUserPassword())) {
                Statement naredba = con.createStatement();
                naredba.executeUpdate(sql);
            } catch (SQLException e) {
                System.out.println(e);
            }            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PreuzmiMeteoPodatke.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public void setSc(ServletContext sc) {
        this.sc = sc;
    }
    
    
}
