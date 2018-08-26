package cc.pusch.serialchat;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

class ChatRoom extends Observable {
    private static Map<String, ChatRoom> chatRooms = new HashMap<>();
    private String name;

    static ChatRoom getOrCreateRoom(String name) {
        if (!chatRooms.containsKey(name)) {
            chatRooms.put(name, new ChatRoom(name));
            for (ChatRoom chatRoom : chatRooms.values()) {
                chatRoom.sendRoomUpdates();
            }
        }
        return chatRooms.get(name);
    }

    static void cleanRoom(ChatRoom room) {
        if (room.countObservers() == 0) {
            chatRooms.remove(room.name);
            for (ChatRoom chatRoom : chatRooms.values()) {
                chatRoom.sendRoomUpdates();
            }
        }
    }

    static String[] getRoomList() {
        return chatRooms.keySet().toArray(new String[0]);
    }

    private ChatRoom(String name) {
        this.name = name;
    }

    synchronized void postMessage(String message) {
        setChanged();
        notifyObservers(new ServerMsg(true, message, false, null));
    }

    private void sendRoomUpdates() {
        String[] chatRoomList = chatRooms.keySet().toArray(new String[0]);
        setChanged();
        notifyObservers(new ServerMsg(false, null, true, chatRoomList));
    }

}
