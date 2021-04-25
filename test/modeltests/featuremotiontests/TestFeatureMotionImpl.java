package modeltests.featuremotiontests;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.featuremotion.AttributeName;
import cs3500.animator.model.featuremotion.FeatureMotion;
import cs3500.animator.model.featuremotion.FeatureMotionImpl;
import cs3500.animator.model.featurestate.FeatureState;
import cs3500.animator.model.featurestate.OvalFeatureState;
import cs3500.animator.model.featurestate.RectangleFeatureState;
import org.junit.Test;


/**
 * Tests for methods in FeatureMotionImpl class.
 */
public class TestFeatureMotionImpl {


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

    //constructing Ellipse
    this.ellipseStart = new OvalFeatureState(11, 11, 11, 11, 11, 11, 11);
    this.ellipseEnd = new OvalFeatureState(21, 21, 21, 21, 21, 21, 21);
    this.ellipseFM1 = new FeatureMotionImpl(this.ellipseStart, this.ellipseEnd, 5, 15);

  }


  @Test
  public void testIsVisible() {
    this.init();
    assertTrue(this.rectFM1.isVisible(1));
    assertTrue(this.ellipseFM1.isVisible(14));
    assertFalse(this.ellipseFM1.isVisible(1));
    assertFalse(this.rectFM1.isVisible(14));
  }

  @Test
  public void testGetStartTick() {
    this.init();
    assertEquals(0, this.rectFM1.getStartTick());
    assertEquals(5, this.ellipseFM1.getStartTick());
  }

  @Test
  public void testGetEndTick() {
    this.init();
    assertEquals(10, this.rectFM1.getEndTick());
    assertEquals(15, this.ellipseFM1.getEndTick());
  }

  @Test
  public void testGetStartState() {
    this.init();
    assertEquals(this.rectStart, this.rectFM1.getStartState());
    assertEquals(this.ellipseStart, this.ellipseFM1.getStartState());
  }

  @Test
  public void testGetEndState() {
    this.init();
    assertEquals(this.rectEnd, this.rectFM1.getEndState());
    assertEquals(this.ellipseEnd, this.ellipseFM1.getEndState());
  }

  @Test
  public void testGetAttributeTypes() {
    this.init();
    assertArrayEquals(
        new AttributeName[]{AttributeName.R, AttributeName.B, AttributeName.G, AttributeName.XDim,
            AttributeName.YDim, AttributeName.XPos, AttributeName.YPos},
        this.ellipseFM1.getAttributeTypes().toArray());

  }

  @Test
  public void testSetAndGetMotionNumber() {
    this.init();
    this.ellipseFM1.setMotionNumber(1);
    this.rectFM1.setMotionNumber(2);
    assertEquals(1, this.ellipseFM1.getMotionNumber());
    assertEquals(2, this.rectFM1.getMotionNumber());


  }


}
