package views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import config.JSON.JSONConfig;

public class ConfigView {

	Button saveButton,backButton;
	Scene welcomeScene;
	
	Stage primary; 
	
	public ConfigView (JSONConfig config,Stage primaryStage, Scene origin) {
		primary= primaryStage;
		
		Label headerText = new Label("Config");
		
		Label todoNote = new Label("To be done");
		
		saveButton = new Button("save changes");
		saveButton.setOnAction(a -> System.out.println("Saving changes to config to be done"));
		
		backButton= new Button("Back");
		backButton.setOnAction(e-> {
			
			primaryStage.setScene(origin);
			primaryStage.show();
			});
		
		VBox layout = new VBox(10);
		
		
		layout.getChildren().add(headerText);
		layout.getChildren().add(todoNote);
		layout.getChildren().add(saveButton);
		layout.getChildren().add(backButton);
		
		Scene welcomeScene= new Scene(layout,config.getSettings().getScreenWidth() , config.getSettings().getScreenHeight());

		primary.setScene(welcomeScene);
		primary.show();
	}
	
}