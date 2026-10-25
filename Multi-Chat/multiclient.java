import java.util.*;
import java.net.*;
import java.io.*;
public class multiclient {
  public static void main(String[] args)throws Exception{
    Socket socket = new Socket("localhost",6000);
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter your name: ");
    String name = sc.nextLine();
    out.println(name);

    Thread receiver = new Thread(() -> {
        try{
            String msg;
            while((msg=in.readLine())!=null)
            {
                System.out.println(msg);
            }

        }catch(Exception e){e.printStackTrace();}
        }
        );
        receiver.start();
        while(true)
        {
            String message = sc.nextLine();
            out.println(message);
        }


  }   
}
