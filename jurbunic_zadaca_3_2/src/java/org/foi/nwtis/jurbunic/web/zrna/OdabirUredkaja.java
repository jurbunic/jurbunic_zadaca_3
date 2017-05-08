/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.jurbunic.web.zrna;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeConfigurationException;
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

    String pogreska;

    /**
     * Creates a new instance of OdabirUredkaja
     */
    public OdabirUredkaja() {
    }

    /**
     * Metoda služi za dodavanje uređaja pozivom web servisa. Ako naziv ili adresa nisu
     * unešeni tada se ispisuje greška.
     * @throws ClassNotFoundException_Exception 
     */
    public void dodajUredaj() throws ClassNotFoundException_Exception {
        if(naziv.isEmpty() || adresa.isEmpty()){
            pogreska = "Naziv ili adresa nije unešena";
            return;
        }
        uredaj = new Uredjaj();
        uredaj.setNaziv(naziv + "-" + adresa);
        MeteoWSKlijent.dodajUredaj(uredaj);
    }
    /**
     * Metoda preuzima meteo podatke za uređaje. Ako nisu unešeni od - do parametri tada se dohvaćaju svi podaci,
     * inače se dohvaćaju podaci u zadanom rasponu
     * @throws ClassNotFoundException_Exception
     * @throws ParseException 
     */

    public void preuzmiMeteoZa() throws ClassNotFoundException_Exception, ParseException {
        if(ID.length==0){
            pogreska = "Niste oznacili ni jedan uređaj!";
            return;
        }
        int id = ID[0];
        if (odDate.isEmpty() || doDate.isEmpty()) {
            preuzmiZadnje();
        } else {
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
            Date from = df.parse(odDate);
            Date to = df.parse(doDate);
            meteoPodaci = MeteoWSKlijent.dajSveMeteoPodatkeZaUredjaj(id,
                    from.getTime(),
                    to.getTime());
        }

    }

    public void preuzmiZadnje() throws ClassNotFoundException_Exception {
        int id = ID[0];
        meteoPodaci = MeteoWSKlijent.dajSveMeteoPodatkeZaUredjaj(id, 0, System.currentTimeMillis());
    }

    /**
     * Metoda služi za upis u bazu preko poziva REST servisa. Ako naziv ili adresa nije unešena tada
     * se ispisuje pogreška. Inače se izrađuje json format zapisa te se poziva servis
     * @throws MalformedURLException 
     */
    public void upisiRESTPOST() throws MalformedURLException {
        if(naziv.isEmpty() || adresa.isEmpty()){
            pogreska = "Naziv ili adresa nije unešena";
            return;
        }
        MeteoRESTResourceContainer_JerseyClient mr = new MeteoRESTResourceContainer_JerseyClient();
        JsonObjectBuilder jo = Json.createObjectBuilder();
        jo.add("naziv", naziv);
        jo.add("adresa", adresa);
        mr.postJson(jo.build().toString());
    }

    /**
     * Služi za preuzimanje podataka sa REST servisa. U slučaju da je odabrano manje od 2 uređaja, tada
     * se ispisuje pogreška te se ne radi upit prema servisu
     * 
     * @throws ParseException
     * @throws DatatypeConfigurationException 
     */
    public void preuzmiREST() throws ParseException, DatatypeConfigurationException {
        if(ID.length<2){
            pogreska = "Odabrano manje od 2 uredaja";
            return;
        }
        meteoPodaci = new ArrayList<>();
        for (int j = 0; j < ID.length; j++) {
            MeteoRESTResourceContainer_JerseyClient mr = new MeteoRESTResourceContainer_JerseyClient(String.valueOf(ID[j]));
            String neociscenJson = mr.getJson();
            JsonReader jsonCitac = Json.createReader(new StringReader(neociscenJson));
            JsonObject ob = jsonCitac.readObject();
            MeteoPodaci mp = new MeteoPodaci();
            mp.setTemperatureValue(Float.valueOf(ob.get("temp").toString()));
            mp.setPressureValue(Float.valueOf(ob.get("tlak").toString()));
            mp.setHumidityValue(Float.valueOf(ob.get("vlaga").toString()));
            meteoPodaci.add(mp);
            jsonCitac.close();
            /*
            JsonArray array = jsonCitac.readArray();
            for (int i = 0; i < array.size(); i++) {
                JsonObject objekt = array.getJsonObject(i);
                MeteoPodaci mp = new MeteoPodaci();
                DateFormat df = new SimpleDateFormat("dd.mm.yyyy hh:MM:ss:SS");
                
                Date sunset = df.parse(objekt.getString("sunSet"));
                Date sunrise = df.parse(objekt.getString("sunRise"));
                GregorianCalendar c = new GregorianCalendar();
                c.setTime(sunset);
                XMLGregorianCalendar greg = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                mp.setSunRise(greg);
                c.setTime(sunrise);
                greg = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                mp.setSunSet(greg);
                mp.setTemperatureValue(Float.valueOf(objekt.get("temperatureValue").toString()));
                mp.setTemperatureMin(Float.valueOf(objekt.get("temperatureMin").toString()));
                mp.setTemperatureMax(Float.valueOf(objekt.get("temperatureMax").toString()));
                mp.setTemperatureUnit(objekt.get("temperatureUnit").toString());
                mp.setHumidityValue(Float.valueOf(objekt.get("humidityValue").toString()));
                mp.setHumidityUnit(objekt.get("humidityUnit").toString());
                mp.setPressureValue(Float.valueOf(objekt.get("pressureValue").toString()));
                mp.setPrecipitationUnit(objekt.get("precipitationUnit").toString());
                mp.setWindSpeedValue(Float.valueOf(objekt.get("windSpeedValue").toString()));
                mp.setWindSpeedName(objekt.get("windSpeedName").toString());
                mp.setWindDirectionValue(Float.valueOf(objekt.get("windDirectionValue").toString()));
                mp.setWindDirectionCode(objekt.get("windDirectionCode").toString());
                mp.setWindDirectionName(objekt.get("windDirectionName").toString());
                mp.setCloudsValue(objekt.getInt("cloudsValue"));
                mp.setCloudsName(objekt.get("cloudsName").toString());
                mp.setVisibility(objekt.get("visibility").toString());
                mp.setPrecipitationValue(Float.valueOf(objekt.get("precipitationValue").toString()));
                mp.setPrecipitationMode(objekt.get("precipitationMode").toString());
                mp.setPrecipitationUnit(objekt.get("precipitationUnit").toString());
                mp.setWeatherNumber(objekt.getInt("weatherNumber"));
                mp.setWeatherValue(objekt.get("weatherValue").toString());
                mp.setWeatherIcon(objekt.get("weatherIcon").toString());
                Date lastUpdate = df.parse(objekt.getString("sunRise"));
                c.setTime(lastUpdate);
                greg = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
                mp.setLastUpdate(greg);
                meteoPodaci.add(mp);
            }
             */
        }
    }

    //-------------GS--------------------
    public List<Uredjaj> getUredjaji() throws ClassNotFoundException_Exception {
        uredjaji = MeteoWSKlijent.dajSveUredjaje();
        return uredjaji;
    }

    public String getPogreska() {
        return pogreska;
    }

    public void setPogreska(String pogreska) {
        this.pogreska = pogreska;
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

    public String getOdDate() {
        return odDate;
    }

    public void setOdDate(String odDate) {
        this.odDate = odDate;
    }

    public String getDoDate() {
        return doDate;
    }

    public void setDoDate(String doDate) {
        this.doDate = doDate;
    }

    static class MeteoRESTResourceContainer_JerseyClient {

        private WebTarget webTarget;
        private Client client;
        private static final String BASE_URI = "http://localhost:8084/jurbunic_zadaca_3_1/webresources";

        public MeteoRESTResourceContainer_JerseyClient() {
            client = javax.ws.rs.client.ClientBuilder.newClient();
            webTarget = client.target(BASE_URI).path("meteoREST");
        }

        public MeteoRESTResourceContainer_JerseyClient(String id) {
            client = javax.ws.rs.client.ClientBuilder.newClient();
            webTarget = client.target(BASE_URI).path("meteoREST/" + id);
        }

        public Response postJson(Object requestEntity) throws ClientErrorException {
            return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public String getJson() throws ClientErrorException {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(String.class);
        }

        public void close() {
            client.close();
        }

    }

}
