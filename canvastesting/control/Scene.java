package canvastesting.control;

import canvastesting.datastructs.GameMap;
import canvastesting.datastructs.TableMap;
import canvastesting.enums.ID;
import canvastesting.gameobjects.Enemy;
import canvastesting.gameobjects.GameObject;
import canvastesting.gameobjects.Meteor;
import canvastesting.gameobjects.Planet;
import canvastesting.gameobjects.Player;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D.Float;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;













public class Scene
{
  private static final int two = 2;
  private static final int ten = 10;
  private static final int three = 3;
  private static final int speedStrX = 980;
  private static final int speedStrY = 300;
  private static final int speedFontStyle = 0;
  private static final int speedFontSize = 30;
  private static final int engStrX = 980;
  private static final int engStrY = 330;
  private static final int engFontStyle = 0;
  private static final int engFontSize = 15;
  private static final int engBarX = 980;
  private static final int engBarY = 335;
  private static final int engBarHeight = 50;
  private static final int engBarWidth = 200;
  private static final int fuelStrX = 980;
  private static final int fuelStrY = 410;
  private static final int fuelFontStyle = 0;
  private static final int fuelFontSize = 15;
  private static final int fuelBarX = 980;
  private static final int fuelBarY = 415;
  private static final int fuelBarHeight = 50;
  private static final int fuelBarWidth = 200;
  private static final int turboStrX = 980;
  private static final int turboStrY = 490;
  private static final int turboFontStyle = 0;
  private static final int turboFontSize = 15;
  private static final int turboBarX = 980;
  private static final int turboBarY = 495;
  private static final int turboBarHeight = 50;
  private static final int turboBarWidth = 200;
  private static final int tStrX = 410;
  private static final int tStrY = 120;
  private static final int hullStrX = 980;
  private static final int hullStrY = 575;
  private static final int hullFontStyle = 0;
  private static final int hullFontSize = 15;
  private static final int hullBarX = 980;
  private static final int hullBarY = 580;
  private static final int hullBarHeight = 50;
  private static final int hullBarWidth = 200;
  private static final int hArrOvalX = 1000;
  private static final int hArrOvalY = 200;
  private static final int hArrOvalR = 25;
  private static final int hArrOvalSize = 50;
  private static final int hArrLineX = 1025;
  private static final int hArrLineY = 225;
  private static final int hForceOvalX = 1100;
  private static final int hForceOvalY = 200;
  private static final int hForceOvalR = 25;
  private static final int hForceOvalSize = 50;
  private static final int hForceLineX = 1125;
  private static final int hForceLineY = 225;
  private static final Color hArrColor = Color.YELLOW;
  private static final Color ForceArrColor = Color.white;
  private static final Color tubroBorderColor = Color.red;
  private static final Color tubroBarColor = Color.green;
  private static final Color fuelBorderColor = Color.red;
  private static final Color fuelBarColor = Color.green;
  private static final Color hullBorderColor = Color.red;
  private static final Color hullBarColor = Color.green;
  private static final Color engBorderColor = Color.red;
  private static final Color engBarColor = Color.ORANGE;
  
  private static final double maxC = 30.0D;
  
  private static final int maxTurbo = 200;
  
  private static final int zero = 0;
  
  private static final int one = 1;
  
  private static final int maxNumberOfMeteors = 10;
  
  private static final int numOfEmemyImg = 4;
  
  private static final int numOfMeteorImg = 3;
  
  private static final int numOfBulletImg = 2;
  private static final int numOfExpImg = 5;
  private static final int maxVectorLength = 5;
  private static final int planetMergin = 100;
  public LinkedList<BufferedImage> planets;
  public LinkedList<BufferedImage> spaces;
  public LinkedList<GameObject> object;
  public BufferedImage explosionImg;
  public BufferedImage meteorImg;
  public BufferedImage bulletImg;
  public BufferedImage enemyImg;
  public BufferedImage planet1;
  public BufferedImage img;
  public Handler handler;
  public Player player;
  public Random rand;
  public Planet landedPlanet;
  public int pTurbo;
  public int pFuel;
  public int pHull;
  public double pEng;
  
