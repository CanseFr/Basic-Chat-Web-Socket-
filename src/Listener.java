import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class implementé de (event) ActionListener
 * @ChatClient -1 -> Envoyé sur la qocket les valeurs get dans le text field de client
 */
public class Listener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        ChatClient.out.println((ChatClient.textField.getText())); // -1
        ChatClient.textField.setText("");
    }
}
