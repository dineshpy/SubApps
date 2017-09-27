package Utils;

import java.math.BigDecimal;
import java.sql.SQLException;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jbo.server.DBTransaction;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.internal.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;




public class DBUtils implements java.io.Serializable {
    public static String IN = "IN";
    public static String OUT = "OUT";
    public static String INOUT = "INOUT";
    
    private static final String DATE_UTILS = "Data Base Utility : ";
    public static DBTransaction getDBTransaction(String DataControleName) {
        DBTransaction dbTrans = null;
        try {
            ApplicationModuleImpl appModulObj =
                (ApplicationModuleImpl)ADFUtils.getApplicationModuleForDataControl(DataControleName);
            if (appModulObj != null) {
                dbTrans = appModulObj.getDBTransaction();
            }
        } catch (Exception e) {
            throw new RuntimeException(DATE_UTILS + e.getMessage());
        }
        return dbTrans;
    }

    /**
     * To call any Stored procedure. IN,OUT and INOUT parameters type should be defined.
     * @param con
     * @param stmt
     * @param bindVars
     * @throws SQLException
     */
    public static void callDBStoredProcedure(DBTransaction con, String stmt, Object[][] bindVars) throws SQLException {
        OracleCallableStatement st = null;
        try {
            // setParameters(con); 
            st = (OracleCallableStatement)con.createCallableStatement("{" + stmt + "}", 0);
            if (bindVars != null) {
                for (int z = 0; z < bindVars.length; z++) {
                    int paramType = ((Integer)bindVars[z][2]).intValue();
                    if ((bindVars[z][0]).equals(OUT)) {
                        if (paramType == OracleTypes.ARRAY) {
                            st.registerOutParameter(z + 1, paramType, ((ArrayDescriptor)bindVars[z][1]).getName());
                        } else {
                            st.registerOutParameter(z + 1, paramType);
                        }
                    } else if ((bindVars[z][0]).equals(IN)) {
                        if (paramType == OracleTypes.ARRAY) {
                            if (bindVars[z][1] != null) {
                                st.setArray(z + 1, (ARRAY)bindVars[z][1]);
                            
                            }
                        } else if (paramType == OracleTypes.VARCHAR) {
                            if (bindVars[z][1] != null) {
                                st.setString(z + 1, (String)bindVars[z][1]);
                            } else {
                                st.setObject(z + 1, bindVars[z][1]);
                            }
                        } else if (paramType == OracleTypes.DATE) {
                            if (bindVars[z][1] != null) {
                                st.setDate(z + 1, ((oracle.jbo.domain.Date)bindVars[z][1]).dateValue());
                            } else {
                                st.setObject(z + 1, bindVars[z][1]);
                            }
                        } else if (paramType == OracleTypes.NUMBER) {
                            if (bindVars[z][1] != null) {
                                String strVal = ((oracle.jbo.domain.Number)bindVars[z][1]).stringValue();
                                BigDecimal val = new BigDecimal(strVal);
                                st.setBigDecimal(z + 1, val);
                            } else {
                                st.setObject(z + 1, bindVars[z][1]);
                            }
                        } else {
                            st.setObject(z + 1, bindVars[z][1]);
                        }
                    } else if ((bindVars[z][0]).equals(INOUT)) {
                        st.registerOutParameter(z + 1, paramType);
                        if (paramType == OracleTypes.ARRAY) {
                            if (bindVars[z][1] != null) {
                                st.setArray(z + 1, (ARRAY)bindVars[z][1]);
                            }
                        } else if (paramType == OracleTypes.VARCHAR) {
                            if (bindVars[z][1] != null) {
                                st.setString(z + 1, (String)bindVars[z][1]);
                            }
                        } else if (paramType == OracleTypes.DATE) {
                            if (bindVars[z][1] != null) {
                                st.setDate(z + 1, ((oracle.jbo.domain.Date)bindVars[z][1]).dateValue());
                            }
                        } else if (paramType == OracleTypes.NUMBER) {
                            if (bindVars[z][1] != null) {
                                String strVal = ((oracle.jbo.domain.Number)bindVars[z][1]).stringValue();
                                BigDecimal val = new BigDecimal(strVal);
                                st.setBigDecimal(z + 1, val);
                            }
                        } else {
                            st.setObject(z + 1, bindVars[z][1]);
                        }
                    }
                }
            }
            st.executeUpdate();
            
            if (bindVars != null) {
                int paramType;
                String paramOut = null;
                for (int z = 0; z < bindVars.length; z++) {
                    paramType = ((Integer)bindVars[z][2]).intValue();
                    paramOut = (String)bindVars[z][0];
                        if (paramOut != null && paramOut.indexOf(OUT) != -1 && paramType == OracleTypes.ARRAY) {
                            if (st.getObject(z + 1) instanceof weblogic.jdbc.wrapper.Array)
                                bindVars[z][1] =
                                        (ARRAY)(((weblogic.jdbc.wrapper.Array)st.getObject(z + 1)).unwrap(Class.forName("oracle.sql.ARRAY")));
                            else {
                                bindVars[z][1] = (ARRAY)st.getARRAY(z + 1);
                                
                            }
                            
                    
                    } else if (paramOut != null && paramOut.indexOf(OUT) != -1) {
                        bindVars[z][1] = st.getObject(z + 1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }
   

   
}
