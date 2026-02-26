package com.mutzin;

import java.net.URI;

public class ConnectionManager {

    private WPWClient client;
    private WPWClientUI ui;

    public ConnectionManager(WPWClientUI ui) {
        this.ui = ui;
    }

    public void connect(String port) {

        if (port.isEmpty()) {
            ui.setStatus("⚠ 포트를 입력하세요");
            return;
        }

        try {
            ui.setStatus("🔄 " + port + " 포트 연결중...");

            client = new WPWClient(
                    new URI("ws://localhost:" + port + "/ws"),
                    this
            );

            new Thread(() -> {
                try {
                    client.connectBlocking();
                } catch (Exception ex) {
                    ui.setStatus("X" + port + "FAILED TO CONNECT");
                }
            }).start();

        } catch (Exception ex) {
            ui.setStatus("X! WRONG PORT");
        }
    }

    public void onConnected() {
        ui.setStatus("O! CONNECTED");
    }

    public void onMessage(String msg) {
        ui.setChangerText("O! COMMAND: " + msg);
    }

    public void onDisconnected() {
        ui.setStatus("X! DISCONNECTED");
    }

    public void setChangerMessage() {
        ui.setChangerText("O! CHANGED THE WALLPAPER");
    }
    public void setChangerFailMessage() {
        ui.setChangerText("X! FAILED TO CHANGE THE WALLPAPER");
    }
    public void onError(Exception ex) {
        ui.setStatus("X! ERROR");
        ex.printStackTrace();
    }
}