
package br.com.fiap.fornecedor.client.governo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for notaFiscal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="notaFiscal">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentoEmissor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="documentoReceptor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valorTotal" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="valorTotalImpostos" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notaFiscal", propOrder = {
    "documentoEmissor",
    "documentoReceptor",
    "valorTotal",
    "valorTotalImpostos"
})
public class NotaFiscal {

    protected String documentoEmissor;
    protected String documentoReceptor;
    protected double valorTotal;
    protected double valorTotalImpostos;

    /**
     * Gets the value of the documentoEmissor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentoEmissor() {
        return documentoEmissor;
    }

    /**
     * Sets the value of the documentoEmissor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentoEmissor(String value) {
        this.documentoEmissor = value;
    }

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
     * Gets the value of the valorTotal property.
     * 
     */
    public double getValorTotal() {
        return valorTotal;
    }

    /**
     * Sets the value of the valorTotal property.
     * 
     */
    public void setValorTotal(double value) {
        this.valorTotal = value;
    }

    /**
     * Gets the value of the valorTotalImpostos property.
     * 
     */
    public double getValorTotalImpostos() {
        return valorTotalImpostos;
    }

    /**
     * Sets the value of the valorTotalImpostos property.
     * 
     */
    public void setValorTotalImpostos(double value) {
        this.valorTotalImpostos = value;
    }

}
