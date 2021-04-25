package controllertests;

import static org.junit.Assert.assertEquals;

import cs3500.animator.controller.EditorControllerMock;
import java.awt.event.ActionEvent;
import java.util.EventObject;
import javax.swing.event.ListSelectionEvent;
import org.junit.Test;

/**
 * A class to test the controller with a mock.
 */
public class TestController {

  private EventObject event;
  private EditorControllerMock mock;
  private Appendable out;

  private void init() {
    this.out = new StringBuilder();
    this.mock = new EditorControllerMock(out);
  }

  @Test
  public void testActionPerformed() {
    this.init();
    String command = "an action was performed";
    this.event = new ActionEvent("not important", ActionEvent.ACTION_PERFORMED, command);
    this.mock.actionPerformed((ActionEvent) this.event);
    assertEquals(command, out.toString());
  }

  @Test
  public void testValueChanged() {
    this.init();
    this.event = new ListSelectionEvent("not important", 0, 100, false);
    this.mock.valueChanged((ListSelectionEvent) this.event);
    assertEquals("value change\n", out.toString());
  }

  @Test
  public void testGiveControl() {
    this.init();
    this.mock.giveControl();
    assertEquals("gave control\n", out.toString());
  }


}
