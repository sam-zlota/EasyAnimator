package cs3500.animator.view;

import cs3500.animator.model.featurestate.FeatureState;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;


/**
 * This class represents a JPanel that will be used in the VisualView class of an animation. This
 * class represents one frame in an animation to be displayed to the JFrame class it is part of.
 */
public class AnimationPanel extends JPanel {

  List<FeatureState> currentFrame;

  /**
   * This constructor builds an AnimationPanel based on its height and width.
   *
   * @param width  the width
   * @param height the height
   */
  public AnimationPanel(int width, int height) {
    super();
    this.currentFrame = new ArrayList<>();
    this.setPreferredSize(new Dimension(width, height));
    this.setBackground(Color.WHITE);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    DrawingStrategy drawingStrategy;
    for (FeatureState shape : currentFrame) {
      drawingStrategy = shape.getDrawingStrategy();
      drawingStrategy
          .draw(g2d, (int) shape.getXDimension(), (int) shape.getYDimension(), shape.getLocation(),
              shape.getColor());
    }
  }

  /**
   * This method sets the List of FeatureState to the next frame.
   *
   * @param next the next list of FeatureState to be displayed
   */
  public void setListOfFeatureStates(List<FeatureState> next) {
    this.currentFrame = next;
  }

}


