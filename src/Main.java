//  IMPORTS
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class Main extends JPanel implements MouseMotionListener
{
  // The variables store coordiates 
  private int mouseX = -10;
  private int mouseY = -10;
  public Main()
  {
    addMouseMotionListener(this);
  }

  @Override
  // Updates the varibles
  public void mouseMoved(MouseEvent e)
  {
    mouseX = e.getX();
    mouseY = e.getY();
  }

  @Override
  public void mouseDragged(MouseEvent e)
  {
    
  }
    
  public int getMouseX()
  {
    return mouseX;
  }

  public int getMouseY()
  {
    return mouseY;
  }
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
  private BufferedImage man_idleImage = null;
  private Imagesfood man_idle;
  private BufferedImage storeImageOriginal = null;
  private BufferedImage storeImageScaled = null;
  private int lastScaledW = -1;
  private int lastScaledH = -1;
  private Imagesfood store;



  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    // Creates a red background
    g.setColor(Color.RED);
    g.fillRect(0, 0, getWidth(), getHeight());

    // Creates a black circle at mouse position
    g.setColor(Color.BLACK);
    g.fillOval(mouseX - 15, mouseY - 15, 30, 30);

    // Renders all the images
    if (storeImageOriginal != null)
    {
      int margin = 10;
      int availW = Math.max(1, getWidth() - margin * 2);
      int availH = Math.max(1, getHeight() - margin * 2);
      int imgW = storeImageOriginal.getWidth();
      int imgH = storeImageOriginal.getHeight();
      double ratio = Math.min((double) availW / imgW, (double) availH / imgH);
      int targetW = Math.max(1, (int) Math.round(imgW * ratio));
      int targetH = Math.max(1, (int) Math.round(imgH * ratio));

      // Only rescale when panel size  changes
      if (targetW != lastScaledW || targetH != lastScaledH || storeImageScaled == null)
      {
        storeImageScaled = getScaledImage(storeImageOriginal, targetW, targetH);
        lastScaledW = targetW;
        lastScaledH = targetH;
      }

      int imgX = (getWidth() - targetW) / 2;
      int imgY = (getHeight() - targetH) / 2;
      if (storeImageScaled != null)
      {
        g.drawImage(storeImageScaled, imgX, imgY, null);
      }
    }
        
    if (appleImage != null)
    {
      int imgX = 10;
      int imgY = 10;
      g.drawImage(appleImage, imgX, imgY, 50, 50, this);
      g.drawString("Apple", 80, 45);
    }
    if (apple_pieImage != null)
    {
      int imgX = 150;
      int imgY = 10;
      g.drawImage(apple_pieImage, imgX, imgY, 50, 50, this);
      g.drawString("Apple Pie", 220, 45);
    }
    if (avocadoImage != null)
    {
      int imgX = 10;
      int imgY = 110;
      g.drawImage(avocadoImage, imgX, imgY, 50, 50, this);  
      g.drawString("Avocado", 80, 145);
    }
    if (boar_headImage != null)
    {
      int imgX = 150;
      int imgY = 110;
      g.drawImage(boar_headImage, imgX, imgY, 50, 50, this);
      g.drawString("Boar Head", 220, 145);
    }
    if (breadImage != null)
    {
      int imgX = 10;
      int imgY = 210;
      g.drawImage(breadImage, imgX, imgY, 50, 50, this);
      g.drawString("Bread", 80, 245);
    }
    if (cheeseImage != null)
    {
      int imgX = 150;
      int imgY = 210;
      g.drawImage(cheeseImage, imgX, imgY, 50, 50, this);
      g.drawString("Cheese", 220, 245);
    }
    if (cheesecakeImage != null)
    {
      int imgX = 10;
      int imgY = 310;
      g.drawImage(cheesecakeImage, imgX, imgY, 50, 50, this);
      g.drawString("Cheesecake", 80, 345);
    }
    if (chickenImage != null)
    {
      int imgX = 150;
      int imgY = 310;
      g.drawImage(chickenImage, imgX, imgY, 50, 50, this); 
      g.drawString("Chicken", 220, 345);
    }
    if (cookieImage != null)
    {
      int imgX = 10;
      int imgY = 410;
      g.drawImage(cookieImage, imgX, imgY, 50, 50, this);
      g.drawString("Cookie", 80, 445);
    }
    if (dragon_fruitImage != null)
    {
      int imgX = 150;
      int imgY = 410;
      g.drawImage(dragon_fruitImage, imgX, imgY, 50, 50, this);
      g.drawString("Dragon Fruit", 220, 445);
    }

    if (fishImage != null)
    {
      int imgX = 10;
      int imgY = 510;
      g.drawImage(fishImage, imgX, imgY, 50, 50, this);
      g.drawString("Fish", 80, 545);
    }

    if (fried_eggsImage != null)
    {
      int imgX = 150;
      int imgY = 510;
      g.drawImage(fried_eggsImage, imgX, imgY, 50, 50, this);
      g.drawString("Fried Eggs", 220, 545);
    }

    if (honeyImage != null)
    {
      int imgX = 10;
      int imgY = 610;
      g.drawImage(honeyImage, imgX, imgY, 50, 50, this);
      g.drawString("Honey", 80, 645);
    }

    if (pineappleImage != null)
    {
      int imgX = 150;
      int imgY = 610;
      g.drawImage(pineappleImage, imgX, imgY, 50, 50, this);
      g.drawString("Pineapple", 220, 645);
    }

    if (pretzelImage != null)
    {
      int imgX = 10;
      int imgY = 710;
      g.drawImage(pretzelImage, imgX, imgY, 50, 50, this);
      g.drawString("Pretzel", 80, 745);
    }

    if (pumpkin_pieImage != null)
    {
      int imgX = 150;
      int imgY = 710;
      g.drawImage(pumpkin_pieImage, imgX, imgY, 50, 50, this);
      g.drawString("Pumpkin Pie", 220, 745);
    }

    if (shrimpImage != null)
    {
      int imgX = 10;
      int imgY = 810;
      g.drawImage(shrimpImage, imgX, imgY, 50, 50, this);
      g.drawString("Shrimp", 80, 845);
    }
    
    if (sushiImage != null)
    {
      int imgX = 150;
      int imgY = 810;
      g.drawImage(sushiImage, imgX, imgY, 50, 50, this);
      g.drawString("Sushi", 220, 845);
    }

    if (tboneImage != null)
    {
      int imgX = 10;
      int imgY = 910;
      g.drawImage(tboneImage, imgX, imgY, 50, 50, this);
      g.drawString("T-Bone", 80, 945);
    }
    
    if (watermelonImage != null)
    {
      int imgX = 150;
      int imgY = 910;
      g.drawImage(watermelonImage, imgX, imgY, 50, 50, this);
      g.drawString("Watermelon", 220, 945);
    }

    if (man_idleImage != null)
    {
      int imgX = 1410;
      int imgY = 644;
      g.drawImage(man_idleImage, imgX, imgY, this);
    }
  }

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

            panel.store = new Imagesfood("store");
            panel.storeImageOriginal = panel.store.getImage();
            panel.storeImageScaled = null; // ensure cached scaled image will be created on first paint
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            panel.repaint();
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
            
            panel.man_idle = new Imagesfood("man_idle");
            panel.man_idleImage = panel.man_idle.getImage();
          
        });
        // Repaint happens after image load in the EDT

    } 
}


