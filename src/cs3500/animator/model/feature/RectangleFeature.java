package cs3500.animator.model.feature;


import cs3500.animator.model.featuremotion.FeatureMotion;
import cs3500.animator.model.featuremotion.FeatureMotionImpl;
import cs3500.animator.model.featurestate.FeatureState;
import cs3500.animator.model.featurestate.RectangleFeatureState;

/**
 * This class represents an Rectangle Feature. One instance of this class represents the lifetime of
 * an rectangle within an Animation which includes all of its instantaneous states.
 */
public class RectangleFeature extends AbstractFeature {

  /**
   * This method constructs an RectangleFeature with the specified name.
   *
   * @param name the name of the rectangle
   */
  public RectangleFeature(String name) {
    super(name);
  }

  @Override
  public FeatureMotion buildMotion(int whenAdded, int t1, int x1, int y1, int w1, int h1, int r1,
      int g1, int b1,
      int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
    FeatureState start = new RectangleFeatureState(x1, y1, w1, h1, r1, g1, b1);
    start.setMotionNumber(whenAdded);
    FeatureState end = new RectangleFeatureState(x2, y2, w2, h2, r2, g2, b2);
    end.setMotionNumber(whenAdded);
    FeatureMotion toAdd = new FeatureMotionImpl(start, end, t1, t2);
    toAdd.setMotionNumber(whenAdded);
    return toAdd;
  }

  @Override
  public String getType() {
    return "rect"; // returns type of Feature in the form of a String
  }


}
