package cs3500.animator.model.featurestate;

import cs3500.animator.view.DrawingStrategy;
import java.awt.Color;

/**
 * This abstract class AbstractFeatureState implements all operations specified by the FeatureState
 * interface. An instance of this class represents the instantaneous state of a shape at a given
 * tick in an animation. This class represents the state with its position, x dimension, y
 * dimension, color, drawing strategy, and the motion number. The drawing strategy provides the
 * visual view with how to draw this shape. And thee motion number corresponds to the motion this
 * state is associated with.
 */
public abstract class AbstractFeatureState implements FeatureState {

  protected DrawingStrategy howToDraw;
  private Posn location;
  private double xDimension;
  private double yDimension;
  private Color color;
  private int motionNumber;

  protected AbstractFeatureState(Posn loc, double xDimension, double yDimension, Color c) {
    if (xDimension <= 0 || yDimension <= 0) {
      throw new IllegalArgumentException("Dimensions must positive");
    }
    this.location = loc;
    this.xDimension = xDimension;
    this.yDimension = yDimension;
    this.color = c;
  }

  protected AbstractFeatureState(double xPos, double yPos, double xDim, double yDim, int r, int g,
      int b) {
    if (xDim <= 0 || yDim <= 0) {
      throw new IllegalArgumentException("Dimensions must positive");
    }
    this.xDimension = xDim;
    this.yDimension = yDim;

    try {
      this.location = new Posn(xPos, yPos);
      this.color = new Color(r, g, b);
    } catch (IllegalArgumentException iae) {
      throw new IllegalArgumentException("Cannot construct this FeatureState:" + iae.getMessage());
    }
  }

  @Override
  public Posn getLocation() {
    return new Posn(this.location);
  }

  @Override
  public Color getColor() {
    return new Color(this.color.getRGB());
  }

  @Override
  public double getXDimension() {
    return this.xDimension;
  }

  @Override
  public double getYDimension() {
    return this.yDimension;
  }

  @Override
  public int hashCode() {
    return (int) (this.getColor().getRGB() * this.getLocation().getX() * this.getLocation().getY()
        * this.getXDimension() * this.getYDimension());
  }

  @Override
  public abstract boolean equals(Object obj);

  @Override
  public DrawingStrategy getDrawingStrategy() {
    return this.howToDraw;
  }

  @Override
  public void setMotionNumber(int motionNumber) {
    this.motionNumber = motionNumber;
  }

  @Override
  public int getMotionNumber() {
    return this.motionNumber;
  }

}