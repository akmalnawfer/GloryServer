/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gloryserver;

import java.io.*;
import java.net.*;

public class GloryServer {
    public static ServerSocket serverSocket;
    static Socket socket;
    static DataOutputStream out;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
         
            System.out.print("starting server....");
            serverSocket=new ServerSocket(7777);
            System.out.println("\n Server started...");
            socket=serverSocket.accept();  
            System.out.println("Connection from : "+socket.getInetAddress() );
            out=new DataOutputStream(socket.getOutputStream());
            out.writeUTF("this is a test java socket");
            System.out.println("data has been sent");
        }
        catch(Exception e)
        {
            
        }
    }
    
}
