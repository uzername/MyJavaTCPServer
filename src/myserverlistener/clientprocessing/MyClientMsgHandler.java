
package myserverlistener.clientprocessing;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
* implements transmission protocol
*/
public class MyClientMsgHandler {
    /**
     * 
     * @param inpLine - string with client request
     * @param contextProcessing - context of processing. contains additional data
     * @return string with response
     */
    public static String processQuery(String inpLine, Object contextProcessing) {
     String resultLine="";
     QueryContext requiredData = (QueryContext) contextProcessing;
                Date date = new Date(requiredData.time);
                DateFormat formatter = new SimpleDateFormat("dd/MMMM/YY HH:mm:ss:SSS");
                String dateFormatted = formatter.format(date);
                resultLine = "HTTP/1.1 200 OK\n\nWorkerRunnable: " + requiredData.SrvrText+ " - " + dateFormatted + "";
     return resultLine;
    }
}
