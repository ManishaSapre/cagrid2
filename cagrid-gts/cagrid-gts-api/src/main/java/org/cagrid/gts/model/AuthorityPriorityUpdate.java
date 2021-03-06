
package org.cagrid.gts.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 * <p>Java class for AuthorityPriorityUpdate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthorityPriorityUpdate">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AuthorityPrioritySpecification" type="{http://cagrid.nci.nih.gov/8/gts}AuthorityPrioritySpecification" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthorityPriorityUpdate", propOrder = {
    "authorityPrioritySpecification"
})
public class AuthorityPriorityUpdate
    implements Serializable, Equals, HashCode, ToString
{

    @XmlElement(name = "AuthorityPrioritySpecification", required = true)
    protected List<AuthorityPrioritySpecification> authorityPrioritySpecification;

    /**
     * Gets the value of the authorityPrioritySpecification property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authorityPrioritySpecification property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthorityPrioritySpecification().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AuthorityPrioritySpecification }
     * 
     * 
     */
    public List<AuthorityPrioritySpecification> getAuthorityPrioritySpecification() {
        if (authorityPrioritySpecification == null) {
            authorityPrioritySpecification = new ArrayList<AuthorityPrioritySpecification>();
        }
        return this.authorityPrioritySpecification;
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
            List<AuthorityPrioritySpecification> theAuthorityPrioritySpecification;
            theAuthorityPrioritySpecification = (((this.authorityPrioritySpecification!= null)&&(!this.authorityPrioritySpecification.isEmpty()))?this.getAuthorityPrioritySpecification():null);
            strategy.appendField(locator, this, "authorityPrioritySpecification", buffer, theAuthorityPrioritySpecification);
        }
        return buffer;
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            List<AuthorityPrioritySpecification> theAuthorityPrioritySpecification;
            theAuthorityPrioritySpecification = (((this.authorityPrioritySpecification!= null)&&(!this.authorityPrioritySpecification.isEmpty()))?this.getAuthorityPrioritySpecification():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "authorityPrioritySpecification", theAuthorityPrioritySpecification), currentHashCode, theAuthorityPrioritySpecification);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof AuthorityPriorityUpdate)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final AuthorityPriorityUpdate that = ((AuthorityPriorityUpdate) object);
        {
            List<AuthorityPrioritySpecification> lhsAuthorityPrioritySpecification;
            lhsAuthorityPrioritySpecification = (((this.authorityPrioritySpecification!= null)&&(!this.authorityPrioritySpecification.isEmpty()))?this.getAuthorityPrioritySpecification():null);
            List<AuthorityPrioritySpecification> rhsAuthorityPrioritySpecification;
            rhsAuthorityPrioritySpecification = (((that.authorityPrioritySpecification!= null)&&(!that.authorityPrioritySpecification.isEmpty()))?that.getAuthorityPrioritySpecification():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "authorityPrioritySpecification", lhsAuthorityPrioritySpecification), LocatorUtils.property(thatLocator, "authorityPrioritySpecification", rhsAuthorityPrioritySpecification), lhsAuthorityPrioritySpecification, rhsAuthorityPrioritySpecification)) {
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
