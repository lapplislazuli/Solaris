package logic.interaction;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.interfaces.Config;
import config.interfaces.KeyConfig;
import interfaces.logical.UpdatingManager;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import logic.manager.ManagerRegistry;

public class KeyBoardManager implements UpdatingManager<Action> {

	private static Logger logger = LogManager.getLogger(KeyBoardManager.class);
	
	private char currentPressed;

	private ActionManager actions;
	private Map<Character,Action> keyBindings;
	private boolean running=true;
	
	public KeyBoardManager() {
		keyBindings=new HashMap<Character,Action> ();
		actions= ManagerRegistry.getActionManager();
	};
	
	@Override
	public void update() {
		if(running) {
			// Maybe handle stuff that needs longer pressed buttons (like charging a laserbeam)
		}
	}
	
	public void initScene(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_TYPED, evt -> keyTyped(evt));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, evt -> keyReleased(evt));
	}

	public void keyReleased(KeyEvent evt) {
		currentPressed = Character.UNASSIGNED;
	}
	
	public char getCurrentPressed() {
		return currentPressed;
	}
	
	public void initKeyBindings(KeyConfig config) {
		for( Entry<String,String> binding : config.getKeyBindings().entrySet())
			if(actions.getActionByName(binding.getValue())!=null)
				keyBindings.put(binding.getKey().toCharArray()[0],actions.getActionByName(binding.getValue()));	
	}
	
	public void keyTyped(KeyEvent evt) {
		if(currentPressed==Character.UNASSIGNED) {
			currentPressed = evt.getCharacter().toCharArray()[0];
			if(keyBindings.get(currentPressed)!=null) {
				logger.debug("Player pressed " + currentPressed + " and do Action " + keyBindings.get(currentPressed).getName());
				keyBindings.get(currentPressed).doAction();
			}
			else
				logger.debug("Player pressed " + currentPressed + " which has no action Bound!");
		}
		else if (currentPressed==evt.getCharacter().toCharArray()[0]) {
			//Someone is keeping the same Character pressed
		}
		else
			logger.trace("Player presses "  + currentPressed + "- needs to release first for new Input");
		
	}
	
	public void registerKeyBinding(Character key, Action action) {
		if(keyBindings.get(key)!=null)
			logger.info("Overrwrite Keybinding for " + key + " with Action " + action.getName());
		keyBindings.put(key,action);
	}

	public void init(Config c) {
		 initKeyBindings(c.getKeyConfig());
	}

	public void reset() {
		keyBindings=new HashMap<Character,Action> ();
		actions= ManagerRegistry.getActionManager();
	}

	public Collection<Action> getRegisteredItems() {
		return keyBindings.entrySet().stream().map(t->t.getValue()).collect(Collectors.toList());
	}
	
	public Map<Character,Action> getKeyBindings(){
		return keyBindings;
	}

	public void toggleUpdate() {
		running = !running;
	}

	public boolean isRunning() {
		return running;
	}
	
}
