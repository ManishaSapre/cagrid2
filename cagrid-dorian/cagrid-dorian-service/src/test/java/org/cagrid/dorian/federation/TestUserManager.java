package org.cagrid.dorian.federation;

import java.security.cert.X509Certificate;
import java.util.List;

import junit.framework.TestCase;

import org.cagrid.dorian.common.CommonUtils;
import org.cagrid.dorian.common.SAMLConstants;
import org.cagrid.dorian.model.exceptions.InvalidUserException;
import org.cagrid.dorian.model.federation.GridUser;
import org.cagrid.dorian.model.federation.GridUserFilter;
import org.cagrid.dorian.model.federation.GridUserRecord;
import org.cagrid.dorian.model.federation.GridUserSearchCriteria;
import org.cagrid.dorian.model.federation.GridUserStatus;
import org.cagrid.dorian.model.federation.SAMLAttributeDescriptor;
import org.cagrid.dorian.model.federation.SAMLAuthenticationMethod;
import org.cagrid.dorian.model.federation.TrustedIdP;
import org.cagrid.dorian.model.federation.TrustedIdPStatus;
import org.cagrid.dorian.service.PropertyManager;
import org.cagrid.dorian.service.ca.CertificateAuthority;
import org.cagrid.dorian.service.federation.AutoApprovalPolicy;
import org.cagrid.dorian.service.federation.FederationDefaults;
import org.cagrid.dorian.service.federation.IdentityAssignmentPolicy;
import org.cagrid.dorian.service.federation.IdentityFederationProperties;
import org.cagrid.dorian.service.federation.Publisher;
import org.cagrid.dorian.service.federation.TrustedIdPManager;
import org.cagrid.dorian.service.federation.UserManager;
import org.cagrid.gaards.dorian.test.CA;
import org.cagrid.gaards.dorian.test.Credential;
import org.cagrid.gaards.dorian.test.Utils;
import org.cagrid.gaards.pki.CertUtil;
import org.cagrid.tools.database.Database;

/**
 * @author <A href="mailto:langella@bmi.osu.edu">Stephen Langella </A>
 * @author <A href="mailto:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A href="mailto:hastings@bmi.osu.edu">Shannon Hastings </A>
 * @version $Id: ArgumentManagerTable.java,v 1.2 2004/10/15 16:35:16 langella
 *          Exp $
 */
public class TestUserManager extends TestCase implements Publisher {
	private static final int INIT_USER = 1;
	private static final String DEFAULT_IDP_NAME = "Dorian IdP";

	private Database db;

	private CertificateAuthority ca;
	private CA memoryCA;

	private PropertyManager props;

