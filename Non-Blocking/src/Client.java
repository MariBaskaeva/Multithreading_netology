import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 8081);
        final SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(socketAddress);
        interact(socketChannel);
    }

    private static void interact(SocketChannel socketChannel) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

            String msg;
            while (true) {
                System.out.println("Enter message for server...");
                msg = scanner.nextLine().trim() + "\r\n";
                if ("end".equals(msg)) break;

                writeMsg(msg, socketChannel);

                Thread.sleep(2000);

                readMsg(socketChannel, inputBuffer);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            socketChannel.close();
        }
    }

    private static void writeMsg(String msg, SocketChannel socketChannel) throws IOException {
        socketChannel.write(ByteBuffer.wrap(
                msg.getBytes(StandardCharsets.UTF_8)));
    }

    private static void readMsg(SocketChannel socketChannel, ByteBuffer inputBuffer) throws IOException {
        int bytesCount = socketChannel.read(inputBuffer);
        System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
        inputBuffer.clear();
    }
}