package com.mutzin;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WPWClient extends WebSocketClient {

    private ConnectionManager manager;

    public WPWClient(URI serverUri, ConnectionManager manager) {
        super(serverUri);
        this.manager = manager;
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        manager.onConnected();
    }

    @Override
    public void onMessage(String message) {
        manager.onMessage(message);
        System.out.println(message);
        if(WallpaperChanger.change(message)){
            manager.setChangerMessage();
        } else {
            manager.setChangerFailMessage();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        manager.onDisconnected();
    }

    @Override
    public void onError(Exception ex) {
        manager.onError(ex);
    }
}