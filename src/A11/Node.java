package A11;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Node {

   private int collatorPORT;
   private String collatorIP;

   private int nodePort;
   private String nodeIP;
   private String nodeName;
   
   private String configFileName;
   private static Sender sender;
   
   public static int TOTAL_ROUNDS = 4;
   public static int EACH_ROUNDS = 3;
   private ReceiverWorker receiverWorker;
   private Thread receiverWorkerThread;
   

   public static void main(String[] args) throws Exception {
      int port = 0;
      int collatorPORT = 0;
      String collatorIP = "";

      if (args.length < 4) {
         System.out.println("Exa: java A11.Node <PORT> <COLLATOR IP> <COLLATOR PORT> <CONFIG FILE ABSOLUTE PATH>");
         System.out.println("Error: Please pass the UNIQUE  port number as listed in the config,IP port for Collator");
         System.exit(0);
      }

      try {
         port = Integer.parseInt(args[0]);
         collatorIP = args[1];
         collatorPORT = Integer.parseInt(args[2]);
      } catch (Exception e) {
         System.out.println("Error: Please provide numneric argument.");
         System.exit(0);
      }

      Node node = new Node();
      node.nodePort = port;
      node.collatorIP = collatorIP;
      node.collatorPORT = collatorPORT;
      node.configFileName = args[3];
      node.intializeServerNode();

      System.out.println(
            "Enter \"Start/start\" to intiate message sending  OR \"pull-traffic-summary\" ");

      boolean continueOperations = true;

      while (continueOperations) {
         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         String exitStr = br.readLine();
         System.out.println("Received command is:" + exitStr);

         if ("exit".equalsIgnoreCase(exitStr)) {
            System.out.println("Exiting.");
            node.receiverWorkerThread.stop();
            continueOperations = false;
         } else if ("start".equalsIgnoreCase(exitStr)) {
            sender = new Sender(node);
            sender.sendMessages();
         } else if ("send".equalsIgnoreCase(exitStr)) {
            node.sendStatistics();
         }
         
      }

      System.out.println("Bye.");

   }

   private void sendStatistics() throws Exception {
      Socket socket = null;
      try {
         System.out.println("Connecting to Collator @:" + collatorIP + ":" + collatorPORT);
         socket = new Socket(collatorIP, collatorPORT);
         sendStatistics(socket);
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if (socket != null)
            socket.close();
      }
   }

   private void sendStatistics(Socket socket) throws Exception {
      DataOutputStream dout = null;
      try {
         dout = new DataOutputStream(socket.getOutputStream());
         int length = getNodeName().length();
         byte[] nodeNameAsBytes = getNodeName().getBytes();
         dout.writeInt(length);
         dout.write(nodeNameAsBytes);
         dout.writeInt(sender.getSendCounter());
         dout.writeDouble(sender.getSumOfDataSent());
         dout.writeInt(receiverWorker.getReceiveCounter());
         dout.writeDouble(receiverWorker.getPayloadTotal());
         dout.flush();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if (dout != null)
            dout.close();
      }
      printStatistics();
   }

   private void printStatistics() {
      System.out.println("sent....");
      System.out.println(sender.getSendCounter());
      System.out.println(sender.getSumOfDataSent());
      System.out.println(receiverWorker.getReceiveCounter());
      System.out.println(receiverWorker.getPayloadTotal());
      
   }
   
   public void intializeServerNode() throws IOException {
      System.out.println("Initializing Server Node...");

      InetAddress ip = InetAddress.getLocalHost();
      this.nodeIP = (ip.getHostAddress()).trim();
      this.nodeName = this.nodeIP + ":" + this.nodePort;

      ServerSocket serversocket = new ServerSocket(nodePort);
      receiverWorker = new ReceiverWorker(serversocket, this);
      receiverWorkerThread = new Thread(receiverWorker);
      receiverWorkerThread.start();

      System.out.println("Node Name : " + this.nodeName + "  IP: " + this.nodeIP
            + ", Listenning on : " + this.nodePort + " Collator IP: " + this.collatorIP
            + " and Port : " + this.collatorPORT);

   }

   public int getCollatorPORT() {
      return collatorPORT;
   }

   public String getCollatorIP() {
      return collatorIP;
   }

   public int getNodePort() {
      return nodePort;
   }

   public String getNodeIP() {
      return nodeIP;
   }

   public String getNodeName() {
      return nodeName;
   }

   public String getConfigFileName() {
      return configFileName;
   }
   
}
