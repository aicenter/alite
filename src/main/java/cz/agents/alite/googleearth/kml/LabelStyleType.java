//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.03.11 at 05:27:40 odp. CET 
//

package cz.agents.alite.googleearth.kml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for LabelStyleType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name=&quot;LabelStyleType&quot;&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base=&quot;{http://earth.google.com/kml/2.2}AbstractColorStyleType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref=&quot;{http://earth.google.com/kml/2.2}scale&quot; minOccurs=&quot;0&quot;/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LabelStyleType", propOrder = { "scale" })
public class LabelStyleType extends AbstractColorStyleType implements
		java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4148186202838770704L;
	@XmlElement(defaultValue = "1.0")
	protected Double scale;

	/**
	 * Gets the value of the scale property.
	 * 
	 * @return possible object is {@link Double }
	 * 
	 */
	public Double getScale() {
		return scale;
	}

	/**
	 * Sets the value of the scale property.
	 * 
	 * @param value
	 *            allowed object is {@link Double }
	 * 
	 */
	public void setScale(Double value) {
		this.scale = value;
	}

}
