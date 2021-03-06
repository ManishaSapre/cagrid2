/**
   CertVerifyPolicyInt.java

   Copyright (C) 1999, Claymore Systems, Inc.
   All Rights Reserved.

   ekr@rtfm.com  Wed May  5 08:47:37 1999

   This package is a SSLv3/TLS implementation written by Eric Rescorla
   <ekr@rtfm.com> and licensed by Claymore Systems, Inc.

   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions
   are met:
   1. Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
   2. Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
   3. All advertising materials mentioning features or use of this software
      must display the following acknowledgement:
      This product includes software developed by Claymore Systems, Inc.
   4. Neither the name of Claymore Systems, Inc. nor the name of Eric
      Rescorla may be used to endorse or promote products derived from this
      software without specific prior written permission.

   THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
   ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
   FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
   DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
   OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
   HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
   LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
   OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
   SUCH DAMAGE.

   $Id: CertVerifyPolicyInt.java,v 1.1.1.1 2003/05/09 20:36:14 gawor Exp $

*/

package COM.claymoresystems.sslg;

public class CertVerifyPolicyInt {
     private boolean checkCertificateDatesV;
     private boolean requireBasicConstraintsV;
     private boolean requireBasicConstraintsCriticalV;
     private boolean requireKeyUsageV;
     
     /* Check certificate dates or not

        @param checkdate true or false
     */
     public void checkDates(boolean checkdate){
       checkCertificateDatesV=checkdate;
     }

     /* Whether we're checking certificate dates
     */
     public boolean checkDatesP(){
       return checkCertificateDatesV;
     }
     

     /* Require basic constraints or not

        @param require true or false
     */
     public void requireBasicConstraints(boolean require){
       requireBasicConstraintsV=require;
     }

     /*
       Whether we require basic constraints in CA certs
     */
     public boolean requireBasicConstraintsP(){
       return requireBasicConstraintsV;
     }

     /* Require basic constraints to be critical or not

        @param require true or false
     */
     public void requireBasicConstraintsCritical(boolean require){
       requireBasicConstraintsCriticalV=require;
     }

     /*
       Whether we require basic constraints in CA certs
     */
     public boolean requireBasicConstraintsCriticalP(){
       return requireBasicConstraintsCriticalV;
     }

     /* Require keyUsage extension in CA certs

        @param require true or false
     */
     public void requireKeyUsage(boolean require) {
       requireKeyUsageV=require;
     }

     /*
       Whether we require Key Usage in CA certs
     */
     public boolean requireKeyUsageP(){
       return requireKeyUsageV;
     }
}
