package A11;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CollatorWorker implements Runnable {

   public Collator collator;
   public ServerSocket serversocket;
   
   public CollatorWorker(Collator c, ServerSocket sc) {
      this.collator = c;
      this.serversocket = sc;
      
   }

   @Override
   public void run() {
      // TODO Auto-generated method stub
      while (true) {


         Socket socket = null;
         DataInputStream din = null;
         // read first data
         try {

            socket = this.serversocket.accept();


            System.out.println(".....Connection has established....");
            System.out.println("...................................");

            din = new DataInputStream(socket.getInputStream());

            while (din.available() > 0) // We don't want to read when available bytes are zero, to
                                        // avoid EOF
            // exception
            {

               int nodeNameLength = din.readInt();
               byte[] nodeNameBytes = new byte[nodeNameLength]; 
               din.readFully(nodeNameBytes); 
               String nodeName = new String(nodeNameBytes);
               
               int sentMessagesCounter = din.readInt();
               double sumOfPayload = din.readDouble();
               
               int receivedMessagesCounter = din.readInt();
               double receivedsumOfPayload = din.readDouble();
               
               Data data = new Data(nodeName, sentMessagesCounter, sumOfPayload, receivedMessagesCounter, receivedsumOfPayload);
               collator.dataCollection.add(data);
            }
            
            din.close();

            socket.close();
         } catch (Exception ex) {
            ex.printStackTrace();
         } finally {
            try {
               if(din!=null) din.close();
               if(socket!=null) socket.close();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }
   }
   
   class Data {

      String name;
      int sentMessagesCounter;
      double sumOfPayload;
      int receivedMessagesCounter;
      double receivedsumOfPayload;
      
      public Data(String name, int sentMessagesCounter, double sumOfPayload, int receivedMessagesCounter, double receivedsumOfPayload) {
         this.name = name;
         this.sentMessagesCounter = sentMessagesCounter;
         this.sumOfPayload = sumOfPayload;
         this.receivedMessagesCounter = receivedMessagesCounter;
         this.receivedsumOfPayload = receivedsumOfPayload;
      }

      @Override
      public String toString() {
         return "Data [name=" + name + ", sentMessagesCounter=" + sentMessagesCounter
               + ", sumOfPayload=" + sumOfPayload + ", receivedMessagesCounter="
               + receivedMessagesCounter + ", receivedsumOfPayload=" + receivedsumOfPayload + "]";
      }
      
   }

}
