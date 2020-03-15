package views;

import config.JSON.JSONConfig;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Program;

public class StartView {

	Button startButton,configButton,closeButton;
	Scene welcomeScene;
	
	Stage primary; 
	
	public StartView (JSONConfig config,Stage primaryStage) {
		primary= primaryStage;
		
		Label welcomeText = new Label("Solaris");
		
		startButton = new Button("Start Solaris!");
		startButton.setOnAction(a -> new GalaxyView(config,primaryStage));
		
		configButton = new Button("Options");
		
		closeButton= new Button("Quit");
		closeButton.setOnAction(a-> Program.invokeStop());
		
		VBox layout = new VBox(10);
		
		layout.getChildren().add(welcomeText);
		layout.getChildren().add(startButton);
		layout.getChildren().add(configButton);
		layout.getChildren().add(closeButton);
		
		Scene welcomeScene= new Scene(layout,config.getSettings().getScreenWidth() , config.getSettings().getScreenHeight());

		// Needs to be down here, otherwise it points to welcome-scene as a null value (lambda logic)
		configButton.setOnAction(e -> new ConfigView(config,primary,welcomeScene));
		
		primary.setScene(welcomeScene);
		primary.show();
		
	}
}
