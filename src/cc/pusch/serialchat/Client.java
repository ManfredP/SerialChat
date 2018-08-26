package cc.pusch.serialchat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

class Client {
    private static ObjectOutputStream objOut;
    private static Socket s;

    public static void main(String[] args) {
        s = new Socket();
        ServerMsg serverMsg;
        ClientUI clientUI = new ClientUI();
        try {
            s.connect(new InetSocketAddress("127.0.0.1", 4711));
            objOut = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream objIn = new ObjectInputStream(s.getInputStream());
            while ((serverMsg = (ServerMsg) objIn.readObject()) != null) {
                if (serverMsg.containsChatMsg()) {
                    clientUI.displayNewMessage(serverMsg.getChatMsg());
                }
                if (serverMsg.containsRoomList()) {
                    clientUI.updateRoomList(serverMsg.getRoomList());
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            exit(1);
        }
    }

    static void sendString(String message) {
        try {
            objOut.writeObject(new ClientMsg(true, message));
        } catch (IOException ex) {
            exit(1);
        }
    }

    static void sendRoomChange(String room) {
        try {
            objOut.writeObject(new ClientMsg(false, null, true, room));
        } catch (IOException ex) {
            exit(1);
        }
    }

    static void exit(int exitCode) {
        try {
            s.close();
        } catch (IOException ioEx) {
        }
        System.exit(exitCode);
    }
}
