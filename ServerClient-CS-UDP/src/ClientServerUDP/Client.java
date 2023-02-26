package ClientServerUDP;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
/**
 *
 * @author   LV
 */
public class Client {
    public static void main(String[] args) throws Exception {
        // Crea il socket UDP del client
        DatagramSocket clientSocket = new DatagramSocket();
        
        // Indirizzo IP e porta del server
        InetAddress serverAddress = InetAddress.getByName("localhost");
        int serverPort = 5000;
        
        // Prepara il messaggio da inviare
        String message = "Ciao, sono un messaggio di prova!";
        byte[] sendData = message.getBytes();
        
        // Crea il pacchetto di dati in cui inserire il messaggio e le informazioni di destinazione
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        
        // Invia il pacchetto al server
        clientSocket.send(sendPacket);
        
        // Prepara il pacchetto di dati in cui verrà inserita la risposta del server
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        
        // Riceve la risposta
            clientSocket.receive(receivePacket);
    
    // Estrae i dati dal pacchetto ricevuto
    String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
    
    // Stampa la risposta del server
    System.out.println("Risposta del server: " + response);
    
    // Indirizzo IP e porta per la comunicazione in multicast
    InetAddress multicastAddress = InetAddress.getByName("224.0.0.1");
    int multicastPort = 5001;
    
    // Crea il socket UDP per la comunicazione in multicast
    DatagramSocket multicastSocket = new DatagramSocket();
    multicastSocket.setBroadcast(true);
    
    // Prepara il messaggio da inviare in multicast
    String multicastMessage = "Questo è un messaggio inviato in multicast!";
    byte[] multicastData = multicastMessage.getBytes();
    
    // Crea il pacchetto di dati in cui inserire il messaggio e le informazioni di destinazione per la comunicazione in multicast
    DatagramPacket multicastPacket = new DatagramPacket(multicastData, multicastData.length, multicastAddress, multicastPort);
    
    // Invia il pacchetto in multicast
    multicastSocket.send(multicastPacket);
    
    // Chiude le connessioni
    clientSocket.close();
    multicastSocket.close();


    void sendMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
