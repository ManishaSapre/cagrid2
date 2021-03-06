package gov.nih.nci.cagrid.common.security.commstyle;


import org.apache.axis.client.Stub;
import org.globus.gsi.GSIConstants;
import org.globus.gsi.GlobusCredential;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.globus.wsrf.security.Constants;
import org.ietf.jgss.GSSCredential;
/**
 * @author <A href="mailto:langella@bmi.osu.edu">Stephen Langella </A>
 * @author <A href="mailto:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A href="mailto:hastings@bmi.osu.edu">Shannon Hastings </A>
 * @version $Id: ArgumentManagerTable.java,v 1.2 2004/10/15 16:35:16 langella
 *          Exp $
 */
public class SecureConversationWithEncryption implements CommunicationStyle{
	private GlobusCredential credential;
	
	public SecureConversationWithEncryption(){	
	
	}
	
	public SecureConversationWithEncryption(GlobusCredential credential){	
		this.credential = credential;
	}
	
	public void configure(Stub stub) throws CommunicationStyleException{
		try{
		stub._setProperty(Constants.GSI_SEC_CONV, GSIConstants.ENCRYPTION);
		if (credential != null) {
			GSSCredential gss = new GlobusGSSCredentialImpl(credential, GSSCredential.INITIATE_AND_ACCEPT);
			stub._setProperty(org.globus.axis.gsi.GSIConstants.GSI_CREDENTIALS, gss);
		}
		}catch(Exception e){
			e.printStackTrace();
			throw new CommunicationStyleException(e.getMessage());
		}
	}

}
