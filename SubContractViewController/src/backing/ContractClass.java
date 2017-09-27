package backing;


import Utils.ADFUtils;

import Utils.JSFUtils;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;

import oracle.adf.view.rich.component.rich.input.RichInputText;

import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

//import view.backing.ADFUtils;
//Dinesh Test

public class ContractClass {
    private RichInputDate stdate;
    private RichInputDate endate;
    private RichInputDate revdate;
    private RichInputText tocInDays;
    private RichInputText qty;
    private RichInputText price;
    private RichInputText lineAmount;
    private RichInputText revQty;
    private RichInputText revPrice;
    private RichInputText revLineAmount;
    private RichInputText revLineAmount1;
    private RichInputText contractAmount;
    private RichInputText varPrice;
    private RichInputText varAmount;
    private RichInputText varQty;
    private RichInputText contVariationAmount;
    private RichInputText contRevisedAmount;
    private RichInputText contTotalSum;
    private RichInputText provisionalSum;


    public ContractClass() {
    }

ViewObject contractVO= ADFUtils.findIterator("XxscContractHeadersVO1Iterator").getViewObject();
ViewObject searchContract=ADFUtils.findIterator("contractROVO1Iterator").getViewObject();
Row contractRow=contractVO.getCurrentRow();
ViewObject contractLineVO= ADFUtils.findIterator("XxscContractLinesVO1Iterator").getViewObject();
Row contractLineRow=contractLineVO.getCurrentRow();
        
    public void onClickSave(ActionEvent actionEvent) {
        if(ADFContext.getCurrent().getSessionScope().get("page").equals("buy")){
            contractRow.setAttribute("ContractNum", "CONT-"+contractRow.getAttribute("ContHeaderId"));
            contractRow.setAttribute("Intent", "B");
            ADFUtils.findOperation("Commit").execute();
            searchContract.executeQuery();
            JSFUtils.numberInfaceMessage("Buy Contract", contractRow.getAttribute("ContractNum"));
            
        }else if(ADFContext.getCurrent().getSessionScope().get("page").equals("sell")){
            contractRow.setAttribute("ContractNum", "CONT-"+contractRow.getAttribute("ContHeaderId"));
            contractRow.setAttribute("Intent", "S");
            ADFUtils.findOperation("Commit").execute();
            searchContract.executeQuery();
            JSFUtils.numberInfaceMessage("Sell Contract", contractRow.getAttribute("ContractNum"));
        }else{
            ADFUtils.findOperation("Commit").execute();
            JSFUtils.addFacesInformationMessage("Information Saved Successfully");   
        }
        
    }

    public void setStdate(RichInputDate stdate) {
        this.stdate = stdate;
    }

    public RichInputDate getStdate() {
        return stdate;
    }

    public void setEndate(RichInputDate endate) {
        this.endate = endate;
    }

    public RichInputDate getEndate() {
        return endate;
    }

    public void setRevdate(RichInputDate revdate) {
        this.revdate = revdate;
    }

    public RichInputDate getRevdate() {
        return revdate;
    }

    public void setTocInDays(RichInputText tocInDays) {
        this.tocInDays = tocInDays;
    }

    public RichInputText getTocInDays() {
        return tocInDays;
    }
    public static long getDifferenceDaysBetweenTwoDates(oracle.jbo.domain.Date d1, oracle.jbo.domain.Date d2){
     if(d1 != null && d2 != null){
//           long diff = d2.getValue().getTime()-d1.getValue().getTime();
//           System.err.println("==d2=="+d2.getValue().getTime());
//           System.err.println("==d1=="+d1.getValue().getTime());
//           System.err.println("==diff=="+diff+"--diff in no  "+ diff/(24*60*60*1000) +"Extra-1--"+((diff/(24*60*60*1000))+1));
        return (((d2.getValue().getTime() - d1.getValue().getTime())/(24 * 60 * 60 * 1000))+1);
       }
           return 0;
       }
    
