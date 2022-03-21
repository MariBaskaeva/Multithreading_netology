import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            Socket socket = new Socket("localhost", 8080);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()
                    )
            );
            PrintWriter writer = new PrintWriter(
                    new OutputStreamWriter(
                            socket.getOutputStream()
                    ),
                    true
            );
            String request;
            while((request = reader.readLine()) != null){
                System.out.println(request);
                int msg = scanner.nextInt();
                writer.println(msg);
                System.out.println(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

