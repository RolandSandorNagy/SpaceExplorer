package canvastesting.datastructs;

import java.util.Random;

public class Treasure
{
  private Random r;
  private int fuel;
  
  public Treasure()
  {
    r = new Random();
    fuel = (r.nextInt(4) + 1);
    fuel *= 50;
  }
  
  public int getTreasure()
  {
    return fuel;
  }
}
