package backing;

import Utils.DBUtils;

import Utils.JSFUtils;

import java.sql.SQLException;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

import oracle.jbo.server.ApplicationModuleImpl;

import oracle.jdbc.OracleTypes;

import view.backing.ADFUtils;

public class contrSearch {
    private RichInputText it2;
    private RichTable t1;
    private RichPopup p1;
    private RichPopup p2;

    public contrSearch() {
    }
    ViewObject conHdrVO = ADFUtils.findIterator("contractROVO1Iterator").getViewObject();
    ViewObject conHistoryHdrVO = ADFUtils.findIterator("contract_HistoryROVO1Iterator").getViewObject();
    
    ViewObject certificationSearchHdrVO = ADFUtils.findIterator("certificationSearchROVO1Iterator").getViewObject();
    ViewObject certificationHistoryHdrVO = ADFUtils.findIterator("certificationHistoryROVO1Iterator").getViewObject();
    
    
    public void contrSearchACL(ActionEvent actionEvent) {
        if(it2.getValue()!=null){
        ViewCriteria vc  = conHdrVO.createViewCriteria();
        ViewCriteriaRow vcr =  vc.createViewCriteriaRow();
        vcr.setAttribute("ContractNum", "like %"+it2.getValue()+"%");
        vc.addRow(vcr);
        conHdrVO.applyViewCriteria(vc);
        conHdrVO.executeQuery();
        System.err.println("111111111111111");
        }
        else{
            //ViewObject conHdrVO = ADFUtils.findIterator("contractROVO1Iterator").getViewObject();
            ViewCriteria vc  = conHdrVO.createViewCriteria();
            ViewCriteriaRow vcr =  vc.createViewCriteriaRow();
            vcr.setAttribute("ContractNum", "");
            vc.addRow(vcr);
            conHdrVO.applyViewCriteria(vc);
            conHdrVO.executeQuery();
            System.err.println("2222222222222");
        }
        AdfFacesContext.getCurrentInstance().addPartialTarget(t1);
    }

    public void setIt2(RichInputText it2) {
        this.it2 = it2;
    }

    public RichInputText getIt2() {
        return it2;
    }

    public void setT1(RichTable t1) {
        this.t1 = t1;
    }

    public RichTable getT1() {
        return t1;
    }

    public String onClickVariation() throws SQLException {
        String flag="E";
        String errorMessage=null;
         String pageRedirect=null;
        if(conHdrVO.getEstimatedRowCount()!=0){
            String headerID = (String)conHdrVO.getCurrentRow().getAttribute("ContHeaderId").toString();
            System.err.println("==headerID=="+headerID);
            String version = (String)conHdrVO.getCurrentRow().getAttribute("Version").toString();
            System.err.println("==version=="+version);
            flag= onCallVaration(headerID, version);
          if(flag.equals("S")){
              System.err.println("Package Created");  
//              ADFContext.getCurrent().getSessionScope().get("idd");
//              ADFContext.getCurrent().getSessionScope().get("versionn");
              
              ADFContext.getCurrent().getSessionScope().put("id", ADFContext.getCurrent().getSessionScope().get("idd"));
              ADFContext.getCurrent().getSessionScope().put("version",ADFContext.getCurrent().getSessionScope().get("versionn"));
              
              conHdrVO.executeQuery();
              pageRedirect="edit"; 
          }else{
            System.err.println("Error: Please check the variation Package");
            JSFUtils.addFacesErrorMessage("Variation not Created. Please select valid Contract Number");
              pageRedirect="";
            }
          }
        return pageRedirect;
     }
    
    private Object[][] dobProcArgs = null;
    
