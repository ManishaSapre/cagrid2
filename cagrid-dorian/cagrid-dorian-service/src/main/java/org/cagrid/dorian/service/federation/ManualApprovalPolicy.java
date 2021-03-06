package org.cagrid.dorian.service.federation;

import org.cagrid.dorian.model.exceptions.DorianInternalException;
import org.cagrid.dorian.model.exceptions.UserPolicyException;
import org.cagrid.dorian.model.federation.GridUser;
import org.cagrid.dorian.model.federation.TrustedIdP;

/**
 * @author <A href="mailto:langella@bmi.osu.edu">Stephen Langella </A>
 * @author <A href="mailto:oster@bmi.osu.edu">Scott Oster </A>
 * @author <A href="mailto:hastings@bmi.osu.edu">Shannon Hastings </A>
 * @version $Id: ArgumentManagerTable.java,v 1.2 2004/10/15 16:35:16 langella
 *          Exp $
 */

public class ManualApprovalPolicy extends AccountPolicy {
	public void applyPolicy(TrustedIdP idp, GridUser user)
			throws DorianInternalException, UserPolicyException {
	}

	public String getDisplayName() {
		return "Manual Approval";
	}
}
