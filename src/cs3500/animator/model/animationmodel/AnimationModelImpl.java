package cs3500.animator.model.animationmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.animator.model.feature.EllipseFeature;
import cs3500.animator.model.feature.Feature;
import cs3500.animator.model.feature.RectangleFeature;
import cs3500.animator.model.featuremotion.FeatureMotion;
import cs3500.animator.model.featurestate.FeatureState;
import cs3500.animator.util.AnimationBuilder;

/**
 * A class that implements the AnimationModel interface and all the necessary operations as
 * specified. One instance of this class repsents an entire animation. This class encapsulates an
 * animation by storing a list of Features which represent all the shapes that are part of the
 * animation. This class also handles the bounds and dimensions of the canvas this animation will be
 * displayed on.
 */
public final class AnimationModelImpl implements AnimationModel {

  private List<Feature> featureList; //Features are shapes in our implementation
  private int lastTick;
  private int xBound;
  private int yBound;
  private int width;
  private int height;

  /**
   * Constructs an AnimationModelImpl with a specified parameters.
   *
   * @param featureList List of Features(Shapes)
   * @param xBound      the x bound of the canvas
   * @param yBound      the y bound of the canvas
   * @param width       the width of the canvas
   * @param height      the height of the canvas
   */
  public AnimationModelImpl(List<Feature> featureList, int xBound, int yBound, int width,
      int height) {
    this.featureList = featureList;
    this.xBound = xBound;
    this.yBound = yBound;
    this.width = width;
    this.height = height;
    this.lastTick = 0;
    for (Feature f : this.featureList) {
      //finds the feature that disappears last and sets the last tick of this animation
      //to when that feature disappears
      if (this.lastTick < f.getDisappearAt()) {
        this.lastTick = f.getDisappearAt();
      }
    }
  }

  /**
   * Constructs an AnimationModelImpl with fields set to default values. This constructor is
   * expected to create an empty animation in which motions and shapes will be added in the future.
   */
  public AnimationModelImpl() {
    this.featureList = new ArrayList<>();
  }

