package myserverlistener;
// http://www.darksleep.com/player/DialogExample/
// http://www.darksleep.com/player/DialogExample/TestTheDialog.java.html
// http://www.darksleep.com/player/DialogExample/CustomDialog.java.html
import java.awt.BorderLayout;
import javax.swing.JDialog; 
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.InputVerifier;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

public class CustomDialog2 extends JDialog implements ActionListener {
    private JPanel upperPanel = null;
    private JPanel myPanel = null;
    private JButton yesButton = null;
    private JButton noButton = null;
    private JFormattedTextField portField = null;
    
    private boolean answer = false;
    private Integer portValue;
    private JTextField hostField;
    
    public boolean getAnswer() { return answer; }

    public CustomDialog2(JFrame frame, boolean modal, String myMessage, String myMessage2, Integer setPort, String setHost) {
        super(frame, modal);
        this.setResizable(false);
        upperPanel = new JPanel();                
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));      
        // http://stackoverflow.com/a/16228698
        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
        format.setGroupingUsed(false);
        
        javax.swing.text.NumberFormatter formatter = new javax.swing.text.NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(10); //who would set port less than 10? way too risky...
        formatter.setMaximum(65535);
        formatter.setAllowsInvalid(false);
        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(true);
        portField = new JFormattedTextField(formatter);
        portField.setValue(setPort);
        
        
        upperPanel.setLayout(new BorderLayout());
        upperPanel.add(portField, BorderLayout.CENTER);
        JLabel msg = new JLabel(myMessage);
        
        getContentPane().add(msg);
        msg.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        getContentPane().add(upperPanel);
        
        JLabel hostMsg = new JLabel(myMessage2);
        getContentPane().add(hostMsg);
        hostMsg.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        hostField = new JTextField(setHost);
        //lowPanel2 = new JPanel(new BorderLayout());
        //lowPanel2.add
        
        myPanel = new JPanel();
        getContentPane().add(myPanel);
        
        yesButton = new JButton("Yes");
        yesButton.addActionListener(this);
        myPanel.add(yesButton); 
        noButton = new JButton("No");
        noButton.addActionListener(this);
        myPanel.add(noButton);  
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(yesButton == e.getSource()) {
            //System.err.println("User chose yes.");
            answer = true;
            DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance();
            decimalFormat.setParseIntegerOnly(true);
            try {
                portValue = decimalFormat.parse(portField.getText() ).intValue();
            } catch (ParseException ex) {
                Logger.getLogger(CustomDialog2.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            setVisible(false);
        }
        else if(noButton == e.getSource()) {
            //System.err.println("User chose no.");
            answer = false;
            setVisible(false);
        }
    }

    /**
     * @return the portValue
     */
    public Integer getPortValue() {
        return portValue;
    }
}
