import java.util.*;
import java.io.*;
import java.net.*;
public class Fileclient {public static void main(String[] args)throws Exception{
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter server IP :");
    String serverip = sc.nextLine();
    System.out.println("Enter the port number: ");
    int serverPort = Integer.parseInt(sc.nextLine());
    //connect to the server
    Socket socket = new Socket(serverip,serverPort);
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
    //Read file name from  the user 
    System.out.println("Enter the dile name :");
    String filename = sc.nextLine();
    //send the file to the server
    out.println(filename);
    //Read the server PID
    String  pidline = in.readLine();
    //read file status 
    String status = in.readLine();

    System.out.println();
    System.out.println("Response from server("+pidline+")");
    if(status.equals("FOUND")){
        System.out.println("===FILE CONTENT===");
        String line;
        //Read until the end marker 
        while(!(line=in.readLine()).equals("<<END>>"))
        {
            System.out.println(line);

        }
    }else{
        //read the error meassage 
        String message = in.readLine();
        //consume the end marker
        in.readLine();
        System.out.println(message);
    }
socket.close();
sc.close();
}    
}

