package cs3500.animator.controller;

/**
 * Represents an IController interface. This Interface is implemented by both controller classes.
 * The controller allows for swift communication between the model and view. If the view supports
 * user interaction, the controller should listen for inputs.
 */
public interface IController {

  /**
   * This method gives control to the controller, meaning that the controller will now
   * listen to events and properly manage the view and model.
   */
  void giveControl();
}
