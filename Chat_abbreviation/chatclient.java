import java.util.*;
import java.net.*;
import java.io.*;
public class chatclient {
    public static void main(String[] args)throws Exception{
        Scanner sc = new Scanner(System.in);
        //UDP SOCKET

        DatagramSocket socket = new DatagramSocket();
        //server IP address
        InetAddress ip = InetAddress.getByName("localhost");
        System.out.println("Enter the sentence: ");
        String sentence = sc.nextLine();
        // converting sentence to byte
        byte[] sendData = sentence.getBytes();
        //creating packet to sent to server

        DatagramPacket sendPacket= new DatagramPacket(sendData,sendData.length,ip,9876);
        //sending packet
        socket.send(sendPacket);

        //packet to recieve translated message 

        byte[] receivedData =  new byte[1024];
        DatagramPacket receivedPacket = new DatagramPacket(receivedData,receivedData.length);

        //recieve response from server

        socket.receive(receivedPacket);

        //converting byte to sentence

        String translated = new String(receivedPacket.getData(),0,receivedPacket.getLength());

        //display the translated sentence

        System.out.println("Translated sentence: "+translated);
        socket.close();
        sc.close();


    }
}
