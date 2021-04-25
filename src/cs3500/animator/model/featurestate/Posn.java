package cs3500.animator.model.featurestate;


/**
 * This class represents a position in the first quadrant of the cartesian plane.
 */
public class Posn {

  private final double x;
  private final double y;

  /**
   * Constructs a Posn according to tis x and y coordinates.
   *
   * @param x x coordinate
   * @param y y coordinate
   * @throws IllegalArgumentException if coordinates are not in first quadrant of cartesian plane
   */
  public Posn(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Constructs a Posn that is a copy of the supplied Posn.
   *
   * @param p Posn to be copied
   */
  public Posn(Posn p) {
    this.x = p.getX();
    this.y = p.getY();
  }

  /**
   * This methods returns the x coordinate of this posn.
   *
   * @return double x coordinate
   */
  public double getX() {
    return this.x;
  }

  /**
   * This methods returns the y coordiante of this posn.
   *
   * @return double y coordinate
   */
  public double getY() {
    return this.y;
  }

}
