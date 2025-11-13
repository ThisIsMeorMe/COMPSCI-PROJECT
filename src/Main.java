import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Main extends JPanel implements MouseMotionListener
{
  private int mouseX = -10;
  private int mouseY = -10;
  public Main() {
        addMouseMotionListener(this);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        // Red background
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), getHeight());


        // Black circle at mouse position
        g.setColor(Color.BLACK);
        g.fillOval(mouseX - 15, mouseY - 15, 30, 30);
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
        JFrame frame = new JFrame("Red Window with Cursor Circle");
        Main panel = new Main();
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
  //hello
}




