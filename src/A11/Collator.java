package A11;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import A11.CollatorWorker.Data;

public class Collator implements Runnable {

	public String collatorIP;
	public int collatorPORT;
	public static final String EXIT_COMMAND = "exit";
	public String nodeName;
	public Socket socket;
	public ServerSocket serversocket;
	public Collator collator;
	public ArrayList<Data> dataCollection = new ArrayList<Data>();

	public void initializeCollatorNode(int port) throws IOException {
		
		System.out.println("initializing Collator Node....");
		this.nodeName = "Collator-Node";
		this.serversocket = new ServerSocket(port);
		this.collatorPORT = port;
		InetAddress ip = InetAddress.getLocalHost();
		this.collatorIP = (ip.getHostAddress()).trim();
		System.out.println(
				"Node Name : " + this.nodeName + "IP: " + this.collatorIP + ", Listenning on : " + this.collatorPORT);
		
	}

	public static void main(String[] args) throws IOException {

		// creating object

		if (args.length <= 1) {
			System.out.println(
					"Error: Please pass the IP --SPACE-- UNIQUE PORT number for the Collator OTHER THAN CONFIG");
			System.exit(0);
		}

		String strIP = "";
		int nodePort = 0;
		try {
			strIP = (args[0]);
			nodePort = Integer.parseInt(args[1]);

		} catch (Exception e) {
			System.out.println("Error: Please provide IP AND PORT for the collator.");
			System.exit(0);
		}

		String[] strSplit = null;
		// TODO Auto-generated method stub
		// strSplit = strIP.split(",");
		/*
		 * System.out.println(
		 * "************************************\n 1.) Please select the ip SPACE port and enter in one line.\n 2.)"
		 * + " Enter the START to start the node \n 3.) " +
		 * "Complete all the config IP entries" +
		 * "\n 4.) veriy all the nodes stood up. \n 5.) Enter  \"RANDOM\" to select the communication node \n 6.) Enter \"START\" to start the round"
		 * +
		 * "\n 7.) Enter \"EXIT\" to exit  and stop the node \n****************************************"
		 * );
		 */

		System.out.println("Enter \"Start/start\" to intiate message sending ");

		Collator collatorNode = new Collator();
		collatorNode.collator=collatorNode;
		// Selecting Random node for sending Messages

		// String messageSenderNode= node.ranodmNodeSelection(port);

		// String[] SplitStrmessageSenderNode = messageSenderNode.split(" ");

		collatorNode.collatorIP = strIP;
		collatorNode.collatorPORT = nodePort;

		collatorNode.initializeCollatorNode(collatorNode.collatorPORT);
		Thread thread = new Thread(collatorNode);
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
				// collatorNode.sendMessages();
			} else if ("pull-traffic-summary".equalsIgnoreCase(exitStr)) {
				collatorNode.trafficSummary();
			}
		}

		System.out.println("Bye.");

	}

	public void trafficSummary() {
		for (Data data : dataCollection) {
            System.out.println(data);
         }

	}

	public void receiveMessages() throws IOException {

		

			/*
			 * DataInputStream dis = new DataInputStream(s.getInputStream()); String str =
			 * (String) dis.readUTF(); System.out.println("message= " + str);
			 */

			// byte[] marshalledBytes =new bytthreade[1000];
			// ByteArrayInputStream baInputStream = new
			// ByteArrayInputStream(marshalledBytes);
			CollatorWorker cw;
			cw=new CollatorWorker(this.collator,this.serversocket);
			Thread thread = new Thread(cw);
			thread.start();

			//socket =;

		
		

	}

	@Override
	public void run() {

		try {
			receiveMessages();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}

}
