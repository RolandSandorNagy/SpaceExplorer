package canvastesting.control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class KeyInput extends KeyAdapter
{
  private static final int key_ESC = 27;
  private static final int one = 1;
  private Handler handler;
  
  public KeyInput(Handler handler)
  {
    this.handler = handler;
  }
  

  public void keyPressed(KeyEvent e)
  {
    int key = e.getKeyCode();
    if (key == 27)
      System.exit(1);
    if (!handler.pressedKeys.contains(Integer.valueOf(key))) {
      handler.pressedKeys.add(Integer.valueOf(key));
    }
  }
  
  public void keyReleased(KeyEvent e)
  {
    Integer key = Integer.valueOf(e.getKeyCode());
    handler.pressedKeys.remove(key);
  }
}