  public Scene(LinkedList<BufferedImage> spaces, Handler handler, BufferedImage meteorImg, BufferedImage enemyImg, BufferedImage explosionImg, BufferedImage bulletImg, LinkedList<BufferedImage> planets, Player player)
  {
    object = new LinkedList();
    rand = new Random();
    img = ((BufferedImage)spaces.get(rand.nextInt(spaces.size())));
    this.handler = handler;
    this.explosionImg = explosionImg;
    this.meteorImg = meteorImg;
    this.bulletImg = bulletImg;
    this.enemyImg = enemyImg;
    this.planets = planets;
    this.player = player;
    this.spaces = spaces;
    landedPlanet = null;
    addMeteors();
    addPlanets();
    addEnemy();
  }
  




  public Scene(Handler handler, LinkedList<BufferedImage> spaces, LinkedList<BufferedImage> other, LinkedList<BufferedImage> planets, Player player)
  {
    object = new LinkedList();
    rand = new Random();
    img = ((BufferedImage)spaces.get(rand.nextInt(spaces.size())));
    this.handler = handler;
    this.planets = planets;
    this.player = player;
    this.spaces = spaces;
    explosionImg = ((BufferedImage)other.get(5));
    enemyImg = ((BufferedImage)other.get(4));
    meteorImg = ((BufferedImage)other.get(3));
    bulletImg = ((BufferedImage)other.get(2));
    addMeteors();
    addPlanets();
    addEnemy();
  }
  
  private void addEnemy()
  {
    int rx_t = rand.nextInt(950);
    int ry_t = rand.nextInt(720);
    Enemy e = new Enemy(rx_t, ry_t, ID.Enemy, enemyImg, explosionImg, bulletImg, handler, player);
    






    object.add(e);
  }
  
  private void addPlanets()
  {
    for (int i = 0; i < 2; i++)
    {
      for (int j = 0; j < 2; j++)
      {
        if (rand.nextInt() % 2 == 0)
        {
          int halfWidth = 475;
          int halfHeight = 360;
          int rw_t = rand.nextInt(halfWidth - 300);
          int rh_t = rand.nextInt(halfHeight - 300);
          int locX = rw_t + 200 + i * halfWidth;
          int locY = rh_t + 100 + j * halfHeight;
          

          object.add(new Planet(locX, locY, ID.Planet, planets, player, handler, this));
        }
      }
    }
  }
  
  private void addMeteors()
  {
    for (int i = 0; i < rand.nextInt(10); i++)
    {
      int rx_t = rand.nextInt(950);
      int ry_t = rand.nextInt(720);
      Meteor m = new Meteor(rx_t, ry_t, ID.Meteor, meteorImg, explosionImg, handler);
      




      object.add(m);
    }
  }
  
  public LinkedList<BufferedImage> getImgs()
  {
    return spaces;
  }
  

  public void tick()
  {
    pTurbo = player.getTurboCountDown();
    pFuel = (player.getFuel() / 2);
    pHull = player.getHull();
    pEng = player.getC();
  }
  
  private void drawBgImg(Graphics g)
  {
    g.drawImage(img, 15, 15, 950, 720, null);
  }
  
  private void renderEachObj(Graphics g)
  {
    for (int i = 0; i < object.size(); i++)
    {
      GameObject tempObject = (GameObject)object.get(i);
      tempObject.render(g);
    }
  }
  
  public void render(Graphics g)
  {
    drawBgImg(g);
    renderEachObj(g);
    
    handler.map.gameMap.render(g);
    
    drawSpeedDisplay(g);
    handleEngineDisplayRender(g);
    handleShieldDisplayRender(g);
    handleFuelDisplayRender(g);
    handleHullDisplayRender(g);
    drawArrows(g);
  }
  
  private int calcSpeed()
  {
    return (int)Math.sqrt(player
      .getFinalVelX() * player.getFinalVelX() + player
      .getFinalVelY() * player.getFinalVelY());
  }
  
  private void drawSpeedDisplay(Graphics g)
  {
    g.setFont(new Font("arial", 0, 30));
    if ((player.getC() >= 30.0D) && (player.getTurboCountDown() == 200)) {
      g.setColor(Color.red);
    } else
      g.setColor(Color.ORANGE);
    int sp = calcSpeed();
    g.drawString("SPEED: " + sp, 980, 300);
  }
  
  private void handleShieldDisplayRender(Graphics g)
  {
    drawShieldTxt(g);
    drawShieldBar(g);
  }
  









  private void drawShieldTxt(Graphics g)
  {
    g.setColor(Color.ORANGE);
    g.setFont(new Font("arial", 0, 15));
    g.drawString("SHIELD", 980, 490);
  }
  
