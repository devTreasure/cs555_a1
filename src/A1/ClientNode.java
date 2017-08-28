package A1;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ClientNode {

	private DataInputStream DataInputStream = null;
	public ServerSocket serversocket;
	public Socket socket;

	public void sendMessage(Socket socket) throws IOException {
		DataOutputStream dout = new DataOutputStream(socket.getOutputStream());

		int randomN = GetTheRandomNumeber();
		System.out.println("Random number generated at client is ");
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

	public static int GetTheRandomNumeber() {

		int max = 25000;
		int min = 1;

		Random rand = new Random();

		int radomNumber = rand.nextInt((max - min) + 1) + min;

		return radomNumber;

	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// WireFormatWidget wid = new WireFormatWidget();

		// .data = Payload.GetTheRandomNumeber();

		// wid.message = "Message from server";

		// 5 Messages
		// 5000 Roundsss

		ClientNode client = new ClientNode();

		Socket socket = new Socket("localhost", 60000);

		// client.socket = socket;

		for (int i = 0; i < 5; i++) {

			client.sendMessage(socket);
		}
		
		socket.close();

		// byte[] marshalledBytes = null;
		// ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();

		// marshalledBytes = baOutputStream.toByteArray();

		/*
		 * 
		 * DataOutputStream dout=new DataOutputStream(s.getOutputStream());
		 * dout.writeUTF("Hello Server"); dout.flush(); dout.close(); s.close();
		 */

		// String thisisMessage = "Message to Server";
		// WireFormatWidget wd = new WireFormatWidget(marshalledBytes)

		// TCPSender sender = new TCPSender(socket);
		// sender.sendData(wid.getBytes());

	}

}
