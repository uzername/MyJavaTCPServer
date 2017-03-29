package myserverlistener.clientnetworkcode;
// http://www.javaworld.com/article/2853780/core-java/socket-programming-for-scalable-systems.html
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
//initSocket -> writeMessage -> closeSocket
public class TraditionalClient {
    /*public*/private Socket socketClient; //better not to fiddle with this!
    public Double timeoutMs; //timeout to stop waiting response
    public void initSocket(String serverPath, int portAddr) throws IOException {
        socketClient = new Socket( serverPath, portAddr );
        timeoutMs = 1000.0;
        
    }
    public String writeMessage(String msg) throws IOException {
            PrintStream out = new PrintStream( socketClient.getOutputStream() );
            BufferedReader in = new BufferedReader( new InputStreamReader( socketClient.getInputStream() ) );
            socketClient.setSoTimeout(timeoutMs.intValue());
            
            out.println( msg );
           // out.println();

            // Read data from the server until we finish reading the document
            String line = in.readLine();
            while( line != null )
            {
                System.out.println( line );
                line = in.readLine();
            }

            // Close our streams
            in.close();
            out.close();
            return line;
    }
    public void closeSocket() throws IOException {
        socketClient.close();
    }
}
