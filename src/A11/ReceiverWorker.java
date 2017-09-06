package A11;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiverWorker implements Runnable {

   private ServerSocket serverSocket;
   private Node node;
   
   private ReceiverData receiverData = new ReceiverData();

   public ReceiverWorker(ServerSocket sc, Node node) {
      this.serverSocket = sc;
      this.node = node;
   }

   @Override
   public void run() {
      System.out.println("Started receiver thread;");
      while (true) {
         Socket socket = null;
         DataInputStream din = null;
         try {
            socket = serverSocket.accept();
            System.out.println("Socket acceepted");
            din = new DataInputStream(socket.getInputStream());

            for (int i = 0; i < Node.EACH_ROUNDS; i++) {
               int radnomNUmertype = din.readInt();
               synchronized (receiverData) {
                  receiverData.receiveCounter += 1;
                  receiverData.payloadTotal += radnomNUmertype;
               }
               System.out.println(String.format("server has received the random number : %1$d ", radnomNUmertype));
            }

            System.out.println("done");
         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            try {
               if (din != null)
                  din.close();
               if (socket != null)
                  socket.close();
            } catch (IOException e) {
               System.out.println("Error while closing resources: " + e.getMessage());
            }
         }
      }
   }

   public int getReceiveCounter() {
      return receiverData.receiveCounter;
   }

   public double getPayloadTotal() {
      return receiverData.payloadTotal;
   }
   
   class ReceiverData {
      int receiveCounter;
      double payloadTotal;
   }
   
}