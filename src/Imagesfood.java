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
            // Try multiple possible paths for assets/images
            String[] possiblePaths = {
                "assets/images/" + imagename + ".png",
                "../assets/images/" + imagename + ".png",
                "../../assets/images/" + imagename + ".png",
                System.getProperty("user.dir") + "/assets/images/" + imagename + ".png",
                System.getProperty("user.dir") + "/COMPSCI-PROJECT-main677777/assets/images/" + imagename + ".png"
            };
            
            File imageFile = null;
            for (String path : possiblePaths) {
                File f = new File(path);
                if (f.exists()) {
                    imageFile = f;
                    break;
                }
            }
            
            if (imageFile != null) {
                this.image = ImageIO.read(imageFile);
            } else {
                System.err.println("Failed to find image for: " + imagename + " in any of the expected locations");
                this.image = null;
            }
        } catch (IOException ex) {
            System.err.println("Failed to load image for: " + imagename);
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
