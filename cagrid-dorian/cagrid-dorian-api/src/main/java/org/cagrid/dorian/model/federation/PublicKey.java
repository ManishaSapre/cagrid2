
package org.cagrid.dorian.model.federation;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for PublicKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PublicKey">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="keyAsString" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PublicKey", propOrder = {
    "keyAsString"
})
public class PublicKey
    implements Serializable, Equals, HashCode, ToString
{

    @XmlElement(required = true)
    protected String keyAsString;

    /**
     * Gets the value of the keyAsString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyAsString() {
        return keyAsString;
    }

    /**
     * Sets the value of the keyAsString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyAsString(String value) {
        this.keyAsString = value;
    }

    public String toString() {
        final ToStringStrategy strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        {
            String theKeyAsString;
            theKeyAsString = this.getKeyAsString();
            strategy.appendField(locator, this, "keyAsString", buffer, theKeyAsString);
        }
        return buffer;
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            String theKeyAsString;
            theKeyAsString = this.getKeyAsString();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "keyAsString", theKeyAsString), currentHashCode, theKeyAsString);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof PublicKey)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final PublicKey that = ((PublicKey) object);
        {
            String lhsKeyAsString;
            lhsKeyAsString = this.getKeyAsString();
            String rhsKeyAsString;
            rhsKeyAsString = that.getKeyAsString();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "keyAsString", lhsKeyAsString), LocatorUtils.property(thatLocator, "keyAsString", rhsKeyAsString), lhsKeyAsString, rhsKeyAsString)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

}
