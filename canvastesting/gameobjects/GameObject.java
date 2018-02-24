package canvastesting.gameobjects;

import canvastesting.enums.ID;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public abstract class GameObject extends JPanel
{
  private static final int two = 2;
  protected int x;
  protected int y;
  protected ID id;
  protected double velX;
  protected double velY;
  
  public GameObject(int x, int y, ID id)
  {
    this.x = x;
    this.y = y;
    this.id = id;
  }
  
  public void setX(int x) {
    this.x = x;
  }
  
  public void setY(int y) {
    this.y = y;
  }
  
  public void setId(ID id) {
    this.id = id;
  }
  
  public void setVelX(int velX) {
    this.velX = velX;
  }
  
  public void setVelY(int velY) {
    this.velY = velY;
  }
  
  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public ID getId() {
    return id;
  }
  
  public double getVelX() {
    return velX;
  }
  
  public double getVelY() {
    return velY;
  }
  

  public void rotateAndDrawImg(BufferedImage img, double deg, Graphics g, int q)
  {
    double rotationRequired = Math.toRadians(deg);
    double locationX = img.getWidth() / 2;
    double locationY = img.getHeight() / 2;
    AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
    AffineTransformOp op = new AffineTransformOp(tx, 2);
    
    Graphics2D g2d = (Graphics2D)g.create();
    
    g2d.drawImage(op.filter(img, null), x, y, img.getWidth() / q, img.getHeight() / q, null);
    g2d.dispose();
  }
  
  public abstract void tick();
  
  public abstract void render(Graphics paramGraphics);
  
  public abstract Rectangle getBounds();
}
