/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gloryserver;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Arthath
 */
public class GloryServer {

    static ServerSocket serverSocket;
    static Socket socket;
    static DataOutputStream out;
    public static Users[] user = new Users[4];
    static DataInputStream in;

    public static void main(String[] args) {
        try {
            System.out.println("Starting Server");
            serverSocket = new ServerSocket(7777);
            System.out.println("Server started...");
            while (true) {
                socket = serverSocket.accept();
                for (int i = 0; i < 4; i++) {
                    if (user[i] == null) {
                        System.out.println("Connection from:" + socket.getInetAddress());
                        out = new DataOutputStream(socket.getOutputStream());
                        in = new DataInputStream(socket.getInputStream());
                        user[i] = new Users(out, in, user, i);
                        Thread thread = new Thread(user[i]);
                        thread.start();
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(GloryServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

class Users implements Runnable {

    DataOutputStream out;
    DataInputStream in;
    Users[] user = new Users[4];
    String name;
    int playerid;
    int playeridin;
    int playerscore;

    public Users(DataOutputStream out, DataInputStream in, Users[] user, int pid) {

        this.out = out;
        this.in = in;
        this.user = user;
        this.playerid = pid;
    }

    public void run() {
        try {
            out.writeInt(playerid);
        } catch (IOException ex) {
            System.out.println("Failed to send player Id");
        }
        while (true) {
            try {
                playeridin = in.readInt();
                playerscore=in.readInt();
                for (int i = 0; i < 4; i++) {
                    if (user[i] != null) {
                        user[i].out.writeInt(playeridin);
                        user[i].out.writeInt(playerscore);
                    }
                }
            } catch (IOException ex) {
                user[playerid] = null;
                break;
            }
        }
    }
}