    public String onCallVaration(Object headId, Object version) throws SQLException{
           
           oracle.jbo.domain.Number headerID=new oracle.jbo.domain.Number(headId.toString());
           System.err.println("=---headID----"+headerID);
           oracle.jbo.domain.Number versionID=new oracle.jbo.domain.Number(version.toString());
           System.err.println("=---versionID----"+versionID);
           String flag = "E"; // Error
           String errorMessage = null;
           Object updatedHeader=null;
           Object updatedVersion=null;
           //        oracle.jbo.domain.Number newHeadId=new oracle.jbo.domain.Number(0);
           
           ApplicationModuleImpl am=(ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl(null); 
           dobProcArgs = new Object[][]{ { "IN", headerID, OracleTypes.NUMBER }, //0
                                         { "IN", versionID, OracleTypes.NUMBER },//1
                                         { "OUT", updatedHeader, OracleTypes.NUMBER},   //2
                                         { "OUT", updatedVersion, OracleTypes.NUMBER} ,  //3
                                         { "OUT", flag, OracleTypes.VARCHAR }, //4
                                         { "OUT", errorMessage, OracleTypes.VARCHAR }};  //5

           try {
               DBUtils.callDBStoredProcedure(am.getDBTransaction(),
                                         "call xxsc_contract_pkg.create_variations(?,?,?,?,?,?)",
                                         dobProcArgs);
               
               } catch (SQLException e) {
           }
           
           updatedHeader=dobProcArgs[2][1];
           updatedVersion=dobProcArgs[3][1];
           flag=(String)dobProcArgs[4][1];
           errorMessage=(String)dobProcArgs[5][1];
           
           System.err.println("==Duplicate Created===="+errorMessage);
           System.err.println("==Duplicate Created===="+updatedHeader);
           System.err.println("==Duplicate Created===="+updatedVersion);
           System.err.println("==Duplicate Created===="+flag);
           
           ADFContext.getCurrent().getSessionScope().put("idd", updatedHeader);
           ADFContext.getCurrent().getSessionScope().put("versionn", updatedVersion);
//           JSFUtils.addFacesInformationMessage("Flag"+updatedHeader);
//           JSFUtils.addFacesInformationMessage("Flag"+updatedVersion);
//           JSFUtils.addFacesInformationMessage("Flag"+flag);
//           JSFUtils.addFacesInformationMessage("Flag"+errorMessage);
             
            //JSFUtils.addFacesInformationMessage("Copy Purchase Order Completed.");
    
           return flag;
       }


    public void onCallContHistroy(ActionEvent actionEvent) {
        
        if(conHdrVO.getEstimatedRowCount()!=0){
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            RichPopup pop = getP1();
            pop.show(hints);
            String headerNum = (String)conHdrVO.getCurrentRow().getAttribute("ContractNum").toString();
            ViewCriteria vc  = conHistoryHdrVO.createViewCriteria();
            ViewCriteriaRow vcr =  vc.createViewCriteriaRow();
            vcr.setAttribute("ContractNum", headerNum);
            vc.addRow(vcr);
            conHistoryHdrVO.applyViewCriteria(vc);
            conHistoryHdrVO.executeQuery();
            System.err.println("===CONTREACT HISTORY TABLE FILTER=====");
        }
    }

    public void setP1(RichPopup p1) {
        this.p1 = p1;
    }

    public RichPopup getP1() {
        return p1;
    }


    public void setP2(RichPopup p2) {
        this.p2 = p2;
    }

    public RichPopup getP2() {
        return p2;
    }
    public void onCallCertificationHistory(ActionEvent actionEvent) {
        if(certificationSearchHdrVO.getEstimatedRowCount()!=0){
            RichPopup.PopupHints hints = new RichPopup.PopupHints();
            RichPopup pop = getP2();
            pop.show(hints);  
            // Contract Search ROVO- contract Id
            String contractID = (String)conHdrVO.getCurrentRow().getAttribute("ContHeaderId").toString();
            // Filter in certification History ROVO- contract Id
            ViewCriteria vc  = certificationHistoryHdrVO.createViewCriteria();
            ViewCriteriaRow vcr =  vc.createViewCriteriaRow();
            vcr.setAttribute("ContHeaderId", contractID);
            vc.addRow(vcr);
            certificationHistoryHdrVO.applyViewCriteria(vc);
            certificationHistoryHdrVO.executeQuery();
            System.err.println("===CERTIFICATION HISTORY TABLE FILTER====="); 
            
        }
        
    }

 
}
