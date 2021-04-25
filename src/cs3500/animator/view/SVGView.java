package cs3500.animator.view;

import java.io.IOException;
import java.io.Writer;
import java.util.Comparator;
import java.util.List;

import cs3500.animator.model.animationmodel.AnimationModel;
import cs3500.animator.model.feature.Feature;
import cs3500.animator.model.featuremotion.AttributeName;
import cs3500.animator.model.featuremotion.FeatureMotion;

/**
 * Represents a SVGView. This class implements IView and writes SVG instructions to a file, which
 * can then be used to run the animation in a web browser.
 */
public class SVGView implements IView {

  AnimationModel model; // represents animation model
  Writer out; // represents writer object
  int speed; // represents speed in ticks per second
  String text; // stores SVGView code as a String

  /**
   * This constructor builds an SVGView instance based on the specified animation model, Writer
   * object, and speed.
   *
   * @param model the model to view
   * @param out   the writer object to output to
   * @param speed speed of animation
   */
  public SVGView(AnimationModel model, Writer out, int speed) {
    this.model = model;
    this.out = out;
    this.speed = speed;
    this.text = "";
  }

  /**
   * Empty constructor for SVGView class. Sets fields to null or 0.
   */
  public SVGView() {
    this.model = null;
    this.out = null;
    this.speed = 0;
    this.text = "";
  }

