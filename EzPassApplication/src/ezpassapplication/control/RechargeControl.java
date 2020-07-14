package ezpassapplication.control;

import ezpassapplication.model.CreditCard;
import ezpassapplication.model.Customer;
import ezpassapplication.view.MainWindowsBO;
import java.awt.Window;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class RechargeControl {

    public RechargeControl(ActionEvent evt, String CID, String User, String CNumber, String NM, String EXPDate, String CVV, float AddBal) {
        Customer cus = new Customer(CID);
        CreditCard card = new CreditCard(CNumber, NM, EXPDate, CVV, CID, AddBal);
        float oldBal = cus.getBalance();
        float newBal = oldBal + AddBal;

        if (card.addCreditCard()) { //add credit card transaction first
            if (cus.updateBalance(newBal)) {
                JOptionPane.showMessageDialog(null, "Recharge successful! Your Transaction ID is " + card.getCreditID() + " and your new balance is: " + newBal, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                MainWindowsBO main = new MainWindowsBO(CID, User); //redirect to main windows and close recharge window
                JComponent component = (JComponent) evt.getSource();
                Window win = SwingUtilities.getWindowAncestor(component);
                win.dispose();
            }
        } else { //add credit card will fail if generated credit id is taken
            JOptionPane.showMessageDialog(null, "Error: Recharge failed unexpectly! If this occurs multiple times, please contact help desk.", "Confirmation", JOptionPane.ERROR_MESSAGE);
        }

    }
}
