package cs3500.animator.view;

import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionListener;

import cs3500.animator.model.featurestate.FeatureState;


/**
 * An interface that supports viewing an animation as well as additional editing and playback
 * controls for a user as specified below. It is inherently meant to be visual with user input via
 * buttons.
 */
public interface IEditorView extends IView {

  /**
   * This method will pause the animation if it is currently running or it will resume the animation
   * if is currently paused.
   */
  void pauseOrResume();

  /**
   * This method will start the animation if it has not been started yet or it will restart the
   * animation if the animation has already started.
   */
  void startOrRestart();

  /**
   * This method will enable looping of the animation if looping is currently disabled or it will
   * disable looping if looping is currently disabled.
   */
  void enableDisableLooping();

  /**
   * This method will decrease the speed of the animation.
   */
  void decreaseSpeed();

  /**
   * This method will increase the speed of the animation.
   */
  void increaseSpeed();

  /**
   * This method sets the ActionListener for the buttons being displayed.
   *
   * @param listener the ActionListener to control buttons
   */
  void setActionListener(ActionListener listener);

  /**
   * This method sets the SelectionListener for the list selection items displayed.
   *
   * @param listener the ListSelectionListener to control the selection
   */
  void setSelectionListener(ListSelectionListener listener);

  /**
   * Gets the shape name selected in the GUI by the user.
   *
   * @return String representing name of shape
   */
  String getShapeName();

  /**
   * Gets the shape selected in the list selection in the visual interface.
   *
   * @return String representing the name of the selected shape
   */
  String getSelectedShape();

  /**
   * Removes the shape with the given name from the animation.
   *
   * @param name representing the shape that is to be removed
   */
  void removeShapeFromList(String name);

  /**
   * Gets the keyFrame that is to be added to the animation from the inputed values.
   *
   * @return FeatureState representing the keyFrame to be added
   */
  FeatureState getKeyFrameToAdd();

  /**
   * Gets the tick inputted by the user to add the next keyFrame.
   *
   * @return int representing the tick of the keyFrame to be added
   */
  int getTickToAddKeyFrame();

  /**
   * Sets the list of keyFrame in the graphical interface based on given shapeName.
   *
   * @param shapeName represents the name of the shape who's keyFrames will be displayed
   */
  void setKeyFrameList(String shapeName);

  /**
   * Gets the tick of the keyFrame to be removed.
   *
   * @return int representing the tick of the keyFrame to be removed
   */
  int getTickToRemoveKeyFrame();

  /**
   * Determines if a keyFrame is selected in the graphical interface.
   *
   * @return boolean representing whether the keyFrame is selected
   */
  boolean isAKeyFrameSelected();

  /**
   * Fills the keyFrame values based on selected keyFrame.
   */
  void fillKeyFrameValues();

  /**
   * Determines if a shape is selected in the graphical interface.
   *
   * @return boolean representing whether the shape is selected
   */
  boolean isANewShapeSelected();

  /**
   * Gets the tick of the keyFrame being edited.
   *
   * @return int representing the tick of the keyFrame being edited
   */
  int getTickToEditKeyFrame();
}
