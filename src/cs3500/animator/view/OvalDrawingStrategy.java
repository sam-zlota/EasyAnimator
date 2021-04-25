package cs3500.animator.view;

import java.awt.Graphics;
import java.awt.Color;

import cs3500.animator.model.featurestate.Posn;



/**
 * Represents a drawing strategy for an oval. This implements one method, the draw method,
 * that draws an oval.
 */
public class OvalDrawingStrategy implements DrawingStrategy {

  @Override
  public void draw(Graphics g, int xDim, int yDim, Posn loc, Color col) {
    g.setColor(col);
    g.fillOval((int) loc.getX(), (int) loc.getY(), xDim, yDim);
  }
}