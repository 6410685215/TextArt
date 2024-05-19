import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;

public class Client {
    private Socket client;
    private String host;
    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() {
        try {
            client = new Socket(host, port);
            System.out.println("Connected to server: " + client.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String message) {
        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveMsg() {
        try {
            DataInputStream in = new DataInputStream(client.getInputStream());
            return in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
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
