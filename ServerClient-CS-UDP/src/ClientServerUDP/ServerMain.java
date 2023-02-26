package ClientServerUDP;
/**
 *
 * @author LV
 */
public class ServerMain {
    public static void main(String[] args) throws Exception {
        // Crea un'istanza della classe UDPServer
        Server server = new Server();
        
        // Avvia il server
        server.start();
    }
}

