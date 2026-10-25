import java.util.*;
import java.net.*;
import java.io.*;
public class timeclient {
public static void main(String[] args)throws Exception{
   DatagramSocket socket = new DatagramSocket();
   Scanner sc= new Scanner(System.in);

   //Read server IP and port 

   System.out.println("Enter the ip address: ");
   String ip = sc.nextLine();
   System.out.println("Enter the port number: ");
   int serverport = Integer.parseInt(sc.nextLine());

   //converting the server ip into InetAdress
   InetAddress serveraddress = InetAddress.getByName(ip);

   //prepare the req message

   String reqmsg ="TIME_REQUEST";
   byte[] sendData = reqmsg.getBytes();

   //create the request packet

   DatagramPacket requestPacket = new DatagramPacket(sendData,sendData.length,serveraddress,serverport);

   //send the req to the server

   socket.send(requestPacket);


   //prepare a buffer for server response

   byte[] receivebuffer = new byte[1024];

   DatagramPacket responsePacket = new DatagramPacket(receivebuffer,receivebuffer.length);

   //wait for the response 

   socket.receive(responsePacket);

   String Timereceived = new String(responsePacket.getData(),0,responsePacket.getLength());

   //display received packet

   System.out.println("Time received from the server: "+Timereceived);
   socket.close();
   sc.close();





}    
}
