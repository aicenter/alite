//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.03.11 at 05:27:40 odp. CET 
//

package cz.agents.alite.googleearth.oasis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref=&quot;{urn:oasis:names:tc:ciq:xsdschema:xAL:2.0}AddressLine&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;choice&gt;
 *           &lt;element name=&quot;PostOfficeName&quot; maxOccurs=&quot;unbounded&quot; minOccurs=&quot;0&quot;&gt;
 *             &lt;complexType&gt;
 *               &lt;complexContent&gt;
 *                 &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *                   &lt;attGroup ref=&quot;{urn:oasis:names:tc:ciq:xsdschema:xAL:2.0}grPostal&quot;/&gt;
 *                   &lt;attribute name=&quot;Type&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}anySimpleType&quot; /&gt;
 *                 &lt;/restriction&gt;
 *               &lt;/complexContent&gt;
 *             &lt;/complexType&gt;
 *           &lt;/element&gt;
 *           &lt;element name=&quot;PostOfficeNumber&quot; minOccurs=&quot;0&quot;&gt;
 *             &lt;complexType&gt;
 *               &lt;complexContent&gt;
 *                 &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
 *                   &lt;attGroup ref=&quot;{urn:oasis:names:tc:ciq:xsdschema:xAL:2.0}grPostal&quot;/&gt;
 *                   &lt;attribute name=&quot;Indicator&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}anySimpleType&quot; /&gt;
 *                   &lt;attribute name=&quot;IndicatorOccurrence&quot;&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}NMTOKEN&quot;&gt;
 *                         &lt;enumeration value=&quot;Before&quot;/&gt;
 *                         &lt;enumeration value=&quot;After&quot;/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/attribute&gt;
 *                 &lt;/restriction&gt;
 *               &lt;/complexContent&gt;
 *             &lt;/complexType&gt;
 *           &lt;/element&gt;
 *         &lt;/choice&gt;
 *         &lt;element name=&quot;PostalRoute&quot; type=&quot;{urn:oasis:names:tc:ciq:xsdschema:xAL:2.0}PostalRouteType&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element ref=&quot;{urn:oasis:names:tc:ciq:xsdschema:xAL:2.0}PostBox&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;element ref=&quot;{urn:oasis:names:tc:ciq:xsdschema:xAL:2.0}PostalCode&quot; minOccurs=&quot;0&quot;/&gt;
 *         &lt;any/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name=&quot;Type&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}anySimpleType&quot; /&gt;
 *       &lt;attribute name=&quot;Indicator&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}anySimpleType&quot; /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "addressLine", "postOfficeName",
		"postOfficeNumber", "postalRoute", "postBox", "postalCode", "any" })
