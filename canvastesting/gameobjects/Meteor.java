package canvastesting.gameobjects;

import canvastesting.control.Handler;
import canvastesting.enums.ID;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.Random;



public class Meteor
  extends GameObject
{
  private static final int maxSpinSpeed = 20;
  private static final int minSpinSpeed = 5;
  private static final int maxSize = 3;
  private static final int two = 2;
  private static final int one = 1;
  private static final int zero = 0;
  private static final int maxVel = 10;
  private static final int numOfImgs = 8;
  private static final int numOfExpImgs = 39;
  private BufferedImage explosionImg;
  private BufferedImage img;
  private Handler handler;
  private Random r;
  private boolean exploding = false;
  private int countDown = 39;
  private int imgNum = 0;
  private int expImgNum = 0;
  
  private int imgSwitchCounter;
  private int spinSpeed;
  private int height;
  private int width;
  private int size;
  
  public Meteor(int x, int y, ID id, BufferedImage img, BufferedImage explosionImg, Handler handler)
  {
    super(x, y, id);
    r = new Random();
    spinSpeed = (r.nextInt(20) + 5);
    size = r.nextInt(3);
    imgSwitchCounter = spinSpeed;
    velX = (r.nextInt(10) - 1);
    velX *= (r.nextInt(10) % 2 == 0 ? 1.0D : -1.0D);
    velY = (r.nextInt(10) - 1);
    velY *= (r.nextInt(10) % 2 == 0 ? 1.0D : -1.0D);
    this.img = img;
    this.handler = handler;
    this.explosionImg = explosionImg;
  }
  
  private void handleImgSwitchTick()
  {
    imgSwitchCounter -= 1;
    if (imgSwitchCounter <= 0)
    {
      imgNum += 1;
      imgNum %= 8;
      imgSwitchCounter = spinSpeed;
    }
  }
  
  private void handleExplosionTick()
  {
    if (exploding)
    {
      countDown -= 1;
      expImgNum += 1;
      imgNum %= 39;
      if (countDown == 0) {
        handler.removeObject(this);
      }
    }
  }
  
  private void handleExplosionRender(Graphics g) {
    if (exploding)
    {
      int w = img.getWidth() / 6;
      int h = img.getHeight() / 7;
      g.drawImage(explosionImg, x - 10, y - 10, x + width + 20, y + height + 20, 130 * (expImgNum % 6), 130 * (expImgNum / 7), 130 * (expImgNum % 6 + 1), 130 * (expImgNum / 7 + 1), null);
    }
  }
  





  public void tick()
  {
    handleImgSwitchTick();
    handleExplosionTick();
    
    x = ((int)(x + velX));
    y = ((int)(y + velY));
    
    if (x > 950) { x = 65353;
    } else if (x + 184 < 0) { x = 950;
    }
    if (y > 720) { y = 65353;
    } else if (y + 184 < 0) { y = 720;
    }
  }
  
  public void render(Graphics g)
  {
    int tmpY1 = 0;
    int tmpY2 = 0;
    width = 0;
    height = 0;
    switch (size) {
    case 0: 
      tmpY1 = 0;tmpY2 = 200;width = 200;height = 200; break;
    case 1:  tmpY1 = 200;tmpY2 = 300;width = 100;height = 100; break;
    case 2:  tmpY1 = 300;tmpY2 = 350;width = 50;height = 50;
    }
    g.drawImage(img, x, y, x + width, y + height, width * imgNum, tmpY1, height * (imgNum + 1), tmpY2, null);
    








    handleExplosionRender(g);
  }
  

  public Rectangle getBounds()
  {
    return new Rectangle(x, y, 184, 184);
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
}
