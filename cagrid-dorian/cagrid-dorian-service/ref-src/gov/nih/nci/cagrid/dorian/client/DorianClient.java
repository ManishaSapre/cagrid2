package gov.nih.nci.cagrid.dorian.client;

import gov.nih.nci.cagrid.authentication.bean.BasicAuthenticationCredential;
import gov.nih.nci.cagrid.authentication.bean.Credential;
import gov.nih.nci.cagrid.authentication.bean.SAMLAssertion;
import gov.nih.nci.cagrid.dorian.common.DorianI;
import gov.nih.nci.cagrid.dorian.stubs.DorianPortType;
import gov.nih.nci.cagrid.dorian.stubs.service.DorianServiceAddressingLocator;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

import java.io.InputStream;
import java.rmi.RemoteException;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.axis.utils.ClassUtils;
import org.globus.gsi.GlobusCredential;


/**
 * This class is autogenerated, DO NOT EDIT.
 * 
 * @created by Introduce Toolkit version 1.0
 * @deprecated As of release 1.3, replaced by
 *             {@link org.cagrid.gaards.dorian.client.DorianClient}
 */
@Deprecated
public class DorianClient extends ServiceSecurityClient implements DorianI {
    protected DorianPortType portType;
    private Object portTypeMutex;


    public DorianClient(String url) throws MalformedURIException, RemoteException {
        this(url, null);
    }


