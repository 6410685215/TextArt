import java.io.FileWriter;

public class TextArt extends Thread {
    private StringBuilder textArt;
    private Image image;
    private int height;

    public TextArt() {
        this.textArt = new StringBuilder();
        this.image = null;
        this.height = 0;
    }
    public TextArt(Image image) {
        this.textArt = new StringBuilder();
        this.image = image;
        this.height = 0;
    }

    public void addLine(String line) {
        this.textArt.append(line).append("\n");
        updateHeight();
    }

    private void updateHeight() {
        int newHeight = 0;
        int index = 0;
        while (this.textArt.indexOf("\n", index) != -1) {
            index = this.textArt.indexOf("\n", index) + 1;
            newHeight++;
        }
        this.height = newHeight;
    }

    public void display() {
        System.out.println(this.textArt);
    }

    public String getTextArt() {
        return this.textArt.toString();
    }

    public int getHeight() {
        return this.height;
    }

    public char getCharFromPixel(int pixel) {
        char[] asciiChars = {' ', '.', ',', ':', ';', 'i', '1', 't', 'f', 'L', 'C', 'G', '0', '8', '#', '@'};
        int[] asciiValues = {  0,   8,  16,  32,  48,  64,  80,  96, 112, 128, 144, 160, 176, 192, 208, 224};
        for (int i = 0; i < asciiValues.length; i++) {
            if (pixel < asciiValues[i]) {
                return asciiChars[i];
            }
        }
        return asciiChars[asciiChars.length - 1];
    }

    public void convertToTextArt(int[] pixelRow) {
        for (int pixel : pixelRow) {
            this.textArt.append(getCharFromPixel(pixel));
        }
        this.textArt.append("\n");
        this.height++;
    }

    public void save(String filename) {
        try {
            FileWriter writer = new FileWriter("../" + filename);
            writer.write(this.textArt.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            System.out.println("TextArt thread is running ..." + Thread.currentThread().getName());
            for (int y = 0; y < this.image.getHeight(); y++) {
                int[] pixelRow = this.image.getPixelRow(y);
                convertToTextArt(pixelRow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Image image = new Image("../imgOrg.jpeg");
        // image.setGrayScale();
        TextArt textArt = new TextArt(image);
        try {
            textArt.start();
            textArt.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        textArt.addLine("Hello, World!\nHow are you?");
        System.out.println("TextArt height: " + textArt.getHeight());
    }
}
