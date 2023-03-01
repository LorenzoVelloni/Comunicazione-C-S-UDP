package ClientServerUDP;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.*;
/**
 *
 * @author   LV
 */
public class Client {
  public static void main(String args[]) throws Exception {
  
    // dichiarazione della porta del server
    int portaServer = 6789;
    
    // dichiarazione dell'indirizzo IP del server
    InetAddress IPServer = InetAddress.getByName("localhost");

    // dichiarazione dei buffer di spedizione e ricezione
    byte[] bufferOUT = new byte[1024];
    byte[] bufferIN = new byte[1024];
    
    // dichiarazione di un buffer di input
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
   
    // creazione del socket del client
    DatagramSocket clientSocket = new DatagramSocket();

    // stampa del messaggio di avviso al client
    System.out.println("Client pronto - inserisci un dato da inviare:");

    // lettura del dato in input dal client
    String daSpedire = input.readLine();
    
    // conversione del dato in un array di byte
    bufferOUT = daSpedire.getBytes();
    
    // creazione del pacchetto da spedire al server
    DatagramPacket sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, IPServer, portaServer);
    
    // invio del pacchetto al server
    clientSocket.send(sendPacket);
 
    // creazione del pacchetto per la ricezione dei dati dal server
    DatagramPacket receivePacket = new DatagramPacket(bufferIN, bufferIN.length);
    
    // ricezione dei dati dal server
    clientSocket.receive(receivePacket);

    // conversione dei dati ricevuti in una stringa
    String ricevuto = new String(receivePacket.getData());

    // elaborazione dei dati ricevuti
    int numCaratteri = receivePacket.getLength();        
    ricevuto = ricevuto.substring(0, numCaratteri); //elimina i caratteri in eccesso 

    // stampa del messaggio ricevuto dal server
    System.out.println("dal SERVER:" + ricevuto);
 
    // chiusura del socket del client
    clientSocket.close();
  }
}
