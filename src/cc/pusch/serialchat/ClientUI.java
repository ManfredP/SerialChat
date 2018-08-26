package cc.pusch.serialchat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class ClientUI extends Frame {
    private TextField tf_input, tf_changeRoom;
    private TextArea ta_output;
    private List li_roomList;

    ClientUI() throws HeadlessException {
        tf_input = new TextField();
        ta_output = new TextArea(null, 20, 50, TextArea.SCROLLBARS_VERTICAL_ONLY);
        ta_output.setEditable(false);
        tf_changeRoom = new TextField();
        Label la_roomList = new Label("Room list", Label.CENTER);
        Label la_newRoom = new Label("New room", Label.CENTER);
        Label la_chatWindow = new Label("Chat window", Label.CENTER);
        li_roomList = new List(12, false);
        Button b_send = new Button("Send");
        Button b_changeRoom = new Button("Change room");
        ActionListener postMessage = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.sendString(tf_input.getText());
                tf_input.setText("");
                tf_input.requestFocus();
            }
        };
        ActionListener changeRoom = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == b_changeRoom) {
                    if (!tf_changeRoom.getText().equals("")) {
                        Client.sendRoomChange(tf_changeRoom.getText());
                        tf_changeRoom.setText("");
                    } else if (li_roomList.getSelectedItem() != null) {
                        Client.sendRoomChange(li_roomList.getSelectedItem());
                    }
                } else if (e.getSource() == li_roomList && li_roomList.getSelectedItem() != null) {
                    Client.sendRoomChange(li_roomList.getSelectedItem());
                } else if (e.getSource() == tf_changeRoom && !tf_changeRoom.getText().equals("")) {
                    Client.sendRoomChange(tf_changeRoom.getText());
                }
                tf_input.requestFocus();
            }
        };
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Client.exit(0);
            }
        });
        setTitle("Chat Client");
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(5, 5, 5, 5);
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        add(la_chatWindow, c);
        c.gridy = 1;
        c.gridheight = 4;
        add(ta_output, c);
        c.gridy = 5;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(tf_input, c);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0;
        add(la_roomList, c);
        c.gridy = 1;
        add(li_roomList, c);
        c.gridy = 2;
        add(la_newRoom, c);
        c.gridy = 3;
        add(tf_changeRoom, c);
        c.gridy = 4;
        add(b_changeRoom, c);
        c.gridy = 5;
        add(b_send, c);
        b_send.addActionListener(postMessage);
        tf_input.addActionListener(postMessage);
        li_roomList.addActionListener(changeRoom);
        tf_changeRoom.addActionListener(changeRoom);
        b_changeRoom.addActionListener(changeRoom);

        pack();
        setVisible(true);
        tf_input.requestFocus();
    }

    void displayNewMessage(String message) {
        ta_output.append(message + "\n");
    }

    void updateRoomList(String[] roomlist) {
        li_roomList.removeAll();
        for (String aRoomlist : roomlist) {
            li_roomList.add(aRoomlist);
        }
    }
}
