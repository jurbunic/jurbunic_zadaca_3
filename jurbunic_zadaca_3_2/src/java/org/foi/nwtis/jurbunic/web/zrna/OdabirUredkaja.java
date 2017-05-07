/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.jurbunic.web.zrna;

import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.foi.nwtis.jurbunic.ws.klijenti.MeteoWSKlijent;
import org.foi.nwtis.jurbunic.ws.serveri.ClassNotFoundException_Exception;
import org.foi.nwtis.jurbunic.ws.serveri.MeteoPodaci;
import org.foi.nwtis.jurbunic.ws.serveri.Uredjaj;

/**
 *
 * @author grupa_1
 */
@Named(value = "odabirUredkaja")
@RequestScoped
public class OdabirUredkaja {

    String naziv;
    String adresa;
    List<Uredjaj> uredjaji;
    List<MeteoPodaci> meteoPodaci;
    Integer[] ID;
    
    Uredjaj uredaj;
    String odDate;
    String doDate;
    
    /**
     * Creates a new instance of OdabirUredkaja
     */
    public OdabirUredkaja() {
    }

    public void dodajUredaj() throws ClassNotFoundException_Exception {
        uredaj = new Uredjaj();
        uredaj.setNaziv(naziv + "-" + adresa);
        MeteoWSKlijent.dodajUredaj(uredaj);
    }

    public void preuzmiMeteoZa() throws ClassNotFoundException_Exception {
        int b = ID[0];
        meteoPodaci = MeteoWSKlijent.dajSveMeteoPodatkeZaUredjaj(b, 
                1483806002312l, 
                System.currentTimeMillis());

    }

    public List<Uredjaj> getUredjaji() throws ClassNotFoundException_Exception {
        uredjaji = MeteoWSKlijent.dajSveUredjaje();
        return uredjaji;
    }

    public void setUredjaji(List<Uredjaj> uredjaji) {
        this.uredjaji = uredjaji;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Integer[] getID() {
        return ID;
    }

    public void setID(Integer[] ID) {
        this.ID = ID;
    }

    public List<MeteoPodaci> getMeteoPodaci() {
        return meteoPodaci;
    }

    public void setMeteoPodaci(List<MeteoPodaci> meteoPodaci) {
        this.meteoPodaci = meteoPodaci;
    }

    
    
}
