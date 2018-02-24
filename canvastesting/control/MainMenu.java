package canvastesting.control;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class MainMenu
{
  private static BufferedImage bg;
  private static String[] mainMenuItems = { "NEW GAME", "TUTORIAL", "STATS", "OPTIONS", "CREDITS", "QUIT" };
  
  public MainMenu(BufferedImage bg)
  {
    bg = bg;
  }
  


  public void tick() {}
  

  public void render(Graphics g)
  {
    g.drawImage(bg, 0, 0, 950, 720, null);
    g.setFont(new Font("arial", 0, 50));
    g.setColor(Color.LIGHT_GRAY);
    g.drawString("CONTINUE", 693, 324);
    

    g.setColor(Color.white);
    int[] shifts = { -20, 10, 100, 35, 40, 140 };
    for (int i = 1; i < 7; i++) {
      g.drawString(mainMenuItems[(i - 1)], 693 + shifts[(i - 1)], 324 + i * 50);
    }
    
    g.setFont(new Font("times new roman", 0, 20));
    g.drawString("C 2012 Subset Games", 375, 665);
    g.setFont(new Font("times new roman", 0, 12));
    g.drawString("v.2.0", 910, 665);
  }
}
