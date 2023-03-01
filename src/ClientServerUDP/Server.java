package ClientServerUDP;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
/**
 *
 * @author LV
 */
class Server
{
  public static void main(String args[]) throws Exception 
  {
    // Creazione del socket del server sulla porta 6789
    DatagramSocket serverSocket = new DatagramSocket(6789);

    // Variabile che tiene traccia dello stato del server
    boolean attivo = true;

    // Buffer per la ricezione e l'invio dei dati
    byte[] bufferIN = new byte[1024];
    byte[] bufferOUT = new byte[1024];

    // Stampa un messaggio di avvio del server
    System.out.println("SERVER avviato...");

    // Ciclo principale del server, in attesa di ricevere dati dai client
    while(attivo)
    {
      // Definizione del datagramma per ricevere dati dal client
      DatagramPacket receivePacket = new DatagramPacket(bufferIN, bufferIN.length);

      // Attesa della ricezione del datagramma dal client
      serverSocket.receive(receivePacket);

      // Analisi del pacchetto ricevuto
      String ricevuto = new String(receivePacket.getData());
      int numCaratteri = receivePacket.getLength();        
      ricevuto = ricevuto.substring(0,numCaratteri); // Rimuove eventuali caratteri di padding

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
      serverSocket.send(sendPacket);

      // Controllo per la terminazione del server (in caso il messaggio ricevuto sia "fine")
      if (ricevuto.equals("fine"))
      {
        System.out.println("SERVER IN CHIUSURA. Buona serata.");
        attivo = false;
      }  
    }

    // Chiusura del socket del server
    serverSocket.close();
  }
}
