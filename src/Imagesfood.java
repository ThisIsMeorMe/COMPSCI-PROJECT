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
            // First try loading from the classpath (works when assets are bundled)
            String resourcePath = "/assets/images/" + imagename + ".png";
            try {
                java.io.InputStream is = Imagesfood.class.getResourceAsStream(resourcePath);
                if (is != null) {
                    this.image = ImageIO.read(is);
                    is.close();
                    return;
                }
            } catch (Exception e) {
                // continue to filesystem search on any failure
            }

            // Next, search filesystem starting from user.dir and walking up a few levels
            File imageFile = null;
            File cwd = new File(System.getProperty("user.dir"));
            for (int up = 0; up < 5 && cwd != null; up++) {
                File candidate = new File(cwd, "assets/images/" + imagename + ".png");
                if (candidate.exists()) {
                    imageFile = candidate;
                    break;
                }
                cwd = cwd.getParentFile();
            }

            // If not found yet, do a limited recursive search under user.dir (depth-limited)
            if (imageFile == null) {
                try {
                    java.nio.file.Path start = new java.io.File(System.getProperty("user.dir")).toPath();
                    final String targetName = imagename + ".png";
                    java.util.Optional<java.nio.file.Path> found = java.nio.file.Files.walk(start, 4)
                        .filter(p -> p.getFileName().toString().equalsIgnoreCase(targetName))
                        .findFirst();
                    if (found.isPresent()) imageFile = found.get().toFile();
                } catch (Exception ex) {
                    // ignore and fallthrough to not-found handling
                }
            }

            if (imageFile != null) {
                this.image = ImageIO.read(imageFile);
            } else {
                System.err.println("Failed to find image for: " + imagename + " (tried classpath and filesystem)");
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
