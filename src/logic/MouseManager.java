package logic;


import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import space.core.SpaceObject;
import space.shuttle.ArmedSpaceShuttle;
import javafx.scene.*;
import geom.AbsolutePoint;
import interfaces.geom.Point;
import interfaces.logical.UpdatingObject;
@SuppressWarnings("restriction")
public class MouseManager implements UpdatingObject {
	
	private Scene scene;
	
	private static MouseManager INSTANCE;
	
	private ArmedSpaceShuttle player;
	
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
	
	public void signupPlayer(ArmedSpaceShuttle p) {
		if(player!=null)
			System.out.println("Overwriting active player...");
		player=p;
	}
	
	private void mouseMoved(MouseEvent evt) {
		//toDo
	}
	
	private void mouseClicked(MouseEvent evt) {
		AbsolutePoint clickedPosition = new AbsolutePoint((int)evt.getSceneX(),(int)evt.getSceneY());
		
		if(evt.getButton().equals(MouseButton.PRIMARY))
			shootAtClickedPoint(clickedPosition);
		else if (evt.getButton().equals(MouseButton.SECONDARY))
			showInformationOnClick(clickedPosition);
		else
			System.out.println("You pressed something strange!");
	}

	private void shootAtClickedPoint(Point clickedPosition) {
		
		if(player!=null)
			player.shootRocket(clickedPosition);
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
