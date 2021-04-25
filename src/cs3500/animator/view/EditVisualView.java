package cs3500.animator.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;

import cs3500.animator.model.feature.Feature;
import cs3500.animator.model.featurestate.FeatureState;
import cs3500.animator.model.featurestate.OvalFeatureState;
import cs3500.animator.model.featurestate.RectangleFeatureState;

import static java.lang.Integer.parseInt;

/**
 * Represents class for EditVisualView. This class extends VisualView and implements IEditorView
 * interface. This class allows editing functionality in the model to work in the Graphical
 * User Interface.
 */
public class EditVisualView extends VisualView implements IEditorView {

  private JPanel mainPanel;

  //playback controls
  private JPanel buttonPanel; //panel to hold playback buttons
  private JToggleButton pauseResumeButton; //button for toggling pause/resume
  private JButton startRestartButton; //button for starting and restarting
  private JToggleButton enableDisableLoopingButton; //button to toggle enable/disable looping
  private JButton increaseSpeedButton; //button to increase speed
  private JButton decreaseSpeedButton; //button to decrease speed
  private JLabel speedLabel; //label to display speed

  //panel for editing
  private JPanel editingPanel; //panel with editing features

  //panel to add shapes contained within editing panel
  //private JPanel addShapePanel;
  private JButton addShapeButton; //button to add shapes
  private JRadioButton rectRadioButton;
  private JRadioButton ellipseRadioButton;
  private JTextField shapeName;


  //panel to remove/edit shapes within editing panel
  private JButton removeKeyFrameButton;
  private JButton editKeyFrameButton;
  private JButton removeShapeButton;
  private JList<String> shapeList;
  private JButton editShapeButton;
  private JList<String> keyFrameList;


  private boolean isPaused; // true if animation is paused
  private boolean hasStarted; // true if animation has started
  private boolean isLooping; //true if looping enabled

  DefaultListModel<String> m;
  DefaultListModel<String> dataForListOfKeyFrames;
  private JButton addKeyFrameButton;

  //text fields for keyframe specification
  private JTextArea tFieldAdd;
  private JTextArea xFieldAdd;
  private JTextArea yFieldAdd;
  private JTextArea wFieldAdd;
  private JTextArea hFieldAdd;
  private JTextArea rFieldAdd;
  private JTextArea bFieldAdd;
  private JTextArea gFieldAdd;

  //stores the previously selected key frame and shape
  //to be used to determine if a new one is selected
  private String prevSelectedKeyFrame;
  private String prevSelectedShape;

  //stores the tick of the keyframe being edited
  private int tickOfEditedKeyframe;

  /**
   * Empty Constructor for EditVisualView class.
   */
  public EditVisualView() {
    super();
    this.mainPanel = new JPanel();
    this.mainPanel.setLayout(new BorderLayout());
    this.constructButtonPanel();
    this.mainPanel.add(this.buttonPanel, BorderLayout.WEST);
    this.setPreferredSize(new Dimension(1000, 1000));
    this.isPaused = true; //default paused so user can start animation
    this.isLooping = true; //default enabled looping
    this.prevSelectedKeyFrame = "";
    this.prevSelectedShape = "";
  }

