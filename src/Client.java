import java.io.*;
import java.net.*;

public class Client {
    private Socket socket = null;
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

    public void sendInt(int[] arr) {
        try {
            send.writeInt(arr.length);
            for (int i = 0; i < arr.length; i++) {
                send.writeInt(arr[i]);
            }
            receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            receive.close();
            send.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            receive = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            send = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Test the client
    public static void main(String[] args) {
        Client client = new Client("localhost", 10000);
        System.out.println("Receive: " + client.receiveMsg());
        client.sendMsg("Hello from client");
        client.close();
    }
}
