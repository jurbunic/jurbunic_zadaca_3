
package org.foi.nwtis.jurbunic.ws.serveri;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dajMinMaxVlagaZaUredjaj complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dajMinMaxVlagaZaUredjaj"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="od" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="parameter1" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dajMinMaxVlagaZaUredjaj", propOrder = {
    "id",
    "od",
    "parameter1"
})
public class DajMinMaxVlagaZaUredjaj {

    protected int id;
    protected long od;
    protected long parameter1;

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
     * Gets the value of the parameter1 property.
     * 
     */
    public long getParameter1() {
        return parameter1;
    }

    /**
     * Sets the value of the parameter1 property.
     * 
     */
    public void setParameter1(long value) {
        this.parameter1 = value;
    }

}