  private void constructEditingPanel() {
    this.editingPanel = new JPanel();

    this.editingPanel.setLayout(new BoxLayout(this.editingPanel, BoxLayout.Y_AXIS));
    this.editingPanel.setPreferredSize(new Dimension(300, 1000));

    this.editingPanel.setBorder(BorderFactory.createTitledBorder("Editing Panel"));

    this.constructAddShapePanel();

    //Selection list for shapes
    JPanel shapeListPanel = new JPanel();
    shapeListPanel.setBorder(BorderFactory.createTitledBorder("Select Shape:"));
    shapeListPanel.setLayout(new GridLayout(8, 1));
    this.editingPanel.add(shapeListPanel);

    this.m = new DefaultListModel<>();
    for (Feature f : this.model.getListOfFeatures()) {
      this.m.addElement(f.getName());
    }

    this.shapeList = new JList<>(this.m);
    this.shapeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    shapeListPanel.add(new JScrollPane(this.shapeList));

    this.removeShapeButton = new JButton("REMOVE shape");
    this.removeShapeButton.setActionCommand("removeShape");
    shapeListPanel.add(this.removeShapeButton);

    JPanel keyFramePanelAdd = keyFramePanelAdd = new JPanel();
    keyFramePanelAdd.setLayout(new GridLayout(2, 4));
    keyFramePanelAdd.setBorder(BorderFactory.createTitledBorder("new keyframe:"));
    keyFramePanelAdd.setSize(new Dimension(300, 300));
    this.tFieldAdd = new JTextArea(1, 1);
    this.xFieldAdd = new JTextArea(1, 1);
    this.yFieldAdd = new JTextArea(1, 1);
    this.wFieldAdd = new JTextArea(1, 1);
    this.hFieldAdd = new JTextArea(1, 1);
    this.rFieldAdd = new JTextArea(1, 1);
    this.gFieldAdd = new JTextArea(1, 1);
    this.bFieldAdd = new JTextArea(1, 1);
    this.tFieldAdd.setBorder(BorderFactory.createTitledBorder("t"));
    this.xFieldAdd.setBorder(BorderFactory.createTitledBorder("x"));
    this.yFieldAdd.setBorder(BorderFactory.createTitledBorder("y"));
    this.wFieldAdd.setBorder(BorderFactory.createTitledBorder("w"));
    this.hFieldAdd.setBorder(BorderFactory.createTitledBorder("h"));
    this.rFieldAdd.setBorder(BorderFactory.createTitledBorder("r"));
    this.gFieldAdd.setBorder(BorderFactory.createTitledBorder("g"));
    this.bFieldAdd.setBorder(BorderFactory.createTitledBorder("b"));

    keyFramePanelAdd.add(this.tFieldAdd);
    keyFramePanelAdd.add(this.xFieldAdd);
    keyFramePanelAdd.add(this.yFieldAdd);
    keyFramePanelAdd.add(this.wFieldAdd);
    keyFramePanelAdd.add(this.hFieldAdd);
    keyFramePanelAdd.add(this.rFieldAdd);
    keyFramePanelAdd.add(this.gFieldAdd);
    keyFramePanelAdd.add(this.bFieldAdd);
    shapeListPanel.add(keyFramePanelAdd);

    this.addKeyFrameButton = new JButton("ADD keyframe to shape");
    this.addKeyFrameButton.setActionCommand("add keyframe");
    shapeListPanel.add(this.addKeyFrameButton);

    JPanel keyFrameListPanel = new JPanel();
    keyFrameListPanel.setBorder(BorderFactory.createTitledBorder("select keyframe:"));
    keyFrameListPanel.setLayout(new BoxLayout(keyFrameListPanel, BoxLayout.X_AXIS));

    this.dataForListOfKeyFrames = new DefaultListModel<>();

    this.keyFrameList = new JList<>(dataForListOfKeyFrames);
    this.keyFrameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    shapeListPanel.add(keyFrameListPanel);

    keyFrameListPanel.add(new JScrollPane(this.keyFrameList));

    this.removeKeyFrameButton = new JButton("REMOVE keyframe from shape");
    this.removeKeyFrameButton.setActionCommand("remove keyFrame");
    shapeListPanel.add(this.removeKeyFrameButton);

    this.editKeyFrameButton = new JButton("EDIT keyframe from shape");
    this.editKeyFrameButton.setActionCommand("edit keyFrame");
    shapeListPanel.add(this.editKeyFrameButton);

    JScrollPane ePane = new JScrollPane(this.editingPanel);
    this.mainPanel.add(ePane, BorderLayout.EAST);


  }

