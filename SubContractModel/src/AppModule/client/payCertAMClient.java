package AppModule.client;

import AppModule.common.payCertAM;

import oracle.jbo.client.remote.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Sep 13 16:57:50 IST 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class payCertAMClient extends ApplicationModuleImpl implements payCertAM {
    /**
     * This is the default constructor (do not remove).
     */
    public payCertAMClient() {
    }

    public void createNewCertification() {
        Object _ret =
            this.riInvokeExportedMethod(this,"createNewCertification",null,null);
        return;
    }

    public void refreshCertification() {
        Object _ret =
            this.riInvokeExportedMethod(this,"refreshCertification",null,null);
        return;
    }
}