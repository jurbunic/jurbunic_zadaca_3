
package org.foi.nwtis.jurbunic.ws.serveri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dodajUredaj complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dodajUredaj"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="uredaj" type="{http://serveri.ws.jurbunic.nwtis.foi.org/}uredjaj" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dodajUredaj", propOrder = {
    "uredaj"
})
public class DodajUredaj {

    protected Uredjaj uredaj;

    /**
     * Gets the value of the uredaj property.
     * 
     * @return
     *     possible object is
     *     {@link Uredjaj }
     *     
     */
    public Uredjaj getUredaj() {
        return uredaj;
    }

    /**
     * Sets the value of the uredaj property.
     * 
     * @param value
     *     allowed object is
     *     {@link Uredjaj }
     *     
     */
    public void setUredaj(Uredjaj value) {
        this.uredaj = value;
    }

}
