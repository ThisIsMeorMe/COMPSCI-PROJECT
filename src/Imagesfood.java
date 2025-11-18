import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Simple container for named food images.
 */
public class Imagesfood {
    private String name;
    private BufferedImage image;

    /**
     * Create an Imagesfood by loading from the assets/images folder
     * using the provided base name (e.g. "apple" -> assets/images/apple.png).
     */
    public Imagesfood(String imagename) {
        this.name = imagename;
        try {
            // Load from assets/images relative to project root
            this.image = ImageIO.read(new File("assets/images/" + imagename + ".png"));
        } catch (IOException ex) {
            // If loading fails, print a helpful error and leave image null
            System.err.println("Failed to load image for: " + imagename + " - expected assets/images/" + imagename + ".png");
            ex.printStackTrace();
            this.image = null;
        }
    }

    public Imagesfood(String imagename, BufferedImage image) {
        this.name = imagename;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Imagesfood(" + name + ")";
    }
}
