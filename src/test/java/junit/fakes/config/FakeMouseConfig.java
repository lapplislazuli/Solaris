package junit.fakes.config;

import java.util.HashMap;
import java.util.Map;

import config.interfaces.MouseConfig;
import javafx.scene.input.MouseButton;

@SuppressWarnings("restriction")
public class FakeMouseConfig implements MouseConfig {
	
	public Map<MouseButton, String> keyBindings= new HashMap<MouseButton,String>();
	
	public Map<MouseButton, String> getKeyBindings() {
		return keyBindings;
	}

	public void putKeyBinding(MouseButton button, String actionName) {
		keyBindings.put(button,actionName);
	}

}
