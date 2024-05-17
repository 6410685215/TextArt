import java.io.FileWriter;
import java.io.IOException;

public class TextArt {
    private StringBuilder textArt;

    public TextArt() {
        this.textArt = new StringBuilder();
    }

    public void addLine(String line) {
        this.textArt.append(line).append("\n");
    }

    public void display() {
        System.out.println(this.textArt);
    }

    public String getTextArt() {
        return this.textArt.toString();
    }

    // save text
    public void save(String filename) {
        try {
            FileWriter writer = new FileWriter("C:/Users/corpu/OneDrive - Thammasat University/CN/CN311/OS-miniPJ/TextArt/"+filename);
            writer.write(this.textArt.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
