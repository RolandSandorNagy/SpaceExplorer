package canvastesting.datastructs;

import java.awt.image.BufferedImage;

public class SpriteSheet
{
  private static final int zero = 0;
  private static final int one = 1;
  private static final int two = 2;
  private int sheetHeight;
  private int sheetWidth;
  private int height;
  private int imgNum;
  private int width;
  private int rows;
  private int cols;
  private BufferedImage img;
  
  public SpriteSheet(BufferedImage img, int rows, int cols)
  {
    this.img = img;
    this.rows = rows;
    this.cols = cols;
    init();
  }
  
  private void init()
  {
    sheetWidth = img.getWidth();
    width = (sheetWidth / cols);
    sheetHeight = img.getWidth();
    width = (sheetWidth / cols);
    imgNum = 0;
  }
  
  public Coord getNextCoord()
  {
    int x = width * imgNum % sheetWidth;
    int y = width * imgNum / sheetWidth;
    imgNum = (imgNum + 1 >= rows * cols - 1 ? 0 : imgNum + 1);
    return new Coord(x, y);
  }
  
  public BufferedImage getSheet()
  {
    return img;
  }
  
  public int getWidth()
  {
    return width;
  }
  
  public int getHeight()
  {
    return height;
  }
}
