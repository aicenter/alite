//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.03.11 at 05:27:40 odp. CET 
//

package cz.agents.alite.googleearth.kml;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for colorModeEnumType.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name=&quot;colorModeEnumType&quot;&gt;
 *   &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}string&quot;&gt;
 *     &lt;enumeration value=&quot;normal&quot;/&gt;
 *     &lt;enumeration value=&quot;random&quot;/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "colorModeEnumType")
@XmlEnum
public enum ColorModeEnumType implements java.io.Serializable {

	@XmlEnumValue("normal")
	NORMAL("normal"), @XmlEnumValue("random")
	RANDOM("random");
	private final String value;

	ColorModeEnumType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static ColorModeEnumType fromValue(String v) {
		for (ColorModeEnumType c : ColorModeEnumType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}