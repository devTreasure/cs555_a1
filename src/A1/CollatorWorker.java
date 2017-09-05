package A1;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class CollatorWorker implements Runnable {

	public Collator collator;
	public Socket socket;
	public ServerSocket serversocket;

	public CollatorWorker(Collator c, ServerSocket sc) {
		this.collator = c;
		this.serversocket = sc;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			
			


			// read first data
			try {

				this.socket= this.serversocket.accept();
				
				
				System.out.println(".....Connection has established....");
				System.out.println("...................................");
				
				DataInputStream din = new DataInputStream(this.socket.getInputStream());

				while (din.available() > 0) // We don't want to read when available bytes are zero, to avoid EOF
				// exception
				{

					int receviedCounter = din.readInt();
					int sendCounter = din.readInt();

					// System.out.println(String.format("server has received the random number :
					// %1$d ", radnomNUmertype));
					System.out.println("Message received : " + receviedCounter);

					System.out.println("Message sent :  " + sendCounter);

					/*
					 * int identifierLength = din.readInt(); byte[] identifierBytes = new
					 * byte[identifierLength]; din.readFully(identifierBytes); String identifier =
					 * new String(identifierBytes);
					 * 
					 * System.out.println(identif
					 * ier);
					 */
		
				}
				din.close();
				
				this.socket.close();
			} catch (SocketException ex) {
				System.out.println(ex.toString());
			}

			catch (Exception e) {
				System.out.println(e.toString());
			}

		}
	}

}
