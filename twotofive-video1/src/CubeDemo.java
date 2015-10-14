// Left click tiles to move.  Right click to flip.  Have fun!
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.GroupLayout.SequentialGroup;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageBuilder;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;

// Class declaration and two static imports = 3 statements - total = 3
public class CubeDemo extends Application {
	private static int emptyPos = 8, tilePos[] = {0,1,2,3,4,5,6,7}, tileFaceUp[] = {1,1,1,1,1,1,1,1};

	private static final List<Group> tiles = IntStream.range(0, 8).mapToObj(i -> GroupBuilder.create().<Group>children(Arrays.<Node>asList(new Node[] {
	        RectangleBuilder.create() // back face
	        .width((double) 175).height((double) 175)
	        .fill(new ImagePattern(new Image("file:media/firsthand-"+(i/3)+"-"+(i%3)+".png")))
	        .translateX(-0.5 * (double) 175)
	        .translateY(-0.5 * (double) 175)
	        .translateZ(0.5 * (double) 20)
	        .build(),
	        RectangleBuilder.create() // bottom face
	        .width((double) 175).height((double) 20)
	        .fill(Color.DARKBLUE.deriveColor(0.0, 1.0, (1 - 0.4 * 1), 1.0))
	        .translateX(-0.5 * (double) 175)
	        .translateY(0.5 * ((double) 175 - (double) 20))
	        .rotationAxis(Rotate.X_AXIS)
	        .rotate(90)
	        .build(),
	        RectangleBuilder.create() // right face
	        .width((double) 20).height((double) 175)
	        .fill(Color.DARKBLUE.deriveColor(0.0, 1.0, (1 - 0.3 * 1), 1.0))
	        .translateX(0.5 * ((double) 175-(double) 20))
	        .translateY(-0.5 * (double) 175)
	        .rotationAxis(Rotate.Y_AXIS)
	        .rotate(90)
	        .build(),
	        RectangleBuilder.create() // left face
	        .width((double) 20).height((double) 175)
	        .fill(Color.DARKBLUE.deriveColor(0.0, 1.0, (1 - 0.2 * 1), 1.0))
	        .translateX(-0.5 * ((double) 175+(double) 20))
	        .translateY(-0.5 * ((double) 175))
	        .rotationAxis(Rotate.Y_AXIS)
	        .rotate(90)
	        .build(),
	        RectangleBuilder.create() // top face
	        .width((double) 175).height((double) 20)
	        .fill(Color.DARKBLUE.deriveColor(0.0, 1.0, (1 - 0.1 * 1), 1.0))
	        .translateX(-0.5 * (double) 175)
	        .translateY(-0.5 * ((double) 175+(double) 20))
	        .rotationAxis(Rotate.X_AXIS)
	        .rotate(90)
	        .build(),
	        RectangleBuilder.create() // front face
	        .width((double) 175).height((double) 175)
	        .fill(new ImagePattern(new Image("file:media/cerner-"+(i/3)+"-"+(i%3)+".png")))
	        .translateX(-0.5 * (double) 175)
	        .translateY(-0.5 * (double) 175)
	        .translateZ(-0.5 * (double) 20)
	        .build()
	}))
	.onMouseClicked(e ->  moveOrFlipTile(i, e))
	.translateX(i%3 * ((double) 175 + 2))
	.translateY(i/3 * ((double) 175 + 2))
	.build())
	.collect(Collectors.toList());

	// Start method being/end brackets and one statement of content = 3 statements - total = 6
    public void start(Stage stage) {
    	StageBuilder.create().title("Cerner and First Hand").scene(SceneBuilder.create().root(
        		StackPaneBuilder.create()
    			.layoutX(20)
    			.layoutY(20)
    			.alignment(Pos.TOP_LEFT)
    			.children(tiles)
    			.style("-fx-background-color: cyan")
    			.build()
        		).camera(new PerspectiveCamera()).width(570).height(570).depthBuffer(true).build()).build().show();
    }
    
    // moveOrFlip method being/end brackets and 10 statements of content = 10 statements - total = 16
    private static void moveOrFlipTile(int tileIndex, MouseEvent e) {
		if (e.getButton() == MouseButton.PRIMARY) {
        	int targetPos = emptyPos;
        	TimelineBuilder.create().keyFrames(
			        new KeyFrame(Duration.ZERO,
			            new KeyValue(tiles.get(tileIndex).translateXProperty(), tilePos[tileIndex]%3*177),
			            new KeyValue(tiles.get(tileIndex).translateYProperty(), tilePos[tileIndex]/3*177)),
			        new KeyFrame(Duration.seconds(1),
			            new KeyValue(tiles.get(tileIndex).translateXProperty(), targetPos%3*177),
			            new KeyValue(tiles.get(tileIndex).translateYProperty(), targetPos/3*177)))
			    .build().play();
            emptyPos = tilePos[tileIndex];
            tilePos[tileIndex] = targetPos;
    	} else {
    		tileFaceUp[tileIndex] = -1 * tileFaceUp[tileIndex];
		    TimelineBuilder.create().keyFrames(
				    new KeyFrame(Duration.ZERO,
				        new KeyValue(tiles.get(tileIndex).rotationAxisProperty(), ((tileIndex/3-(tileIndex%3)) & 1) == 1 ? Rotate.X_AXIS : Rotate.Y_AXIS),
				        new KeyValue(tiles.get(tileIndex).rotateProperty(), tileFaceUp[tileIndex] < 0 ? 0d : 180d)),
				    new KeyFrame(Duration.seconds(1),
				        new KeyValue(tiles.get(tileIndex).rotationAxisProperty(), ((tileIndex/3-(tileIndex%3)) & 1) == 1 ? Rotate.X_AXIS : Rotate.Y_AXIS),
				        new KeyValue(tiles.get(tileIndex).rotateProperty(), tileFaceUp[tileIndex] < 0  ? 180d : 360d)))
				.build().play();
    	}
    }

    // main method being/end brackets and 1 statement of content = 3 statements - total = 19
	public static void main(String[] args) {
        launch(args);
    }
}
