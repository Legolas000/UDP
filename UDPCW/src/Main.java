import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Main 
{
	//IP Address Regex Pattern
	private static final String IP_ADDRESS_REGEX =
            "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
	
	private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile(IP_ADDRESS_REGEX);
	 
	//Validate IP Address
    public static boolean isValidIPAddress(String IP_ADDRESS)
    {
        if (IP_ADDRESS == null) 
        {
            return false;
        }
 
        Matcher matcher = IP_ADDRESS_PATTERN.matcher(IP_ADDRESS);
 
        return matcher.matches();
    }
    
	
    public static void main(String[] args)
    {
    	Scanner sc = new Scanner(System.in);
    	    	    	
    	System.out.println("Please enter IP Address");
    	String ipAddress = sc.next();
    	
    	//validate user enter ip address
    	if (!isValidIPAddress(ipAddress)) {
    		System.out.println("The IP address " + ipAddress + " isn't valid");
    		
    		System.out.println("Please enter IP Address");
        	ipAddress = sc.next();
        }
        
    	//validate user enter port
    	int port = getValidPort();
  
    	boolean isPortInUse = isPortInUse(ipAddress, port);
    	//System.out.println(isPortInUse);
    	
    	//check port currently used by someone else
    	if(isPortInUse == true)
    	{
    		System.out.println("Port No is Currently Using!");
    		System.out.println("Please enter Different Port No between 0 and 65535");
    		port = getValidPort();
    	}
       	
    	//start udp server and client
        UDPServer server = new UDPServer(port);
        UDPClient client = new UDPClient(ipAddress, port);

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.submit(client);
        executorService.submit(server);
    	
    }
    
    //Validate User Enter Port
    public static int getValidPort() 
	{
    	Scanner sc = new Scanner(System.in);
		
		//to hold user insert port
		int port;
		
		System.out.println("Please enter your port in range 0 and 65535");
		port = sc.nextInt();
		
		//check port in the range or not
		while (port < 0 || port > 65535)
		{
			System.out.println("Invalid port !");
			
			//since port was invalid prompt another one to input port
			System.out.println("Please enter your ports in range 0 to 65535");
			port = sc.nextInt();
		}
		
		//return validate port from method
		return port;
		
	}
    
    //Check Port Currently Using
    private static boolean isPortInUse(String host, int port) 
    {
    	boolean result = false;

    	try 
    	{
    		(new Socket(host, port)).close();
            result = true;    //Port is Using
        } 
    	catch (SocketException e) 
    	{
    		// Could not connect.
        } 
    	catch (UnknownHostException e) 
    	{
            // Host not found
        } 
    	catch (IOException e) 
    	{
            // IO exception
        }
        
        return result;
    }
    
}