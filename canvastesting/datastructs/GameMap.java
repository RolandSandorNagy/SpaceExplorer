package canvastesting.datastructs;

import canvastesting.control.Handler;
import canvastesting.control.Scene;
import java.awt.Color;
import java.awt.Graphics;

public class GameMap
{
  private static final int zero = 0;
  private static final int one = 1;
  private static final int two = 2;
  private int width = 150;
  private int height = 130;
  private int x = 1000;
  private int y = 50;
  private int blockHeight;
  private int blockWidth;
  private int gapHeight;
  private int blockSize;
  private int gapWidth;
  private int gapSize;
  private Color visitedCellColor;
  private Color playerCellColor;
  private Color mapColor;
  private TableMap map;
  
  public GameMap(TableMap map)
  {
    visitedCellColor = new Color(1.0F, 1.0F, 1.0F, 0.5F);
    mapColor = new Color(1.0F, 0.0F, 0.0F, 0.5F);
    playerCellColor = Color.YELLOW;
    this.map = map;
  }
  
  public void tick()
  {
    int yHeight = map.A.length + 2;
    int xWidth = map.A[0].length + 2;
    
    int afterReserve = width - (xWidth + 1);
    blockWidth = (afterReserve / xWidth);
    int remain = afterReserve % xWidth;
    gapWidth = (1 + remain / (xWidth + 1));
    
    afterReserve = height - (yHeight + 1);
    blockHeight = (afterReserve / yHeight);
    remain = afterReserve % yHeight;
    gapHeight = (1 + remain / (yHeight + 1));
    
    if ((blockWidth < 1) || (gapWidth < 1))
      width *= 2;
    if ((blockHeight < 1) || (gapHeight < 1)) {
      height *= 2;
    }
    blockSize = Math.min(blockWidth, blockHeight);
    gapSize = Math.min(gapWidth, gapHeight);
  }
  


  public void render(Graphics g)
  {
    g.drawImage(ctsidebarBg, 950, 0, 300, 720, null);
    


    g.setColor(new Color(1.0F, 0.4F, 0.4F));
    g.fillRect(x, y, width, height);
    for (int i = 0; i < map.A.length; i++)
    {
      for (int j = 0; j < map.A[i].length; j++)
      {
        Scene tmpScene = map.A[i][j];
        if (tmpScene != null)
        {
          if (tmpScene.containsPlayer()) {
            g.setColor(playerCellColor);
          } else
            g.setColor(visitedCellColor);
          int x_t = x + gapSize + (j + 1) * (gapSize + blockSize);
          int y_t = y + gapSize + (i + 1) * (gapSize + blockSize);
          g.fillRect(x_t, y_t, blockSize, blockSize);
        }
      }
    }
  }
}
