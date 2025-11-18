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
        private BufferedImage storeImage = null;
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
        // Draw the image in the top-left (or center if you prefer)
        if (storeImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 10;
            int imgY = 10;
            g.drawImage(storeImage, imgX, imgY, this);
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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Red Window with Cursor Circle");
            Main panel = new Main();
            frame.add(panel);
            // Load image from assets (synchronously) and repaint.
            panel.store = new Imagesfood("store");
            panel.storeImage = panel.store.getImage();
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            panel.repaint();
        });
        // Repaint happens after image load in the EDT

    } 
}

// public class Food (String name, int completed, double price, int rarity, ArrayList<Ingredients> listofingredients )
//   {
//    
//     public String getname()
//     {
//       return(name):
//     }
//   public int getcompleted()
//     {
//       return(completed):
//     }
//   public double getprice()
//     {
//       return(price):
//     }
//   public int getname()
//     {
//       return(rarity):
//     }
//   public boolean completioncheck
//   {
//     for(int i = 0; i < listofingredient.length; i++)
//     {
//       if(listofingredients.get(i).equals(something))
//         {
//           x += 1
//         }
//     }
  
//   }
// public class Ingredients(String name1)
// {
//   // Source - https://stackoverflow.com/a
// // Posted by Emz, modified by community. See post 'Timeline' for change history
// // Retrieved 2025-11-17, License - CC BY-SA 3.0


// }

  
// public class Imagesfood (String imagename, BufferedImage image) {
//      private String imagename;
//      private BufferedImage image;
//      public Imagesfood (String imagename) {
//          this.imagename = imagename;
//          this.image = ImageIO.read(new File(name + ".png"));
//      }

//      public String getName () {
//          return name;
//      }

//      public BufferedImage getImage () {
//          return image;
//      }
// }

 