  @Override
  public boolean isVisible(int tick) {
    for (Feature f : this.featureList) {
      //finds the feature that disappears last and sets the last tick of this animation
      //to when that feature disappears
      if (this.lastTick < f.getDisappearAt()) {
        this.lastTick = f.getDisappearAt();
      }
    }
    return this.lastTick >= tick;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public List<Feature> getListOfFeatures() {
    return new ArrayList<>(this.featureList);
  }

  @Override
  public void addShape(Feature shape) {
    for (Feature f : this.featureList) {
      if (f.getName().equals(shape.getName())) {
        throw new IllegalArgumentException("shape with this name already exists");
      }
    }
    this.featureList.add(shape);
    if (this.lastTick < shape.getDisappearAt()) {
      this.lastTick = shape.getDisappearAt();
    }
  }

  @Override
  public void removeShape(String shapeName) {
    Feature toRemove = null;
    for (Feature f : this.featureList) {
      if (f.getName().equals(shapeName)) {
        toRemove = f;
      }
    }
    this.featureList.remove(toRemove);
  }

  @Override
  public void addMotion(String name, FeatureMotion motion) {
    for (Feature f : this.featureList) {
      if (f.getName().equals(name)) {
        f.addMotion(motion);
      }
    }
  }

  @Override
  public void removeMotion(String name, FeatureMotion motion) {
    for (Feature f : this.featureList) {
      if (f.getName().equals(name)) {
        f.removeMotion(motion);
      }
    }
  }

  @Override
  public void setWidth(int w) {
    this.width = w;
  }

  @Override
  public void setHeight(int h) {
    this.height = h;
  }

  @Override
  public void setXBound(int xBound) {
    this.xBound = xBound;
  }

  @Override
  public void setYBound(int yBound) {
    this.yBound = yBound;
  }

  @Override
  public void addKeyFrame(String name, FeatureState state, int tick) {
    // determines which Feature is being edited
    for (Feature f : this.getListOfFeatures()) {
      if (f.getName().equals(name)) {
        f.addKeyFrame(state, tick);
      }
    }
  }

  @Override
  public void removeKeyFrame(String name, int tick) {

    // determines which Feature is being edited
    for (Feature f : this.getListOfFeatures()) {
      if (f.getName().equals(name)) {
        f.removeKeyFrame(tick);
      }
    }
  }

  @Override
  public void replaceKeyFrame(String nameE, FeatureState kfE, int tE) {
    this.removeKeyFrame(nameE, tE);
    this.addKeyFrame(nameE, kfE, tE);
  }

  /**
   * This class allows for the user to build an animation using the supplied methods to add shapes
   * and add motions. This class allows for the user to not have to use a constructor.
   */
  public static final class Builder implements AnimationBuilder<AnimationModel> {

    // The key is the Feature to be constructed and the List<FeatureMotion>'s are the corresponding
    // unordered list of FeatureMotions for that Feature.
    private Map<Feature, List<FeatureMotion>> featureMotionMap = new HashMap<>();
    private int xBound;
    private int yBound;
    private int width;
    private int height;

    //we keep track of when a motion is added so that the shapes can be drawn in proper order
    //based on when they were inputted to have proper layering
    private int whenAdded;

    @Override
    public AnimationModel build() {
      for (Map.Entry<Feature, List<FeatureMotion>> entry : featureMotionMap.entrySet()) {
        // all the fields except for name in Feature are set based on the corresponding
        // FeatureMotions in featureMotionMap
        entry.getKey().setAllButName(entry.getValue());
      }

      // return new AnimationModelImpl object with the set of Features in featureMotionMap and
      // other fields in the builder.
      return new AnimationModelImpl(new ArrayList<Feature>(this.featureMotionMap.keySet()),
          this.xBound,
          this.yBound, this.width, this.height);
    }

    @Override
    public AnimationBuilder<AnimationModel> setBounds(int x, int y, int width, int height) {
      this.xBound = x;
      this.yBound = y;
      this.width = width;
      this.height = height;
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> declareShape(String name, String type) {
      for (Feature feature : this.featureMotionMap.keySet()) {
        // make sure the name is unique (the name has not been added to featureList already)
        if (feature.getName().equalsIgnoreCase(name)) {
          throw new IllegalArgumentException("Shape with this name already exists");
        }
      }

      // adds a Feature to the list of Features stored in the featureMotionMap field
      // based on the type of Feature
      switch (type) {
        case "rectangle":
          this.featureMotionMap.put(new RectangleFeature(name), new ArrayList<FeatureMotion>());
          break;
        case "ellipse":
          this.featureMotionMap.put(new EllipseFeature(name), new ArrayList<FeatureMotion>());
          break;
        default:
          throw new IllegalArgumentException("Unsupported type of shape!");
      }
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> addMotion(String name, int t1, int x1, int y1, int w1,
        int h1, int r1, int g1, int b1, int t2, int x2, int y2, int w2, int h2, int r2, int g2,
        int b2) {
      boolean nameDoesNotExist = true;
      for (Feature f : this.featureMotionMap.keySet()) {
        if (f.getName().equals(name)) {
          // add new FeatureMotion to List of FeatureMotions
          // corresponding to Feature with given name
          this.featureMotionMap.get(f)
              .add(f.buildMotion(whenAdded, t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2, w2, h2, r2,
                  g2, b2));
          nameDoesNotExist = false;
          this.whenAdded++;
        }
      }
      if (nameDoesNotExist) {
        throw new IllegalArgumentException("No Shape with this name exists");
      }
      return this;
    }

    @Override
    public AnimationBuilder<AnimationModel> addKeyframe(String name, int t, int x, int y, int w,
        int h, int r, int g, int b) {
      throw new UnsupportedOperationException("Not implemented yet!");
    }
  }

}
