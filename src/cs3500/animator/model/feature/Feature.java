package cs3500.animator.model.feature;

import java.util.List;
import java.util.Map;

import cs3500.animator.model.featuremotion.FeatureMotion;
import cs3500.animator.model.featurestate.FeatureState;


/**
 * An interface to represent all operations that must be supported by a Feature of a given
 * animation. A Feature is synonymous with a shape. A Feature is intended to encapsulate the entire
 * set of motions for any given shape and all of the states encapsulated in those motions.
 */
public interface Feature {

  /**
   * Gets the name of this Feature.
   *
   * @return the name of this Feature.
   */
  String getName();

  /**
   * Builds a FeatureMotion corresponding to the type of this Feature according to the
   * specifications of the inputted parameters.
   *
   * @param t1 The starting tick of the motion to be returned.
   * @param x1 The x position of the starting state of the motion to be returned.
   * @param y1 The y position of the starting state of the motion to be returned.
   * @param w1 The width of the starting state of the the motion to be returned.
   * @param h1 The height of the starting state of the motion to be returned.
   * @param r1 The R value of the color of the starting state of the motion to be returned.
   * @param g1 The G value of the color of the starting state of the motion to be returned.
   * @param b1 The B value of the color of the starting state of the motion to be returned.
   * @param t2 The ending tick of the motion to be returned.
   * @param x2 The x position of the ending state of the motion to be returned.
   * @param y2 The y position of the ending state of the motion to be returned.
   * @param w2 The width of the ending state of the the motion to be returned.
   * @param h2 The height of the ending state of the motion to be returned.
   * @param r2 The R value of the color of the ending state of the motion to be returned.
   * @param g2 The G value of the color of the ending state of the motion to be returned.
   * @param b2 The B value of the color of the ending state of the motion to be returned.
   * @return A properly constructed FeatureMotion.
   */
  FeatureMotion buildMotion(int whenAdded, int t1, int x1, int y1, int w1, int h1, int r1, int g1,
      int b1, int t2,
      int x2, int y2, int w2, int h2, int r2, int g2, int b2);

  /**
   * Gets the tick that this Feature disappears at.
   *
   * @return an int representing the tick the Feature disappears at.
   */
  int getDisappearAt();

  /**
   * Gets the tick that this Feature appears at.
   *
   * @return the tick this Feature appears at.
   */
  int getAppearAt();

  /**
   * Sets the fields of this Feature according to the inputted list of FeatureMotion.
   *
   * @param motionList list of FeatureMotion
   */
  void setAllButName(List<FeatureMotion> motionList);

  /**
   * Gets the list of FeatureMotion that is stored in this Feature.
   *
   * @return list of FeatureMotion
   */
  List<FeatureMotion> getListOfFeatureMotions();

  /**
   * Returns the type of this Feature. For example, if the shape is a rectangle, this method wil
   * return "rect".
   *
   * @return a String representing the type of this Feature.
   */
  String getType();

  /**
   * This method adds the specified motion to the set of motions for this shape.
   *
   * @param motion the motion to add.
   */
  void addMotion(FeatureMotion motion);

  /**
   * This method removes the specified motion from the set of motions for this shape.
   *
   * @param motion the motion to return
   */
  void removeMotion(FeatureMotion motion);

  /**
   * Returns true if this AnimationComponent is visible during the provided tick.
   *
   * @param tick represents the current tick of the animation
   * @return true if visible
   */
  boolean isVisible(int tick);

  /**
   * Removes the keyFrame from this Feature at the given tick.
   *
   * @param tick represents the tick of the keyFrame that is to be removed.
   */
  void removeKeyFrame(int tick);

  /**
   * Adds a keyFrame to this Feature.
   *
   * @param state represents the keyFrame to be added.
   * @param tick represents the tick of the keyFrame to be added.
   */
  void addKeyFrame(FeatureState state, int tick);

  /**
   * Gets a map holding keyFrames for this Feature.
   *
   * @return Map where Integer represents tick and FeatureState represents keyFrame
   */
  Map<Integer, FeatureState> getKeyFrameMap();
}
