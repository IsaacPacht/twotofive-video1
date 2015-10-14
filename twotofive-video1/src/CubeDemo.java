// Class declaration and two static imports = 3 statements - total = 3
public class CubeDemo extends javafx.application.Application {
	private static int emptyPos = 8, tilePos[] = {0,1,2,3,4,5,6,7}, tileFaceUp[] = {1,1,1,1,1,1,1,1};

	private static final java.util.List<javafx.scene.Group> tiles = java.util.stream.IntStream.range(0, 8).mapToObj(i -> javafx.scene.GroupBuilder.create().<javafx.scene.Group>children(java.util.Arrays.<javafx.scene.Node>asList(new javafx.scene.Node[] {
			javafx.scene.shape.RectangleBuilder.create() // back face
	        .width((double) 175).height((double) 175)
	        .fill(new javafx.scene.paint.ImagePattern(new javafx.scene.image.Image("file:media/firsthand-"+(i/3)+"-"+(i%3)+".png")))
	        .translateX(-0.5 * (double) 175)
	        .translateY(-0.5 * (double) 175)
	        .translateZ(0.5 * (double) 20)
	        .build(),
	        javafx.scene.shape.RectangleBuilder.create() // bottom face
	        .width((double) 175).height((double) 20)
	        .fill(javafx.scene.paint.Color.DARKBLUE.deriveColor(0.0, 1.0, (1 - 0.4 * 1), 1.0))
	        .translateX(-0.5 * (double) 175)
	        .translateY(0.5 * ((double) 175 - (double) 20))
	        .rotationAxis(javafx.scene.transform.Rotate.X_AXIS)
	        .rotate(90)
	        .build(),
	        javafx.scene.shape.RectangleBuilder.create() // right face
	        .width((double) 20).height((double) 175)
	        .fill(javafx.scene.paint.Color.DARKBLUE.deriveColor(0.0, 1.0, (1 - 0.3 * 1), 1.0))
	        .translateX(0.5 * ((double) 175-(double) 20))
	        .translateY(-0.5 * (double) 175)
	        .rotationAxis(javafx.scene.transform.Rotate.Y_AXIS)
	        .rotate(90)
	        .build(),
	        javafx.scene.shape.RectangleBuilder.create() // left face
	        .width((double) 20).height((double) 175)
	        .fill(javafx.scene.paint.Color.DARKBLUE.deriveColor(0.0, 1.0, (1 - 0.2 * 1), 1.0))
	        .translateX(-0.5 * ((double) 175+(double) 20))
	        .translateY(-0.5 * ((double) 175))
	        .rotationAxis(javafx.scene.transform.Rotate.Y_AXIS)
	        .rotate(90)
	        .build(),
	        javafx.scene.shape.RectangleBuilder.create() // top face
	        .width((double) 175).height((double) 20)
	        .fill(javafx.scene.paint.Color.DARKBLUE.deriveColor(0.0, 1.0, (1 - 0.1 * 1), 1.0))
	        .translateX(-0.5 * (double) 175)
	        .translateY(-0.5 * ((double) 175+(double) 20))
	        .rotationAxis(javafx.scene.transform.Rotate.X_AXIS)
	        .rotate(90)
	        .build(),
	        javafx.scene.shape.RectangleBuilder.create() // front face
	        .width((double) 175).height((double) 175)
	        .fill(new javafx.scene.paint.ImagePattern(new javafx.scene.image.Image("file:media/cerner-"+(i/3)+"-"+(i%3)+".png")))
	        .translateX(-0.5 * (double) 175)
	        .translateY(-0.5 * (double) 175)
	        .translateZ(-0.5 * (double) 20)
	        .build()
	}))
	.onMouseClicked(e ->  moveOrFlipTile(i, e))
	.translateX(i%3 * ((double) 175 + 2))
	.translateY(i/3 * ((double) 175 + 2))
	.build())
	.collect(java.util.stream.Collectors.toList());

	// Start method begin/end brackets and one statement of content = 3 statements - total = 6
    public void start(javafx.stage.Stage stage) {
    	javafx.stage.StageBuilder.create().title("Cerner and First Hand").scene(javafx.scene.SceneBuilder.create().root(
    			javafx.scene.layout.StackPaneBuilder.create()
    			.layoutX(20)
    			.layoutY(20)
    			.alignment(javafx.geometry.Pos.TOP_LEFT)
    			.children(tiles)
    			.style("-fx-background-color: cyan")
    			.build()
        		).camera(new javafx.scene.PerspectiveCamera()).width(570).height(570).depthBuffer(true).build()).build().show();
    }
    
    // moveOrFlip method begin/end brackets and 10 statements of content = 10 statements - total = 16
    private static void moveOrFlipTile(int tileIndex, javafx.scene.input.MouseEvent e) {
		if (e.getButton() == javafx.scene.input.MouseButton.PRIMARY) {
        	int targetPos = emptyPos;
        	javafx.animation.TimelineBuilder.create().keyFrames(
			        new javafx.animation.KeyFrame(javafx.util.Duration.ZERO,
			            new javafx.animation.KeyValue(tiles.get(tileIndex).translateXProperty(), tilePos[tileIndex]%3*177),
			            new javafx.animation.KeyValue(tiles.get(tileIndex).translateYProperty(), tilePos[tileIndex]/3*177)),
			        new javafx.animation.KeyFrame(javafx.util.Duration.seconds(1),
			            new javafx.animation.KeyValue(tiles.get(tileIndex).translateXProperty(), targetPos%3*177),
			            new javafx.animation.KeyValue(tiles.get(tileIndex).translateYProperty(), targetPos/3*177)))
			    .build().play();
            emptyPos = tilePos[tileIndex];
            tilePos[tileIndex] = targetPos;
    	} else {
    		tileFaceUp[tileIndex] = -1 * tileFaceUp[tileIndex];
    		javafx.animation.TimelineBuilder.create().keyFrames(
				    new javafx.animation.KeyFrame(javafx.util.Duration.ZERO,
				        new javafx.animation.KeyValue(tiles.get(tileIndex).rotationAxisProperty(), ((tileIndex/3-(tileIndex%3)) & 1) == 1 ? javafx.scene.transform.Rotate.X_AXIS : javafx.scene.transform.Rotate.Y_AXIS),
				        new javafx.animation.KeyValue(tiles.get(tileIndex).rotateProperty(), tileFaceUp[tileIndex] < 0 ? 0d : 180d)),
				    new javafx.animation.KeyFrame(javafx.util.Duration.seconds(1),
				        new javafx.animation.KeyValue(tiles.get(tileIndex).rotationAxisProperty(), ((tileIndex/3-(tileIndex%3)) & 1) == 1 ? javafx.scene.transform.Rotate.X_AXIS : javafx.scene.transform.Rotate.Y_AXIS),
				        new javafx.animation.KeyValue(tiles.get(tileIndex).rotateProperty(), tileFaceUp[tileIndex] < 0  ? 180d : 360d)))
				.build().play();
    	}
    }

    // main method being/end brackets and 1 statement of content = 3 statements - total = 19
	public static void main(String[] args) {
        launch(args);
    }
}
