package org.cagrid.dorian.service.federation;

import gov.nih.nci.cagrid.common.Utils;

import java.security.interfaces.RSAPublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.cagrid.core.common.FaultHelper;
import org.cagrid.dorian.common.CommonUtils;
import org.cagrid.dorian.common.Lifetime;
import org.cagrid.dorian.common.X509Certificate;
import org.cagrid.dorian.model.exceptions.DorianInternalException;
import org.cagrid.dorian.model.exceptions.InvalidHostCertificateException;
import org.cagrid.dorian.model.exceptions.InvalidHostCertificateRequestException;
import org.cagrid.dorian.model.federation.HostCertificateFilter;
import org.cagrid.dorian.model.federation.HostCertificateRecord;
import org.cagrid.dorian.model.federation.HostCertificateRequest;
import org.cagrid.dorian.model.federation.HostCertificateStatus;
import org.cagrid.dorian.model.federation.HostCertificateUpdate;
import org.cagrid.dorian.model.federation.HostRecord;
import org.cagrid.dorian.model.federation.HostSearchCriteria;
import org.cagrid.dorian.model.federation.PublicKey;
import org.cagrid.dorian.service.CertificateSignatureAlgorithm;
import org.cagrid.dorian.service.ca.CertificateAuthority;
import org.cagrid.dorian.service.util.PreparedStatementBuilder;
import org.cagrid.gaards.pki.CertUtil;
import org.cagrid.gaards.pki.KeyUtil;
import org.cagrid.tools.database.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostCertificateManager {

	public static final String TABLE = "host_certificates";
	public static final String ID = "ID";
	public static final String SERIAL = "SERIAL_NUMBER";
	public static final String HOST = "HOST";
	public static final String SUBJECT = "SUBJECT";
	public static final String STATUS = "STATUS";
	public static final String OWNER = "OWNER";
	public static final String CERTIFICATE = "CERTIFICATE";
	public static final String PUBLIC_KEY = "PUBLIC_KEY";
	public static final String EXPIRATION = "EXPIRATION";

	private final static Logger logger = LoggerFactory.getLogger(HostCertificateManager.class);

	private boolean dbBuilt = false;
	private Database db;
	private CertificateAuthority ca;
	private IdentityFederationProperties conf;
	private Publisher publisher;
	private CertificateBlacklistManager blackList;

	public HostCertificateManager(Database db, IdentityFederationProperties conf, CertificateAuthority ca, Publisher publisher, CertificateBlacklistManager blackList) {
		this.db = db;
		this.ca = ca;
		this.conf = conf;
		this.publisher = publisher;
		this.blackList = blackList;
	}

	private void publishCRLIfNeeded(HostCertificateStatus s1, HostCertificateStatus s2) {
		if ((s1.equals(HostCertificateStatus.ACTIVE)) && (s2.equals(HostCertificateStatus.SUSPENDED))) {
			publisher.publishCRL();
		} else if ((s1.equals(HostCertificateStatus.ACTIVE)) && (s2.equals(HostCertificateStatus.COMPROMISED))) {
			publisher.publishCRL();
		} else if ((s1.equals(HostCertificateStatus.SUSPENDED)) && (s2.equals(HostCertificateStatus.ACTIVE))) {
			publisher.publishCRL();
		}

	}

	public synchronized HostCertificateRecord renewHostCertificate(long id, CertificateSignatureAlgorithm algorithm) throws DorianInternalException, InvalidHostCertificateException {
		HostCertificateRecord record = this.getHostCertificateRecord(id);
		if (!record.getStatus().equals(HostCertificateStatus.ACTIVE)) {
			InvalidHostCertificateException fault = FaultHelper.createFaultException(InvalidHostCertificateException.class, "Only active host certificates may be renewed.");
			throw fault;
		}

		Connection c = null;
		try {
			java.security.cert.X509Certificate oldCert = CertUtil.loadCertificate(record.getCertificate().getCertificateAsString());
			blackList.addCertificateToBlackList(oldCert, CertificateBlacklistManager.CERTIFICATE_RENEWED);
			java.security.PublicKey key = KeyUtil.loadPublicKey(record.getPublicKey().getKeyAsString());
			Date start = new Date();
			Lifetime lifetime = this.conf.getIssuedCertificateLifetime();
			Date end = org.cagrid.dorian.service.util.Utils.getExpiredDate(lifetime);
			if (end.after(ca.getCACertificate().getNotAfter())) {
				end = ca.getCACertificate().getNotAfter();
			}
			java.security.cert.X509Certificate cert = ca.signHostCertificate(record.getHost(), key, start, end, algorithm);
			record.setSerialNumber(cert.getSerialNumber().longValue());
			record.setSubject(cert.getSubjectDN().getName());
			X509Certificate x509 = new X509Certificate();
			x509.setCertificateAsString(CertUtil.writeCertificate(cert));
			record.setCertificate(x509);
			c = db.getConnection();

			PreparedStatement s = c.prepareStatement("update " + TABLE + " SET " + SERIAL + " = ? , " + SUBJECT + " = ? , " + EXPIRATION + " = ? , " + CERTIFICATE + " = ? WHERE " + ID + "= ?");
			s.setLong(1, record.getSerialNumber());
			s.setString(2, record.getSubject());
			s.setLong(3, cert.getNotAfter().getTime());
			s.setString(4, record.getCertificate().getCertificateAsString());
			s.setLong(5, record.getId());
			s.execute();
			return record;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "An unexpected error occurred.");
			FaultHelper.addMessage(fault, e.getMessage());
			throw fault;

		} finally {
			db.releaseConnection(c);
		}
	}

	public synchronized HostCertificateRecord approveHostCertifcate(long id, CertificateSignatureAlgorithm alg) throws DorianInternalException, InvalidHostCertificateException {
		Connection c = null;
		HostCertificateRecord record = this.getHostCertificateRecord(id);

		// Check to see if the status is pending.
		if (!record.getStatus().equals(HostCertificateStatus.PENDING)) {
			InvalidHostCertificateException fault = FaultHelper.createFaultException(InvalidHostCertificateException.class, "Only pending host certificates may be approved.");
			throw fault;
		}
		try {
			java.security.PublicKey key = KeyUtil.loadPublicKey(record.getPublicKey().getKeyAsString());
			String host = record.getHost();

			Date start = new Date();
			Lifetime lifetime = this.conf.getIssuedCertificateLifetime();
			Date end = org.cagrid.dorian.service.util.Utils.getExpiredDate(lifetime);
			if (end.after(ca.getCACertificate().getNotAfter())) {
				end = ca.getCACertificate().getNotAfter();
			}
			java.security.cert.X509Certificate cert = ca.signHostCertificate(host, key, start, end, alg);

			record.setSerialNumber(cert.getSerialNumber().longValue());
			record.setSubject(cert.getSubjectDN().getName());
			record.setStatus(HostCertificateStatus.ACTIVE);
			X509Certificate x509 = new X509Certificate();
			x509.setCertificateAsString(CertUtil.writeCertificate(cert));
			record.setCertificate(x509);
			c = db.getConnection();
			PreparedStatement s = c.prepareStatement("update " + TABLE + " SET " + SERIAL + " = ? , " + SUBJECT + " = ? , " + STATUS + " = ? , " + EXPIRATION + " = ?, " + CERTIFICATE + " = ? WHERE "
					+ ID + "= ?");
			s.setLong(1, record.getSerialNumber());
			s.setString(2, record.getSubject());
			s.setString(3, record.getStatus().value());
			s.setLong(4, cert.getNotAfter().getTime());
			s.setString(5, record.getCertificate().getCertificateAsString());
			s.setLong(6, record.getId());
			s.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "An unexpected error occurred.");
			FaultHelper.addMessage(fault, e.getMessage());
			throw fault;

		} finally {
			db.releaseConnection(c);
		}
		return record;
	}

	public synchronized long requestHostCertifcate(String owner, HostCertificateRequest req) throws DorianInternalException, InvalidHostCertificateRequestException {

		if (Utils.clean(req.getHostname()) == null) {
			InvalidHostCertificateRequestException fault = FaultHelper.createFaultException(InvalidHostCertificateRequestException.class, "No host specified.");
			throw fault;
		}

		if (req.getPublicKey() == null) {
			InvalidHostCertificateRequestException fault = FaultHelper.createFaultException(InvalidHostCertificateRequestException.class, "No public key specified.");
			throw fault;
		}

		if (Utils.clean(req.getPublicKey().getKeyAsString()) == null) {
			InvalidHostCertificateRequestException fault = FaultHelper.createFaultException(InvalidHostCertificateRequestException.class, "No public key specified.");
			throw fault;
		}

		// 1) We need to verify that a certificate for this host has never been
		// requested, unless it was compromised or rejected.
		if (certificateCannotBeRequested(req.getHostname())) {
			InvalidHostCertificateRequestException fault = FaultHelper.createFaultException(InvalidHostCertificateRequestException.class,
					"A host certificate cannot be request for " + req.getHostname() + " a certificate for that host already exists.");
			throw fault;
		}

		java.security.PublicKey key = null;
		try {
			key = KeyUtil.loadPublicKey(req.getPublicKey().getKeyAsString());
		} catch (Exception e) {
			InvalidHostCertificateRequestException fault = FaultHelper.createFaultException(InvalidHostCertificateRequestException.class, "Invalid Public Key provided.");
			throw fault;
		}

		if (key == null) {
			InvalidHostCertificateRequestException fault = FaultHelper.createFaultException(InvalidHostCertificateRequestException.class, "Invalid Public Key provided.");
			throw fault;
		}

		// 2) If it was compromised we want to verify that the public keys are
		// different.
		HostCertificateFilter f = new HostCertificateFilter();
		f.setStatus(HostCertificateStatus.COMPROMISED);
		List<HostCertificateRecord> records = findHostCertificates(f);
		for (int i = 0; i < records.size(); i++) {
			java.security.PublicKey oldKey = null;
			try {
				oldKey = KeyUtil.loadPublicKey(records.get(i).getPublicKey().getKeyAsString());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "An unexpected error occurred validating the public key.");
				throw fault;
			}
			if (oldKey.equals(key)) {
				InvalidHostCertificateRequestException fault = FaultHelper.createFaultException(InvalidHostCertificateRequestException.class,
						"The public key provided is not acceptable it was determined that this key was compromised.");
				throw fault;
			}
		}
		// 3) We need to verify the size of the public key
		int keySize = ca.getProperties().getIssuedCertificateKeySize();
		int size = ((RSAPublicKey) key).getModulus().bitLength();
		if (keySize != size) {
			InvalidHostCertificateRequestException fault = FaultHelper.createFaultException(InvalidHostCertificateRequestException.class, "The size of the public key specified is " + size
					+ " bits and is required to be " + keySize + " bits.");
			throw fault;
		}
		Connection c = null;
		long id = -1;
		try {
			c = db.getConnection();
			PreparedStatement s = c.prepareStatement("INSERT INTO " + TABLE + " SET " + SERIAL + "= ?," + HOST + "= ?," + SUBJECT + "= ?," + OWNER + "= ?," + STATUS + "= ?," + PUBLIC_KEY + "= ?,"
					+ CERTIFICATE + "= ?");
			s.setLong(1, -1);
			s.setString(2, req.getHostname());
			s.setString(3, "");
			s.setString(4, owner);
			s.setString(5, HostCertificateStatus.PENDING.value());
			s.setString(6, req.getPublicKey().getKeyAsString());
			s.setString(7, "");
			s.execute();
			id = db.getLastAutoId(c);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "An unexpected error occurred.");
			FaultHelper.addMessage(fault, e.getMessage());
			throw fault;
		} finally {
			db.releaseConnection(c);
		}
		return id;

	}

	public List<HostCertificateRecord> findHostCertificates(HostCertificateFilter f) throws DorianInternalException {
		this.buildDatabase();
		Connection c = null;
		List<HostCertificateRecord> hosts = new ArrayList<HostCertificateRecord>();

		try {
			c = db.getConnection();
			PreparedStatementBuilder select = new PreparedStatementBuilder(TABLE);
			select.addSelectField(ID);
			select.addSelectField(HOST);
			select.addSelectField(OWNER);
			select.addSelectField(SUBJECT);
			select.addSelectField(SERIAL);
			select.addSelectField(STATUS);
			select.addSelectField(PUBLIC_KEY);
			select.addSelectField(CERTIFICATE);

			if (f != null) {

				if (f.getId() != null) {
					select.addWhereField(ID, "=", f.getId());
				}

				if (f.getSerialNumber() != null) {
					select.addWhereField(SERIAL, "=", f.getSerialNumber());
				}

				if (f.getHost() != null) {
					select.addWhereField(HOST, "LIKE", "%" + f.getHost() + "%");
				}

				if (f.getSubject() != null) {
					select.addWhereField(SUBJECT, "LIKE", "%" + f.getSubject() + "%");
				}
				if (f.getOwner() != null) {
					select.addWhereField(OWNER, "LIKE", "%" + f.getOwner() + "%");
				}

				if (f.getStatus() != null) {
					select.addWhereField(STATUS, "=", f.getStatus().value());
				}

				if (f.isIsExpired() != null) {
					Calendar cal = new GregorianCalendar();
					long time = cal.getTimeInMillis();
					if (f.isIsExpired().booleanValue()) {
						select.addClause("(" + EXPIRATION + ">0 AND " + EXPIRATION + "<" + Long.valueOf(time) + ")");
					} else {
						select.addClause("(" + EXPIRATION + ">0 AND " + EXPIRATION + ">" + Long.valueOf(time) + ")");
					}
				}

			}
			PreparedStatement s = select.prepareStatement(c);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				HostCertificateRecord record = new HostCertificateRecord();
				record.setId(rs.getInt(ID));
				record.setHost(rs.getString(HOST));
				record.setOwner(rs.getString(OWNER));
				record.setSubject(rs.getString(SUBJECT));
				record.setSerialNumber(rs.getLong(SERIAL));
				record.setStatus(HostCertificateStatus.fromValue(rs.getString(STATUS)));
				String keyStr = Utils.clean(rs.getString(PUBLIC_KEY));
				if (keyStr != null) {
					PublicKey pk = new PublicKey();
					pk.setKeyAsString(keyStr);
					record.setPublicKey(pk);
				}
				String certStr = Utils.clean(rs.getString(CERTIFICATE));
				if (certStr != null) {
					X509Certificate cert = new X509Certificate();
					cert.setCertificateAsString(certStr);
					record.setCertificate(cert);
				}
				hosts.add(record);
			}
			rs.close();
			s.close();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "An unexpected error occurred.");
			FaultHelper.addMessage(fault, e.getMessage());
			throw fault;
		} finally {
			db.releaseConnection(c);
		}
		return hosts;
	}

	public List<HostRecord> getHostRecords(HostSearchCriteria f) throws DorianInternalException {
		this.buildDatabase();
		Connection c = null;
		List<HostRecord> hosts = new ArrayList<HostRecord>();

		try {
			c = db.getConnection();
			PreparedStatementBuilder select = new PreparedStatementBuilder(TABLE);
			select.addSelectField(HOST);
			select.addSelectField(OWNER);
			select.addSelectField(SUBJECT);

			if (f != null) {
				select.addWhereField(STATUS, "=", HostCertificateStatus.ACTIVE.value());

				if (f.getHostname() != null) {
					select.addWhereField(HOST, "LIKE", "%" + f.getHostname() + "%");
				}
				if (f.getIdentity() != null) {
					String subject = CommonUtils.identityToSubject(f.getIdentity());
					select.addWhereField(SUBJECT, "LIKE", "%" + subject + "%");
				}

				if (f.getHostCertificateSubject() != null) {
					select.addWhereField(SUBJECT, "LIKE", "%" + f.getHostCertificateSubject() + "%");
				}

				if (f.getOwner() != null) {
					select.addWhereField(OWNER, "LIKE", "%" + f.getOwner() + "%");
				}
			}
			PreparedStatement s = select.prepareStatement(c);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				HostRecord record = new HostRecord();
				record.setHostname(rs.getString(HOST));
				record.setOwner(rs.getString(OWNER));
				record.setIdentity(CommonUtils.subjectToIdentity(rs.getString(SUBJECT)));
				record.setHostCertificateSubject(rs.getString(SUBJECT));
				hosts.add(record);
			}
			rs.close();
			s.close();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "An unexpected error occurred.");
			FaultHelper.addMessage(fault, e.getMessage());
			throw fault;
		} finally {
			db.releaseConnection(c);
		}
		return hosts;
	}

	public List<HostCertificateRecord> getHostCertificateRecords(String owner) throws DorianInternalException {
		this.buildDatabase();
		List<HostCertificateRecord> records = new ArrayList<HostCertificateRecord>();
		Connection c = null;
		try {
			c = db.getConnection();
			PreparedStatement s = c.prepareStatement("select " + ID + " from  " + TABLE + " WHERE " + OWNER + " = ?");
			s.setString(1, owner);
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				records.add(getHostCertificateRecord(rs.getInt(ID)));
			}
			rs.close();
			s.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "An unexpected error occurred.");
			FaultHelper.addMessage(fault, e.getMessage());
			throw fault;
		} finally {
			db.releaseConnection(c);
		}
		return records;
	}

	public List<Long> getHostCertificateRecordsSerialNumbers(String owner) throws DorianInternalException {
		this.buildDatabase();
		List<Long> sn = new ArrayList<Long>();
		Connection c = null;
		try {
			c = db.getConnection();
			PreparedStatement s = c.prepareStatement("select " + SERIAL + " from  " + TABLE + " WHERE " + OWNER + " = ? AND " + STATUS + " <> ? AND " + STATUS + " <> ? ");
			s.setString(1, owner);
			s.setString(2, HostCertificateStatus.PENDING.value());
			s.setString(3, HostCertificateStatus.REJECTED.value());
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				sn.add(new Long(rs.getLong(SERIAL)));
			}
			rs.close();
			s.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "An unexpected error occurred.");
			FaultHelper.addMessage(fault, e.getMessage());
			throw fault;
		} finally {
			db.releaseConnection(c);
		}
		return sn;
	}

	public List<Long> getDisabledHostCertificatesSerialNumbers() throws DorianInternalException {
		this.buildDatabase();
		List<Long> entries = new ArrayList<Long>();
		Connection c = null;
		try {
			c = db.getConnection();
			PreparedStatement s = c.prepareStatement("select " + SERIAL + " from  " + TABLE + " WHERE " + STATUS + " = ? OR " + STATUS + " = ? ");
			s.setString(1, HostCertificateStatus.SUSPENDED.value());
			s.setString(2, HostCertificateStatus.COMPROMISED.value());
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				long sn = rs.getLong(SERIAL);
				entries.add(new Long(sn));
			}
			rs.close();
			s.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "An unexpected error occurred.");
			FaultHelper.addMessage(fault, e.getMessage());
			throw fault;
		} finally {
			db.releaseConnection(c);
		}
		return entries;
	}

	public HostCertificateRecord getHostCertificateRecord(long id) throws DorianInternalException, InvalidHostCertificateException {
		buildDatabase();
		Connection c = null;
		HostCertificateRecord record = null;
		try {
			c = db.getConnection();
			PreparedStatement s = c.prepareStatement("select " + ID + "," + HOST + "," + SUBJECT + "," + OWNER + "," + SERIAL + "," + STATUS + "," + PUBLIC_KEY + "," + CERTIFICATE + " from " + TABLE
					+ " WHERE " + ID + "= ?");
			s.setLong(1, id);

			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				record = new HostCertificateRecord();
				record.setId(rs.getInt(ID));
				record.setHost(rs.getString(HOST));
				record.setOwner(rs.getString(OWNER));
				record.setSerialNumber(rs.getLong(SERIAL));
				record.setStatus(HostCertificateStatus.fromValue(rs.getString(STATUS)));
				record.setSubject(rs.getString(SUBJECT));
				String keyStr = Utils.clean(rs.getString(PUBLIC_KEY));
				if (keyStr != null) {
					PublicKey pk = new PublicKey();
					pk.setKeyAsString(keyStr);
					record.setPublicKey(pk);
				}
				String certStr = Utils.clean(rs.getString(CERTIFICATE));
				if (certStr != null) {
					X509Certificate cert = new X509Certificate();
					cert.setCertificateAsString(certStr);
					record.setCertificate(cert);
				}
			}
			rs.close();
			s.close();
		} catch (Exception e) {
			DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "An unexpected error occurred.");
			FaultHelper.addMessage(fault, e.getMessage());
			throw fault;
		} finally {
			db.releaseConnection(c);
		}

		if (record == null) {
			InvalidHostCertificateException fault = FaultHelper.createFaultException(InvalidHostCertificateException.class, "No such host certificate exists.");
			throw fault;
		}

		return record;
	}

	public HostCertificateRecord getHostCertificateRecordBySerialNumber(long sn) throws DorianInternalException, InvalidHostCertificateException {
		buildDatabase();
		Connection c = null;
		HostCertificateRecord record = null;
		try {
			c = db.getConnection();
			PreparedStatement s = c.prepareStatement("select " + ID + "," + HOST + "," + SUBJECT + "," + OWNER + "," + SERIAL + "," + STATUS + "," + PUBLIC_KEY + "," + CERTIFICATE + " from " + TABLE
					+ " WHERE " + SERIAL + "= ?");
			s.setLong(1, sn);

			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				record = new HostCertificateRecord();
				record.setId(rs.getInt(ID));
				record.setHost(rs.getString(HOST));
				record.setOwner(rs.getString(OWNER));
				record.setSerialNumber(rs.getLong(SERIAL));
				record.setStatus(HostCertificateStatus.fromValue(rs.getString(STATUS)));
				record.setSubject(rs.getString(SUBJECT));
				String keyStr = Utils.clean(rs.getString(PUBLIC_KEY));
				if (keyStr != null) {
					PublicKey pk = new PublicKey();
					pk.setKeyAsString(keyStr);
					record.setPublicKey(pk);
				}
				String certStr = Utils.clean(rs.getString(CERTIFICATE));
				if (certStr != null) {
					X509Certificate cert = new X509Certificate();
					cert.setCertificateAsString(certStr);
					record.setCertificate(cert);
				}
			}
			rs.close();
			s.close();
		} catch (Exception e) {
			DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "An unexpected error occurred.");
			FaultHelper.addMessage(fault, e.getMessage());
			throw fault;
		} finally {
			db.releaseConnection(c);
		}

		if (record == null) {
			InvalidHostCertificateException fault = FaultHelper.createFaultException(InvalidHostCertificateException.class, "No such host certificate exists.");
			throw fault;
		}

		return record;
	}

	public synchronized void updateHostCertificateRecord(HostCertificateUpdate update) throws DorianInternalException, InvalidHostCertificateException {

		if (!determineIfRecordExistById(update.getId())) {
			InvalidHostCertificateException fault = FaultHelper.createFaultException(InvalidHostCertificateException.class,
					"Could not update host certificate record, no such host certificate exists.");
			throw fault;
		}
		HostCertificateRecord record = getHostCertificateRecord(update.getId());
		// First see if the certificate has been compromised, if it has NO
		// updates are allowed.
		if (record.getStatus().equals(HostCertificateStatus.COMPROMISED)) {
			InvalidHostCertificateException fault = FaultHelper.createFaultException(InvalidHostCertificateException.class,
					"Could not update host certificate record, the host certificate has been compromised.");
			throw fault;
		}

		if (record.getStatus().equals(HostCertificateStatus.REJECTED)) {
			InvalidHostCertificateException fault = FaultHelper.createFaultException(InvalidHostCertificateException.class,
					"Could not update host certificate record, the host certificate request was rejected.");
			throw fault;
		}

		boolean updateStatus = false;
		if ((update.getStatus() != null) && (!update.getStatus().equals(record.getStatus()))) {
			if (update.getStatus().equals(HostCertificateStatus.PENDING)) {
				InvalidHostCertificateException fault = FaultHelper.createFaultException(InvalidHostCertificateException.class,
						"Could not update host certificate record, the status of a host certificate cannot be change to " + HostCertificateStatus.PENDING + " once it has been approved.");
				throw fault;
			} else if (record.getStatus().equals(HostCertificateStatus.PENDING) && (!update.getStatus().equals(HostCertificateStatus.REJECTED))) {
				InvalidHostCertificateException fault = FaultHelper.createFaultException(InvalidHostCertificateException.class,
						"Could not update the status of a host certificate record until it has been approved or rejected.");
				throw fault;
			}
			updateStatus = true;
		}
		boolean updateOwner = false;
		if ((update.getOwner() != null) && (!update.getOwner().equals(record.getOwner()))) {
			updateOwner = true;
		}

		if (updateOwner || updateStatus) {
			Connection c = null;
			try {
				c = db.getConnection();
				StringBuffer sb = new StringBuffer();
				sb.append("update " + TABLE + " SET ");

				if (updateStatus) {
					sb.append(STATUS + "= ?");
				}
				if (updateOwner) {
					if (updateStatus) {
						sb.append(",");
					}
					sb.append(OWNER + "= ? ");
				}

				sb.append(" WHERE " + ID + "= ?");
				PreparedStatement s = c.prepareStatement(sb.toString());
				int count = 1;
				if (updateStatus) {
					s.setString(count, update.getStatus().value());
					count++;
				}
				if (updateOwner) {
					s.setString(count, update.getOwner());
					count++;
				}
				s.setLong(count, update.getId());
				s.execute();
				if (updateStatus) {
					publishCRLIfNeeded(record.getStatus(), update.getStatus());
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "An unexpected error occurred.");
				FaultHelper.addMessage(fault, e.getMessage());
				throw fault;
			} finally {
				db.releaseConnection(c);
			}
		}

	}

	private boolean determineIfRecordExistById(long id) throws DorianInternalException {
		buildDatabase();
		Connection c = null;
		boolean exists = false;
		try {
			c = db.getConnection();
			PreparedStatement s = c.prepareStatement("select count(*) from " + TABLE + " WHERE " + ID + "= ?");
			s.setLong(1, id);
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					exists = true;
				}
			}
			rs.close();
			s.close();
		} catch (Exception e) {
			DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "Unexpected Database Error");
			FaultHelper.addMessage(fault, e.getMessage());
			throw fault;
		} finally {
			db.releaseConnection(c);
		}
		return exists;
	}

	private boolean certificateCannotBeRequested(String host) throws DorianInternalException {
		buildDatabase();
		Connection c = null;
		boolean exists = false;
		try {
			c = db.getConnection();
			PreparedStatement s = c.prepareStatement("select count(*) from " + TABLE + " WHERE " + HOST + "= ? AND " + STATUS + " <> ? AND " + STATUS + " <> ? ");
			s.setString(1, host);
			s.setString(2, HostCertificateStatus.REJECTED.value());
			s.setString(3, HostCertificateStatus.COMPROMISED.value());
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				if (rs.getInt(1) > 0) {
					exists = true;
				}
			}
			rs.close();
			s.close();
		} catch (Exception e) {
			DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "An unexpected error occurred.");
			FaultHelper.addMessage(fault, e.getMessage());
			throw fault;
		} finally {
			db.releaseConnection(c);
		}
		return exists;
	}

	public void buildDatabase() throws DorianInternalException {
		if (!dbBuilt) {
			try {
				if (!this.db.tableExists(TABLE)) {

					String certificates = "CREATE TABLE " + TABLE + " (" + ID + " INT NOT NULL AUTO_INCREMENT PRIMARY KEY," + SERIAL + " BIGINT," + HOST + " VARCHAR(255) NOT NULL," + SUBJECT
							+ " TEXT," + STATUS + " VARCHAR(15) NOT NULL," + OWNER + " TEXT NOT NULL," + EXPIRATION + " BIGINT," + CERTIFICATE + " TEXT," + PUBLIC_KEY + " TEXT NOT NULL, "
							+ "INDEX document_index (ID));";
					db.update(certificates);

				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "An unexpected database error occurred.");
				FaultHelper.addMessage(fault, e.getMessage());
				throw fault;
			}
			this.dbBuilt = true;
		}
	}

	public void clearDatabase() throws DorianInternalException {
		buildDatabase();
		try {
			db.update("delete from " + TABLE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			DorianInternalException fault = FaultHelper.createFaultException(DorianInternalException.class, "An unexpected database error occurred.");
			FaultHelper.addMessage(fault, e.getMessage());
			throw fault;
		}
	}

}
