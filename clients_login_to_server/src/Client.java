/**
 * @author Kryzhanivskyi Denys S18714
 */



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Client {
    private InetSocketAddress connection;
    private SocketChannel socketClient;
    private String id;
    private static final Charset charset = StandardCharsets.UTF_8;

    public Client(String host, int port, String id) {
        this.connection = new InetSocketAddress(host, port);
        this.id = id;
    }

    public void connect() {
        try {
            socketClient = SocketChannel.open(connection);
            socketClient.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String send(String req) {
        String response = "";
        try {
            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
            writeBuffer.put(charset.encode(req));
            writeBuffer.flip();
            socketClient.write(writeBuffer);

            ByteBuffer readBuffer = ByteBuffer.allocate(1024);

            int bytesRead = socketClient.read(readBuffer);
            while(bytesRead == 0){
                Thread.sleep(10);
                bytesRead = socketClient.read(readBuffer);
            }

            while(bytesRead > 0){
                readBuffer.flip();
                CharBuffer charBuffer = charset.decode(readBuffer);
                response = response + charBuffer;
                bytesRead = socketClient.read(readBuffer);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }

    public String getId() {
        return id;
    }
}