
Changes for Assignment 7

- added addKeyFrame, removeKeyFrame, and replaceKeyFame to AnimationModel Interface, so that
a model could be edited from the editorView
- added removeKeyFrame, addKeyFrame, and getKeyFrameMap to Feature Interface so that shapes
can be edited.
- added two private methods in the Feature class to switch between a keyFrameMap and a list of FeatureMotions.



Description of Design:

The AnimationModel interface represents a full animation. The AnimationModelImpl represents an entire animation with a list of Features. A
Feature is a representation of a shape and all of its respective motions (for the whole course of its "lifetime"), which are represented
as FeatureMotions. Each FeatureMotion has a beginning state and an end state. The beginning and
end states are of type FeatureState. A FeatureState represents a shape at some point(tick) in the animation and
is represented by fields such as position, dimensions, and color. Our model supports adding and
removing Features and FeatureMotions.

Changes from Assignment 5

- Deleted AnimationComponent Interface because it was not needed and added confusion, the methods it held were no longer needed
- moved getAnimationState method from AnimationModel Interface to TextualView class
- removed next and hasNext methods from the AnimationModel Interface because they required that the model keep track of ticks, but this is not allowed because
ticks are for the cs3500.animator.cs3500.animator.util.util.controller or view
- added addShape, addMotion, removeShape, and removeMotion methods to AnimationModel Interface per comments made by grader on assignment 5
- removed tick field from AnimationModelImpl class and added tick field to view classes


Feature Interface
- added addMotion method to Feature Interface
- added isVisible method to Feature Interface
- added getDisappearAt, getAppearAt, setAllButName, getLitOfFeatureMotions, getType,
addMotion, and removeMotion methods to Feature.
- added AbstractFeature class to abstract over behavior of implementing classes of Feature interface

FeatureMotion Interface
- added getEndState, getAttributeTypes, getMotionNumber, isVisible, and setMotionNumber methods.
- we keep track of when a motion was inputted (motion number) so that shapes can be drawn in that order if they are overlapping
- added AttributeName enum class so that the SVGView can know which attributes are being changed across a given motion
- removed intermediateStates and tick fields.
- moved interpolation operation from FeatureMotionImpl class to the view.

FeatureState Interface
- added setMotionNumber and getMotionNumber methods
- overrided hashCode method in AbstractFeatureState class
- overrided equals method in OvalFeatureState and RectangleFeatureState
- added copy method

