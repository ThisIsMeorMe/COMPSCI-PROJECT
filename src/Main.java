//  IMPORTS
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class Main extends JPanel implements MouseMotionListener, MouseListener
{
  private boolean mouseWasReleased = false;
  private boolean mouseWasPressed = false;
  // The variables store coordiates 
  private int mouseX = -10;
  private int mouseY = -10;
  private Timer refreshTimer;
  // Controls whether the inventory (tan box, outline, food images and text)
  // are visible. Starts hidden (false).
  private boolean showInventory = false;
  // Global UI shift (virtual coordinates). Change these to move the
  // shopping cart, background, outline, and food list by the same amount.
  private int uiShiftX = 70;
  private int uiShiftY = 50;
  // Question panel and visibility flag
  private QuestionPanel questionPanel = null;
  private boolean questionVisible = false;
  
  public Main()
  {
    addMouseMotionListener(this);
    addMouseListener(this);
    // Refresh panel 60 times per second for smooth cursor tracking
    refreshTimer = new Timer(16, e -> repaint());
    refreshTimer.start();
  }

  @Override
  // Updates the varibles
  public void mouseMoved(MouseEvent e)
  {
    mouseX = e.getX();
    mouseY = e.getY();
    // update the panel immediately so cursor image follows smoothly
    repaint();
  }

  @Override
  public void mouseDragged(MouseEvent e)
  {
    if (currentDrag == null) return;
    // update current drag position using virtual coordinates
    int panelW = getWidth();
    int panelH = getHeight();
    double scale = Math.min((double) panelW / VIRTUAL_WIDTH, (double) panelH / VIRTUAL_HEIGHT);
    int offsetX = (int) Math.round((panelW - VIRTUAL_WIDTH * scale) / 2.0);
    int offsetY = (int) Math.round((panelH - VIRTUAL_HEIGHT * scale) / 2.0);
    int vx = (int) Math.round((e.getX() - offsetX) / scale);
    int vy = (int) Math.round((e.getY() - offsetY) / scale);
    currentDrag.vx = vx - currentDrag.offsetX;
    currentDrag.vy = vy - currentDrag.offsetY;
    repaint();
  }

  @Override
    public void mouseClicked(MouseEvent e) {
    // Show the question panel when the question image is clicked
    int panelW = getWidth();
    int panelH = getHeight();
    double scale = Math.min((double) panelW / VIRTUAL_WIDTH, (double) panelH / VIRTUAL_HEIGHT);
    int offsetX = (int) Math.round((panelW - VIRTUAL_WIDTH * scale) / 2.0);
    int offsetY = (int) Math.round((panelH - VIRTUAL_HEIGHT * scale) / 2.0);
    int vx = (int) Math.round((e.getX() - offsetX) / scale);
    int vy = (int) Math.round((e.getY() - offsetY) / scale);
    if (vx >= 800 && vx <= 950 && vy >= 80 && vy <= 230) {
    if (questionPanel == null) {
        questionPanel = new QuestionPanel();
        questionPanel.setBounds(350, 270, 800, 500);

        // Add to the layered pane above everything
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.getLayeredPane().add(questionPanel, JLayeredPane.POPUP_LAYER);
    }

    // Toggle visibility
    questionVisible = !questionVisible;
    questionPanel.setVisible(questionVisible);
    questionPanel.repaint();
    }
}

  @Override
  public void mousePressed(MouseEvent e)
  {
    // Convert actual panel coords to virtual coords so hit test matches drawn cart
    int panelW = getWidth();
    int panelH = getHeight();
    double scale = Math.min((double) panelW / VIRTUAL_WIDTH, (double) panelH / VIRTUAL_HEIGHT);
    int offsetX = (int) Math.round((panelW - VIRTUAL_WIDTH * scale) / 2.0);
    int offsetY = (int) Math.round((panelH - VIRTUAL_HEIGHT * scale) / 2.0);
    int vx = (int) Math.round((e.getX() - offsetX) / scale);
    int vy = (int) Math.round((e.getY() - offsetY) / scale);

    // Shopping cart is drawn at base (50,70) shifted by uiShiftX/uiShiftY — toggle when clicked
    if (vx >= 50 + uiShiftX && vx <= 50 + uiShiftX + 60 && vy >= 70 + uiShiftY && vy <= 70 + uiShiftY + 60) {
      showInventory = !showInventory;
      repaint();
      return;
    }

    // If inventory is visible, check whether user clicked on any inventory food to create a draggable copy
    if (showInventory) {
      final int V_IMG_W = 50;
      final int V_IMG_H = 50;
      final int leftX = 35 + uiShiftX;
      final int rightX = 220 + uiShiftX;
      final int startY = 135 + uiShiftY;
      final int rowSpacing = 55;
      //mouseWasReleased = true;

      // helper to check a single item at (col,row) and start drag copy if hit
      java.util.function.BiFunction<java.awt.image.BufferedImage, java.awt.Point, Boolean> tryStart = (img, pt) -> {
        if (img == null) return false;
        int imgX = pt.x;
        int imgY = pt.y;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(img, imgX, imgY, V_IMG_W, V_IMG_H);
          di.offsetX = vx - imgX;
          di.offsetY = vy - imgY;
          activeDrags.add(di);
          currentDrag = di;
          repaint();
          return true;
        }
        return false;
      };

      // check items in same order as drawn (two columns)
      // row 0
      if (tryStart.apply(appleImage, new Point(leftX, startY + 0*rowSpacing))) return;
      if (tryStart.apply(apple_pieImage, new Point(rightX, startY + 0*rowSpacing))) return;
      // row1
      if (tryStart.apply(avocadoImage, new Point(leftX, startY + 1*rowSpacing))) return;
      if (tryStart.apply(boar_headImage, new Point(rightX, startY + 1*rowSpacing))) return;
      // row2
      if (tryStart.apply(breadImage, new Point(leftX, startY + 2*rowSpacing))) return;
      if (tryStart.apply(cheeseImage, new Point(rightX, startY + 2*rowSpacing))) return;
      // row3
      if (tryStart.apply(cheesecakeImage, new Point(leftX, startY + 3*rowSpacing))) return;
      if (tryStart.apply(chickenImage, new Point(rightX, startY + 3*rowSpacing))) return;
      // row4
      if (tryStart.apply(cookieImage, new Point(leftX, startY + 4*rowSpacing))) return;
      if (tryStart.apply(dragon_fruitImage, new Point(rightX, startY + 4*rowSpacing))) return;
      // row5
      if (tryStart.apply(fishImage, new Point(leftX, startY + 5*rowSpacing))) return;
      if (tryStart.apply(fried_eggsImage, new Point(rightX, startY + 5*rowSpacing))) return;
      // row6
      if (tryStart.apply(honeyImage, new Point(leftX, startY + 6*rowSpacing))) return;
      if (tryStart.apply(pineappleImage, new Point(rightX, startY + 6*rowSpacing))) return;
      // row7
      if (tryStart.apply(pretzelImage, new Point(leftX, startY + 7*rowSpacing))) return;
      if (tryStart.apply(pumpkin_pieImage, new Point(rightX, startY + 7*rowSpacing))) return;
      // row8
      if (tryStart.apply(shrimpImage, new Point(leftX, startY + 8*rowSpacing))) return;
      if (tryStart.apply(sushiImage, new Point(rightX, startY + 8*rowSpacing))) return;
      // row9
      if (tryStart.apply(tboneImage, new Point(leftX, startY + 9*rowSpacing))) return;
      if (tryStart.apply(watermelonImage, new Point(rightX, startY + 9*rowSpacing))) return;
      mouseWasPressed = true;
  }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    mouseWasReleased = true;
  }

  @Override
  public void mouseEntered(MouseEvent e) { }

  @Override
  public void mouseExited(MouseEvent e) { }
    
  public int getMouseX()
  {
    return mouseX;
  }

  public int getMouseY()
  {
    return mouseY;
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
  private BufferedImage shoppingcartImage = null;
  private Imagesfood shoppingcart;
  private BufferedImage cursorImage = null;
  private Imagesfood cursor;
  private BufferedImage customer2Image = null;
  private Imagesfood customer2;
  private BufferedImage cloudImage = null;
  private Imagesfood cloud;
  // apple image that appears inside the cloud (separate from inventory apple)
  private BufferedImage cloudAppleImage = null;
  private Imagesfood cloudApple;
  private boolean showCloudApple = false;

  private BufferedImage questionImage = null;
  private Imagesfood question;

  private BufferedImage moneyImage = null;
  private Imagesfood money;
  private BufferedImage pointImage = null;
  private Imagesfood point;
  // Drag copies created when user clicks inventory items. Originals remain.
  private static class DragItem {
    BufferedImage img;
    int vx, vy, vW, vH;
    int offsetX, offsetY;
    DragItem(BufferedImage img, int vx, int vy, int vW, int vH) {
      this.img = img; this.vx = vx; this.vy = vy; this.vW = vW; this.vH = vH; this.offsetX = 0; this.offsetY = 0;
    }
  }
  private final java.util.List<DragItem> activeDrags = new java.util.ArrayList<>();
  private DragItem currentDrag = null;
  // Virtual design resolution the UI is laid out in
  private static final int VIRTUAL_WIDTH = 1535;
  private static final int VIRTUAL_HEIGHT = 830;

  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    // Creates a red background
    
    g.setColor(Color.BLACK);
    
    g.fillRect(0, 0, getWidth(), getHeight());

    // Precompute virtual -> actual scale and offsets so all drawings
    // keep locked positions relative to a 1535x830 design resolution.
    int panelW = getWidth();
    int panelH = getHeight();
    double scale = Math.min((double) panelW / VIRTUAL_WIDTH, (double) panelH / VIRTUAL_HEIGHT);
    @SuppressWarnings("unused")
    int offsetX = (int) Math.round((panelW - VIRTUAL_WIDTH * scale) / 2.0);
    @SuppressWarnings("unused")
    int offsetY = (int) Math.round((panelH - VIRTUAL_HEIGHT * scale) / 2.0);

    // Renders all the images
    if (storeImageOriginal != null)
    {
      // Draw store background at fixed position (not affected by uiShift)
      drawVirtualImage(g, storeImageOriginal, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
    }
    
    if (showInventory) {
    // Calculate the tan box width based on the longest food label text
    Graphics2D g2Measure = (Graphics2D) g;
    Font originalFont = g2Measure.getFont();
    Font bigFont = originalFont.deriveFont(originalFont.getSize2D() * 2f);
    g2Measure.setFont(bigFont);
    FontMetrics fm = g2Measure.getFontMetrics();
    g2Measure.setFont(originalFont); // restore original font
    
    String[] foodNames = {"Apple", "Apple Pie", "Avocado", "Boar Head", "Bread", "Cheese", 
                          "Cheesecake", "Chicken", "Cookie", "Dragon Fruit", "Fish", "Fried Eggs",
                          "Honey", "Pineapple", "Pretzel", "Pumpkin Pie", "Shrimp", "Sushi", "T-Bone", "Watermelon"};
    int maxTextWidth = 0;
    for (String name : foodNames) {
      int textWidth = fm.stringWidth(name);
      maxTextWidth = Math.max(maxTextWidth, textWidth);
    }
    
    // Convert pixel width back to virtual coordinates (divide by scale)
    int maxTextWidthVirtual = (int) Math.round(maxTextWidth / scale);
    
    // Text starts at: rightX (290) + V_IMG_W (50) + 8 = 348
    // Box should extend past the text: 348 + maxTextWidthVirtual + padding
    int tanBoxWidth = 290 + 50 + 8 + maxTextWidthVirtual -20; // rightmost column + small padding
    
    // Draw tan background behind foods (width extends based on longest text)
    g.setColor(new Color(210, 180, 140)); // Tan color
    // Draw box at base (30,70) shifted by uiShiftX/uiShiftY
    drawVirtualRectangle(g, 30 + uiShiftX, 70 + uiShiftY, tanBoxWidth, 620);
    
    // Draw black border around tan box
    g.setColor(Color.BLACK);
    drawVirtualBorder(g, 30 + uiShiftX, 70 + uiShiftY, tanBoxWidth, 620, 4); // 4px border thickness
    
    // Draw "Inventory" title at top center of box (below border)
    drawVirtualTitleString(g, "Inventory", 30 + uiShiftX + tanBoxWidth / 2, 120 + uiShiftY);
        
    // Layout constants for the item list
    final int V_IMG_W = 50;
    final int V_IMG_H = 50;
    // moved 25px right and 15px down from original values (small extra nudge to the right)
    // then shifted overall UI by uiShiftX/uiShiftY
    final int leftX = 35 + uiShiftX;
    final int rightX = 220 + uiShiftX;
    final int startY = 135 + uiShiftY;
    // decreased vertical spacing to squish items closer together
    final int rowSpacing = 55;

    if (appleImage != null)
    {
      int row = 0;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, appleImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Apple", imgX + V_IMG_W + 8, imgY + 30);
    }
    if (apple_pieImage != null)
    {
      int row = 0;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, apple_pieImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Apple Pie", imgX + V_IMG_W + 8, imgY + 35);
    }
    if (avocadoImage != null)
    {
      int row = 1;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, avocadoImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Avocado", imgX + V_IMG_W + 8, imgY + 35);
    }
    if (boar_headImage != null)
    {
      int row = 1;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, boar_headImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Boar Head", imgX + V_IMG_W + 8, imgY + 35);
    }
    if (breadImage != null)
    {
      int row = 2;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, breadImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Bread", imgX + V_IMG_W + 8, imgY + 35);
    }
    if (cheeseImage != null)
    {
      int row = 2;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, cheeseImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Cheese", imgX + V_IMG_W + 8, imgY + 35);
    }
    if (cheesecakeImage != null)
    {
      int row = 3;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, cheesecakeImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Cheesecake", imgX + V_IMG_W + 8, imgY + 35);
    }
    if (chickenImage != null)
    {
      int row = 3;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, chickenImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Chicken", imgX + V_IMG_W + 8, imgY + 35);
    }
    if (cookieImage != null)
    {
      int row = 4;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, cookieImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Cookie", imgX + V_IMG_W + 8, imgY + 35);
    }
    if (dragon_fruitImage != null)
    {
      int row = 4;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, dragon_fruitImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Dragon Fruit", imgX + V_IMG_W + 8, imgY + 35);
    }

    if (fishImage != null)
    {
      int row = 5;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, fishImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Fish", imgX + V_IMG_W + 8, imgY + 35);
    }

    if (fried_eggsImage != null)
    {
      int row = 5;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, fried_eggsImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Fried Eggs", imgX + V_IMG_W + 8, imgY + 35);
    }

    if (honeyImage != null)
    {
      int row = 6;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, honeyImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Honey", imgX + V_IMG_W + 8, imgY + 35);
    }

    if (pineappleImage != null)
    {
      int row = 6;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, pineappleImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Pineapple", imgX + V_IMG_W + 8, imgY + 35);
    }

    if (pretzelImage != null)
    {
      int row = 7;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, pretzelImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Pretzel", imgX + V_IMG_W + 8, imgY + 35);
    }

    if (pumpkin_pieImage != null)
    {
      int row = 7;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, pumpkin_pieImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Pumpkin Pie", imgX + V_IMG_W + 8, imgY + 35);
    }

    if (shrimpImage != null)
    {
      int row = 8;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, shrimpImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Shrimp", imgX + V_IMG_W + 8, imgY + 35);
    }
    
    if (sushiImage != null)
    {
      int row = 8;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, sushiImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Sushi", imgX + V_IMG_W + 8, imgY + 35);
    }

    if (tboneImage != null)
    {
      int row = 9;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, tboneImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "T-Bone", imgX + V_IMG_W + 8, imgY + 35);
    }
    
    if (watermelonImage != null)
    {
      int row = 9;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, watermelonImage, imgX, imgY, V_IMG_W, V_IMG_H);
      drawVirtualString(g, "Watermelon", imgX + V_IMG_W + 8, imgY + 35);
    }
    }

    if (man_idleImage != null)
    {
      int imgX = 1300; // fixed position — not affected by uiShift
      int imgY = 520;  // fixed position — not affected by uiShift
      // draw at its own image size but scaled to virtual coords
      drawVirtualImage(g, man_idleImage, imgX, imgY, man_idleImage.getWidth(), man_idleImage.getHeight());
    }

    // Draw shopping cart image on top (layered above foods)
    if (shoppingcartImage != null)
    {
      // draw shopping cart at base (50,70) shifted by uiShiftX/uiShiftY
      drawVirtualImage(g, shoppingcartImage, 50 + uiShiftX, 70 + uiShiftY, 60, 60);
    }
    if (customer2Image != null)
    {
      // draw shopping cart at base (50,70) shifted by uiShiftX/uiShiftY
      drawVirtualImage(g, customer2Image, 750 + uiShiftX, 400 + uiShiftY, 250, 250);
    }
    if (cloudImage != null)
    {
      // draw shopping cart at base (50,70) shifted by uiShiftX/uiShiftY
      drawVirtualImage(g, cloudImage, 900 + uiShiftX, 450 + uiShiftY, 100, 100);
    }
    if (cloudAppleImage != null)
    {
      // draw the apple inside the cloud at the same position/size
      drawVirtualImage(g, cloudAppleImage, 933 + uiShiftX, 490 + uiShiftY, 30, 30);
    }

    if (questionImage != null)
    {
      // draw question image at fixed position (not affected by uiShift)
      drawVirtualImage(g, questionImage, 800, 80, 150, 150);
    }

    if (pointImage != null)
    {
      // draw point image at fixed position (not affected by uiShift)
      drawVirtualImage(g, pointImage, 250, 60, 250, 100);
    }
    if (moneyImage != null)
    {
      // draw money image at fixed position (not affected by uiShift)
      drawVirtualImage(g, moneyImage, 480, 60, 250, 100);
    }

    // draw active drag copies on top
    if (!activeDrags.isEmpty()) {
      for (DragItem d : activeDrags) {
        if (d != null && d.img != null) drawVirtualImage(g, d.img, d.vx, d.vy, d.vW, d.vH);
      }
    }

    // No custom cursor image — let the system cursor be used.
    // (Cursor-follow image removed.)
  }

  // Helper: draw a buffered image positioned/sized in virtual coordinates
  private void drawVirtualImage(Graphics g, BufferedImage img, int vx, int vy, int vWidth, int vHeight)
  {
    if (img == null) return;
    int panelW = getWidth();
    int panelH = getHeight();
    double scale = Math.min((double) panelW / VIRTUAL_WIDTH, (double) panelH / VIRTUAL_HEIGHT);
    int offsetX = (int) Math.round((panelW - VIRTUAL_WIDTH * scale) / 2.0);
    int offsetY = (int) Math.round((panelH - VIRTUAL_HEIGHT * scale) / 2.0);
    int ax = offsetX + (int) Math.round(vx * scale);
    int ay = offsetY + (int) Math.round(vy * scale);
    int aw = Math.max(1, (int) Math.round(vWidth * scale));
    int ah = Math.max(1, (int) Math.round(vHeight * scale));
    g.drawImage(img, ax, ay, aw, ah, this);
  }

  // Helper: draw a rectangle at virtual coordinates
  private void drawVirtualRectangle(Graphics g, int vx, int vy, int vWidth, int vHeight)
  {
    int panelW = getWidth();
    int panelH = getHeight();
    double scale = Math.min((double) panelW / VIRTUAL_WIDTH, (double) panelH / VIRTUAL_HEIGHT);
    int offsetX = (int) Math.round((panelW - VIRTUAL_WIDTH * scale) / 2.0);
    int offsetY = (int) Math.round((panelH - VIRTUAL_HEIGHT * scale) / 2.0);
    int ax = offsetX + (int) Math.round(vx * scale);
    int ay = offsetY + (int) Math.round(vy * scale);
    int aw = Math.max(1, (int) Math.round(vWidth * scale));
    int ah = Math.max(1, (int) Math.round(vHeight * scale));
    g.fillRect(ax, ay, aw, ah);
  }

  // Helper: draw text anchored at virtual coordinates
  private void drawVirtualString(Graphics g, String text, int vx, int vy)
  {
    int panelW = getWidth();
    int panelH = getHeight();
    double scale = Math.min((double) panelW / VIRTUAL_WIDTH, (double) panelH / VIRTUAL_HEIGHT);
    int offsetX = (int) Math.round((panelW - VIRTUAL_WIDTH * scale) / 2.0);
    int offsetY = (int) Math.round((panelH - VIRTUAL_HEIGHT * scale) / 2.0);
    int ax = offsetX + (int) Math.round(vx * scale);
    int ay = offsetY + (int) Math.round(vy * scale);
    Graphics2D g2 = (Graphics2D) g;
    Font oldFont = g2.getFont();
    Color oldColor = g2.getColor();
    // Make the font double size for these labels
    Font bigFont = oldFont.deriveFont(oldFont.getSize2D() * 2f);
    g2.setFont(bigFont);
    g2.setColor(Color.BLACK);
    g2.drawString(text, ax, ay);
    // restore original font and color
    g2.setFont(oldFont);
    g2.setColor(oldColor);
  }

  // Helper: draw a border at virtual coordinates
  private void drawVirtualBorder(Graphics g, int vx, int vy, int vWidth, int vHeight, int borderThickness)
  {
    int panelW = getWidth();
    int panelH = getHeight();
    double scale = Math.min((double) panelW / VIRTUAL_WIDTH, (double) panelH / VIRTUAL_HEIGHT);
    int offsetX = (int) Math.round((panelW - VIRTUAL_WIDTH * scale) / 2.0);
    int offsetY = (int) Math.round((panelH - VIRTUAL_HEIGHT * scale) / 2.0);
    int ax = offsetX + (int) Math.round(vx * scale);
    int ay = offsetY + (int) Math.round(vy * scale);
    int aw = Math.max(1, (int) Math.round(vWidth * scale));
    int ah = Math.max(1, (int) Math.round(vHeight * scale));
    int bt = Math.max(1, (int) Math.round(borderThickness * scale));
    Graphics2D g2 = (Graphics2D) g;
    g2.setStroke(new java.awt.BasicStroke(bt));
    g2.drawRect(ax, ay, aw, ah);
  }

  // Helper: draw title text centered and bold at virtual coordinates
  private void drawVirtualTitleString(Graphics g, String text, int vxCenter, int vy)
  {
    int panelW = getWidth();
    int panelH = getHeight();
    double scale = Math.min((double) panelW / VIRTUAL_WIDTH, (double) panelH / VIRTUAL_HEIGHT);
    int offsetX = (int) Math.round((panelW - VIRTUAL_WIDTH * scale) / 2.0);
    int offsetY = (int) Math.round((panelH - VIRTUAL_HEIGHT * scale) / 2.0);
    int ay = offsetY + (int) Math.round(vy * scale);
    Graphics2D g2 = (Graphics2D) g;
    Font oldFont = g2.getFont();
    Color oldColor = g2.getColor();
    // Make the font bold and larger
    Font boldFont = oldFont.deriveFont(java.awt.Font.BOLD, oldFont.getSize2D() * 2.5f);
    g2.setFont(boldFont);
    g2.setColor(Color.BLACK);
    FontMetrics fm = g2.getFontMetrics();
    int textWidth = fm.stringWidth(text);
    int ax = offsetX + (int) Math.round(vxCenter * scale) - textWidth / 2;
    g2.drawString(text, ax, ay);
    // restore original font and color
    g2.setFont(oldFont);
    g2.setColor(oldColor);
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
            
            panel.shoppingcart = new Imagesfood("shoppingcart");
            panel.shoppingcartImage = panel.shoppingcart.getImage();
            
            panel.man_idle = new Imagesfood("man_idle");
            panel.man_idleImage = panel.man_idle.getImage();

            panel.question = new Imagesfood("question");
            panel.questionImage = panel.question.getImage();

            panel.point = new Imagesfood("point");
            panel.pointImage = panel.point.getImage();
            panel.money = new Imagesfood("money");
            panel.moneyImage = panel.money.getImage();

            // cursor image removed - use system cursor instead

            // Debug: print which images loaded successfully
            System.out.println("Image load status:");
            System.out.println("store=" + (panel.storeImageOriginal != null));
            System.out.println("apple=" + (panel.appleImage != null));
            System.out.println("apple_pie=" + (panel.apple_pieImage != null));
            System.out.println("avocado=" + (panel.avocadoImage != null));
            System.out.println("boar_head=" + (panel.boar_headImage != null));
            System.out.println("bread=" + (panel.breadImage != null));
            System.out.println("cheese=" + (panel.cheeseImage != null));
            System.out.println("cheesecake=" + (panel.cheesecakeImage != null));
            System.out.println("chicken=" + (panel.chickenImage != null));
            System.out.println("cookie=" + (panel.cookieImage != null));
            System.out.println("dragon_fruit=" + (panel.dragon_fruitImage != null));
            System.out.println("fish=" + (panel.fishImage != null));
            System.out.println("fried_eggs=" + (panel.fried_eggsImage != null));
            System.out.println("honey=" + (panel.honeyImage != null));
            System.out.println("pineapple=" + (panel.pineappleImage != null));
            System.out.println("pretzel=" + (panel.pretzelImage != null));
            System.out.println("pumpkin_pie=" + (panel.pumpkin_pieImage != null));
            System.out.println("shrimp=" + (panel.shrimpImage != null));
            System.out.println("sushi=" + (panel.sushiImage != null));
            System.out.println("tbone=" + (panel.tboneImage != null));
            System.out.println("watermelon=" + (panel.watermelonImage != null));
            System.out.println("man_idle=" + (panel.man_idleImage != null));
          
            // Load customer2 image after 10 seconds
            Timer delayedImageTimer = new Timer(2000, e -> {
                  panel.customer2 = new Imagesfood("customer2_idle/1");
                  panel.customer2Image = panel.customer2.getImage();
                  System.out.println("Delayed customer2 image loaded: " + (panel.customer2Image != null));
                  panel.cloud = new Imagesfood("cloud");
                  panel.cloudImage = panel.cloud.getImage(); 
                  System.out.println("Delayed cloud image loaded: " + (panel.cloudImage != null));
                // load an apple to appear inside the cloud
                panel.cloudApple = new Imagesfood("apple");
                panel.cloudAppleImage = panel.cloudApple.getImage(); 
                panel.showCloudApple = (panel.cloudAppleImage != null);
                System.out.println("Delayed cloud-apple image loaded: " + (panel.cloudAppleImage != null));
                  panel.repaint();
            });
            Timer delayedImageTimer1 = new Timer(3000, e -> {
                  panel.customer2 = new Imagesfood("customer2_walking/2");
                  panel.customer2Image = panel.customer2.getImage();
                  System.out.println("Delayed customer2 image loaded: " + (panel.customer2Image != null));
                  panel.cloud = new Imagesfood("cloud");
                  panel.cloudImage = panel.cloud.getImage(); 
                  System.out.println("Delayed cloud image loaded: " + (panel.cloudImage != null));
                // load an apple to appear inside the cloud
                panel.cloudApple = new Imagesfood("apple");
                panel.cloudAppleImage = panel.cloudApple.getImage(); 
                panel.showCloudApple = (panel.cloudAppleImage != null);
                System.out.println("Delayed cloud-apple image loaded: " + (panel.cloudAppleImage != null));
                  panel.repaint();
            });
            

            delayedImageTimer.setRepeats(false);
            delayedImageTimer.start();
            delayedImageTimer1.setRepeats(false);
            delayedImageTimer1.start();

            // --- Add a draggable apple component to the layered pane ---
            // Create an Imagesfood for the apple and a draggable component
            // Imagesfood draggableApple = new Imagesfood("apple");
            // javax.swing.JComponent appleComp = draggableApple.createDraggableComponent(900 + panel.uiShiftX, 450 + panel.uiShiftY);
            // // Add to the frame's layered pane so it uses absolute positioning and can be dragged
            // frame.getLayeredPane().add(appleComp, javax.swing.JLayeredPane.DRAG_LAYER);
            // Imagesfood draggableApple_Pie = new Imagesfood("apple_pie");
            // javax.swing.JComponent apple_pieComp = draggableApple_Pie.createDraggableComponent(900 + panel.uiShiftX, 450 + panel.uiShiftY);
            // // Add to the frame's layered pane so it uses absolute positioning and can be dragged
            // frame.getLayeredPane().add(apple_pieComp, javax.swing.JLayeredPane.DRAG_LAYER);
            // Imagesfood draggableAvacado = new Imagesfood("avacado");
            // javax.swing.JComponent avacadoComp = draggableAvacado.createDraggableComponent(900 + panel.uiShiftX, 450 + panel.uiShiftY);
            // // Add to the frame's layered pane so it uses absolute positioning and can be dragged
            // frame.getLayeredPane().add(avacadoComp, javax.swing.JLayeredPane.DRAG_LAYER);
        });
        // Repaint happens after image load in the EDT

        

    }
    
}
