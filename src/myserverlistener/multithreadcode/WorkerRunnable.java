package myserverlistener.multithreadcode;

// http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html
// read BYTES from Input Stream of clientSocket: https://stackoverflow.com/questions/9520911/java-sending-and-receiving-file-byte-over-sockets
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
            // 280617 begin
            //comment out and add the next block
//            clientLine = "";            
//            while ((clientLine = in_reader.readLine()) != null) {
//                if (clientLine.isEmpty()||clientLine.equals("")) {
//                    break;
//                } 
//                completeLine += "\n"+clientLine;
//            }
            // 280617 end
            // 280617 begin
                
                byte[] buffer2 = new byte[8192];
                int count;
                while ( (count=input.read(buffer2)) > 0 ) {
                    completeLine+=processBuffer2(buffer2, count); 
                    if (count<8191) { break; }
                }
            // 280617 end
            long time = System.currentTimeMillis();
            QueryContext myContext = new QueryContext();
            myContext.SrvrText = this.serverText;
            myContext.time = time;
            String resultLine = myserverlistener.clientprocessing.MyClientMsgHandler.processQuery(completeLine, myContext);
            output.write(( resultLine ).getBytes());
            try {
                Thread.sleep(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkerRunnable.class.getName()).log(Level.SEVERE, null, ex);
            }
            synchronized (this) {
            System.out.println("Request processed: " + time);
            Logger.getLogger(WorkerRunnable.class.getName()).log(Level.SEVERE, "Request processed: " + time);
            Logger.getLogger( this.getClass().getName() ).log(Level.SEVERE, "Request processed: \n Client: "+
                                                              clientSocket.toString()
                                                            //+"\n"+"Data from client: "
                                                            //+String.valueOf(b)+"\n"+"Client line length: "+String.valueOf(numRead)
                                                            );
            //  if (completeLine.length() <= 300) {
                    Logger.getLogger( this.getClass().getName() ).log(Level.SEVERE, completeLine); 
            //  } else {Logger.getLogger( this.getClass().getName() ).log(Level.SEVERE, "!TOO LONG!");}
            }
            in_reader.close();
            output.close();
            input.close();
                  //System.out.println(completeLine);
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
    // 280617 begin
    //added this routine
    private String processBuffer2(byte[] cbuf, int len) {
        String byteString = "";
        for (int i=0; i<len; i++) {
            byteString += "["+String.format("%02x", cbuf[i])+"]"+( (cbuf[i]>=32) ? ((char)cbuf[i]) : '\u2300' );
        }
        return byteString;
    }
    // 280617 end
}