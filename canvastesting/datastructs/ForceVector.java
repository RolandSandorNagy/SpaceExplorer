package canvastesting.datastructs;

public class ForceVector
{
  public static final double division = 20.0D;
  public static final int zero = 0;
  public double gravity;
  public double deg;
  public double vx;
  public double vy;
  
  public ForceVector(double vx, double vy, double gravity)
  {
    this.gravity = gravity;
    this.vx = vx;
    this.vy = vy;
    deg = 0.0D;
  }
  
  public ForceVector(double gravity, double deg)
  {
    this.deg = deg;
    this.gravity = (gravity / 20.0D);
    double degR = Math.toRadians(deg);
    vx = (Math.sin(degR) * this.gravity);
    vy = (-(Math.cos(degR) * this.gravity));
  }
}
