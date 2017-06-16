package myserverlistener.multithreadcode;
// http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html
// http://paultyma.blogspot.com/2008/03/writing-java-multithreaded-servers.html
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
//main class for server. All processing is done in WorkerRunnable
public class MultiThreadedServer implements Runnable{
    //protected: члены класса доступны внутри пакета и в наследниках;
    protected int          serverPort   = 8080;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    
    //private  myserverlistener.clientprocessing.MyClientMsgHandler clientHandler;

    public MultiThreadedServer(int port){
        this.serverPort = port;
    }

    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while(! isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();                
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, (new StringBuilder("CLIENT CONNECTION ACCEPTED:").append(clientSocket.getLocalAddress().getHostAddress())).toString() );
            } catch (IOException e) {
                if(isStopped()) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Сервер зупинено; прийом повідомлень від клієнта не відбувається.");
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
            
            
            
            new Thread(
                new WorkerRunnable(
                    clientSocket, "Multithreaded Server")
            ).start();
        }
        //System.out.println("Server Stopped.") ;
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error closing server");
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Cannot open port "+String.valueOf(serverPort));
            throw new RuntimeException("Cannot open port "+String.valueOf(serverPort), e);
        }
    }

}