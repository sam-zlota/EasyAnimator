package cs3500.animator.model.feature;

import cs3500.animator.model.featuremotion.FeatureMotion;
import cs3500.animator.model.featuremotion.FeatureMotionImpl;
import cs3500.animator.model.featurestate.FeatureState;
import cs3500.animator.model.featurestate.OvalFeatureState;

/**
 * This class represents an Ellipse Feature. One instance of this class represents the lifetime of
 * an Ellipse within an Animation which includes all of its instantaneous states.
 */
public class EllipseFeature extends AbstractFeature {

  /**
   * This method constructs an EllipseFeature with the specified name.
   *
   * @param name the name of the ellipse
   */
  public EllipseFeature(String name) {
    super(name);
  }

  // returns FeatureMotion with given start and end FeatureStates and start and end ticks
  @Override
  public FeatureMotion buildMotion(int whenAdded, int t1, int x1, int y1, int w1, int h1, int r1,
      int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
    FeatureState start = new OvalFeatureState(x1, y1, w1, h1, r1, g1, b1);
    start.setMotionNumber(whenAdded);
    FeatureState end = new OvalFeatureState(x2, y2, w2, h2, r2, g2, b2);
    end.setMotionNumber(whenAdded);
    FeatureMotion toAdd = new FeatureMotionImpl(start, end, t1, t2);
    toAdd.setMotionNumber(whenAdded);
    return toAdd;
  }

  @Override
  public String getType() {
    return "ellipse"; // returns type of Feature in the form of a String
  }


}
