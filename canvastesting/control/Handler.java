package canvastesting.control;

import canvastesting.CanvasTesting;
import canvastesting.datastructs.TableMap;
import canvastesting.enums.ID;
import canvastesting.gameobjects.Bullet;
import canvastesting.gameobjects.Enemy;
import canvastesting.gameobjects.GameObject;
import canvastesting.gameobjects.Meteor;
import canvastesting.gameobjects.Planet;
import canvastesting.gameobjects.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

public class Handler
{
  public static CanvasTesting ct;
  private static final int STARTINGWIDTHOFMAP = 5;
  private static final int STARTINGHEIGHTOFMAP = 5;
  private static final int startX = 400;
  private static final int startY = 400;
  private static final int zero = 0;
  public LinkedList<Integer> pressedKeys;
  public TableMap map;
  private Random r;
  public boolean onPlanet = false;
  public boolean gameOver = false;
  




  private BufferedImage frame;
  




  public Handler(LinkedList<BufferedImage> spaces, BufferedImage playerImg, BufferedImage playerImgAcc, BufferedImage bulletImg, BufferedImage meteorImg, BufferedImage enemyImg, BufferedImage explosionImg, BufferedImage frame, LinkedList<BufferedImage> planets, CanvasTesting ct)
  {
    ct = ct;
    this.frame = frame;
    pressedKeys = new LinkedList();
    r = new Random();
    Player player = new Player(400, 400, ID.Player, playerImg, playerImgAcc, bulletImg, explosionImg, this);
    






    map = new TableMap(5, 5, new Scene(spaces, this, meteorImg, enemyImg, explosionImg, bulletImg, planets, player), this);
    








    map.getCurrentScene().object.add(player);
  }
  



  public Handler(LinkedList<BufferedImage> spaces, LinkedList<BufferedImage> other, LinkedList<BufferedImage> planets)
  {
    r = new Random();
    Player player = new Player(400, 400, ID.Player, other, this);
    



    map = new TableMap(5, 5, new Scene(this, spaces, other, planets, player), this);
    





    map.getCurrentScene().object.add(player);
  }
  
  public void tick()
  {
    if (gameOver)
    {
      gameOverTick();
      return;
    }
    if (onPlanet)
    {
      onPlanetTick();
      return;
    }
    
    map.tick();
    for (int i = 0; i < map.getCurrentScene().object.size(); i++)
    {
      GameObject tmp = (GameObject)map.getCurrentScene().object.get(i);
      if (tmp.getId() == ID.Player)
      {
        map.getCurrentScene().object.remove(tmp);
        map.getCurrentScene().object.add(tmp);
        if (((Player)tmp).exited())
        {
          ((Player)tmp).setExited(false);
          map.move(((Player)tmp).whereExited(), tmp);
        }
      }
    }
    for (int i = 0; i < map.getCurrentScene().object.size(); i++)
    {
      GameObject tempObject = (GameObject)map.getCurrentScene().object.get(i);
      tempObject.tick();
    }
    collision();
  }
  
  private void onPlanetTick()
  {
    map.getCurrentScene().getLandedPlanet().tick();
  }
  


  private void gameOverTick() {}
  

  private void collision()
  {
    for (int i = 0; i < map.getCurrentScene().object.size(); i++)
    {
      GameObject tmpObj = (GameObject)map.getCurrentScene().object.get(i);
      if (tmpObj.getId() == ID.Player)
      {
        for (int j = 0; j < map.getCurrentScene().object.size(); j++)
        {
          GameObject tmpObj2 = (GameObject)map.getCurrentScene().object.get(j);
          if ((tmpObj2.getId() != ID.Meteor) || 
            (((Meteor)tmpObj2).getExploding()) || 
            (!tmpObj2.getBounds().intersects(tmpObj.getBounds())))
          {


            if ((tmpObj2.getId() == ID.EnemyBullet) && 
              (tmpObj2.getBounds().intersects(tmpObj.getBounds())) && (!counted))
            {


              ((Player)tmpObj).takeDamage();
              counted = true;
              removeObject(tmpObj2);
            }
          }
        }
      } else if (tmpObj.getId() == ID.Bullet)
      {
        for (int j = 0; j < map.getCurrentScene().object.size(); j++)
        {
          GameObject tmpObj2 = (GameObject)map.getCurrentScene().object.get(j);
          if (((tmpObj2.getId() == ID.Meteor) || 
            (tmpObj2.getId() == ID.Enemy)) && 
            (tmpObj2.getBounds().intersects(tmpObj.getBounds()))) if ((!(tmpObj2 instanceof Meteor)) || 
            
              (((Meteor)tmpObj2).getExploding())) { if ((tmpObj2 instanceof Enemy))
              {
                if (((Enemy)tmpObj2).getExploding()) {}
              }
              
            }
            else
            {
              removeObject(tmpObj);
              if ((tmpObj2 instanceof Meteor)) {
                ((Meteor)tmpObj2).setExploding(true);
              } else {
                ((Enemy)tmpObj2).setExploding(true);
              }
            }
        }
      }
    }
  }
  
  public void render(Graphics g) {
    if (gameOver)
    {
      g.setColor(Color.black);
      g.fillRect(0, 0, 950, 720);
      g.setFont(new java.awt.Font("arial", 0, 75));
      g.setColor(Color.white);
      g.drawString("Game Over", 275, 410);
      g.drawImage(frame, -20, -40, 990, 770, null);
      g.drawImage(frame, 880, -40, 1290, 770, null);
      g.drawImage(frame, 1190, -40, 950, 770, null);
      return;
    }
    map.render(g);
    g.drawImage(frame, -20, -40, 990, 770, null);
    g.drawImage(frame, 880, -40, 1290, 770, null);
    g.drawImage(frame, 1190, -40, 950, 770, null);
  }
  
  public void addObject(GameObject object)
  {
    map.getCurrentScene().object.add(object);
  }
  
  public void removeObject(GameObject object)
  {
    map.getCurrentScene().object.remove(object);
  }
  
  public void gameOver()
  {
    gameOver = true;
  }
}
