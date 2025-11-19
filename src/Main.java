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
    private BufferedImage appleImage = null;
        private Imagesfood apple;
        private BufferedImage apple_pieImage = null;
        private Imagesfood apple_pie;
        private BufferedImage avocadoImage = null;
        private Imagesfood avocado;
        private BufferedImage boar_headImage = null;
        private Imagesfood boar_head;
        private BufferedImage breadImage = null;
        private Imagesfood bread;
        private BufferedImage cheeseImage = null;
        private Imagesfood cheese;
        private BufferedImage cheesecakeImage = null;
        private Imagesfood cheesecake;
        private BufferedImage chickenImage = null;
        private Imagesfood chicken;
        private BufferedImage cookieImage = null;
        private Imagesfood cookie;
        private BufferedImage dragon_fruitImage = null;
        private Imagesfood dragon_fruit;
        private BufferedImage fishImage = null;
        private Imagesfood fish;
        private BufferedImage fried_eggsImage = null;
        private Imagesfood fried_eggs;
        private BufferedImage honeyImage = null;
        private Imagesfood honey;
        private BufferedImage pineappleImage = null;
        private Imagesfood pineapple;
        private BufferedImage pretzelImage = null;
        private Imagesfood pretzel;
        private BufferedImage pumpkin_pieImage = null;
        private Imagesfood pumpkin_pie;
        private BufferedImage shrimpImage = null;
        private Imagesfood shrimp;
        private BufferedImage sushiImage = null;
        private Imagesfood sushi;
        private BufferedImage tboneImage = null;
        private Imagesfood tbone;
        private BufferedImage watermelonImage = null;
        private Imagesfood watermelon;

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
        if (appleImage != null) {
//             // Example: draw on the left side, with a 10px margin
            int imgX = 10;
            int imgY = 10;
            g.drawImage(appleImage, imgX, imgY, this);
        }
        if (apple_pieImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 50;
            int imgY = 10;
            g.drawImage(apple_pieImage, imgX, imgY, this);
        }
        if (avocadoImage != null) {
//             // Example: draw on the left side, with a 10px margin
            int imgX = 100;
            int imgY = 10;
            g.drawImage(avocadoImage, imgX, imgY, this);
        }
        if (boar_headImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 150;
            int imgY = 10;
            g.drawImage(boar_headImage, imgX, imgY, this);
        }
        if (breadImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 200;
            int imgY = 10;
            g.drawImage(breadImage, imgX, imgY, this);
        }
        if (cheeseImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 250;
            int imgY = 10;
            g.drawImage(cheeseImage, imgX, imgY, this);
        }
        if (cheesecakeImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 300;
            int imgY = 10;
            g.drawImage(cheesecakeImage, imgX, imgY, this);
        }
        if (chickenImage != null) {
            // Example: draw on the left side, with a 10px margin
            int imgX = 350;
            int imgY = 10;
            g.drawImage(chickenImage, imgX, imgY, this);
        }
        if (cookieImage != null) {
//             // Example: draw on the left side, with a 10px margin
            int imgX = 400;
            int imgY = 10;
            g.drawImage(cookieImage, imgX, imgY, this);
        }
        if (dragon_fruitImage != null) {
//             // Example: draw on the left side, with a 10px margin
            int imgX = 450;
            int imgY = 10;
            g.drawImage(dragon_fruitImage, imgX, imgY, this);
        }
        if (fishImage != null) {
//             // Example: draw on the left side, with a 10px margin
            int imgX = 500;
            int imgY = 10;
            g.drawImage(fishImage, imgX, imgY, this);
        }
        if (fried_eggsImage != null) {
//             // Example: draw on the left side, with a 10px margin
            int imgX = 550;
            int imgY = 10;
            g.drawImage(fried_eggsImage, imgX, imgY, this);
        }
        if (honeyImage != null) {
//             // Example: draw on the left side, with a 10px margin
            int imgX = 600;
            int imgY = 10;
            g.drawImage(honeyImage, imgX, imgY, this);
        }
        if (pineappleImage != null) {
//             // Example: draw on the left side, with a 10px margin
            int imgX = 650;
            int imgY = 10;
            g.drawImage(pineappleImage, imgX, imgY, this);
        }
        if (pretzelImage != null) {
//             // Example: draw on the left side, with a 10px margin
            int imgX = 700;
            int imgY = 10;
            g.drawImage(pretzelImage, imgX, imgY, this);
        }
        if (pumpkin_pieImage != null) {
//             // Example: draw on the left side, with a 10px margin
            int imgX = 10;
            int imgY = 50;
            g.drawImage(pumpkin_pieImage, imgX, imgY, this);
        }
        if (shrimpImage != null) {
//             // Example: draw on the left side, with a 10px margin
            int imgX = 50;
            int imgY = 50;
            g.drawImage(shrimpImage, imgX, imgY, this);
        }
        if (sushiImage != null) {
//             // Example: draw on the left side, with a 10px margin
            int imgX = 100;
            int imgY = 50;
            g.drawImage(sushiImage, imgX, imgY, this);
        }
        if (tboneImage != null) {
//             // Example: draw on the left side, with a 10px margin
            int imgX = 150;
            int imgY = 50;
            g.drawImage(tboneImage, imgX, imgY, this);
        }
        if (watermelonImage != null) {
//             // Example: draw on the left side, with a 10px margin
            int imgX = 200;
            int imgY = 50;
            g.drawImage(watermelonImage, imgX, imgY, this);
        }

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
            panel.apple = new Imagesfood("apple");
            panel.appleImage = panel.apple.getImage();
            panel.apple_pie = new Imagesfood("apple_pie");
            panel.apple_pieImage = panel.apple_pie.getImage();
            panel.avocado = new Imagesfood("avocado");
            panel.avocadoImage = panel.avocado.getImage();
            panel.boar_head = new Imagesfood("boar_head");
            panel.boar_headImage = panel.boar_head.getImage();
            panel.bread = new Imagesfood("bread");
            panel.breadImage = panel.bread.getImage();
            panel.cheese = new Imagesfood("cheese");
            panel.cheeseImage = panel.cheese.getImage();
            panel.cheesecake = new Imagesfood("cheesecake");
            panel.cheesecakeImage = panel.cheesecake.getImage();
            panel.chicken = new Imagesfood("chicken");
            panel.chickenImage = panel.chicken.getImage();
            panel.cookie = new Imagesfood("cookie");
            panel.cookieImage = panel.cookie.getImage();
            panel.dragon_fruit = new Imagesfood("dragon_fruit");
            panel.dragon_fruitImage = panel.dragon_fruit.getImage();
            panel.fish = new Imagesfood("fish");
            panel.fishImage = panel.fish.getImage();
            panel.fried_eggs = new Imagesfood("fried_eggs");
            panel.fried_eggsImage = panel.fried_eggs.getImage();
            panel.honey = new Imagesfood("honey");
            panel.honeyImage = panel.honey.getImage();
            panel.pineapple = new Imagesfood("pineapple");
            panel.pineappleImage = panel.pineapple.getImage();
            panel.pretzel = new Imagesfood("pretzel");
            panel.pretzelImage = panel.pretzel.getImage();
            panel.pumpkin_pie = new Imagesfood("pumpkin_pie");
            panel.pumpkin_pieImage = panel.pumpkin_pie.getImage();
            panel.shrimp = new Imagesfood("shrimp");
            panel.shrimpImage = panel.shrimp.getImage();
            panel.sushi = new Imagesfood("sushi");
            panel.sushiImage = panel.sushi.getImage();
            panel.tbone = new Imagesfood("t-bone");
            panel.tboneImage = panel.tbone.getImage();
            panel.watermelon = new Imagesfood("watermelon");
            panel.watermelonImage = panel.watermelon.getImage();
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








