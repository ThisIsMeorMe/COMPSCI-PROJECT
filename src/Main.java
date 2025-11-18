import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class Main extends JPanel implements MouseMotionListener
{
  private int mouseX = -10;
  private int mouseY = -10;
  public Main() {
        addMouseMotionListener(this);
    }

        // Image loaded via Imagesfood
        // keep original so we can rescale dynamically to the window size
        private BufferedImage storeImageOriginal = null;
        // cached scaled image for current panel size
        private BufferedImage storeImageScaled = null;
        private int lastScaledW = -1;
        private int lastScaledH = -1;
        private Imagesfood store;
        


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        // Red background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());


        // Black circle at mouse position
        g.setColor(Color.BLACK);
        g.fillOval(mouseX - 15, mouseY - 15, 30, 30);
        // g.drawImage(storeImage, x, y, this);
        // Draw the image scaled to fit the panel while preserving aspect ratio
        if (storeImageOriginal != null) {
            int margin = 10;
            int availW = Math.max(1, getWidth() - margin * 2);
            int availH = Math.max(1, getHeight() - margin * 2);
            int imgW = storeImageOriginal.getWidth();
            int imgH = storeImageOriginal.getHeight();
            double ratio = Math.min((double) availW / imgW, (double) availH / imgH);
            int targetW = Math.max(1, (int) Math.round(imgW * ratio));
            int targetH = Math.max(1, (int) Math.round(imgH * ratio));

            // Only rescale when panel size (targetW/targetH) changes
            if (targetW != lastScaledW || targetH != lastScaledH || storeImageScaled == null) {
                storeImageScaled = getScaledImage(storeImageOriginal, targetW, targetH);
                lastScaledW = targetW;
                lastScaledH = targetH;
            }

            int imgX = (getWidth() - targetW) / 2;
            int imgY = (getHeight() - targetH) / 2;
            if (storeImageScaled != null) {
                g.drawImage(storeImageScaled, imgX, imgY, null);
            }
        }

    }


    // Update mouse position when moved
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        repaint(); // redraw
    }


    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    /**
     * High-quality image scaler. Returns a new BufferedImage sized to targetW/targetH.
     */
    private BufferedImage getScaledImage(BufferedImage src, int targetW, int targetH) {
        if (src == null) return null;
        BufferedImage resized = new BufferedImage(targetW, targetH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resized.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(src, 0, 0, targetW, targetH, null);
        g2.dispose();
        return resized;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Red Window with Cursor Circle");
            Main panel = new Main();
            frame.add(panel);
            // Load image from assets (synchronously) and repaint.
            panel.store = new Imagesfood("store");
            // keep original; painting will scale to fit the window
            panel.storeImageOriginal = panel.store.getImage();
            panel.storeImageScaled = null; // ensure cached scaled image will be created on first paint
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            panel.repaint();
          // JLabel label = new JLabel("This is a label with static text.");
          //   frame.add(label);

          //   // Using a JTextField to display editable or dynamic text
          //   JTextField textField = new JTextField("Initial text in a text field", 20);
          //   frame.add(textField);

          //   frame.setVisible(true);
        });
        // Repaint happens after image load in the EDT

    } 
}


 

