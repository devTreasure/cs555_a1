* Open command prompt
* Set java in path
* Do to src directory
* compile all the classes.
* create a config file which lists all the nodes ip and port.
* Go to bin directory of your directory.
* first start collator node. [Should not use any port listed in config file.]
    java A11.Collator 127.0.0.1 6050
  
* Start new prompt. Run all the node as:

    java A11.Node <PORT> <COLLATOR IP> <COLLATOR PORT> <CONFIG FILE ABSOLUTE PATH>"
    
    java A11.Node 6005 127.0.0.1 6050 D:\Bhavin\CSU\cs555_DistributedComputing\cs555_a1\Config.txt
    
    Do this all the nodes.
    
 * On each node send comamnd "start" - to start messaging
 
 * On each node send command "send" - to send data to collator
   
 * On collator node  send command "pull-traffic-summary" to print summary.