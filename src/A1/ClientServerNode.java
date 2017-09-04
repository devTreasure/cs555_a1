package A1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ClientServerNode implements Runnable {

	// 5 Messages
	// 5000 Rounds

	public ServerSocket serversocket;
	public Socket socket;
	public String receiverNodeIP;
	public int  receiverNodePORT;
	public int nodePort;
	public static final String EXIT_COMMAND = "exit";
	public String nodeName;
	public String nodeIP;
	public int totalROUNDS=5;
	public int sendCounter = 0;
	public int receiveCounter = 0;
	public int collatorPort=0;
	public String collatorIP ="";
	public ClientServerNode clientServerNode;
	public ReceiverWorker Rworker;
	

	public ClientServerNode() {
		
	}

	public void intializeClientServerNode(int port) throws IOException {
		System.out.println("initializing ClientServer Node....");
	
		this.serversocket = new ServerSocket(port);
		this.nodePort = port;
		InetAddress ip = InetAddress.getLocalHost();
		this.nodeIP = (ip.getHostAddress()).trim();
		this.nodeName =  this.nodeIP+":"+  this.nodePort;

		System.out.println("Node Name : " + this.nodeName + "  IP: " + this.nodeIP
					+ ", Listenning on : " + this.nodePort + "  Selected sender Node:" + this.receiverNodeIP + " and Port : "+ this.receiverNodePORT + " "
					+ " Collator IP: " + this.collatorIP + " and Port : " + this.collatorPort);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// Here server receives the code that will run
		try {

			recevieMessge();
		}

		catch (Exception ex) {

		}

	}

	private String  ranodmNodeSelection(int port) throws FileNotFoundException, IOException {
		
	// TODO Auto-generated method stub
		
	 ConfigReader cr = new ConfigReader();
	 
	 String str = cr.getRandomNode(port);
	
	 return str;
	
	}


	public void sendReceiveTrafficSummary(Socket socket) throws IOException
	{
		DataOutputStream dout = new DataOutputStream(socket.getOutputStream());	
		
		System.out.println(" total messages received at : " + this.nodeName);
		System.out.println(this.Rworker.receiveCounter);
		
		
		//flushing this number to Collator		
		dout.writeInt(this.Rworker.receiveCounter);
		
		
		dout.writeInt(this.clientServerNode.sendCounter);

		/*
		String thisisMessage = "Messages recevied, by :  " + this.nodeName;

		byte[] strinMessageBytes = thisisMessage.getBytes();

		int elementLength = strinMessageBytes.length;

	
		dout.writeInt(elementLength);

	
		dout.write(strinMessageBytes);
		*/
		

		dout.flush();
		
		socket.close();
		
		
		
	}

	
	public void LoopMessaging(Socket socket) throws IOException
	{
		DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
		
		for(int i=0;i<=this.totalROUNDS;i++)
		{
			
		this.sendCounter+=1;
		
		int randomN = Payload.GetTheRandomNumeber();
		
		System.out.println("Random number generated at the client-payLoad is ");
		
		System.out.println(randomN);

		// 1 .RANDOM NUMBER

		dout.writeInt(randomN);

		String thisisMessage = "Message with love, to Server";

		byte[] strinMessageBytes = thisisMessage.getBytes();

		int elementLength = strinMessageBytes.length;

		// 2 . STRING length
		dout.writeInt(elementLength);

		// 3. String message

		dout.write(strinMessageBytes);

		dout.flush();
		}
		
		socket.close();
		
		
		
		
	}
	public  void sendMessages(String strMessage) throws IOException {
		
		Socket socket =null;
		
		if(strMessage=="message")
		{
			socket= new Socket(this.receiverNodeIP, this.receiverNodePORT);	
			LoopMessaging(socket);
		}
		else if(strMessage=="traffic") {
			socket= new Socket(this.collatorIP, this.collatorPort);		
			sendReceiveTrafficSummary(socket);
		}
		
		

	}

	public void recevieMessge() throws IOException {

		// Socket s = this.serversocket.accept();// establishes connection

		while (true) {

			/*
			 * DataInputStream dis = new DataInputStream(s.getInputStream()); String str =
			 * (String) dis.readUTF(); System.out.println("message= " + str);
			 */

			// byte[] marshalledBytes =new byte[1000];
			// ByteArrayInputStream baInputStream = new
			// ByteArrayInputStream(marshalledBytes);
			ReceiverWorker rv;
			rv= new ReceiverWorker(serversocket.accept(),this.clientServerNode ); // Passing same object that we created on main Thread to recevierWorkerThread.
			Thread recevierworkerThread = new Thread(rv);
			this.Rworker=rv;   //We need to get hold of received numbers count
			recevierworkerThread.start();
			/*
			socket = serversocket.accept();
			System.out.println(".....Connection has established....");
			System.out.println("...................................");
			DataInputStream din = new DataInputStream(this.socket.getInputStream());

			// read first data
			try {

				while (din.available() > 0) // We dont want to read when available bytes are zero, to avoid EOF
				// exception
				{
					this.receiveCounter += 1;
					int radnomNUmertype = din.readInt();

					System.out.println(String.format("server has received the random number : %1$d ", radnomNUmertype));

					int identifierLength = din.readInt();
					byte[] identifierBytes = new byte[identifierLength];
					din.readFully(identifierBytes);
					String identifier = new String(identifierBytes);

					System.out.println(identifier);
				}
			} catch (SocketException ex) {
				System.out.println(ex.toString());
			}

			catch (Exception e) {
				System.out.println(e.toString());
			}
       */
		}

		// baInputStream.close();
		// din.close();
		// obj.serversocket.close();
	}

	public static void main(String[] args) throws IOException {

		// creating object
		int collatorPORT=0;
		String StrCollatorIP="";

		if (args.length < 3) {
			System.out.println("Error: Please pass the UNIQUE  port number as listed in the config,IP port for Collator");
			System.exit(0);
		}

		int port = 0;
		try {
			port = Integer.parseInt(args[0]);
			StrCollatorIP =args[1];
			collatorPORT=Integer.parseInt(args[2]);
		} catch (Exception e) {
			System.out.println("Error: Please provide numneric argument.");
			System.exit(0);
		}

		String[] strSplit = null;
		// TODO Auto-generated method stub
		
		/*
		System.out.println(
				"************************************\n 1.) Please select the ip SPACE port and enter in one line.\n 2.)"
						+ " Enter the START to start the node \n 3.) " + "Complete all the config IP entries"
						+ "\n 4.) veriy all the nodes stood up. \n 5.) Enter  \"RANDOM\" to select the communication node \n 6.) Enter \"START\" to start the round"
						+ "\n 7.) Enter \"EXIT\" to exit  and stop the node \n****************************************");
		 */
		
		System.out.println("Enter \"Start/start\" to intiate message sending  OR \"pull-traffic-summary\" ");
		
		ClientServerNode node = new ClientServerNode();
		node.clientServerNode=node;

		//Selecting Random node for sending Messages
		
		String  messageSenderNode=  node.ranodmNodeSelection(port);
		String[] SplitStrmessageSenderNode = messageSenderNode.split(" ");
		
		//putting collator IP and PORT
		node.receiverNodeIP = SplitStrmessageSenderNode[0];
		node.receiverNodePORT = Integer.parseInt(SplitStrmessageSenderNode[1]);
		node.collatorPort=collatorPORT;
		node.collatorIP= StrCollatorIP;
		
		node.intializeClientServerNode(port);
		Thread thread = new Thread(node);
		thread.start();
		
		boolean continueOperations = true;

		while (continueOperations) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String exitStr = br.readLine();
			System.out.println("Received command is:" + exitStr);

			if (EXIT_COMMAND.equalsIgnoreCase(exitStr)) {
				System.out.println("Exiting.");
				continueOperations = false;
			} else if ("start".equalsIgnoreCase(exitStr)) {
					
			 node.sendMessages("message");
			
			}			
			else if ("pull-traffic-summary".equalsIgnoreCase(exitStr)) {
				node.sendMessages("traffic");;
			}
		}

		System.out.println("Bye.");

	}



}
