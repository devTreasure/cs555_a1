package A1;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
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
	
		
	public String RandomNode() throws FileNotFoundException, IOException
	{
		
		
		this.readTheAllNodes();
		
		Random m = new Random();
		
		
		int  num= m.nextInt(nodeSize);
		
	
		return this.nodeList.get(num);
	}
	
	
	public void  readTheAllNodes() throws FileNotFoundException, IOException
	{
		nodeList=new ArrayList<String>();
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
	

	
	public static void main(String[] args) throws IOException {
		ConfigReader cr = new ConfigReader();
	
		for(int i=0;i<5;i++)
		{
	     	System.out.println(cr.RandomNode());
		}
		
		
		
		
		
	}
	
}
