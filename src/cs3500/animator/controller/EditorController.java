package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs3500.animator.model.animationmodel.AnimationModel;
import cs3500.animator.model.feature.EllipseFeature;
import cs3500.animator.model.feature.Feature;
import cs3500.animator.model.feature.RectangleFeature;
import cs3500.animator.model.featurestate.FeatureState;
import cs3500.animator.view.IEditorView;

/**
 * Represents a controller for an editable animation. This class provides a means of
 * communication between the view and the model. All user inputs are handled
 * in this class.
 */
public class EditorController implements IController, ActionListener,
    ListSelectionListener {

  IEditorView view;
  AnimationModel model;
  private String shapeTypeToAdd;

  /**
   * Constructor for EditorController class. It accepts a view and a model.
   *
   * @param view represents a view object.
   * @param model represents an AnimationModel object.
   */
  public EditorController(IEditorView view, AnimationModel model) {
    this.view = view;
    this.model = model;
    this.shapeTypeToAdd = "";
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "pauseResume":
        this.view.pauseOrResume();
        break;
      case "startRestart":
        System.out.println("here");
        this.view.startOrRestart();
        break;
      case "enableDisableLooping":
        this.view.enableDisableLooping();
        break;
      case "increase":
        this.view.increaseSpeed();
        break;
      case "decrease":
        this.view.decreaseSpeed();
        break;
      case "addShape":
        Feature toAdd = null;
        if (this.shapeTypeToAdd.equals("Rectangle")) {
          toAdd = new RectangleFeature(this.view.getShapeName());
          this.model.addShape(toAdd);
        }
        if (this.shapeTypeToAdd.equals("Ellipse")) {
          toAdd = new EllipseFeature(this.view.getShapeName());
          this.model.addShape(toAdd);
        }
        break;
      case "removeShape":
        this.model.removeShape(this.view.getSelectedShape());
        this.view.removeShapeFromList(this.view.getSelectedShape());
        break;
      case "add rect":
        this.shapeTypeToAdd = "Rectangle";
        break;
      case "add ellipse":
        this.shapeTypeToAdd = "Ellipse";
        break;
      case "add keyframe":
        String nameA = this.view.getSelectedShape();
        FeatureState kfA = this.view.getKeyFrameToAdd();
        int tA = this.view.getTickToAddKeyFrame();
        this.model.addKeyFrame(nameA, kfA, tA);
        this.view.setKeyFrameList(this.view.getSelectedShape());
        break;
      case "remove keyFrame":
        String nameR = this.view.getSelectedShape();
        int tR = this.view.getTickToRemoveKeyFrame();
        this.model.removeKeyFrame(nameR, tR);
        this.view.setKeyFrameList(this.view.getSelectedShape());
        break;
      case "edit keyFrame":
        String nameE = this.view.getSelectedShape();
        FeatureState kfE = this.view.getKeyFrameToAdd();
        int tE = this.view.getTickToEditKeyFrame();
        this.model.replaceKeyFrame(nameE, kfE, tE);
        this.view.setKeyFrameList(this.view.getSelectedShape());
        break;
      default:
        break;
    }
  }

  @Override
  public void giveControl() {
    this.view.play();
    this.view.setActionListener(this);
    this.view.setSelectionListener(this);
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    if (this.view.isAKeyFrameSelected()) {
      this.view.fillKeyFrameValues();
    }

    if (this.view.isANewShapeSelected()) {
      this.view.setKeyFrameList(this.view.getSelectedShape());
    }

  }
}
