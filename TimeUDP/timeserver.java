import java.util.*;
import java.io.*;
import java.net.*;
 public class timeserver {
public static void main(String[] args)throws Exception{
    //Create UDP server socket

    DatagramSocket socket = new DatagramSocket(9876);
    System.out.println("Concurrent timeserver started ");

    //Buffer to receive client request

    byte[] receiveBuffer = new byte[1024];

    //continuously accept client request

    while(true){
        DatagramPacket requestPacket = new DatagramPacket(receiveBuffer,receiveBuffer.length);

        //wait for the client request 

        socket.receive(requestPacket);
        System.out.println("Request received from: "+requestPacket.getAddress()+":"+requestPacket.getPort());

        //create separate thread for this request 

        RequestHandler handler = new RequestHandler(socket,requestPacket);
        handler.start();
         
    }
}    
}
class RequestHandler extends  Thread{
    DatagramSocket socket;
    DatagramPacket requestPacket;

    //constructor

    RequestHandler(DatagramSocket socket,DatagramPacket requestPacket)

    {
        this.socket=socket;
        this.requestPacket=requestPacket;
    }
    public void run(){
        try{
            //get client ip and port number

            InetAddress clientAddress = requestPacket.getAddress();
            int clientPort= requestPacket.getPort();

            //Get the current date and time
            String timeString = new Date().toString();

            //convert time to bytes

            byte[] sendData = timeString.getBytes();

            //create response packet

            DatagramPacket responsePacket = new DatagramPacket(sendData,sendData.length,clientAddress,clientPort);
            //send the response using shared socket

            synchronized(socket)
            {
                socket.send(responsePacket);
            }
            System.out.println("Time sent to : "+clientAddress+":"+clientPort+"->"+timeString);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
