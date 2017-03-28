package myserverlistener;
/**
 * This code is a java listener on TCP connection (actually a wrapper)
 * see https://docs.oracle.com/javase/tutorial/networking/sockets/readingWriting.html
 * see (might be useful too) https://docs.oracle.com/javase/tutorial/networking/sockets/examples/EchoClient.java 
 * see (that's the main part of code) https://docs.oracle.com/javase/tutorial/networking/sockets/examples/EchoServer.java
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
             in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()) );
            //Logger.getLogger(NetworkCode.class.getName()).log(Level.SEVERE, null, ex);
            keepListening = true;
    }
    
    @Override
    public void run() {
        char[] cbuf = null;
        while (keepListening) {
            try {                
                in.read(cbuf);
                this.processBuffer(cbuf);
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
    
}
