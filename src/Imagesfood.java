import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

    /**
     * Create a Swing component that displays this image and supports
     * simple mouse dragging. The returned component uses absolute
     * positioning (its bounds are set to [startX,startY,width,height])
     * so you should add it to a container that supports absolute layout
     * (for example a `JLayeredPane` or a container with `null` layout).
     *
     * Example:
     *   JComponent c = img.createDraggableComponent(100, 200);
     *   layeredPane.add(c, JLayeredPane.DRAG_LAYER);
     *
     * @param startX virtual x (pixels) where component will be placed
     * @param startY virtual y (pixels) where component will be placed
     * @return a JComponent you can add to your Swing hierarchy
     */
    public JComponent createDraggableComponent(int startX, int startY) {
        if (image == null) {
            JPanel empty = new JPanel();
            empty.setOpaque(false);
            empty.setBounds(startX, startY, 0, 0);
            return empty;
        }
        DraggableComponent comp = new DraggableComponent(image);
        comp.setBounds(startX, startY, image.getWidth(), image.getHeight());
        return comp;
    }

    // Simple inner component that paints the BufferedImage and allows dragging
    private static class DraggableComponent extends JComponent implements MouseListener, MouseMotionListener {
        private final BufferedImage img;
        private int pressX, pressY;

        DraggableComponent(BufferedImage img) {
            this.img = img;
            setSize(img.getWidth(), img.getHeight());
            addMouseListener(this);
            addMouseMotionListener(this);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            pressX = e.getX();
            pressY = e.getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Container p = getParent();
            if (p == null) return;
            int newX = getX() + e.getX() - pressX;
            int newY = getY() + e.getY() - pressY;
            // keep inside parent bounds
            newX = Math.max(0, Math.min(newX, Math.max(0, p.getWidth() - getWidth())));
            newY = Math.max(0, Math.min(newY, Math.max(0, p.getHeight() - getHeight())));
            setLocation(newX, newY);
            p.repaint();
        }

        @Override public void mouseReleased(MouseEvent e) { }
        @Override public void mouseClicked(MouseEvent e) { }
        @Override public void mouseEntered(MouseEvent e) { }
        @Override public void mouseExited(MouseEvent e) { }
        @Override public void mouseMoved(MouseEvent e) { }
    }

    @Override
    public String toString() {
        return "Imagesfood(" + name + ")";
    }
}
