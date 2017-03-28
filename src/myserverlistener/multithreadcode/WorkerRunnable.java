package myserverlistener.multithreadcode;

// http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            //BufferedReader in = new BufferedReader(input);
            OutputStream output = clientSocket.getOutputStream();
            long time = System.currentTimeMillis();
            output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " +
this.serverText + " - " +
time +
"").getBytes());
            //probably reading is done from clientSocket inputStream
            /*
            byte[] b = new byte[1];
            int numRead = input.read(b);
            */
            output.close();
            input.close();
            System.out.println("Request processed: " + time);
            Logger.getLogger( this.getClass().getName() ).log(Level.SEVERE, "Request processed: \n Client: "+
                                                              clientSocket.toString()
                                                            //+"\n"+"Data from client: "
                                                            //+String.valueOf(b)+"\n"+"Client line length: "+String.valueOf(numRead)
                                                            );
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}