  private void constructAddShapePanel() {
    JPanel addShapePanel;
    addShapePanel = new JPanel();
    addShapePanel.setMinimumSize(new Dimension(300, 200));

    addShapePanel.setBorder(BorderFactory.createTitledBorder("ADD new shape"));

    ButtonGroup rGroup1 = new ButtonGroup();

    this.rectRadioButton = new JRadioButton("Rectangle");
    this.ellipseRadioButton = new JRadioButton("Ellipse");

    this.rectRadioButton.setActionCommand("add rect");
    this.ellipseRadioButton.setActionCommand("add ellipse");

    rGroup1.add(this.rectRadioButton);
    rGroup1.add(this.ellipseRadioButton);
    addShapePanel.add(this.rectRadioButton);
    addShapePanel.add(this.ellipseRadioButton);

    this.shapeName = new JTextField(10);
    shapeName.setBorder(BorderFactory.createTitledBorder("shape name:"));
    addShapePanel.add(shapeName);

    this.addShapeButton = new JButton("ADD");
    this.addShapeButton.setActionCommand("addShape");
    addShapePanel.add(this.addShapeButton);

    this.editingPanel.add(addShapePanel);
  }

  private void constructButtonPanel() {
    this.buttonPanel = new JPanel();
    this.buttonPanel.setLayout(new GridLayout(20, 1)); //7 buttons
    this.buttonPanel.setPreferredSize(new Dimension(200, 400));

    //adds label to button panel to function as title
    JLabel title = new JLabel();
    title.setText("\t\t\tPlayback Controls:");
    this.buttonPanel.add(title);

    //button for starting animation initially and restarting once started
    this.startRestartButton = new JButton("click to START");
    this.startRestartButton.setActionCommand("startRestart");
    this.buttonPanel.add(this.startRestartButton);

    //toggle button to toggle between enabling/disabling looping
    this.enableDisableLoopingButton = new JToggleButton("click to DISABLE looping", false);
    this.enableDisableLoopingButton.setActionCommand("enableDisableLooping");
    this.buttonPanel.add(this.enableDisableLoopingButton);

    //toggle button to toggle between pausing/resuming animation
    this.pauseResumeButton = new JToggleButton("click to PAUSE", false);
    this.pauseResumeButton.setActionCommand("pauseResume");
    this.buttonPanel.add(this.pauseResumeButton);

    //label to add to button panel to display speed
    this.speedLabel = new JLabel();
    speedLabel.setText("\t\t\tSpeed: " + this.speed);
    this.buttonPanel.add(speedLabel);

    //button to increase speed
    this.increaseSpeedButton = new JButton("click to INCREASE speed");
    this.increaseSpeedButton.setActionCommand("increase");
    this.buttonPanel.add(this.increaseSpeedButton);

    //button to decrease speed
    this.decreaseSpeedButton = new JButton("click to DECREASE speed");
    this.decreaseSpeedButton.setActionCommand("decrease");
    this.buttonPanel.add(this.decreaseSpeedButton);
  }

  @Override
  public void play() {
    this.aPanel = new AnimationPanel(this.model.getWidth(), this.model.getHeight());
    this.sp = new JScrollPane(this.aPanel);
    this.setTitle("Animation");
    this.setSize(this.model.getWidth(), this.model.getHeight());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.mainPanel.add(sp, BorderLayout.CENTER);
    //inherited wrong timer from super class, so resets timer
    this.resetTimer();
    //sets all buttons to invisible except start button
    //these buttons cannot be used until user starts animation
    this.pauseResumeButton.setVisible(false);
    this.enableDisableLoopingButton.setVisible(false);
    this.increaseSpeedButton.setVisible(false);
    this.decreaseSpeedButton.setVisible(false);
    this.speedLabel.setVisible(false);

    this.constructEditingPanel();
    this.add(mainPanel);

    this.setVisible(true);

    this.pack();

  }

  //to be used when speed is updated
  private void resetTimer() {
    this.timer.cancel();
    this.timer = new Timer();
    this.timer.scheduleAtFixedRate(new PausableRefresh(this),
        0, 1000 / this.speed);
  }

  //called by start/restart button
  @Override
  public void startOrRestart() {
    if (this.hasStarted) {
      //if animation running, then restart animation by setting tick to zero
      this.tick = 0;
      if (this.isPaused) {
        //if animation restarted while paused, then unpause
        this.isPaused = false;
        this.pauseResumeButton.setText("click to PAUSE");
      }
    } else {
      //start animation upon click of start button
      //start button becomes restart button
      //all other buttons and labels become visible in button panel
      this.startRestartButton.setText("click to RESTART");
      this.pauseResumeButton.setVisible(true);
      this.enableDisableLoopingButton.setVisible(true);
      this.increaseSpeedButton.setVisible(true);
      this.decreaseSpeedButton.setVisible(true);
      this.speedLabel.setVisible(true);

      this.hasStarted = true; //animation has begun
      this.isPaused = false; //unpause to start animation
    }
  }

