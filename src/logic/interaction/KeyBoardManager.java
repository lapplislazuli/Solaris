package logic.interaction;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import config.KeyManagerConfig;
import config.MouseManagerConfig;
import interfaces.logical.UpdatingObject;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import logic.manager.CollisionManager;
import logic.manager.UpdateManager;

@SuppressWarnings("restriction")
public class KeyBoardManager implements UpdatingObject {
	
	private char currentPressed;

	private static KeyBoardManager INSTANCE;
	private ActionRegistry actions;
	private Map<Character,Action> keyBindings;
	
	private KeyBoardManager() {
		keyBindings=new HashMap<Character,Action> ();
		actions= ActionRegistry.getInstance();
		//initNormalKeyBindings();
	};
	
	public static KeyBoardManager getInstance() {
		if(INSTANCE==null)
			INSTANCE=new KeyBoardManager();
		return INSTANCE;
	}

	@Override
	public void update() {
		// Maybe handle stuff that needs longer pressed buttons (like charging a laserbeam)
	}
	
	public void init(Scene scene, KeyManagerConfig config) {
        scene.addEventHandler(KeyEvent.KEY_TYPED, evt -> keyTyped(evt));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, evt -> keyReleased(evt));
        initKeyBindings(config);
	}

	private void keyReleased(KeyEvent evt) {
		currentPressed = Character.UNASSIGNED;
	}
	
	public void initKeyBindings(KeyManagerConfig config) {
		for( Entry<String,String> binding : config.getKeyBindings().entrySet())
			keyBindings.put(binding.getKey().toCharArray()[0],actions.getActionByName(binding.getValue()));	
	}
	
	private void keyTyped(KeyEvent evt) {
		if(currentPressed==Character.UNASSIGNED) {
			currentPressed = evt.getCharacter().toCharArray()[0];
			if(keyBindings.get(currentPressed)!=null)
				keyBindings.get(currentPressed).doAction();
			else
				System.out.println("You pressed " + currentPressed + " which has no action Bound!");
		}
		else if (currentPressed==evt.getCharacter().toCharArray()[0]) {
			//Someone is keeping the same Character pressed
		}
		else
			System.out.println("You are pressing "  + currentPressed + "- release first for new Input!");
		
	}
	
	public void registerKeyBinding(Character key, Action action) {
		if(keyBindings.get(key)!=null)
			System.out.println("Overrwrite Keybinding for " + key + " with Action " + action.getName());
		keyBindings.put(key,action);
	}
	
}
