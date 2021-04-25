package cs3500.animator.model.feature;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cs3500.animator.model.featuremotion.FeatureMotion;
import cs3500.animator.model.featuremotion.FeatureMotionImpl;
import cs3500.animator.model.featurestate.FeatureState;


/**
 * This class is an implementation of the Feature interface, implementing all specified operations
 * and enforcing proper construction. One instance of AbstractFeature represents all the motions and
 * states of a given shape in an Animation. This class also stores the name and the ticks this
 * motion begins and ends at.
 */
public abstract class AbstractFeature implements Feature {

  private String name; // represents name of Feature
  private int appearAt; // represents tick that Feature appears at
  private int disappearAt; // represents tick that Feature disappears at
  private List<FeatureMotion> motionList; // represents List of FeatureMotions for this Feature

  /**
   * Constructor for AbstractFeature class that only sets the name field.
   *
   * @param name represents the name of the Feature.
   */
  protected AbstractFeature(String name) {
    this.name = name;
    this.motionList = new ArrayList<>();
  }

  @Override
  public boolean isVisible(int tick) {
    //this motion is visible if it is greater than or equal to the tick it appears at
    //and less than the tick it disappears at
    return tick >= this.appearAt && tick < this.disappearAt;
  }

  @Override
  public int getDisappearAt() {
    return this.disappearAt; // gets disappearAt field
  }

  @Override
  public String getName() {
    return this.name; // gets name field
  }

  /**
   * This method sets all fields but the name. This method allows for the builder to set these
   * fields after calling the empty constructor.
   *
   * @param motionList list of FeatureMotion
   */
  public void setAllButName(List<FeatureMotion> motionList) {
    // sets appearAt field to start tick of first featureMotion in motionList
    this.appearAt = motionList.get(0).getStartTick();
    // sets disappearAt field to end tick in last featureMotion in motionList
    this.disappearAt = motionList.get(motionList.size() - 1).getEndTick();
    // sets motionList field to list of FeatureMotions given in argument
    this.motionList = new ArrayList<>(motionList);
  }

  @Override
  public List<FeatureMotion> getListOfFeatureMotions() {
    // returns copy of motionList field
    return new ArrayList<>(this.motionList);
  }


  @Override
  public void addMotion(FeatureMotion motion) {
    // make sure motion does not overlap with any other motions in motionList field
    if (doesOverlap(motion)) {
      throw new IllegalArgumentException("Motion overlaps!");
    } else {
      // add motion to motionList
      this.motionList.add(motion);
      // sort motionList
      Comparator<FeatureMotion> compTicks = Comparator.comparingInt(FeatureMotion::getStartTick);
      this.motionList.sort(compTicks);
    }
  }

  @Override
  public void removeMotion(FeatureMotion motion) {
    // removes given motion from motionList field
    this.motionList.remove(motion);

  }

  private boolean doesOverlap(FeatureMotion motion) {
    // starting tick of motion to be added is in between starting and ending tick of a
    // motion
    for (FeatureMotion current : this.motionList) {
      if (motion.getStartTick() >= current.getStartTick() && motion.getStartTick() <= current
          .getStartTick()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int getAppearAt() {
    // returns appearAt field
    return this.appearAt;
  }


  private void setListOfFeatureMotion(Map<Integer, FeatureState> kfMap) {

    // turn kfMap back into list of FeatureMotions
    List<FeatureMotion> newFMList = new ArrayList<>();
    // holds values in kfMap as list of FeatureStates
    List<FeatureState> fsValues = new ArrayList<FeatureState>(kfMap.values());
    // holds keys in kfMap as list of Integers
    List<Integer> fsTicks = new ArrayList<Integer>(kfMap.keySet());

    FeatureState currentState;
    FeatureState nextState;

    if (kfMap.size() == 1) {
      newFMList.add(new FeatureMotionImpl(fsValues.get(0), fsValues.get(0),
          fsTicks.get(0), fsTicks.get(0)));
    }

    for (int i = 0; i < kfMap.size() - 1; i++) {
      currentState = fsValues.get(i);
      nextState = fsValues.get(i + 1);

      newFMList.add(new FeatureMotionImpl(currentState, nextState,
          fsTicks.get(i), fsTicks.get(i + 1)));

    }

    Comparator<FeatureMotion> chronologicalOrder = Comparator
        .comparingInt(FeatureMotion::getStartTick);
    newFMList.sort(chronologicalOrder);

    if (!newFMList.isEmpty()) {
      this.appearAt = newFMList.get(0).getStartTick();
      this.disappearAt = newFMList.get(newFMList.size() - 1).getEndTick();
    }

    this.motionList = newFMList;

  }

  /**
   * Gets all the keyFrame in this Feature in the form of a Map with Integer representing
   * tick and FeatureState representing keyFrame. It basically supports going from a list of
   * motions to a map of tick->keyframe.
   *
   * @return Map that represents keyFrames in this Feature
   */
  public Map<Integer, FeatureState> getKeyFrameMap() {

    // represents list of FeatureMotions in Feature that is being edited
    List<FeatureMotion> fmList = this.getListOfFeatureMotions();

    // List of keyFrames representing fmList (list of FeatureMotions)
    Map<Integer, FeatureState> kfMap = new TreeMap<>();

    if (fmList.size() == 1) {
      kfMap.put(fmList.get(0).getStartTick(), fmList.get(0).getStartState());
      if (fmList.get(0).getStartTick() != fmList.get(0).getEndTick()) {
        kfMap.put(fmList.get(0).getEndTick(), fmList.get(0).getEndState());
      }
    }

    FeatureMotion current;
    FeatureMotion next;
    for (int i = 0; i < fmList.size() - 1; i++) {
      current = fmList.get(i);
      next = fmList.get(i + 1);

      kfMap.put(current.getStartTick(), current.getStartState());

      // test for gap (current end tick does not equal next start tick)
      if (current.getEndTick() != next.getStartTick()) {
        // add current end state
        kfMap.put(current.getEndTick(), current.getEndState());
      }

      // if current is at second to last motion
      if (i == fmList.size() - 2) {
        // add final motion start and end state
        kfMap.put(next.getStartTick(), next.getStartState());
        kfMap.put(next.getEndTick(), next.getEndState());

      }
    }

    return kfMap;
  }

  @Override
  public void addKeyFrame(FeatureState state, int tick) {
    Map<Integer, FeatureState> kfMap = this.getKeyFrameMap();
    kfMap.put(tick, state);
    this.setListOfFeatureMotion(kfMap);
  }

  @Override
  public void removeKeyFrame(int tick) {
    Map<Integer, FeatureState> kfMap = this.getKeyFrameMap();
    kfMap.remove(tick);
    this.setListOfFeatureMotion(kfMap);
  }
}
