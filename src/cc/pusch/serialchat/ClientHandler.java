package cc.pusch.serialchat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Observer roomObserver;
    private ChatRoom currentRoom;
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;
    private boolean connHealthy;

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        roomObserver = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                sendMessageToClient((ServerMsg) arg);
            }
        };
        connHealthy = true;
        try {
            objOut = new ObjectOutputStream(clientSocket.getOutputStream());
            objIn = new ObjectInputStream(clientSocket.getInputStream());
            joinRoom("Lobby");
        } catch (IOException ex) {
            connHealthy = false;
        }
    }

    @Override
    public void run() {
        while (connHealthy) {
            try {
                ClientMsg clientMsg = (ClientMsg) objIn.readObject();
                if (clientMsg == null) {
                    connHealthy = false;
                } else {
                    processInMessage(clientMsg);
                }
            } catch (IOException | ClassNotFoundException ex) {
                connHealthy = false;
            }
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
        }
        currentRoom.deleteObserver(roomObserver);
        ChatRoom.cleanRoom(currentRoom);
    }

    private void sendMessageToClient(ServerMsg serverMsg) {
        try {
            objOut.writeObject(serverMsg);
        } catch (IOException ex) {
            connHealthy = false;
        }
    }

    private void sendStringToClient(String message) {
        sendMessageToClient(new ServerMsg(true, message));
    }

    private void joinRoom(String room) {
        if (currentRoom != null) {
            currentRoom.deleteObserver(roomObserver);
            ChatRoom.cleanRoom(currentRoom);
        }
        currentRoom = ChatRoom.getOrCreateRoom(room);
        currentRoom.addObserver(roomObserver);
        sendMessageToClient(new ServerMsg(true, "Admin: you are now in room " + room, true, ChatRoom.getRoomList()));
    }

    private void processInMessage(ClientMsg clientMsg) {
        if (clientMsg.containsRoomChange()) {
            joinRoom(clientMsg.getNewRoom());
        }
        if (clientMsg.containsChatMsg()) {
            currentRoom.postMessage(clientMsg.getChatMsg());
        }
    }

}