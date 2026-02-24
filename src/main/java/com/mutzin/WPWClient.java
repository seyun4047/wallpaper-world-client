package com.mutzin;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.http.WebSocket;

public class WPWClient extends WebSocketClient {
    public WPWClient(URI serverUri) {
        super(serverUri);
    }
    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("server connected");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("command get!: "+message);
        WallpaperChanger.change(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("disconnected");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static void main(String[] args) throws Exception {
        WPWClient client = new WPWClient(new URI("ws://localhost:21020/ws"));

        client.connectBlocking();
    }


}
