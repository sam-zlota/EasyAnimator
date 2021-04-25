package cs3500.animator.model.featuremotion;

import cs3500.animator.model.featurestate.FeatureState;
import java.util.List;


/**
 * An interface to define all operations that are needed to be implemented by a FeatureMotion. A
 * FeatureMotion encapsulates the continuous set of states(FeatureState) of a given animation across
 * a certain interval of time.
 */
public interface FeatureMotion {

  /**
   * This methods returns the starting tick of this FeatureMotion.
   *
   * @return an int representing the start tick
   */
  int getStartTick();

  /**
   * This methods returns the ending tick of this FeatureMotion.
   *
   * @return an int representing the end tick
   */
  int getEndTick();

  /**
   * Gets the initial FeatureState of this FeatureMotion.
   *
   * @return the initial FeatureState
   */
  FeatureState getStartState();

  /**
   * Gets the final FeatureState of this FeatureMotion.
   *
   * @return the final FeatureState
   */
  FeatureState getEndState();

  /**
   * Gets a list of the attribute types changed during this feature motion.
   *
   * @return List of AttributeTypes
   */
  List<AttributeName> getAttributeTypes();

  /**
   * Returns a number that corresponds to when it was inputted. This method allows the
   * animation to know which motions were added before others so that it can determine what order to
   * display overlapping shapes. This assumes that motions inputted first were meant to be drawn
   * before those drawn later.
   *
   * @return the motion number as specified above
   */
  int getMotionNumber();

  /**
   * This method sets the motion number of this motion. The motion number is a number that
   * corresponds to when it was inputted. This method allows the animation to know which motions
   * were added before others so that it can determine what order to display overlapping shapes.
   * This assumes that motions inputted first were meant to be drawn before those drawn later.
   */
  void setMotionNumber(int motionNumber);

  /**
   * This method determines if this motion is occruing at the specified tick.
   *
   * @param tick the tick
   * @return true if is visible
   */
  boolean isVisible(int tick);
}
