// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  String login_identificator;
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String login_id, String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    login_identificator=login_id;
    openConnection();
    //System.out.println("Still working");
    
    
  }

  
  //Instance methods ************************************************
  
//  public void setInfo(String entry) {
//	  login_identificator=entry;
//  }
  
  public String getInfo() {
	  return login_identificator;
  }
   
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
      if(message.length()>0) {
    	  if(message.substring(0,1).equals("#")) {
        	  switch(message) {
        	  	case "#quit":
        	  		//System.out.println("#quit received");
        	  		quit();
        	  		break;
        	  	case "#logoff":
        	  		//System.out.println("#logoff received");
        	  		closeConnection();
        	  		break;
        	  	case "#login":
        	  		//System.out.println("#login received");
        	  		openConnection();
        	  		break;
        	  	case "#gethost":
        	  		//System.out.println("#gethost received");
        	  		clientUI.display("host is: "+getHost());
        	  		break;
        	  	case "#getport":
        	  		//System.out.println("#getport received");
        	  		clientUI.display("port is: "+getPort());
        	  		break;
        	  }
          }
      }
      
      if(message.length()>7) {
    	  if(message.substring(0,8).equals("#sethost")) {
    		  String[] messy = message.split(" ",2);
    		  setHost(messy[1]);
    	  }
    	  if(message.substring(0,8).equals("#setport")) {
    		  String[] messy = message.split(" ",2);
    		  setPort(Integer.parseInt(messy[1]));
    	  }
      }

      sendToServer(message);
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  public void connectionClosed() {
	  System.out.println("le serveur s'est arrêté");
  }
  
  public void connectionException(Exception e) {
	  System.out.println("le serveur s'est arrêté");
  }
}
//End of ChatClient class
