
package br.com.fiap.fornecedor.client.governo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for emitirNotaFiscal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="emitirNotaFiscal">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentoReceptor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valorNota" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "emitirNotaFiscal", propOrder = {
    "documentoReceptor",
    "valorNota"
})
public class EmitirNotaFiscal {

    protected String documentoReceptor;
    protected Double valorNota;

    /**
     * Gets the value of the documentoReceptor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentoReceptor() {
        return documentoReceptor;
    }

    /**
     * Sets the value of the documentoReceptor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentoReceptor(String value) {
        this.documentoReceptor = value;
    }

    /**
     * Gets the value of the valorNota property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getValorNota() {
        return valorNota;
    }

    /**
     * Sets the value of the valorNota property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setValorNota(Double value) {
        this.valorNota = value;
    }

}