  @Override
  public void play() {
    // creates header of SVG code with width and height data
    String svg = String.format("<svg width=\"%d\" height=\"%d\" version=\"1.1\"\n" +
            "     xmlns=\"http://www.w3.org/2000/svg\">\n", this.model.getWidth(),
        this.model.getHeight());

    // stores List of Features to be sorted in new List
    List<Feature> toSort = this.model.getListOfFeatures();

    // sorts Features by the motionNumber of the first
    // FeatureMotion that occurs (a motionNumber represents the order that the FeatureMotion
    // was inputted)
    Comparator<Feature> compMotionNumbers = Comparator
        .comparingInt((Feature a) -> a.getListOfFeatureMotions().get(0).getMotionNumber());
    toSort.sort(compMotionNumbers);
    // constructs svg String by getting SVG String for each Feature in toSort List
    for (Feature f : toSort) {
      svg += (this.getSVGForFeature(f));
    }

    svg += "\n</svg>"; // adds closing line in SVG code

    try {
      this.text = svg; // sets text field to built up svg String holding full SVG code
      out.flush();
      out.write(text);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setOut(Writer out) {
    this.out = out; // sets out field to provided writer
  }

  @Override
  public void setSpeed(int speed) {
    this.speed = speed; // sets speed field to provided value
  }

  @Override
  public void setModel(AnimationModel model) {
    this.model = model; // sets model field to given model
  }

  @Override
  public String toString() {
    return this.text; // returns text field holding SVG code as a String
  }

  // outputs SVG code for given Feature in a String
  private String getSVGForFeature(Feature f) {
    String head = "";
    String tail = "";

    String name = f.getName();

    int xPos = (int) f.getListOfFeatureMotions().get(0).getStartState().getLocation().getX();
    int yPos = (int) f.getListOfFeatureMotions().get(0).getStartState().getLocation().getY();
    int xDim = (int) f.getListOfFeatureMotions().get(0).getStartState().getXDimension();
    int yDim = (int) f.getListOfFeatureMotions().get(0).getStartState().getYDimension();
    int r = (int) f.getListOfFeatureMotions().get(0).getStartState().getColor().getRed();
    int g = (int) f.getListOfFeatureMotions().get(0).getStartState().getColor().getGreen();
    int b = (int) f.getListOfFeatureMotions().get(0).getStartState().getColor().getBlue();

    // tests type of Feature
    switch (f.getType()) {
      case "rect":
        head = String.format(
            "<rect id=\"%s\" x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" fill=\"rgb(%d,%d,%d)\""
                + " visibility=\"hidden\" >\n",
            name, xPos, yPos, xDim, yDim, r, g, b);
        tail = "</rect>";
        head += String.format("<set attributeName=\"visibility\" to=\"visible\" begin=\"%dms\" />",
            f.getAppearAt() * 1000 / speed);
        break;
      case "ellipse":
        head = String.format(
            "<ellipse id=\"%s\" cx=\"%d\" cy=\"%d\" rx=\"%d\" ry=\"%d\" fill=\"rgb(%d,%d,%d)\" "
                + "visibility=\"hidden\" >\n",
            name, xPos + xDim / 2, yPos + yDim / 2, xDim / 2, yDim / 2, r, g, b);
        tail = "</ellipse>";
        head += String.format("<set attributeName=\"visibility\" to=\"visible\" begin=\"%dms\" />",
            f.getAppearAt() * 1000 / speed);
        break;
      default:
        break;
    }

    String svgFeat = head;

    for (FeatureMotion fm : f.getListOfFeatureMotions()) {
      for (AttributeName a : fm.getAttributeTypes()) {
        // adds SVG for FeatureMotion to svgFeat String
        svgFeat += this.getSVGForFeatureMotion(fm, a, f.getType());
      }
    }
    svgFeat = svgFeat + "\n" + tail + "\n\n";
    return svgFeat; // returns formatted String
  }

  private String getSVGForFeatureMotion(FeatureMotion fm, AttributeName a, String type) {
    int dur = ((fm.getEndTick() - fm.getStartTick()) * 1000 / this.speed);
    int begin = fm.getStartTick() * 1000 / this.speed;
    String attributeName = "";
    int from = 0;
    int to = 0;
    String svgFeatMot;
    switch (a) {
      case XDim:
        if (type.equals("rect")) {
          attributeName = "width";
          from = (int) fm.getStartState().getXDimension();
          to = (int) fm.getEndState().getXDimension();
        } else if (type.equals("ellipse")) {
          attributeName = "rx";
          from = (int) fm.getStartState().getXDimension();
          to = (int) fm.getEndState().getXDimension();
        }
        svgFeatMot = String.format(
            "    <animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" attributeName=\"%s\" "
                + "from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
            begin, dur, attributeName, from, to);
        break;
      case YDim:
        if (type.equals("rect")) {
          attributeName = "height";
          from = (int) fm.getStartState().getYDimension();

          to = (int) fm.getEndState().getYDimension();
        } else if (type.equals("ellipse")) {
          attributeName = "ry";
          from = (int) fm.getStartState().getYDimension();
          to = (int) fm.getEndState().getYDimension();
        }
        svgFeatMot = String.format(
            "    <animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" attributeName=\"%s\" "
                + "from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
            begin, dur, attributeName, from, to);
        break;
      case XPos:
        if (type.equals("ellipse")) {
          attributeName = "cx";
          from = (int) ((int) fm.getStartState().getLocation().getX()
              + fm.getStartState().getXDimension() / 2);
          to = (int) ((int) fm.getEndState().getLocation().getX()
              + fm.getEndState().getXDimension() / 2);
        } else if (type.equals("rect")) {
          attributeName = "x";
          from = (int) fm.getStartState().getLocation().getX();
          to = (int) fm.getEndState().getLocation().getX();
        }

        svgFeatMot = String.format(
            "    <animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" attributeName=\"%s\" "
                + "from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
            begin, dur, attributeName, from, to);
        break;
      case YPos:
        if (type.equals("ellipse")) {
          attributeName = "cy";
          from = (int) ((int) fm.getStartState().getLocation().getY()
              + fm.getStartState().getYDimension() / 2);
          to = (int) ((int) fm.getEndState().getLocation().getY()
              + fm.getEndState().getYDimension() / 2);
        } else if (type.equals("rect")) {
          attributeName = "y";
          from = (int) fm.getStartState().getLocation().getY();
          to = (int) fm.getEndState().getLocation().getY();
        }
        svgFeatMot = String.format(
            "    <animate attributeType=\"xml\" begin=\"%dms\" dur=\"%dms\" attributeName=\"%s\" "
                + "from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
            begin, dur, attributeName, from, to);
        break;
      case R:
      case G:
      case B:
        svgFeatMot = String.format(
            "<animate attributeType=\"XML\" attributeName=\"fill\" from=\"rgb(%d,%d,%d)\" "
                + "to=\"rgb(%d,%d,%d)\" begin = \"%dms\" dur = \"%dms\" fill=\"freeze\"/>",
            fm.getStartState().getColor().getRed(), fm.getStartState().getColor().getGreen(),
            fm.getStartState().getColor().getBlue(),
            fm.getEndState().getColor().getRed(), fm.getEndState().getColor().getGreen(),
            fm.getEndState().getColor().getBlue(), begin, dur);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + a);
    }
    return svgFeatMot + "\n";
  }
}