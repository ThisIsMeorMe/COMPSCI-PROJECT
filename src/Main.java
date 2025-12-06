import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


public class Main extends JPanel implements MouseMotionListener, MouseListener
{
  
  public static Main mainInstance = null;
  private boolean mouseWasReleased = false;
  private boolean mouseWasPressed = false;
  
  private int mouseX = -10;
  private int mouseY = -10;
  private Timer refreshTimer;
  
  private boolean showInventory = false;
  
  private int uiShiftX = 70;
  
  private int uiShiftY = 20;
  
  private QuestionPanel questionPanel = null;
  private boolean questionVisible = false;
  
  public Main()
  {
    addMouseMotionListener(this);
    addMouseListener(this);
    refreshTimer = new Timer(16, e -> { updatePositions(); repaint(); });
    refreshTimer.start();
  }

  
  private void updatePositions() {
    long now = System.currentTimeMillis();
    long last = lastUpdateMillis <= 0 ? now : lastUpdateMillis;
    double dt = Math.max(0.0, (now - last) / 1000.0);
    lastUpdateMillis = now;

    
    if (customer2SpawnTimeMillis > 0L) {
      long waited = now - customer2SpawnTimeMillis;
      if (waited < customer2StartDelayMs) {
        
        lastUpdateMillis = now;
        return;
      } else {
        
        customer2Visible = true;
        customer2SpawnTimeMillis = 0L;
        
        lastUpdateMillis = now;
        return; 
      }
    }

    
    if (customer2State != 2) {
      int targetManX = 1300; 
      int targetManY = 520;  
      
      double finalY = targetManY - customer2FinalOffsetBelow;
      double vFinalX = targetManX; 

      if (customer2State == 0) {
        
        double dy = customer2Speed * dt;
        if (customer2VerticalMovementStart == 0L) {
          customer2VerticalMovementStart = now;
          customer2VerticalMovementAccum = 0L;
          customer2AnimationPaused = false;
          customer2PausedFrameIndex = -1;
        }
        if (customer2Vy <= finalY) {
          
          customer2Vy = finalY;
          customer2State = 1; 
        } else {
          double newY = customer2Vy - dy;
          if (newY <= finalY) {
            customer2Vy = finalY;
            customer2State = 1;
          } else {
            customer2Vy = newY;
            customer2VerticalMovementAccum += (long) (dt * 1000.0);
              
          }
        }
      } else if (customer2State == 1) {
        
        
        double vFinalXFrac = customer2InitialVx + (vFinalX - customer2InitialVx) * customer2HorizontalFraction;
        double dx = customer2Speed * dt;
        if (Math.abs(customer2Vx - vFinalXFrac) <= dx) {
          customer2Vx = vFinalXFrac;
          customer2State = 2; 
        } else if (customer2Vx < vFinalXFrac) {
          customer2Vx += dx;
        } else {
          customer2Vx -= dx;
        }
      }
    }
  }

  @Override
  public void mouseMoved(MouseEvent e)
  {
    mouseX = e.getX();
    mouseY = e.getY();
    repaint();
  }