	public void testSingleUserIdPNameBasedIdentitfiers() {
		try {
			checkSingleUser(getUserManagerNameBasedIdentities());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}

	public void testSingleUserIdPIdBasedIdentitfiers() {
		try {
			checkSingleUser(getUserManagerIdBasedIdentities());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}

	public void testMultipleUsersIdPNameBasedIdentitfiers() {
		try {
			checkMultipleUsers(getUserManagerNameBasedIdentities());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}

	public void testMultipleUsersIdPIdBasedIdentitfiers() {
		try {
			checkMultipleUsers(getUserManagerIdBasedIdentities());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}

	public void checkSingleUser(UserManager um) {
		try {
			// Test adding user
			GridUser user = new GridUser();
			user.setIdPId(INIT_USER + 1);
			user.setUID("user");
			user.setFirstName("John");
			user.setLastName("Doe");
			user.setEmail("user@user.com");
			user = um.addUser(getIdp(user), user);
			String expectedGridIdentity = CommonUtils.subjectToIdentity(UserManager.getUserSubject(um.getIdentityAssignmentPolicy(), ca.getCACertificate().getSubjectDN().getName(), getIdp(user),
					user.getUID()));
			assertNotNull(user.getGridId());
			assertNotNull(user.getUserStatus());
			assertEquals(GridUserStatus.PENDING, user.getUserStatus());
			assertEquals(expectedGridIdentity, user.getGridId());
			assertEquals(user, um.getUser(user.getIdPId(), user.getUID()));
			assertEquals(user, um.getUser(user.getGridId()));

			GridUserRecord userRecord = um.getUserRecord(user.getGridId());
			assertEquals(user.getGridId(), userRecord.getIdentity());
			assertEquals(user.getFirstName(), userRecord.getFirstName());
			assertEquals(user.getLastName(), userRecord.getLastName());
			assertEquals(user.getEmail(), userRecord.getEmail());

			// Test Querying for users
			GridUserFilter f1 = new GridUserFilter();
			GridUser[] l1 = um.getUsers(f1);
			assertEquals(1 + INIT_USER, l1.length);

			GridUser u3 = um.getUser(user.getGridId());
			u3.setUserStatus(GridUserStatus.ACTIVE);
			um.updateUser(u3);
			assertEquals(u3, um.getUser(u3.getGridId()));
			user = u3;

			List<GridUserRecord> s1 = um.getUsers(toSearchCriteria(f1));
			assertEquals(1 + INIT_USER, s1.size());

			// Test querying by uid
			GridUserFilter f2 = new GridUserFilter();
			f2.setUID("nobody");
			GridUser[] l2 = um.getUsers(f2);
			assertEquals(0, l2.length);
			f2.setUID("use");
			l2 = um.getUsers(f2);
			assertEquals(1, l2.length);
			assertEquals(user, l2[0]);

			// Test querying by IdP_Id
			GridUserFilter f3 = new GridUserFilter();
			f3.setIdPId(Long.MAX_VALUE);
			GridUser[] l3 = um.getUsers(f3);
			assertEquals(0, l3.length);
			f3.setIdPId(user.getIdPId());
			l3 = um.getUsers(f3);
			assertEquals(1, l3.length);
			assertEquals(user, l3[0]);

			// Test querying by GID
			GridUserFilter f4 = new GridUserFilter();
			f4.setGridId("nobody");
			GridUser[] l4 = um.getUsers(f4);
			assertEquals(0, l4.length);
			List<GridUserRecord> s4 = um.getUsers(toSearchCriteria(f4));
			assertEquals(0, s4.size());
			f4.setGridId(user.getGridId());
			l4 = um.getUsers(f4);
			assertEquals(1, l4.length);
			assertEquals(user, l4[0]);
			s4 = um.getUsers(toSearchCriteria(f4));
			assertEquals(1, s4.size());
			assertEquals(userRecord, s4.get(0));

			// Test querying by Email
			GridUserFilter f5 = new GridUserFilter();
			f5.setEmail("nobody");
			GridUser[] l5 = um.getUsers(f5);
			assertEquals(0, l5.length);
			List<GridUserRecord> s5 = um.getUsers(toSearchCriteria(f5));
			assertEquals(0, s5.size());
			f5.setEmail(user.getEmail());
			l5 = um.getUsers(f5);
			assertEquals(1, l5.length);
			assertEquals(user, l5[0]);
			s5 = um.getUsers(toSearchCriteria(f5));
			assertEquals(1, s5.size());
			assertEquals(userRecord, s5.get(0));

			// Test querying by Status
			GridUserFilter f7 = new GridUserFilter();
			f7.setUserStatus(GridUserStatus.SUSPENDED);
			GridUser[] l7 = um.getUsers(f7);
			assertEquals(0, l7.length);
			f7.setUserStatus(user.getUserStatus());
			l7 = um.getUsers(f7);
			assertEquals(1 + INIT_USER, l7.length);

			// Test querying by First Name
			GridUserFilter f8 = new GridUserFilter();
			f8.setFirstName("nobody");
			GridUser[] l8 = um.getUsers(f8);
			assertEquals(0, l8.length);
			List<GridUserRecord> s8 = um.getUsers(toSearchCriteria(f8));
			assertEquals(0, s8.size());
			f8.setFirstName(user.getFirstName());
			l8 = um.getUsers(f8);
			assertEquals(1, l8.length);
			assertEquals(user, l8[0]);
			s8 = um.getUsers(toSearchCriteria(f8));
			assertEquals(1, s8.size());
			assertEquals(userRecord, s8.get(0));

			// Test querying by Last Name
			GridUserFilter f9 = new GridUserFilter();
			f9.setLastName("nobody");
			GridUser[] l9 = um.getUsers(f9);
			assertEquals(0, l9.length);
			List<GridUserRecord> s9 = um.getUsers(toSearchCriteria(f9));
			assertEquals(0, s9.size());
			f9.setLastName(user.getLastName());
			l9 = um.getUsers(f9);
			assertEquals(1, l9.length);
			assertEquals(user, l9[0]);
			s9 = um.getUsers(toSearchCriteria(f9));
			assertEquals(1, s9.size());
			assertEquals(userRecord, s9.get(0));

			// Test All
			GridUserFilter all = new GridUserFilter();
			all.setIdPId(user.getIdPId());
			all.setUID(user.getUID());
			all.setGridId(user.getGridId());
			all.setFirstName(user.getFirstName());
			all.setLastName(user.getLastName());
			all.setEmail(user.getEmail());
			all.setUserStatus(user.getUserStatus());
			GridUser[] allList = um.getUsers(all);
			assertEquals(1, allList.length);
			assertEquals(user, allList[0]);
			List<GridUserRecord> sAll = um.getUsers(toSearchCriteria(all));
			assertEquals(1, sAll.size());
			assertEquals(userRecord, sAll.get(0));

			// Test Update
			GridUser u1 = um.getUser(user.getGridId());
			u1.setFirstName("newfirst");
			u1.setLastName("newlast");
			u1.setEmail("newemail@example.com");
			um.updateUser(u1);
			assertEquals(u1, um.getUser(u1.getGridId()));

			GridUser u4 = um.getUser(user.getGridId());
			u4.setUserStatus(GridUserStatus.SUSPENDED);
			u4.setEmail("newemail2@example.com");
			um.updateUser(u4);
			assertEquals(u4, um.getUser(u4.getGridId()));

			GridUser u5 = um.getUser(user.getGridId());
			u5.setGridId("changed grid id");
			try {
				um.updateUser(u5);
				fail("Should not be able to change a user's grid identity.");
			} catch (InvalidUserException e) {
				if (!gov.nih.nci.cagrid.common.Utils.getExceptionMessage(e).equals(UserManager.CANNOT_UPDATE_GRID_IDENTITY_ERROR)) {
					fail("Should not be able to change a user's grid identity.");
				}
			}
			;
			um.removeUser(u5);
			assertEquals(INIT_USER, um.getUsers(new GridUserFilter()).length);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			try {
				um.clearDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void checkMultipleUsers(UserManager um) {
		try {

			String prefix = "user";
			String firstNamePrefix = "John";
			String lastNamePrefix = "Doe";

			int userCount = 9;

			for (int i = 0; i < userCount; i++) {
				// Test adding user
				long idpId = (i % 3) + 1 + INIT_USER;
				long idpCount = (i / 3) + 1;

				String uname = prefix + i;
				String firstName = firstNamePrefix + i;
				String lastName = lastNamePrefix + i;

				GridUser user = new GridUser();

				user.setIdPId(idpId);
				user.setUID(uname);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmail(uname + "@user.com");
				user = um.addUser(getIdp(user), user);

				String expectedGridIdentity = CommonUtils.subjectToIdentity(UserManager.getUserSubject(um.getIdentityAssignmentPolicy(), ca.getCACertificate().getSubjectDN().getName(), getIdp(user),
						user.getUID()));
				assertNotNull(user.getGridId());
				assertNotNull(user.getUserStatus());
				assertEquals(expectedGridIdentity, user.getGridId());
				assertEquals(user, um.getUser(user.getIdPId(), user.getUID()));
				assertEquals(user, um.getUser(user.getGridId()));

				GridUserRecord userRecord = um.getUserRecord(user.getGridId());
				assertEquals(user.getGridId(), userRecord.getIdentity());
				assertEquals(user.getFirstName(), userRecord.getFirstName());
				assertEquals(user.getLastName(), userRecord.getLastName());
				assertEquals(user.getEmail(), userRecord.getEmail());

				// Test Querying for users
				GridUserFilter f1 = new GridUserFilter();
				GridUser[] l1 = um.getUsers(f1);
				assertEquals((i + 1 + INIT_USER), l1.length);

				GridUser u3 = um.getUser(user.getGridId());
				u3.setUserStatus(GridUserStatus.ACTIVE);
				um.updateUser(u3);
				assertEquals(u3, um.getUser(u3.getGridId()));
				user = u3;

				List<GridUserRecord> s1 = um.getUsers(toSearchCriteria(f1));
				assertEquals((i + 1 + INIT_USER), s1.size());

				// Test querying by uid
				GridUserFilter f2 = new GridUserFilter();
				f2.setUID("nobody");
				GridUser[] l2 = um.getUsers(f2);
				assertEquals(0, l2.length);
				f2.setUID("use");
				l2 = um.getUsers(f2);
				assertEquals((i + 1), l2.length);
				f2.setUID(uname);
				l2 = um.getUsers(f2);
				assertEquals(1, l2.length);
				assertEquals(user, l2[0]);

				// Test querying by IdP_Id
				GridUserFilter f3 = new GridUserFilter();
				f3.setIdPId(Long.MAX_VALUE);
				GridUser[] l3 = um.getUsers(f3);
				assertEquals(0, l3.length);
				f3.setIdPId(user.getIdPId());
				l3 = um.getUsers(f3);
				assertEquals(idpCount, l3.length);

				// Test querying by GID
				GridUserFilter f4 = new GridUserFilter();
				f4.setGridId("nobody");
				GridUser[] l4 = um.getUsers(f4);
				assertEquals(0, l4.length);
				List<GridUserRecord> s4 = um.getUsers(toSearchCriteria(f4));
				assertEquals(0, s4.size());

				String temp = user.getGridId();
				int index = temp.lastIndexOf("/");
				temp = temp.substring(0, index);
				f4.setGridId(temp);
				l4 = um.getUsers(f4);
				assertEquals(idpCount, l4.length);
				s4 = um.getUsers(toSearchCriteria(f4));
				assertEquals(idpCount, s4.size());
				f4.setGridId(user.getGridId());
				l4 = um.getUsers(f4);
				assertEquals(1, l4.length);
				assertEquals(user, l4[0]);

				s4 = um.getUsers(toSearchCriteria(f4));
				assertEquals(1, s4.size());
				assertEquals(userRecord, s4.get(0));

				// Test querying by Email
				GridUserFilter f5 = new GridUserFilter();
				f5.setEmail("nobody");
				GridUser[] l5 = um.getUsers(f5);
				assertEquals(0, l5.length);
				List<GridUserRecord> s5 = um.getUsers(toSearchCriteria(f5));
				assertEquals(0, s5.size());
				f5.setEmail(user.getEmail());
				l5 = um.getUsers(f5);
				assertEquals(1, l5.length);
				assertEquals(user, l5[0]);
				s5 = um.getUsers(toSearchCriteria(f5));
				assertEquals(1, s5.size());
				assertEquals(userRecord, s5.get(0));

				// Test querying by Status
				GridUserFilter f7 = new GridUserFilter();
				f7.setUserStatus(GridUserStatus.SUSPENDED);
				GridUser[] l7 = um.getUsers(f7);
				assertEquals(0, l7.length);
				f7.setUserStatus(user.getUserStatus());
				l7 = um.getUsers(f7);
				assertEquals((i + 1 + INIT_USER), l7.length);

				// Test querying by First Name
				GridUserFilter f8 = new GridUserFilter();
				f8.setFirstName("nobody");
				GridUser[] l8 = um.getUsers(f8);
				assertEquals(0, l8.length);
				List<GridUserRecord> s8 = um.getUsers(toSearchCriteria(f8));
				assertEquals(0, s8.size());
				f8.setFirstName(firstNamePrefix);
				l8 = um.getUsers(f8);
				assertEquals((i + 1), l8.length);
				s8 = um.getUsers(toSearchCriteria(f8));
				assertEquals((i + 1), s8.size());
				f8.setFirstName(user.getFirstName());
				l8 = um.getUsers(f8);
				assertEquals(1, l8.length);
				assertEquals(user, l8[0]);

				s8 = um.getUsers(toSearchCriteria(f8));
				assertEquals(1, s8.size());
				assertEquals(userRecord, s8.get(0));

				// Test querying by Last Name
				GridUserFilter f9 = new GridUserFilter();
				f9.setLastName("nobody");
				GridUser[] l9 = um.getUsers(f9);
				assertEquals(0, l9.length);
				List<GridUserRecord> s9 = um.getUsers(toSearchCriteria(f9));
				assertEquals(0, s9.size());
				f9.setLastName(lastNamePrefix);
				l9 = um.getUsers(f9);
				assertEquals((i + 1), l9.length);
				s9 = um.getUsers(toSearchCriteria(f9));
				assertEquals((i + 1), s9.size());
				f9.setLastName(user.getLastName());
				l9 = um.getUsers(f9);
				assertEquals(1, l9.length);
				assertEquals(user, l9[0]);
				s9 = um.getUsers(toSearchCriteria(f9));
				assertEquals(1, s9.size());
				assertEquals(userRecord, s9.get(0));

				// Test All
				GridUserFilter all = new GridUserFilter();
				all.setIdPId(user.getIdPId());
				all.setUID(user.getUID());
				all.setGridId(user.getGridId());
				all.setFirstName(user.getFirstName());
				all.setLastName(user.getLastName());
				all.setEmail(user.getEmail());
				all.setUserStatus(user.getUserStatus());
				GridUser[] lall = um.getUsers(all);
				assertEquals(1, lall.length);
				assertEquals(user, lall[0]);
				List<GridUserRecord> sall = um.getUsers(toSearchCriteria(all));
				assertEquals(1, sall.size());
				assertEquals(userRecord, sall.get(0));

				// Test Update
				GridUser u1 = um.getUser(user.getGridId());
				u1.setEmail("newemail@example.com");
				um.updateUser(u1);
				assertEquals(u1, um.getUser(u1.getGridId()));

				u3.setUserStatus(GridUserStatus.SUSPENDED);
				um.updateUser(u3);
				assertEquals(u3, um.getUser(u3.getGridId()));

				GridUser u4 = um.getUser(user.getGridId());
				u4.setUserStatus(GridUserStatus.ACTIVE);
				u4.setEmail("newemail2@example.com");
				um.updateUser(u4);
				assertEquals(u4, um.getUser(u4.getGridId()));

				GridUser u5 = um.getUser(user.getGridId());
				u5.setGridId("changed grid id" + i);
				try {
					um.updateUser(u5);
					fail("Should not be able to change a user's grid identity.");
				} catch (InvalidUserException e) {
					if (!gov.nih.nci.cagrid.common.Utils.getExceptionMessage(e).equals(UserManager.CANNOT_UPDATE_GRID_IDENTITY_ERROR)) {
						fail("Should not be able to change a user's grid identity.");
					}
				}
			}

			// um.removeUser(u5);
			GridUser[] list = um.getUsers(new GridUserFilter());
			assertEquals(userCount + INIT_USER, list.length);
			int count = userCount;
			for (int i = 0; i < list.length; i++) {
				count = count - 1;
				um.removeUser(list[i]);
				assertEquals(count + INIT_USER, um.getUsers(new GridUserFilter()).length);
			}
			assertEquals(0, um.getUsers(new GridUserFilter()).length);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			try {
				um.clearDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void testUpdateRejectedUserInvalidStatus() {
		UserManager um = null;
		try {
			um = getUserManagerNameBasedIdentities();
			GridUser user = new GridUser();
			user.setIdPId(INIT_USER + 1);
			user.setUID("user");
			user.setFirstName("John");
			user.setLastName("Doe");
			user.setEmail("user@user.com");
			user = um.addUser(getIdp(user), user);
			String expectedGridIdentity = CommonUtils.subjectToIdentity(UserManager.getUserSubject(um.getIdentityAssignmentPolicy(), ca.getCACertificate().getSubjectDN().getName(), getIdp(user),
					user.getUID()));
			assertNotNull(user.getGridId());
			assertNotNull(user.getUserStatus());
			assertEquals(GridUserStatus.PENDING, user.getUserStatus());
			assertEquals(expectedGridIdentity, user.getGridId());
			assertEquals(user, um.getUser(user.getIdPId(), user.getUID()));
			assertEquals(user, um.getUser(user.getGridId()));
			user.setUserStatus(GridUserStatus.REJECTED);
			um.updateUser(user);
			GridUser u1 = um.getUser(user.getGridId());
			assertEquals(user.getUserStatus(), u1.getUserStatus());

			user.setUserStatus(GridUserStatus.ACTIVE);

			try {
				um.updateUser(user);
				fail("Should not be able to change the status of a user to an invalid status.");
			} catch (InvalidUserException e) {

			}

			user.setUserStatus(GridUserStatus.SUSPENDED);
			try {
				um.updateUser(user);
				fail("Should not be able to change the status of a user to an invalid status.");
			} catch (InvalidUserException e) {

			}

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			if (um != null) {
				try {
					um.clearDatabase();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void testUpdateActiveUserInvalidStatus() {
		UserManager um = null;
		try {
			um = getUserManagerNameBasedIdentities();
			GridUser user = new GridUser();
			user.setIdPId(INIT_USER + 1);
			user.setUID("user");
			user.setFirstName("John");
			user.setLastName("Doe");
			user.setEmail("user@user.com");
			user = um.addUser(getIdp(user), user);
			String expectedGridIdentity = CommonUtils.subjectToIdentity(UserManager.getUserSubject(um.getIdentityAssignmentPolicy(), ca.getCACertificate().getSubjectDN().getName(), getIdp(user),
					user.getUID()));
			assertNotNull(user.getGridId());
			assertNotNull(user.getUserStatus());
			assertEquals(GridUserStatus.PENDING, user.getUserStatus());
			assertEquals(expectedGridIdentity, user.getGridId());
			assertEquals(user, um.getUser(user.getIdPId(), user.getUID()));
			assertEquals(user, um.getUser(user.getGridId()));
			user.setUserStatus(GridUserStatus.ACTIVE);
			um.updateUser(user);
			GridUser u1 = um.getUser(user.getGridId());
			assertEquals(user.getUserStatus(), u1.getUserStatus());

			user.setUserStatus(GridUserStatus.REJECTED);

			try {
				um.updateUser(user);
				fail("Should not be able to change the status of a user to an invalid status.");
			} catch (InvalidUserException e) {

			}

			user.setUserStatus(GridUserStatus.PENDING);

			try {
				um.updateUser(user);
				fail("Should not be able to change the status of a user to an invalid status.");
			} catch (InvalidUserException e) {

			}

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		} finally {
			if (um != null) {
				try {
					um.clearDatabase();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	private IdentityFederationProperties getConf(String policy) throws Exception {
		IdentityFederationProperties conf = Utils.getIdentityFederationProperties();
		conf.setIdentityAssignmentPolicy(policy);
		return conf;
	}

	private FederationDefaults getDefaults() throws Exception {
		TrustedIdP idp = new TrustedIdP();
		idp.setName("Initial IdP");
		idp.setDisplayName(idp.getName());

		SAMLAttributeDescriptor uid = new SAMLAttributeDescriptor();
		uid.setNamespaceURI(SAMLConstants.UID_ATTRIBUTE_NAMESPACE);
		uid.setName(SAMLConstants.UID_ATTRIBUTE);
		idp.setUserIdAttributeDescriptor(uid);

		SAMLAttributeDescriptor firstName = new SAMLAttributeDescriptor();
		firstName.setNamespaceURI(SAMLConstants.FIRST_NAME_ATTRIBUTE_NAMESPACE);
		firstName.setName(SAMLConstants.FIRST_NAME_ATTRIBUTE);
		idp.setFirstNameAttributeDescriptor(firstName);

		SAMLAttributeDescriptor lastName = new SAMLAttributeDescriptor();
		lastName.setNamespaceURI(SAMLConstants.LAST_NAME_ATTRIBUTE_NAMESPACE);
		lastName.setName(SAMLConstants.LAST_NAME_ATTRIBUTE);
		idp.setLastNameAttributeDescriptor(lastName);

		SAMLAttributeDescriptor email = new SAMLAttributeDescriptor();
		email.setNamespaceURI(SAMLConstants.EMAIL_ATTRIBUTE_NAMESPACE);
		email.setName(SAMLConstants.EMAIL_ATTRIBUTE);
		idp.setEmailAttributeDescriptor(email);

		idp.getAuthenticationMethod().add(SAMLAuthenticationMethod.URN_OASIS_NAMES_TC_SAML_1_0_AM_PASSWORD);
		idp.setUserPolicyClass(AutoApprovalPolicy.class.getName());

		String subject = Utils.CA_SUBJECT_PREFIX + ",CN=" + idp.getName();
		Credential cred = memoryCA.createIdentityCertificate(idp.getName());
		X509Certificate cert = cred.getCertificate();
		assertNotNull(cert);
		assertEquals(cert.getSubjectDN().getName(), subject);
		idp.setIdPCertificate(CertUtil.writeCertificate(cert));
		idp.setStatus(TrustedIdPStatus.ACTIVE);
		GridUser usr = new GridUser();
		usr.setUID("inital_admin");
		usr.setFirstName("Mr");
		usr.setLastName("Admin");
		usr.setEmail("inital_admin@test.com");
		usr.setUserStatus(GridUserStatus.ACTIVE);
		return new FederationDefaults(idp, usr);
	}

	protected void setUp() throws Exception {
		super.setUp();
		try {
			db = Utils.getDB();
			assertEquals(0, db.getUsedConnectionCount());
			ca = Utils.getCA();
			memoryCA = new CA(Utils.getCASubject());
			props = new PropertyManager(db);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public UserManager getUserManagerNameBasedIdentities() throws Exception {
		IdentityFederationProperties conf = getConf(IdentityAssignmentPolicy.NAME);
		TrustedIdPManager tm = new TrustedIdPManager(conf, db);
		UserManager um = new UserManager(db, conf, props, ca, tm, this, getDefaults());
		um.clearDatabase();
		return um;
	}

	public UserManager getUserManagerIdBasedIdentities() throws Exception {
		IdentityFederationProperties conf = getConf(IdentityAssignmentPolicy.ID);
		TrustedIdPManager tm = new TrustedIdPManager(conf, db);
		UserManager um = new UserManager(db, conf, props, ca, tm, this, getDefaults());
		um.clearDatabase();
		return um;
	}

	protected void tearDown() throws Exception {
		super.setUp();
		try {
			assertEquals(0, db.getUsedConnectionCount());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	public void publishCRL() {

	}

	private GridUserSearchCriteria toSearchCriteria(GridUserFilter filter) {
		if (filter != null) {
			GridUserSearchCriteria c = new GridUserSearchCriteria();
			c.setIdentity(filter.getGridId());
			c.setFirstName(filter.getFirstName());
			c.setLastName(filter.getLastName());
			c.setEmail(filter.getEmail());
			return c;
		} else {
			return null;
		}
	}

	private TrustedIdP getIdp(GridUser usr) {
		TrustedIdP idp = new TrustedIdP();
		idp.setId(usr.getIdPId());
		idp.setName(DEFAULT_IDP_NAME + usr.getIdPId());
		return idp;
	}

}
