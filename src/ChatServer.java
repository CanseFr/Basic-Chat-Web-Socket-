import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    static ArrayList<String> userNames = new ArrayList<>();
    static ArrayList<PrintWriter> printWriters = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        System.out.println("En attente du client ... ");
        ServerSocket ss = new ServerSocket(9800);

        while (true){
            Socket soc = ss.accept();
            System.out.println("Connection établie");
            ConversationHandler handler = new ConversationHandler(soc);
            handler.start();
        }

    }

}
