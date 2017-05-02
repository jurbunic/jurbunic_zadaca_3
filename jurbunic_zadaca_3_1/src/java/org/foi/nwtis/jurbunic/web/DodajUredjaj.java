/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.jurbunic.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.foi.nwtis.dkermek.web.podaci.MeteoPodaci;
import org.foi.nwtis.jurbunic.rest.klijenti.GMKlijent;
import org.foi.nwtis.jurbunic.rest.klijenti.OWMKlijent;
import org.foi.nwtis.jurbunic.web.podaci.Lokacija;

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String naziv;
        String adresa;
        String gumbGeolokacija;
        String gumbSpremi;
        String gumbPodaci;
        naziv = request.getParameter("naziv");
        adresa = request.getParameter("adresa");
        
        gumbGeolokacija = request.getParameter("geolokacija");
        gumbSpremi = request.getParameter("spremi");
        gumbPodaci = request.getParameter("podaci");

        if(gumbGeolokacija != null){
            geoLokacija(naziv, adresa);
        }else if(gumbSpremi != null){
            spremi(naziv, adresa);
        }else if(gumbPodaci != null){
            meteoPodaci(naziv, adresa);
        }
        System.out.println(gumbGeolokacija);
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
        Lokacija lokacija = gmk.getGeoLocation(adresa);
        System.out.println("Adresa: "+ adresa);
        System.out.println("Lat: " + lokacija.getLatitude());
        System.out.println("Long: " + lokacija.getLongitude());
        System.out.println();
        // todo prikazi podatke u index.jsp
        // todo zapamti geo lokaciju
        
    }

    private void spremi(String naziv, String adresa) {
        
    }

    private void meteoPodaci(String naziv, String adresa) {
        // todo preuzmi APIKEY iz konfiguracijskih podataka;
        String apikey = "";
        OWMKlijent owmKlijent = new OWMKlijent(apikey);
        // todo popuni sa stvarnim podacima
        String lat = "46.305746";
        String log = "16.3366066";
        MeteoPodaci mp = owmKlijent.getRealTimeWeather(lat, log);
        String temp = mp.getTemperatureValue().toString();
        String vlaga = mp.getHumidityValue().toString();
        String tlak = mp.getPressureValue().toString();
        
        System.out.println("Temp: "+temp);
        System.out.println("Vlaga:"+vlaga);
        System.out.println("Tlak:"+tlak);
    }

}