@XmlRootElement(name = "PostOffice")
public class PostOffice implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9213143900269913825L;
	@XmlElement(name = "AddressLine")
	protected List<AddressLine> addressLine;
	@XmlElement(name = "PostOfficeName")
	protected List<PostOffice.PostOfficeName> postOfficeName;
	@XmlElement(name = "PostOfficeNumber")
	protected PostOffice.PostOfficeNumber postOfficeNumber;
	@XmlElement(name = "PostalRoute")
	protected PostalRouteType postalRoute;
	@XmlElement(name = "PostBox")
	protected PostBox postBox;
	@XmlElement(name = "PostalCode")
	protected PostalCode postalCode;
	@XmlAnyElement(lax = true)
	protected List<Object> any;
	@XmlAttribute(name = "Type")
	@XmlSchemaType(name = "anySimpleType")
	protected String type;
	@XmlAttribute(name = "Indicator")
	@XmlSchemaType(name = "anySimpleType")
	protected String indicator;
	@XmlAnyAttribute
	private Map<QName, String> otherAttributes = new HashMap<QName, String>();

	/**
	 * Gets the value of the addressLine property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the addressLine property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAddressLine().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link AddressLine }
	 * 
	 * 
	 */
	public List<AddressLine> getAddressLine() {
		if (addressLine == null) {
			addressLine = new ArrayList<AddressLine>();
		}
		return this.addressLine;
	}

	/**
	 * Gets the value of the postOfficeName property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the postOfficeName property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getPostOfficeName().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link PostOffice.PostOfficeName }
	 * 
	 * 
	 */
	public List<PostOffice.PostOfficeName> getPostOfficeName() {
		if (postOfficeName == null) {
			postOfficeName = new ArrayList<PostOffice.PostOfficeName>();
		}
		return this.postOfficeName;
	}

	/**
	 * Gets the value of the postOfficeNumber property.
	 * 
	 * @return possible object is {@link PostOffice.PostOfficeNumber }
	 * 
	 */
	public PostOffice.PostOfficeNumber getPostOfficeNumber() {
		return postOfficeNumber;
	}

	/**
	 * Sets the value of the postOfficeNumber property.
	 * 
	 * @param value
	 *            allowed object is {@link PostOffice.PostOfficeNumber }
	 * 
	 */
	public void setPostOfficeNumber(PostOffice.PostOfficeNumber value) {
		this.postOfficeNumber = value;
	}

	/**
	 * Gets the value of the postalRoute property.
	 * 
	 * @return possible object is {@link PostalRouteType }
	 * 
	 */
	public PostalRouteType getPostalRoute() {
		return postalRoute;
	}

	/**
	 * Sets the value of the postalRoute property.
	 * 
	 * @param value
	 *            allowed object is {@link PostalRouteType }
	 * 
	 */
	public void setPostalRoute(PostalRouteType value) {
		this.postalRoute = value;
	}

	/**
	 * Gets the value of the postBox property.
	 * 
	 * @return possible object is {@link PostBox }
	 * 
	 */
	public PostBox getPostBox() {
		return postBox;
	}

	/**
	 * Sets the value of the postBox property.
	 * 
	 * @param value
	 *            allowed object is {@link PostBox }
	 * 
	 */
	public void setPostBox(PostBox value) {
		this.postBox = value;
	}

	/**
	 * Gets the value of the postalCode property.
	 * 
	 * @return possible object is {@link PostalCode }
	 * 
	 */
	public PostalCode getPostalCode() {
		return postalCode;
	}

	/**
	 * Sets the value of the postalCode property.
	 * 
	 * @param value
	 *            allowed object is {@link PostalCode }
	 * 
	 */
	public void setPostalCode(PostalCode value) {
		this.postalCode = value;
	}

	/**
	 * Gets the value of the any property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the any property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getAny().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Object }
	 * 
	 * 
	 */
	public List<Object> getAny() {
		if (any == null) {
			any = new ArrayList<Object>();
		}
		return this.any;
	}

	/**
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the value of the type property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setType(String value) {
		this.type = value;
	}

	/**
	 * Gets the value of the indicator property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getIndicator() {
		return indicator;
	}

	/**
	 * Sets the value of the indicator property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setIndicator(String value) {
		this.indicator = value;
	}

	/**
	 * Gets a map that contains attributes that aren't bound to any typed
	 * property on this class.
	 * 
	 * <p>
	 * the map is keyed by the name of the attribute and the value is the string
	 * value of the attribute.
	 * 
	 * the map returned by this method is live, and you can add new attribute by
	 * updating the map directly. Because of this design, there's no setter.
	 * 
	 * 
	 * @return always non-null
	 */
	public Map<QName, String> getOtherAttributes() {
		return otherAttributes;
	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 * 
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
	 *       &lt;attGroup ref=&quot;{urn:oasis:names:tc:ciq:xsdschema:xAL:2.0}grPostal&quot;/&gt;
	 *       &lt;attribute name=&quot;Type&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}anySimpleType&quot; /&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "content" })
	public static class PostOfficeName implements java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7987041953842106003L;
		@XmlValue
		protected String content;
		@XmlAttribute(name = "Type")
		@XmlSchemaType(name = "anySimpleType")
		protected String type;
		@XmlAttribute(name = "Code")
		@XmlSchemaType(name = "anySimpleType")
		protected String code;
		@XmlAnyAttribute
		private Map<QName, String> otherAttributes = new HashMap<QName, String>();

		/**
		 * Gets the value of the content property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getContent() {
			return content;
		}

		/**
		 * Sets the value of the content property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setContent(String value) {
			this.content = value;
		}

		/**
		 * Gets the value of the type property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getType() {
			return type;
		}

		/**
		 * Sets the value of the type property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setType(String value) {
			this.type = value;
		}

		/**
		 * Gets the value of the code property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getCode() {
			return code;
		}

		/**
		 * Sets the value of the code property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setCode(String value) {
			this.code = value;
		}

		/**
		 * Gets a map that contains attributes that aren't bound to any typed
		 * property on this class.
		 * 
		 * <p>
		 * the map is keyed by the name of the attribute and the value is the
		 * string value of the attribute.
		 * 
		 * the map returned by this method is live, and you can add new
		 * attribute by updating the map directly. Because of this design,
		 * there's no setter.
		 * 
		 * 
		 * @return always non-null
		 */
		public Map<QName, String> getOtherAttributes() {
			return otherAttributes;
		}

	}

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * 
	 * <p>
	 * The following schema fragment specifies the expected content contained
	 * within this class.
	 * 
	 * <pre>
	 * &lt;complexType&gt;
	 *   &lt;complexContent&gt;
	 *     &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}anyType&quot;&gt;
	 *       &lt;attGroup ref=&quot;{urn:oasis:names:tc:ciq:xsdschema:xAL:2.0}grPostal&quot;/&gt;
	 *       &lt;attribute name=&quot;Indicator&quot; type=&quot;{http://www.w3.org/2001/XMLSchema}anySimpleType&quot; /&gt;
	 *       &lt;attribute name=&quot;IndicatorOccurrence&quot;&gt;
	 *         &lt;simpleType&gt;
	 *           &lt;restriction base=&quot;{http://www.w3.org/2001/XMLSchema}NMTOKEN&quot;&gt;
	 *             &lt;enumeration value=&quot;Before&quot;/&gt;
	 *             &lt;enumeration value=&quot;After&quot;/&gt;
	 *           &lt;/restriction&gt;
	 *         &lt;/simpleType&gt;
	 *       &lt;/attribute&gt;
	 *     &lt;/restriction&gt;
	 *   &lt;/complexContent&gt;
	 * &lt;/complexType&gt;
	 * </pre>
	 * 
	 * 
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "content" })
	public static class PostOfficeNumber implements java.io.Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1739126195348497336L;
		@XmlValue
		protected String content;
		@XmlAttribute(name = "Indicator")
		@XmlSchemaType(name = "anySimpleType")
		protected String indicator;
		@XmlAttribute(name = "IndicatorOccurrence")
		@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
		protected String indicatorOccurrence;
		@XmlAttribute(name = "Code")
		@XmlSchemaType(name = "anySimpleType")
		protected String code;
		@XmlAnyAttribute
		private Map<QName, String> otherAttributes = new HashMap<QName, String>();

		/**
		 * Gets the value of the content property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getContent() {
			return content;
		}

		/**
		 * Sets the value of the content property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setContent(String value) {
			this.content = value;
		}

		/**
		 * Gets the value of the indicator property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getIndicator() {
			return indicator;
		}

		/**
		 * Sets the value of the indicator property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setIndicator(String value) {
			this.indicator = value;
		}

		/**
		 * Gets the value of the indicatorOccurrence property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getIndicatorOccurrence() {
			return indicatorOccurrence;
		}

		/**
		 * Sets the value of the indicatorOccurrence property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setIndicatorOccurrence(String value) {
			this.indicatorOccurrence = value;
		}

		/**
		 * Gets the value of the code property.
		 * 
		 * @return possible object is {@link String }
		 * 
		 */
		public String getCode() {
			return code;
		}

		/**
		 * Sets the value of the code property.
		 * 
		 * @param value
		 *            allowed object is {@link String }
		 * 
		 */
		public void setCode(String value) {
			this.code = value;
		}

		/**
		 * Gets a map that contains attributes that aren't bound to any typed
		 * property on this class.
		 * 
		 * <p>
		 * the map is keyed by the name of the attribute and the value is the
		 * string value of the attribute.
		 * 
		 * the map returned by this method is live, and you can add new
		 * attribute by updating the map directly. Because of this design,
		 * there's no setter.
		 * 
		 * 
		 * @return always non-null
		 */
		public Map<QName, String> getOtherAttributes() {
			return otherAttributes;
		}

	}

}