  //to be called when pause/resume button is clicked
  @Override
  public void pauseOrResume() {
    if (this.isPaused) {
      this.pauseResumeButton.setText("click to PAUSE");

    } else {
      this.pauseResumeButton.setText("click to RESUME");
    }
    //set to opposite
    this.isPaused = !this.isPaused;
  }

  //to be called when enable/disable looping button is clicked
  @Override
  public void enableDisableLooping() {
    if (this.isLooping) {
      this.enableDisableLoopingButton.setText("click to ENABLE looping");
    } else {
      this.enableDisableLoopingButton.setText("click to DISABLE looping");
    }
    //set to opposite
    this.isLooping = !this.isLooping;
  }

  //to be called when increase speed button is clicked
  @Override
  public void increaseSpeed() {
    this.setSpeed(this.speed + 50);
  }

  //to be called when decrease speed button is clicked
  @Override
  public void decreaseSpeed() {
    // to ensure that speed can not be zero or negative
    if (this.speed > 50) {
      this.setSpeed(this.speed - 50);
    }
  }

  @Override
  public void setSpeed(int speed) {
    this.speed = speed;
    //update speed label
    this.speedLabel.setText("\t\t\tSpeed: " + this.speed);
    //reset timer to correct speed
    this.resetTimer();
  }

  @Override
  protected void setPanel() {
    //functions similarly to inherited setPanel but allows for looping
    if (this.model.isVisible(this.tick)) {
      super.setPanel();
    } else if (this.isLooping) {
      this.tick = 0;
    } else {
      this.isPaused = true;
    }
  }

  //to be called by controller
  @Override
  public void setActionListener(ActionListener listener) {
    //playback buttons
    this.pauseResumeButton.addActionListener(listener);
    this.startRestartButton.addActionListener(listener);
    this.enableDisableLoopingButton.addActionListener(listener);
    this.increaseSpeedButton.addActionListener(listener);
    this.decreaseSpeedButton.addActionListener(listener);

    //editing buttons
    //adding shape buttons
    this.addShapeButton.addActionListener(listener);
    this.removeShapeButton.addActionListener(listener);
    this.rectRadioButton.addActionListener(listener);
    this.ellipseRadioButton.addActionListener(listener);
    this.shapeName.addActionListener(listener);

    this.removeKeyFrameButton.addActionListener(listener);
    this.addKeyFrameButton.addActionListener(listener);
    this.editKeyFrameButton.addActionListener(listener);


  }


  @Override
  public void setSelectionListener(ListSelectionListener listener) {
    this.shapeList.addListSelectionListener(listener);
    this.keyFrameList.addListSelectionListener(listener);
  }

  @Override
  public String getShapeName() {
    String command = this.shapeName.getText();
    this.shapeName.setText("");
    this.m.addElement(command);
    return command;
  }

  @Override
  public String getSelectedShape() {
    return this.shapeList.getSelectedValue();
  }

  public void removeShapeFromList(String name) {
    this.m.removeElement(name);
  }

  @Override
  public FeatureState getKeyFrameToAdd() {
    String type = "";

    int x = parseInt(this.xFieldAdd.getText());
    int y = parseInt(this.yFieldAdd.getText());
    int w = parseInt(this.wFieldAdd.getText());
    int h = parseInt(this.hFieldAdd.getText());
    int r = parseInt(this.rFieldAdd.getText());
    int g = parseInt(this.gFieldAdd.getText());
    int b = parseInt(this.bFieldAdd.getText());

    //default type rect
    FeatureState toReturn = new RectangleFeatureState(x, y, w, h, r, g, b);

    for (Feature f : this.model.getListOfFeatures()) {
      if (f.getName().equals(this.getSelectedShape())) {
        type = f.getType();
      }
    }

    if (type.equals("ellipse")) {
      toReturn = new OvalFeatureState(x, y, w, h, r, g, b);

    }
    this.setPanel();
    return toReturn;
  }

