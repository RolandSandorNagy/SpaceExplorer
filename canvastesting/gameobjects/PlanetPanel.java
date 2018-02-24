package canvastesting.gameobjects;

import canvastesting.CanvasTesting;
import canvastesting.control.Handler;
import canvastesting.control.MouseInput;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class PlanetPanel extends Canvas
{
  private Handler handler;
  private Planet planet;
  private boolean visible = false;
  private boolean visible_K1 = false;
  private boolean openning = false;
  public boolean visited = false;
  private boolean added = false;
  public int pWAkt = 0;
  public int pHAkt = 0;
  public int pW;
  public int pH;
  public int pX;
  public int pY;
  public int originX;
  public int originY;
  public int finishX;
  public int finishY;
  
  PlanetPanel(int pX, int pY, int pW, int pH, Handler handler, Planet planet)
  {
    this.handler = handler;
    this.planet = planet;
    this.pX = pX;
    this.pY = pY;
    originX = pX;
    originY = pY;
    this.pW = pW;
    this.pH = pH;
  }
  
  public void tick()
  {
    if ((!visible_K1) && (visible == true))
    {
      visible_K1 = true;
      openning = true;
      int quarter = planet.getQuarter();
      int x_t = 0;int y_t = 0;
      if (quarter == 1)
      {
        x_t = pX + (pW - 15);
        y_t = pY;
      }
      else if (quarter == 2)
      {
        x_t = pX - pW;
        y_t = pY;
      }
      else if (quarter == 3)
      {
        x_t = pX - 15;
        y_t = pY - pH;
      }
      else if (quarter == 4)
      {
        x_t = pX + (pW - 15);
        y_t = pY - pH;
      }
      finishX = x_t;
      finishY = y_t;
      Handler.ct.addMouseListener(new MouseInput(handler, this, x_t, y_t, 15, 15));
      if (CanvasTesting.window != null)
        windowframe.add(this);
    }
    if (openning)
    {
      if (pWAkt < pW)
        pWAkt += 10;
      if (pHAkt < pH)
        pHAkt += 10;
      if ((pWAkt >= pW) && (pHAkt >= pH))
      {
        openning = false;
        int quarter = planet.getQuarter();
        if (quarter == 2) {
          pX -= pWAkt;
        } else if (quarter == 3) {
          pX -= pWAkt;pY -= pHAkt;
        } else if (quarter == 4) {
          pY -= pHAkt;
        }
      }
    }
  }
  
  public void shut() {
    visible_K1 = false;
    visible = false;
    openning = false;
    visited = true;
    pX = originX;
    pY = originY;
    pWAkt = 0;
    pHAkt = 0;
    planet.setOnPlanet(false);
  }
  
  public void render(Graphics g)
  {
    int quarter = planet.getQuarter();
    g.setColor(new Color(1.0F, 1.0F, 1.0F, 0.1F));
    if (!openning) {
      g.fillRect(pX, pY, pWAkt, pHAkt);

    }
    else if (quarter == 1) {
      g.fillRect(pX, pY, pWAkt, pHAkt);
    } else if (quarter == 2) {
      g.fillRect(pX - pWAkt, pY, pWAkt, pHAkt);
    } else if (quarter == 3) {
      g.fillRect(pX - pWAkt, pY - pHAkt, pWAkt, pHAkt);
    } else if (quarter == 4) {
      g.fillRect(pX, pY - pHAkt, pWAkt, pHAkt);
    }
    
    if ((pW == pWAkt) && (pH == pHAkt))
    {
      g.setColor(new Color(1.0F, 0.0F, 0.0F, 0.3F));
      g.fillRect(finishX, finishY, 15, 15);
      g.setColor(Color.white);
      g.setFont(new Font("arial", 1, 15));
      g.drawString("X", finishX, finishY + 15);
      if (!openning)
      {
        if (!visited)
        {
          g.drawString("You've found " + planet.getTreasure() + " fuel.", pX + 50, pY + pH / 2);
          if (!added)
          {
            planet.getPlayer().addFuel(planet.getTreasure());
            added = true;
          }
        }
        else {
          g.drawString("This place is empty.", pX + 50, pY + pH / 2);
        }
      }
    }
  }
  
  void setVis(boolean b) {
    visible = b;
  }
}
