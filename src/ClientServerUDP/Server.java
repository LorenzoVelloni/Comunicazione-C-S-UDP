package ClientServerUDP;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
/**
 *
 * @author LV
 */
public class Server {
    public static void main(String[] args) throws Exception {
        // Crea il socket UDP del server sulla porta 5000
        DatagramSocket serverSocket = new DatagramSocket(5000);
        
        byte[] receiveData = new byte[1024];
        
        while(true) {
            // Prepara il pacchetto di dati in cui verrà inserito il messaggio inviato dal client
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            
            // Riceve il pacchetto dal client
            serverSocket.receive(receivePacket);
            
            // Estrae i dati dal pacchetto ricevuto
            String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            
            // Stampa il messaggio ricevuto dal client
            System.out.println("Messaggio ricevuto dal client: " + message);
            
            // Prepara il pacchetto di dati in cui viene inserito il messaggio di risposta
            byte[] sendData = message.toUpperCase().getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            
            // Invia il pacchetto di dati contenente il messaggio di risposta al client
            serverSocket.send(sendPacket);
            
            // Prepara il pacchetto di dati in cui verrà inserito il messaggio di risposta in multicast
            byte[] multicastData = message.toLowerCase().getBytes();
            InetAddress multicastAddress = InetAddress.getByName("224.0.0.1");
            int multicastPort = 5001;
            DatagramPacket multicastPacket = new DatagramPacket(multicastData, multicastData.length, multicastAddress, multicastPort);
            
            // Invia il pacchetto di dati contenente il messaggio di risposta in multicast
            serverSocket.send(multicastPacket);
        }
    }

    void start() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
