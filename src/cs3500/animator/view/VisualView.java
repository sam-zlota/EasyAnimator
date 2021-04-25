package cs3500.animator.view;

import java.awt.Color;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JFrame;


import cs3500.animator.model.animationmodel.AnimationModel;
import cs3500.animator.model.feature.Feature;
import cs3500.animator.model.featuremotion.FeatureMotion;
import cs3500.animator.model.featurestate.FeatureState;
import cs3500.animator.model.featurestate.Posn;

/**
 * This represents a Visual view of an Animation. This class implements IView and shows a visual
 * view of the animation using the Swing library.
 */
public class VisualView extends JFrame implements IView {

  protected int speed; // represents speed of animation in ticks per second
  protected AnimationModel model; // represents model for animation
  protected AnimationPanel aPanel; // holds what is to be displayed inside the JFrame
  protected JScrollPane sp; // an object to implement scroll functionality in JFrame
  protected Timer timer; // an object for managing time
  protected int tick; // represents the ticks of an animation


  /**
   * Empty constructor to be used by view factor.
   */
  public VisualView() {
    super();
    this.timer = new Timer();
    this.tick = 0;
  }

  @Override
  public void play() {
    this.aPanel = new AnimationPanel(this.model.getWidth(), this.model.getHeight());
    this.sp = new JScrollPane(this.aPanel);
    this.setTitle("Animation");
    this.setSize(this.model.getWidth(), this.model.getHeight());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.add(sp, BorderLayout.CENTER);
    this.setVisible(true);
    this.pack();

    this.timer.scheduleAtFixedRate(new Refresh(this), 0, 1000 / this.speed);
  }

  @Override
  public void setModel(AnimationModel model) {
    this.model = model; // sets model field to given model

  }

  @Override
  public void setOut(Writer out) {
    // this method is empty because this is the visual view and there is no ouput file
  }

  @Override
  public void setSpeed(int speed) {
    this.speed = speed; // sets speed field to given value
  }

  // handles interpolation of FeatureMotions by determining the intermediate state at the current
  // tick
  private FeatureState getIntermediateStateAtCurrentTick(FeatureMotion fm) {

    FeatureState start = fm.getStartState();
    FeatureState end = fm.getEndState();

    // getting changes between start and end fields
    // if there is a change, add the type of attribute changed to attributesChanged List

    // change in r attribute over the entire interval
    double deltaR = end.getColor().getRed() - start.getColor().getRed();

    // change in b attribute over the entire interval
    double deltaB = end.getColor().getBlue() - start.getColor().getBlue();

    // change in g attribute over the entire interval
    double deltaG = end.getColor().getGreen() - start.getColor().getGreen();

    // change in xDim attribute over the entire interval
    double deltaXDimension = end.getXDimension() - start.getXDimension();

    // change in yDim attribute over the entire interval
    double deltaYDimension = end.getYDimension() - start.getYDimension();

    // change in XPos attribute over the entire interval
    double deltaXPos = end.getLocation().getX() - start.getLocation().getX();

    // change in YPos attribute over the entire interval
    double deltaYPos = end.getLocation().getY() - start.getLocation().getY();

    // change in time over the entire interval
    double deltaTime = fm.getEndTick() - fm.getStartTick();

    // each intermediate state of this motion represents a portion of the total change
    // deltaTime-1 represents the number of intermediate states
    double multiplier = (this.tick - fm.getStartTick()) / (deltaTime - 1);

    double nextR = Math.ceil(start.getColor().getRed() + (deltaR * multiplier));
    double nextB = Math.ceil(start.getColor().getBlue() + (deltaB * multiplier));
    double nextG = Math.ceil(start.getColor().getGreen() + (deltaG * multiplier));

    double nextXDim = Math.ceil(start.getXDimension() + (deltaXDimension * multiplier));
    double nextYDim = Math.ceil(start.getYDimension() + (deltaYDimension * multiplier));

    double nextXPos = Math.ceil(start.getLocation().getX() + (deltaXPos * multiplier));
    double nextYPos = Math.ceil(start.getLocation().getY() + (deltaYPos * multiplier));

    Color nextColor = new Color((int) nextR, (int) nextG, (int) nextB);
    Posn nextPosn = new Posn(nextXPos, nextYPos);

    // creating FeatureState with correct location, dimension, and color attributes
    FeatureState toReturn = end.factory(nextPosn, nextXDim, nextYDim, nextColor);
    // sets the motionNumber of FeatureState to given FeatureMotion motionNumber
    toReturn.setMotionNumber(fm.getMotionNumber());
    return toReturn;
  }

  // to be called at every tick
  protected void setPanel() {
    // determines if animation is still going on
    if (this.model.isVisible(this.tick)) {
      List<FeatureState> ft = new ArrayList<>();
      for (Feature f : this.model.getListOfFeatures()) {
        // gets the Features that are currently visible
        if (f.isVisible(this.tick)) {
          for (FeatureMotion fm : f.getListOfFeatureMotions()) {
            // gets the FeatureState within the FeatureMotion that is currently occurring at this
            // tick
            if (fm.isVisible(this.tick)) {
              ft.add(this.getIntermediateStateAtCurrentTick(fm));
            }
          }
        }
      }

      // ensures the shapes are drawn in the order they were inputted to have proper
      // layering for shapes that overlap
      Comparator<FeatureState> compMotionNumbers = Comparator
          .comparingInt(FeatureState::getMotionNumber);
      ft.sort(compMotionNumbers);

      this.aPanel.setListOfFeatureStates(ft);
    } else {
      // if animation is over exits program
      System.exit(0);
    }
  }

  // class representing TimerTask to perform at every tick
  protected class Refresh extends TimerTask {

    protected VisualView view;

    // sets VisualView's view field to given field
    public Refresh(VisualView view) {
      this.view = view;
    }

    @Override
    public void run() {
      this.view.repaint(); // paints JFrame
      this.view.setPanel(); // sets panel in JFrame
      this.view.incrementTick(); // increments tick by one
    }

  }

  public void incrementTick() {
    this.tick++; // increments tick by one
  }
}