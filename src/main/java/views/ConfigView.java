package views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.JSON.JSONConfig;

public class ConfigView {
	
	private static Logger logger = LogManager.getLogger(ConfigView.class);

	Button saveButton,backButton;
	Scene configScene;
	
	Stage primary; 
	
	public ConfigView (JSONConfig config,Stage primaryStage, Scene origin) {
		primary= primaryStage;
		
		Label headerText = new Label("Config");
		
		Label todoNote = new Label("To be done");
		
		saveButton = new Button("save changes");
		saveButton.setOnAction(a -> logger.debug("Saving changes to config to be done"));
		
		backButton= new Button("Back");
		backButton.setOnAction(e-> {
			logger.debug("Moving back to WelcomeView");
			primaryStage.setScene(origin);
			primaryStage.show();
		});
		
		VBox layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);
		layout.setPrefWidth(120);
		
		todoNote.setMinWidth(layout.getPrefWidth());
		backButton.setMinWidth(layout.getPrefWidth());
		saveButton.setMinWidth(layout.getPrefWidth());
		
		layout.getChildren().addAll(headerText,todoNote,saveButton,backButton);

		logger.debug("Build ConfigView");
		
		Scene configScene= new Scene(layout,config.getSettings().getScreenWidth() , config.getSettings().getScreenHeight());

		primary.setScene(configScene);
		primary.show();
	}
	
}