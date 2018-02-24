package canvastesting.gameobjects;

import canvastesting.control.Handler;
import canvastesting.datastructs.TableMap;
import canvastesting.enums.ID;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.LinkedList;
import java.util.Random;

public class Enemy extends GameObject
{
  private static final int zero = 0;
  private static final int one = 1;
  private static final int two = 2;
  private static final int three = 3;
  private static final int four = 4;
  private static final int degDeviation = 5;
  private static final int twoPiDeg = 360;
  private static final int lockDownLimit = 30;
  private static final int sensorDistance = 400;
  private static final int degUnit = 5;
  private static final int width = 40;
  private static final int height = 40;
  private static final double rightAngle = 90.0D;
  private static final double cAccelerateUnit = 0.025D;
  private static final double cDeccelerateUnit = 0.25D;
  private double c = 0.0D;
  private double deg = 0.0D;
  private int shootingLockCountDown = 30;
  private int countDown = 39;
  private int expImgNum = 0;
  private int quarter = 1;
  private int pQuarter = 0;
  private boolean accelerate = false;
  private boolean exploding = false;
  
  private BufferedImage explosionImg;
  
  private BufferedImage bulletImg;
  private BufferedImage img;
  private Handler handler;
  private Player player;
  private Random r;
  
  public Enemy(int x, int y, ID id, BufferedImage img, BufferedImage explosionImg, BufferedImage bulletImg, Handler handler, Player player)
  {
    super(x, y, id);
    r = new Random();
    velX = 0.0D;
    velY = 0.0D;
    this.explosionImg = explosionImg;
    this.bulletImg = bulletImg;
    this.img = img;
    this.handler = handler;
    this.player = player;
  }
  

  public void tick()
  {
    if (shootingLockCountDown < 30)
    {
      shootingLockCountDown += 1;
    }
    if (exploding)
    {
      countDown -= 1;
      expImgNum += 1;
      if (countDown == 0)
        handler.removeObject(this);
      x = ((int)(x + velX));
      y = ((int)(y + velY)); return;
    }
    double a;
    double a;
    if (x < player.getX()) a = player.getX() - x; else
      a = x - player.getX();
    double b;
    double b;
    if (y < player.getY()) b = player.getY() - y; else {
      b = y - player.getY();
    }
    int distance = (int)Math.sqrt(a * a + b * b);
    if (distance > 400)
      return;
    double toDeg;
    double plusDeg;
    if (x <= player.getX())
    {
      if (y <= player.getY())
      {
        double toDeg = Math.atan(b / a);
        double plusDeg = 90.0D;
        pQuarter = 2;
      }
      else
      {
        double toDeg = Math.atan(a / b);
        double plusDeg = 0.0D;
        pQuarter = 1;
      }
      

    }
    else if (y <= player.getY())
    {
      double toDeg = Math.atan(a / b);
      double plusDeg = 180.0D;
      pQuarter = 3;
    }
    else
    {
      toDeg = Math.atan(b / a);
      plusDeg = 270.0D;
      pQuarter = 4;
    }
    
    int desiredDeg = (int)(Math.toDegrees(toDeg) + plusDeg);
    
    if ((deg <= desiredDeg - 5) || (deg >= desiredDeg + 5))
    {

      accelerate = false;
      deccelerate();
      if (((quarter == 1) && (pQuarter == 2)) || ((quarter == 2) && (pQuarter == 3)) || ((quarter == 3) && (pQuarter == 4)) || ((quarter == 4) && (pQuarter == 1)))
      {


        deg = (deg + 1.0D <= 355.0D ? deg + 5.0D : 0.0D);
      } else if (((quarter == 1) && (pQuarter == 4)) || ((quarter == 2) && (pQuarter == 1)) || ((quarter == 3) && (pQuarter == 2)) || ((quarter == 4) && (pQuarter == 3)))
      {


        deg = (deg - 1.0D >= 0.0D ? deg - 5.0D : 355.0D);
      } else if (quarter == pQuarter)
      {
        if (deg > desiredDeg) {
          deg = (deg - 1.0D >= 0.0D ? deg - 5.0D : 355.0D);
        } else {
          deg = (deg + 1.0D <= 355.0D ? deg + 5.0D : 0.0D);
        }
      } else {
        deg = (deg + 1.0D <= 355.0D ? deg + 5.0D : 0.0D);
      }
    }
    else {
      shoot();
      if (distance > 200)
        accelerate();
    }
    quarter = ((int)deg % 360 / 90 + 1);
    double degR = Math.toRadians(deg);
    velX = ((int)(Math.sin(degR) * c));
    velY = (-(int)(Math.cos(degR) * c));
    x = ((int)(x + velX));
    y = ((int)(y + velY));
    
    if (x > 950) {
      x = -39;
    } else if (x + 40 < 0) {
      x = 950;
    }
    if (y > 720) {
      y = -39;
    } else if (y + 40 < 0) {
      y = 720;
    }
  }
  
