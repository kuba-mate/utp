

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Server {
    private Thread thread;
    private final StringBuilder serverLog = new StringBuilder();
    private final InetSocketAddress connection;
    private final Map<SocketChannel, ClientLog> clientLog = new HashMap<>();
    private static final int BUFFER_SIZE = 1024;
    private static final Charset charset = StandardCharsets.UTF_8;

    private ServerSocketChannel server;
    private Selector selector;

    public Server(String host, int port) {
        connection = new InetSocketAddress(host, port);
    }

    public void startServer() {
        thread = new Thread(()->{
            try {
                selector = Selector.open();
                server = ServerSocketChannel.open();
                server.socket().bind(connection);
                server.configureBlocking(false);
                server.register(selector, SelectionKey.OP_ACCEPT);

                while (!thread.isInterrupted()) {
                    selector.select();
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();

                        if (key.isAcceptable()) {
                            SocketChannel clientSocket = server.accept();
                            clientSocket.configureBlocking(false);
                            clientSocket.register(selector, SelectionKey.OP_READ);
                        }

                        if (key.isReadable()) {
                            SocketChannel clientSocket = (SocketChannel) key.channel();
                            serviceRequest(clientSocket);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        thread.start();
    }

    public void stopServer() {
        thread.interrupt();
    }

    private void writeResponse(SocketChannel sc, String response) throws IOException {
        CharBuffer charBuffer = CharBuffer.wrap(response);
        ByteBuffer byteBuffer = charset.encode(charBuffer);
        sc.write(byteBuffer);
    }

    private void serviceRequest(SocketChannel sc) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        sc.read(buffer);
        String clientMessage = new String(buffer.array()).trim();
        StringBuilder serverResponse = new StringBuilder();
        LocalTime currentTime = LocalTime.now();

        if (clientMessage.contains("login")) {

            clientMessage = clientMessage.replace("login ", "");
            clientLog.put(sc, new ClientLog(clientMessage));
            clientLog.get(sc).buildString("=== " + clientLog.get(sc).getId() + " log start ===\n" + "logged in\n");
            serverLog.append(clientLog.get(sc).getId()).append(" logged in at ").append(currentTime).append("\n");
            serverResponse.append("logged in");

        } else if (clientMessage.contains("bye")) {

            clientLog.get(sc).buildString("logged out\n" + "=== " + clientLog.get(sc).getId() + " log end ===\n");
            serverLog.append(clientLog.get(sc).getId()).append(" logged out at ").append(currentTime).append("\n");

            if (clientMessage.equals("bye and log transfer")) {
                serverResponse.append(clientLog.get(sc).getLog());
            }

        } else {

            String[] arr = clientMessage.split(" ");
            clientLog.get(sc).buildString("Request: " + clientMessage + "\nResult:\n"
                    + Time.passed(arr[0],arr[1]) + "\n");
            serverLog.append(clientLog.get(sc).getId()).append(" request at ").append(currentTime).append(": \"")
                    .append(clientMessage).append("\"").append("\n");
            serverResponse.append(Time.passed(arr[0], arr[1]));

        }
        writeResponse(sc, serverResponse.toString());
    }

    public String getServerLog() {
        return serverLog.toString();
    }
}