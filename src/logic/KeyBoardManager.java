/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package logic
 */
package logic;

import interfaces.logical.UpdatingObject;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

@SuppressWarnings("restriction")
public class KeyBoardManager implements UpdatingObject {
	
	private char currentPressed;

	private static final KeyBoardManager INSTANCE = new KeyBoardManager();
	
	private KeyBoardManager() {};
	
	public static KeyBoardManager getInstance() {return INSTANCE;}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		// Maybe handle stuff that needs longer pressed buttons (like charging a laserbeam)
	}
	
	public void init(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_TYPED, evt -> keyTyped(evt));
        scene.addEventHandler(KeyEvent.KEY_RELEASED, evt -> keyReleased(evt));
	}

	private void keyReleased(KeyEvent evt) {
		currentPressed = Character.UNASSIGNED;
	}

	private void keyTyped(KeyEvent evt) {
		if(currentPressed==Character.UNASSIGNED) {
			currentPressed = evt.getCharacter().toCharArray()[0];
			switch (currentPressed) {
			case 'p': UpdateManager.getInstance().pause(); break;
			case 's': UpdateManager.getInstance().start();break;
			case 'v': CollisionManager.getInstance().pause();break;
			case 'c': CollisionManager.getInstance().start();break;
			//ToDo: Case Escape to close Game
			default: System.out.println("You pressed: " + currentPressed + " ... nothing happened");
			}
		}
		else if (currentPressed==evt.getCharacter().toCharArray()[0]) {
			//Someone is keeping the same Character pressed
		}
		else
			System.out.println("You are pressing "  + currentPressed + "- release first for new Input!");
		
	}
	
}