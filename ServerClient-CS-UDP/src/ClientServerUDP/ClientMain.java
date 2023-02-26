package ClientServerUDP;
/**
 *
 * @author LV
 */
public class ClientMain {
    public static void main(String[] args) throws Exception {
        // Crea un'istanza della classe UDPClient
        Client client = new Client();
        
        // Invia un messaggio al server
        String message = "Questo è un messaggio di prova!";
        client.sendMessage(message);
    }
}
