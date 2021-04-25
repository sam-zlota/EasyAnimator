package cs3500.animator.view;

import java.io.Writer;

import cs3500.animator.model.animationmodel.AnimationModel;


/**
 * This interface represents the view of an Animation.
 */
public interface IView {

  /**
   * Runs animation in respective view.
   */
  void play();

  /**
   * Sets writer object field to given object.
   *
   * @param out represents a writer object.
   */
  void setOut(Writer out);

  /**
   * Sets the speed field to given speed value.
   *
   * @param speed represents the speed of the animation.
   */
  void setSpeed(int speed);

  /**
   * Sets the model field to the given model.
   *
   * @param model represents an AnimationModel.
   */
  void setModel(AnimationModel model);
}