// import java.awt.*;
// import java.awt.event.*;
// import java.awt.image.BufferedImage;
// import javax.swing.*;


// public class Main extends JPanel implements MouseMotionListener
// {
//   private int mouseX = -10;
//   private int mouseY = -10;
//   public Main() {
//         addMouseMotionListener(this);
//     }

//         // Image loaded via Imagesfood
//         private BufferedImage appleImage = null;
//         private Imagesfood apple;
//         private BufferedImage apple_pieImage = null;
//         private Imagesfood apple_pie;
//         private BufferedImage avocadoImage = null;
//         private Imagesfood avocado;
//         private BufferedImage boar_headImage = null;
//         private Imagesfood boar_head;
//         private BufferedImage breadImage = null;
//         private Imagesfood bread;
//         private BufferedImage cheeseImage = null;
//         private Imagesfood cheese;
//         private BufferedImage cheesecakeImage = null;
//         private Imagesfood cheesecake;
//         private BufferedImage chickenImage = null;
//         private Imagesfood chicken;
//         private BufferedImage cookieImage = null;
//         private Imagesfood cookie;
//         private BufferedImage dragon_fruitImage = null;
//         private Imagesfood dragon_fruit;
//         private BufferedImage fishImage = null;
//         private Imagesfood fish;
//         private BufferedImage fried_eggsImage = null;
//         private Imagesfood fried_eggs;
//         private BufferedImage honeyImage = null;
//         private Imagesfood honey;
//         private BufferedImage pineappleImage = null;
//         private Imagesfood pineapple;
//         private BufferedImage pretzelImage = null;
//         private Imagesfood pretzel;
//         private BufferedImage pumpkin_pieImage = null;
//         private Imagesfood pumpkin_pie;
//         private BufferedImage shrimpImage = null;
//         private Imagesfood shrimp;
//         private BufferedImage sushiImage = null;
//         private Imagesfood sushi;
//         private BufferedImage tboneImage = null;
//         private Imagesfood tbone;
//         private BufferedImage watermelonImage = null;
//         private Imagesfood watermelon;
        


