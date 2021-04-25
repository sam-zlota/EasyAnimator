package cs3500.animator.controller;


import cs3500.animator.view.IView;

/**
 * Represents a SimpleController class that implements IController Interface. Only implements
 * one method, which starts the animation. This controller does not support user input.
 */
public class SimpleController implements IController {

  IView view;

  /**
   * Constructor for SimpleController class.
   *
   * @param view represents a view object.
   */
  public SimpleController(IView view) {
    this.view = view;
  }

  @Override
  public void giveControl() {
    view.play();
  }
}
