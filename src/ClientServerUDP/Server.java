package ClientServerUDP;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
/**
 *
 * @author LV
 */
public class Server {
    public static void main(String args[]) throws IOException {
        // Dichiarazione delle porte per la comunicazione unicast e multicast
        int unicastPort = 6789;


        // Creazione delle socket per la comunicazione unicast e multicast
        DatagramSocket unicastSocket = new DatagramSocket(unicastPort);

        // Buffer per la ricezione e l'invio dei dati
        byte[] bufferIN = new byte[1024];
        byte[] bufferOUT = new byte[1024];

        // Variabile che tiene traccia dello stato del server
        boolean attivo = true;

        // Stampa un messaggio di avvio del server
        System.out.println("SERVER avviato...");

        // Ciclo principale del server, in attesa di ricevere dati dai client
        while (attivo) {
            // Definizione del datagramma per ricevere dati dal client
            DatagramPacket receivePacket = new DatagramPacket(bufferIN, bufferIN.length);

            // Attesa della ricezione del datagramma dal client
            if (Math.random() < 0.5) {
                // Ricezione tramite unicast
                unicastSocket.receive(receivePacket);
            } 

            // Analisi del pacchetto ricevuto
            String ricevuto = new String(receivePacket.getData());
            int numCaratteri = receivePacket.getLength();
            ricevuto = ricevuto.substring(0, numCaratteri); // Rimuove eventuali caratteri di padding

            // Stampa del messaggio ricevuto dal client
            System.out.println("RICEVUTO: " + ricevuto);

            // Riconoscimento degli attributi del socket del client (IP e porta)
            InetAddress IPClient = receivePacket.getAddress();
            int portaClient = receivePacket.getPort();

            // Preparazione del messaggio da inviare al client
            String daSpedire = ricevuto.toUpperCase();
            bufferOUT = daSpedire.getBytes();

            // Invio del datagramma al client
            DatagramPacket sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, IPClient, portaClient);
            if (Math.random() < 0.5) {
                // Invio tramite unicast
                unicastSocket.send(sendPacket);
            } 

            // Controllo per la terminazione del server (in caso il messaggio ricevuto sia "fine")
            if (ricevuto.equals("fine")) {
                System.out.println("SERVER IN CHIUSURA. Buona serata.");
                attivo = false;
            }
        }

        // Chiusura delle socket del server
        unicastSocket.close();
    }
}
