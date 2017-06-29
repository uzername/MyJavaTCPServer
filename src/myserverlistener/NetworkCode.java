package myserverlistener;
/**
 * This code is a java listener on TCP connection (actually a wrapper)
 * see https://docs.oracle.com/javase/tutorial/networking/sockets/readingWriting.html
 * see (might be useful too) https://docs.oracle.com/javase/tutorial/networking/sockets/examples/EchoClient.java 
 * see (that's the main part of code) https://docs.oracle.com/javase/tutorial/networking/sockets/examples/EchoServer.java
 * 280617 begin
 * see (reading BYTES, but not strings entirelly) https://stackoverflow.com/questions/9520911/java-sending-and-receiving-file-byte-over-sockets
 * 280617 end
 * @author Ivan
 */

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkCode extends Thread {
    private Integer portNumber;
    private Boolean keepListening;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    //read data from socket
    private BufferedReader in;
    // 280617 begin
    private InputStream in2;
    // 280617 end
    /**
     * @return the portNumber
     */
    public Integer getPortNumber() {
        return portNumber;
    }
    /**
     * @param portNumber the portNumber to set
     */
    public void setPortNumber(Integer portNumber) {
        this.portNumber = portNumber;
    }
    
    
    public void initConnection() throws IOException {
        
             this.serverSocket = new ServerSocket(this.portNumber);
             this.clientSocket = serverSocket.accept();
             //PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);        
             
             in = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()) );
             in2 = clientSocket.getInputStream();
            //Logger.getLogger(NetworkCode.class.getName()).log(Level.SEVERE, null, ex);
            keepListening = true;
    }
    
    @Override
    public void run() {
        char[] cbuf = null;
        while (keepListening) {
            try {       
                //commenting out these 2 lines and adding next lines
                // 280617 begin
                    //in.read(cbuf);
                    //this.processBuffer(cbuf);
                // 280617 end
                // 280617 begin
                byte[] bufferok = new byte[8192];
                int count;
                while ( (count=in2.read(bufferok)) > 0 ) {
                    processBuffer2(bufferok, count);
                }
                // 280617 end
                
            } catch (IOException ex) {
                Logger.getLogger(NetworkCode.class.getName()).log(Level.SEVERE, null, ex);
                keepListening = false;
            }
        }
    }

    /**
     * @return the keepListening
     */
    public Boolean getKeepListening() {
        return keepListening;
    }

    /**
     * @param keepListening the keepListening to set
     */
    public void setKeepListening(Boolean keepListening) {
        this.keepListening = keepListening;
    }
    
    private void processBuffer(char[] cbuf) {
        Logger.getLogger(NetworkCode.class.getName()).log( Level.INFO, String.valueOf(cbuf) );
    }
    // 280617 begin
    //added this routine
    private void processBuffer2(byte[] cbuf, int len) {
        String byteString = "";
        for (int i=0; i<len; i++) {
            byteString += "["+String.format("%02x", cbuf[i])+"]"+(char)cbuf[i];
        }
        Logger.getLogger(NetworkCode.class.getName()).log( Level.INFO, byteString );
    }
    // 280617 end
}
