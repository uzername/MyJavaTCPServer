package myserverlistener;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ScrollPaneConstants;

import myserverlistener.multithreadcode.*;
import myserverlistener.servicepkg.TextAreaHandler;

public class MainJFrame extends javax.swing.JFrame {

    private java.awt.GridBagConstraints c = new java.awt.GridBagConstraints(); 
    private java.awt.GridBagConstraints c2 = new java.awt.GridBagConstraints(); 
    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    /**
     * Creates new form MainJFrame
     */
    public MainJFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     * 
     * JOVAN SAYS: disregard that, better do it by hand
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        this.setPreferredSize(new java.awt.Dimension(640, 480));
        //jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
//        jButton1 = new javax.swing.JButton();
            java.awt.Container pane = this.getContentPane();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
            pane.setLayout(layout);
        jScrollPane1.setViewportView(jEditorPane1);
        jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        //javax.swing.JButton button;
        GridBagConstraints c = new GridBagConstraints();
	if (shouldFill) {
	//natural height, maximum width
	c.fill = GridBagConstraints.HORIZONTAL;
	}

	//button = new javax.swing.JButton("Button 1");
	if (shouldWeightX) {
	c.weightx = 0.5;
	}
/*
	c.fill = GridBagConstraints.HORIZONTAL;
	c.gridx = 0;
	c.gridy = 0;
	pane.add(button, c);

	button = new javax.swing.JButton("Button 2");
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.5;
	c.gridx = 1;
	c.gridy = 0;
	pane.add(button, c);

	button = new javax.swing.JButton("Button 3");
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 0.5;
	c.gridx = 2;
	c.gridy = 0;
	pane.add(button, c);
*/
	//button = new javax.swing.JButton("Long-Named Button 4");
	c.fill = GridBagConstraints.BOTH;
	c.ipady = 40;      //make this component tall
	c.weightx = 0.9;
        c.weighty=7.0;
	c.gridwidth = 3;
	c.gridx = 0;
	c.gridy = 1;
	pane.add(jScrollPane1, c);
        //connect-disconnect button
	button = new javax.swing.JButton("Запуск");
	c.fill = GridBagConstraints.NONE;
	c.ipady = 0;       //reset to default
	c.weighty = 0.03;   //request no extra vertical space
	c.anchor = GridBagConstraints.PAGE_END; //bottom of space
	c.insets = new Insets(1,0,0,0);  //top padding
	c.gridx = 1;       //aligned with button 2
	c.gridwidth = 1;   //2 columns wide
	c.gridy = 2;       //third row
        c.weightx=0.5;
	pane.add(button, c);
        
        javax.swing.JFrame piercingClosure = this;
        button.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serverStarted == false) {
                    //show dialog with port selection
                    CustomDialog myDialog = new CustomDialog(piercingClosure, true, "Порт", usedPort);
                    if (myDialog.getAnswer()) {
                        //start server with provided port
                        Integer inpPort = myDialog.getPortValue();
                        startServerDispatcher(inpPort);
                        usedPort = inpPort;
                        
                    }
                    
                    
                } else {
                   stopServerDispatcher();
                   /*
                   serverStarted =false;
                   button.setText("Запуск");
                   */
                }
            }
        } );

        configureLogger();
        
        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
        
        
        //java.util.logging.
    }
    
    public void startServerDispatcher(Integer portAddr) {
        //see at the end: http://tutorials.jenkov.com/java-multithreaded-servers/multithreaded-server.html
        try {
            serverDispatcher = new MultiThreadedServer(portAddr);
            new Thread(serverDispatcher).start();
        } catch (Exception e) {
            Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, "Виникла проблема при запуску сервера: "+e.getMessage());
            serverStarted = false;
            button.setText("Запуск");
            return;
        }
        
        Logger.getLogger(MainJFrame.class.getName()).log(Level.INFO, "Сервер запущено, порт: "+portAddr.toString());
        serverStarted = true;
        button.setText("Стоп");
    }
    
    public void stopServerDispatcher() {
        try {
            serverDispatcher.stop();
        } catch (Exception e) {
            Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, "Виникла проблема при зупинці сервера: "+e.getMessage());
        } finally {
            serverStarted =false;
            button.setText("Запуск"); 
        }
        Logger.getLogger(MainJFrame.class.getName()).log(Level.INFO, "Сервер зупинено");
    }
    public void configureLogger() {
        //configure logger. mind jEditorPane1 not null
        TextAreaHandler textAreaHandler = new TextAreaHandler();
        textAreaHandler.setTextArea(this.jEditorPane1);
        Logger.getLogger(MainJFrame.class.getName()).addHandler(textAreaHandler);
        Logger.getLogger(MultiThreadedServer.class.getName()).addHandler(textAreaHandler);
        Logger.getLogger(WorkerRunnable.class.getName()).addHandler(textAreaHandler);
        /*
        Logger.getLogger(MainJFrame.class.getName()).log(Level.INFO,  "ROLL");
        Logger.getLogger(MainJFrame.class.getName()).log(Level.INFO,  "ROLL2");
        Logger.getLogger(MainJFrame.class.getName()).log(Level.INFO,  "ROLL3");
        */
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button;
    private javax.swing.JTextPane jEditorPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    private Integer usedPort = 9000;
    private Boolean serverStarted = false;
    MultiThreadedServer serverDispatcher;
}
