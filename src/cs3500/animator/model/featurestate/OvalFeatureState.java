package cs3500.animator.model.featurestate;


import cs3500.animator.view.OvalDrawingStrategy;
import java.awt.Color;

/**
 * This abstract class AbstractFeatureState implements all operations specified by the FeatureState
 * interface. An instance of this class represents the instantaneous state of a shape at a given
 * tick in an animation. This class represents the state with its position, x dimension, y
 * dimension, color, drawing strategy, and the motion number. The drawing strategy provides the
 * visual view with how to draw this shape. And thee motion number corresponds to the motion this
 * state is associated with.
 */
public final class OvalFeatureState extends AbstractFeatureState {

  /**
   * This constructor constructs an OvalFeatureState according to its position, its horizontal
   * radius, its vertical radius, and its color.
   *
   * @param loc  position
   * @param xDim horizontal radius
   * @param yDim vertical radius
   * @param c    color
   * @throws IllegalArgumentException if either of the supplied dimensions is not positive
   */
  public OvalFeatureState(Posn loc, double xDim, double yDim, Color c) {
    super(loc, xDim, yDim, c);
    this.howToDraw = new OvalDrawingStrategy();
  }

  /**
   * This constructor constructs an OvalFeatureState according to its x coordinate, y coordinate,
   * its horizontal radius, its vertical radius, and the three RGB values of its color.
   *
   * @param xPos x coordinate of position
   * @param yPos y coordinate of position
   * @param xDim horizontal radius
   * @param yDim vertical radius
   * @param r    r value of color
   * @param g    g value of color
   * @param b    b value of color
   * @throws IllegalArgumentException if r, g or b are outside of the range 0 to 255, inclusive
   * @throws IllegalArgumentException if the coordinates are not in first quadrant of cartesian
   *                                  plane
   * @throws IllegalArgumentException if either of the supplied dimensions is not positive
   */
  public OvalFeatureState(double xPos, double yPos, double xDim, double yDim, int r, int g, int b) {
    super(xPos, yPos, xDim, yDim, r, g, b);
    this.howToDraw = new OvalDrawingStrategy();
  }

  @Override
  public FeatureState factory(Posn loc, double xDim, double yDim, Color c) {
    return new OvalFeatureState(loc, xDim, yDim, c);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof OvalFeatureState)) {
      return false;
    }
    OvalFeatureState that = (OvalFeatureState) obj;
    return this.getLocation().getX() == that.getLocation().getX() &&
        this.getLocation().getY() == that.getLocation().getY() &&
        this.getColor().equals(that.getColor()) &&
        this.getXDimension() == that.getXDimension() &&
        this.getYDimension() == that.getYDimension();
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public FeatureState copy() {
    return new OvalFeatureState(this.getLocation(), this.getXDimension(), this.getYDimension(),
        this.getColor());
  }
}