package logic;


import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import space.core.SpaceObject;
import javafx.scene.*;
import geom.AbsolutePoint;
import interfaces.geom.Point;
import interfaces.logical.UpdatingObject;
@SuppressWarnings("restriction")
public class MouseManager implements UpdatingObject {
	
	private Scene scene;
	
	private static MouseManager INSTANCE;
	
	private MouseManager() {};
	
	public static MouseManager getInstance() {
		if(INSTANCE==null)
			INSTANCE=new MouseManager();
		return INSTANCE;
	}

	
	public void init(Scene scene) {
		this.scene=scene;
		//scene.addEventHandler(MouseEvent.MOUSE_MOVED, evt -> mouseMoved(evt));
        this.scene.addEventHandler(MouseEvent.MOUSE_PRESSED, evt -> mouseClicked(evt));
    }
	
	
	private void mouseMoved(MouseEvent evt) {
		//toDo
	}
	
	private void mouseClicked(MouseEvent evt) {
		AbsolutePoint clickedPosition = new AbsolutePoint((int)evt.getSceneX(),(int)evt.getSceneY());
		
		if(evt.getButton().equals(MouseButton.PRIMARY))
			shootAtClickedPoint(clickedPosition);
		else if (evt.getButton().equals(MouseButton.SECONDARY))
			registerSpaceObjectToPlayerRoute(clickedPosition);
			//showInformationOnClick(clickedPosition);
		else
			System.out.println("You pressed something strange!");
	}

	private void shootAtClickedPoint(Point clickedPosition) {
		PlayerManager.getInstance().getPlayerShuttle().shootRocket(clickedPosition);
	}
	
	private void registerSpaceObjectToPlayerRoute(Point clickedPosition) {
		DrawingManager.getInstance().registeredItems.
			stream()
			.filter( drawable -> drawable instanceof SpaceObject)
			.flatMap(space -> ((SpaceObject)space).getAllChildrenFlat().stream())
			.filter(item -> item.shape.contains(clickedPosition))
			.forEach(clicked -> PlayerManager.getInstance().getPlayerNavigator().route.add(clicked));
		System.out.println("New Route:"+PlayerManager.getInstance().getPlayerNavigator().route.toString());
	}
	
	private void showInformationOnClick(Point clickedPosition) {
		DrawingManager.getInstance().registeredItems.
			stream()
			.filter( drawable -> drawable instanceof SpaceObject)
			.flatMap(space -> ((SpaceObject)space).getAllChildrenFlat().stream())
			.filter(item -> item.shape.contains(clickedPosition))
			.forEach(clicked -> clicked.click());
	}
	
	@Override
	public void update() {
		
	}
}
