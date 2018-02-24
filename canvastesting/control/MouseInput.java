package canvastesting.control;

import canvastesting.gameobjects.PlanetPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter
{
  private Handler handler;
  private MainMenu mainMenu;
  private PlanetPanel panel;
  private int x;
  private int y;
  private int width;
  private int height;
  
  public MouseInput(Handler handler, MainMenu mainMenu)
  {
    this.handler = handler;
    this.mainMenu = mainMenu;
  }
  
  public MouseInput(Handler handler)
  {
    this.handler = handler;
  }
  

  public MouseInput(Handler handler, PlanetPanel panel, int x, int y, int width, int height)
  {
    System.out.println("MouseListener was created.");
    this.handler = handler;
    this.panel = panel;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }
  
  public MouseInput(PlanetPanel panel)
  {
    this.panel = panel;
  }
  




  public void mouseClicked(MouseEvent e)
  {
    int x_t = e.getX();
    int y_t = e.getY();
    

    if ((x_t > x) && (x_t < x + width) && (y_t > y) && (y_t < y + height))
    {





      panel.shut();
      
      Handler.ct.removeMouseListener(this);
    }
  }
  
  public void mouseEntered(MouseEvent e) {}
  
  public void mouseMoved(MouseEvent e) {}
}