  @Override
  public void mouseDragged(MouseEvent e)
  {
    if (currentDrag == null) return;
    
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

        
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        topFrame.getLayeredPane().add(questionPanel, JLayeredPane.POPUP_LAYER);
    }

   
    questionVisible = !questionVisible;
    questionPanel.setVisible(questionVisible);
    questionPanel.repaint();
    }
}

  @Override
  public void mousePressed(MouseEvent e)
  {
    
    int panelW = getWidth();
    int panelH = getHeight();
    double scale = Math.min((double) panelW / VIRTUAL_WIDTH, (double) panelH / VIRTUAL_HEIGHT);
    int offsetX = (int) Math.round((panelW - VIRTUAL_WIDTH * scale) / 2.0);
    int offsetY = (int) Math.round((panelH - VIRTUAL_HEIGHT * scale) / 2.0);
    int vx = (int) Math.round((e.getX() - offsetX) / scale);
    int vy = (int) Math.round((e.getY() - offsetY) / scale);

    
    if (vx >= 50 + uiShiftX && vx <= 50 + uiShiftX + 60 && vy >= 70 + uiShiftY && vy <= 70 + uiShiftY + 60) {
      showInventory = !showInventory;
      repaint();
      return;
    }

    if (showInventory) {
      final int V_IMG_W = 50;
      final int V_IMG_H = 50;
      final int leftX = 35 + uiShiftX;
      final int rightX = 220 + uiShiftX;
      final int startY = 135 + uiShiftY;
      final int rowSpacing = 55;

      
      
      if (appleImage != null && !isFoodUnlocked("apple")) {
        int imgX = leftX, imgY = startY + 0*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("apple");
          return;
        }
      }
      if (apple_pieImage != null && !isFoodUnlocked("apple_pie")) {
        int imgX = rightX, imgY = startY + 0*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("apple_pie");
          return;
        }
      }
      if (avocadoImage != null && !isFoodUnlocked("avocado")) {
        int imgX = leftX, imgY = startY + 1*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("avocado");
          return;
        }
      }
      if (boar_headImage != null && !isFoodUnlocked("boar_head")) {
        int imgX = rightX, imgY = startY + 1*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("boar_head");
          return;
        }
      }
      if (breadImage != null && !isFoodUnlocked("bread")) {
        int imgX = leftX, imgY = startY + 2*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("bread");
          return;
        }
      }
      if (cheeseImage != null && !isFoodUnlocked("cheese")) {
        int imgX = rightX, imgY = startY + 2*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("cheese");
          return;
        }
      }
      if (cheesecakeImage != null && !isFoodUnlocked("cheesecake")) {
        int imgX = leftX, imgY = startY + 3*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("cheesecake");
          return;
        }
      }
      if (chickenImage != null && !isFoodUnlocked("chicken")) {
        int imgX = rightX, imgY = startY + 3*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("chicken");
          return;
        }
      }
      if (cookieImage != null && !isFoodUnlocked("cookie")) {
        int imgX = leftX, imgY = startY + 4*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("cookie");
          return;
        }
      }
      if (dragon_fruitImage != null && !isFoodUnlocked("dragon_fruit")) {
        int imgX = rightX, imgY = startY + 4*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("dragon_fruit");
          return;
        }
      }
      if (fishImage != null && !isFoodUnlocked("fish")) {
        int imgX = leftX, imgY = startY + 5*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("fish");
          return;
        }
      }
      if (fried_eggsImage != null && !isFoodUnlocked("fried_eggs")) {
        int imgX = rightX, imgY = startY + 5*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("fried_eggs");
          return;
        }
      }
      if (honeyImage != null && !isFoodUnlocked("honey")) {
        int imgX = leftX, imgY = startY + 6*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("honey");
          return;
        }
      }
      if (pineappleImage != null && !isFoodUnlocked("pineapple")) {
        int imgX = rightX, imgY = startY + 6*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("pineapple");
          return;
        }
      }
      if (pretzelImage != null && !isFoodUnlocked("pretzel")) {
        int imgX = leftX, imgY = startY + 7*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("pretzel");
          return;
        }
      }
      if (pumpkin_pieImage != null && !isFoodUnlocked("pumpkin_pie")) {
        int imgX = rightX, imgY = startY + 7*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("pumpkin_pie");
          return;
        }
      }
      if (shrimpImage != null && !isFoodUnlocked("shrimp")) {
        int imgX = leftX, imgY = startY + 8*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("shrimp");
          return;
        }
      }
      if (sushiImage != null && !isFoodUnlocked("sushi")) {
        int imgX = rightX, imgY = startY + 8*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("sushi");
          return;
        }
      }
      if (tboneImage != null && !isFoodUnlocked("t-bone")) {
        int imgX = leftX, imgY = startY + 9*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("t-bone");
          return;
        }
      }
      if (watermelonImage != null && !isFoodUnlocked("watermelon")) {
        int imgX = rightX, imgY = startY + 9*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          consumePointOnClick();
          attemptUnlock("watermelon");
          return;
        }
      }

      
      
      if (appleImage != null && isFoodUnlocked("apple")) {
        int imgX = leftX, imgY = startY + 0*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(appleImage, imgX, imgY, V_IMG_W, V_IMG_H, "apple");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (apple_pieImage != null && isFoodUnlocked("apple_pie")) {
        int imgX = rightX, imgY = startY + 0*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(apple_pieImage, imgX, imgY, V_IMG_W, V_IMG_H, "apple_pie");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (avocadoImage != null && isFoodUnlocked("avocado")) {
        int imgX = leftX, imgY = startY + 1*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(avocadoImage, imgX, imgY, V_IMG_W, V_IMG_H, "avocado");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (boar_headImage != null && isFoodUnlocked("boar_head")) {
        int imgX = rightX, imgY = startY + 1*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(boar_headImage, imgX, imgY, V_IMG_W, V_IMG_H, "boar_head");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (breadImage != null && isFoodUnlocked("bread")) {
        int imgX = leftX, imgY = startY + 2*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(breadImage, imgX, imgY, V_IMG_W, V_IMG_H, "bread");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (cheeseImage != null && isFoodUnlocked("cheese")) {
        int imgX = rightX, imgY = startY + 2*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(cheeseImage, imgX, imgY, V_IMG_W, V_IMG_H, "cheese");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (cheesecakeImage != null && isFoodUnlocked("cheesecake")) {
        int imgX = leftX, imgY = startY + 3*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(cheesecakeImage, imgX, imgY, V_IMG_W, V_IMG_H, "cheesecake");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (chickenImage != null && isFoodUnlocked("chicken")) {
        int imgX = rightX, imgY = startY + 3*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(chickenImage, imgX, imgY, V_IMG_W, V_IMG_H, "chicken");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (cookieImage != null && isFoodUnlocked("cookie")) {
        int imgX = leftX, imgY = startY + 4*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(cookieImage, imgX, imgY, V_IMG_W, V_IMG_H, "cookie");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (dragon_fruitImage != null && isFoodUnlocked("dragon_fruit")) {
        int imgX = rightX, imgY = startY + 4*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(dragon_fruitImage, imgX, imgY, V_IMG_W, V_IMG_H, "dragon_fruit");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (fishImage != null && isFoodUnlocked("fish")) {
        int imgX = leftX, imgY = startY + 5*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(fishImage, imgX, imgY, V_IMG_W, V_IMG_H, "fish");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (fried_eggsImage != null && isFoodUnlocked("fried_eggs")) {
        int imgX = rightX, imgY = startY + 5*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(fried_eggsImage, imgX, imgY, V_IMG_W, V_IMG_H, "fried_eggs");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (honeyImage != null && isFoodUnlocked("honey")) {
        int imgX = leftX, imgY = startY + 6*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(honeyImage, imgX, imgY, V_IMG_W, V_IMG_H, "honey");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (pineappleImage != null && isFoodUnlocked("pineapple")) {
        int imgX = rightX, imgY = startY + 6*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(pineappleImage, imgX, imgY, V_IMG_W, V_IMG_H, "pineapple");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (pretzelImage != null && isFoodUnlocked("pretzel")) {
        int imgX = leftX, imgY = startY + 7*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(pretzelImage, imgX, imgY, V_IMG_W, V_IMG_H, "pretzel");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (pumpkin_pieImage != null && isFoodUnlocked("pumpkin_pie")) {
        int imgX = rightX, imgY = startY + 7*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(pumpkin_pieImage, imgX, imgY, V_IMG_W, V_IMG_H, "pumpkin_pie");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (shrimpImage != null && isFoodUnlocked("shrimp")) {
        int imgX = leftX, imgY = startY + 8*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(shrimpImage, imgX, imgY, V_IMG_W, V_IMG_H, "shrimp");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (sushiImage != null && isFoodUnlocked("sushi")) {
        int imgX = rightX, imgY = startY + 8*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(sushiImage, imgX, imgY, V_IMG_W, V_IMG_H, "sushi");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (tboneImage != null && isFoodUnlocked("t-bone")) {
        int imgX = leftX, imgY = startY + 9*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(tboneImage, imgX, imgY, V_IMG_W, V_IMG_H, "t-bone");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      if (watermelonImage != null && isFoodUnlocked("watermelon")) {
        int imgX = rightX, imgY = startY + 9*rowSpacing;
        if (vx >= imgX && vx <= imgX + V_IMG_W && vy >= imgY && vy <= imgY + V_IMG_H) {
          DragItem di = new DragItem(watermelonImage, imgX, imgY, V_IMG_W, V_IMG_H, "watermelon");
          di.offsetX = vx - imgX; di.offsetY = vy - imgY;
          consumePointOnClick(); activeDrags.add(di); currentDrag = di; repaint(); return;
        }
      }
      mouseWasPressed = true;
  }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    mouseWasReleased = true;
    
    if (currentDrag != null) {
      
      int panelW = getWidth();
      int panelH = getHeight();
      double scale = Math.min((double) panelW / VIRTUAL_WIDTH, (double) panelH / VIRTUAL_HEIGHT);
      int offsetX = (int) Math.round((panelW - VIRTUAL_WIDTH * scale) / 2.0);
      int offsetY = (int) Math.round((panelH - VIRTUAL_HEIGHT * scale) / 2.0);
      int vx = (int) Math.round((e.getX() - offsetX) / scale);
      int vy = (int) Math.round((e.getY() - offsetY) / scale);
      
      
      int cloudX = 1300 + uiShiftX;
      int cloudY = 460 + uiShiftY;
      int cloudW = 100, cloudH = 100;
      
      
      if (vx >= cloudX && vx <= cloudX + cloudW && vy >= cloudY && vy <= cloudY + cloudH) {
        
        if (currentDrag.key != null && currentDrag.key.equals(cloudFoodKey) && pointAmount >= 1) {
          int price = getPriceForKey(currentDrag.key);
          moneyAmount += price;
          pointAmount -= 1;
          
          try {
            java.util.List<String> unlocked = FoodEach.allUnlockedFood;
            if (unlocked != null && !unlocked.isEmpty()) {
              String newKey = unlocked.get(rand.nextInt(unlocked.size()));
              cloudFoodKey = newKey;
              Imagesfood f = new Imagesfood(newKey);
              BufferedImage bi = f.getImage();
              if (bi != null) {
                cloudApple = f;
                cloudAppleImage = bi;
                showCloudApple = true;
              }
            }
          } catch (Throwable t) {
            
          }
        }
        
        activeDrags.remove(currentDrag);
      } else {
        
        activeDrags.remove(currentDrag);
      }
      currentDrag = null;
    }
    repaint();
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
  
  private double customer2Vx = 1484; 
  private double customer2Vy = 961;  
  
  private int customer2State = 0;
  private long lastUpdateMillis = System.currentTimeMillis();
  
  private double customer2Speed = 80.0; 
  
  private int customer2ApproachOffsetY = 120; 
  private int customer2FinalOffsetBelow = 65;  
  
  private long customer2SpawnTimeMillis = 0L;
  private int customer2StartDelayMs = 3000; 
  
  private boolean customer2Visible = false;
  
  private int customer2PauseAfterVerticalMs = 2000; 
  private long customer2VerticalMovementStart = 0L;
  private long customer2VerticalMovementAccum = 0L;
  private boolean customer2AnimationPaused = false;
  private int customer2PausedFrameIndex = -1;
  
  
  
  
  private double customer2SpawnPercentX = 0.70; 
  private double customer2SpawnPercentY = 0.9; 
  private double customer2SpawnOffsetX = 0.0;  
  private double customer2SpawnOffsetY = 0.0;  
  
  private double customer2InitialVx = 0.0;
  
  private double customer2HorizontalFraction = 0.3; 
  
  
  private java.util.List<BufferedImage> customer2FramesOriginal = new java.util.ArrayList<>();
  
  private java.util.List<BufferedImage> customer2FramesScaled = new java.util.ArrayList<>();
  private int customer2FrameIndex = 0;
  
  private long customer2AnimStartMillis = 0L;
  private int customer2FrameDurationMs = 60; 
  
  private int lastCustomer2ScaledW = -1;
  private int lastCustomer2ScaledH = -1;
  private BufferedImage cloudImage = null;
  private Imagesfood cloud;
  
  private BufferedImage cloudAppleImage = null;
  private Imagesfood cloudApple;
  private String cloudFoodKey = null;  
  private boolean showCloudApple = false;
  private final java.util.Random rand = new java.util.Random();

  
  public void onCorrectAnswer() {
    
    pointAmount += 1;
    
    try {
      java.util.List<String> unlocked = FoodEach.allUnlockedFood;
      if (unlocked != null && !unlocked.isEmpty()) {
        String key = unlocked.get(rand.nextInt(unlocked.size()));
        cloudFoodKey = key;  
        Imagesfood f = new Imagesfood(key);
        BufferedImage bi = f.getImage();
        if (bi != null) {
          cloudApple = f;
          cloudAppleImage = bi;
          showCloudApple = true;
        }
      }
    } catch (Throwable t) {
      
    }
    repaint();
  }

  
  private final java.util.Map<BufferedImage, BufferedImage> grayscaleCache = new java.util.HashMap<>();

  
  private BufferedImage toGray(BufferedImage src) {
    if (src == null) return null;
    BufferedImage cached = grayscaleCache.get(src);
    if (cached != null) return cached;
    int w = src.getWidth();
    int h = src.getHeight();
    BufferedImage gray = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < h; y++) {
      for (int x = 0; x < w; x++) {
        int argb = src.getRGB(x, y);
        int a = (argb >> 24) & 0xff;
        int r = (argb >> 16) & 0xff;
        int g = (argb >> 8) & 0xff;
        int b = argb & 0xff;
        int lum = (int) Math.round(0.2126 * r + 0.7152 * g + 0.0722 * b);
        int grayPixel = (a << 24) | (lum << 16) | (lum << 8) | lum;
        gray.setRGB(x, y, grayPixel);
      }
    }
    grayscaleCache.put(src, gray);
    return gray;
  }

  
  private BufferedImage getImageForKey(BufferedImage img, String key) {
    if (img == null) return null;
    try {
      if (FoodEach.allUnlockedFood.contains(key)) return img;
    } catch (Throwable t) {
      
      return img;
    }
    return toGray(img);
  }

  
  private int getPriceForKey(String key) {
    try {
      FoodEach fe = FoodEach.getByName(key);
      if (fe != null) return fe.getPrice();
    } catch (Throwable t) {
      
    }
    return 0;
  }

  
  private boolean isFoodUnlocked(String key) {
    try {
      return FoodEach.allUnlockedFood.contains(key);
    } catch (Throwable t) {
      return false;
    }
  }

  
  private int getUnlockCostForKey(String key) {
    try {
      FoodEach fe = FoodEach.getByName(key);
      if (fe != null) return fe.getReqMoney();
    } catch (Throwable t) {
      
    }
    return 0;
  }

  
  private void attemptUnlock(String key) {
    try {
      FoodEach fe = FoodEach.getByName(key);
      if (fe != null) {
        int cost = fe.getReqMoney();
        if (moneyAmount >= cost) {
          
          if (fe.unlock(moneyAmount)) {
            
            moneyAmount -= cost;
            repaint();
          }
        }
      }
    } catch (Throwable t) {
      
    }
  }

  private void consumePointOnClick() {
    if (pointAmount > 0) pointAmount -= 0;
    repaint();
  }

  private String getInventoryNumberLabel(String key) {
    try {
      if (isFoodUnlocked(key)) {
        return Integer.toString(getPriceForKey(key)) + "$";
      } else {
        return Integer.toString(getUnlockCostForKey(key)) + "p";
      }
    } catch (Throwable t) {
      return "0p";
    }
  }

  
  private void removeInventoryImageForKey(String key) {
    switch (key) {
      case "apple": appleImage = null; break;
      case "apple_pie": apple_pieImage = null; break;
      case "avocado": avocadoImage = null; break;
      case "boar_head": boar_headImage = null; break;
      case "bread": breadImage = null; break;
      case "cheese": cheeseImage = null; break;
      case "cheesecake": cheesecakeImage = null; break;
      case "chicken": chickenImage = null; break;
      case "cookie": cookieImage = null; break;
      case "dragon_fruit": dragon_fruitImage = null; break;
      case "fish": fishImage = null; break;
      case "fried_eggs": fried_eggsImage = null; break;
      case "honey": honeyImage = null; break;
      case "pineapple": pineappleImage = null; break;
      case "pretzel": pretzelImage = null; break;
      case "pumpkin_pie": pumpkin_pieImage = null; break;
      case "shrimp": shrimpImage = null; break;
      case "sushi": sushiImage = null; break;
      case "t-bone": tboneImage = null; break;
      case "watermelon": watermelonImage = null; break;
    }
  }

  private BufferedImage questionImage = null;
  private Imagesfood question;

  private BufferedImage moneyImage = null;
  private Imagesfood money;
  private BufferedImage pointImage = null;
  private Imagesfood point;
  
  private int moneyAmount = 0;
  private int pointAmount = 0;
  
  private static class DragItem {
    BufferedImage img;
    int vx, vy, vW, vH;
    int offsetX, offsetY;
    String key;  
    DragItem(BufferedImage img, int vx, int vy, int vW, int vH, String key) {
      this.img = img; this.vx = vx; this.vy = vy; this.vW = vW; this.vH = vH; this.offsetX = 0; this.offsetY = 0; this.key = key;
    }
  }
  private final java.util.List<DragItem> activeDrags = new java.util.ArrayList<>();
  private DragItem currentDrag = null;
  
  private static final int VIRTUAL_WIDTH = 1535;
  private static final int VIRTUAL_HEIGHT = 830;

  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    
    
    g.setColor(Color.BLACK);
    
    g.fillRect(0, 0, getWidth(), getHeight());

    
    
    int panelW = getWidth();
    int panelH = getHeight();
    double scale = Math.min((double) panelW / VIRTUAL_WIDTH, (double) panelH / VIRTUAL_HEIGHT);
    @SuppressWarnings("unused")
    int offsetX = (int) Math.round((panelW - VIRTUAL_WIDTH * scale) / 2.0);
    @SuppressWarnings("unused")
    int offsetY = (int) Math.round((panelH - VIRTUAL_HEIGHT * scale) / 2.0);

    
    if (storeImageOriginal != null)
    {
      
      drawVirtualImage(g, storeImageOriginal, 0, 0, VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
    }
    
    if (showInventory) {
    
    Graphics2D g2Measure = (Graphics2D) g;
    Font originalFont = g2Measure.getFont();
    Font bigFont = originalFont.deriveFont(originalFont.getSize2D() * 2f);
    g2Measure.setFont(bigFont);
    FontMetrics fm = g2Measure.getFontMetrics();
    g2Measure.setFont(originalFont); 
    
    String[] foodNames = {"Apple", "Apple Pie", "Avocado", "Boar Head", "Bread", "Cheese", 
                          "Cheesecake", "Chicken", "Cookie", "Dragon Fruit", "Fish", "Fried Eggs",
                          "Honey", "Pineapple", "Pretzel", "Pumpkin Pie", "Shrimp", "Sushi", "T-Bone", "Watermelon"};
    int maxTextWidth = 0;
    for (String name : foodNames) {
      int textWidth = fm.stringWidth(name);
      maxTextWidth = Math.max(maxTextWidth, textWidth);
    }
    
    
    int maxTextWidthVirtual = (int) Math.round(maxTextWidth / scale);
    
    
    
    int tanBoxWidth = 290 + 50 + 8 + maxTextWidthVirtual -20; 
    
    
    g.setColor(new Color(210, 180, 140)); 
    
    drawVirtualRectangle(g, 30 + uiShiftX, 70 + uiShiftY, tanBoxWidth, 620);
    
    
    g.setColor(Color.BLACK);
    drawVirtualBorder(g, 30 + uiShiftX, 70 + uiShiftY, tanBoxWidth, 620, 4); 
    
    
    drawVirtualTitleString(g, "Inventory", 30 + uiShiftX + tanBoxWidth / 2, 120 + uiShiftY);
        
    
    final int V_IMG_W = 50;
    final int V_IMG_H = 50;
    
    
    final int leftX = 35 + uiShiftX;
    final int rightX = 220 + uiShiftX;
    final int startY = 135 + uiShiftY;
    
    final int rowSpacing = 55;

    if (appleImage != null)
    {
      int row = 0;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(appleImage, "apple"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("apple")) {
        drawVirtualString(g, "Apple " + getPriceForKey("apple") + "$", imgX + V_IMG_W + 8, imgY + 30);
      } else {
        drawVirtualString(g, "Apple - " + getUnlockCostForKey("apple") + "p", imgX + V_IMG_W + 8, imgY + 30);
      }
    }
    if (apple_pieImage != null)
    {
      int row = 0;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(apple_pieImage, "apple_pie"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("apple_pie")) {
        drawVirtualString(g, "Apple Pie " + getPriceForKey("apple_pie") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Apple Pie - " + getUnlockCostForKey("apple_pie") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }
    if (avocadoImage != null)
    {
      int row = 1;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(avocadoImage, "avocado"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("avocado")) {
        drawVirtualString(g, "Avocado " + getPriceForKey("avocado") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Avocado - " + getUnlockCostForKey("avocado") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }
    if (boar_headImage != null)
    {
      int row = 1;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(boar_headImage, "boar_head"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("boar_head")) {
        drawVirtualString(g, "Boar Head " + getPriceForKey("boar_head") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Boar Head - " + getUnlockCostForKey("boar_head") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }
    if (breadImage != null)
    {
      int row = 2;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(breadImage, "bread"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("bread")) {
        drawVirtualString(g, "Bread " + getPriceForKey("bread") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Bread - " + getUnlockCostForKey("bread") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }
    if (cheeseImage != null)
    {
      int row = 2;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(cheeseImage, "cheese"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("cheese")) {
        drawVirtualString(g, "Cheese " + getPriceForKey("cheese") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Cheese - " + getUnlockCostForKey("cheese") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }
    if (cheesecakeImage != null)
    {
      int row = 3;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(cheesecakeImage, "cheesecake"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("cheesecake")) {
        drawVirtualString(g, "Cheesecake " + getPriceForKey("cheesecake") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Cheesecake - " + getUnlockCostForKey("cheesecake") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }
    if (chickenImage != null)
    {
      int row = 3;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(chickenImage, "chicken"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("chicken")) {
        drawVirtualString(g, "Chicken " + getPriceForKey("chicken") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Chicken - " + getUnlockCostForKey("chicken") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }
    if (cookieImage != null)
    {
      int row = 4;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(cookieImage, "cookie"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("cookie")) {
        drawVirtualString(g, "Cookie " + getPriceForKey("cookie") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Cookie - " + getUnlockCostForKey("cookie") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }
    if (dragon_fruitImage != null)
    {
      int row = 4;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(dragon_fruitImage, "dragon_fruit"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("dragon_fruit")) {
        drawVirtualString(g, "Dragon Fruit " + getPriceForKey("dragon_fruit") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Dragon Fruit - " + getUnlockCostForKey("dragon_fruit") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }

    if (fishImage != null)
    {
      int row = 5;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(fishImage, "fish"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("fish")) {
        drawVirtualString(g, "Fish " + getPriceForKey("fish") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Fish - " + getUnlockCostForKey("fish") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }

    if (fried_eggsImage != null)
    {
      int row = 5;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(fried_eggsImage, "fried_eggs"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("fried_eggs")) {
        drawVirtualString(g, "Fried Eggs " + getPriceForKey("fried_eggs") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Fried Eggs - " + getUnlockCostForKey("fried_eggs") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }

    if (honeyImage != null)
    {
      int row = 6;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(honeyImage, "honey"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("honey")) {
        drawVirtualString(g, "Honey " + getPriceForKey("honey") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Honey - " + getUnlockCostForKey("honey") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }

    if (pineappleImage != null)
    {
      int row = 6;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(pineappleImage, "pineapple"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("pineapple")) {
        drawVirtualString(g, "Pineapple " + getPriceForKey("pineapple") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Pineapple - " + getUnlockCostForKey("pineapple") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }

    if (pretzelImage != null)
    {
      int row = 7;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(pretzelImage, "pretzel"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("pretzel")) {
        drawVirtualString(g, "Pretzel " + getPriceForKey("pretzel") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Pretzel - " + getUnlockCostForKey("pretzel") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }

    if (pumpkin_pieImage != null)
    {
      int row = 7;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(pumpkin_pieImage, "pumpkin_pie"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("pumpkin_pie")) {
        drawVirtualString(g, "Pumpkin Pie " + getPriceForKey("pumpkin_pie") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Pumpkin Pie - " + getUnlockCostForKey("pumpkin_pie") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }

    if (shrimpImage != null)
    {
      int row = 8;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(shrimpImage, "shrimp"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("shrimp")) {
        drawVirtualString(g, "Shrimp " + getPriceForKey("shrimp") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Shrimp - " + getUnlockCostForKey("shrimp") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }
    
    if (sushiImage != null)
    {
      int row = 8;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(sushiImage, "sushi"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("sushi")) {
        drawVirtualString(g, "Sushi " + getPriceForKey("sushi") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Sushi - " + getUnlockCostForKey("sushi") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }

    if (tboneImage != null)
    {
      int row = 9;
      int imgX = leftX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(tboneImage, "t-bone"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("t-bone")) {
        drawVirtualString(g, "T-Bone " + getPriceForKey("t-bone") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "T-Bone - " + getUnlockCostForKey("t-bone") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }
    
    if (watermelonImage != null)
    {
      int row = 9;
      int imgX = rightX;
      int imgY = startY + row * rowSpacing;
      drawVirtualImage(g, getImageForKey(watermelonImage, "watermelon"), imgX, imgY, V_IMG_W, V_IMG_H);
      if (isFoodUnlocked("watermelon")) {
        drawVirtualString(g, "Watermelon " + getPriceForKey("watermelon") + "$", imgX + V_IMG_W + 8, imgY + 35);
      } else {
        drawVirtualString(g, "Watermelon - " + getUnlockCostForKey("watermelon") + "p", imgX + V_IMG_W + 8, imgY + 35);
      }
    }
    }

    if (man_idleImage != null)
    {
      int imgX = 1300; 
      int imgY = 520;  
      
      drawVirtualImage(g, man_idleImage, imgX, imgY, man_idleImage.getWidth(), man_idleImage.getHeight());
    }

    
    if (shoppingcartImage != null)
    {
      
      drawVirtualImage(g, shoppingcartImage, 50 + uiShiftX, 97 + uiShiftY, 60, 60);
    }
    if (customer2Image != null && customer2Visible)
    {
      
      final int V_W = 250;
      final int V_H = 250;
      
      if (customer2State == 2) {
        drawVirtualImage(g, customer2Image, (int)Math.round(customer2Vx), (int)Math.round(customer2Vy), V_W, V_H);
        
        
      } else {
      
      
      int aw = Math.max(1, (int) Math.round(V_W * scale));
      int ah = Math.max(1, (int) Math.round(V_H * scale));

      BufferedImage frameToDraw = null;
      if (!customer2FramesOriginal.isEmpty()) {
        
        if (customer2FramesScaled.size() != customer2FramesOriginal.size() || lastCustomer2ScaledW != aw || lastCustomer2ScaledH != ah) {
          customer2FramesScaled.clear();
          for (BufferedImage orig : customer2FramesOriginal) {
            BufferedImage scaled = getScaledImage(orig, aw, ah);
            customer2FramesScaled.add(scaled);
          }
          lastCustomer2ScaledW = aw;
          lastCustomer2ScaledH = ah;
        }

        if (!customer2FramesScaled.isEmpty()) {
          long now = System.currentTimeMillis();
          if (customer2AnimationPaused && customer2PausedFrameIndex >= 0) {
            int safe = Math.max(0, Math.min(customer2PausedFrameIndex, customer2FramesScaled.size()-1));
            frameToDraw = customer2FramesScaled.get(safe);
          } else {
            long elapsed = now - customer2AnimStartMillis;
            if (customer2AnimStartMillis == 0L) elapsed = 0;
            int idx = (int) ((elapsed / Math.max(1, customer2FrameDurationMs)) % customer2FramesScaled.size());
            frameToDraw = customer2FramesScaled.get(idx);
          }
        }
      }

        
        if (frameToDraw == null) {
          drawVirtualImage(g, customer2Image, 750 + uiShiftX, 400 + uiShiftY, V_W, V_H);
        } else {
          
          int offsetX_local = (int) Math.round((panelW - VIRTUAL_WIDTH * scale) / 2.0);
          int offsetY_local = (int) Math.round((panelH - VIRTUAL_HEIGHT * scale) / 2.0);
          int ax = offsetX_local + (int) Math.round(customer2Vx * scale);
          int ay = offsetY_local + (int) Math.round(customer2Vy * scale);
          g.drawImage(frameToDraw, ax, ay, aw, ah, this);
        }
      }
    } else {
      
    }
    if (cloudImage != null)
    {
      
      drawVirtualImage(g, cloudImage, 1300 + uiShiftX, 460 + uiShiftY, 100, 100);
    }
    if (cloudAppleImage != null)
    {
      
      drawVirtualImage(g, cloudAppleImage, 1336 + uiShiftX, 500 + uiShiftY, 30, 30);
    }

    if (questionImage != null)
    {
      
      drawVirtualImage(g, questionImage, 800, 75, 150, 150);
    }

    if (pointImage != null)
    {
      
      drawVirtualImage(g, pointImage, 250, 25, 250, 100);
      
      drawVirtualString(g, Integer.toString(pointAmount), 390, 94);
    }
    if (moneyImage != null)
    {
      
      drawVirtualImage(g, moneyImage, 480, 25, 250, 100);
      
      drawVirtualString(g, Integer.toString(moneyAmount), 610, 97);
    }

    
    if (!activeDrags.isEmpty()) {
      for (DragItem d : activeDrags) {
        if (d != null && d.img != null) drawVirtualImage(g, d.img, d.vx, d.vy, d.vW, d.vH);
      }
    }

    
    try {
      Graphics2D g2coords = (Graphics2D) g;
      Font oldF = g2coords.getFont();
      Color oldC = g2coords.getColor();
      
      Font small = oldF.deriveFont(12f);
      g2coords.setFont(small);
      String coords = "x:" + mouseX + " y:" + mouseY;
      
      g2coords.setColor(Color.BLACK);
      g2coords.drawString(coords, 11, 19);
      
      g2coords.setColor(Color.WHITE);
      g2coords.drawString(coords, 10, 18);
      g2coords.setFont(oldF);
      g2coords.setColor(oldC);
    } catch (Throwable t) {
      
    }

    
    
  }

  
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
    
    Font bigFont = oldFont.deriveFont(oldFont.getSize2D() * 2f);
    g2.setFont(bigFont);
    g2.setColor(Color.BLACK);
    g2.drawString(text, ax, ay);
    
    g2.setFont(oldFont);
    g2.setColor(oldColor);
  }

  
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
    
    Font boldFont = oldFont.deriveFont(java.awt.Font.BOLD, oldFont.getSize2D() * 2.5f);
    g2.setFont(boldFont);
    g2.setColor(Color.BLACK);
    FontMetrics fm = g2.getFontMetrics();
    int textWidth = fm.stringWidth(text);
    int ax = offsetX + (int) Math.round(vxCenter * scale) - textWidth / 2;
    g2.drawString(text, ax, ay);
    
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
            JFrame frame = new JFrame("Stoe Simulator");
            Main panel = new Main();
              mainInstance = panel;
            frame.add(panel);

            
            new Driver();

            panel.store = new Imagesfood("store");
            panel.storeImageOriginal = panel.store.getImage();
            panel.storeImageScaled = null; 
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

            
            panel.pointAmount = 0;
            panel.moneyAmount = 0;

            

            
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
          
                
                panel.customer2 = new Imagesfood("customer2_idle/1");
                panel.customer2Image = panel.customer2.getImage();
                
                
                panel.customer2Vx = panel.customer2SpawnPercentX * VIRTUAL_WIDTH + panel.customer2SpawnOffsetX;
                panel.customer2Vy = panel.customer2SpawnPercentY * VIRTUAL_HEIGHT + panel.customer2SpawnOffsetY;
                
                panel.customer2InitialVx = panel.customer2Vx;
                panel.customer2State = 0;
                long now = System.currentTimeMillis();
                panel.lastUpdateMillis = now;
                panel.customer2SpawnTimeMillis = now; 
                
                panel.cloud = new Imagesfood("cloud");
                panel.cloudImage = panel.cloud.getImage();
                panel.cloudApple = new Imagesfood("apple");
                panel.cloudAppleImage = panel.cloudApple.getImage();
                panel.showCloudApple = (panel.cloudAppleImage != null);

                  
                  new Thread(() -> {
                    java.util.List<BufferedImage> loaded = new java.util.ArrayList<>();
                    for (int i = 1; i <= 10; i++) {
                      Imagesfood f = new Imagesfood("customer2_walking/" + i);
                      BufferedImage bi = f.getImage();
                      if (bi != null) loaded.add(bi);
                    }
                    if (!loaded.isEmpty()) {
                      
                      SwingUtilities.invokeLater(() -> {
                        panel.customer2FramesOriginal.clear();
                        panel.customer2FramesOriginal.addAll(loaded);
                        
                        panel.customer2FramesScaled.clear();
                        panel.lastCustomer2ScaledW = -1;
                        panel.lastCustomer2ScaledH = -1;
                        panel.customer2AnimStartMillis = System.currentTimeMillis();
                        System.out.println("Loaded customer2 frames: " + panel.customer2FramesOriginal.size());
                        panel.repaint();
                      });
                    }
                  }, "customer2-loader").start();

            
            
            
            
            
            
            
            
            
            
            
            
            
            
        });
        

        

    }
    
}
