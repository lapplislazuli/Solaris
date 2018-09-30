package logic;


import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import space.core.SpaceObject;
import javafx.scene.*;

import interfaces.logical.UpdatingObject;
@SuppressWarnings("restriction")
public class MouseManager implements UpdatingObject {
	
	private Scene scene;
	
	private static MouseManager INSTANCE;
	
	private MouseManager() {
		
		return;
	};
	
	public static MouseManager getInstance() {
		if(INSTANCE==null)
			INSTANCE=new MouseManager();
		return INSTANCE;
	}

	
	public void init(Scene scene) {
		this.scene=scene;
		
		//scene.addEventHandler(MouseEvent.MOUSE_MOVED, evt -> mouseMoved(evt));
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, evt -> mouseClicked(evt));
        
	}
	
	private void mouseMoved(MouseEvent evt) {
		//toDo
	}
	
	private void mouseClicked(MouseEvent evt) {
		UpdateManager.getInstance().toDraw.
			stream()
			.filter( drawable -> drawable instanceof SpaceObject)
			.flatMap(space -> ((SpaceObject)space).getAllChildrenFlat().stream())
			.filter(item -> item.isCovered((int)evt.getSceneX(),(int)evt.getSceneY()))
			.forEach(clicked -> clicked.click());
	}
	
	@Override
	public void update() {
		
	}
}
