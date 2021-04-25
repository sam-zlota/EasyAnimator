package cs3500.animator.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import cs3500.animator.controller.EditorController;
import cs3500.animator.controller.IController;
import cs3500.animator.controller.SimpleController;
import cs3500.animator.model.animationmodel.AnimationModel;
import cs3500.animator.model.animationmodel.AnimationModelImpl;
import cs3500.animator.view.EditVisualView;
import cs3500.animator.view.IEditorView;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextualView;
import cs3500.animator.view.VisualView;

import static cs3500.animator.util.AnimationReader.parseFile;
import static java.lang.Integer.parseInt;

/**
 * A class to support running Animation from command line. The user will be able to specify what
 * type of view they would like, what file to read from, and optionally the speed and the output
 * file.
 */
public final class Excellence {

  /**
   * The main method to run an Animation from command line. The user must specify an input file,
   * output file, view type, and speed. To specify input file they must type "-in" followed by the
   * name of the file. To specify output file they must type "-out" followed by the name of the
   * file. To specify the view type the user must type "-view" followed by one of: "svg", "visual",
   * or "text". To specify speed, the user must type "-speed", followed by the speed in ticks per
   * second. If output is not specified, the default output is System.out. If no speed is specified
   * the default is 1.
   *
   * @param args the command line arguments as specified above
   * @throws IllegalArgumentException if args are invalid (not as specified above)
   */
  public static void main(String[] args) {

    //values to represent parameters to run, build, and control and animation
    String in = "";
    boolean inRead = false;
    String viewInput = "";
    boolean viewRead = false;
    String out = "";
    boolean outRead = false;
    String speed = "1";// default speed 1
    boolean speedRead = false;

    //to be used to read input file
    Readable inputReader = null;

    //to be used to write animation to a file, if necessary
    Writer outputWriter = null;

    //the model of the animation that will be constructed based on the input file
    AnimationModel model = null;

    //the view
    IView view = null;

    //the controller
    IController controller;

    //parses args
    //will throw error if arguments are specified more than once
    for (int i = 0; i < args.length - 1; i++) {
      switch (args[i]) {
        case "-in":
          if (inRead) {
            displayErrorMessage("in file already inputted");
          }
          inRead = true;
          in = args[i + 1];
          break;
        case "-view":
          if (viewRead) {
            displayErrorMessage("view already inputted");
          }
          viewRead = true;
          viewInput = args[i + 1];
          break;
        case "-out":
          if (outRead) {
            displayErrorMessage("out file already inputted");
          }
          outRead = true;
          out = args[i + 1];
          break;
        case "-speed":
          if (speedRead) {
            displayErrorMessage("speed already inputted");
          }
          speedRead = true;
          speed = args[i + 1];
          break;
        default:
          break;
      }
    }

    //error message if non-optional arguments not specified on command-line
    if (!viewRead) {
      displayErrorMessage("User must specify the type of view in order to run animation");
    }
    if (!inRead) {
      displayErrorMessage("User must specify the input file name in order to run animation");
    }

    //try to read input file
    try {
      inputReader = new FileReader(in);
    } catch (FileNotFoundException e) {
      displayErrorMessage("Input file does not exist");
    }

    model = parseFile(inputReader, new AnimationModelImpl.Builder());


    //if user specified output correctly, will create FileWriter to output to that file
    //if user did not specify output, will set default output to System.out
    if (outRead) {
      try {
        outputWriter = new FileWriter(new File(out), true);
      } catch (IOException e) {
        displayErrorMessage("Error, output invalid");
      }
    } else {
      //default output System.out
      outputWriter = new PrintWriter(System.out);
    }

    //creates view based on what user specified
    try {
      view = new ViewFactory().factory(viewInput);
    } catch (IllegalArgumentException invalidView) {
      displayErrorMessage(invalidView.getMessage());
    }
    
    //passes model to View
    view.setModel(model);
    //sets speed of view. has no effect for textual view
    view.setSpeed(parseInt(speed));
    //passes output writer to view. has no effect for visual and editor views
    view.setOut(outputWriter);

    if (viewInput.equals("edit")) {
      //constructs controller
      controller = new EditorController((IEditorView) view, model);
    } else {
      controller = new SimpleController(view);
    }

    //begins animation or opens editing window
    controller.giveControl();

  }

  // to display error messages before quitting
  private static void displayErrorMessage(String message) {
    JOptionPane.showMessageDialog(new JFrame(),
        message);
    System.exit(0);
  }

  //view factory to construct proper view based on input
  private static class ViewFactory {
    public IView factory(String viewType) {
      switch (viewType) {
        case "text":
          return new TextualView();
        case "visual":
          return new VisualView();
        case "svg":
          return new SVGView();
        case "edit":
          return new EditVisualView();
        default:
          throw new IllegalArgumentException("view type specified does not exist");
      }
    }
  }


}














