package A1;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ReceiverWorker  implements Runnable{
	public Socket socket;
	public int receiveCounter = 0;
	public ClientServerNode clientServeNode;
	public ServerSocket serverSocket;
	public ReceiverWorker(ServerSocket sc,  ClientServerNode cv)
	{
		this.serverSocket=sc;
		this.clientServeNode=cv;
		
	}

	@Override
	public void run() {

		// TODO Auto-generated method stub
		while(true)
		{
	
			try {

				this.socket =this.serverSocket.accept();
				
				System.out.println(".....Connection has established....");
				System.out.println("...................................");
				
				DataInputStream din = new DataInputStream(this.socket.getInputStream());


				while (din.available() > 0) // We don't want to read when available bytes are zero, to avoid EOF
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
