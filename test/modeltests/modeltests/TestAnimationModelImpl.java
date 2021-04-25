package modeltests.modeltests;


import org.junit.Test;

import java.util.Arrays;

import cs3500.animator.model.animationmodel.AnimationModel;
import cs3500.animator.model.animationmodel.AnimationModelImpl;
import cs3500.animator.model.feature.EllipseFeature;
import cs3500.animator.model.feature.Feature;
import cs3500.animator.model.feature.RectangleFeature;
import cs3500.animator.model.featuremotion.FeatureMotion;
import cs3500.animator.model.featuremotion.FeatureMotionImpl;
import cs3500.animator.model.featurestate.FeatureState;
import cs3500.animator.model.featurestate.OvalFeatureState;
import cs3500.animator.model.featurestate.RectangleFeatureState;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for methods in AnimationModelImpl class in AnimationModel interface.
 */
public class TestAnimationModelImpl {

  AnimationModel model1;
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

    this.model1 = new AnimationModelImpl();
    this.model1.addShape(this.rectF);
    this.model1.addShape(this.ellipseF);
    this.model1.setHeight(100);
    this.model1.setWidth(100);
    this.model1.setXBound(0);
    this.model1.setYBound(100);

  }

  @Test
  public void testIsVisible() {
    this.init();
    assertTrue(this.model1.isVisible(9));
    assertFalse(this.model1.isVisible(20));
  }

  @Test
  public void testGetWidth() {
    this.init();
    assertEquals(100, this.model1.getWidth());
  }

  @Test
  public void testGetHeight() {
    this.init();
    assertEquals(100, this.model1.getHeight());
  }

  @Test
  public void testGetListOfFeatures() {
    this.init();
    assertArrayEquals(new Feature[]{this.rectF, this.ellipseF},
        this.model1.getListOfFeatures().toArray());
  }

  @Test
  public void testAddShape() {
    this.init();
    Feature toAdd = new RectangleFeature("boop");
    this.model1.addShape(toAdd);
    assertArrayEquals(new Feature[]{this.rectF, this.ellipseF, toAdd},
        this.model1.getListOfFeatures().toArray());
  }

  @Test
  public void testAddMotion() {
    this.init();

    FeatureMotion rectFM2 = new FeatureMotionImpl(this.rectEnd, this.rectStart, 20, 25);
    this.model1.addMotion("R", rectFM2);
    assertArrayEquals(new FeatureMotion[]{this.rectFM1, rectFM2},
        this.model1.getListOfFeatures().get(0).getListOfFeatureMotions().toArray());

  }

  @Test
  public void testRemoveMotion() {
    this.init();
    FeatureMotion rectFM2 = new FeatureMotionImpl(this.rectEnd, this.rectStart, 20, 25);
    this.model1.addMotion("R", rectFM2);
    assertArrayEquals(new FeatureMotion[]{this.rectFM1, rectFM2},
        this.model1.getListOfFeatures().get(0).getListOfFeatureMotions().toArray());
    this.model1.removeMotion("R", rectFM2);
    assertArrayEquals(new FeatureMotion[]{this.rectFM1},
        this.model1.getListOfFeatures().get(0).getListOfFeatureMotions().toArray());
  }


}
