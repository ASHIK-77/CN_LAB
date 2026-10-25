import java.util.*;
import java.net.*;
import java.io.*;
public class Fileserver{
    public static void main(String[] args)throws Exception{
        //create TCP server on port 600;
        ServerSocket serversocket= new ServerSocket(6000);
        //create the pid of the server 
        long serverPID = ProcessHandle.current().pid();
        System.out.println("concurrent file server started on port 6000"+serverPID);
        //accept the client continuously
        while(true)
        {
            Socket socket = serversocket.accept();
            System.out.println("New Client Connected:"+socket.getInetAddress());
            //create a separate thread for each client 
            FileHandler handler = new FileHandler(socket,serverPID);
            handler.start();
        }
    }
}
class FileHandler extends Thread{
    private Socket socket;
    private long serverPID;
    FileHandler(Socket socket,long serverPID)
    {
        this.socket = socket;
        this.serverPID = serverPID;
    
    }
    public void run(){
        try(
         BufferedReader in = new BufferedReader(
            new InputStreamReader(socket.getInputStream())
         ); 
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true)
        ){
            //Read requested file name
            String filename = in.readLine();
            System.out.println("Requested file name:"+filename+"from "+socket.getInetAddress());

            //send server PID
            out.println("PID:"+serverPID);
            //check whether the file exist
            File file = new File(filename);
            if((file.exists())&&!file.isDirectory()){
                //tell the client file exist

                out.println("FOUND");
                //read and send file content line by line
                BufferedReader fileReader = new BufferedReader(new FileReader(file));
                String line;
                while((line = fileReader.readLine())!=null)
                {
                    out.println(line);

                }
                fileReader.close();
            }else{
                //tell the client file does not exist
                out.println("Requested file not found("+filename+")");
            }
            //mark the end of response
          out.println("<<END>>");
        }catch(IOException e)
        {e.printStackTrace();}
        finally{
            try{
                socket.close();

            }catch(Exception e)
            {
                //ignore cleanup error
            }
        }
    }

}
