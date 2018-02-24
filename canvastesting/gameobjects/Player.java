package canvastesting.gameobjects;

import canvastesting.control.Handler;
import canvastesting.control.Scene;
import canvastesting.datastructs.ForceVector;
import canvastesting.datastructs.TableMap;
import canvastesting.enums.Direction;
import canvastesting.enums.ID;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;





public class Player
  extends GameObject
{
  public final int width = 34;
  public final int height = 38;
  private final int zero = 0;
  private final int one = 1;
  private final int numOfImgAcc = 0;
  private final int numOfImg = 1;
  private final int numOfBulletImg = 2;
  private final int trbCntDnUnit = 2;
  private final int numOfExpImg = 5;
  private final int degUnit = 5;
  private final int LockCountDownLimit = 10;
  private final int multiTurbo = 10;
  private final int bulletSpeed = 10;
  private final int twoPiDeg = 360;
  private final int key_W = 87;
  private final int key_Space = 32;
  private final int key_A = 65;
  private final int key_D = 68;
  private final int key_S = 83;
  private final int key_T = 84;
  private final int maxTurbo = 200;
  private final double maxC = 30.0D;
  private final double finVelXInitVal = 0.1D;
  private final double cDecrementUnit = 0.25D;
  private final double zero_d = 0.0D;
  private int shootingLockCountDown = 10;
  private int turboCountdown = 200;
  private int countDown = 195;
  private int expImgNum = 0;
  private int fuel = 400;
  private int hull = 200;
  private double finalVelX = 0.0D;
  private double finalVelY = 0.0D;
  private double accDeg = 0.0D;
  private double vxGrav = 0.0D;
  private double vyGrav = 0.0D;
  private double deg = 0.0D;
  private double c = 0.0D;
  private boolean accelerate = false;
  private boolean exploding = false;
  private boolean exited = false;
  private boolean turbo = false;
  
  private LinkedList<ForceVector> vectors;
  
  private BufferedImage explosionImg;
  
  private BufferedImage currentImg;
  private BufferedImage bulletImg;
  private BufferedImage imgAcc;
  private BufferedImage img;
  private Direction whereExited;
  private ForceVector engVect;
  private Handler handler;
  private float shieldColor;
  
  public Player(int x, int y, ID id, BufferedImage img, BufferedImage imgAcc, BufferedImage bulletImg, BufferedImage explosionImg, Handler handler)
  {
    super(x, y, id);
    vectors = new LinkedList();
    engVect = new ForceVector(0.0D, 0.0D, 0.0D);
    shieldColor = 0.5F;
    this.img = img;
    this.imgAcc = imgAcc;
    currentImg = img;
    this.handler = handler;
    this.bulletImg = bulletImg;
    this.explosionImg = explosionImg;
  }
  
  public Player(int x, int y, ID id, LinkedList<BufferedImage> other, Handler handler)
  {
    super(x, y, id);
    vectors = new LinkedList();
    engVect = new ForceVector(0.0D, 0.0D, 0.0D);
    imgAcc = ((BufferedImage)other.get(0));
    img = ((BufferedImage)other.get(1));
    bulletImg = ((BufferedImage)other.get(2));
    explosionImg = ((BufferedImage)other.get(5));
    this.handler = handler;
    currentImg = img;
  }
  
  private void handleShootingTick()
  {
    if (shootingLockCountDown < 10)
    {
      shootingLockCountDown += 1;
    }
    if (fuel <= 0) {
      setExploding();
    }
  }
  
  private void handleKeyInputTick() {
    if (exploding) return;
    for (int i = 0; i < handler.pressedKeys.size(); i++)
    {
      int key = ((Integer)handler.pressedKeys.get(i)).intValue();
      
      if (key == 87) {
        accelerate();
      } else if (key == 32) {
        shoot();
      } else if (key == 65) {
        turnLeft();
      } else if (key == 68) {
        turnRight();
      } else if (key != 83)
      {
        if ((key == 87) && 
          (handler.pressedKeys.contains(Integer.valueOf(84))) && (c >= 30.0D) && (turboCountdown == 200))
        {

          turboON(); } }
    }
    if ((!handler.pressedKeys.contains(Integer.valueOf(87))) && 
      (!handler.pressedKeys.contains(Integer.valueOf(83))))
    {
      accelerate = false;
      currentImg = img;
      engVect.vx = 0.0D;
      engVect.vy = 0.0D;
      c = 0.0D;
    }
  }
  
  private void turnLeft()
  {
    deg = (deg - 1.0D >= 0.0D ? deg - 5.0D : 360.0D);
  }
  
  private void turnRight()
  {
    deg = (deg + 1.0D <= 360.0D ? deg + 5.0D : 0.0D);
  }
  
  private void handleTurboTick()
  {
    if (turbo)
    {
      turboCountdown -= 2;
      if (turboCountdown <= 0)
      {
        turboOFF();
      }
      

    }
    else if (turboCountdown < 200) {
      turboCountdown += 1;
    }
  }
  

  public void tick()
  {
    handleShootingTick();
    handleExplodingTick();
    handleKeyInputTick();
    
    handleVelocityTick();
    handleExitSectorTick();
    vectors.clear();
  }
  
  private void calcVGrav()
  {
    vxGrav = 0.0D;
    vyGrav = 0.0D;
    for (int i = 0; i < vectors.size(); i++)
    {
      vxGrav += vectors.get(i)).vx;
      vyGrav += vectors.get(i)).vy;
    }
  }
  
  private void normVGrav()
  {
    double gravVectLeng = Math.sqrt(vxGrav * vxGrav + vyGrav * vyGrav);
    vxGrav /= gravVectLeng;
    vyGrav /= gravVectLeng;
  }
  
  private void normFinalVel()
  {
    double gravVectLeng = Math.sqrt(finalVelX * finalVelX + finalVelY * finalVelY);
    finalVelX = ((int)(finalVelX / gravVectLeng));
    finalVelY = ((int)(finalVelY / gravVectLeng));
  }
  
  private void handleVelocityTick()
  {
    calcVGrav();
    
    finalVelX += engVect.vx + vxGrav;
    finalVelY += engVect.vy + vyGrav;
    
    if (Double.isNaN(finalVelX))
      finalVelX = 0.1D;
    if (Double.isNaN(finalVelY)) {
      finalVelY = 0.1D;
    }
    x += (int)finalVelX;
    y += (int)finalVelY;
  }
  
  private void exitRight()
  {
    x = -33;
    whereExited = Direction.Right;
    exited = true;
  }
  
  private void exitLeft()
  {
    x = 949;
    whereExited = Direction.Left;
    exited = true;
  }
  
  private void exitDown()
  {
    y = -37;
    whereExited = Direction.Down;
    exited = true;
  }
  
  private void exitUp()
  {
    y = 719;
    whereExited = Direction.Up;
    exited = true;
  }
  
  private void handleExitSectorTick()
  {
    if (x > 950) {
      exitRight();
    } else if (x + 34 < 0)
      exitLeft();
    if (y > 720) {
      exitDown();
    } else if (y + 38 < 0) {
      exitUp();
    }
  }
  
  private void shoot() {
    if (shootingLockCountDown < 10) {
      return;
    }
    shootingLockCountDown = 0;
    double degR = Math.toRadians(deg);
    double pvx = finalVelX * (turbo ? 10 : 1);
    double pvy = finalVelY * (turbo ? 10 : 1);
    int vx = (int)(Math.sin(degR) * 10.0D + pvx);
    int vy = -(int)(Math.cos(degR) * 10.0D - pvy);
    handler.map.getCurrentScene().object.add(new Bullet(
      getHeadX(), 
      getHeadY(), ID.Bullet, vx, vy, deg, bulletImg, handler));
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
  

  public void render(Graphics g)
  {
    rotateAndDrawImg(currentImg, deg, g, 1);
    handleAccRender(g);
    handleExpRender(g);
    drawShield(g);
  }
  
  public void takeDamage()
  {
    turboCountdown -= 4;
    shieldColor -= 0.01F;
    if (turboCountdown <= 0)
    {
      turboCountdown = 0;
      shieldColor = 0.0F;
      setExploding();
    }
  }
  
  private void handleExplodingTick()
  {
    if (exploding)
    {
      countDown -= 1;
      if (countDown % 5 == 0)
      {
        expImgNum += 1;
      }
      if (countDown == 0)
        handler.removeObject(this);
      handler.gameOver();
    }
  }
  
  private void handleExpRender(Graphics g)
  {
    if (exploding) {
      g.drawImage(explosionImg, x, y, x + 34, y + 38, expImgNum % 7 * 130, expImgNum / 7 * 130, (expImgNum % 7 + 1) * 130, (expImgNum / 7 + 1) * 130, null);
    }
  }
  









  private void handleAccRender(Graphics g)
  {
    if ((accelerate) && (!exploding)) {
      currentImg = imgAcc;
    }
  }
  
  private void drawShield(Graphics g) {
    if (turboCountdown <= 0) return;
    g.setColor(new Color(0.0F, 1.0F, 0.0F, shieldColor));
    g.fillOval(x - 7, y - 6, 50, 50);
  }
  
  public double getDeg()
  {
    return deg;
  }
  
  public void calcC()
  {
    if (c < 0.2D) c += 0.02D;
    if ((c >= 0.2D) && (c < 0.4D)) c += 0.01D;
    if ((c >= 0.4D) && (c < 0.6D)) { c += 0.005D;
    }
  }
  
  private void calcVEng()
  {
    double degR = Math.toRadians(accDeg);
    engVect.vx = (Math.sin(degR) * c);
    engVect.vy = (-(Math.cos(degR) * c));
  }
  
  private void normVEng()
  {
    double vx2 = engVect.vx * engVect.vx;
    double vy2 = engVect.vy * engVect.vy;
    double vectLength = Math.sqrt(vx2 + vy2);
    engVect.vx /= vectLength;
    engVect.vy /= vectLength;
  }
  
  private void accelerate()
  {
    fuel -= 1;
    if (fuel <= 0) return;
    accelerate = true;
    accDeg = deg;
    calcC();
    calcVEng();
  }
  
  private void turboON()
  {
    turbo = true;
  }
  
  private void turboOFF()
  {
    turbo = false;
  }
  
  private void deccelerate()
  {
    accelerate = false;
    currentImg = img;
    if (c > 0.0D) c -= 0.25D;
    if (c < 0.0D) { c = 0.0D;
    }
  }
  
  public Rectangle getBounds()
  {
    return new Rectangle(x, y, 34, 38);
  }
  
  public boolean exited()
  {
    return exited;
  }
  
  public void setExited(boolean exited)
  {
    this.exited = exited;
  }
  
  public Direction whereExited()
  {
    return whereExited;
  }
  
  public double getC()
  {
    return c;
  }
  
  public boolean isTurboOn()
  {
    return turbo;
  }
  
  public void setExploding()
  {
    accelerate = false;
    exploding = true;
  }
  
  public BufferedImage getImg()
  {
    return img;
  }
  
  public int getTurboCountDown()
  {
    return turboCountdown;
  }
  
  public void receiveGravity(double gravity, double desiredDeg)
  {
    double degR = Math.toRadians(desiredDeg);
    vxGrav += Math.sin(degR) * gravity;
    vyGrav -= Math.cos(degR) * gravity;
  }
  
  public void receiveGravity(ForceVector vector)
  {
    vectors.add(vector);
  }
  
  public double getFinalVelX()
  {
    return finalVelX;
  }
  
  public double getFinalVelY()
  {
    return finalVelY;
  }
  
  public int getFuel()
  {
    return fuel;
  }
  
  public int getHull()
  {
    return hull;
  }
  
  public void addFuel(int fuel)
  {
    this.fuel += fuel;
    if (this.fuel >= 400) this.fuel = 400;
  }
}
