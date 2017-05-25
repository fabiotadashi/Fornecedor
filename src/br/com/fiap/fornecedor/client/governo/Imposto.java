
package br.com.fiap.fornecedor.client.governo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for imposto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="imposto">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.governo.com.br/}arrayList">
 *       &lt;sequence>
 *         &lt;element name="aliquota" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "imposto", propOrder = {
    "aliquota",
    "nome"
})
public class Imposto
    extends ArrayList
{

    protected double aliquota;
    protected String nome;

    /**
     * Gets the value of the aliquota property.
     * 
     */
    public double getAliquota() {
        return aliquota;
    }

    /**
     * Sets the value of the aliquota property.
     * 
     */
    public void setAliquota(double value) {
        this.aliquota = value;
    }

    /**
     * Gets the value of the nome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets the value of the nome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

}
