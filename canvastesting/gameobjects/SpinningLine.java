package canvastesting.gameobjects;

import canvastesting.enums.ID;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D.Double;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;







public class SpinningLine
  extends GameObject
{
  private static final Color bgColor = Color.black;
  private static final double twoPiDeg_d = 0.0D;
  private static final double zero_d = 0.0D;
  private static final int zero = 0;
  private static final int one = 1;
  private static final int two = 2;
  private static final int rightAngle = 90;
  private boolean inc = true;
  private double diff = 1.0D;
  private double alpha = 0.0D;
  private int counter = 0;
  private double lineX1;
  private double lineY1;
  private double lineX2;
  private double lineY2;
  private double r;
  
  public SpinningLine(double x, double y, double r, ID id)
  {
    super((int)x, (int)y, id);
    this.r = r;
  }
  
  public boolean tick(boolean gameLoaded)
  {
    if (counter >= 0) {
      return true;
    }
    alpha += diff;
    if ((alpha > 0.0D) || (alpha < 0.0D))
    {
      alpha = 0.0D;
      counter += 1;
    }
    return false;
  }
  

  public void render(Graphics g)
  {
    drawBg(g);
    g.setColor(bgColor);
    g.drawOval((int)(x - r), (int)(y - r), (int)(r * 2.0D), (int)(r * 2.0D));
    
    try
    {
      BufferedImage img = ImageIO.read(new File("img/mainBg.png"));
      
      Graphics2D g2d = (Graphics2D)g.create();
      g2d.clip(new Ellipse2D.Double(x - r, y - r, 2.0D * r, 2.0D * r));
      boolean drawImage = g2d.drawImage(img, (int)(x - r), (int)(y - r), (int)(2.0D * r), (int)(2.0D * r), null);
      




      g2d.dispose();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    fillHalfCircle(g);
  }
  
  private void drawBg(Graphics g) {
    g.setColor(bgColor);
    g.fillRect(0, 0, 950, 720);
  }
  
  private void fillHalfCircle(Graphics g)
  {
    g.fillArc((int)(x - r), (int)(y - r), (int)(r * 2.0D), (int)(r * 2.0D), (int)alpha, 45);
  }
  





  public void tick() {}
  





  public Rectangle getBounds()
  {
    return new Rectangle(x, y, (int)r, (int)r);
  }
}