    public void endDateTOC(ValueChangeEvent valueChangeEvent) {
        if(revdate.getValue()!=null){
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            oracle.jbo.domain.Date st = (oracle.jbo.domain.Date)stdate.getValue();
            System.err.println("---JBO S Date"+st);
            oracle.jbo.domain.Date rev = (oracle.jbo.domain.Date)revdate.getValue();
            System.err.println("---JBO Rev Date"+rev);
            long nDays=getDifferenceDaysBetweenTwoDates(st, rev);
            oracle.jbo.domain.Number noDays= new oracle.jbo.domain.Number(nDays);
            System.err.println("======retDate no of Days======"+noDays);
            tocInDays.setValue(noDays);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tocInDays);
            
        }else{
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            oracle.jbo.domain.Date st = (oracle.jbo.domain.Date)stdate.getValue();
            System.err.println("---JBO S Date"+st);
            oracle.jbo.domain.Date en = (oracle.jbo.domain.Date)valueChangeEvent.getNewValue();
            System.err.println("---JBO E Date"+en);
            long nDays=getDifferenceDaysBetweenTwoDates(st, en);
            oracle.jbo.domain.Number noDays= new oracle.jbo.domain.Number(nDays);
            System.err.println("======END no of Days======"+noDays);    
            tocInDays.setValue(noDays);
            AdfFacesContext.getCurrentInstance().addPartialTarget(tocInDays);
        }
    }


    public void revDateTOC(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
        oracle.jbo.domain.Date st = (oracle.jbo.domain.Date)stdate.getValue();
        System.err.println("---JBO S Date"+st);
        oracle.jbo.domain.Date rev = (oracle.jbo.domain.Date)valueChangeEvent.getNewValue();
        System.err.println("---JBO Rev Date"+rev);
        long nDays=getDifferenceDaysBetweenTwoDates(st, rev);
        oracle.jbo.domain.Number noDays= new oracle.jbo.domain.Number(nDays);
        System.err.println("======END no of Days======"+noDays);    
        tocInDays.setValue(noDays);
        AdfFacesContext.getCurrentInstance().addPartialTarget(tocInDays);
    }

    public void setQty(RichInputText qty) {
        this.qty = qty;
    }

    public RichInputText getQty() {
        return qty;
    }

    public void setPrice(RichInputText price) {
        this.price = price;
    }

    public RichInputText getPrice() {
        return price;
    }

    public void setLineAmount(RichInputText lineAmount) {
        this.lineAmount = lineAmount;
    }

    public RichInputText getLineAmount() {
        return lineAmount;
    }
    
    
    public void setContractAmount(RichInputText contractAmount) {
        this.contractAmount = contractAmount;
    }

    public RichInputText getContractAmount() {
        return contractAmount;
    }

/**@@ Original Qty Calculation @@**/

    public void onChangeQTY(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null&&price.getValue()!=null){
            float orQty=valueChangeEvent.getNewValue()==null?0:Float.parseFloat(valueChangeEvent.getNewValue().toString());  
            float orPrize=price.getValue()==null?0:Float.parseFloat(price.getValue().toString());  
            float amount=orQty*orPrize;
            contractLineRow.setAttribute("OrigAmount", amount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lineAmount);
            
            // Iterator Contract Line Amount
            RowSetIterator rs=contractLineVO.createRowSetIterator(null); 
            float totalLineAmount=0;
            while(rs.hasNext()){
                contractLineRow=rs.next();
                float orgLineAmount=contractLineRow.getAttribute("OrigAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigAmount").toString());
                totalLineAmount+=orgLineAmount;
                System.err.println("==totalLineAmount=="+totalLineAmount);
            }
            contractVO.getCurrentRow().setAttribute("ContractAmount", totalLineAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(contractAmount);
            
            // Provisional Sum Calculation
            if(provisionalSum.getValue()!=null){
                float contractAmount=contractVO.getCurrentRow().getAttribute("ContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ContractAmount").toString());   
                float provisionalSum=contractVO.getCurrentRow().getAttribute("ProvisionalSum")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ProvisionalSum").toString());   
                float tcs=contractAmount+provisionalSum;
                contractVO.getCurrentRow().setAttribute("TotalContractSum", tcs);
                AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);   
            }else{
                float contractAmount=contractVO.getCurrentRow().getAttribute("ContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ContractAmount").toString());   
                contractVO.getCurrentRow().setAttribute("TotalContractSum", (contractAmount));
                AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);   
            }
            
        }else{
           
            float orQty=valueChangeEvent.getNewValue()==null?0:Float.parseFloat(valueChangeEvent.getNewValue().toString());  
            float orPrize=price.getValue()==null?0:Float.parseFloat(price.getValue().toString());  
            float amount=orQty*orPrize;
            if(amount==0){
                contractLineRow.setAttribute("OrigAmount", "");
                AdfFacesContext.getCurrentInstance().addPartialTarget(lineAmount);
            }else{
                contractLineRow.setAttribute("OrigAmount", amount);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lineAmount);                
            }
            
            // Iterator Contract Line Amount
            RowSetIterator rs=contractLineVO.createRowSetIterator(null); 
            float totalLineAmount=0;
            while(rs.hasNext()){
                contractLineRow=rs.next();
                float orgLineAmount=contractLineRow.getAttribute("OrigAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigAmount").toString());
                totalLineAmount+=orgLineAmount;
                System.err.println("==totalLineAmount=="+totalLineAmount);
            }
            contractVO.getCurrentRow().setAttribute("ContractAmount", totalLineAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(contractAmount);
           
           // Provisional Sum Calculation
            if(provisionalSum.getValue()!=null){
                float contractAmount=contractVO.getCurrentRow().getAttribute("ContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ContractAmount").toString());   
                float provisionalSum=contractVO.getCurrentRow().getAttribute("ProvisionalSum")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ProvisionalSum").toString());   
                float tcs=contractAmount+provisionalSum;
                contractVO.getCurrentRow().setAttribute("TotalContractSum", tcs);
                AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);   
            }else{
                float contractAmount=contractVO.getCurrentRow().getAttribute("ContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ContractAmount").toString());   
                contractVO.getCurrentRow().setAttribute("TotalContractSum", (contractAmount));
                AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);   
            }
           
        }
    }

