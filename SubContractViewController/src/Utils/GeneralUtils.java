package Utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;

import oracle.adf.view.rich.util.FacesMessageUtils;

import oracle.jbo.ViewCriteriaManager;
import oracle.jbo.ViewObject;

public class GeneralUtils {
   


//        public void enableDisableSaveCancelIcon(String saveIconId, String cancelIconId, boolean boolval) {
//            RichButton saveRb = (RichButton) JSFUtils.findComponentInRoot(saveIconId);
//            RichButton cancelRb = (RichButton) JSFUtils.findComponentInRoot(cancelIconId);
//            saveRb.setDisabled(boolval);
//            cancelRb.setDisabled(boolval);
//            AdfFacesContext.getCurrentInstance().addPartialTarget(saveRb);
//            AdfFacesContext.getCurrentInstance().addPartialTarget(cancelRb);
//        }

        public void displayMessage(String str, String type) {
            AdfFacesContext adfFacesContext = null;
            adfFacesContext = AdfFacesContext.getCurrentInstance();
            FacesContext ctx = FacesContext.getCurrentInstance();
            FacesMessage fm = null;
            if (type.equalsIgnoreCase("")) {
                fm = FacesMessageUtils.getConfirmationMessage(new FacesMessage(str));
            }
            if (type.equalsIgnoreCase("ERROR")) {
                fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, str, "");
            }
            if (type.equalsIgnoreCase("WARN")) {
                fm = new FacesMessage(FacesMessage.SEVERITY_WARN, str, "");
            }
            if (type.equalsIgnoreCase("INFO")) {
                fm = new FacesMessage(FacesMessage.SEVERITY_INFO, str, "");
            }
            if (type.equalsIgnoreCase("FATAL")) {
                fm = new FacesMessage(FacesMessage.SEVERITY_FATAL, str, "");
            }
            ctx.addMessage(null, fm);
        }

//        public void cascadeDisableChildern(Iterator<UIComponent> children, boolean flag) {
//            if (children != null) {
//                while (children.hasNext()) {
//                    UIComponent childComp = children.next();
//                    childComp.getAttributes().put("disabled", flag);
//                    RequestContext.getCurrentInstance().addPartialTarget(childComp);
//                }
//            }
//        }

//        public String rollbackTable(String Iterator, String tableId) {
//            try {
//                ViewObject vo = ADFUtils.findIterator(Iterator).getViewObject();
//
//                DCIteratorBinding dcBindCont = ADFUtils.findIterator(Iterator);
//                RichTable tab = (RichTable) JSFUtils.findComponentInRoot(tableId);
//
//                if (tab.getEstimatedRowCount() != 0) {
//                    Row curRow = vo.getCurrentRow();
//                    //curRow.refresh(curRow.REFRESH_UNDO_CHANGES | curRow.REFRESH_WITH_DB_FORGET_CHANGES);
//                    Key custMasKey = vo.getCurrentRow().getKey();
//                    OperationBinding operBind = ADFUtils.findOperation("Rollback");
//                    operBind.execute();
//                    dcBindCont.setCurrentRowWithKey(custMasKey.toStringFormat(true));
//                }
//
//            } catch (RowNotFoundException e) {
//            }
//            return null;
//        }

//        public String rollbackTable(String IteratorMaster, String IteratorChild, String tableIdMaster,
//                                    String tableIdChild) {
//
//            ViewObject voMas = ADFUtils.findIterator(IteratorMaster).getViewObject();
//            ViewObject voChild = ADFUtils.findIterator(IteratorChild).getViewObject();
//
//            DCIteratorBinding dcBindContMas = ADFUtils.findIterator(IteratorMaster);
//            DCIteratorBinding dcBindContChild = ADFUtils.findIterator(IteratorChild);
//
//            RichTable tabMas = (RichTable) JSFUtils.findComponentInRoot(tableIdMaster);
//            RichTable tabChild = (RichTable) JSFUtils.findComponentInRoot(tableIdChild);
//
//            if (tabMas.getEstimatedRowCount() != 0) {
//                Key keyMaster = voMas.getCurrentRow().getKey();
//                Key keyChild = null;
//                if (tabChild.getEstimatedRowCount() != 0) {
//                    keyChild = voChild.getCurrentRow().getKey();
//                }
//                OperationBinding operBind = ADFUtils.findOperation("Rollback");
//                operBind.execute();
//                dcBindContMas.setCurrentRowWithKey(keyMaster.toStringFormat(true));
//                if (keyChild != null)
//                    dcBindContChild.setCurrentRowWithKey(keyChild.toStringFormat(true));
//            }
//            return null;
//        }

        public String clearViewCrieteria(String Iterator) {
            try {
                ViewObject custSearchVO = ADFUtils.findIterator(Iterator).getViewObject();
                ViewCriteriaManager custVcman = custSearchVO.getViewCriteriaManager();
                custVcman.clearViewCriterias();
                custSearchVO.executeQuery();
            } catch (NullPointerException e) {
                //            System.err.println("ERROR " + e);
            }
            return null;
        }

//        public void resetAfQuerySearch(String afQueryComponentID) {
//            RichQuery queryComp = (RichQuery) JSFUtils.findComponentInRoot(afQueryComponentID);
//            QueryModel queryModel = queryComp.getModel();
//            QueryDescriptor queryDescriptor = queryComp.getValue();
//            queryModel.reset(queryDescriptor);
//            queryComp.refresh(FacesContext.getCurrentInstance());
//        }

//        public void calendarColor(HashMap activityStyles) {
//            try {
//                HashSet setconf = new HashSet<String>();
//                HashSet setnoconf = new HashSet<String>();
//                HashSet setadm = new HashSet<String>();
//                HashSet setadm1 = new HashSet<String>();
//                setconf.add("HOLIDAY");
//                setnoconf.add("LEAVE");
//                setadm.add("WEEK OFF");
//                setadm1.add("SCHEDULE");
//                activityStyles.put(setconf, CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.RED));
//                activityStyles.put(setnoconf, CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.LAVENDAR));
//                activityStyles.put(setadm, CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.PLUM));
//                activityStyles.put(setadm1, CalendarActivityRamp.getActivityRamp(CalendarActivityRamp.RampKey.SEAWEED));
//            } catch (Exception e) {
//                System.err.println(e);
//            }
//
//        }
        /*
         Table's filter refresh
         */
        public void tableFilterRefresh(RichTable getTableMethod) {
            FilterableQueryDescriptor queryDescriptor = (FilterableQueryDescriptor) getTableMethod.getFilterModel();
            if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null) {
                queryDescriptor.getFilterCriteria().clear();
                getTableMethod.queueEvent(new QueryEvent(getTableMethod, queryDescriptor));
            }
        }

//        public String tableFirst(RichTable id) {
//            CollectionModel tableModel = (CollectionModel) id.getValue();
//            JUCtrlHierBinding adfModel = (JUCtrlHierBinding) tableModel.getWrappedData();
//            DCIteratorBinding dciter = adfModel.getDCIteratorBinding();
//            NavigatableRowIterator nav = dciter.getNavigatableRowIterator();
//            Row newRow = nav.createRow();
//            newRow.setNewRowState(Row.STATUS_INITIALIZED);
//            nav.insertRowAtRangeIndex(0, newRow);
//            dciter.setCurrentRowWithKey(newRow.getKey().toStringFormat(true));
//            return null;
//
//        }


       

}
