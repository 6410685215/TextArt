import java.awt.Color;

public class Convert extends Thread {
    public String convertToTextArt(int[] pixelRow) {
        StringBuilder textArt = new StringBuilder();
        for (int pixel : pixelRow) {
            Color color = new Color(pixel);
            int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
            textArt.append(getCharFromPixel(gray));
        }
        return textArt.toString();
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

    private int[] pixelRow;
    private String textArt;
    public Convert(int[] pixelRow) {
        this.pixelRow = pixelRow;
    }

    public void run() {
        try {
            System.out.println("Convert thread is running ..." + Thread.currentThread().getName());
            textArt = convertToTextArt(pixelRow);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        System.out.println("Starting convert thread ...");
        super.start();
    }

    public String getTextArt() {
        return textArt;
    }
}
