/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.jurbunic.web.zrna;

import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
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
    String id;

    /**
     * Creates a new instance of OdabirUredkaja
     */
    public OdabirUredkaja() {
        if(uredjaji == null){
            return;
        }
        for(Uredjaj u:uredjaji){
            System.out.println(u.getNaziv());
        }
    }

    public List<Uredjaj> getUredjaji() {
        return uredjaji;
    }

    public void setUredjaji(List<Uredjaj> uredjaji) {
        this.uredjaji = uredjaji;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    
    
}
