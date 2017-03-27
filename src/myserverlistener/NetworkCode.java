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

public class NetworkCode {
    private Integer portNumber;

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
    
}
