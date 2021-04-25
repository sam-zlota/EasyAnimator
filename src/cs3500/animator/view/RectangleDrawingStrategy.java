package cs3500.animator.view;

import cs3500.animator.model.featurestate.Posn;
import java.awt.Color;
import java.awt.Graphics;




/**
 * Represents a drawing strategy for a rectangle. This implements one method, the draw method,
 * that draws a rectangle.
 */
public class RectangleDrawingStrategy implements DrawingStrategy {

  @Override
  public void draw(Graphics g, int xDim, int yDim, Posn loc, Color col) {
    g.setColor(col);
    g.fillRect((int) loc.getX(), (int) loc.getY(), xDim, yDim);
  }
}
