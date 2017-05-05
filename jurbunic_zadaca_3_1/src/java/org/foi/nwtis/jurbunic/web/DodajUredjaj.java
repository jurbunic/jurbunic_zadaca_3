/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.jurbunic.web;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.foi.nwtis.jurbunic.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.jurbunic.web.podaci.MeteoPodaci;
import org.foi.nwtis.jurbunic.rest.klijenti.GMKlijent;
import org.foi.nwtis.jurbunic.rest.klijenti.OWMKlijent;
import org.foi.nwtis.jurbunic.web.podaci.Lokacija;
import org.foi.nwtis.jurbunic.ws.serveri.GeoMeteoWS;

/**
 *
 * @author grupa_1
 */
@WebServlet(name = "DodajUredjaj", urlPatterns = {"/DodajUredjaj"})
public class DodajUredjaj extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    static final String API_KEY = "8a137f80e58000b8bd42ee309da78b11";

    String temp;
    String vlaga;
    String tlak;

    Lokacija lokacija;
    BP_Konfiguracija bp;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String naziv;
        String adresa;
        String gumbGeolokacija;
        String gumbSpremi;
        String gumbPodaci;
        naziv = request.getParameter("naziv");
        adresa = request.getParameter("adresa");

        gumbGeolokacija = request.getParameter("btn_geolokacija");
        gumbSpremi = request.getParameter("btn_spremi");
        gumbPodaci = request.getParameter("btn_podaci");

        if (gumbGeolokacija != null) {
            geoLokacija(naziv, adresa);
        } else if (gumbSpremi != null) {
            spremi(request, naziv);
        } else if (gumbPodaci != null) {
            meteoPodaci();
            request.setAttribute("temp", temp);
            request.setAttribute("vlaga", vlaga);
            request.setAttribute("tlak", tlak);
        }
        request.setAttribute("naziv", naziv);
        request.setAttribute("adresa", adresa);
        request.setAttribute("lokacija", lokacija.getLatitude() + ", " + lokacija.getLongitude());
        request.setAttribute("temp", temp);
        request.setAttribute("vlaga", vlaga);
        request.setAttribute("tlak", tlak);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void geoLokacija(String naziv, String adresa) {
        GMKlijent gmk = new GMKlijent();
        lokacija = gmk.getGeoLocation(adresa);
        // todo prikazi podatke u index.jsp
        // todo zapamti geo lokaciju     
    }

    private void spremi(HttpServletRequest request, String naziv) {
        try {
            bp = (BP_Konfiguracija) request.getServletContext().getAttribute("BP_Konfig");
            Class.forName(bp.getDriverDatabase());
            try (Connection con = DriverManager.getConnection(bp.getServerDatabase() + bp.getUserDatabase(),
                    bp.getUserUsername(), bp.getUserPassword())) {
                String sql = "SELECT MAX(ID) FROM UREDAJI";
                Statement naredba = con.createStatement();
                ResultSet odgovor = naredba.executeQuery(sql);
                int id = 0;
                odgovor.next();
                id = odgovor.getInt(1) + 1;
                sql = "INSERT INTO UREDAJI VALUES "
                        + "(" + id + ",'" + naziv + "',"
                        + lokacija.getLatitude() + ","
                        + lokacija.getLongitude() + ","
                        + "1,'" + new Timestamp(System.currentTimeMillis()) + "',"
                        + "'" + new Timestamp(System.currentTimeMillis()) + "')";
                naredba = con.createStatement();
                naredba.executeUpdate(sql);

            } catch (SQLException ex) {
                Logger.getLogger(GeoMeteoWS.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DodajUredjaj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void meteoPodaci() {
        // todo preuzmi APIKEY iz konfiguracijskih podataka;
        OWMKlijent owmKlijent = new OWMKlijent(API_KEY);
        MeteoPodaci mp = owmKlijent.getRealTimeWeather(lokacija.getLatitude(), lokacija.getLongitude());
        temp = mp.getTemperatureValue().toString();
        vlaga = mp.getHumidityValue().toString();
        tlak = mp.getPressureValue().toString();
    }

}
