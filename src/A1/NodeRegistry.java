package A1;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class NodeRegistry  implements Runnable {
	
	//way to find node shoud not send message to him self.
	//
	//5 Messages 
	//5000 Rounds
	
	public ServerSocket RegistryServerSock;
	public Socket  socket;
	public  String StrExit = "EXIT";
	public volatile Boolean isDone=false;
	
	
	
	public void setDone()
	{
		isDone=true;
	}
	



	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    Scanner scanner = new Scanner(System.in);

	    //  prompt for the user's name
	    
	    NodeRegistry registry= new NodeRegistry();
	    
	    System.out.print("Enter 'EXIT' To Quit ");
	    
	    String strWord = scanner.next();
	    
	    while(!strWord.equals("EXIT"))
	    {
	    	
	    	  Scanner scanner2 = new Scanner(System.in);
	    	  strWord = scanner2.next();
	    	  
	    	  
	    	  if(strWord.equals("EXIT"))
	    	  {
	    		  System.out.print("Exiting the System");
	    	  }
	    	
	    }
	    

	
	    registry.setDone();
	    
	}


}