  void accelerate() {
    if (c < 2.0D)
      c += 0.025D;
    accelerate = true;
  }
  
  void deccelerate() {
    if (c > 0.0D)
      c -= 0.25D;
    if (c < 0.0D) {
      c = 0.0D;
    }
  }
  
  public void render(Graphics g)
  {
    int tmpY1 = 0;
    int tmpY2 = 0;
    
    rotateAndDrawImg(img, deg, g, 1);
    if (exploding)
    {
      int w = img.getWidth() / 6;
      int h = img.getHeight() / 7;
      g.drawImage(explosionImg, x - 10, y - 10, x + 40 + 20, y + 40 + 20, 130 * (expImgNum % 6), 130 * (expImgNum / 7), 130 * (expImgNum % 6 + 1), 130 * (expImgNum / 7 + 1), null);
    }
  }
  










  public Rectangle getBounds()
  {
    return new Rectangle(x, y, 40, 40);
  }
  
  public void setExploding(boolean exploding)
  {
    this.exploding = exploding;
  }
  
  public boolean getExploding()
  {
    return exploding;
  }
  
  public BufferedImage getImg()
  {
    return img;
  }
  
  public BufferedImage getImgExp()
  {
    return explosionImg;
  }
  
  public boolean pixelCollise(GameObject obj)
  {
    if (obj.getId() != ID.Player) return false;
    BufferedImage img = ((Player)obj).getImg();
    

    Color[] colorData1 = new Color[33856];
    Color[] colorData2 = new Color['Ԍ'];
    byte[] data1 = ((DataBufferByte)this.img.getRaster().getDataBuffer()).getData();
    byte[] data2 = ((DataBufferByte)((Player)obj).getImg().getRaster().getDataBuffer()).getData();
    
    int top = Math.max(y, obj.getY());
    int bottom = Math.min(y + 184, obj.getY() + 38);
    int left = Math.max(x, obj.getX());
    int right = Math.min(x + 184, obj.getX() + 34);
    
    for (int i = top; i < bottom; i++)
    {
      for (int j = left; j < right; j++)
      {
        Color A = new Color(data1[((i - y) * (x + 184) + (j - x))]);
        Color B = new Color(data1[((i - obj.getY()) * (obj.getX() + 34) + (j - obj.getX()))]);
        
        if ((A.getTransparency() > 0) && (B.getTransparency() > 0)) {
          return true;
        }
      }
    }
    return false;
  }
  
  private void shoot()
  {
    if (shootingLockCountDown < 30) {
      return;
    }
    double degR = Math.toRadians(deg);
    double vx = (int)(Math.sin(degR) * 10.0D) + velX;
    double vy = -((int)(Math.cos(degR) * 10.0D) - velY);
    
    Bullet b = new Bullet(getHeadX(), getHeadY(), ID.EnemyBullet, (int)vx, (int)vy, deg, bulletImg, handler);
    





    handler.map.getCurrentScene().object.add(b);
    shootingLockCountDown = 0;
  }
  
  private int getHeadX()
  {
    double degR = Math.toRadians(deg);
    int headX = (int)(Math.sin(degR) * 19.0D);
    return x + headX + 11;
  }
  
  private int getHeadY()
  {
    double degR = Math.toRadians(deg);
    int headY = -(int)(Math.cos(degR) * 19.0D);
    return y + headY + 16;
  }
}
