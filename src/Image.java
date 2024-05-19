import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import javax.imageio.ImageIO;

public class Image {
    private BufferedImage image;

    public Image() {
        this.image = null;
    }

    public Image(String filePath) {
        try {
            this.image = ImageIO.read(new File(filePath));
            this.resize(256, 128);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Image(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public int[] getPixelRow(int y) {
        int width = image.getWidth();
        int[] pixelRow = new int[width];
        for (int x = 0; x < width; x++) {
            Color color = new Color(image.getRGB(x, y));
            pixelRow[x] = color.getGreen();
        }
        return pixelRow;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setGrayScale() {
        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                int pixel = this.image.getRGB(x, y);
                Color color = new Color(pixel);
                int gray = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color grayColor = new Color(gray, gray, gray);
                this.image.setRGB(x, y, grayColor.getRGB());
            }
        }
    }

    public Image get4PartImage(int part) {
        int width = image.getWidth();
        int height = image.getHeight();
        int newHeight = height / 4;
        BufferedImage newImage = new BufferedImage(width, newHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = part * newHeight; y < (part + 1) * newHeight; y++) {
                newImage.setRGB(x, y - part * newHeight, image.getRGB(x, y));
            }
        }
        return new Image(newImage);
    }

    public void margeImage(Image image) {
        int width = this.image.getWidth();
        int height = this.image.getHeight();
        BufferedImage newImage = new BufferedImage(width, height + image.getImage().getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                newImage.setRGB(x, y, this.image.getRGB(x, y));
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < image.getImage().getHeight(); y++) {
                newImage.setRGB(x, y + height, image.getImage().getRGB(x, y));
            }
        }
        this.image = newImage;
    }

    public void resize(int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        resizedImage.createGraphics().drawImage(this.image, 0, 0, width, height, null);
        this.image = resizedImage;
    }

    public static void main(String[] args) {
        Image image = new Image(args[0]);
        image.setGrayScale();
        for (int x = 0; x < 10; x++) {
            System.out.println(image.getPixelRow(0)[x]);
        }
    }
}
