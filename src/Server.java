import java.io.*;
import java.net.*;

public class Server {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream receive = null;
    private DataOutputStream send = null;

    private void send(String message) {
        try {
            send.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String receive() {
        try {
            return receive.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendMsg(String message) {
        send(message);
        receive();
    }

    public void acknowledge() {
        send("ack");
    }

    public String receiveMsg() {
        String message = receive();
        acknowledge();
        return message;
    }

    public int[] receiveInt() {
        try {
            int size = receive.readInt();
            if (size == -1) {
                return null;
            }
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = receive.readInt();
            }
            acknowledge();
            return arr;
        } catch (IOException e) {
            System.exit(0);
        }
        return null;
    }

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            socket = server.accept();
            System.out.println("Client accepted");

            receive = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            send = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Test the server
    public static void main(String[] args) {
        Server server = new Server(10000);
        server.sendMsg("Hello from server");
        System.out.println("Receive: " + server.receiveMsg());
    }
}