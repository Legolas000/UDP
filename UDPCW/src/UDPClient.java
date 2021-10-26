import java.io.*;
import java.util.*;
import java.net.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPClient implements Runnable
{
	private DatagramSocket socket;
    private InetAddress address;
    private byte[] buf;
    
	private final String ipAddress;
	private final int port;

    public UDPClient(String ipAddress, int port) 
    {
    	this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public void run()
    {
    	try
    	{
    		DatagramSocket clientSocket = new DatagramSocket();
    		
    		Scanner sc = new Scanner(System.in);
    		
    		// Get the IP address of the server
    		//InetAddress IPAddress = InetAddress.getByName("localhost");
    		InetAddress IPAddress = InetAddress.getByName(ipAddress);
    	      
    		// Creating corresponding buffers
    		byte[] sendingDataBuffer = new byte[1024];
    		byte[] receivingDataBuffer = new byte[1024];
    		
    		System.out.println("Enter message : ");
    		String s = sc.next();
    		sendingDataBuffer = s.getBytes();
    		
    		// Creating a UDP packet 
    		DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer,sendingDataBuffer.length,IPAddress, port);
    	      
    		// sending UDP packet to the server
    		clientSocket.send(sendingPacket);
    	      
    		// Get the server response .i.e. capitalized sentence
    		DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer,receivingDataBuffer.length);
    		clientSocket.receive(receivingPacket);
    	      
    		// Printing the received data
    		String receivedData = new String(receivingPacket.getData());
    	    System.out.println("Sent from the server: "+receivedData);
    	      
    	    // Closing the socket connection with the server
    	    clientSocket.close();
    	      
        } 
    	catch (SocketException e) 
    	{
            e.printStackTrace();
        } 
    	catch (IOException e) 
    	{
            System.out.println("Timeout. Client is closing.");
        }
    }

}
