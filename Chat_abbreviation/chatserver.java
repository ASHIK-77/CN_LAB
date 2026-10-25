import java.util.*;
import java.net.*;
import java.io.*;
public class chatserver {
public static void main(String[] args)throws Exception{
    //create UDP socket and port 9876
    DatagramSocket socket = new DatagramSocket(9876);
    //buffer to receive data 
    byte[] receivedData = new byte[1024];
    System.out.println("Server started ");
    //create packet to receive client message 

   DatagramPacket receivePacket = new DatagramPacket(receivedData,receivedData.length);
   //receive message from the client 
   socket.receive(receivePacket);

   //convert the byte into string

   String sentence = new String(receivePacket.getData(),0,receivePacket.getLength());

   System.out.println("Received : "+sentence);

   sentence = sentence.replace("tbh","to be honest")
                      .replace("idk","i dont know")
                      .replace("atm","at the moment");

    //convert the translated sentence to byte

    byte[] sendData = sentence.getBytes();

    //create reply packet

    DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,receivePacket.getAddress(),receivePacket.getPort());

    //send the translated sentence

    socket.send(sendPacket);
    System.out.println("translation send");
    socket.close();




}    
}
