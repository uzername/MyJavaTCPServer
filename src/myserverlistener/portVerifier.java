package myserverlistener;
// UNUSED. instead Formatter is being used

// https://docs.oracle.com/javase/7/docs/api/javax/swing/InputVerifier.html
// https://docs.oracle.com/javase/tutorial/uiswing/misc/focus.html#inputVerification
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.JComponent;

public class portVerifier extends javax.swing.InputVerifier {

    @Override
    public boolean verify(JComponent input) {
        javax.swing.JTextField tf = (javax.swing.JTextField) input;
        boolean wasValid = true;
        int numPeriods = 0;
        DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance();
        decimalFormat.setParseIntegerOnly(true);
        try {
                numPeriods = decimalFormat.parse(tf.getText()).intValue();
            } catch (ParseException pe) {
                wasValid = false;
            }
        return wasValid;
    }
    @Override
    public boolean shouldYieldFocus(JComponent input) {
            boolean inputOK = verify(input);
            if (inputOK) {
                return true;
            } else {
                java.awt.Toolkit.getDefaultToolkit().beep();
                return false;
            }
        }
}
