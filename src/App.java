// import java.util.concurrent.TimeUnit;

public class App {

    private void runServer() {
        Server server = new Server();
        server.start();

        Image image = null;
        while (true) {
            String message = server.receiveMsg();
            if (message == null) {
                server.start();
                continue;
            }
            if (message.equals("exit")) {
                server.sendMsg("Goodbye!");
                server.stop();
                break;
            }

            if (message.equals("sendImage")) {
                System.out.println("Receiving image ...");
                image = server.receiveImage();
                System.out.println("Image received.");
            }

            if (image != null) {
                TextArt textArt1 = new TextArt(image.get4PartImage(0));
                TextArt textArt2 = new TextArt(image.get4PartImage(1));
                TextArt textArt3 = new TextArt(image.get4PartImage(2));
                TextArt textArt4 = new TextArt(image.get4PartImage(3));

                try {
                    textArt1.start();
                    textArt2.start();
                    textArt3.start();
                    textArt4.start();
                    textArt1.join();

                    server.sendInt(image.getHeight());
                    String[] textArt1Str = textArt1.getTextArt().split("\n");
                    for (int i = 0; i < textArt1.getHeight(); i++) {
                        server.sendMsg(textArt1Str[i]);
                    }
            
                    textArt2.join();
                    String[] textArt2Str = textArt2.getTextArt().split("\n");
                    for (int i = 0; i < textArt2.getHeight(); i++) {
                        server.sendMsg(textArt2Str[i]);
                    }

                    textArt3.join();
                    String[] textArt3Str = textArt3.getTextArt().split("\n");
                    for (int i = 0; i < textArt3.getHeight(); i++) {
                        server.sendMsg(textArt3Str[i]);
                    }

                    textArt4.join();
                    String[] textArt4Str = textArt4.getTextArt().split("\n");
                    for (int i = 0; i < textArt4.getHeight(); i++) {
                        server.sendMsg(textArt4Str[i]);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void runClient(String filePath) {
        Client client = new Client("localhost", 10000);
        TextArt textArt = new TextArt();

        client.connect();

        client.sendMsg("sendImage");
        Image image = new Image(filePath);
        image.setGrayScale();
        client.sendImage(image);
        System.out.println("Image sent.");

        int height = client.receiveInt();
        for (int i = 0; i < height; i++) {
            textArt.addLine(client.receiveMsg());
        }

        // client.sendMsg("exit");
        // System.out.println(client.receiveMsg());
        client.close();

        textArt.save("output.txt");
        textArt.display();
    }
        
    public static void main(String[] args) throws Exception {
       if (args[0].equals("server")) {
            new App().runServer();
        } else if (args[0].equals("client")) {
            new App().runClient(args[1]);
        } else {
            System.out.println("Invalid argument. Please use 'server' or 'client' as the first argument.");
        }
    }
}
