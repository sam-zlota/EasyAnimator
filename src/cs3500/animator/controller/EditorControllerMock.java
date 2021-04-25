package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * Mock controller to be used to test contoller.
 */
public class EditorControllerMock implements IController, ActionListener,
    ListSelectionListener {

  private Appendable out;

  /**
   * This constructor accepts an Appendable object to be used in tests.
   * @param out the appenable object
   */
  public EditorControllerMock(Appendable out) {
    super();
    this.out = out;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      out.append(e.getActionCommand());
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }

  @Override
  public void giveControl() {
    try {
      out.append("gave control\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    try {
      out.append("value change\n");
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
  }
}
