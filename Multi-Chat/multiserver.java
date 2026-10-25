import java.util.*;
import java.io.*;
import java.net.*;
public class multiserver {
    static Vector<ClientHandler> clients = new Vector<>();
public static void main(String[] args)throws Exception{
ServerSocket serversocket = new ServerSocket(6000);
System.out.println("Chat server started ");
while(true){
    Socket socket = serversocket.accept();
    System.out.println("New client connected ");
    ClientHandler handler = new ClientHandler(socket);
    clients.add(handler);
    handler.start();

}
}    
}
class ClientHandler extends Thread{
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    String name;
    ClientHandler(Socket socket) throws IOException{
        this.socket=socket;
        in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out=new PrintWriter(socket.getOutputStream(),true);
    }
    public void run(){
        try{
            name=in.readLine();
            broadcast(name+" Joined the chat");
            String message;
            while((message=in.readLine())!=null)
            {
                broadcast(name+":"+message);
            }
        }catch(Exception e)
        {
            // handle client disconnection
        }finally{
            try{
                multiserver.clients.remove(this);
                broadcast(name+"Left the chat");
                socket.close();
            }catch(Exception e)
            {
                //cleanup error
            }
        }
    }

void broadcast(String message)
{
    for(ClientHandler client : multiserver.clients){
        if(client !=this)
        {
            client.out.println(message);
        }

    }
}
}
