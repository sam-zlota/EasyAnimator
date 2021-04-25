package cs3500.animator.view;

import cs3500.animator.model.featurestate.Posn;
import java.awt.Color;
import java.awt.Graphics;


/**
 * This interface represents a drawing strategy. A drawing strategy object contains one method that
 * is used to draw a certain shape. Every Shape has a drawing strategy that is used by the
 * VisualView to draw it.
 */
public interface DrawingStrategy {

  /**
   * This method is onvoked by the VisualView to draw a given shape with the given specifications.
   *
   * @param g    the Graphics object to draw a shape onto
   * @param xDim the x dimension of the shape
   * @param yDim the y dimension of the shape
   * @param loc  the position of the shape
   * @param col  the color of the shape
   */
  void draw(Graphics g, int xDim, int yDim, Posn loc, Color col);
}
