package cs3500.animator.model.animationmodel;

import java.util.List;

import cs3500.animator.model.feature.Feature;
import cs3500.animator.model.featuremotion.FeatureMotion;
import cs3500.animator.model.featurestate.FeatureState;

/**
 * An interface to represent an Animation. This interface supports all operations to be used
 * representing an Animation. This interface extends Iterator where a List of FeatureState
 * represents a singular frame to be displayed by view.
 */
public interface AnimationModel {

  /**
   * This method determines if this AnimationModel is currently visible. This means that the
   * inputted tick is less than the last tick of the animation. If this method is true, the
   * animation is still ongoing.
   *
   * @param tick a tick to be compared to the last tick of this animation
   * @return true if animation still ocurring.
   */
  boolean isVisible(int tick);

  /**
   * Gets the width of the canvas.
   *
   * @return an int representing the width of the canvas.
   */
  int getWidth();

  /**
   * Gets the height of the canvas.
   *
   * @return an representing the width of the canvas.
   */
  int getHeight();

  /**
   * Gets the List of Features associated with this AnimationModel.
   *
   * @return a list of features
   */
  List<Feature> getListOfFeatures();

  /**
   * Will add the inuputted shape to this animation.
   *
   * @param shape the shape to be added.
   */
  void addShape(Feature shape);

  /**
   * This method will remove the inputted shape from this animation.
   *
   */
  void removeShape(String shapeName);

  /**
   * This method will add the specified motion to the shape with the specified name.
   *
   * @param name the name of the shape the motion will be added to
   * @param motion the motion to add to the shape with specified name
   */
  void addMotion(String name, FeatureMotion motion);

  /**
   * This method will remove the specified motion from the shape with the specified name.
   * @param name the name of the shape the motion will be removed from
   * @param motion the motion to remove from the shape with specified name
   */
  void removeMotion(String name, FeatureMotion motion);

  /**
   * This method sets the width of this animation canvas to the specified value.
   * @param w the desired width
   */
  void setWidth(int w);

  /**
   * This method sets the height of the animation canvas to the specified value.
   * @param h the desired height
   */
  void setHeight(int h);

  /**
   * This method sets the x bound of the animation canvas to the specified value.
   * @param xBound the desired x bound
   */
  void setXBound(int xBound);

  /**
   * This method sets the y bound of the animation canvas to the specified value.
   * @param yBound the desired y bound
   */
  void setYBound(int yBound);

  /**
   * Adds the keyframe to the specified shape by name.
   *
   * @param name represents name of keyFrame
   * @param state represents FeatureState to be added as keyFrame
   * @param tick represents tick where keyFrame is added
   */
  void addKeyFrame(String name, FeatureState state, int tick);

  /**
   * Removes the keyframe to the specified shape by name.
   *
   * @param name represents name of keyFrame to be removed
   * @param tick represents tick of keyFrame to be removed
   */
  void removeKeyFrame(String name, int tick);

  /**
   * Replaces the keyFrame that is specified by shape and name.
   *
   * @param nameE represents name of shape of keyFrame to be replaced
   * @param kfE represents FeatureState of keyFrame to be replaced
   * @param tE represents tick of keyFrame to be replaced
   */
  void replaceKeyFrame(String nameE, FeatureState kfE, int tE);
}