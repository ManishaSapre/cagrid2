/*
 * Portions of this file Copyright 1999-2005 University of Chicago
 * Portions of this file Copyright 1999-2005 The University of Southern California.
 *
 * This file or a portion of this file is licensed under the
 * terms of the Globus Toolkit Public License, found at
 * http://www.globus.org/toolkit/download/license.html.
 * If you redistribute this file, with or without
 * modifications, you must include this notice in the file.
 */
package org.cagrid.security.ssl.proxy.trust;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

/**
 * Represents the policy part of the ProxyCertInfo extension. <BR>
 * 
 * <PRE>
 * ProxyPolicy ::= SEQUENCE {
 *    policyLanguage    OBJECT IDENTIFIER,
 *    policy            OCTET STRING OPTIONAL }
 * </PRE>
 */
public class ProxyPolicy implements DEREncodable {

	/** Impersonation proxy OID */
	public static final DERObjectIdentifier IMPERSONATION = new DERObjectIdentifier(
			"1.3.6.1.5.5.7.21.1");

	/** Independent proxy OID */
	public static final DERObjectIdentifier INDEPENDENT = new DERObjectIdentifier(
			"1.3.6.1.5.5.7.21.2");

	/** Limited proxy OID */
	public static final DERObjectIdentifier LIMITED = new DERObjectIdentifier(
			"1.3.6.1.4.1.3536.1.1.1.9");

	private DERObjectIdentifier policyLanguage;
	private DEROctetString policy;

	/**
	 * Creates a new instance of the ProxyPolicy object from given ASN1Sequence
	 * object.
	 * 
	 * @param seq
	 *            ASN1Sequence object to create the instance from.
	 */
	public ProxyPolicy(ASN1Sequence seq) {
		if (seq.size() < 1) {
			throw new IllegalArgumentException("Invalid sequence");
		}
		this.policyLanguage = (DERObjectIdentifier) seq.getObjectAt(0);
		if (seq.size() > 1) {
			DEREncodable obj = seq.getObjectAt(1);
			if (obj instanceof DERTaggedObject) {
				obj = ((DERTaggedObject) obj).getObject();
			}
			this.policy = (DEROctetString) obj;
		}
		checkConstraints();
	}

	/**
	 * Returns the DER-encoded ASN.1 representation of proxy policy.
	 * 
	 * @return <code>DERObject</code> the encoded representation of the proxy
	 *         policy.
	 */
	public DERObject getDERObject() {
		ASN1EncodableVector vec = new ASN1EncodableVector();

		vec.add(this.policyLanguage);

		if (this.policy != null) {
			vec.add(this.policy);
		}

		return new DERSequence(vec);
	}

	/**
	 * Creates a new instance of the ProxyPolicy object.
	 * 
	 * @param policyLanguage
	 *            the language policy Oid.
	 * @param policy
	 *            the policy.
	 */
	public ProxyPolicy(DERObjectIdentifier policyLanguage, byte[] policy) {
		if (policyLanguage == null) {
			throw new IllegalArgumentException("Policy langauge oid required");
		}
		this.policyLanguage = policyLanguage;
		if (policy != null) {
			this.policy = new DEROctetString(policy);
		}
		checkConstraints();
	}

	/**
	 * Creates a new instance of the ProxyPolicy object.
	 * 
	 * @param policyLanguageOid
	 *            the language policy Oid.
	 * @param policy
	 *            the policy.
	 */
	public ProxyPolicy(String policyLanguageOid, byte[] policy) {
		if (policyLanguageOid == null) {
			throw new IllegalArgumentException("Policy langauge oid required");
		}
		this.policyLanguage = new DERObjectIdentifier(policyLanguageOid);
		if (policy != null) {
			this.policy = new DEROctetString(policy);
		}
		checkConstraints();
	}

	/**
	 * Creates a new instance of the ProxyPolicy object.
	 * 
	 * @param policyLanguage
	 *            the language policy Oid.
	 * @param policy
	 *            the policy.
	 */
	public ProxyPolicy(DERObjectIdentifier policyLanguage, String policy) {
		this(policyLanguage, (policy != null) ? policy.getBytes() : null);
	}

	/**
	 * Creates a new instance of the ProxyPolicy object with no policy.
	 * 
	 * @param policyLanguage
	 *            the language policy Oid.
	 */
	public ProxyPolicy(DERObjectIdentifier policyLanguage) {
		this(policyLanguage, (byte[]) null);
	}

	protected void checkConstraints() {
		if ((this.policyLanguage.equals(IMPERSONATION) || this.policyLanguage
				.equals(INDEPENDENT)) && this.policy != null) {
			throw new IllegalArgumentException("Constrains violation.");
		}
	}

	/**
	 * Returns the actual policy embedded in the ProxyPolicy object.
	 * 
	 * @return the policy in bytes. Might be null.
	 */
	public byte[] getPolicy() {
		return (this.policy != null) ? this.policy.getOctets() : null;
	}

	/**
	 * Returns the actual policy embedded in the ProxyPolicy object.
	 * 
	 * @return the policy as String. Might be null.
	 */
	public String getPolicyAsString() {
		return (this.policy != null) ? new String(this.policy.getOctets())
				: null;
	}

	/**
	 * Returns the policy language of the ProxyPolicy.
	 * 
	 * @return the policy language Oid.
	 */
	public DERObjectIdentifier getPolicyLanguage() {
		return this.policyLanguage;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("ProxyPolicy: ");
		buf.append(this.policyLanguage.getId());
		if (this.policy != null) {
			buf.append(System.getProperty("line.separator"));
			buf.append(getPolicyAsString());
		}
		return buf.toString();
	}

}
