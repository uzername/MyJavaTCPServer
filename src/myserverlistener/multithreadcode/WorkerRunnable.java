package myserverlistener.multithreadcode;

// http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import myserverlistener.clientprocessing.QueryContext;

/**
 *
 */
public class WorkerRunnable implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;

    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText   = serverText;
    }

    public void run() {
        try {
            InputStream input  = clientSocket.getInputStream();
            BufferedReader in_reader = new BufferedReader(new InputStreamReader( input ));
            OutputStream output = clientSocket.getOutputStream();
            
            //probably reading is done from clientSocket inputStream
            // http://stackoverflow.com/questions/28977308/read-all-lines-with-bufferedreader
            String clientLine; String completeLine=""; 
            clientLine = "";            
            while ((clientLine = in_reader.readLine()) != null) {
                if (clientLine.isEmpty()||clientLine.equals("")) {
                    break;
                } 
                completeLine += "\n"+clientLine;
            }
            
            long time = System.currentTimeMillis();
            QueryContext myContext = new QueryContext();
            myContext.SrvrText = this.serverText;
            myContext.time = time;
            String resultLine = myserverlistener.clientprocessing.MyClientMsgHandler.processQuery(completeLine, myContext);
            output.write(( resultLine ).getBytes());
            
            
            in_reader.close();
            
            output.close();
            input.close();
            System.out.println("Request processed: " + time);
            Logger.getLogger( this.getClass().getName() ).log(Level.INFO, "Request processed: \n Client: "+
                                                              clientSocket.toString()
                                                            //+"\n"+"Data from client: "
                                                            //+String.valueOf(b)+"\n"+"Client line length: "+String.valueOf(numRead)
                                                            );
            Logger.getLogger( this.getClass().getName() ).log(Level.INFO, completeLine);
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}