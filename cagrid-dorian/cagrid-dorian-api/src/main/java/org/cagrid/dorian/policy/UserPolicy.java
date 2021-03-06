
package org.cagrid.dorian.policy;

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
 * <p>Java class for UserPolicy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserPolicy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://cagrid.nci.nih.gov/1/dorian-policy}UserCertificateLifetime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserPolicy", propOrder = {
    "userCertificateLifetime"
})
public class UserPolicy
    implements Serializable, Equals, HashCode, ToString
{

    @XmlElement(name = "UserCertificateLifetime", required = true)
    protected UserCertificateLifetime userCertificateLifetime;

    /**
     * Gets the value of the userCertificateLifetime property.
     * 
     * @return
     *     possible object is
     *     {@link UserCertificateLifetime }
     *     
     */
    public UserCertificateLifetime getUserCertificateLifetime() {
        return userCertificateLifetime;
    }

    /**
     * Sets the value of the userCertificateLifetime property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserCertificateLifetime }
     *     
     */
    public void setUserCertificateLifetime(UserCertificateLifetime value) {
        this.userCertificateLifetime = value;
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
            UserCertificateLifetime theUserCertificateLifetime;
            theUserCertificateLifetime = this.getUserCertificateLifetime();
            strategy.appendField(locator, this, "userCertificateLifetime", buffer, theUserCertificateLifetime);
        }
        return buffer;
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            UserCertificateLifetime theUserCertificateLifetime;
            theUserCertificateLifetime = this.getUserCertificateLifetime();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "userCertificateLifetime", theUserCertificateLifetime), currentHashCode, theUserCertificateLifetime);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof UserPolicy)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final UserPolicy that = ((UserPolicy) object);
        {
            UserCertificateLifetime lhsUserCertificateLifetime;
            lhsUserCertificateLifetime = this.getUserCertificateLifetime();
            UserCertificateLifetime rhsUserCertificateLifetime;
            rhsUserCertificateLifetime = that.getUserCertificateLifetime();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "userCertificateLifetime", lhsUserCertificateLifetime), LocatorUtils.property(thatLocator, "userCertificateLifetime", rhsUserCertificateLifetime), lhsUserCertificateLifetime, rhsUserCertificateLifetime)) {
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
