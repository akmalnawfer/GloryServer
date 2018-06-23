/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gloryserver;

import java.net.*;
import java.io.*;

/**
 *
 * @author Arthath
 */
public class GloryServer {

    private static Socket socket;
    static DataOutputStream out;
    static DataInputStream in;
    static String Username;
    static int NumberOfPlayers = 0;
    static String[] connectedUsers = new String[4];

    public static void main(String[] args) {
        try {
            int port = 25000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port 25000");
            while (true) {
                socket = serverSocket.accept();

                if (NumberOfPlayers < 4) {
                    InputStream is = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String username = br.readLine();
                    connectedUsers[NumberOfPlayers] = username;
                    System.out.println("Message received from client is " + username);
                    NumberOfPlayers++;
                }

                if (NumberOfPlayers >= 2 && NumberOfPlayers <= 4) {
                    System.out.println("Staring for " + NumberOfPlayers + " players");
//                    OutputStream os = socket.getOutputStream();
//                    OutputStreamWriter osw = new OutputStreamWriter(os);
//                    BufferedWriter bw = new BufferedWriter(osw);
//                    bw.write(NumberOfPlayers+"\n");
//                    System.out.println("Message sent to the client is " + NumberOfPlayers + "\n");
//                    bw.flush();
                }
                OutputStream os = socket.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osw);
                    bw.write(NumberOfPlayers+"\n");
                    System.out.println("Message sent to the client is " + NumberOfPlayers + "\n");
                    bw.flush();
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
    }
}
