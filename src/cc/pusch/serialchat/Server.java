package cc.pusch.serialchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 4711;
        Socket s;
        Thread t;
        try {
            ServerSocket ss = new ServerSocket(port);
            while (true) {
                s = ss.accept();
                t = new Thread(new ClientHandler(s));
                t.start();
            }
        } catch (IOException ex) {
            System.exit(1);
        }
    }
}
