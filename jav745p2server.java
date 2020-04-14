
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class jav745p2server {
	private static String[] SaveArgs;
	public static String [] getArgs() {return SaveArgs;}


	
  public static void main(String[] args) throws Exception {

	  try{
		  SaveArgs = args;
      ServerSocket server=new ServerSocket(Integer.parseInt(args[0]));   //port Number will be at args[0]   Integer.parseInt(args[0])
      int counter=0;
      System.out.println("Server Started ....Waiting For Client");
      while(true){
        counter++;
        Socket serverClient=server.accept();  //server accept the client connection request
        System.out.println(" >> " + "Client No:" + counter + " started!");
        
        ServerClientThread sct = new ServerClientThread(serverClient,counter); //send  the request to a separate thread
        sct.start();
        ServerClientThread cla = new ServerClientThread();
        //cla.printArgs();
        
      }
    }catch(Exception e){
      System.out.println(e);
    }
  }
}

class ServerClientThread extends Thread {
	  Socket serverClient;
	  int clientNo;
	  int capacity=1;
	  
	  ServerClientThread(Socket inSocket,int counter){
	    serverClient = inSocket;
	    clientNo=counter;
	    
	  }
	  ServerClientThread(){}
	  
	  public void run(){
	    try{
	    	
	      DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
	      DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
	      String clientMessage="", serverMessage="";
	      Detail acc = new Detail();
	     
	      SimpleDateFormat formatter = new SimpleDateFormat("dd MMMMM yyyy HH:mm:ss");
	      Date date = new Date();

	      String[] args = jav745p2server.getArgs();
	     
	      BufferedReader into = new BufferedReader(new FileReader(args[1]));
		    String str1;
		     str1 = into.readLine(); 
		     //System.out.println(str1);
		     List<String> sp1 = Arrays.asList(str1.split("\\s*,\\s*"));
	    
		     BufferedReader in1 = new BufferedReader(new FileReader(args[2]));
			    String str2;
			     str2 = in1.readLine(); 
			     //System.out.println(str2);
			     List<String> sp2 = Arrays.asList(str2.split("\\s*,\\s*"));
			     
			     int p1 = Integer.parseInt(sp2.get(4));
			     int p2 = Integer.parseInt(sp2.get(5));
			     int p3 = Integer.parseInt(sp2.get(6));
			     int p4 = Integer.parseInt(sp2.get(7));           
			     
			     int Addition = Integer.parseInt(args[3]);
			     //acc.setCBalance(Addition);//System.out.println(Addition);
	     File file = new File(System.getProperty("user.dir") +" TotalCredited.txt");
	     File file1 = new File(System.getProperty("user.dir") +" CTicket.txt");
			     
	     byte ab[] =new byte[2000];
	     InputStream is = serverClient.getInputStream(); 
	     is.read(ab,0,ab.length);
	     FileOutputStream fr = new FileOutputStream(file1.getAbsoluteFile());
	     fr.write(ab,0,ab.length);
	  
	     FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        
	     clientMessage=inStream.readUTF();
	     
	     int CAmount=Integer.parseInt(clientMessage);
	     acc.setCAmount(CAmount);
	     
	     //System.out.println(CAmount);
	  
	     try 
	     { 
		     BufferedReader in = new BufferedReader(new FileReader(file1.getAbsoluteFile()));
			 boolean firstLine = true;
			 String str;
			 
			 while((str = in.readLine())!=null) 
			 {
				 if(str.startsWith(sp1.get(0)) || str.startsWith(sp1.get(1)) ) 
				 
				 {
			       List<String> sp = Arrays.asList(str.split("\\s*,\\s*"));
			       if(sp.size()<1) {outStream.writeUTF("Empty File Found");
			        outStream.flush();}
			  
			     String a1 = String.valueOf(sp.get(2).charAt(2));
			     
			     if(sp.get(0).equalsIgnoreCase(sp1.get(0))) 
			     {
			    	 if(sp.get(1).equalsIgnoreCase(sp1.get(2))) 
			    	 {
			    		if(a1.equalsIgnoreCase("G")) 
			    		{
			    		int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		int Remain = Type1.getv1c1G()-Num;
			    		
			    			if(Num<Remain) 
			    			{
			    				
			    				int totalAmount=Num*p3;
			    				
			    				if(totalAmount>acc.getCAmount())
			    				{
			    				Type1.setv1c1G(0);	
			    				outStream.writeUTF("Not Enough Available Balance");
		    			        outStream.flush();
		    			        bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
		    			        Addition = 0;
		    			        
		    			        }
			    				
			    				else {
			    					Type1.setv1c1G(Num);
			    					int RAmount=acc.getCAmount()-totalAmount;
			    					Addition =acc.getCBalance()+totalAmount;
			    					//acc.setCBalance(Addition);
			    					acc.setCAmount(RAmount);
			    					acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(4));acc.setBalance(RAmount);acc.setType(sp2.get(2));
			    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
			    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"+Num+" "+acc.getType()+"Tickets"+"\n Your Remaining Amount is: $"
			    							+acc.getBalance()+"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
			    					bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
			    					bw.write("For Vanue :"+acc.getVanue()+" And Concert :"+acc.getConcert()+"\n Remaining "+acc.getType()+" Tickets are :"+Type1.getv1c1G()+"\n");
			    					outStream.writeUTF(serverMessage);
			    			        outStream.flush();
			    					
			    					}
			    				}
			    			else 
			    			{
			    				outStream.writeUTF("No Availble seat for this type");outStream.flush();
			    				}
			    		}
			    		else if(a1.equalsIgnoreCase("D")) 
			    		{ 
			    			int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		    int Remain = Type1.getv1c1D()-Num;
		    			    if(Num<Remain) 
		    			    {
		    				int totalAmount=Num*p1;
		    				
		    				if(totalAmount>acc.getCAmount()) 
		    				{
		    					Type1.setv1c1D(0);
		    					outStream.writeUTF("Not Enough Available Balance");
	    			        outStream.flush();bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
	    			        Addition = 0;
	    			        }
		    				else {
		    					Type1.setv1c1D(Num);
		    					int RAmount=acc.getCAmount()-totalAmount;
		    					acc.setCAmount(RAmount);
		    					Addition = acc.getCBalance() + totalAmount;
		    					//acc.setCBalance(Addition);
		    					acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(4));acc.setBalance(RAmount);acc.setType(sp2.get(0));
		    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
		    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"
		    							+Num+" "+acc.getType()+"Tickets"+"\n Your Remaining Amount is: $"+acc.getBalance()+"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
		    					bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
		    					bw.write("For Vanue :"+acc.getVanue()+" And Concert :"+acc.getConcert()+"\n Remaining "+acc.getType()+" Tickets are :"+Type1.getv1c1D()+"\n");
		    					outStream.writeUTF(serverMessage);
		    					outStream.flush();
		    					}
		    				}
		    			    else 
		    			    {
		    			    	outStream.writeUTF("No Availble seat for this type");outStream.flush();
		    			    	}
		    			    }
			    		
			    		else if(a1.equalsIgnoreCase("S")) 
			    		{
			    			int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		    int Remain = Type1.getv1c1S()-Num;
		    			    if(Num<Remain) {
		    				int totalAmount=Num*p4;
		    				
		    				if(totalAmount>acc.getCAmount()) 
		    				{
		    					Type1.setv1c1S(0);
		    					outStream.writeUTF("Not Enough Available Balance");
	    			        outStream.flush();bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
	    			        Addition = 0;
	    			        }
		    				else {
		    					Type1.setv1c1S(Num);
		    					int RAmount=acc.getCAmount()-totalAmount;
		    					acc.setCAmount(RAmount);
		    					Addition = acc.getCBalance() + totalAmount;
		    					//acc.setCBalance(Addition);
		    					acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(4));acc.setBalance(RAmount);acc.setType(sp2.get(3));
		    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
		    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"+Num+" "+acc.getType()+"Tickets"+"\n Your Remaining Amount is: $"
		    							+acc.getBalance()+"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
		    					bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
		    					bw.write("For Vanue :"+acc.getVanue()+" And Concert :"+acc.getConcert()+"\n Remaining "+acc.getType()+" Tickets are :"+Type1.getv1c1S()+"\n");
		    					outStream.writeUTF(serverMessage);
		    			        outStream.flush();
		    					
		    					}
		    				}
		    			    else 
		    			    {
		    			    	outStream.writeUTF("No Availble seat for this type");outStream.flush();
		    			    	}
		    			    }
			    		
			    		else if(a1.equalsIgnoreCase("P")) 
			    		{
			    			int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		    int Remain = Type1.getv1c1P()-Num;
		    			    if(Num<Remain) 
		    			    {
		    				int totalAmount=Num*p2;
		    				
		    				if(totalAmount>acc.getCAmount()) 
		    				{
		    					Type1.setv1c1P(0);outStream.writeUTF("Not Enough Available Balance");
	    			        outStream.flush();bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
	    			        Addition = 0;
	    			        }
		    				else 
		    				{
		    					Type1.setv1c1P(Num);
		    				int RAmount=acc.getCAmount()-totalAmount;
	    					acc.setCAmount(RAmount);
	    					Addition = acc.getCBalance() + totalAmount;
	    					//acc.setCBalance(Addition);
		    					acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(4));acc.setBalance(RAmount);acc.setType(sp2.get(1));
		    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
		    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"+Num+" "+acc.getType()+
		    					                 "Tickets"+"\nYour Remaining Amount is: $"+acc.getBalance()+"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
		    					bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
		    					outStream.writeUTF(serverMessage);
		    			        outStream.flush();
		    					
		    					}
		    				}
		    			    else 
		    			    {
		    			    	outStream.writeUTF("No Availble seat for this type");outStream.flush();
		    			    	}
		    			    }
			    			
			    	 }
			    	 
			    	 else if(sp.get(1).equalsIgnoreCase(sp1.get(3))) 
			    	 {
			    		if(a1.equalsIgnoreCase("G")) 
			    		{
			    		int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		int Remain = Type1.getv1c2G()-Num;
			    			if(Num<Remain) 
			    			{
			    				int totalAmount=Num*p3;
			    				
			    				if(totalAmount>acc.getCAmount()) 
			    				{
			    					Type1.setv1c2G(0);outStream.writeUTF("Not Enough Available Balance");
		    			        outStream.flush();bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
		    			        Addition = 0;
		    			        }
			    				else {
			    					int RAmount=acc.getCAmount()-totalAmount;
			    					acc.setCAmount(RAmount);
			    					Addition = acc.getCBalance() + totalAmount;
			    					//acc.setCBalance(Addition);
			    					Type1.setv1c2G(Num);
			    					acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(5));acc.setBalance(RAmount);acc.setType(sp2.get(2));
			    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
			    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"+Num+" "+acc.getType()+
			    					                 "Tickets"+"\nYour Remaining Amount is: $"+acc.getBalance()
			    					                 +"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
			    					bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
			    					bw.write("For Vanue :"+acc.getVanue()+" And Concert :"+acc.getConcert()+"\n Remaining "+acc.getType()+" Tickets are :"+Type1.getv1c2G()+"\n");
			    					outStream.writeUTF(serverMessage);
			    			        outStream.flush();
			    					
			    					}
			    				}
			    			else 
			    			{
			    				outStream.writeUTF("No Availble seat for this type");outStream.flush();
			    				}
			    		}
			    		else if(a1.equalsIgnoreCase("D")) 
			    		{ 
			    			int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		    int Remain = Type1.getv1c2D()-Num;
		    			    if(Num<Remain) 
		    			    {
		    				int totalAmount=Num*p1;
		    				
		    				if(totalAmount>acc.getCAmount()) 
		    				{
		    				Type1.setv1c2D(0);
		    				outStream.writeUTF("Not Enough Available Balance");
	    			        outStream.flush();
	    			        bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
	    			       
	    			        Addition = 0;
	    			        }
		    				else 
		    				{ 
		    					Type1.setv1c2D(Num);
		    					int RAmount=acc.getCAmount()-totalAmount;
		    					acc.setCAmount(RAmount);
		    					Addition = acc.getCBalance() + totalAmount;
		    					//acc.setCBalance(Addition);
		    				    acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(5));acc.setBalance(RAmount);acc.setType(sp2.get(0));
		    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
		    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"+Num+" "+acc.getType()
		    					                 +"Tickets"+"\nYour Remaining Amount is: $"+acc.getBalance()+"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
		    				    bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
		    				    bw.write("For Vanue :"+acc.getVanue()+" And Concert :"+acc.getConcert()+"\n Remaining "+acc.getType()+" Tickets are :"+Type1.getv1c2D()+"\n");
		    					outStream.writeUTF(serverMessage);
		    			        outStream.flush();
		    					
		    					}
		    				}
		    			    else 
		    			    {
		    			    	outStream.writeUTF("No Availble seat for this type");outStream.flush();
		    			    	}
		    			    }
			    		
			    		else if(a1.equalsIgnoreCase("S")) 
			    		{
			    			int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		    int Remain = Type1.getv1c2S()-Num;
		    			    if(Num<Remain) 
		    			    {
		    				int totalAmount=Num*p4;
		    				
		    				if(totalAmount>CAmount) 
		    				{
		    				Type1.setv1c2S(0);outStream.writeUTF("Not Enough Available Balance");
	    			        outStream.flush();bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
	    			        Addition = 0;
	    			        }
		    				else 
		    				{
		    				Type1.setv1c2S(Num);
		    				int RAmount=acc.getCAmount()-totalAmount;
	    					acc.setCAmount(RAmount);
	    					Addition = acc.getCBalance() + totalAmount;
	    					//acc.setCBalance(Addition);
	    					acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(5));acc.setBalance(RAmount);acc.setType(sp2.get(3));
		    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
		    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"+Num+" "+acc.getType()
		    					                 +"Tickets"+"\nYour Remaining Amount is: $"+acc.getBalance()+"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
		    					bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
		    					bw.write("For Vanue :"+acc.getVanue()+" And Concert :"+acc.getConcert()+"\n Remaining "+acc.getType()+" Tickets are :"+Type1.getv1c2S()+"\n");
		    					outStream.writeUTF(serverMessage);
		    			        outStream.flush();
		    					
		    					}
		    				}
		    			    else
		    			    {
		    			    	outStream.writeUTF("No Availble seat for this type");outStream.flush();}
		    			    }
			    		
