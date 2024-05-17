import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Image {
    private String imgPath;
    private BufferedImage img;

    public Image(String imgPath) {
        this.imgPath = imgPath;
        this.img = load();
        this.img = resize(256, 128);
    }

    private BufferedImage load() {
        try {
            return ImageIO.read(new File(this.imgPath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public BufferedImage getImg() {
        return this.img;
    }

    public BufferedImage resize(int width, int height) {
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        resizedImg.createGraphics().drawImage(this.img, 0, 0, width, height, null);
        return resizedImg;
    }

    public int getWidth() {
        return this.img.getWidth();
    }

    public int getHeight() {
        return this.img.getHeight();
    }

    public int[] getPixelRow(int row) {
        int[] pixelRow = new int[this.getWidth()];
        for (int i = 0; i < this.getWidth(); i++) {
            pixelRow[i] = this.img.getRGB(i, row);
        }
        return pixelRow;
    }
}
