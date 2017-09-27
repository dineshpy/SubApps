package Utils;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.el.ELContext;

import javax.el.ExpressionFactory;

import javax.el.ValueExpression;

import javax.faces.context.FacesContext;

public class MailServices {



/**********************Calling EL Expression*****************************************/

    public static Object evaluateEL(String el) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                ELContext elContext = facesContext.getELContext();
                ExpressionFactory expressionFactory =
                facesContext.getApplication().getExpressionFactory();
                ValueExpression exp =
                expressionFactory.createValueExpression(elContext, el,
                Object.class);

                return exp.getValue(elContext);
                }

/**********************double quotes convert into single quotes*****************************************/

    public static String quotesReplace(String htmldata){
            String out=htmldata.replace( "\"",  "'");
            //StringBuilder builder = new StringBuilder("<html> <body>");
            //builder.append(out);
            //builder.append("</body> </html>");
            //System.err.println("----HTML BODY value---"+builder.toString());    
            //return builder.toString();
            System.out.println("---Quotes Replace Output-----: "+out);
            return out;
            }

    /*******************Calling REST ful Services********************************************/
    
    public static String sendNotification(String to, String from, String textBody, String htmlBody, String subject)
                                                    throws MalformedURLException,IOException {
                    
                    String output = null;
                    try {
        
                        URL url = new URL("https://apex-a418962.db.em2.oraclecloudapps.com/apex/bfm/bfmmail/");
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Accept", "application/json");            
                        conn.setRequestProperty( "User-Agent", "Mozilla/5.0" );
                        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");            
                        conn.setDoOutput(true);
                        conn.setDoInput(true);
                        
                        String payload =   "{\"l_to\":\""+to+"\"," +    "\"l_from\":\""+from+"\"," +    "\"l_body\":\""+textBody+"\", " +
                                            "\"l_body_html\":\""+htmlBody+"\", " +     "\"l_subject\":\""+subject+"\"}";
                        conn.setRequestProperty("Content-Length", String.valueOf(payload.getBytes().length));
                        
                        OutputStream writer = conn.getOutputStream();
                        writer.write(payload.getBytes());
                        writer.flush();
                        writer .close();
                        System.out.println(payload);
                        if (conn.getResponseCode() != 200) {
                            throw new RuntimeException("Failed : HTTP error code : " +
                                                       conn.getResponseCode());
                        }

                        BufferedReader br =  new BufferedReader(new InputStreamReader((conn.getInputStream())));


                        System.out.println("Output from Server .... \n");
                        while ((output = br.readLine()) != null) {
                            System.out.println("=======OUTPUT======="+output);
                        }           
                        conn.disconnect();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return output;
                }



//    public void sendMessage(ActionEvent actionEvent) {
//            String to = (String)evaluateEL("#{pageFlowScope.pMessageTo}");
//                    System.err.println("=====TO====="+to);
//                    String from = (String)evaluateEL("#{pageFlowScope.pMessageFrom}");
//                    System.err.println("=====From====="+from);
//                    String subject = (String)evaluateEL("#{pageFlowScope.pMessageSubject}");
//                    System.err.println("=====Subject====="+subject);
//                    String body = (String)evaluateEL("#{pageFlowScope.pMessageBody}");
//                    System.err.println("====body======"+body);
//                    String  convertHtmlBody= (String)evaluateEL("#{pageFlowScope.pMessageBodyhtml}");
//                    String  htmlbody = quotesReplace(convertHtmlBody);
//                    System.err.println("====htmlbody======"+htmlbody);
//                    
//                    try {
//                        sendNotification(to, from, null, htmlbody, subject);
//                    } catch (MalformedURLException e) {
//                    } catch (IOException e) {
//                    }
//        }




}