    public DorianClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
        super(url, proxy);
        initialize();
    }


    public DorianClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
        this(epr, null);
    }


    public DorianClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException,
        RemoteException {
        super(epr, proxy);
        initialize();
    }


    private void initialize() throws RemoteException {
        this.portTypeMutex = new Object();
        this.portType = createPortType();
    }


    private DorianPortType createPortType() throws RemoteException {

        DorianServiceAddressingLocator locator = new DorianServiceAddressingLocator();
        // attempt to load our context sensitive wsdd file
        InputStream resourceAsStream = ClassUtils.getResourceAsStream(getClass(), "client-config.wsdd");
        if (resourceAsStream != null) {
            // we found it, so tell axis to configure an engine to use it
            EngineConfiguration engineConfig = new FileProvider(resourceAsStream);
            // set the engine of the locator
            locator.setEngine(new AxisClient(engineConfig));
        }
        DorianPortType port = null;
        try {
            port = locator.getDorianPortTypePort(getEndpointReference());
        } catch (Exception e) {
            throw new RemoteException("Unable to locate portType:" + e.getMessage(), e);
        }

        return port;
    }


    public static void usage() {
        System.out.println(DorianClient.class.getName() + " -url <service url>");
    }


    public static void main(String[] args) {
        System.out.println("Running the Grid Service Client");
        try {
            if (!(args.length < 2)) {
                if (args[0].equals("-url")) {
                    DorianClient client = new DorianClient(args[1]);
                    // place client calls here if you want to use this main as a
                    // test....
                } else {
                    usage();
                    System.exit(1);
                }
            } else {
                usage();
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public gov.nih.nci.cagrid.metadata.security.ServiceSecurityMetadata getServiceSecurityMetadata()
        throws RemoteException {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "getServiceSecurityMetadata");
            gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest params = new gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest();
            gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataResponse boxedResult = portType
                .getServiceSecurityMetadata(params);
            return boxedResult.getServiceSecurityMetadata();
        }
    }


    public java.lang.String registerWithIdP(gov.nih.nci.cagrid.dorian.idp.bean.Application application)
        throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.InvalidUserPropertyFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "registerWithIdP");
            gov.nih.nci.cagrid.dorian.stubs.RegisterWithIdPRequest params = new gov.nih.nci.cagrid.dorian.stubs.RegisterWithIdPRequest();
            gov.nih.nci.cagrid.dorian.stubs.RegisterWithIdPRequestApplication applicationContainer = new gov.nih.nci.cagrid.dorian.stubs.RegisterWithIdPRequestApplication();
            applicationContainer.setApplication(application);
            params.setApplication(applicationContainer);
            gov.nih.nci.cagrid.dorian.stubs.RegisterWithIdPResponse boxedResult = portType.registerWithIdP(params);
            return boxedResult.getResponse();
        }
    }


    public gov.nih.nci.cagrid.dorian.idp.bean.IdPUser[] findIdPUsers(
        gov.nih.nci.cagrid.dorian.idp.bean.IdPUserFilter filter) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "findIdPUsers");
            gov.nih.nci.cagrid.dorian.stubs.FindIdPUsersRequest params = new gov.nih.nci.cagrid.dorian.stubs.FindIdPUsersRequest();
            gov.nih.nci.cagrid.dorian.stubs.FindIdPUsersRequestFilter filterContainer = new gov.nih.nci.cagrid.dorian.stubs.FindIdPUsersRequestFilter();
            filterContainer.setIdPUserFilter(filter);
            params.setFilter(filterContainer);
            gov.nih.nci.cagrid.dorian.stubs.FindIdPUsersResponse boxedResult = portType.findIdPUsers(params);
            return boxedResult.getIdPUser();
        }
    }


    public void updateIdPUser(gov.nih.nci.cagrid.dorian.idp.bean.IdPUser user) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.NoSuchUserFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "updateIdPUser");
            gov.nih.nci.cagrid.dorian.stubs.UpdateIdPUserRequest params = new gov.nih.nci.cagrid.dorian.stubs.UpdateIdPUserRequest();
            gov.nih.nci.cagrid.dorian.stubs.UpdateIdPUserRequestUser userContainer = new gov.nih.nci.cagrid.dorian.stubs.UpdateIdPUserRequestUser();
            userContainer.setIdPUser(user);
            params.setUser(userContainer);
            gov.nih.nci.cagrid.dorian.stubs.UpdateIdPUserResponse boxedResult = portType.updateIdPUser(params);
        }
    }


    public void removeIdPUser(java.lang.String userId) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "removeIdPUser");
            gov.nih.nci.cagrid.dorian.stubs.RemoveIdPUserRequest params = new gov.nih.nci.cagrid.dorian.stubs.RemoveIdPUserRequest();
            params.setUserId(userId);
            gov.nih.nci.cagrid.dorian.stubs.RemoveIdPUserResponse boxedResult = portType.removeIdPUser(params);
        }
    }


    public gov.nih.nci.cagrid.dorian.bean.SAMLAssertion authenticateWithIdP(
        gov.nih.nci.cagrid.dorian.idp.bean.BasicAuthCredential cred) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        Credential c = new Credential();
        BasicAuthenticationCredential auth = new BasicAuthenticationCredential();
        auth.setUserId(cred.getUserId());
        auth.setPassword(cred.getPassword());
        c.setBasicAuthenticationCredential(auth);
        SAMLAssertion saml = this.authenticate(c);
        gov.nih.nci.cagrid.dorian.bean.SAMLAssertion saml2 = new gov.nih.nci.cagrid.dorian.bean.SAMLAssertion();
        saml2.setXml(saml.getXml());
        return saml2;
    }


    public gov.nih.nci.cagrid.dorian.bean.X509Certificate[] createProxy(
        gov.nih.nci.cagrid.dorian.bean.SAMLAssertion saml, gov.nih.nci.cagrid.dorian.ifs.bean.PublicKey publicKey,
        gov.nih.nci.cagrid.dorian.ifs.bean.ProxyLifetime lifetime,
        gov.nih.nci.cagrid.dorian.ifs.bean.DelegationPathLength delegation) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.InvalidAssertionFault,
        gov.nih.nci.cagrid.dorian.stubs.types.InvalidProxyFault, gov.nih.nci.cagrid.dorian.stubs.types.UserPolicyFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "createProxy");
            gov.nih.nci.cagrid.dorian.stubs.CreateProxyRequest params = new gov.nih.nci.cagrid.dorian.stubs.CreateProxyRequest();
            gov.nih.nci.cagrid.dorian.stubs.CreateProxyRequestSaml samlContainer = new gov.nih.nci.cagrid.dorian.stubs.CreateProxyRequestSaml();
            samlContainer.setSAMLAssertion(saml);
            params.setSaml(samlContainer);
            gov.nih.nci.cagrid.dorian.stubs.CreateProxyRequestPublicKey publicKeyContainer = new gov.nih.nci.cagrid.dorian.stubs.CreateProxyRequestPublicKey();
            publicKeyContainer.setPublicKey(publicKey);
            params.setPublicKey(publicKeyContainer);
            gov.nih.nci.cagrid.dorian.stubs.CreateProxyRequestLifetime lifetimeContainer = new gov.nih.nci.cagrid.dorian.stubs.CreateProxyRequestLifetime();
            lifetimeContainer.setProxyLifetime(lifetime);
            params.setLifetime(lifetimeContainer);
            gov.nih.nci.cagrid.dorian.stubs.CreateProxyRequestDelegation delegationContainer = new gov.nih.nci.cagrid.dorian.stubs.CreateProxyRequestDelegation();
            delegationContainer.setDelegationPathLength(delegation);
            params.setDelegation(delegationContainer);
            gov.nih.nci.cagrid.dorian.stubs.CreateProxyResponse boxedResult = portType.createProxy(params);
            return boxedResult.getX509Certificate();
        }
    }


    public gov.nih.nci.cagrid.dorian.bean.X509Certificate getCACertificate() throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "getCACertificate");
            gov.nih.nci.cagrid.dorian.stubs.GetCACertificateRequest params = new gov.nih.nci.cagrid.dorian.stubs.GetCACertificateRequest();
            gov.nih.nci.cagrid.dorian.stubs.GetCACertificateResponse boxedResult = portType.getCACertificate(params);
            return boxedResult.getX509Certificate();
        }
    }


    public gov.nih.nci.cagrid.dorian.ifs.bean.TrustedIdP[] getTrustedIdPs() throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "getTrustedIdPs");
            gov.nih.nci.cagrid.dorian.stubs.GetTrustedIdPsRequest params = new gov.nih.nci.cagrid.dorian.stubs.GetTrustedIdPsRequest();
            gov.nih.nci.cagrid.dorian.stubs.GetTrustedIdPsResponse boxedResult = portType.getTrustedIdPs(params);
            return boxedResult.getTrustedIdP();
        }
    }


    public gov.nih.nci.cagrid.dorian.ifs.bean.TrustedIdP addTrustedIdP(gov.nih.nci.cagrid.dorian.ifs.bean.TrustedIdP idp)
        throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.InvalidTrustedIdPFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "addTrustedIdP");
            gov.nih.nci.cagrid.dorian.stubs.AddTrustedIdPRequest params = new gov.nih.nci.cagrid.dorian.stubs.AddTrustedIdPRequest();
            gov.nih.nci.cagrid.dorian.stubs.AddTrustedIdPRequestIdp idpContainer = new gov.nih.nci.cagrid.dorian.stubs.AddTrustedIdPRequestIdp();
            idpContainer.setTrustedIdP(idp);
            params.setIdp(idpContainer);
            gov.nih.nci.cagrid.dorian.stubs.AddTrustedIdPResponse boxedResult = portType.addTrustedIdP(params);
            return boxedResult.getTrustedIdP();
        }
    }


    public void updateTrustedIdP(gov.nih.nci.cagrid.dorian.ifs.bean.TrustedIdP trustedIdP) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.InvalidTrustedIdPFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "updateTrustedIdP");
            gov.nih.nci.cagrid.dorian.stubs.UpdateTrustedIdPRequest params = new gov.nih.nci.cagrid.dorian.stubs.UpdateTrustedIdPRequest();
            gov.nih.nci.cagrid.dorian.stubs.UpdateTrustedIdPRequestTrustedIdP trustedIdPContainer = new gov.nih.nci.cagrid.dorian.stubs.UpdateTrustedIdPRequestTrustedIdP();
            trustedIdPContainer.setTrustedIdP(trustedIdP);
            params.setTrustedIdP(trustedIdPContainer);
            gov.nih.nci.cagrid.dorian.stubs.UpdateTrustedIdPResponse boxedResult = portType.updateTrustedIdP(params);
        }
    }


    public void removeTrustedIdP(gov.nih.nci.cagrid.dorian.ifs.bean.TrustedIdP trustedIdP) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.InvalidTrustedIdPFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "removeTrustedIdP");
            gov.nih.nci.cagrid.dorian.stubs.RemoveTrustedIdPRequest params = new gov.nih.nci.cagrid.dorian.stubs.RemoveTrustedIdPRequest();
            gov.nih.nci.cagrid.dorian.stubs.RemoveTrustedIdPRequestTrustedIdP trustedIdPContainer = new gov.nih.nci.cagrid.dorian.stubs.RemoveTrustedIdPRequestTrustedIdP();
            trustedIdPContainer.setTrustedIdP(trustedIdP);
            params.setTrustedIdP(trustedIdPContainer);
            gov.nih.nci.cagrid.dorian.stubs.RemoveTrustedIdPResponse boxedResult = portType.removeTrustedIdP(params);
        }
    }


    public gov.nih.nci.cagrid.dorian.ifs.bean.IFSUser[] findIFSUsers(
        gov.nih.nci.cagrid.dorian.ifs.bean.IFSUserFilter filter) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "findIFSUsers");
            gov.nih.nci.cagrid.dorian.stubs.FindIFSUsersRequest params = new gov.nih.nci.cagrid.dorian.stubs.FindIFSUsersRequest();
            gov.nih.nci.cagrid.dorian.stubs.FindIFSUsersRequestFilter filterContainer = new gov.nih.nci.cagrid.dorian.stubs.FindIFSUsersRequestFilter();
            filterContainer.setIFSUserFilter(filter);
            params.setFilter(filterContainer);
            gov.nih.nci.cagrid.dorian.stubs.FindIFSUsersResponse boxedResult = portType.findIFSUsers(params);
            return boxedResult.getIFSUser();
        }
    }


    public void updateIFSUser(gov.nih.nci.cagrid.dorian.ifs.bean.IFSUser user) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.InvalidUserFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "updateIFSUser");
            gov.nih.nci.cagrid.dorian.stubs.UpdateIFSUserRequest params = new gov.nih.nci.cagrid.dorian.stubs.UpdateIFSUserRequest();
            gov.nih.nci.cagrid.dorian.stubs.UpdateIFSUserRequestUser userContainer = new gov.nih.nci.cagrid.dorian.stubs.UpdateIFSUserRequestUser();
            userContainer.setIFSUser(user);
            params.setUser(userContainer);
            gov.nih.nci.cagrid.dorian.stubs.UpdateIFSUserResponse boxedResult = portType.updateIFSUser(params);
        }
    }


    public void removeIFSUser(gov.nih.nci.cagrid.dorian.ifs.bean.IFSUser user) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.InvalidUserFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "removeIFSUser");
            gov.nih.nci.cagrid.dorian.stubs.RemoveIFSUserRequest params = new gov.nih.nci.cagrid.dorian.stubs.RemoveIFSUserRequest();
            gov.nih.nci.cagrid.dorian.stubs.RemoveIFSUserRequestUser userContainer = new gov.nih.nci.cagrid.dorian.stubs.RemoveIFSUserRequestUser();
            userContainer.setIFSUser(user);
            params.setUser(userContainer);
            gov.nih.nci.cagrid.dorian.stubs.RemoveIFSUserResponse boxedResult = portType.removeIFSUser(params);
        }
    }


    public gov.nih.nci.cagrid.dorian.ifs.bean.IFSUser renewIFSUserCredentials(
        gov.nih.nci.cagrid.dorian.ifs.bean.IFSUser user) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.InvalidUserFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "renewIFSUserCredentials");
            gov.nih.nci.cagrid.dorian.stubs.RenewIFSUserCredentialsRequest params = new gov.nih.nci.cagrid.dorian.stubs.RenewIFSUserCredentialsRequest();
            gov.nih.nci.cagrid.dorian.stubs.RenewIFSUserCredentialsRequestUser userContainer = new gov.nih.nci.cagrid.dorian.stubs.RenewIFSUserCredentialsRequestUser();
            userContainer.setIFSUser(user);
            params.setUser(userContainer);
            gov.nih.nci.cagrid.dorian.stubs.RenewIFSUserCredentialsResponse boxedResult = portType
                .renewIFSUserCredentials(params);
            return boxedResult.getIFSUser();
        }
    }


    public gov.nih.nci.cagrid.dorian.ifs.bean.IFSUserPolicy[] getIFSUserPolicies() throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "getIFSUserPolicies");
            gov.nih.nci.cagrid.dorian.stubs.GetIFSUserPoliciesRequest params = new gov.nih.nci.cagrid.dorian.stubs.GetIFSUserPoliciesRequest();
            gov.nih.nci.cagrid.dorian.stubs.GetIFSUserPoliciesResponse boxedResult = portType
                .getIFSUserPolicies(params);
            return boxedResult.getIFSUserPolicy();
        }
    }


    public gov.nih.nci.cagrid.authentication.bean.SAMLAssertion authenticate(
        gov.nih.nci.cagrid.authentication.bean.Credential credential) throws RemoteException,
        gov.nih.nci.cagrid.authentication.stubs.types.InvalidCredentialFault,
        gov.nih.nci.cagrid.authentication.stubs.types.InsufficientAttributeFault,
        gov.nih.nci.cagrid.authentication.stubs.types.AuthenticationProviderFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "authenticate");
            gov.nih.nci.cagrid.authentication.AuthenticateRequest params = new gov.nih.nci.cagrid.authentication.AuthenticateRequest();
            gov.nih.nci.cagrid.authentication.AuthenticateRequestCredential credentialContainer = new gov.nih.nci.cagrid.authentication.AuthenticateRequestCredential();
            credentialContainer.setCredential(credential);
            params.setCredential(credentialContainer);
            gov.nih.nci.cagrid.authentication.AuthenticateResponse boxedResult = portType.authenticate(params);
            return boxedResult.getSAMLAssertion();
        }
    }


    public void addAdmin(java.lang.String gridIdentity) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "addAdmin");
            gov.nih.nci.cagrid.dorian.stubs.AddAdminRequest params = new gov.nih.nci.cagrid.dorian.stubs.AddAdminRequest();
            params.setGridIdentity(gridIdentity);
            gov.nih.nci.cagrid.dorian.stubs.AddAdminResponse boxedResult = portType.addAdmin(params);
        }
    }


    public void removeAdmin(java.lang.String gridIdentity) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "removeAdmin");
            gov.nih.nci.cagrid.dorian.stubs.RemoveAdminRequest params = new gov.nih.nci.cagrid.dorian.stubs.RemoveAdminRequest();
            params.setGridIdentity(gridIdentity);
            gov.nih.nci.cagrid.dorian.stubs.RemoveAdminResponse boxedResult = portType.removeAdmin(params);
        }
    }


    public java.lang.String[] getAdmins() throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "getAdmins");
            gov.nih.nci.cagrid.dorian.stubs.GetAdminsRequest params = new gov.nih.nci.cagrid.dorian.stubs.GetAdminsRequest();
            gov.nih.nci.cagrid.dorian.stubs.GetAdminsResponse boxedResult = portType.getAdmins(params);
            return boxedResult.getResponse();
        }
    }


    public gov.nih.nci.cagrid.dorian.ifs.bean.HostCertificateRecord requestHostCertificate(
        gov.nih.nci.cagrid.dorian.ifs.bean.HostCertificateRequest req) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.InvalidHostCertificateRequestFault,
        gov.nih.nci.cagrid.dorian.stubs.types.InvalidHostCertificateFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "requestHostCertificate");
            gov.nih.nci.cagrid.dorian.stubs.RequestHostCertificateRequest params = new gov.nih.nci.cagrid.dorian.stubs.RequestHostCertificateRequest();
            gov.nih.nci.cagrid.dorian.stubs.RequestHostCertificateRequestReq reqContainer = new gov.nih.nci.cagrid.dorian.stubs.RequestHostCertificateRequestReq();
            reqContainer.setHostCertificateRequest(req);
            params.setReq(reqContainer);
            gov.nih.nci.cagrid.dorian.stubs.RequestHostCertificateResponse boxedResult = portType
                .requestHostCertificate(params);
            return boxedResult.getHostCertificateRecord();
        }
    }


    public gov.nih.nci.cagrid.dorian.ifs.bean.HostCertificateRecord[] getOwnedHostCertificates()
        throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "getOwnedHostCertificates");
            gov.nih.nci.cagrid.dorian.stubs.GetOwnedHostCertificatesRequest params = new gov.nih.nci.cagrid.dorian.stubs.GetOwnedHostCertificatesRequest();
            gov.nih.nci.cagrid.dorian.stubs.GetOwnedHostCertificatesResponse boxedResult = portType
                .getOwnedHostCertificates(params);
            return boxedResult.getHostCertificateRecord();
        }
    }


    public gov.nih.nci.cagrid.dorian.ifs.bean.HostCertificateRecord approveHostCertificate(java.math.BigInteger recordId)
        throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.InvalidHostCertificateFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "approveHostCertificate");
            gov.nih.nci.cagrid.dorian.stubs.ApproveHostCertificateRequest params = new gov.nih.nci.cagrid.dorian.stubs.ApproveHostCertificateRequest();
            params.setRecordId(recordId);
            gov.nih.nci.cagrid.dorian.stubs.ApproveHostCertificateResponse boxedResult = portType
                .approveHostCertificate(params);
            return boxedResult.getHostCertificateRecord();
        }
    }


    public gov.nih.nci.cagrid.dorian.ifs.bean.HostCertificateRecord[] findHostCertificates(
        gov.nih.nci.cagrid.dorian.ifs.bean.HostCertificateFilter hostCertificateFilter) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "findHostCertificates");
            gov.nih.nci.cagrid.dorian.stubs.FindHostCertificatesRequest params = new gov.nih.nci.cagrid.dorian.stubs.FindHostCertificatesRequest();
            gov.nih.nci.cagrid.dorian.stubs.FindHostCertificatesRequestHostCertificateFilter hostCertificateFilterContainer = new gov.nih.nci.cagrid.dorian.stubs.FindHostCertificatesRequestHostCertificateFilter();
            hostCertificateFilterContainer.setHostCertificateFilter(hostCertificateFilter);
            params.setHostCertificateFilter(hostCertificateFilterContainer);
            gov.nih.nci.cagrid.dorian.stubs.FindHostCertificatesResponse boxedResult = portType
                .findHostCertificates(params);
            return boxedResult.getHostCertificateRecord();
        }
    }


    public void updateHostCertificateRecord(
        gov.nih.nci.cagrid.dorian.ifs.bean.HostCertificateUpdate hostCertificateUpdate) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.InvalidHostCertificateFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "updateHostCertificateRecord");
            gov.nih.nci.cagrid.dorian.stubs.UpdateHostCertificateRecordRequest params = new gov.nih.nci.cagrid.dorian.stubs.UpdateHostCertificateRecordRequest();
            gov.nih.nci.cagrid.dorian.stubs.UpdateHostCertificateRecordRequestHostCertificateUpdate hostCertificateUpdateContainer = new gov.nih.nci.cagrid.dorian.stubs.UpdateHostCertificateRecordRequestHostCertificateUpdate();
            hostCertificateUpdateContainer.setHostCertificateUpdate(hostCertificateUpdate);
            params.setHostCertificateUpdate(hostCertificateUpdateContainer);
            gov.nih.nci.cagrid.dorian.stubs.UpdateHostCertificateRecordResponse boxedResult = portType
                .updateHostCertificateRecord(params);
        }
    }


    public gov.nih.nci.cagrid.dorian.ifs.bean.HostCertificateRecord renewHostCertificate(java.math.BigInteger recordId)
        throws RemoteException, gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.InvalidHostCertificateFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "renewHostCertificate");
            gov.nih.nci.cagrid.dorian.stubs.RenewHostCertificateRequest params = new gov.nih.nci.cagrid.dorian.stubs.RenewHostCertificateRequest();
            params.setRecordId(recordId);
            gov.nih.nci.cagrid.dorian.stubs.RenewHostCertificateResponse boxedResult = portType
                .renewHostCertificate(params);
            return boxedResult.getHostCertificateRecord();
        }
    }


    public void changeIdPUserPassword(gov.nih.nci.cagrid.dorian.idp.bean.BasicAuthCredential credential,
        java.lang.String newPassword) throws RemoteException,
        gov.nih.nci.cagrid.dorian.stubs.types.DorianInternalFault,
        gov.nih.nci.cagrid.dorian.stubs.types.PermissionDeniedFault,
        gov.nih.nci.cagrid.dorian.stubs.types.InvalidUserPropertyFault {
        synchronized (portTypeMutex) {
            configureStubSecurity((Stub) portType, "changeIdPUserPassword");
            gov.nih.nci.cagrid.dorian.stubs.ChangeIdPUserPasswordRequest params = new gov.nih.nci.cagrid.dorian.stubs.ChangeIdPUserPasswordRequest();
            gov.nih.nci.cagrid.dorian.stubs.ChangeIdPUserPasswordRequestCredential credentialContainer = new gov.nih.nci.cagrid.dorian.stubs.ChangeIdPUserPasswordRequestCredential();
            credentialContainer.setBasicAuthCredential(credential);
            params.setCredential(credentialContainer);
            params.setNewPassword(newPassword);
            gov.nih.nci.cagrid.dorian.stubs.ChangeIdPUserPasswordResponse boxedResult = portType
                .changeIdPUserPassword(params);
        }
    }

}
