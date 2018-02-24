package canvastesting.gameobjects;

import canvastesting.control.Handler;
import canvastesting.enums.ID;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;






public class Bullet
  extends GameObject
{
  private static final double rightAngle = 90.0D;
  private static final int zero = 0;
  private static final int one = 1;
  private int width = 4;
  private int height = 8;
  private double deg;
  private BufferedImage img;
  private Handler handler;
  public boolean counted = false;
  

  public Bullet(int x, int y, ID id, int velX, int velY, double deg, BufferedImage img, Handler handler)
  {
    super(x, y, id);
    this.velX = velX;
    this.velY = velY;
    this.deg = (deg - 90.0D);
    this.img = img;
    this.handler = handler;
  }
  

  public void tick()
  {
    x = ((int)(x + velX));
    y = ((int)(y + velY));
    if ((x < 0) || (x > 950) || (y < 0) || (y > 720))
    {



      handler.removeObject(this);
    }
  }
  

  public void render(Graphics g)
  {
    rotateAndDrawImg(img, deg, g, 1);
  }
  

  public Rectangle getBounds()
  {
    return new Rectangle(x, y, width, height);
  }
}
