package viewtests;

import static cs3500.animator.util.AnimationReader.parseFile;
import static org.junit.Assert.assertEquals;

import cs3500.animator.model.animationmodel.AnimationModel;
import cs3500.animator.model.animationmodel.AnimationModelImpl;
import cs3500.animator.view.IView;
import cs3500.animator.view.TextualView;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import org.junit.Test;


/**
 * Class with tests for an TextualView of an Animation.
 */
public class TestTextualView {

  IView view;

  @Test
  public void testTextual() throws IOException {
    String in = "toh-3.txt";
    Readable fRead = new FileReader(in);
    Writer writer = new PrintWriter(System.out);
    AnimationModel model = parseFile(fRead, new AnimationModelImpl.Builder());

    this.view = new TextualView(model, writer);

    writer.flush();
    this.view.play();

    System.out.println(this.view.toString());
    assertEquals("motion disk2 1\t167\t210\t65\t30\t6\t247\t41\t\t1\t167\t210\t65\t30\t6\t247\t41\n"
        + "motion disk2 1\t167\t210\t65\t30\t6\t247\t41\t\t57\t167\t210\t65\t30\t6\t247\t41\n"
        + "motion disk2 57\t167\t210\t65\t30\t6\t247\t41\t\t67\t167\t50\t65\t30\t6\t247\t41\n"
        + "motion disk2 67\t167\t50\t65\t30\t6\t247\t41\t\t68\t167\t50\t65\t30\t6\t247\t41\n"
        + "motion disk2 68\t167\t50\t65\t30\t6\t247\t41\t\t78\t317\t50\t65\t30\t6\t247\t41\n"
        + "motion disk2 78\t317\t50\t65\t30\t6\t247\t41\t\t79\t317\t50\t65\t30\t6\t247\t41\n"
        + "motion disk2 79\t317\t50\t65\t30\t6\t247\t41\t\t89\t317\t240\t65\t30\t6\t247\t41\n"
        + "motion disk2 89\t317\t240\t65\t30\t6\t247\t41\t\t185\t317\t240\t65\t30\t6\t247\t41\n"
        + "motion disk2 185\t317\t240\t65\t30\t6\t247\t41\t\t195\t317\t50\t65\t30\t6\t247\t41\n"
        + "motion disk2 195\t317\t50\t65\t30\t6\t247\t41\t\t196\t317\t50\t65\t30\t6\t247\t41\n"
        + "motion disk2 196\t317\t50\t65\t30\t6\t247\t41\t\t206\t467\t50\t65\t30\t6\t247\t41\n"
        + "motion disk2 206\t467\t50\t65\t30\t6\t247\t41\t\t207\t467\t50\t65\t30\t6\t247\t41\n"
        + "motion disk2 207\t467\t50\t65\t30\t6\t247\t41\t\t217\t467\t210\t65\t30\t6\t247\t41\n"
        + "motion disk2 217\t467\t210\t65\t30\t6\t247\t41\t\t225\t467\t210\t65\t30\t0\t255\t0\n"
        + "motion disk2 225\t467\t210\t65\t30\t0\t255\t0\t\t302\t467\t210\t65\t30\t0\t255\t0\n"
        + "\n"
        + "motion disk1 1\t190\t180\t20\t30\t0\t49\t90\t\t1\t190\t180\t20\t30\t0\t49\t90\n"
        + "motion disk1 1\t190\t180\t20\t30\t0\t49\t90\t\t25\t190\t180\t20\t30\t0\t49\t90\n"
        + "motion disk1 25\t190\t180\t20\t30\t0\t49\t90\t\t35\t190\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 35\t190\t50\t20\t30\t0\t49\t90\t\t36\t190\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 36\t190\t50\t20\t30\t0\t49\t90\t\t46\t490\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 46\t490\t50\t20\t30\t0\t49\t90\t\t47\t490\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 47\t490\t50\t20\t30\t0\t49\t90\t\t57\t490\t240\t20\t30\t0\t49\t90\n"
        + "motion disk1 57\t490\t240\t20\t30\t0\t49\t90\t\t89\t490\t240\t20\t30\t0\t49\t90\n"
        + "motion disk1 89\t490\t240\t20\t30\t0\t49\t90\t\t99\t490\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 99\t490\t50\t20\t30\t0\t49\t90\t\t100\t490\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 100\t490\t50\t20\t30\t0\t49\t90\t\t110\t340\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 110\t340\t50\t20\t30\t0\t49\t90\t\t111\t340\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 111\t340\t50\t20\t30\t0\t49\t90\t\t121\t340\t210\t20\t30\t0\t49\t90\n"
        + "motion disk1 121\t340\t210\t20\t30\t0\t49\t90\t\t153\t340\t210\t20\t30\t0\t49\t90\n"
        + "motion disk1 153\t340\t210\t20\t30\t0\t49\t90\t\t163\t340\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 163\t340\t50\t20\t30\t0\t49\t90\t\t164\t340\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 164\t340\t50\t20\t30\t0\t49\t90\t\t174\t190\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 174\t190\t50\t20\t30\t0\t49\t90\t\t175\t190\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 175\t190\t50\t20\t30\t0\t49\t90\t\t185\t190\t240\t20\t30\t0\t49\t90\n"
        + "motion disk1 185\t190\t240\t20\t30\t0\t49\t90\t\t217\t190\t240\t20\t30\t0\t49\t90\n"
        + "motion disk1 217\t190\t240\t20\t30\t0\t49\t90\t\t227\t190\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 227\t190\t50\t20\t30\t0\t49\t90\t\t228\t190\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 228\t190\t50\t20\t30\t0\t49\t90\t\t238\t490\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 238\t490\t50\t20\t30\t0\t49\t90\t\t239\t490\t50\t20\t30\t0\t49\t90\n"
        + "motion disk1 239\t490\t50\t20\t30\t0\t49\t90\t\t249\t490\t180\t20\t30\t0\t49\t90\n"
        + "motion disk1 249\t490\t180\t20\t30\t0\t49\t90\t\t257\t490\t180\t20\t30\t0\t255\t0\n"
        + "motion disk1 257\t490\t180\t20\t30\t0\t255\t0\t\t302\t490\t180\t20\t30\t0\t255\t0\n"
        + "\n"
        + "motion disk3 1\t145\t240\t110\t30\t11\t45\t175\t\t1\t145\t240\t110\t30\t11\t45\t175\n"
        + "motion disk3 1\t145\t240\t110\t30\t11\t45\t175\t\t121\t145\t240\t110\t30\t11\t45\t175\n"
        + "motion disk3 121\t145\t240\t110\t30\t11\t45\t175\t\t131\t145\t50\t110\t30\t11\t45\t175\n"
        + "motion disk3 131\t145\t50\t110\t30\t11\t45\t175\t\t132\t145\t50\t110\t30\t11\t45\t175\n"
        + "motion disk3 132\t145\t50\t110\t30\t11\t45\t175\t\t142\t445\t50\t110\t30\t11\t45\t175\n"
        + "motion disk3 142\t445\t50\t110\t30\t11\t45\t175\t\t143\t445\t50\t110\t30\t11\t45\t175\n"
        + "motion disk3 143\t445\t50\t110\t30\t11\t45\t175\t\t153\t445\t240\t110\t30\t11\t45\t175\n"
        + "motion disk3 153\t445\t240\t110\t30\t11\t45\t175\t\t161\t445\t240\t110\t30\t0\t255\t0\n"
        + "motion disk3 161\t445\t240\t110\t30\t0\t255\t0\t\t302\t445\t240\t110\t30\t0\t255\t0\n\n",
        this.view.toString());
  }
}