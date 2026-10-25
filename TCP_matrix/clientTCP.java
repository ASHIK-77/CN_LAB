import java.util.*;
import java.io.*;
import java.net.*;
public class clientTCP {
 public static void main(String[] args)throws Exception{
    Socket socket= new Socket("localhost",9002);
    Scanner sc = new Scanner(System.in);
    Random rand = new Random();
    System.out.println("Enter the order of matrix: ");
    int n = sc.nextInt();
    if(n<=0)
    {
        System.out.println("Enter a positive integer ");
        socket.close();sc.close();
        return;

    }
    int[][] mat = new int[n][n];
    for(int i=0;i<n;i++)
    {
        for(int j =0;j<n;j++)
        {
            mat[i][j]=rand.nextInt(50)+1;
        }
    }
    int type = rand.nextInt(4);
    switch(type)
    {
        case 0 :
                for(int i=0;i<n;i++)
                {
                    for(int j=0;j<n;j++)
                    {
                        if(i>j)
                        {
                          mat[i][j]=0;
                        }
                    }
                }
                break;
            case 1 :
                for(int i=0;i<n;i++)
                {
                    for(int j=0;j<n;j++)
                    {
                        if(i<j)
                        {
                          mat[i][j]=0;
                        }
                    }
                }
                break;
                case 2 :
                for(int i=0;i<n;i++)
                {
                    for(int j=0;j<n;j++)
                    {
                        if(i!=j)
                        {
                          mat[i][j]=0;
                        }
                    }
                }
                break;    
            default: break;
    }
    System.out.println("Generated matrix");
    
                for(int i=0;i<n;i++)
                {
                    for(int j=0;j<n;j++)
                    {
                        System.out.println(mat[i][j]+"");
                        System.out.println();
                    }
                }
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeInt(n);
        out.writeObject(mat);
        out.flush();

        DataInputStream in = new DataInputStream(socket.getInputStream());

        String result = in.readUTF();
        System.out.println("MATRIX TYPE: "+result);
        socket.close();
        sc.close();
                
 }   
}
