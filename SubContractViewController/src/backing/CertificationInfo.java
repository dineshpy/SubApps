package backing;

import Utils.ADFUtils;

import Utils.JSFUtils;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

public class CertificationInfo {
    private RichTable t2;

    public CertificationInfo() {
    }

    ViewObject contractLineVO=ADFUtils.findIterator("XxscContractLinesVO2Iterator").getViewObject();
    ViewObject certificationHrdVO=ADFUtils.findIterator("XxscCertificationHeadersVO1Iterator").getViewObject();
    ViewObject certificationLineVO=ADFUtils.findIterator("XxscCertificationLinesVO1Iterator").getViewObject();
    

    public void onChangeContractNum(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if(valueChangeEvent.getNewValue()!=null){

          int certContID=certificationHrdVO.getCurrentRow().getAttribute("ContHeaderId")==null?0:Integer.parseInt(certificationHrdVO.getCurrentRow().getAttribute("ContHeaderId").toString());
          int certVersion=certificationHrdVO.getCurrentRow().getAttribute("Version")==null?0:Integer.parseInt(certificationHrdVO.getCurrentRow().getAttribute("Version").toString());
//          certificationHrdVO.getCurrentRow().getAttribute("ContHeaderId");
//          certificationHrdVO.getCurrentRow().getAttribute("Version");
          System.err.println("--contID---"+certContID+"-Version--"+certVersion);
          // Filtering contract line VO
          ViewCriteria  contractLineVC= contractLineVO.createViewCriteria();  
          ViewCriteriaRow contractLineVCRow = contractLineVC.createViewCriteriaRow();
          contractLineVCRow.setAttribute("ContHeaderId", certContID);
          contractLineVCRow.setAttribute("Version", certVersion);
          contractLineVC.addRow(contractLineVCRow);
          contractLineVO.applyViewCriteria(contractLineVC);
          contractLineVO.executeQuery();
          System.err.println("==COUNT==" +contractLineVO.getEstimatedRowCount());
          // Insert Certification Line Row
          RowSetIterator rs = contractLineVO.createRowSetIterator(null);
          while (rs.hasNext()) {
              Row contracrLineRow = rs.next();
              Object hid = contracrLineRow.getAttribute("ContHeaderId");
              Object lid = contracrLineRow.getAttribute("ContLineId");
              Object version = contracrLineRow.getAttribute("Version");
              System.err.println("HID==" + hid + "LID===" + lid+"==="+version);
              //Certification line Row adding
              Row certificaLineRow = certificationLineVO.createRow();
              certificaLineRow.setAttribute("ContractHeaderId", hid);
              certificaLineRow.setAttribute("ContLineId", lid);
              certificaLineRow.setAttribute("Version", version);
              certificaLineRow.setAttribute("CertHeaderId", certificationHrdVO.getCurrentRow().getAttribute("CertHeaderId"));
              certificaLineRow.setAttribute("OrgId", certificationHrdVO.getCurrentRow().getAttribute("OrgId"));
              certificationLineVO.insertRow(certificaLineRow);
              certificationLineVO.executeQuery();
              AdfFacesContext.getCurrentInstance().addPartialTarget(t2);
              System.err.println("Certification Line inserted");
              
          }
        }
    }

    public void setT2(RichTable t2) {
        this.t2 = t2;
    }

    public RichTable getT2() {
        return t2;
    }
}
