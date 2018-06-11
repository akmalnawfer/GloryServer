/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gloryserver;

import java.io.*;
import java.net.*;
import java.util.Date;

public class GloryServer {
//    public static ServerSocket serverSocket;
//    static Socket socket;
//    static DataOutputStream out;
//    public int PlayerCount = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(4000);
        String line;
        try {
            while (true) {
                Socket socket = listener.accept();
                BufferedReader readerChannel = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writerChannel = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                try {
                    writerChannel.write(new Date().toString() + "\n\r");
                    writerChannel.flush();

                    while ((line = readerChannel.readLine()) != null) {
                        System.out.println(line);
                    }
                } finally {
                    socket.close();
                }
            }
        } finally {
            listener.close();
        }
    }
//        try{
//         
//            System.out.print("starting server....");
//            serverSocket=new ServerSocket(7777);
//            System.out.println("\n Server started...");
//            socket=serverSocket.accept();  
//            System.out.println("Connection from : "+socket.getInetAddress() );
//            out=new DataOutputStream(socket.getOutputStream());
//            out.writeUTF("this is a test java socket");
//            System.out.println("data has been sent");
//        }
//        catch(Exception e)
//        {
//            
//        }
//    }

}
