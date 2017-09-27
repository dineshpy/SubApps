package AppModule;

import AppModule.common.applicationAM;

import oracle.adf.share.ADFContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.ViewLinkImpl;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Sep 23 17:30:58 IST 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class applicationAMImpl extends ApplicationModuleImpl implements applicationAM {
    /**
     * This is the default constructor (do not remove).
     */
    public applicationAMImpl() {
    }

    /**
     * Container's getter for XxscPayApplHeadersVO1.
     * @return XxscPayApplHeadersVO1
     */
    public ViewObjectImpl getXxscPayApplHeadersVO1() {
        return (ViewObjectImpl)findViewObject("XxscPayApplHeadersVO1");
    }

    /**
     * Container's getter for XxscPayApplLinesVO2.
     * @return XxscPayApplLinesVO2
     */
    public ViewObjectImpl getXxscPayApplLinesVO2() {
        return (ViewObjectImpl)findViewObject("XxscPayApplLinesVO2");
    }

    /**
     * Container's getter for XxscContractLinesVO1.
     * @return XxscContractLinesVO1
     */
    public ViewObjectImpl getXxscContractLinesVO1() {
        return (ViewObjectImpl)findViewObject("XxscContractLinesVO1");
    }

    /**
     * Container's getter for PayApplLineHdrFkLink1.
     * @return PayApplLineHdrFkLink1
     */
    public ViewLinkImpl getPayApplLineHdrFkLink1() {
        return (ViewLinkImpl)findViewLink("PayApplLineHdrFkLink1");
    }
    
    public void createNewApplication(){
        System.err.println("===" +ADFContext.getCurrent().getSessionScope().get("contHdrID"));
        System.err.println("===" +ADFContext.getCurrent().getSessionScope().get("version"));
        System.err.println("===" +ADFContext.getCurrent().getSessionScope().get("ordID"));
        System.err.println("===" +ADFContext.getCurrent().getSessionScope().get("paymentID"));
        System.err.println("===" +ADFContext.getCurrent().getSessionScope().get("paymentNum"));
        ViewObject appHrdVO = getXxscPayApplHeadersVO1();
        ViewObject appLineVO = getXxscPayApplLinesVO2();
        // App New Row
         Row appHrdRow = appHrdVO.createRow();
        appHrdRow.setAttribute("ContHeaderId",ADFContext.getCurrent().getSessionScope().get("contHdrID"));
        appHrdRow.setAttribute("Version",ADFContext.getCurrent().getSessionScope().get("version"));
        appHrdRow.setAttribute("OrgId",ADFContext.getCurrent().getSessionScope().get("ordID"));
        appHrdRow.setAttribute("PaymentTermId", ADFContext.getCurrent().getSessionScope().get("paymentID"));
        appHrdRow.setAttribute("PaymentTerm", ADFContext.getCurrent().getSessionScope().get("paymentNum"));
        appHrdVO.insertRow(appHrdRow);
        appHrdVO.executeQuery();
        System.err.println("App header id-->  " + appHrdRow.getAttribute("ApplHeaderId"));
        // Filter contract line
        ViewObject contractLinedVO = getXxscContractLinesVO1();
        ViewCriteria vc = contractLinedVO.createViewCriteria();
        ViewCriteriaRow vcRow = vc.createViewCriteriaRow();
        System.err.println("--=LINE=--" +ADFContext.getCurrent().getSessionScope().get("contHdrID"));
        vcRow.setAttribute("ContHeaderId",ADFContext.getCurrent().getSessionScope().get("contHdrID"));
        vcRow.setAttribute("Version",ADFContext.getCurrent().getSessionScope().get("version"));
        vc.addRow(vcRow);
        contractLinedVO.applyViewCriteria(vc);
        contractLinedVO.executeQuery();
        System.err.println("==COUNT==" +contractLinedVO.getEstimatedRowCount());
        // Insert application Line Row
        RowSetIterator rs = contractLinedVO.createRowSetIterator(null);
        while (rs.hasNext()) {
          Row contracrLineRow = rs.next();
          Object hid = contracrLineRow.getAttribute("ContHeaderId");
          Object lid = contracrLineRow.getAttribute("ContLineId");
          Object version = contracrLineRow.getAttribute("Version");
          System.err.println("HID==" + hid + "LID===" + lid+"==="+version);
          Row appLineRow = appLineVO.createRow();
        System.err.println("App header id-->  " + appHrdRow.getAttribute("ApplHeaderId"));
         appLineRow.setAttribute("ContHeaderId", hid);
         appLineRow.setAttribute("ContLineId", lid);
         appLineRow.setAttribute("Version", version);
         appLineRow.setAttribute("ApplHeaderId", appHrdRow.getAttribute("ApplHeaderId"));
         appLineRow.setAttribute("OrgId", ADFContext.getCurrent().getSessionScope().get("ordID"));
         appLineVO.insertRow(appLineRow);
         appLineVO.executeQuery();
         System.err.println("Application Line inserted");

    }
    }
        
        
    public void refreshApplication(){
    System.err.println("==APP==PAGE 0=="+ADFContext.getCurrent().getSessionScope().get("page"));
        if("certification".equalsIgnoreCase((String)ADFContext.getCurrent().getSessionScope().get("page"))){
            System.err.println("==CERT==PAGE=="+ADFContext.getCurrent().getSessionScope().get("page"));
            getXxscPayApplHeadersVO1().createRow();
            getXxscPayApplHeadersVO1().executeQuery();
          }
      else{
            //createNewApplication();
            //getXxscPayApplHeadersVO1().executeQuery();            
          }
    System.err.println("==CERT==PAGE END=="+ADFContext.getCurrent().getSessionScope().get("page"));
    
    
    }   
        
        
}
