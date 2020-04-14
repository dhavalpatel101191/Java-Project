


import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class jav745p2client {
  public static void main(String[] args) throws Exception {
	  
  try{
	  String serverMessage="",Address= "",serverMessage1= "",serverMessage2= "",serverMessage3= "",serverMessage4= "";
	   Address = args[0];
	  int Port=Integer.parseInt(args[1]);
    Socket socket=new Socket(Address,Port);   // address will at ,port number will be at args[1]
    DataInputStream inStream=new DataInputStream(socket.getInputStream());
    DataOutputStream outStream=new DataOutputStream(socket.getOutputStream());
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    
   
    
    byte ab[] = new byte[2000];
    FileInputStream fr= new FileInputStream(args[2]);   //order filename will be at args[2]
    fr.read(ab,0,ab.length);
    OutputStream os=socket.getOutputStream();
    os.write(ab,0,ab.length);
    fr.close();
    
    //Scanner go = new Scanner(System.in);	// no need it
	
    
    //System.out.println("Enter the Balance");   // need to remove 
    
    
     
    int StartingBalance = Integer.parseInt(args[3]);  //go.nextInt();  
    
    outStream.writeUTF(String.valueOf(StartingBalance));
    outStream.flush();
    
  
  
    List<String> order = new ArrayList<String>();
    
    serverMessage=inStream.readUTF(); System.out.println(serverMessage);
    serverMessage1=inStream.readUTF(); System.out.println(serverMessage1);
    serverMessage2=inStream.readUTF(); System.out.println(serverMessage2);
    serverMessage3=inStream.readUTF();  System.out.println(serverMessage3);
    serverMessage4=inStream.readUTF();  System.out.println(serverMessage4);
   
    order.add(serverMessage);
    order.add(serverMessage1);
    order.add(serverMessage2);
    order.add(serverMessage3);
    order.add(serverMessage4);
    
    
    int increase =1;
    
    String name = String.valueOf(increase);
    
     File file = new File(System.getProperty("user.dir") +" Ticket " + name + ".txt");

    while(file.exists())
    {
         increase++;
         
         name = String.valueOf(increase);
         file = new File(System.getProperty("user.dir") +" Ticket " + name + ".txt");
    } 
    if(!file.exists()) {

    try {

        
        file.createNewFile();

        FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
        BufferedWriter bw = new BufferedWriter(fw);
        for(String abc:order)
        	{
        	bw.write(abc);
        	}
        //bw.write(serverMessage1);
        

        System.out.println("Done");
        increase =1;
        bw.close();
       
    }
    catch (IOException e){
        e.printStackTrace();
    }
    
    }
    
    
    
    br.close();
    outStream.close();
    outStream.close();
    socket.close();
  }catch(Exception e){
    System.out.println(e);
  }
  }
}