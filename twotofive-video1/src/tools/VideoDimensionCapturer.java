package tools;
import java.io.File;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;


public class VideoDimensionCapturer extends  Application {
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) throws Exception {
		Group root = new Group();
		String path = "D:\\temp\\temp4.mp4";
		Media media = new Media(new File(path).toURI().toString());
		MediaPlayer player = new MediaPlayer(media);
		MediaView view = new MediaView(player);
		
		root.getChildren().add(view);
		Scene scene = new Scene(root, 1000, 700);
		stage.setScene(scene);
		stage.show();
		
		player.play();
		player.setOnReady(new Runnable() {
			@Override
			public void run() {
				System.out.println(player.getMedia().getWidth());
				System.out.println(player.getMedia().getHeight());
				
			}
		});
		
	}

}
