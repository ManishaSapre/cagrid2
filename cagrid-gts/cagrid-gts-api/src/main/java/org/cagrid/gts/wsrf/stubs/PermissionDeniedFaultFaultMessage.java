
package org.cagrid.gts.wsrf.stubs;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.6.3
 * 2013-06-04T12:08:10.842-04:00
 * Generated source version: 2.6.3
 */

@WebFault(name = "PermissionDeniedFault", targetNamespace = "http://cagrid.nci.nih.gov/GTS/types")
public class PermissionDeniedFaultFaultMessage extends Exception {
    
    private org.cagrid.gts.types.PermissionDeniedFault permissionDeniedFault;

    public PermissionDeniedFaultFaultMessage() {
        super();
    }
    
    public PermissionDeniedFaultFaultMessage(String message) {
        super(message);
    }
    
    public PermissionDeniedFaultFaultMessage(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionDeniedFaultFaultMessage(String message, org.cagrid.gts.types.PermissionDeniedFault permissionDeniedFault) {
        super(message);
        this.permissionDeniedFault = permissionDeniedFault;
    }

    public PermissionDeniedFaultFaultMessage(String message, org.cagrid.gts.types.PermissionDeniedFault permissionDeniedFault, Throwable cause) {
        super(message, cause);
        this.permissionDeniedFault = permissionDeniedFault;
    }

    public org.cagrid.gts.types.PermissionDeniedFault getFaultInfo() {
        return this.permissionDeniedFault;
    }
}
