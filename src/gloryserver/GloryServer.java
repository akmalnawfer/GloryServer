/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gloryserver;

import java.io.*;
import java.net.*;
import java.util.concurrent.Executors;

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
    static boolean firstPlayer = false;

    public static void main(String[] args) {

        try {
            int port = 25000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port 25000");

            while (true) {

                socket = serverSocket.accept();
                Executors.newCachedThreadPool().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InputStream is1 = socket.getInputStream();
                            InputStreamReader isr1 = new InputStreamReader(is1);
                            BufferedReader br1 = new BufferedReader(isr1);
                            String username1 = br1.readLine();
                            System.out.println("Message received from client is " + username1);

                            if (firstPlayer && NumberOfPlayers == 1 && connectedUsers[0].equals(username1)) {
                                boolean isTwo = false;
                                while (!isTwo) {
                                    System.out.println("Message received from client is " + username1);
                                    OutputStream os = socket.getOutputStream();
                                    OutputStreamWriter osw = new OutputStreamWriter(os);
                                    BufferedWriter bw = new BufferedWriter(osw);
                                    bw.write(NumberOfPlayers + "\n");
                                    System.out.println("Message sent to the client is " + NumberOfPlayers + "\n");
                                    bw.flush();
                                    if (NumberOfPlayers >= 2) {
                                        isTwo = true;
                                    }
                                }
                                //continue ;
                            }

                            if (NumberOfPlayers < 4) {

                                if (NumberOfPlayers == 0) {
                                    connectedUsers[NumberOfPlayers] = username1;
                                    NumberOfPlayers++;
                                    System.out.println("Message received from client is " + username1);
                                } else if (connectedUsers[NumberOfPlayers] != (username1)) {
                                    connectedUsers[NumberOfPlayers] = username1;
                                    NumberOfPlayers++;
                                    System.out.println("Message received from client is " + username1);
                                }

                                if (NumberOfPlayers == 1) {
                                    firstPlayer = true;
                                } else {
                                    firstPlayer = false;
                                }
                            }

                            if (NumberOfPlayers >= 2 && NumberOfPlayers <= 4) {
                                System.out.println("Staring for " + NumberOfPlayers + " players");

                            }
                            // if (NumberOfPlayers != 1) {
                            OutputStream os = socket.getOutputStream();
                            OutputStreamWriter osw = new OutputStreamWriter(os);
                            BufferedWriter bw = new BufferedWriter(osw);
                            bw.write(NumberOfPlayers + "\n");
                            System.out.println("Message sent to the client is " + NumberOfPlayers + "\n");
                            bw.flush();
//                            }

// code goes here.
                        } catch (Exception ex) {
                        }
                    }
                    ///}).start();
                });
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