/**@@ Original Price Calculation @@**/
    
    public void onChangePrice(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null&&qty.getValue()!=null){
            float orQty=qty.getValue()==null?0:Float.parseFloat(qty.getValue().toString());  
            float orPrize=valueChangeEvent.getNewValue()==null?0:Float.parseFloat(valueChangeEvent.getNewValue().toString());  
            float amount=orQty*orPrize;
            contractLineRow.setAttribute("OrigAmount", amount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(lineAmount);
            
            // Iterator Contract Line Amount
            RowSetIterator rs=contractLineVO.createRowSetIterator(null); 
            float totalLineAmount=0;
            while(rs.hasNext()){
                contractLineRow=rs.next();
                float orgLineAmount=contractLineRow.getAttribute("OrigAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigAmount").toString());
                totalLineAmount+=orgLineAmount;
                System.err.println("==totalLineAmount=="+totalLineAmount);
            }
            contractVO.getCurrentRow().setAttribute("ContractAmount", totalLineAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(contractAmount);
            
            // Provisional Sum Calculation
            if(provisionalSum.getValue()!=null){
                float contractAmount=contractVO.getCurrentRow().getAttribute("ContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ContractAmount").toString());   
                float provisionalSum=contractVO.getCurrentRow().getAttribute("ProvisionalSum")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ProvisionalSum").toString());   
                float tcs=contractAmount+provisionalSum;
                contractVO.getCurrentRow().setAttribute("TotalContractSum", tcs);
                AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);   
            }else{
                float contractAmount=contractVO.getCurrentRow().getAttribute("ContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ContractAmount").toString());   
                contractVO.getCurrentRow().setAttribute("TotalContractSum", (contractAmount));
                AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);   
            }
        }else{
            
            float orQty=qty.getValue()==null?0:Float.parseFloat(qty.getValue().toString());  
            float orPrize=valueChangeEvent.getNewValue()==null?0:Float.parseFloat(valueChangeEvent.getNewValue().toString());  
            float amount=orQty*orPrize;
            if(amount==0){
                contractLineRow.setAttribute("OrigAmount", "");
                AdfFacesContext.getCurrentInstance().addPartialTarget(lineAmount);
            }else{
                contractLineRow.setAttribute("OrigAmount", amount);
                AdfFacesContext.getCurrentInstance().addPartialTarget(lineAmount);
            }
            
            // Iterator Contract Line Amount
            RowSetIterator rs=contractLineVO.createRowSetIterator(null); 
            float totalLineAmount=0;
            while(rs.hasNext()){
                contractLineRow=rs.next();
                float orgLineAmount=contractLineRow.getAttribute("OrigAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigAmount").toString());
                totalLineAmount+=orgLineAmount;
                System.err.println("==totalLineAmount=="+totalLineAmount);
            }
            contractVO.getCurrentRow().setAttribute("ContractAmount", totalLineAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(contractAmount);
            
            // Provisional Sum Calculation
                if(provisionalSum.getValue()!=null){
                    float contractAmount=contractVO.getCurrentRow().getAttribute("ContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ContractAmount").toString());   
                    float provisionalSum=contractVO.getCurrentRow().getAttribute("ProvisionalSum")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ProvisionalSum").toString());   
                    float tcs=contractAmount+provisionalSum;
                    contractVO.getCurrentRow().setAttribute("TotalContractSum", tcs);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);   
                }else{
                    float contractAmount=contractVO.getCurrentRow().getAttribute("ContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ContractAmount").toString());   
                    contractVO.getCurrentRow().setAttribute("TotalContractSum", (contractAmount));
                    AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);   
                }
           }
    }


    public void setRevQty(RichInputText revQty) {
        this.revQty = revQty;
    }

    public RichInputText getRevQty() {
        return revQty;
    }

    public void setRevPrice(RichInputText revPrice) {
        this.revPrice = revPrice;
    }

    public RichInputText getRevPrice() {
        return revPrice;
    }

    public void setRevLineAmount(RichInputText revLineAmount) {
        this.revLineAmount = revLineAmount;
    }

    public RichInputText getRevLineAmount() {
        return revLineAmount;
    }

    public void setRevLineAmount1(RichInputText revLineAmount1) {
        this.revLineAmount1 = revLineAmount1;
    }

    public RichInputText getRevLineAmount1() {
        return revLineAmount1;
    }

    public void setContractVO(ViewObject contractVO) {
        this.contractVO = contractVO;
    }

    public ViewObject getContractVO() {
        return contractVO;
    }

    public void setContractRow(Row contractRow) {
        this.contractRow = contractRow;
    }

    public Row getContractRow() {
        return contractRow;
    }

    public void setContractLineVO(ViewObject contractLineVO) {
        this.contractLineVO = contractLineVO;
    }

    public ViewObject getContractLineVO() {
        return contractLineVO;
    }

    public void setContractLineRow(Row contractLineRow) {
        this.contractLineRow = contractLineRow;
    }

    public Row getContractLineRow() {
        return contractLineRow;
    }



    public void setVarPrice(RichInputText varPrice) {
        this.varPrice = varPrice;
    }

    public RichInputText getVarPrice() {
        return varPrice;
    }

    public void setVarAmount(RichInputText varAmount) {
        this.varAmount = varAmount;
    }

    public RichInputText getVarAmount() {
        return varAmount;
    }

    public void setVarQty(RichInputText varQty) {
        this.varQty = varQty;
    }

    public RichInputText getVarQty() {
        return varQty;
    }

    public void setContVariationAmount(RichInputText contVariationAmount) {
        this.contVariationAmount = contVariationAmount;
    }

    public RichInputText getContVariationAmount() {
        return contVariationAmount;
    }

    public void setContRevisedAmount(RichInputText contRevisedAmount) {
        this.contRevisedAmount = contRevisedAmount;
    }

    public RichInputText getContRevisedAmount() {
        return contRevisedAmount;
    }