  @Override
  public int getTickToAddKeyFrame() {
    return parseInt(this.tFieldAdd.getText());
  }

  @Override
  public void setKeyFrameList(String shapeName) {
    this.dataForListOfKeyFrames.clear();
    this.prevSelectedShape = this.getSelectedShape();
    Feature currentFeature = null;
    for (Feature f : this.model.getListOfFeatures()) {
      if (f.getName().equals(shapeName)) {
        currentFeature = f;
      }
    }

    String toAdd = "";
    for (Map.Entry<Integer, FeatureState> entry : currentFeature.getKeyFrameMap().entrySet()) {
      toAdd = String.format("t %d  x %d  y %d  w %d  h %d  r %d  g %d  b %d",
          entry.getKey(), (int) entry.getValue().getLocation().getX(),
          (int) entry.getValue().getLocation().getY(),
          (int) entry.getValue().getXDimension(),
          (int) entry.getValue().getYDimension(),
          entry.getValue().getColor().getRed(),
          entry.getValue().getColor().getGreen(),
          entry.getValue().getColor().getBlue());
      this.dataForListOfKeyFrames.addElement(toAdd);
    }

  }

  @Override
  public int getTickToRemoveKeyFrame() {
    String nameFS = this.keyFrameList.getSelectedValue();
    Scanner s = new Scanner(nameFS.substring(nameFS.indexOf("t") + 1));
    int t = s.nextInt();
    this.setKeyFrameList(this.shapeList.getSelectedValue());
    return t;
  }

  @Override
  public boolean isAKeyFrameSelected() {
    if (this.keyFrameList.getSelectedValue() == null) {
      return false;
    }
    return !this.keyFrameList.getSelectedValue().equalsIgnoreCase(this.prevSelectedKeyFrame);
  }

  @Override
  public void fillKeyFrameValues() {
    if (this.keyFrameList.getSelectedValue() != null) {
      String kf = this.keyFrameList.getSelectedValue();
      this.prevSelectedKeyFrame = kf;

      this.tickOfEditedKeyframe = new Scanner(kf.substring(kf.indexOf("t") + 1)).nextInt();

      String x = String.valueOf(new Scanner(kf.substring(kf.indexOf("x") + 1)).nextInt());
      String y = String.valueOf(new Scanner(kf.substring(kf.indexOf("y") + 1)).nextInt());
      String w = String.valueOf(new Scanner(kf.substring(kf.indexOf("w") + 1)).nextInt());
      String h = String.valueOf(new Scanner(kf.substring(kf.indexOf("h") + 1)).nextInt());
      String r = String.valueOf(new Scanner(kf.substring(kf.indexOf("r") + 1)).nextInt());
      String g = String.valueOf(new Scanner(kf.substring(kf.indexOf("g") + 1)).nextInt());
      String b = String.valueOf(new Scanner(kf.substring(kf.indexOf("b") + 1)).nextInt());

      this.tFieldAdd.setText("XXX");
      this.xFieldAdd.setText(x);
      this.yFieldAdd.setText(y);
      this.wFieldAdd.setText(w);
      this.hFieldAdd.setText(h);
      this.rFieldAdd.setText(r);
      this.gFieldAdd.setText(g);
      this.bFieldAdd.setText(b);
    }
  }

  @Override
  public boolean isANewShapeSelected() {
    if (this.shapeList.isSelectionEmpty()) {
      return false;
    }
    return !this.prevSelectedShape.equals(this.shapeList.getSelectedValue());
  }

  @Override
  public int getTickToEditKeyFrame() {
    return this.tickOfEditedKeyframe;
  }


  //to be called on every tick of timer
  //if animation is running, will repaint and update panel and increment tick
  //if animation is paused, will do nothing
  private class PausableRefresh extends TimerTask {

    EditVisualView view;

    public PausableRefresh(EditVisualView view) {
      this.view = view;
    }

    @Override
    public void run() {
      if (!this.view.isPaused) {
        this.view.repaint(); // paints JFrame
        this.view.setPanel(); // sets panel in JFrame
        this.view.incrementTick(); // increases tick by one
      }
    }
  }

}


