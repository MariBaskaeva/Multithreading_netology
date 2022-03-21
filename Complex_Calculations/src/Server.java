import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{

    public static void main(String[] args){
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while(true) {
                Socket socket = serverSocket.accept();

                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()
                        )
                );

                System.out.println("New connection accepted");

                writer.println("Please, write a number: ");
                System.out.println("Trying to get result...");
                int num = Integer.parseInt(reader.readLine());
                writer.println("The result is: " + fibonacci(num));
                System.out.println("We've got result");
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int fibonacci(int num){
        if(num <= 1)    return 0;
        else if(num == 2)    return 1;
        else{
            return fibonacci(num - 1) + fibonacci(num - 2);
        }
    }
}
