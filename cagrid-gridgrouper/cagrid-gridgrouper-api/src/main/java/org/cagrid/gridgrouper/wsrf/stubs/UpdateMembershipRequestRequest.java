
package org.cagrid.gridgrouper.wsrf.stubs;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.cagrid.gridgrouper.model.GroupIdentifier;
import org.cagrid.gridgrouper.model.MembershipRequestUpdate;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="group">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://cagrid.nci.nih.gov/1/GridGrouper}GroupIdentifier"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="subject">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://cagrid.nci.nih.gov/1/GridGrouper}SubjectIdentifier"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="update">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://cagrid.nci.nih.gov/1/GridGrouper}MembershipRequestUpdate"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "group",
    "subject",
    "update"
})
@XmlRootElement(name = "UpdateMembershipRequestRequest")
public class UpdateMembershipRequestRequest
    implements Serializable
{

    @XmlElement(required = true)
    protected Group group;
    @XmlElement(required = true)
    protected Subject subject;
    @XmlElement(required = true)
    protected Update update;

    /**
     * Gets the value of the group property.
     * 
     * @return
     *     possible object is
     *     {@link org.cagrid.gridgrouper.wsrf.stubs.UpdateMembershipRequestRequest.Group }
     *     
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Sets the value of the group property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.cagrid.gridgrouper.wsrf.stubs.UpdateMembershipRequestRequest.Group }
     *     
     */
    public void setGroup(Group value) {
        this.group = value;
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link org.cagrid.gridgrouper.wsrf.stubs.UpdateMembershipRequestRequest.Subject }
     *     
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.cagrid.gridgrouper.wsrf.stubs.UpdateMembershipRequestRequest.Subject }
     *     
     */
    public void setSubject(Subject value) {
        this.subject = value;
    }

    /**
     * Gets the value of the update property.
     * 
     * @return
     *     possible object is
     *     {@link org.cagrid.gridgrouper.wsrf.stubs.UpdateMembershipRequestRequest.Update }
     *     
     */
    public Update getUpdate() {
        return update;
    }

    /**
     * Sets the value of the update property.
     * 
     * @param value
     *     allowed object is
     *     {@link org.cagrid.gridgrouper.wsrf.stubs.UpdateMembershipRequestRequest.Update }
     *     
     */
    public void setUpdate(Update value) {
        this.update = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://cagrid.nci.nih.gov/1/GridGrouper}GroupIdentifier"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "groupIdentifier"
    })
    public static class Group
        implements Serializable
    {

        @XmlElement(name = "GroupIdentifier", namespace = "http://cagrid.nci.nih.gov/1/GridGrouper", required = true)
        protected GroupIdentifier groupIdentifier;

        /**
         * Gets the value of the groupIdentifier property.
         * 
         * @return
         *     possible object is
         *     {@link org.cagrid.gridgrouper.model.GroupIdentifier }
         *     
         */
        public GroupIdentifier getGroupIdentifier() {
            return groupIdentifier;
        }

        /**
         * Sets the value of the groupIdentifier property.
         * 
         * @param value
         *     allowed object is
         *     {@link org.cagrid.gridgrouper.model.GroupIdentifier }
         *     
         */
        public void setGroupIdentifier(GroupIdentifier value) {
            this.groupIdentifier = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://cagrid.nci.nih.gov/1/GridGrouper}SubjectIdentifier"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "subjectIdentifier"
    })
    public static class Subject
        implements Serializable
    {

        @XmlElement(name = "SubjectIdentifier", namespace = "http://cagrid.nci.nih.gov/1/GridGrouper", required = true)
        protected String subjectIdentifier;

        /**
         * Gets the value of the subjectIdentifier property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSubjectIdentifier() {
            return subjectIdentifier;
        }

        /**
         * Sets the value of the subjectIdentifier property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSubjectIdentifier(String value) {
            this.subjectIdentifier = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://cagrid.nci.nih.gov/1/GridGrouper}MembershipRequestUpdate"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "membershipRequestUpdate"
    })
    public static class Update
        implements Serializable
    {

        @XmlElement(name = "MembershipRequestUpdate", namespace = "http://cagrid.nci.nih.gov/1/GridGrouper", required = true)
        protected MembershipRequestUpdate membershipRequestUpdate;

        /**
         * Gets the value of the membershipRequestUpdate property.
         * 
         * @return
         *     possible object is
         *     {@link org.cagrid.gridgrouper.model.MembershipRequestUpdate }
         *     
         */
        public MembershipRequestUpdate getMembershipRequestUpdate() {
            return membershipRequestUpdate;
        }

        /**
         * Sets the value of the membershipRequestUpdate property.
         * 
         * @param value
         *     allowed object is
         *     {@link org.cagrid.gridgrouper.model.MembershipRequestUpdate }
         *     
         */
        public void setMembershipRequestUpdate(MembershipRequestUpdate value) {
            this.membershipRequestUpdate = value;
        }

    }

}
