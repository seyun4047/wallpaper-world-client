package com.mutzin;

import javax.swing.*;
import java.awt.*;

public class WPWClientUI {

    private JFrame frame;
    private JTextField portField;
    private JLabel statusLabel;
    private JLabel wpChanger;

    private ConnectionManager manager;

    public WPWClientUI() {
        manager = new ConnectionManager(this);
        initUI();
    }

    private void initUI() {

        frame = new JFrame("WPW Client");
        frame.setSize(350, 180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        portField = new JTextField(10);
        JButton connectBtn = new JButton("CONNECT");

        statusLabel = new JLabel("INPUT THE PORT NUMBER OF THE SERVER");
        wpChanger = new JLabel("");

        frame.add(new JLabel("Port:"));
        frame.add(portField);
        frame.add(connectBtn);
        frame.add(statusLabel);
        frame.add(wpChanger);

        connectBtn.addActionListener(e ->
                manager.connect(portField.getText())
        );

        frame.setVisible(true);
    }

    public void setStatus(String text) {
        SwingUtilities.invokeLater(() ->
                statusLabel.setText(text)
        );
    }

    public void setChangerText(String text) {
        SwingUtilities.invokeLater(() ->
                wpChanger.setText(text)
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WPWClientUI::new);
    }
}