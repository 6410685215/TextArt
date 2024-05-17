public class App {
    public static void main(String[] args) throws Exception {
        String option = args[0];
        if (option.equals("server")) {
            Server server = new Server(10000);
            while (true) {
                // System.out.println("Pre recInt");
                int[] pixelRow = server.receiveInt();
                System.out.println(pixelRow);
                Convert convert = new Convert(pixelRow);
                convert.start();
                convert.join();
                server.sendMsg(convert.getTextArt());
            }
        } else if (option.equals("client")) {
            Client client = new Client("localhost", 10000);
            Image image = new Image(args[1]);
            TextArt textArt = new TextArt();
            for(int i = 0; i < image.getHeight(); i++) {
                // System.out.println("Sending row " + i);
                client.sendInt(image.getPixelRow(i));
                // System.out.println("Send " + i);
                textArt.addLine(client.receiveMsg());
                // System.out.println("RecMsg");
            }
            textArt.save("output.txt");
            client.close();
        }
    }
}
