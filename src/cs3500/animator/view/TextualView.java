package cs3500.animator.view;

import cs3500.animator.model.animationmodel.AnimationModel;
import cs3500.animator.model.feature.Feature;
import cs3500.animator.model.featuremotion.FeatureMotion;
import cs3500.animator.model.featurestate.FeatureState;
import java.io.IOException;
import java.io.Writer;

/**
 * Represents a TextualView of an Animation. This class implements IView and outputs a
 * textual representation of the Animation with instructions.
 */
public class TextualView implements IView {

  AnimationModel model;
  Writer out;
  String text;

  /**
   * Consturcts a TextualView based on the specified model and Writer object. Will write to the
   * sopecfied location.
   * @param model the model
   * @param out Writer object to writer to
   */
  public TextualView(AnimationModel model, Writer out) {
    this.model = model;
    this.out = out;
    this.text = "";
  }

  /**
   * Empty constructor to be used by view factory.
   */
  public TextualView() {
    this.model = null;
    this.out = null;
    this.text = "";
  }

  @Override
  public void play() {
    try {
      this.text = this.animationModelToString(); // sets text field to AnimationModel as String
      out.flush();
      out.write(text);
      out.close();
    } catch (IOException i) {
      throw new IllegalArgumentException(i.getMessage());
    }
  }

  @Override
  public void setModel(AnimationModel model) {
    this.model = model; // sets model field to given model
  }

  @Override
  public void setOut(Writer out) {
    this.out = out; // sets writer field to given writer
  }

  @Override
  public void setSpeed(int speed) {
    throw new UnsupportedOperationException("This operation is not supported!");
  }

  @Override
  public String toString() {
    return this.text; // created for testing purposed
  }

  private String animationModelToString() {
    String animationState = "";
    // builds String by every Feature in model's list of Features. Every Feature separated by
    // new line
    for (Feature f : this.model.getListOfFeatures()) {
      animationState += this.featureToString(f) + "\n";
    }
    return animationState; // returns built String
  }

  private String featureToString(Feature f) {
    String state = "";
    // builds String by every FeatureMotion in the given Feature's list of FeatureMotions
    for (FeatureMotion fm : f.getListOfFeatureMotions()) {
      state = state + "motion " + f.getName() + " ";
      state += this.featureMotionToString(fm);
      state += "\n";
    }
    return state; // returns build String
  }

  private String featureMotionToString(FeatureMotion fm) {
    // returns String representing given FeatureMotion
    return fm.getStartTick() + "\t" +
        this.featureStateToString(fm.getStartState()) + "\t\t" + fm.getEndTick() + "\t"
        + this.featureStateToString(fm.getEndState());
  }

  private String featureStateToString(FeatureState featureState) {
    String state = "";

    state = state + (int) featureState.getLocation().getX() + "\t";
    state = state + (int) featureState.getLocation().getY() + "\t";

    state = state + (int) featureState.getXDimension() + "\t";
    state = state + (int) featureState.getYDimension() + "\t";

    state = state + featureState.getColor().getRed() + "\t";
    state = state + featureState.getColor().getGreen() + "\t";
    state = state + featureState.getColor().getBlue();

    return state; // returns a String representing the given FeatureState
  }
}
