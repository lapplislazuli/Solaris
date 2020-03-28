package views;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.manager.ManagerRegistry;

public class KeybindingAlert {

    public static void display() {
        Stage window = new Stage();
        
        window.setTitle("Solaris - Keybindings");
        window.setMinWidth(250);

        VBox layout = new VBox(7);
        
        ManagerRegistry.getMouseManager().getMouseBindings().entrySet()
	        .stream().forEach(kv -> {
	        	Label l = new Label();
	        	l.setText("Mouse - "+ kv.getKey().toString() + ":\t" + kv.getValue().getDescription());        	
	        	layout.getChildren().add(l);
	        });
        
        ManagerRegistry.getKeyBoardManager().getKeyBindings().entrySet()
	        .stream().forEach(kv -> {
	        	Label l = new Label();
	        	l.setText("["+kv.getKey() + "]:\t" + kv.getValue().getDescription());
	        	layout.getChildren().add(l);
	        });
        
        Button closeButton = new Button("Close Info");
        closeButton.setOnAction(e -> window.close());

        layout.getChildren().add(closeButton);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }
    
}
