package A1;

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
	public  String line;
	public ArrayList<String> nodeList;
	public int nodeSize=3;
	
		
	public String getRandomNode(int port) throws FileNotFoundException, IOException
	{
	
		this.readTheAllNodes();
		
		String foundNode = "";
		
		Boolean isportFound=false;
		
		while(!isportFound)
		{
			
			Random m = new Random();
				
			int  num= m.nextInt(nodeSize);
			
			String strNode = this.nodeList.get(num);
			
			String strSplit []= strNode.split(" ");
			
			int configPort = Integer.parseInt(strSplit[1]);
			
			if(configPort != port)
			{
				isportFound = true;
				foundNode = strNode;
			}
			
			
			
		}
		
		return foundNode;
		
	}
	
	
	public void  readTheAllNodes() throws FileNotFoundException, IOException
	{
		this.nodeList=new ArrayList<String>();
		try (

		   // System.out.println(directory.getAbsolutePath().toString());
		    InputStream fis = new FileInputStream("Config.txt");
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    BufferedReader br = new BufferedReader(isr);
		) 
		{
		    while ((line = br.readLine()) != null) {
		        // Deal with the line
		    	//System.out.println(line);
		    	nodeList.add(line);
		    }
		    
		    
		}
		
		//return nodeList;
		
	}
	
	
}
