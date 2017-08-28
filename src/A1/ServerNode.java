package A1;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerNode implements Runnable {

	// 5 Messages
	// 5000 Rounds

	public ServerSocket serversocket;
	public Socket socket;
	
	
   public  ServerNode(Socket sc) {
	   this.socket=sc;
   	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// Here server receives the code that will run
		try {
			recevieMessge();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void recevieMessge() throws IOException {

		//Socket s = this.serversocket.accept();// establishes connection

	

		while (true) {

			/*
			 * DataInputStream dis = new DataInputStream(s.getInputStream()); String str =
			 * (String) dis.readUTF(); System.out.println("message= " + str);
			 */

			// byte[] marshalledBytes =new byte[1000];

			// ByteArrayInputStream baInputStream = new
			// ByteArrayInputStream(marshalledBytes);
			DataInputStream din = new DataInputStream(this.socket.getInputStream());

			// read first data
			try {

				while (din.available() > 0)  //We dont want to read when available bytes are zero, to avoid EOF exception

				{

					int radnomNUmertype = din.readInt();

					System.out.println(String.format("server random nymber is :  %1$d ", radnomNUmertype));

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

		// TODO Auto-generated method stub
		System.out.println("************************************\n 1.) Please select the ip SPACE port and enter in one line.\n 2.)"
				+ " Enter the START to start the node \n 3.) "
				+ "Complete all the config IP entries"
				+ "\n 4.) veriy all the nodes stood up. \n 5.) Enter  \"RANDOM\" to select the communication node \n 6.) Enter \"START\" to start the round"
				+ "\n 7.) Enter \"EXIT\" to exit  and stop the node \n****************************************"); 

		
		//obj.serversocket = new ServerSocket(60000);
		ServerSocket serversocket = new ServerSocket(60000 );
		
		//ServerNode obj = new ServerNode(serversocket);
		// TCPReceiver receiver = new TCPReceiver(obj.serversocket);
		System.out.println("Server has started and waiting for client connection....");
		while(true)
		{
		    Socket clientSocket = null;
		    clientSocket = serversocket.accept();
			System.out.println(".....Connection has established....");
			System.out.println("...................");
			//obj.run();
		    new Thread( new ServerNode(clientSocket)).start();
		}
	

	}

}
