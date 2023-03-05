package ClientServerUDP;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.*;
import java.net.MulticastSocket;
/**
 *
 * @author   LV
 */
public class Client {
    public static void main(String args[]) throws Exception {

        // Dichiarazione della porta e dell'indirizzo Multicast
        int portaMultiCast = 6789;
        String IPServerMultiCast = "224.0.0.1";

        // Dichiarazione della porta del server
        int portaServer = 6790;

        // Dichiarazione dell'indirizzo IP del server
        InetAddress IPServer = InetAddress.getLocalHost();

        // Dichiarazione dei buffer di spedizione e ricezione
        byte[] bufferOUT = new byte[1024];
        byte[] bufferIN = new byte[1024];

        // Dichiarazione di un buffer di input
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        // Creazione della socket Multicast del client
        MulticastSocket clientMultiCastSocket = new MulticastSocket(portaMultiCast);

        // Aggiunta del client al gruppo di Multicast
        InetAddress gruppo = InetAddress.getByName(IPServerMultiCast);
        clientMultiCastSocket.joinGroup(gruppo);

        // Creazione del socket del client per la comunicazione con il server
        DatagramSocket clientSocket = new DatagramSocket();

        // Stampa del messaggio di avviso al client
        System.out.println("Client pronto - inserisci un dato da inviare:");

        // Lettura del dato in input dal client
        String daSpedire = input.readLine();

        // Conversione del dato in un array di byte
        bufferOUT = daSpedire.getBytes();

        // Creazione del pacchetto da spedire al gruppo di Multicast
        DatagramPacket sendMultiCastPacket = new DatagramPacket(bufferOUT, bufferOUT.length, gruppo, portaMultiCast);

        // Invio del pacchetto al gruppo di Multicast
        clientMultiCastSocket.send(sendMultiCastPacket);

        // Creazione del pacchetto per la ricezione dei dati dal server
        DatagramPacket receivePacket = new DatagramPacket(bufferIN, bufferIN.length);

        // Ricezione dei dati dal server
        clientSocket.receive(receivePacket);

        // Conversione dei dati ricevuti in una stringa
        String ricevuto = new String(receivePacket.getData());

        // Elaborazione dei dati ricevuti
        int numCaratteri = receivePacket.getLength();
        ricevuto = ricevuto.substring(0, numCaratteri); // Elimina i caratteri in eccesso

        // Stampa del messaggio ricevuto dal server
        System.out.println("dal SERVER: " + ricevuto);

        // Chiusura dei socket del client
        clientSocket.close();
        clientMultiCastSocket.close();
    }
}
