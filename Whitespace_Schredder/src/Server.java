import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static final Map<SocketChannel, ByteBuffer> sockets = new ConcurrentHashMap<>();
    private static Selector selector;

    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", 8081));
        serverChannel.configureBlocking(false);

        selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        log("Server start");
        try {
            serverStart(serverChannel);

        } catch (IOException err) {
            System.out.println(err.getMessage());
        } finally {
            serverChannel.close();
        }
    }

    private static void serverStart(ServerSocketChannel serverChannel) throws IOException {
        while (true) {
            selector.select();
            for (SelectionKey key : selector.selectedKeys()) {
                keyCheck(key, serverChannel);
            }
            selector.selectedKeys().clear();
        }
    }

    private static void keyCheck(SelectionKey key, ServerSocketChannel serverChannel) {
        if (key.isValid()) {
            try {
                if (key.isAcceptable()) {
                    accept(serverChannel);
                } else if (key.isReadable()) {
                    read(key);
                } else if (key.isWritable()) {
                    write(key);
                }
            } catch (IOException e) {
                log("error " + e.getMessage());
            }
        }
    }

    private static void accept(ServerSocketChannel serverChannel) throws IOException {
        SocketChannel socketChannel = serverChannel.accept();
        socketChannel.configureBlocking(false);
        log("Connected " + socketChannel.getRemoteAddress());
        sockets.put(socketChannel, ByteBuffer.allocate(1000));
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private static void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = sockets.get(socketChannel);
        int bytesRead = socketChannel.read(buffer);
        log("Reading from " + socketChannel.getRemoteAddress() + ", bytes read=" + bytesRead);

        if (bytesRead == -1) {
            log("Connection closed " + socketChannel.getRemoteAddress());
            sockets.remove(socketChannel);
            socketChannel.close();
        }

        if (bytesRead > 0 && buffer.get(buffer.position() - 1) == '\n') {
            socketChannel.register(selector, SelectionKey.OP_WRITE);
        }
    }

    private static void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = sockets.get(socketChannel);

        buffer.flip();
        String clientMessage = new String(buffer.array(), buffer.position(), buffer.limit());

        String resp = "" + clientMessage.replaceAll("\\s+", "");
        String response = clientMessage.replace("\r\n", "") +
                ", server time=" + System.currentTimeMillis() + "\r\n";

        buffer.clear();
        buffer.put(ByteBuffer.wrap(resp.getBytes()));
        buffer.flip();

        int bytesWritten = socketChannel.write(buffer);
        log("Writing to " + socketChannel.getRemoteAddress() + ", bytes writteb=" + bytesWritten);
        if (!buffer.hasRemaining()) {
            buffer.compact();
            socketChannel.register(selector, SelectionKey.OP_READ);
        }
    }

    private static void log(String message) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + message);
    }
}
