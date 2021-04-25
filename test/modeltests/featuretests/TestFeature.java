package modeltests.featuretests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
 * Tests for methods in Feature class.
 */
public class TestFeature {

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
  public void testIsVisible() {
    this.init();
    assertTrue(this.rectF.isVisible(1));
    assertTrue(this.ellipseF.isVisible(10));
    assertFalse(this.rectF.isVisible(100));
    assertFalse(this.ellipseF.isVisible(2));

  }

  @Test
  public void testGetDisappearAt() {
    this.init();
    assertEquals(10, this.rectF.getDisappearAt());
    assertEquals(15, this.ellipseF.getDisappearAt());
  }

  @Test
  public void testGetAppearAt() {
    this.init();
    assertEquals(0, this.rectF.getAppearAt());
    assertEquals(5, this.ellipseF.getAppearAt());
  }

  @Test
  public void testGetName() {
    this.init();
    assertEquals("R", this.rectF.getName());
    assertEquals("E", this.ellipseF.getName());
  }

  @Test
  public void testGetListOfFeatureMotions() {
    this.init();
    assertEquals(this.rectFM1, this.rectF.getListOfFeatureMotions().get(0));
    assertEquals(this.ellipseFM1, this.ellipseF.getListOfFeatureMotions().get(0));

  }

  @Test
  public void getType() {
    this.init();
    assertEquals("rect", this.rectF.getType());
    assertEquals("ellipse", this.ellipseF.getType());
  }

}
