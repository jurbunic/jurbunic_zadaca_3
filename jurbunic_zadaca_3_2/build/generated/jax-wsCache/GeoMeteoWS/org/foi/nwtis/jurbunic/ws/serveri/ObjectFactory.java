
package org.foi.nwtis.jurbunic.ws.serveri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.foi.nwtis.jurbunic.ws.serveri package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DajSveMeteoPodatkeZaUredjaj_QNAME = new QName("http://serveri.ws.jurbunic.nwtis.foi.org/", "dajSveMeteoPodatkeZaUredjaj");
    private final static QName _DajSveMeteoPodatkeZaUredjajResponse_QNAME = new QName("http://serveri.ws.jurbunic.nwtis.foi.org/", "dajSveMeteoPodatkeZaUredjajResponse");
    private final static QName _DajSveUredjaje_QNAME = new QName("http://serveri.ws.jurbunic.nwtis.foi.org/", "dajSveUredjaje");
    private final static QName _DajSveUredjajeResponse_QNAME = new QName("http://serveri.ws.jurbunic.nwtis.foi.org/", "dajSveUredjajeResponse");
    private final static QName _DodajUredaj_QNAME = new QName("http://serveri.ws.jurbunic.nwtis.foi.org/", "dodajUredaj");
    private final static QName _DodajUredajResponse_QNAME = new QName("http://serveri.ws.jurbunic.nwtis.foi.org/", "dodajUredajResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.foi.nwtis.jurbunic.ws.serveri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DajSveMeteoPodatkeZaUredjaj }
     * 
     */
    public DajSveMeteoPodatkeZaUredjaj createDajSveMeteoPodatkeZaUredjaj() {
        return new DajSveMeteoPodatkeZaUredjaj();
    }

    /**
     * Create an instance of {@link DajSveMeteoPodatkeZaUredjajResponse }
     * 
     */
    public DajSveMeteoPodatkeZaUredjajResponse createDajSveMeteoPodatkeZaUredjajResponse() {
        return new DajSveMeteoPodatkeZaUredjajResponse();
    }

    /**
     * Create an instance of {@link DajSveUredjaje }
     * 
     */
    public DajSveUredjaje createDajSveUredjaje() {
        return new DajSveUredjaje();
    }

    /**
     * Create an instance of {@link DajSveUredjajeResponse }
     * 
     */
    public DajSveUredjajeResponse createDajSveUredjajeResponse() {
        return new DajSveUredjajeResponse();
    }

    /**
     * Create an instance of {@link DodajUredaj }
     * 
     */
    public DodajUredaj createDodajUredaj() {
        return new DodajUredaj();
    }

    /**
     * Create an instance of {@link DodajUredajResponse }
     * 
     */
    public DodajUredajResponse createDodajUredajResponse() {
        return new DodajUredajResponse();
    }

    /**
     * Create an instance of {@link Uredjaj }
     * 
     */
    public Uredjaj createUredjaj() {
        return new Uredjaj();
    }

    /**
     * Create an instance of {@link Lokacija }
     * 
     */
    public Lokacija createLokacija() {
        return new Lokacija();
    }

    /**
     * Create an instance of {@link MeteoPodaci }
     * 
     */
    public MeteoPodaci createMeteoPodaci() {
        return new MeteoPodaci();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajSveMeteoPodatkeZaUredjaj }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.jurbunic.nwtis.foi.org/", name = "dajSveMeteoPodatkeZaUredjaj")
    public JAXBElement<DajSveMeteoPodatkeZaUredjaj> createDajSveMeteoPodatkeZaUredjaj(DajSveMeteoPodatkeZaUredjaj value) {
        return new JAXBElement<DajSveMeteoPodatkeZaUredjaj>(_DajSveMeteoPodatkeZaUredjaj_QNAME, DajSveMeteoPodatkeZaUredjaj.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajSveMeteoPodatkeZaUredjajResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.jurbunic.nwtis.foi.org/", name = "dajSveMeteoPodatkeZaUredjajResponse")
    public JAXBElement<DajSveMeteoPodatkeZaUredjajResponse> createDajSveMeteoPodatkeZaUredjajResponse(DajSveMeteoPodatkeZaUredjajResponse value) {
        return new JAXBElement<DajSveMeteoPodatkeZaUredjajResponse>(_DajSveMeteoPodatkeZaUredjajResponse_QNAME, DajSveMeteoPodatkeZaUredjajResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajSveUredjaje }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.jurbunic.nwtis.foi.org/", name = "dajSveUredjaje")
    public JAXBElement<DajSveUredjaje> createDajSveUredjaje(DajSveUredjaje value) {
        return new JAXBElement<DajSveUredjaje>(_DajSveUredjaje_QNAME, DajSveUredjaje.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DajSveUredjajeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.jurbunic.nwtis.foi.org/", name = "dajSveUredjajeResponse")
    public JAXBElement<DajSveUredjajeResponse> createDajSveUredjajeResponse(DajSveUredjajeResponse value) {
        return new JAXBElement<DajSveUredjajeResponse>(_DajSveUredjajeResponse_QNAME, DajSveUredjajeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DodajUredaj }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.jurbunic.nwtis.foi.org/", name = "dodajUredaj")
    public JAXBElement<DodajUredaj> createDodajUredaj(DodajUredaj value) {
        return new JAXBElement<DodajUredaj>(_DodajUredaj_QNAME, DodajUredaj.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DodajUredajResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://serveri.ws.jurbunic.nwtis.foi.org/", name = "dodajUredajResponse")
    public JAXBElement<DodajUredajResponse> createDodajUredajResponse(DodajUredajResponse value) {
        return new JAXBElement<DodajUredajResponse>(_DodajUredajResponse_QNAME, DodajUredajResponse.class, null, value);
    }

}
