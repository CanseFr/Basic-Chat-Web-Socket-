import java.io.*;
import java.net.Socket;

/**
 * Conversation Handler extends de thread permet d'envoyer une ligne d'execution depuis le serveur prenant en paramettre
 * le ServerSocket coté serveur
 * @out     - 1 -> Writing data to socket output stream
 * @pw      - 2 -> Writing data to a file
 * @true    - 3 -> true de FileWriter permet d'ajouter au fichier, sans il efface le contenu et remplace par de nouvelle valeurs
 */
public class ConversationHandler  extends Thread{

    Socket socket ;
    BufferedReader in ;
    PrintWriter out ; // - 1
    String name ;
    PrintWriter pw ; // - 2
    static FileWriter fw ;
    static BufferedWriter bw ;

    public ConversationHandler(Socket socket) throws IOException{
        this.socket = socket;
        fw = new FileWriter("C:\\Users\\julie\\OneDrive\\Documents\\Java\\JavaSocket A.Redkar\\1 Into_TestCo\\ChatApplication\\ChatServer-logs.txt", true); // -3
        bw = new BufferedWriter(fw);
        pw = new PrintWriter(bw, true);
    }

    /**
     * Run methode permet de venir questionner l'utilisateur pour renseigner Pseudo et verif serveur
     *@getInputStream     -1 -> Recuperation
     *@InputStreamReader  -1* -> Traduction
     *@BufferedReader     -1** -> Memoir tampon
     *@getOutputStream    -2 -> Ecrire byte sur la socket
     *@PrintWriter        -2* -> Envoyer element vers socket
     */
    public void run(){

        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // -1 , 1*, 1**
            out = new PrintWriter(socket.getOutputStream(), true); // -2, 2*

            int count = 0 ;
            while (true){

                if (count > 0){out.println("NAMEALREADYEXIST");}

                else {out.println("NAMEREQUIRED");}

                name = in.readLine();

                if (name == null){return;}

                if ( !ChatServer.userNames.contains(name)){
                    ChatServer.userNames.add(name);
                    break;
                }
                count++;
            }

            out.println("NAMEACCEPTED" + name);
            ChatServer.printWriters.add(out);

            pw.println("IP : " + socket);

            while (true){

                String message = in.readLine();
                if ( message == null){return;}

                pw.println(name + " : " + message);

                for (PrintWriter writer : ChatServer.printWriters){
                    writer.println( name + " : " + message );
                }

            }

        } catch (IOException e) {

            throw new RuntimeException(e);

        }
    }
}
