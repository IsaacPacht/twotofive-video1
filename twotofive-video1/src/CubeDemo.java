/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CubeDemo extends Application {

    

    double size = 175;
    double shortsize = 20;
    Color color = Color.DARKCYAN;

    @Override
    public void start(Stage primaryStage) {

    	StackPane root = new StackPane();
//    	root.layoutXProperty().bind(primaryStage.xProperty());
    	root.setLayoutX(20);
    	root.setLayoutY(20);
    	root.setAlignment(Pos.TOP_LEFT);
    	
        
    	Group[][] cubes = new Group[3][3]; 
        for (int i = 0; i < 3; i++) {
        	for (int j = 0; j < 3; j++) {
		        Group cube = createCube(i,j);
		        addOffset(cube, i, j);
		        root.getChildren().add(cube);
		        cubes[i][j] = cube;
		        animateCube(cube, i, j);
        	}
        }
        
        
        Scene scene = new Scene(root, 570, 570, true);
        scene.setCamera(new PerspectiveCamera());
        primaryStage.setResizable(true);
        primaryStage.setTitle("Cube Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	private void animateCube(Group cube, int x, int y) {
		Timeline animation = new Timeline();
		Point3D axis1, axis2;
		
		if (((x-y) & 1) == 0) {
			axis1 = Rotate.Y_AXIS;
			axis2 = Rotate.Z_AXIS;
		} else {
			axis1 = Rotate.X_AXIS;
			axis2 = Rotate.Y_AXIS;
		}
		
		animation.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
	                new KeyValue(cube.rotationAxisProperty(), axis1),
	                new KeyValue(cube.rotateProperty(), 0d)),
                new KeyFrame(Duration.seconds(5),
	                new KeyValue(cube.rotationAxisProperty(), axis2),
	                new KeyValue(cube.rotateProperty(), 360d)));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
	}

	private void addOffset(Group cube, int x, int y) {
		cube.setTranslateX(x * (size + 2));
		cube.setTranslateY(y * (size + 2));
	}

	private Group createCube(int i, int j) {
		Group cube = new Group();
        cube.getChildren().addAll(
                RectangleBuilder.create() // back face
                .width(size).height(size)
                .fill(new ImagePattern(new Image("file:media/firsthand-"+j+"-"+i+".png")))
                .translateX(-0.5 * size)
                .translateY(-0.5 * size)
                .translateZ(0.5 * shortsize)
                .build(),
                RectangleBuilder.create() // bottom face
                .width(size).height(shortsize)
                .fill(Color.RED.deriveColor(0.0, 1.0, (1 - 0.4 * 1), 1.0))
                .translateX(-0.5 * size)
                .translateY(0.5 * (size - shortsize))
                .rotationAxis(Rotate.X_AXIS)
                .rotate(90)
                .build(),
                RectangleBuilder.create() // right face
                .width(shortsize).height(size)
                .fill(Color.YELLOW.deriveColor(0.0, 1.0, (1 - 0.3 * 1), 1.0))
                .translateX(0.5 * (size-shortsize))
                .translateY(-0.5 * size)
                .rotationAxis(Rotate.Y_AXIS)
                .rotate(90)
                .build(),
                RectangleBuilder.create() // left face
                .width(shortsize).height(size)
                .fill(Color.PINK.deriveColor(0.0, 1.0, (1 - 0.2 * 1), 1.0))
                .translateX(-0.5 * (size+shortsize))
                .translateY(-0.5 * (size))
                .rotationAxis(Rotate.Y_AXIS)
                .rotate(90)
                .build(),
                RectangleBuilder.create() // top face
                .width(size).height(shortsize)
                .fill(Color.BLACK.deriveColor(0.0, 1.0, (1 - 0.1 * 1), 1.0))
                .translateX(-0.5 * size)
                .translateY(-0.5 * (size+shortsize))
                .rotationAxis(Rotate.X_AXIS)
                .rotate(90)
                .build(),
                RectangleBuilder.create() // front face
                .width(size).height(size)
                .fill(new ImagePattern(new Image("file:media/cerner-"+j+"-"+i+".png")))
                .translateX(-0.5 * size)
                .translateY(-0.5 * size)
                .translateZ(-0.5 * shortsize)
                .build());
		return cube;
	}

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
