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
    static Users[] user = new Users[4];
    static DataInputStream in;

    public static void main(String[] args) {
        try {
            System.out.println("Starting Server");
            serverSocket = new ServerSocket(7777);
            System.out.println("Server started...");
            while (true) {
                socket = serverSocket.accept();
                for (int i = 0; i < 4; i++) {
                    System.out.println("Connection from:" + socket.getInetAddress());
                    out = new DataOutputStream(socket.getOutputStream());
                    in = new DataInputStream(socket.getInputStream());
                    if (user[i] == null) {
                        user[i] = new Users(out, in, user);
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

    public Users(DataOutputStream out, DataInputStream in, Users[] user) {

        this.out = out;
        this.in = in;
        this.user = user;
    }

    public void run() {
        try {
            name = in.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(Users.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (true) {
            try {
                String msg = in.readUTF();
                for (int i = 0; i < 4; i++) {
                    if (user[i] != null) {
                        user[i].out.writeUTF(name + ":" + msg);
                    }
                }
            } catch (IOException ex) {
                this.out = null;
                this.in = null;
            }
        }
    }
}
