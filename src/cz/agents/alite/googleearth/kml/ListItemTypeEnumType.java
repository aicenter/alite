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
 * Java class for listItemTypeEnumType.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name=&quot;listItemTypeEnumType&quot;&gt;
 *   &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}string&quot;&gt;
 *     &lt;enumeration value=&quot;radioFolder&quot;/&gt;
 *     &lt;enumeration value=&quot;check&quot;/&gt;
 *     &lt;enumeration value=&quot;checkHideChildren&quot;/&gt;
 *     &lt;enumeration value=&quot;checkOffOnly&quot;/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "listItemTypeEnumType")
@XmlEnum
public enum ListItemTypeEnumType implements java.io.Serializable {

	@XmlEnumValue("radioFolder")
	RADIO_FOLDER("radioFolder"), @XmlEnumValue("check")
	CHECK("check"), @XmlEnumValue("checkHideChildren")
	CHECK_HIDE_CHILDREN("checkHideChildren"), @XmlEnumValue("checkOffOnly")
	CHECK_OFF_ONLY("checkOffOnly");
	private final String value;

	ListItemTypeEnumType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static ListItemTypeEnumType fromValue(String v) {
		for (ListItemTypeEnumType c : ListItemTypeEnumType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}