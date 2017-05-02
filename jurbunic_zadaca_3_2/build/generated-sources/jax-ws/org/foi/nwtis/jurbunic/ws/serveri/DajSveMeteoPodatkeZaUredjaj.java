
package org.foi.nwtis.jurbunic.ws.serveri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dajSveMeteoPodatkeZaUredjaj complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dajSveMeteoPodatkeZaUredjaj"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="od" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="do" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dajSveMeteoPodatkeZaUredjaj", propOrder = {
    "id",
    "od",
    "_do"
})
public class DajSveMeteoPodatkeZaUredjaj {

    protected int id;
    protected long od;
    @XmlElement(name = "do")
    protected long _do;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the od property.
     * 
     */
    public long getOd() {
        return od;
    }

    /**
     * Sets the value of the od property.
     * 
     */
    public void setOd(long value) {
        this.od = value;
    }

    /**
     * Gets the value of the do property.
     * 
     */
    public long getDo() {
        return _do;
    }

    /**
     * Sets the value of the do property.
     * 
     */
    public void setDo(long value) {
        this._do = value;
    }

}
