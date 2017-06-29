// http://stackoverflow.com/a/10787377
package myserverlistener.servicepkg;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
// 280617 begin 
import myserverlistener.Globale;
// 280617 end
        
public class TextAreaHandler extends java.util.logging.StreamHandler {

    private JTextPane textArea;

    @Override
    public void publish(final LogRecord record) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                // 280617 begin
                    //do nothing while this global variable is busy
                    while (Globale.LoggingUsed.get() == true) { ; }
                    Globale.LoggingUsed.set(true);
                // 280617 end
                        
                StringWriter text = new StringWriter();
                PrintWriter out = new PrintWriter(text);
                //out.println(textArea.getText());
                /*
                out.printf("[%s] [Thread-%d]: %s.%s -> %s \n", record.getLevel(),
                        record.getThreadID(), record.getSourceClassName(),
                        record.getSourceMethodName(), record.getMessage());
                */
                
                Date date = new Date(record.getMillis());
                DateFormat formatter = new SimpleDateFormat("dd/MM/YY HH:mm:ss:SSS");
                String dateFormatted = formatter.format(date);
                
                out.printf("[%s] [%s]: -> %s \n", record.getLevel(),
                        dateFormatted, record.getMessage());
                //easily add text: http://stackoverflow.com/a/4059365
                //StyledDocument doc = myserverlistener.MainJFrame.jEditorPane1.getStyledDocument();
                StyledDocument doc = textArea.getStyledDocument();
                System.out.println("Before INSERT");
                try { 
                    doc.insertString(doc.getLength(), text.toString() , null);
                    System.out.println("INSERTED!");
                    Globale.LoggingUsed.set(false);
                } catch (BadLocationException ex) {
                    System.out.println("exception in TextAreaHandler!");
                    Globale.LoggingUsed.set(false);
                }
            }

        });
    }

    public JTextPane getTextArea() {
        return this.textArea;
    }

    /**
     * @param textArea the textArea to set
     */
    public void setTextArea(JTextPane textArea) {
        this.textArea = textArea;
    }

}
