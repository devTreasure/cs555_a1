package A11;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class ConfigReader {

   public ArrayList<String> nodeList;
   private String configFilePath;

   public ConfigReader(String configFilePath) throws Exception {
      this.configFilePath = configFilePath;
      readTheAllNodes();
   }

   public String getRandomNode(int port) throws FileNotFoundException, IOException {

      // this.readTheAllNodes(); //Do not read files all the time. Read once in constructor.
      String foundNode = "";
      Boolean isportFound = false;

      while (!isportFound) {

         Random m = new Random();
         int num = m.nextInt(nodeList.size());
         System.out.println("random Node:" + num);
         String strNode = this.nodeList.get(num);
         String strSplit[] = strNode.split(" ");
         int configPort = Integer.parseInt(strSplit[1]);
         if (configPort != port) {
            isportFound = true;
            foundNode = strNode;
         }
      }

      return foundNode;

   }


   public void readTheAllNodes() throws Exception {
      this.nodeList = new ArrayList<String>();
      InputStream fis = null;
      BufferedReader br = null;
      try {

         fis = new FileInputStream(configFilePath);

         InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
         br = new BufferedReader(isr);

         String line = null;
         while ((line = br.readLine()) != null) {
            // Deal with the line
            // System.out.println(line);
            nodeList.add(line);
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         if(br!=null) br.close();
         if(fis!=null) fis.close();
      }
   }

}
