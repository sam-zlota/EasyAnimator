package modeltests.featuresstatetests;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.featurestate.FeatureState;
import cs3500.animator.model.featurestate.RectangleFeatureState;
import org.junit.Test;

/**
 * Tests for methods in FeatureState class.
 */
public class TestFeatureState {

  FeatureState rect;
  FeatureState ellipse;

  private void init() {
    this.rect = new RectangleFeatureState(10, 12, 14,
        16, 18, 20, 22);
    this.ellipse = new RectangleFeatureState(15, 17,
        19, 21, 23, 25, 27);
  }

  @Test
  public void testGetLocation() {
    this.init();
    assertEquals(10, this.rect.getLocation().getX(), .001);
    assertEquals(12, this.rect.getLocation().getY(), .001);
    assertEquals(15, this.ellipse.getLocation().getX(), .001);
    assertEquals(17, this.ellipse.getLocation().getY(), .001);
  }

  @Test
  public void testGetColor() {
    this.init();
    assertEquals(18, this.rect.getColor().getRed(), .001);
    assertEquals(20, this.rect.getColor().getGreen(), .001);
    assertEquals(22, this.rect.getColor().getBlue(), .001);
    assertEquals(23, this.ellipse.getColor().getRed(), .001);
    assertEquals(25, this.ellipse.getColor().getGreen(), .001);
    assertEquals(27, this.ellipse.getColor().getBlue(), .001);
  }

  @Test
  public void testGetXDim() {
    this.init();
    assertEquals(14, this.rect.getXDimension(), .001);
    assertEquals(19, this.ellipse.getXDimension(), .001);

  }

  @Test
  public void testGetYDim() {
    this.init();
    assertEquals(16, this.rect.getYDimension(), .001);
    assertEquals(21, this.ellipse.getYDimension(), .001);
  }

  @Test
  public void testGetAndSetMotionNumber() {
    this.init();
    this.rect.setMotionNumber(10);
    assertEquals(10, this.rect.getMotionNumber());
    this.ellipse.setMotionNumber(11);
    assertEquals(11, this.ellipse.getMotionNumber());
  }


}
