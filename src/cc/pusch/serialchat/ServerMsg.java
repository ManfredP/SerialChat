package cc.pusch.serialchat;

import java.io.Serializable;
import java.util.Arrays;

class ServerMsg implements Serializable {
    private boolean hasChatMsg;
    private String chatMsg;
    private boolean hasRoomList;
    private String[] roomList;

    ServerMsg(boolean hasChatMsg, String chatMsg, boolean hasRoomList, String[] roomList) {
        this.hasChatMsg = hasChatMsg;
        this.chatMsg = chatMsg;
        this.hasRoomList = hasRoomList;
        this.roomList = roomList;
    }

    ServerMsg(boolean hasChatMsg, String chatMsg) {
        this.hasChatMsg = hasChatMsg;
        this.chatMsg = chatMsg;
        this.hasRoomList = false;
        this.roomList = null;
    }

    boolean containsChatMsg() {
        return hasChatMsg && chatMsg != null && !chatMsg.isEmpty();
    }

    String getChatMsg() {
        return chatMsg;
    }

    boolean containsRoomList() {
        return hasRoomList && roomList != null && (roomList.length > 0);
    }

    String[] getRoomList() {
        return roomList;
    }

    String dumpString() {
        return "HasChatMsg: " +
                Boolean.toString(hasChatMsg) + "\n" +
                "ChatMsg: " + chatMsg + "\n" +
                "HasRoomList: " +
                Boolean.toString(hasRoomList) + "\n" +
                "RoomList: " + Arrays.toString(roomList) + "\n";
    }
}
