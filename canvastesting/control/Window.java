package canvastesting.control;

import canvastesting.CanvasTesting;
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Window extends Canvas
{
  public JFrame frame;
  
  public Window(int width, int height, String title, CanvasTesting ct)
  {
    frame = new JFrame(title);
    
    frame.setPreferredSize(new Dimension(width, height));
    frame.setMaximumSize(new Dimension(width, height));
    frame.setMinimumSize(new Dimension(width, height));
    
    frame.setDefaultCloseOperation(3);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.add(ct);
    frame.setVisible(true);
    ct.start();
  }
}