			    		else if(a1.equalsIgnoreCase("P")) 
			    		{
			    			int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		    int Remain = Type1.getv1c2P()-Num;
		    			    if(Num<Remain) 
		    			    {
		    				int totalAmount=Num*p2;
		    				
		    				if(totalAmount>acc.getCAmount()) 
		    				{
		    				Type1.setv1c2P(0);outStream.writeUTF("Not Enough Available Balance");
	    			        outStream.flush();bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
	    			        Addition = 0;
	    			        }
		    				else 
		    				{
		    				Type1.setv1c2P(Num);
		    				int RAmount=acc.getCAmount()-totalAmount;
	    					acc.setCAmount(RAmount);
	    					Addition = acc.getCBalance() + totalAmount;
	    					//acc.setCBalance(Addition);
	    					acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(5));acc.setBalance(RAmount);acc.setType(sp2.get(1));
		    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
		    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"+Num+" "+acc.getType()
		    					                 +"Tickets"+"\nYour Remaining Amount is: $"+acc.getBalance()+"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
		    					bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
		    					bw.write("For Vanue :"+acc.getVanue()+" And Concert :"+acc.getConcert()+"\n Remaining "+acc.getType()+" Tickets are :"+Type1.getv1c2P()+"\n");
		    					outStream.writeUTF(serverMessage);
		    			        outStream.flush();
		    					
		    					}
		    				}
		    			    else 
		    			    {
		    			    	outStream.writeUTF("No Availble seat for this type");outStream.flush();
		    			    	}
		    			    }
			    	 
			     }
			    	 else 
			    	 {
			    		 outStream.writeUTF("Invalid Concert Type");outStream.flush();
			    		 }
			     
			     }
			     else if(sp.get(0).equalsIgnoreCase(sp1.get(1))) 
			     {
			    	 if(sp.get(1).equalsIgnoreCase(sp1.get(2))) 
			    	 {
			    		if(a1.equalsIgnoreCase("G")) 
			    		{
			    		int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		int Remain = Type2.getv2c1G()-Num;
			    			if(Num<Remain)
			    			{
			    				int totalAmount=Num*p3;
			    				
			    				if(totalAmount>acc.getCAmount()) 
			    				{
			    				Type2.setv2c1G(0);
			    				outStream.writeUTF("Not Enough Available Balance");
		    			        outStream.flush();bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
		    			        Addition = 0;
		    			        }
			    				else {
			    					Type2.setv2c1G(Num);
			    					int RAmount=acc.getCAmount()-totalAmount;
			    					acc.setCAmount(RAmount);
			    					Addition = acc.getCBalance() + totalAmount;
			    					//acc.setCBalance(Addition);
			    					acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(5));acc.setBalance(RAmount);acc.setType(sp2.get(2));
			    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
			    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"+Num+" "+acc.getType()
			    					                 +"Tickets"+"\nYour Remaining Amount is: $"+acc.getBalance()+"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
			    					bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
			    					bw.write("For Vanue :"+acc.getVanue()+" And Concert :"+acc.getConcert()+"\n Remaining "+acc.getType()+" Tickets are :"+Type2.getv2c1G()+"\n");
			    					outStream.writeUTF(serverMessage);
			    			        outStream.flush();
			    					
			    					}
			    				}
			    			else 
			    			{
			    				outStream.writeUTF("No Availble seat for this type");outStream.flush();
			    			}
			    		}
			    		else if(a1.equalsIgnoreCase("D")) 
			    		{ 
			    			int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		    int Remain = Type2.getv2c1D()-Num;
		    			    if(Num<Remain) 
		    			    {
		    				int totalAmount=Num*p1;
		    				
		    				if(totalAmount>acc.getCAmount())
		    				{
		    				Type2.setv2c1D(0);
		    				outStream.writeUTF("Not Enough Available Balance");
	    			        outStream.flush();bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
	    			        Addition = 0;
	    			        }
		    				else 
		    				{
		    				Type2.setv2c1D(Num);
		    				int RAmount=acc.getCAmount()-totalAmount;
	    					acc.setCAmount(RAmount);
	    					Addition = acc.getCBalance() + totalAmount;
	    					//acc.setCBalance(Addition);
	    					acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(1));acc.setBalance(RAmount);acc.setType(sp2.get(0));
		    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
		    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"+Num+" "+acc.getType()
		    					                 +"Tickets"+"\nYour Remaining Amount is: $"+acc.getBalance()+"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
		    					bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
		    					bw.write("For Vanue :"+acc.getVanue()+" And Concert :"+acc.getConcert()+"\n Remaining "+acc.getType()+" Tickets are :"+Type2.getv2c1D()+"\n");
		    					outStream.writeUTF(serverMessage);
		    			        outStream.flush();
		    					
		    					}
		    				}
		    			    else 
		    			    {
		    			    	outStream.writeUTF("No Availble seat for this type");outStream.flush();
		    			    	}
		    			    }
			    		
			    		else if(a1.equalsIgnoreCase("S")) 
			    		{
			    			int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		    int Remain = Type2.getv2c1S()-Num;
		    			    if(Num<Remain) {
		    				int totalAmount=Num*p4;
		    				
		    				if(totalAmount>CAmount) 
		    				{
		    				Type2.setv2c1S(0);outStream.writeUTF("Not Enough Available Balance");
	    			        outStream.flush();bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
	    			        Addition = 0;
	    			        }
		    				else 
		    				{
		    				Type2.setv2c1S(Num);
		    				int RAmount=acc.getCAmount()-totalAmount;
	    					acc.setCAmount(RAmount);
	    					Addition = acc.getCBalance() + totalAmount;
	    					//acc.setCBalance(Addition);
		    					acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(5));acc.setBalance(RAmount);acc.setType(sp2.get(3));
		    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
		    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"+Num+" "+acc.getType()
		    					                 +"Tickets"+"\nYour Remaining Amount is: $"+acc.getBalance()+"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
		    					bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
		    					bw.write("For Vanue :"+acc.getVanue()+" And Concert :"+acc.getConcert()+"\n Remaining "+acc.getType()+" Tickets are :"+Type2.getv2c1S()+"\n");
		    					outStream.writeUTF(serverMessage);
		    			        outStream.flush();
		    					
		    					}
		    				}
		    			    else 
		    			    {
		    			    	outStream.writeUTF("No Availble seat for this type");outStream.flush();
		    			    	}
		    			    }
			    		
			    		else if(a1.equalsIgnoreCase("P")) 
			    		{
			    			int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		    int Remain = Type2.getv2c1P()-Num;
		    			    if(Num<Remain)
		    			    {
		    				int totalAmount=Num*p2;
		    				
		    				if(totalAmount>acc.getCAmount()) 
		    				{
		    				Type2.setv2c1P(0);outStream.writeUTF("Not Enough Available Balance");
	    			        outStream.flush();bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
	    			        Addition = 0;}
		    				else 
		    				{
		    				Type2.setv2c1P(Num);
		    				int RAmount=acc.getCAmount()-totalAmount;
	    					acc.setCAmount(RAmount);
	    					Addition = acc.getCBalance() + totalAmount;
	    					//acc.setCBalance(Addition);
	    					acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(5));acc.setBalance(RAmount);acc.setType(sp2.get(1));
		    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
		    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"+Num+" "+acc.getType()
		    					                 +"Tickets"+"\nYour Remaining Amount is: $"+acc.getBalance()+"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
		    					bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
		    					outStream.writeUTF(serverMessage);
		    			        outStream.flush();
		    					
		    					}
		    				}
		    			    else 
		    			    {
		    			    	outStream.writeUTF("No Availble seat for this type");outStream.flush();
		    			    	}
		    			    }
			    			
			    	 }
			    	 
			    	 else if(sp.get(1).equalsIgnoreCase(sp1.get(3))) 
			    	 {
			    		if(a1.equalsIgnoreCase("G")) 
			    		{
			    		int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		int Remain = Type2.getv2c2G()-Num;
			    			if(Num<Remain) 
			    			{
			    				int totalAmount=Num*p3;
			    				
			    				if(totalAmount>acc.getCAmount()) 
			    				{
			    				Type2.setv2c2G(0);outStream.writeUTF("Not Enough Available Balance");
		    			        outStream.flush();
		    			        bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
		    			        Addition = 0;
		    			        }
			    				else 
			    				{
			    				Type2.setv2c2G(Num);
			    				int RAmount=acc.getCAmount()-totalAmount;
		    					acc.setCAmount(RAmount);
		    					Addition = acc.getCBalance() + totalAmount;
		    					//acc.setCBalance(Addition);
			    					acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(4));acc.setBalance(RAmount);acc.setType(sp2.get(2));
			    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
			    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"+Num+" "+acc.getType()
			    					                 +"Tickets"+"\nYour Remaining Amount is: $"+acc.getBalance()+"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
			    					bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
			    					bw.write("For Vanue :"+acc.getVanue()+" And Concert :"+acc.getConcert()+"\n Remaining "+acc.getType()+" Tickets are :"+Type2.getv2c2G()+"\n");
			    					outStream.writeUTF(serverMessage);
			    			        outStream.flush();
			    					
			    					}
			    				}
			    			else 
			    			{
			    				outStream.writeUTF("No Availble seat for this type");outStream.flush();
			    				}
			    		}
			    		else if(a1.equalsIgnoreCase("D")) 
			    		{ 
			    			int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		    int Remain = Type2.getv2c2D()-Num;
		    			    if(Num<Remain) {
		    				int totalAmount=Num*p1;
		    				
		    				if(totalAmount>acc.getCAmount()) 
		    				{
		    				Type2.setv2c2D(0);outStream.writeUTF("Not Enough Available Balance");
	    			        outStream.flush();
	    			        bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
	    			        Addition = 0;
	    			        }
		    				else 
		    				{
		    					Type2.setv2c2D(Num);
		    				    int RAmount=acc.getCAmount()-totalAmount;
	    					    acc.setCAmount(RAmount);
	    					    Addition = acc.getCBalance() + totalAmount;
	    					    //acc.setCBalance(Addition);
		    					acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(4));acc.setBalance(RAmount);acc.setType(sp2.get(0));
		    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
		    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"+Num+" "+acc.getType()
		    					                 +"Tickets"+"\nYour Remaining Amount is: $"+acc.getBalance()+"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
		    					bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
		    					bw.write("For Vanue :"+acc.getVanue()+" And Concert :"+acc.getConcert()+"\n Remaining "+acc.getType()+" Tickets are :"+Type2.getv2c2D()+"\n");
		    					outStream.writeUTF(serverMessage);
		    			        outStream.flush();
		    					
		    					}
		    				}
		    			    else 
		    			    {
		    			    	outStream.writeUTF("No Availble seat for this type");outStream.flush();
		    			    	}
		    			    }
			    		
			    		else if(a1.equalsIgnoreCase("S")) 
			    		{
			    			int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		    int Remain = Type2.getv2c2S()-Num;
		    			    if(Num<Remain) 
		    			    {
		    				int totalAmount=Num*p4;
		    				
		    				if(totalAmount>acc.getCAmount()) 
		    				{
		    				Type2.setv2c2S(0);outStream.writeUTF("Not Enough Available Balance");
	    			        outStream.flush();
	    			        bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
	    			        Addition = 0;
	    			        }
		    				else 
		    				{
		    					Type2.setv2c2S(Num);
		    					int RAmount=acc.getCAmount()-totalAmount;
		    					acc.setCAmount(RAmount);
		    					Addition = acc.getCBalance() + totalAmount;
		    					//acc.setCBalance(Addition);
		    					acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(4));acc.setBalance(RAmount);acc.setType(sp2.get(3));
		    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
		    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"+Num+" "+acc.getType()
		    					                 +"Tickets"+"\nYour Remaining Amount is: $"+acc.getBalance()+"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
		    					bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
		    					bw.write("For Vanue :"+acc.getVanue()+" And Concert :"+acc.getConcert()+"\n Remaining "+acc.getType()+" Tickets are :"+Type2.getv2c2S()+"\n");
		    					outStream.writeUTF(serverMessage);
		    			        outStream.flush();
		    					
		    					}
		    				
		    			    }
		    			else 
		    			{
		    				outStream.writeUTF("No Availble seat for this type");
    			        outStream.flush();
    			        }
		    			    }
			    		
			    		else if(a1.equalsIgnoreCase("P")) 
			    		{
			    			int Num=Character.getNumericValue(sp.get(2).charAt(0));
			    		    int Remain = Type2.getv2c2P()-Num;
		    			    if(Num<Remain) {
		    				int totalAmount=Num*p2;
		    				
		    				if(totalAmount>acc.getCAmount()) 
		    				{
		    					Type2.setv2c2P(0);outStream.writeUTF("Not Enough Available Balance");
	    			        outStream.flush();bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+0+"\n");
	    			        Addition = 0;
	    			        } 
		    				else 
		    				{
		    					int RAmount=acc.getCAmount()-totalAmount;
		    					acc.setCAmount(RAmount);
		    					Addition = acc.getCBalance() + totalAmount;
		    					//acc.setCBalance(Addition);
		    					Type2.setv2c2P(Num);
		    					acc.setVanue(sp.get(0));acc.setConcert(sp.get(1));acc.setDate(sp1.get(4));acc.setBalance(RAmount);acc.setType(sp2.get(1));
		    					serverMessage = "Your Ticket is Confirmed: "+"Vanue: "+acc.getVanue()+"\nConcert: "
		    					                 +acc.getConcert()+"\nConcert Date: "+acc.getDate()+"\n"+Num+" "+acc.getType()
		    					                 +"Tickets"+"\nYour Remaining Amount is: $"+acc.getBalance()+"\nConfirmation Date & Time : " + formatter.format(date)+"\n";
		    					bw.write("From Client No :"+String.valueOf(clientNo)+" Amount Taken: $"+String.valueOf(totalAmount)+"\n");
		    					bw.write("For Vanue :"+acc.getVanue()+" And Concert :"+acc.getConcert()+"\n Remaining "+acc.getType()+" Tickets are :"+Type2.getv2c2P()+"\n");
		    					outStream.writeUTF(serverMessage);
		    			        outStream.flush();
		    					
		    					}
		    				}
		    			else 
		    			
		    			{ 
		    			 outStream.writeUTF("No Available seat for this Type");
					     outStream.flush();
					     }
					     }
			    	 
			     }
			    	 else 
			    	 {
			    	 outStream.writeUTF("Invalid Concert Type");
				     outStream.flush();
				     }
			     
			     }
			     
			     else 
			     {
			     outStream.writeUTF("Invalid Vanue Type....Please Try Valid Entry");
			     outStream.flush();
			     
			     }
			      
			     
			    acc.setCBalance(Addition);
			    firstLine = false;continue;
			        
			    }
				 
			 }
			 outStream.writeUTF("");outStream.flush();outStream.writeUTF("");outStream.flush();outStream.writeUTF("");outStream.flush();outStream.writeUTF("");outStream.flush();
			 //outStream.writeUTF("");outStream.flush();outStream.writeUTF("");outStream.flush();outStream.writeUTF("");outStream.flush();outStream.writeUTF("");outStream.flush();
			 //outStream.writeUTF("");outStream.flush();
			 acc.setCBalance(Addition);
		     int x=acc.getCBalance();
		     Detail.setNum(x);
		     
		     
			 in.close();
	     }
		catch(Exception e) 
	     {
			System.out.println("No file Found");
		outStream.writeUTF("No file found");
	     outStream.flush();
	     }
	     //System.out.println(Detail.getCBalance());
	     bw.write("At :-"+formatter.format(date)+"--->"+String.valueOf("Total Amount Credited to System is:- $"+Detail.getNum())+"\n\n");
	     into.close();
	        in1.close();
	        fr.close();
	        
	      bw.close();
	      inStream.close();
	      outStream.close();
	      serverClient.close();
	      
	    }catch(Exception ex){
	      System.out.println(ex);
	    }finally{
	      System.out.println("Client: " + clientNo + " exit!! ");
	      
	    }
	  }
	}