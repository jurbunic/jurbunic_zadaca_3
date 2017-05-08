/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.jurbunic.web.slusaci;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.jurbunic.konfiguracije.Konfiguracija;
import org.foi.nwtis.jurbunic.konfiguracije.KonfiguracijaApstraktna;
import org.foi.nwtis.jurbunic.konfiguracije.NeispravnaKonfiguracija;
import org.foi.nwtis.jurbunic.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.jurbunic.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.jurbunic.web.PreuzmiMeteoPodatke;

/**
 * Web application lifecycle listener.
 *
 * @author grupa_1
 */
@WebListener
public class SlusacAplikacije implements ServletContextListener {
    
    ServletContext context;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            context = sce.getServletContext();
            String datoteka = context.getRealPath("/WEB-INF")
                    + File.separator + sce.getServletContext()
                            .getInitParameter("konfiguracija");
            BP_Konfiguracija bpkonf = new BP_Konfiguracija(datoteka);
            Konfiguracija konf = KonfiguracijaApstraktna.preuzmiKonfiguraciju(datoteka);
            sce.getServletContext().setAttribute("BP_Konfig", bpkonf);
            sce.getServletContext().setAttribute("konf", konf);
            PreuzmiMeteoPodatke pmp = new PreuzmiMeteoPodatke();
            pmp.setSc(context);
            pmp.start();
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NeispravnaKonfiguracija ex) {
            Logger.getLogger(SlusacAplikacije.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ServletContext getContext() {
        return context;
    }    
}
