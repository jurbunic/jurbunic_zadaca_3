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

    List<Uredjaj> uredjaji;
    String id;

    /**
     * Creates a new instance of OdabirUredkaja
     */
    public OdabirUredkaja() {
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
}
