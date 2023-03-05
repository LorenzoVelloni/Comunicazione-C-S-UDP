package ClientServerUDP;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
/**
 *
 * @author LV
 */
public class Server {
    public static void main(String args[]) throws Exception {
        // Indirizzo di multicast su cui inviare i messaggi
        InetAddress group = InetAddress.getByName("224.0.0.1");
        // Porta su cui inviare i messaggi
        int port = 6789;

        // Crea una MulticastSocket associata alla porta specificata
        MulticastSocket multicastSocket = new MulticastSocket(port);

        // Aggiunge il server al gruppo di multicast
        multicastSocket.joinGroup(group);

        // Buffer di spedizione e ricezione dei pacchetti UDP.
        byte[] bufferIN = new byte[1024];
        byte[] bufferOUT = new byte[1024];

        // Stampa un messaggio di avvio del server.
        System.out.println("SERVER avviato...");

        // Ciclo principale del server.
        while (true) {
            // Definisce un pacchetto UDP per la ricezione dei dati dal client.
            DatagramPacket receivePacket = new DatagramPacket(bufferIN, bufferIN.length);

            // Attende la ricezione di un pacchetto dal client.
            multicastSocket.receive(receivePacket);

            // Converte i dati ricevuti in una stringa.
            String ricevuto = new String(receivePacket.getData());

            // Determina la lunghezza del messaggio ricevuto.
            int numCaratteri = receivePacket.getLength();

            // Elimina eventuali caratteri in eccesso nella stringa ricevuta.
            ricevuto = ricevuto.substring(0, numCaratteri);

            // Stampa il messaggio ricevuto.
            System.out.println("RICEVUTO: " + ricevuto);

            // Prepara la stringa da inviare al client.
            String daSpedire = ricevuto.toUpperCase();

            // Converte la stringa in un array di byte.
            bufferOUT = daSpedire.getBytes();

            // Crea un pacchetto UDP per l'invio dei dati al client.
            DatagramPacket sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, group, port);

            // Invia il pacchetto al client.
            multicastSocket.send(sendPacket);

            // Controlla se il messaggio ricevuto è "fine" per terminare il server.
            if (ricevuto.equals("fine")) {
                System.out.println("SERVER IN CHIUSURA. Buona serata.");
                break;
            }
        }

        // Rimuove il server dal gruppo di multicast
        multicastSocket.leaveGroup(group);

        // Chiude la socket del server.
        multicastSocket.close();
    }
}
