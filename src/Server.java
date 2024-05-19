import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;

public class Server {
    private static final int PORT = 10000;
    private ServerSocket server;
    private Socket client;

    public Server() {
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            client = server.accept();
            System.out.println("Client connected: " + client.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            client.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String message) {
        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(message);
        } catch (IOException e) {
            // e.printStackTrace();
            // System.out.println("Client disconnected.");
        }
    }

    public String receiveMsg() {
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            return in.readUTF();
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.println("Client disconnected.");
            return null;
        }
    }

    public void sendInt(int number) {
        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeInt(number);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int receiveInt() {
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            return in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void sendIntArray(int[] numbers) {
        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeInt(numbers.length);
            for (int number : numbers) {
                out.writeInt(number);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[] receiveIntArr() {
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            int length = in.readInt();
            int[] numbers = new int[length];
            for (int i = 0; i < length; i++) {
                numbers[i] = in.readInt();
            }
            return numbers;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendImage(Image image) {
        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeInt(image.getImage().getWidth());
            out.writeInt(image.getImage().getHeight());
            for (int x = 0; x < image.getImage().getWidth(); x++) {
                for (int y = 0; y < image.getImage().getHeight(); y++) {
                    out.writeInt(image.getImage().getRGB(x, y));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image receiveImage() {
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            int width = in.readInt();
            int height = in.readInt();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, in.readInt());
                }
            }
            return new Image(image);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