/**@@ Revised Qty Calculation @@**/

    public void onChangeRevQty(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null&&revPrice.getValue()!=null){
             //Getting Contract Line==Original Qty , Price  and Amount
            float oriQty=contractLineRow.getAttribute("OrigQuantity")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigQuantity").toString());
            float oriPrice=contractLineRow.getAttribute("OrigUnitPrice")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigUnitPrice").toString());
            float oriAmount=contractLineRow.getAttribute("OrigAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigAmount").toString());
            
            // Calculating Contract Line--Revised Qty , Price and Amount
            float reQty=valueChangeEvent.getNewValue()==null?0:Float.parseFloat(valueChangeEvent.getNewValue().toString());  
            float rePrice=revPrice.getValue()==null?0:Float.parseFloat(revPrice.getValue().toString());  
            float revAmount=(reQty*rePrice);
            contractLineRow.setAttribute("RevAmount", revAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(revLineAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(revLineAmount1);
            
            // Setting Contract Line--Varation field
            float varQ=reQty-oriQty;
            contractLineRow.setAttribute("VarQuantity", varQ);
            AdfFacesContext.getCurrentInstance().addPartialTarget(varQty);
            float varP=rePrice-oriPrice;
            contractLineRow.setAttribute("VarUnitPrice", varP);
            AdfFacesContext.getCurrentInstance().addPartialTarget(varPrice);
            float varA=revAmount-oriAmount;
            contractLineRow.setAttribute("VarAmount", varA);
            AdfFacesContext.getCurrentInstance().addPartialTarget(varAmount);
            
            // Iterator Contract Line--Varation Amount
            RowSetIterator rs=contractLineVO.createRowSetIterator(null); 
            float totalVariationLineAmount=0;
            while(rs.hasNext()){
                contractLineRow=rs.next();
                float varLineAmount=contractLineRow.getAttribute("VarAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("VarAmount").toString());
                totalVariationLineAmount+=varLineAmount;
                System.err.println("-Total Variation Amount-"+totalVariationLineAmount);
            }
            contractVO.getCurrentRow().setAttribute("VariationAmount", totalVariationLineAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(contVariationAmount);
            
            
         // Iterator Contract Line--Revised Amount
            RowSetIterator revRs=contractLineVO.createRowSetIterator(null); 
            float totalRevisedLineAmount=0;
            float totOAmt=0;
            float totRAmt=0;
            while(revRs.hasNext()){
                contractLineRow=revRs.next();
                float varRevisedAmount=contractLineRow.getAttribute("RevAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("RevAmount").toString());
                if(varRevisedAmount==0){
                    float oriAmt=contractLineRow.getAttribute("OrigAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigAmount").toString());
                    totOAmt=totOAmt+oriAmt;
                }else{
                    float revAmt=contractLineRow.getAttribute("RevAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("RevAmount").toString());
                    totRAmt=totRAmt+revAmt;
                }
                totalRevisedLineAmount=totOAmt+totRAmt;
                System.err.println("-Total Revised Amount-"+totalRevisedLineAmount);
            }
            contractVO.getCurrentRow().setAttribute("RevisedContractAmount", totalRevisedLineAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(contRevisedAmount);
            
            
           // Provisional Sum Calculation--setting Total contract sum
            if(provisionalSum!=null){
                float revisedAmount=contractVO.getCurrentRow().getAttribute("RevisedContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("RevisedContractAmount").toString());   
                float provisionalSum=contractVO.getCurrentRow().getAttribute("ProvisionalSum")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ProvisionalSum").toString());   
                float tcs=revisedAmount+provisionalSum;
                contractVO.getCurrentRow().setAttribute("TotalContractSum", tcs);
                AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);   
            }else{
                float revisedAmount=contractVO.getCurrentRow().getAttribute("RevisedContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("RevisedContractAmount").toString());   
                contractVO.getCurrentRow().setAttribute("TotalContractSum", revisedAmount);
                AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);   
            }
            
        }else{
            
            //Getting Contract Line==Original Qty , Price  and Amount
            float oriQty=contractLineRow.getAttribute("OrigQuantity")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigQuantity").toString());
            float oriPrice=contractLineRow.getAttribute("OrigUnitPrice")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigUnitPrice").toString());
            float oriAmount=contractLineRow.getAttribute("OrigAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigAmount").toString());
            
            
            // Calculating Contract Line--Revised Qty , Price and Amount
            float reQty=valueChangeEvent.getNewValue()==null?0:Float.parseFloat(valueChangeEvent.getNewValue().toString());
            float rePrice=revPrice.getValue()==null?0:Float.parseFloat(revPrice.getValue().toString());
            float revAmount=reQty*rePrice;
            if(revAmount==0){
                contractLineRow.setAttribute("RevAmount", "");
                AdfFacesContext.getCurrentInstance().addPartialTarget(revLineAmount);
                AdfFacesContext.getCurrentInstance().addPartialTarget(revLineAmount1);
                contractLineRow.setAttribute("VarQuantity", "");
                AdfFacesContext.getCurrentInstance().addPartialTarget(varQty);  
                contractLineRow.setAttribute("VarUnitPrice", "");
                AdfFacesContext.getCurrentInstance().addPartialTarget(varPrice);  
                contractLineRow.setAttribute("VarAmount", "");
                AdfFacesContext.getCurrentInstance().addPartialTarget(varAmount);    
            }else{
                // Setting Contract Line--Varation field
                float varQ=reQty-oriQty;
                float varP=rePrice-oriPrice;
                float varA=revAmount-oriAmount;
                contractLineRow.setAttribute("RevAmount", revAmount);
                AdfFacesContext.getCurrentInstance().addPartialTarget(revLineAmount);
                AdfFacesContext.getCurrentInstance().addPartialTarget(revLineAmount1);  
                contractLineRow.setAttribute("VarQuantity", varQ);
                AdfFacesContext.getCurrentInstance().addPartialTarget(varQty);
                contractLineRow.setAttribute("VarUnitPrice", varP);
                AdfFacesContext.getCurrentInstance().addPartialTarget(varPrice);
                contractLineRow.setAttribute("VarAmount", varA);
                AdfFacesContext.getCurrentInstance().addPartialTarget(varAmount);
            }
            
            // Iterator Contract Line--Varation Amount
            RowSetIterator rs=contractLineVO.createRowSetIterator(null);
            float totalVariationLineAmount=0;
            while(rs.hasNext()){
               contractLineRow=rs.next();
               float varLineAmount=contractLineRow.getAttribute("VarAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("VarAmount").toString());
               totalVariationLineAmount+=varLineAmount;
               System.err.println("-Total Variation Amount-"+totalVariationLineAmount);
            }
            contractVO.getCurrentRow().setAttribute("VariationAmount", totalVariationLineAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(contVariationAmount);
            
            
            // Iterator Contract Line--Revised Amount
            RowSetIterator revRs=contractLineVO.createRowSetIterator(null);
            float totalRevisedLineAmount=0;
            float totOAmt=0;
            float totRAmt=0;
            while(revRs.hasNext()){
               contractLineRow=revRs.next();
               float varRevisedAmount=contractLineRow.getAttribute("RevAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("RevAmount").toString());
               if(varRevisedAmount==0){
                   float oriAmt=contractLineRow.getAttribute("OrigAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigAmount").toString());
                   totOAmt=totOAmt+oriAmt;
               }else{
                   float revAmt=contractLineRow.getAttribute("RevAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("RevAmount").toString());
                   totRAmt=totRAmt+revAmt;
               }
               totalRevisedLineAmount=totOAmt+totRAmt;
               System.err.println("-Total Revised Amount-"+totalRevisedLineAmount);
            }
            contractVO.getCurrentRow().setAttribute("RevisedContractAmount", totalRevisedLineAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(contRevisedAmount);
            
            
            // Provisional Sum Calculation--setting Total contract sum
            if(provisionalSum!=null){
               float revisedAmount=contractVO.getCurrentRow().getAttribute("RevisedContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("RevisedContractAmount").toString());   
               float provisionalSum=contractVO.getCurrentRow().getAttribute("ProvisionalSum")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ProvisionalSum").toString());   
                float tcs=revisedAmount+provisionalSum;
                contractVO.getCurrentRow().setAttribute("TotalContractSum", tcs);
                AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);   
            }else{
                float revisedAmount=contractVO.getCurrentRow().getAttribute("RevisedContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("RevisedContractAmount").toString());   
                contractVO.getCurrentRow().setAttribute("TotalContractSum", revisedAmount);
                AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);   
            }
            
        }
    }
/**@@ Revised Price Calculation @@**/

    public void onChangeRevPrice(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null&&revQty.getValue()!=null){
            //Getting Contract Line==Original Qty , Price  and Amount
            float oriQty=contractLineRow.getAttribute("OrigQuantity")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigQuantity").toString());
            float oriPrice=contractLineRow.getAttribute("OrigUnitPrice")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigUnitPrice").toString());
            float oriAmount=contractLineRow.getAttribute("OrigAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigAmount").toString());
            
            // Calculating Contract Line--Revised Qty , Price and Amount
            float reQty=revQty.getValue()==null?0:Float.parseFloat(revQty.getValue().toString());  
            float rePrice=valueChangeEvent.getNewValue()==null?0:Float.parseFloat(valueChangeEvent.getNewValue().toString());  
            float revAmount=reQty*rePrice;
            contractLineRow.setAttribute("RevAmount", revAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(revLineAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(revLineAmount1);
            
            // Setting Contract Line--Varation field
            float varQ=reQty-oriQty;
            contractLineRow.setAttribute("VarQuantity", varQ);
            AdfFacesContext.getCurrentInstance().addPartialTarget(varQty);
            float varP=rePrice-oriPrice;
            contractLineRow.setAttribute("VarUnitPrice", varP);
            AdfFacesContext.getCurrentInstance().addPartialTarget(varPrice);
            float varA=revAmount-oriAmount;
            contractLineRow.setAttribute("VarAmount", varA);
            AdfFacesContext.getCurrentInstance().addPartialTarget(varAmount);
            
            // Iterator Contract Line--Varation Amount
            RowSetIterator rs=contractLineVO.createRowSetIterator(null); 
            float totalVariationLineAmount=0;
            while(rs.hasNext()){
                contractLineRow=rs.next();
                float varLineAmount=contractLineRow.getAttribute("VarAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("VarAmount").toString());
                totalVariationLineAmount+=varLineAmount;
                System.err.println("-Total Variation Amount-"+totalVariationLineAmount);
            }
            contractVO.getCurrentRow().setAttribute("VariationAmount", totalVariationLineAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(contVariationAmount);
            
            // Iterator Contract Line--Revised Amount
               RowSetIterator revRs=contractLineVO.createRowSetIterator(null); 
               float totalRevisedLineAmount=0;
               float totOAmt=0;
               float totRAmt=0;
               while(revRs.hasNext()){
                   contractLineRow=revRs.next();
                   float varRevisedAmount=contractLineRow.getAttribute("RevAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("RevAmount").toString());
                   if(varRevisedAmount==0){
                       float oriAmt=contractLineRow.getAttribute("OrigAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigAmount").toString());
                       totOAmt=totOAmt+oriAmt;
                   }else{
                       float revAmt=contractLineRow.getAttribute("RevAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("RevAmount").toString());
                       totRAmt=totRAmt+revAmt;
                   }
                   totalRevisedLineAmount=totOAmt+totRAmt;
                   System.err.println("-Total Revised Amount-"+totalRevisedLineAmount);
               }
               contractVO.getCurrentRow().setAttribute("RevisedContractAmount", totalRevisedLineAmount);
               AdfFacesContext.getCurrentInstance().addPartialTarget(contRevisedAmount);
            
            
            // Provisional Sum Calculation--setting Total contract sum
             if(provisionalSum!=null){
                 float revisedAmount=contractVO.getCurrentRow().getAttribute("RevisedContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("RevisedContractAmount").toString());   
                 float provisionalSum=contractVO.getCurrentRow().getAttribute("ProvisionalSum")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ProvisionalSum").toString());   
                 float tcs=revisedAmount+provisionalSum;
                 contractVO.getCurrentRow().setAttribute("TotalContractSum", tcs);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);   
             }
            
        }else{
            
            //Getting Contract Line==Original Qty , Price  and Amount
            float oriQty=contractLineRow.getAttribute("OrigQuantity")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigQuantity").toString());
            float oriPrice=contractLineRow.getAttribute("OrigUnitPrice")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigUnitPrice").toString());
            float oriAmount=contractLineRow.getAttribute("OrigAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigAmount").toString());
            
            // Calculating Contract Line--Revised Qty , Price and Amount
            float reQty=revQty.getValue()==null?0:Float.parseFloat(revQty.getValue().toString());  
            float rePrice=valueChangeEvent.getNewValue()==null?0:Float.parseFloat(valueChangeEvent.getNewValue().toString());  
            float revAmount=reQty*rePrice;
            if(revAmount==0){
                contractLineRow.setAttribute("RevAmount", "");
                AdfFacesContext.getCurrentInstance().addPartialTarget(revLineAmount);
                AdfFacesContext.getCurrentInstance().addPartialTarget(revLineAmount1);    
                contractLineRow.setAttribute("VarQuantity", "");
                AdfFacesContext.getCurrentInstance().addPartialTarget(varQty);    
                contractLineRow.setAttribute("VarUnitPrice", "");
                AdfFacesContext.getCurrentInstance().addPartialTarget(varPrice);    
                contractLineRow.setAttribute("VarAmount", "");
                AdfFacesContext.getCurrentInstance().addPartialTarget(varAmount);   
            }else{
                float varQ=reQty-oriQty;
                float varP=rePrice-oriPrice;
                float varA=revAmount-oriAmount;
                contractLineRow.setAttribute("RevAmount", revAmount);
                AdfFacesContext.getCurrentInstance().addPartialTarget(revLineAmount);
                AdfFacesContext.getCurrentInstance().addPartialTarget(revLineAmount1);
                contractLineRow.setAttribute("VarQuantity", varQ);
                AdfFacesContext.getCurrentInstance().addPartialTarget(varQty);
                contractLineRow.setAttribute("VarUnitPrice", varP);
                AdfFacesContext.getCurrentInstance().addPartialTarget(varPrice);
                contractLineRow.setAttribute("VarAmount", varA);
                AdfFacesContext.getCurrentInstance().addPartialTarget(varAmount);
            }
            
            
            // Iterator Contract Line--Varation Amount
            RowSetIterator rs=contractLineVO.createRowSetIterator(null); 
            float totalVariationLineAmount=0;
            while(rs.hasNext()){
                contractLineRow=rs.next();
                float varLineAmount=contractLineRow.getAttribute("VarAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("VarAmount").toString());
                totalVariationLineAmount+=varLineAmount;
                System.err.println("-Total Variation Amount-"+totalVariationLineAmount);
            }
            contractVO.getCurrentRow().setAttribute("VariationAmount", totalVariationLineAmount);
            AdfFacesContext.getCurrentInstance().addPartialTarget(contVariationAmount);
            
            // Iterator Contract Line--Revised Amount
               RowSetIterator revRs=contractLineVO.createRowSetIterator(null); 
               float totalRevisedLineAmount=0;
               float totOAmt=0;
               float totRAmt=0;
               while(revRs.hasNext()){
                   contractLineRow=revRs.next();
                   float varRevisedAmount=contractLineRow.getAttribute("RevAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("RevAmount").toString());
                   if(varRevisedAmount==0){
                       float oriAmt=contractLineRow.getAttribute("OrigAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("OrigAmount").toString());
                       totOAmt=totOAmt+oriAmt;
                   }else{
                       float revAmt=contractLineRow.getAttribute("RevAmount")==null?0:Float.parseFloat(contractLineRow.getAttribute("RevAmount").toString());
                       totRAmt=totRAmt+revAmt;
                   }
                   totalRevisedLineAmount=totOAmt+totRAmt;
                   System.err.println("-Total Revised Amount-"+totalRevisedLineAmount);
               }
               contractVO.getCurrentRow().setAttribute("RevisedContractAmount", totalRevisedLineAmount);
               AdfFacesContext.getCurrentInstance().addPartialTarget(contRevisedAmount);
            
            
            // Provisional Sum Calculation--setting Total contract sum
             if(provisionalSum!=null){
                 float revisedAmount=contractVO.getCurrentRow().getAttribute("RevisedContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("RevisedContractAmount").toString());   
                 float provisionalSum=contractVO.getCurrentRow().getAttribute("ProvisionalSum")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ProvisionalSum").toString());   
                 float tcs=revisedAmount+provisionalSum;
                 contractVO.getCurrentRow().setAttribute("TotalContractSum", tcs);
                 AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);   
             }else{
                float revisedAmount=contractVO.getCurrentRow().getAttribute("RevisedContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("RevisedContractAmount").toString());   
                contractVO.getCurrentRow().setAttribute("TotalContractSum", revisedAmount);
                AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);   
            }
             
        }
    }


    public void setContTotalSum(RichInputText contTotalSum) {
        this.contTotalSum = contTotalSum;
    }

    public RichInputText getContTotalSum() {
        return contTotalSum;
    }

    public void setProvisionalSum(RichInputText provisionalSum) {
        this.provisionalSum = provisionalSum;
    }

    public RichInputText getProvisionalSum() {
        return provisionalSum;
    }

    public void onChangeProvisionSum(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue()!=null){
            if(contractAmount.getValue()==null){
                if(contRevisedAmount.getValue()==null){
                    contractVO.getCurrentRow().setAttribute("TotalContractSum", valueChangeEvent.getNewValue());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);
                }else{
                    
                }
                
            }else if(contractAmount.getValue()!=null){
                    if(contRevisedAmount.getValue()==null){
                        float contractAmount=contractVO.getCurrentRow().getAttribute("ContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ContractAmount").toString());   
                        float provisionalSum=Float.parseFloat(valueChangeEvent.getNewValue().toString());
                        float tcs=contractAmount+provisionalSum;
                        contractVO.getCurrentRow().setAttribute("TotalContractSum", tcs);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);
                    }else if(contRevisedAmount.getValue()!=null){
                        float revisedAmount=contractVO.getCurrentRow().getAttribute("RevisedContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("RevisedContractAmount").toString());   
                        float provisionalSum=Float.parseFloat(valueChangeEvent.getNewValue().toString());
                        float tcs=revisedAmount+provisionalSum;
                        contractVO.getCurrentRow().setAttribute("TotalContractSum", tcs);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);
                    }
            }
            
        }else{
            
            if(contractAmount.getValue()==null){
                if(contRevisedAmount.getValue()==null){
                    contractVO.getCurrentRow().setAttribute("TotalContractSum", valueChangeEvent.getNewValue());
                    AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);
                }else{
                    
                }
                
            }else if(contractAmount.getValue()!=null){
                    if(contRevisedAmount.getValue()==null){
                        float contractAmount=contractVO.getCurrentRow().getAttribute("ContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("ContractAmount").toString());   
                        float provisionalSum=Float.parseFloat(valueChangeEvent.getNewValue().toString());
                        float tcs=contractAmount+provisionalSum;
                        contractVO.getCurrentRow().setAttribute("TotalContractSum", tcs);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);
                    }else if(contRevisedAmount.getValue()!=null){
                        float revisedAmount=contractVO.getCurrentRow().getAttribute("RevisedContractAmount")==null?0:Float.parseFloat(contractVO.getCurrentRow().getAttribute("RevisedContractAmount").toString());   
                        float provisionalSum=Float.parseFloat(valueChangeEvent.getNewValue().toString());
                        float tcs=revisedAmount+provisionalSum;
                        contractVO.getCurrentRow().setAttribute("TotalContractSum", tcs);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(contTotalSum);
                    }
            }
            
            
        }
    }
}
