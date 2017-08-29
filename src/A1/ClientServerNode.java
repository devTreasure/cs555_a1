package A1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

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
	

	public ClientServerNode() {

	}

	public void intializeClientServerNode(int port) throws IOException {
		System.out.println("initializing ClientServer Node....");
		this.nodeName = "ClientServer-Node";
		this.serversocket = new ServerSocket(port);
		this.nodePort = port;
		InetAddress ip = InetAddress.getLocalHost();
		this.nodeIP = (ip.getHostAddress()).trim();

		System.out.println("Node Name : " + this.nodeName + "IP: " + this.nodeIP
				+ ", Listenning on : " + this.nodePort);
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



	public void trafficSummary() {
		// print node statistics
	}
	
	public  void sendMessages() throws IOException {
		// TODO Auto-generated method stub
		
		Socket socket = new Socket(this.receiverNodeIP, this.receiverNodePORT);		
		
		DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

		int randomN = Payload.GetTheRandomNumeber();
		
		System.out.println("Random number generated as a payLoad is ");
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
		
		//socket.close();
		
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
			socket = serversocket.accept();
			System.out.println(".....Connection has established....");
			System.out.println("...................................");
			DataInputStream din = new DataInputStream(this.socket.getInputStream());

			// read first data
			try {

				while (din.available() > 0) // We dont want to read when available bytes are zero, to avoid EOF
									// exception

				{

					int radnomNUmertype = din.readInt();

					System.out.println(String.format("server random number is : %1$d ", radnomNUmertype));

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

		}

		// baInputStream.close();
		// din.close();
		// obj.serversocket.close();
	}

	public static void main(String[] args) throws IOException {

		// creating object

		if (args.length < 1) {
			System.out.println("Error: Please pass the UNIQUE  port number as listed in the config");
			System.exit(0);
		}

		int port = 0;
		try {
			port = Integer.parseInt(args[0]);
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
		
		System.out.println("Enter \"Start/start\" to intiate message sending ");
		
		ClientServerNode node = new ClientServerNode();


		
		String  messageSenderNode=  node.ranodmNodeSelection(port);
		String[] SplitStrmessageSenderNode = messageSenderNode.split(" ");
		
		node.receiverNodeIP = SplitStrmessageSenderNode[0];
		node.receiverNodePORT = Integer.parseInt(SplitStrmessageSenderNode[1]);
		
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
					node.sendMessages();
			 }
			else if ("pull-traffic-summary".equalsIgnoreCase(exitStr)) {
				node.trafficSummary();
			}
		}

		System.out.println("Bye.");

	}



}
