package cs3500.animator.model.featurestate;

import cs3500.animator.view.DrawingStrategy;
import java.awt.Color;


/**
 * A FeatureState represents the instantaneous state of one Feature in an animation. A FeatureState
 * encapsulates the location, height, width, and color of a given shape in an animation at a given
 * tick.
 */
public interface FeatureState {

  /**
   * Returns the location of the FeatureState.
   *
   * @return Posn
   */
  Posn getLocation();

  /**
   * Returns the color of the FeatureState.
   *
   * @return Color
   */
  Color getColor();

  /**
   * Returns x dimension of the FeatureState.
   *
   * @return double
   */
  double getXDimension();

  /**
   * Returns y dimension of the FeatureState.
   *
   * @return double
   */
  double getYDimension();

  /**
   * Returns an instance of this FeatureStates exact class using the specified arguments.
   *
   * @param pos  position
   * @param xDim x dimension
   * @param yDim y dimension
   * @param c    color
   * @return the FeatureState as specified
   */
  FeatureState factory(Posn pos, double xDim, double yDim, Color c);

  /**
   * Gets the drawing strategy used to draw this shape.
   *
   * @return
   */
  DrawingStrategy getDrawingStrategy();

  /**
   * This method sets the motion number. The motion number corresponds to the order in which the
   * motion associated with this FeatureState was added.
   *
   * @param motionNumber the desired motion number
   */
  void setMotionNumber(int motionNumber);

  /**
   * This method gets the motion number for this FeatureState. The motion number corresponds to the
   * order in which the motion associated with this FeatureState was added.
   *
   * @return
   */
  int getMotionNumber();

  /**
   * Returns a copy of this FeatureState.
   *
   * @return a copy of this FeatureState
   */
  FeatureState copy();
}