//     @Override
//     public void paintComponent(Graphics g) {
//         super.paintComponent(g);


//         // Red background
//         g.setColor(Color.RED);
//         g.fillRect(0, 0, getWidth(), getHeight());


//         // Black circle at mouse position
//         g.setColor(Color.BLACK);
//         g.fillOval(mouseX - 15, mouseY - 15, 30, 30);
//         //g.drawImage(appleImage, x, y, this);
//         //Draw the image in the top-left (or center if you prefer)
//         if (appleImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 10;
//             int imgY = 10;
//             g.drawImage(appleImage, imgX, imgY, this);
//         }
//         if (apple_pieImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 50;
//             int imgY = 10;
//             g.drawImage(apple_pieImage, imgX, imgY, this);
//         }
//         if (avocadoImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 100;
//             int imgY = 10;
//             g.drawImage(avocadoImage, imgX, imgY, this);
//         }
//         if (boar_headImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 150;
//             int imgY = 10;
//             g.drawImage(boar_headImage, imgX, imgY, this);
//         }
//         if (breadImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 200;
//             int imgY = 10;
//             g.drawImage(breadImage, imgX, imgY, this);
//         }
//         if (cheeseImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 250;
//             int imgY = 10;
//             g.drawImage(cheeseImage, imgX, imgY, this);
//         }
//         if (cheesecakeImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 300;
//             int imgY = 10;
//             g.drawImage(cheesecakeImage, imgX, imgY, this);
//         }
//         if (chickenImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 350;
//             int imgY = 10;
//             g.drawImage(chickenImage, imgX, imgY, this);
//         }
//         if (cookieImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 400;
//             int imgY = 10;
//             g.drawImage(cookieImage, imgX, imgY, this);
//         }
//         if (dragon_fruitImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 450;
//             int imgY = 10;
//             g.drawImage(dragon_fruitImage, imgX, imgY, this);
//         }
//         if (fishImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 500;
//             int imgY = 10;
//             g.drawImage(fishImage, imgX, imgY, this);
//         }
//         if (fried_eggsImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 550;
//             int imgY = 10;
//             g.drawImage(fried_eggsImage, imgX, imgY, this);
//         }
//         if (honeyImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 600;
//             int imgY = 10;
//             g.drawImage(honeyImage, imgX, imgY, this);
//         }
//         if (pineappleImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 650;
//             int imgY = 10;
//             g.drawImage(pineappleImage, imgX, imgY, this);
//         }
//         if (pretzelImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 700;
//             int imgY = 10;
//             g.drawImage(pretzelImage, imgX, imgY, this);
//         }
//         if (pumpkin_pieImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 10;
//             int imgY = 50;
//             g.drawImage(pumpkin_pieImage, imgX, imgY, this);
//         }
//         if (shrimpImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 50;
//             int imgY = 50;
//             g.drawImage(shrimpImage, imgX, imgY, this);
//         }
//         if (sushiImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 100;
//             int imgY = 50;
//             g.drawImage(sushiImage, imgX, imgY, this);
//         }
//         if (tboneImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 150;
//             int imgY = 50;
//             g.drawImage(tboneImage, imgX, imgY, this);
//         }
//         if (watermelonImage != null) {
//             // Example: draw on the left side, with a 10px margin
//             int imgX = 200;
//             int imgY = 50;
//             g.drawImage(watermelonImage, imgX, imgY, this);
//         }


//     }


