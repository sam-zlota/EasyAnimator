package modeltests.modeltests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import java.util.Arrays;
import org.junit.Test;

/**
 * A class to test the methods used in editing a model.
 */
public class TestEditorModelMethods {

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
    this.rectStart = new RectangleFeatureState(10, 10, 10, 10,
        10, 10, 10);
    this.rectEnd = new RectangleFeatureState(20, 20, 20, 20,
        20, 20, 20);
    this.rectFM1 = new FeatureMotionImpl(this.rectStart, this.rectEnd, 0, 10);
    this.rectF = new RectangleFeature("R");
    this.rectF.setAllButName(Arrays.asList(this.rectFM1));

    //constructing Ellipse
    this.ellipseStart = new OvalFeatureState(11, 11, 11, 11,
        11, 11, 11);
    this.ellipseEnd = new OvalFeatureState(21, 21, 21, 21,
        21, 21, 21);
    this.ellipseFM1 = new FeatureMotionImpl(this.ellipseStart, this.ellipseEnd,
        5, 15);
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
  public void testAddKeyFrame() {
    this.init();
    assertFalse(this.model1.isVisible(17));
    this.model1.addKeyFrame("E", new OvalFeatureState(10, 10,
        10, 10, 10, 10, 10), 20);
    assertTrue(this.model1.isVisible(17));
    assertEquals(20, this.model1.getListOfFeatures().get(1).getDisappearAt());
  }


  @Test
  public void testRemoveKeyFrame() {
    this.init();
    assertFalse(this.model1.isVisible(17));
    this.model1.addKeyFrame("E", new OvalFeatureState(10, 10, 10,
        10, 10, 10, 10), 20);
    assertTrue(this.model1.isVisible(17));
    assertEquals(20, this.model1.getListOfFeatures().get(1).getDisappearAt());
    this.model1.removeKeyFrame("E", 20);
    assertEquals(15, this.model1.getListOfFeatures().get(1).getDisappearAt());
    assertFalse(this.model1.isVisible(17));

  }

  @Test
  public void testEditKeyFrame() {
    this.init();
    assertEquals(10,
        this.model1.getListOfFeatures().get(0).getListOfFeatureMotions().get(0).getStartState()
            .getColor().getRed());
    this.model1.replaceKeyFrame("E", new OvalFeatureState(10, 10, 10,
        10, 20, 10, 10), 5);
    assertEquals(20,
        this.model1.getListOfFeatures().get(1).getListOfFeatureMotions().get(0).getStartState()
            .getColor().getRed());
  }

}
