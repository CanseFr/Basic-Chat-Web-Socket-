import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

    /**
     * Creation des attributs d'une fenetre
     *  @chatWindow    -1 -> Creation fenetre
     *  @chatArea    -2 -> Text, raws / col
     */
    static JFrame chatWindow = new JFrame("Chat App"); // -1
    static JTextArea chatArea = new JTextArea(22,40); // -2
    static JTextField textField = new JTextField(40);
    static JLabel blankLabel = new JLabel("         ");
    static JButton sendButton = new JButton("Envoyer");
    static BufferedReader in;
    static PrintWriter out;
    static JLabel nameLabel = new JLabel("          ");


    /**
     * Add et set les attributs a ma fenetre lors de sa creation
     * @setDefaultCloseOperation  -1 -> Close button (kill prog)
     * @setEnabled                -2 -> Permet de desactiver le champ d'ecriture tant que la connetion n'est pas etablished
     * @setEditable               -3 -> Le chat area est uniquement utilisé pour display les dialogues, il faut ne pas pouvoir ecrire dedans
     * @addActionListener         -4 -> Creation depuis le constructeur, du bouton de type ecoute evenement qui lui crée un objet de recuperation
     *                            de textField, ce bouton est relier a mon "sendButton" *-* crée juste avant.
     */
    ChatClient() throws UnknownHostException {

        chatWindow.setLayout(new FlowLayout());

        chatWindow.add(nameLabel);
        chatWindow.add(new JScrollPane(chatArea));
        chatWindow.add(blankLabel);
        chatWindow.add(textField);
        chatWindow.add(sendButton); // *-*

        chatWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // -1
        chatWindow.setSize(475,500);
        chatWindow.setVisible(true);

        textField.setEditable(false);  // -2
        chatArea.setEditable(false);  // -3

        sendButton.addActionListener(new Listener()); // -4 *-*
        textField.addActionListener(new Listener());


    }

    /**
     * Start windows ClientChat + Demande IP + Verif. Pseudo utilisateur
     * @throws Exception
     * @startsWith Car on vas display le substring de la chaine restant *-*
     */
    void startChat()throws Exception{

////        -> Methode Maison
//        InetAddress ip = InetAddress.getLocalHost();
//        String ipAdress = ip.getHostAddress();


////        -> TUTO
        String ipAdress = JOptionPane.showInputDialog(
                        chatWindow,
                        "Entrez votre adresse IP : ",
                        JOptionPane.PLAIN_MESSAGE );

        Socket soc = new Socket(ipAdress, 9800);
        in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        out = new PrintWriter(soc.getOutputStream(), true);

        while (true){

            String str = in.readLine();

            if (str.equals("NAMEREQUIRED")){

                String name = JOptionPane.showInputDialog(
                  chatWindow,
                  "Entrez un Pseudo Unique :",
                  "Pseudo Requis !",
                  JOptionPane.PLAIN_MESSAGE );

                out.println(name);

            } else if ( str.equals("NAMEALREADYEXIST")){

                String name = JOptionPane.showInputDialog(
                        chatWindow,
                        "Entrez un autre Pseudo !",
                        "Ce Pseudo existe deja !",
                        JOptionPane.WARNING_MESSAGE );

                out.println(name);

            } else if (str.startsWith("NAMEACCEPTED")){ // - 1 *-*

                textField.setEditable(true);
                nameLabel.setText("Vous etes connecté sous : " + str.substring(12));// *-*

            } else {

                chatArea.append(str + "\n");

            }
        }
    }



    public static void main(String[] args) throws Exception {

        ChatClient client = new ChatClient();
        client.startChat();

    }
}
