package canvastesting.datastructs;

import canvastesting.control.Handler;
import canvastesting.control.Scene;
import canvastesting.enums.Direction;
import canvastesting.gameobjects.GameObject;
import canvastesting.gameobjects.Player;
import java.awt.Graphics;
import java.util.LinkedList;

public class TableMap
{
  private static final int zero = 0;
  private static final int one = 1;
  private static final int two = 2;
  private Scene currentScene;
  private Coord currentCoord;
  public GameMap gameMap;
  private Player player;
  private int n;
  private int m;
  public Scene[][] A;
  public Handler handler;
  
  public TableMap(int n, int m, Scene scene, Handler handler)
  {
    currentCoord = new Coord((m - 1) / 2, (n - 1) / 2);
    gameMap = new GameMap(this);
    A = new Scene[m][n];
    player = scene.getPlayer();
    currentScene = scene;
    this.n = n;
    this.m = m;
    A[currentCoord.y][currentCoord.x] = scene;
    initTmp(A);
  }
  
  private void initTmp(Scene[][] tmp)
  {
    for (int i = 0; i < tmp.length; i++)
    {
      for (int j = 0; j < tmp[i].length; j++)
      {
        tmp[i][j] = null;
      }
    }
  }
  
  private void handleEmptyCell()
  {
    if (A[currentCoord.y][currentCoord.x] == null)
    {
      Scene tmpScene = getCurrentScene();
      A[currentCoord.y][currentCoord.x] = new Scene(tmpScene
        .getImgs(), handler, meteorImg, enemyImg, explosionImg, bulletImg, planets, player);
    }
  }
  







  private void handlePlayerTransfer()
  {
    currentScene.object.remove(player);
    currentScene = A[currentCoord.y][currentCoord.x];
    currentScene.object.add(player);
  }
  
  private void tryExpand(Direction dir)
  {
    expand(dir);
    move(dir, player);
  }
  
  public void move(Direction dir, GameObject player)
  {
    boolean condLeft = currentCoord.x > 0;
    boolean condRight = currentCoord.x < A[currentCoord.y].length - 1;
    boolean condUp = currentCoord.y > 0;
    boolean condDown = currentCoord.y < A.length - 1;
    boolean expandNeeded = false;
    
    if (dir == Direction.Left)
    {
      if (condLeft) {
        currentCoord.x -= 1;
      } else {
        expandNeeded = true;
      }
    } else if (dir == Direction.Right)
    {
      if (condRight) {
        currentCoord.x += 1;
      } else {
        expandNeeded = true;
      }
    } else if (dir == Direction.Up)
    {
      if (condUp) {
        currentCoord.y -= 1;
      } else {
        expandNeeded = true;
      }
    } else if (dir == Direction.Down)
    {
      if (condDown) {
        currentCoord.y += 1;
      } else
        expandNeeded = true;
    }
    if (expandNeeded)
    {
      tryExpand(dir);
      return;
    }
    handleEmptyCell();
    handlePlayerTransfer();
  }
  
  private void expand(Direction dir)
  {
    if ((dir == Direction.Left) || (dir == Direction.Right))
    {

      Scene[][] tmp = new Scene[m][2 * n];
      initTmp(tmp);
      copy(tmp);
      if (dir == Direction.Left)
      {
        shift(dir, n);
        currentCoord.x += n;
      }
      n *= 2;
    }
    else if ((dir == Direction.Up) || (dir == Direction.Down))
    {

      Scene[][] tmp = new Scene[2 * m][n];
      initTmp(tmp);
      copy(tmp);
      if (dir == Direction.Up)
      {
        shift(dir, m);
        currentCoord.y += m;
      }
      m *= 2;
    }
  }
  
  private void copy(Scene[][] tmp)
  {
    for (int i = 0; i < A.length; i++)
    {
      for (int j = 0; j < A[i].length; j++)
      {
        tmp[i][j] = A[i][j];
      }
    }
    A = tmp;
  }
  
  private void shift(Direction dir, int step)
  {
    if (dir == Direction.Left)
    {
      for (int i = 0; i < A.length; i++)
      {
        for (int j = 0; j < A[i].length - step; j++)
        {
          A[i][(j + step)] = A[i][j];
          A[i][j] = null;
        }
        
      }
    } else if (dir == Direction.Up)
    {
      for (int i = 0; i < A.length - step; i++)
      {
        for (int j = 0; j < A[i].length; j++)
        {
          A[(i + step)][j] = A[i][j];
          A[i][j] = null;
        }
      }
    }
  }
  
  public Scene getScene(int i, int j)
  {
    return A[j][i];
  }
  
  public Scene getScene(Coord coord)
  {
    return A[y][x];
  }
  

  public Coord getCurrentCoord()
  {
    return currentCoord;
  }
  
  public Scene getCurrentScene()
  {
    return currentScene;
  }
  
  public void tick()
  {
    currentScene.tick();
    gameMap.tick();
  }
  
  public void render(Graphics g)
  {
    currentScene.render(g);
  }
  

  public void addPlayerToGameMap(Player player)
  {
    currentScene.object.add(player);
  }
}
