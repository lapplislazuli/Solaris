package config.interfaces;

import java.util.Map;

import javafx.scene.input.MouseButton;


public interface MouseConfig {

	Map<MouseButton, String> getKeyBindings();

	void putKeyBinding(MouseButton button, String actionName);

}