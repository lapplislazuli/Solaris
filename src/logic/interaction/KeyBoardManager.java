package logic.interaction;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.pmw.tinylog.Logger;

import config.interfaces.Config;
import config.interfaces.KeyConfig;
import interfaces.logical.UpdatingObject;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import logic.manager.ManagerRegistry;

@SuppressWarnings("restriction")
public class KeyBoardManager implements UpdatingObject {
	
	private char currentPressed;

	private static KeyBoardManager INSTANCE;
	private ActionManager actions;
	private Map<Character,Action> keyBindings;
	
	private KeyBoardManager() {
		keyBindings=new HashMap<Character,Action> ();
		actions= ManagerRegistry.getActionManager();
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
	
	public void init(Scene scene, Config config) {
        scene.addEventHandler(KeyEvent.KEY_TYPED, evt -> keyTyped(evt));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, evt -> keyReleased(evt));
        initKeyBindings(config.getKeyConfig());
	}

	private void keyReleased(KeyEvent evt) {
		currentPressed = Character.UNASSIGNED;
	}
	
	public void initKeyBindings(KeyConfig config) {
		for( Entry<String,String> binding : config.getKeyBindings().entrySet())
			keyBindings.put(binding.getKey().toCharArray()[0],actions.getActionByName(binding.getValue()));	
	}
	
	private void keyTyped(KeyEvent evt) {
		if(currentPressed==Character.UNASSIGNED) {
			currentPressed = evt.getCharacter().toCharArray()[0];
			if(keyBindings.get(currentPressed)!=null) {
				Logger.debug("Player pressed " + currentPressed + " and do Action " + keyBindings.get(currentPressed).getName());
				keyBindings.get(currentPressed).doAction();
			}
			else
				Logger.debug("Player pressed " + currentPressed + " which has no action Bound!");
		}
		else if (currentPressed==evt.getCharacter().toCharArray()[0]) {
			//Someone is keeping the same Character pressed
		}
		else
			Logger.trace("Player presses "  + currentPressed + "- needs to release first for new Input");
		
	}
	
	public void registerKeyBinding(Character key, SimpleAction action) {
		if(keyBindings.get(key)!=null)
			Logger.info("Overrwrite Keybinding for " + key + " with Action " + action.getName());
		keyBindings.put(key,action);
	}
	
}