  private void drawShieldBar(Graphics g)
  {
    g.setColor(Color.GRAY);
    g.fillRect(980, 495, 200, 50);
    g.setColor(tubroBarColor);
    g.fillRect(980, 495, pTurbo, 50);
    g.setColor(tubroBorderColor);
    g.drawRect(980, 495, 200, 50);
  }
  
  private void handleFuelDisplayRender(Graphics g)
  {
    drawFuelTxt(g);
    drawFuelBar(g);
  }
  
  private void drawFuelTxt(Graphics g)
  {
    g.setColor(Color.ORANGE);
    g.setFont(new Font("arial", 0, 15));
    g.drawString("FUEL", 980, 410);
  }
  
  private void drawFuelBar(Graphics g)
  {
    g.setColor(Color.GRAY);
    g.fillRect(980, 415, 200, 50);
    g.setColor(fuelBarColor);
    g.fillRect(980, 415, pFuel, 50);
    g.setColor(fuelBorderColor);
    g.drawRect(980, 415, 200, 50);
  }
  
  private void handleHullDisplayRender(Graphics g)
  {
    drawHullTxt(g);
    drawHullBar(g);
  }
  
  private void drawHullTxt(Graphics g)
  {
    g.setColor(Color.ORANGE);
    g.setFont(new Font("arial", 0, 15));
    g.drawString("HULL", 980, 575);
  }
  
  private void drawHullBar(Graphics g)
  {
    g.setColor(Color.GRAY);
    g.fillRect(980, 580, 200, 50);
    g.setColor(hullBarColor);
    g.fillRect(980, 580, pHull, 50);
    g.setColor(hullBorderColor);
    g.drawRect(980, 580, 200, 50);
  }
  
  private void handleEngineDisplayRender(Graphics g)
  {
    drawEngineTxt(g);
    drawEngineBar(g);
  }
  
  private void drawEngineTxt(Graphics g)
  {
    g.setColor(Color.ORANGE);
    g.setFont(new Font("arial", 0, 15));
    g.drawString("ENGINE", 980, 330);
  }
  
  private void drawEngineBar(Graphics g)
  {
    int pEng_t = pEng == 0.0D ? 0 : (int)(200.0D * (pEng / 0.6D));
    g.setColor(Color.GRAY);
    g.fillRect(980, 335, 200, 50);
    g.setColor(engBarColor);
    g.fillRect(980, 335, pEng_t, 50);
    g.setColor(engBorderColor);
    g.drawRect(980, 335, 200, 50);
  }
  

  private void drawArrows(Graphics g)
  {
    drawHeadingArrow(g);
    drawForceArrow(g);
  }
  
  private void drawHeadingArrow(Graphics g)
  {
    Graphics2D g2 = (Graphics2D)g;
    g2.setStroke(new BasicStroke(4.0F));
    g2.setColor(Color.GRAY);
    g2.fillOval(1000, 200, 50, 50);
    g2.setColor(hArrColor);
    g2.drawOval(1000, 200, 50, 50);
    double deg = player.getDeg();
    double degR = Math.toRadians(deg);
    int dx = (int)(Math.sin(degR) * 25.0D);
    int dy = -(int)(Math.cos(degR) * 25.0D);
    g2.draw(new Line2D.Float(1025.0F, 225.0F, 1025 + dx, 225 + dy));
  }
  



  private void drawForceArrow(Graphics g)
  {
    g.setColor(Color.GRAY);
    g.fillOval(1100, 200, 50, 50);
    g.setColor(ForceArrColor);
    g.drawOval(1100, 200, 50, 50);
    double dx = player.getFinalVelX();
    double dy = player.getFinalVelY();
    
    double dis = Math.sqrt(dx * dx + dy * dy);
    if (dis != 0.0D)
    {
      dx = dx / dis * 25.0D;
      dy = dy / dis * 25.0D;
    }
    g.drawLine(1125, 225, 1125 + (int)dx, 225 + (int)dy);
  }
  



  public boolean containsPlayer()
  {
    if (object == null)
      return false;
    for (int i = 0; i < object.size(); i++)
    {
      if (((GameObject)object.get(i)).getId() == ID.Player)
        return true;
    }
    return false;
  }
  
  public Player getPlayer()
  {
    return player;
  }
  
  public Planet getLandedPlanet()
  {
    return landedPlanet;
  }
  
  public void setLandedPlanet(Planet p)
  {
    landedPlanet = p;
  }
}
