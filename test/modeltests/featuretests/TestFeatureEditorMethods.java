package modeltests.featuretests;

import static org.junit.Assert.assertArrayEquals;

import cs3500.animator.model.feature.EllipseFeature;
import cs3500.animator.model.feature.Feature;
import cs3500.animator.model.feature.RectangleFeature;
import cs3500.animator.model.featuremotion.FeatureMotion;
import cs3500.animator.model.featuremotion.FeatureMotionImpl;
import cs3500.animator.model.featurestate.FeatureState;
import cs3500.animator.model.featurestate.OvalFeatureState;
import cs3500.animator.model.featurestate.RectangleFeatureState;
import java.util.Arrays;
import org.junit.Test;

/**
 * Class to test methods used in editing shapes/features of an animation.
 */
public class TestFeatureEditorMethods {

  Feature rectF;
  Feature ellipseF;
  FeatureMotion rectFM1;
  FeatureMotion ellipseFM1;
  FeatureState rectStart;
  FeatureState rectEnd;
  FeatureState ellipseStart;
  FeatureState ellipseEnd;

  private void init() {
    //constructing Rectangle
    this.rectStart = new RectangleFeatureState(10, 10, 10, 10, 10, 10, 10);
    this.rectEnd = new RectangleFeatureState(20, 20, 20, 20, 20, 20, 20);
    this.rectFM1 = new FeatureMotionImpl(this.rectStart, this.rectEnd, 0, 10);
    this.rectF = new RectangleFeature("R");
    this.rectF.setAllButName(Arrays.asList(this.rectFM1));

    //constructing Ellipse
    this.ellipseStart = new OvalFeatureState(11, 11, 11, 11, 11, 11, 11);
    this.ellipseEnd = new OvalFeatureState(21, 21, 21, 21, 21, 21, 21);
    this.ellipseFM1 = new FeatureMotionImpl(this.ellipseStart, this.ellipseEnd, 5, 15);
    this.ellipseF = new EllipseFeature("E");
    this.ellipseF.setAllButName(Arrays.asList(this.ellipseFM1));
  }


  @Test
  public void testGetKeyFrameMap() {
    this.init();
    assertArrayEquals(new Integer[]{0, 10}, this.rectF.getKeyFrameMap().keySet().toArray());
    assertArrayEquals(new FeatureState[]{this.rectStart, this.rectEnd},
        this.rectF.getKeyFrameMap().values().toArray());
  }

  @Test
  public void testAddKeyFrame() {
    this.init();
    assertArrayEquals(new Integer[]{0, 10}, this.rectF.getKeyFrameMap().keySet().toArray());
    assertArrayEquals(new FeatureState[]{this.rectStart, this.rectEnd},
        this.rectF.getKeyFrameMap().values().toArray());
    FeatureState toAdd = new RectangleFeatureState(10, 10, 10, 10, 10, 10, 10);
    this.rectF.addKeyFrame(toAdd, 20);
    assertArrayEquals(new Integer[]{0, 10, 20}, this.rectF.getKeyFrameMap().keySet().toArray());
    assertArrayEquals(new FeatureState[]{this.rectStart, this.rectEnd, toAdd},
        this.rectF.getKeyFrameMap().values().toArray());
  }

  @Test
  public void testRemoveKeyFrame() {
    this.init();
    assertArrayEquals(new Integer[]{0, 10}, this.rectF.getKeyFrameMap().keySet().toArray());
    assertArrayEquals(new FeatureState[]{this.rectStart, this.rectEnd},
        this.rectF.getKeyFrameMap().values().toArray());
    this.rectF.removeKeyFrame(10);
    assertArrayEquals(new Integer[]{0}, this.rectF.getKeyFrameMap().keySet().toArray());
    assertArrayEquals(new FeatureState[]{this.rectStart},
        this.rectF.getKeyFrameMap().values().toArray());
  }
}
