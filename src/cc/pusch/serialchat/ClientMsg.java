package cc.pusch.serialchat;

import java.io.Serializable;

class ClientMsg implements Serializable {
    private boolean hasChatMsg;
    private String chatMsg;
    private boolean changeRoom;
    private String newRoom;

    ClientMsg(boolean hasChatMsg, String chatMsg, boolean changeRoom, String newRoom) {
        this.hasChatMsg = hasChatMsg;
        this.chatMsg = chatMsg;
        this.changeRoom = changeRoom;
        this.newRoom = newRoom;
    }

    ClientMsg(boolean hasChatMsg, String chatMsg) {
        this.hasChatMsg = hasChatMsg;
        this.chatMsg = chatMsg;
        this.changeRoom = false;
        this.newRoom = null;
    }

    boolean containsChatMsg() {
        return hasChatMsg && chatMsg != null && !chatMsg.isEmpty();
    }

    String getChatMsg() {
        return chatMsg;
    }

    boolean containsRoomChange() {
        return changeRoom && newRoom != null && !newRoom.isEmpty();
    }

    String getNewRoom() {
        return newRoom;
    }

    String dumpString() {
        return "HasChatMsg: " +
                Boolean.toString(hasChatMsg) + "\n" +
                "ChatMsg: " + chatMsg + "\n" +
                "Contains room change: " +
                Boolean.toString(changeRoom) + "\n" +
                "New room: " + newRoom + "\n";
    }
}
