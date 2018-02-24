package canvastesting.gameobjects;

import canvastesting.control.Handler;
import canvastesting.control.Scene;
import canvastesting.datastructs.ForceVector;
import canvastesting.datastructs.SpriteSheet;
import canvastesting.datastructs.Treasure;
import canvastesting.enums.ID;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Random;



public class Planet
  extends GameObject
{
  private static final int zero = 0;
  private static final int one = 1;
  private static final int two = 2;
  private static final int three = 3;
  private static final int numOfColsOnSheet = 5;
  private static final int numOfRowsOnSheet = 4;
  private static final int landingCounterMax = 500;
  private static final int maxCounter = 10;
  private static final int numOfImgs = 19;
  private static final int twoPiDeg = 360;
  private static final int gravityStep = 50;
  private static final int pW = 300;
  private static final int pH = 100;
  private static final double rightAngle = 90.0D;
  private Treasure treasure;
  private LinkedList<BufferedImage> planets;
  private SpriteSheet sprite;
  private BufferedImage img;
  private Player player;
  private Random r;
  private double gravity;
  private boolean onPlanet = false;
  private boolean tryLanding = false;
  private int landingCounter = 0;
  private int imgNum = 0;
  private int counter = 10;
  private int height;
  private int width;
  private int pX;
  private int pY;
  private Handler handler;
  private Scene scene;
  private PlanetPanel panel;
  
  public Planet(int x, int y, ID id, LinkedList<BufferedImage> planets, Player player, Handler handler, Scene scene) {
    super(x, y, id);
    treasure = new Treasure();
    this.player = player;
    this.handler = handler;
    this.scene = scene;
    r = new Random();
    this.planets = planets;
    gravity = r.nextInt(planets.size());
    img = ((BufferedImage)planets.get((int)gravity));
    gravity = (3.0D + gravity / 4.0D);
    sprite = new SpriteSheet(img, 4, 5);
    
    width = (img.getWidth() / 5);
    height = (img.getHeight() / 4);
    pX = (x + width);
    pY = (y + height);
    panel = new PlanetPanel(pX, pY, 300, 100, handler, this);
  }
  
  private void handleCounter()
  {
    if (counter == 0)
    {
      imgNum += 1;
      imgNum %= 19;
      counter = 10;
    }
    else {
      counter -= 1;
    }
  }
  
  public void onPlanetTick() {
    panel.tick();
  }
  

  public void tick()
  {
    if (onPlanet)
    {
      onPlanetTick();
      handleCounter();
      return;
    }
    
    handleCounter();
    
    player.getClass();double a = player.getX() + 34 / 2 - (x + width / 1);
    if (a < 0.0D)
      a *= -1.0D;
    player.getClass();double b = player.getY() + 38 / 2 - (y + height / 1);
    if (b < 0.0D) {
      b *= -1.0D;
    }
    if ((a == 0.0D) || (b == 0.0D)) {
      return;
    }
    double distance = Math.sqrt(a * a + b * b);
    


    player.getClass(); double plusDeg; double toDeg; double plusDeg; if (x + width / 1 <= player.getX() + 34 / 2)
    {
      player.getClass(); double plusDeg; if (y + height / 1 <= player.getY() + 38 / 2)
      {
        double toDeg = Math.atan(b / a);
        plusDeg = 90.0D;
      }
      else
      {
        double toDeg = Math.atan(a / b);
        plusDeg = 0.0D;
      }
    }
    else
    {
      player.getClass(); double plusDeg; if (y + height / 1 <= player.getY() + 38 / 2)
      {
        double toDeg = Math.atan(a / b);
        plusDeg = 180.0D;
      }
      else
      {
        toDeg = Math.atan(b / a);
        plusDeg = 270.0D;
      }
    }
    double desiredDeg = Math.toDegrees(toDeg);
    desiredDeg += plusDeg;
    desiredDeg += 180.0D;
    desiredDeg %= 360.0D;
    if (distance < 50.0D * gravity)
    {
      sendGravity(distance, desiredDeg);
      tryLanding(distance);
    }
  }
  
  private void sendGravity(double distance, double desiredDeg)
  {
    double power = distance / 50.0D;
    player.receiveGravity(new ForceVector(power, desiredDeg));
  }
  
  private void tryLanding(double distance)
  {
    if (landingCounter == 0) {
      tryLanding = false;
    } else
      tryLanding = true;
    if (distance < width) {
      landingCounter += 1;
    } else
      landingCounter = 0;
    if (landingCounter >= 500) {
      landed();
    }
  }
  
  private void landed() {
    landingCounter = 0;
    handler.onPlanet = (this.onPlanet = 1);
    panel.setVis(true);
    scene.setLandedPlanet(this);
    System.out.println("Landed!");
  }
  
  private void onPlanetRender(Graphics g)
  {
    panel.render(g);
  }
  

  public void render(Graphics g)
  {
    if (onPlanet) {
      onPlanetRender(g);
    } else if (tryLanding)
    {
      g.setColor(Color.white);
      g.setFont(new Font("arial", 1, 10));
      g.drawString("Searching", x, y - 12);
      g.setColor(Color.green);
      g.fillRect(x, y - 12, (int)(width * 2 * (landingCounter / 500.0D)), 10);
      

      g.setColor(Color.red);
      g.drawRect(x, y - 12, width * 2, 10);
    }
    
    g.drawImage(img, x, y, x + 2 * width, y + 2 * height, imgNum % 5 * width, imgNum / 5 * height, (imgNum % 5 + 1) * width, (imgNum / 5 + 1) * height, null);
    








    drawGravityFields(g);
  }
  
  private void drawGravityFields(Graphics g)
  {
    for (int i = 0; i <= gravity + 2.0D; i++)
    {
      float n = 1.0F - i / (float)(gravity + 2.0D);
      if (panel.visited) g.setColor(new Color(1.0F, 0.3F, 0.4F, n)); else
        g.setColor(new Color(1.0F, 1.0F, 1.0F, n));
      int x_t = x - i * 50 / 2;
      int y_t = y - i * 50 / 2;
      int size = width * 2 + i * 50;
      g.drawOval(x_t, y_t, size, size);
    }
  }
  

  public Rectangle getBounds()
  {
    return new Rectangle(x, y, width, height);
  }
  
  public void setOnPlanet(boolean b)
  {
    handler.onPlanet = b;
    onPlanet = b;
  }
  
  public int getTreasure()
  {
    return treasure.getTreasure();
  }
  
  public Player getPlayer()
  {
    return player;
  }
  
  int getQuarter()
  {
    int quarter = 0;
    if ((x < 475) && (y < 360)) {
      quarter = 1;
    } else if ((x >= 475) && (y < 360)) {
      quarter = 2;
    } else if ((x >= 475) && (y >= 360)) {
      quarter = 3;
    } else if ((x < 475) && (y >= 360)) {
      quarter = 4;
    }
    return quarter;
  }
}
