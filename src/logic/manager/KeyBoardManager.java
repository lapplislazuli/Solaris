package logic.manager;

import java.util.HashMap;
import java.util.Map;

import interfaces.logical.UpdatingObject;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

@SuppressWarnings("restriction")
public class KeyBoardManager implements UpdatingObject {
	
	private char currentPressed;

	private static KeyBoardManager INSTANCE;
	private Map<Character,Runnable> keyBindings;
	
	private KeyBoardManager() {
		keyBindings=new HashMap<Character,Runnable> ();
		initNormalKeyBindings();
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
	
	public void init(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_TYPED, evt -> keyTyped(evt));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, evt -> keyReleased(evt));
	}

	private void keyReleased(KeyEvent evt) {
		currentPressed = Character.UNASSIGNED;
	}
	
	private void initNormalKeyBindings() {
		keyBindings.put('p', ()->UpdateManager.getInstance().togglePause());
		keyBindings.put('c', ()->CollisionManager.getInstance().togglePause());
		keyBindings.put('d', ()->PlayerManager.getInstance().getPlayerNavigator().clearRoute());
		keyBindings.put('l', ()->PlayerManager.getInstance().forceRespawn());
		keyBindings.put('q', ()->System.exit(0));
		keyBindings.put('k', ()->PlayerManager.getInstance().speedUp());
		keyBindings.put('j', ()->PlayerManager.getInstance().slowDown());
	}
	
	private void keyTyped(KeyEvent evt) {
		if(currentPressed==Character.UNASSIGNED) {
			currentPressed = evt.getCharacter().toCharArray()[0];
			if(keyBindings.get(currentPressed)!=null)
				keyBindings.get(currentPressed).run();
			else
				System.out.println("You pressed " + currentPressed + " which has no action Bound!");
		}
		else if (currentPressed==evt.getCharacter().toCharArray()[0]) {
			//Someone is keeping the same Character pressed
		}
		else
			System.out.println("You are pressing "  + currentPressed + "- release first for new Input!");
		
	}
	
	public void registerKeyBinding(Character key, Runnable action) {
		if(keyBindings.get(key)!=null)
			System.out.println("Overrwrite Keybinding for " + key);
		keyBindings.put(key,action);
	}
	
}
