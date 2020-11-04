import java.io.IOException;
import java.util.Scanner;

import client.ChatClient;
import common.ChatIF;

public class ServerConsole implements ChatIF {

	
	//Class variables *************************************************
	  
	  /**
	   * The default port to connect on.
	   */
	  final public static int DEFAULT_PORT = 5555;
	  
	  //Instance variables **********************************************
	  
	  /**
	   * The instance of the client that created this ConsoleChat.
	   */
	  ChatClient client;
	  
	  /**
	   * The instance of the server that created this ServerConsole.
	   */
	  EchoServer echo;
	  
	  
	  /**
	   * Scanner to read from the console
	   */
	  Scanner fromConsole; 

	  
	  //Constructors ****************************************************

	  /**
	   * Constructs an instance of the ClientConsole UI.
	   *
	   * @param host The host to connect to.
	   * @param port The port to connect on.
	   */
	  public ServerConsole(int port) 
	  {
		echo = new EchoServer(port);
		try 
	    {
	      echo.listen(); //Start listening for connections
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println("ERROR - Could not listen for clients!");
	    }
	    // Create scanner object to read from console
	    fromConsole = new Scanner(System.in); 
	  }

	  
	  //Instance methods ************************************************
	  
	  /**
	   * This method waits for input from the console.  Once it is 
	   * received, it sends it to the client's message handler.
	   */
	  public void accept() 
	  {
	    try
	    {

	      String message;

	      while (true) 
	      {
	        message = fromConsole.nextLine();
	        echo.handleMessageFromServer(message);
	      }
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println
	        ("Unexpected error while reading from console!");
	    }
	  }

	  /**
	   * This method overrides the method in the ChatIF interface.  It
	   * displays a message onto the screen.
	   *
	   * @param message The string to be displayed.
	   */
	  public void display(String message) 
	  {
	    System.out.println("> " + message);
	  }

	  
	  //Class methods ***************************************************
	  
	  /**
	   * This method is responsible for the creation of the Client UI.
	   *
	   * @param args[0] The host to connect to.
	   */
	  public static void main(String[] args) 
	  {
	    int port=0;
	    
	    try
	    {
	      port = Integer.parseInt(args[1]);
	    }
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	      port = 5555;
	    }
	    catch(NumberFormatException e) {
	      port = 5555;
	    }
	     
	    ServerConsole chat= new ServerConsole(port);

	    chat.accept();  //Wait for console data
	  }
}
