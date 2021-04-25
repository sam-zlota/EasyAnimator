package cs3500.animator.model.featuremotion;


import cs3500.animator.model.featurestate.FeatureState;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is an implementation of the FeatureMotion interface, implementing all specified
 * operations and enforcing proper construction. One instance of this class represnts a motion
 * of a shape in an animation. A motion consists of a start state, an end state and the start
 * and end ticks the motion occurs at.
 */
public class FeatureMotionImpl implements FeatureMotion {

  private final FeatureState start;
  private final FeatureState end;
  private final int startTick;
  private final int endTick;
  private List<AttributeName> attributesChanged;
  private int motionNumber;

  /**
   * This constructor builds an instance of the FeatureMotionImpl class according to the starting
   * state, the ending state, the starting tick, and the ending tick.
   *
   * @param start     the first state of this motion
   * @param end       the last state of this motion
   * @param startTick the tick this motion begins at
   * @param endTick   the tick this motion ends at
   * @throws IllegalArgumentException if start and ending states are not of the same class
   * @throws IllegalArgumentException if the starting tick is greater than or equal to the ending
   *                                  tick
   * @throws IllegalArgumentException if the starting tick is less than zero
   */
  public FeatureMotionImpl(FeatureState start, FeatureState end, int startTick, int endTick) {
    //all concrete implementing classes of FeatureState must be final
    //start and end must be the same class
    //we do not support a motion that changes classes from start to end even if it is a subclass
    if (!start.getClass().getName().equals(end.getClass().getName())) {
      throw new IllegalArgumentException("Start and End states are different classes");
    } else if (startTick > endTick) {
      throw new IllegalArgumentException("Start tick must be strictly less than end tick");
    } else if (startTick < 0) {
      throw new IllegalArgumentException("Start tick must be greater than or equal to zero");
    } else {
      //initializing fields
      this.start = start;
      this.end = end;
      this.startTick = startTick;
      this.endTick = endTick;
      this.attributesChanged = new ArrayList<AttributeName>();
      this.setListOfAttributes();
    }
  }

  private void setListOfAttributes() {
    //determines which attributes of the shape are changed during this motion
    //getting changes between start and end fields
    // if there is a change, add the type of attribute changed to attributesChanged List
    double deltaR = end.getColor().getRed() - start.getColor().getRed();
    if (deltaR != 0) {
      this.attributesChanged.add(AttributeName.R);
    }
    double deltaB = end.getColor().getBlue() - start.getColor().getBlue();
    if (deltaB != 0) {
      this.attributesChanged.add(AttributeName.B);
    }
    double deltaG = end.getColor().getGreen() - start.getColor().getGreen();
    if (deltaG != 0) {
      this.attributesChanged.add(AttributeName.G);
    }
    double deltaXDimension = end.getXDimension() - start.getXDimension();
    if (deltaXDimension != 0) {
      this.attributesChanged.add(AttributeName.XDim);
    }
    double deltaYDimension = end.getYDimension() - start.getYDimension();
    if (deltaYDimension != 0) {
      this.attributesChanged.add(AttributeName.YDim);
    }
    double deltaXPos = end.getLocation().getX() - start.getLocation().getX();
    if (deltaXPos != 0) {
      this.attributesChanged.add(AttributeName.XPos);
    }
    double deltaYPos = end.getLocation().getY() - start.getLocation().getY();
    if (deltaYPos != 0) {
      this.attributesChanged.add(AttributeName.YPos);
    }

  }

  @Override
  public boolean isVisible(int tick) {
    return tick >= this.startTick && tick < this.endTick;
  }

  @Override
  public int getStartTick() {
    return this.startTick;
  }

  @Override
  public int getEndTick() {
    return this.endTick;
  }

  @Override
  public FeatureState getStartState() {
    return this.start.copy();
  }

  @Override
  public FeatureState getEndState() {
    return this.end.copy();
  }

  @Override
  public List<AttributeName> getAttributeTypes() {
    return new ArrayList<>(this.attributesChanged);
  }

  @Override
  public int getMotionNumber() {
    return this.motionNumber;
  }

  @Override
  public void setMotionNumber(int motionNumber) {
    this.motionNumber = motionNumber;
  }

}