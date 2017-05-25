
package br.com.fiap.fornecedor.client.governo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for emitirNotaFiscalResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="emitirNotaFiscalResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://ws.governo.com.br/}notaFiscal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "emitirNotaFiscalResponse", propOrder = {
    "_return"
})
public class EmitirNotaFiscalResponse {

    @XmlElement(name = "return")
    protected NotaFiscal _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link NotaFiscal }
     *     
     */
    public NotaFiscal getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotaFiscal }
     *     
     */
    public void setReturn(NotaFiscal value) {
        this._return = value;
    }

}
