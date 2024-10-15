package lingmod.util.net.websocket;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

import static lingmod.ModCore.logger;

public class WSServer extends WebSocketServer {

    public WSServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        webSocket.send("Welcome to LingMod");
        logger.info("WebSocket Connected");
    }

    @Override
    public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {
        webSocket.send("Bye");
        logger.info("WebSocket closed with code: {} and reason: {} ", code, reason);
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        logger.info("Websocket: {}", s);
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        logger.warn("Websocket: {}", e.getLocalizedMessage());
    }

    @Override
    public void onStart() {
        logger.info("Websocket Started at port{}", this.getPort());
    }
}
