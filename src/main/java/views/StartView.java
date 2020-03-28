package views;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.json.JSONConfig;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Program;

public class StartView {

	Button startButton,configButton,closeButton;
	Scene welcomeScene;
	private static Logger logger = LogManager.getLogger(StartView.class);

	
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
		layout.setAlignment(Pos.CENTER);
		layout.setPrefWidth(120);
		
		layout.getChildren().addAll(welcomeText,startButton,configButton,closeButton);
		
		startButton.setMinSize(layout.getPrefWidth(), startButton.getMinHeight());
		configButton.setMinSize(layout.getPrefWidth(), startButton.getMinHeight());
		closeButton.setMinSize(layout.getPrefWidth(), startButton.getMinHeight());
		
		Scene welcomeScene= new Scene(layout,config.getSettings().getScreenWidth() , config.getSettings().getScreenHeight());

		// Needs to be down here, otherwise it points to welcome-scene as a null value (lambda logic)
		configButton.setOnAction(e -> new ConfigView(config,primary,welcomeScene));
		
		logger.debug("Build Welcome-View");
		
		primary.setScene(welcomeScene);
		primary.show();
		
	}
}
