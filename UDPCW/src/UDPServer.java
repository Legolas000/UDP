import java.io.IOException;
import java.net.*;


public class UDPServer implements Runnable {
    /**
     * The port where the client is listening.
     */
    private final int clientPort;

    public UDPServer(int clientPort) {
        this.clientPort = clientPort;
    }

    @Override
    public void run() {
        try
        {
        	DatagramSocket serverSocket = new DatagramSocket(clientPort);
        	
        	/* Create buffers to hold sending and receiving data.
            It temporarily stores data in case of communication delays */
            byte[] receivingDataBuffer = new byte[1024];
            byte[] sendingDataBuffer = new byte[1024];
            
            /* Instantiate a UDP packet to store the 
            client data using the buffer for receiving data*/
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            System.out.println("Waiting for a client to connect...");
            
            // Receive data from the client and store in inputPacket
            serverSocket.receive(inputPacket);
            
            // Printing out the client sent data
            String receivedData = new String(inputPacket.getData());
            System.out.println("Sent from the client: "+receivedData);
            
            /* 
            * Convert client sent data string to upper case,
            * Convert it to bytes
            *  and store it in the corresponding buffer. */
            sendingDataBuffer = receivedData.toUpperCase().getBytes();
            
            // Obtain client's IP address and the port
            InetAddress senderAddress = inputPacket.getAddress();
            int senderPort = inputPacket.getPort();
            
            // Create new UDP packet with data to send to the client
            DatagramPacket outputPacket = new DatagramPacket(
              sendingDataBuffer, sendingDataBuffer.length,
              senderAddress,senderPort
            );
            
            // Send the created packet to client
            serverSocket.send(outputPacket);
            // Close the socket connection
            serverSocket.close();
        }
        catch (SocketException e) 
        {
            e.printStackTrace();
        } 
        catch (UnknownHostException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}