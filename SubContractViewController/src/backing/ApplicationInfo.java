package backing;

import Utils.ADFUtils;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewCriteria;
import oracle.jbo.ViewCriteriaRow;
import oracle.jbo.ViewObject;

public class ApplicationInfo {
    private RichTable t1;

    public ApplicationInfo() {
    }


    ViewObject contractLineVO=ADFUtils.findIterator("XxscContractLinesVO1Iterator").getViewObject();
    ViewObject applicationHrdVO=ADFUtils.findIterator("XxscPayApplHeadersVO1Iterator").getViewObject();
    ViewObject applicationLineVO=ADFUtils.findIterator("XxscPayApplLinesVO2Iterator").getViewObject();
    
    
    public void onChangeContractNumber(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        if(valueChangeEvent.getNewValue()!=null){
          int appContID=applicationHrdVO.getCurrentRow().getAttribute("ContHeaderId")==null?0:Integer.parseInt(applicationHrdVO.getCurrentRow().getAttribute("ContHeaderId").toString());
          int appVersion=applicationHrdVO.getCurrentRow().getAttribute("Version")==null?0:Integer.parseInt(applicationHrdVO.getCurrentRow().getAttribute("Version").toString());
            System.err.println("--contID---"+appContID+"-Version--"+appVersion);
            // Filtering contract line VO
            ViewCriteria  contractLineVC= contractLineVO.createViewCriteria();  
            ViewCriteriaRow contractLineVCRow = contractLineVC.createViewCriteriaRow();
            contractLineVCRow.setAttribute("ContHeaderId", appContID);
            contractLineVCRow.setAttribute("Version", appVersion);
            contractLineVC.addRow(contractLineVCRow);
            contractLineVO.applyViewCriteria(contractLineVC);
            contractLineVO.executeQuery();
            System.err.println("==COUNT==" +contractLineVO.getEstimatedRowCount());
            // Insert Application Line Row
            RowSetIterator rs = contractLineVO.createRowSetIterator(null);
            while (rs.hasNext()) {
                Row contracrLineRow = rs.next();
                Object hid = contracrLineRow.getAttribute("ContHeaderId");
                Object lid = contracrLineRow.getAttribute("ContLineId");
                Object version = contracrLineRow.getAttribute("Version");
                //Application line Row adding
                Row appLineRow = applicationLineVO.createRow();
                appLineRow.setAttribute("ContHeaderId", hid);
                appLineRow.setAttribute("ContLineId", lid);
                appLineRow.setAttribute("Version", version);
                appLineRow.setAttribute("ApplHeaderId", applicationHrdVO.getCurrentRow().getAttribute("ApplHeaderId"));
                appLineRow.setAttribute("OrgId", applicationHrdVO.getCurrentRow().getAttribute("OrgId"));
                applicationLineVO.insertRow(appLineRow);
                applicationLineVO.executeQuery();
                applicationHrdVO.executeQuery();
                AdfFacesContext.getCurrentInstance().addPartialTarget(t1);
                System.err.println("Certification Line inserted");
            }
        }
    }

    public void setT1(RichTable t1) {
        this.t1 = t1;
    }

    public RichTable getT1() {
        return t1;
    }
}
