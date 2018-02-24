package canvastesting;

import canvastesting.control.Handler;
import canvastesting.control.KeyInput;
import canvastesting.control.MainMenu;
import canvastesting.control.Window;
import canvastesting.enums.ID;
import canvastesting.gameobjects.SpinningLine;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import javax.imageio.ImageIO;



public class CanvasTesting
  extends Canvas
  implements Runnable
{
  public static final double FPS = 60.0D;
  public static final double NSC = 1.0E9D;
  public static final long secInMillisecs = 1000L;
  public static final int numberOfBS = 3;
  public static final int WIDTH = 950;
  public static final int HEIGHT = 720;
  public static final int loadingTime = 720;
  public static final int loaderRadius = 60;
  public static final int zero = 0;
  public static final int one = 1;
  public static final int two = 2;
  public static final int sideMenuWidth = 300;
  private static int loading = 0;
  private static boolean gameLoaded = false;
  private static boolean inMainMenu = false;
  private static boolean running = false;
  private BufferedImage explosionImg;
  private BufferedImage playerImgAcc;
  public BufferedImage sidebarBg;
  private BufferedImage playerImg;
  private BufferedImage asteroid1;
  private BufferedImage asteroid2;
  private BufferedImage asteroid3;
  private BufferedImage meteorImg;
  private BufferedImage bulletImg;
  private BufferedImage enemyImg;
  private BufferedImage planet1;
  private BufferedImage planet2;
  private BufferedImage space2;
  private BufferedImage space3;
  private BufferedImage space4;
  private BufferedImage space5;
  private BufferedImage space;
  private BufferedImage frame;
  private BufferedImage img;
  private SpinningLine loader;
  private MainMenu mainMenu;
  private Handler handler;
  public static Window window = null;
  private Thread thread;
  private Random rand;
  
  public static void main(String[] args)
  {
    new CanvasTesting();
  }
  
  public CanvasTesting()
  {
    rand = new Random();
    loadImages();
    LinkedList<BufferedImage> planets = new LinkedList();
    LinkedList<BufferedImage> spaces = new LinkedList();
    LinkedList<BufferedImage> other = new LinkedList();
    addPlanetsToList(planets);
    addSpacesToList(spaces);
    addOtherToList(other);
    mainMenu = new MainMenu(img);
    
    handler = new Handler(spaces, playerImg, playerImgAcc, bulletImg, meteorImg, enemyImg, explosionImg, frame, planets, this);
    









    loader = new SpinningLine(halfIntToDouble(950) - 60.0D, halfIntToDouble(720) - 60.0D, 120.0D, ID.SpinningLine);
    


    addKeyListener(new KeyInput(handler));
    window = new Window(1250, 720, "Space Explorer", this);
  }
  
  private void loadImages()
  {
    try {
      sidebarBg = ImageIO.read(new File("img/sideMenuBg2.jpg"));
      playerImgAcc = ImageIO.read(new File("img/player_Acc.png"));
      explosionImg = ImageIO.read(new File("img/explosion.png"));
      asteroid1 = ImageIO.read(new File("img/asteroid1.png"));
      asteroid2 = ImageIO.read(new File("img/asteroid2.png"));
      asteroid3 = ImageIO.read(new File("img/asteroid3.png"));
      meteorImg = ImageIO.read(new File("img/meteor_n2.png"));
      playerImg = ImageIO.read(new File("img/player_n.png"));
      planet1 = ImageIO.read(new File("img/planet1.png"));
      planet2 = ImageIO.read(new File("img/planet2.png"));
      bulletImg = ImageIO.read(new File("img/bullet.png"));
      space2 = ImageIO.read(new File("img/space2.jpg"));
      space3 = ImageIO.read(new File("img/space3.jpg"));
      space4 = ImageIO.read(new File("img/space4.jpg"));
      space5 = ImageIO.read(new File("img/space5.jpg"));
      img = ImageIO.read(new File("img/mainBg.png"));
      enemyImg = ImageIO.read(new File("img/enemy.png"));
      space = ImageIO.read(new File("img/space.jpg"));
      frame = ImageIO.read(new File("img/frame1.png"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public synchronized void start() {
    thread = new Thread(this);
    running = true;
    thread.start();
  }
  
  public synchronized void stop()
  {
    try
    {
      thread.join();
      running = false;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  

  public void run()
  {
    requestFocus();
    long lastTime = System.nanoTime();
    double amountOfTicks = 60.0D;
    double ns = 1.0E9D / amountOfTicks;
    double delta = 0.0D;
    long timer = System.currentTimeMillis();
    int frames = 0;
    while (running)
    {
      long now = System.nanoTime();
      delta += (now - lastTime) / ns;
      lastTime = now;
      while (delta >= 1.0D)
      {
        tick();
        delta -= 1.0D;
      }
      if (running)
        render();
      frames++;
      if (System.currentTimeMillis() - timer > 1000L)
      {
        timer += 1000L;
        
        frames = 0;
      }
    }
    stop();
  }
  
  private void tick()
  {
    chooseTickStrategy();
  }
  
  private void chooseTickStrategy()
  {
    if (!gameLoaded)
    {
      gameLoaded = loader.tick(gameLoaded);
    }
    else if (inMainMenu)
    {
      mainMenu.tick();
    }
    else
    {
      handler.tick();
    }
  }
  
  private void render()
  {
    BufferStrategy bs = getBufferStrategy();
    if (bs == null)
    {
      createBufferStrategy(3);
      return;
    }
    Graphics g = bs.getDrawGraphics();
    chooseRenderStrategy(g);
    g.dispose();
    bs.show();
  }
  
  private void chooseRenderStrategy(Graphics g)
  {
    if (!gameLoaded)
    {
      loader.render(g);
    }
    else if (inMainMenu)
    {
      mainMenu.render(g);
    }
    else
    {
      handler.render(g);
    }
  }
  
  private void addPlanetsToList(LinkedList<BufferedImage> planets)
  {
    planets.add(asteroid3);
    planets.add(asteroid2);
    planets.add(asteroid1);
    planets.add(planet2);
    planets.add(planet1);
  }
  
  private void addSpacesToList(LinkedList<BufferedImage> spaces)
  {
    spaces.add(space);
    spaces.add(space2);
    spaces.add(space3);
    spaces.add(space4);
    spaces.add(space5);
  }
  
  private void addOtherToList(LinkedList<BufferedImage> other)
  {
    other.add(playerImgAcc);
    other.add(playerImg);
    other.add(bulletImg);
    other.add(meteorImg);
    other.add(enemyImg);
    other.add(explosionImg);
  }
  
  public double halfIntToDouble(int a)
  {
    return a / 2;
  }
}