//     // Update mouse position when moved
//     @Override
//     public void mouseMoved(MouseEvent e) {
//         mouseX = e.getX();
//         mouseY = e.getY();
//         repaint(); // redraw
//     }


//     @Override
//     public void mouseDragged(MouseEvent e) {
//         mouseMoved(e);
//     }


//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> {
//             JFrame frame = new JFrame("Red Window with Cursor Circle");
//             Main panel = new Main();
//             frame.add(panel);
//             // Load image from assets (synchronously) and repaint.
//             panel.apple = new Imagesfood("apple");
//             panel.appleImage = panel.apple.getImage();
//             panel.apple_pie = new Imagesfood("apple_pie");
//             panel.apple_pieImage = panel.apple_pie.getImage();
//             panel.avocado = new Imagesfood("avocado");
//             panel.avocadoImage = panel.avocado.getImage();
//             panel.boar_head = new Imagesfood("boar_head");
//             panel.boar_headImage = panel.boar_head.getImage();
//             panel.bread = new Imagesfood("bread");
//             panel.breadImage = panel.bread.getImage();
//             panel.cheese = new Imagesfood("cheese");
//             panel.cheeseImage = panel.cheese.getImage();
//             panel.cheesecake = new Imagesfood("cheesecake");
//             panel.cheesecakeImage = panel.cheesecake.getImage();
//             panel.chicken = new Imagesfood("chicken");
//             panel.chickenImage = panel.chicken.getImage();
//             panel.cookie = new Imagesfood("cookie");
//             panel.cookieImage = panel.cookie.getImage();
//             panel.dragon_fruit = new Imagesfood("dragon_fruit");
//             panel.dragon_fruitImage = panel.dragon_fruit.getImage();
//             panel.fish = new Imagesfood("fish");
//             panel.fishImage = panel.fish.getImage();
//             panel.fried_eggs = new Imagesfood("fried_eggs");
//             panel.fried_eggsImage = panel.fried_eggs.getImage();
//             panel.honey = new Imagesfood("honey");
//             panel.honeyImage = panel.honey.getImage();
//             panel.pineapple = new Imagesfood("pineapple");
//             panel.pineappleImage = panel.pineapple.getImage();
//             panel.pretzel = new Imagesfood("pretzel");
//             panel.pretzelImage = panel.pretzel.getImage();
//             panel.pumpkin_pie = new Imagesfood("pumpkin_pie");
//             panel.pumpkin_pieImage = panel.pumpkin_pie.getImage();
//             panel.shrimp = new Imagesfood("shrimp");
//             panel.shrimpImage = panel.shrimp.getImage();
//             panel.sushi = new Imagesfood("sushi");
//             panel.sushiImage = panel.sushi.getImage();
//             panel.tbone = new Imagesfood("t-bone");
//             panel.tboneImage = panel.tbone.getImage();
//             panel.watermelon = new Imagesfood("watermelon");
//             panel.watermelonImage = panel.watermelon.getImage();
//             frame.setSize(800, 600);
//             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//             frame.setVisible(true);
//             panel.repaint();
//         });
//         // Repaint happens after image load in the EDT

//     } 
// }

// // public class Food (String name, int completed, double price, int rarity, ArrayList<Ingredients> listofingredients )
// //   {
// //    
// //     public String getname()
// //     {
// //       return(name):
// //     }
// //   public int getcompleted()
// //     {
// //       return(completed):
// //     }
// //   public double getprice()
// //     {
// //       return(price):
// //     }
// //   public int getname()
// //     {
// //       return(rarity):
// //     }
// //   public boolean completioncheck
// //   {
// //     for(int i = 0; i < listofingredient.length; i++)
// //     {
// //       if(listofingredients.get(i).equals(something))
// //         {
// //           x += 1
// //         }
// //     }
  
// //   }
// // public class Ingredients(String name1)
// // {
// //   // Source - https://stackoverflow.com/a
// // // Posted by Emz, modified by community. See post 'Timeline' for change history
// // // Retrieved 2025-11-17, License - CC BY-SA 3.0


// // }

  
// // public class Imagesfood (String imagename, BufferedImage image) {
// //      private String imagename;
// //      private BufferedImage image;
// //      public Imagesfood (String imagename) {
// //          this.imagename = imagename;
// //          this.image = ImageIO.read(new File(name + ".png"));
// //      }

// //      public String getName () {
// //          return name;
// //      }

// //      public BufferedImage getImage () {
// //          return image;
// //      }
// // }

 

