package logic.interaction;


import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import logic.manager.DrawingManager;
import space.core.SpaceObject;
import javafx.scene.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import config.Config;
import config.MouseConfig;
import geom.AbsolutePoint;
import interfaces.geom.Point;
import interfaces.logical.UpdatingObject;
@SuppressWarnings("restriction")
public class MouseManager implements UpdatingObject {
	
	private Scene scene;
	private AbsolutePoint mousePos;
	
	private ActionRegistry actions;
	private final Map<MouseButton,Action> mouseBindings;
	
	private static MouseManager INSTANCE;
	
	private MouseManager() {
		actions=ActionRegistry.getInstance();
		mouseBindings=new HashMap<MouseButton,Action>();
	};

	public static MouseManager getInstance() {
		if(INSTANCE==null)
			INSTANCE=new MouseManager();
		return INSTANCE;
	}
	
	public void init(Scene scene, Config config) {
		this.scene=scene;
		//this.scene.addEventHandler(MouseEvent.MOUSE_MOVED, evt -> mouseMoved(evt));
        this.scene.addEventHandler(MouseEvent.MOUSE_PRESSED, evt -> mouseClicked(evt));
        initMouseBindings(config.mouseConfig);
    }
	
	private void initMouseBindings(MouseConfig config) {
		for(Entry<MouseButton,String> binding : config.getKeyBindings().entrySet())
			mouseBindings.put(binding.getKey(),  actions.getActionByName(binding.getValue()));
	}
	
	public void registerMouseBindings(MouseButton button, Action action) {
		if(mouseBindings.get(button)!=null)
			System.out.println("Overrwrite Keybinding for " + button.toString() + " with Action " + action.getName());
		mouseBindings.put(button,action);
	}
	
	private void mouseMoved(MouseEvent evt) {
		
	}
	
	private void mouseClicked(MouseEvent evt) {
		mousePos = new AbsolutePoint((int)evt.getSceneX(),(int)evt.getSceneY());
		if(mouseBindings.get(evt.getButton())!=null)
			mouseBindings.get(evt.getButton()).doAction();
		else
			System.out.println("No MouseBinding for this Action registered!");
	}
	
	public void shootAtMousePos() {
		shootAtClickedPoint(mousePos);
	}
	
	public void registerSpaceObjectToPlayerRoute() {
		registerSpaceObjectToPlayerRoute(mousePos);
	}

	public void showInformation() {
		showInformationOnClick(mousePos);
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
	
	public void update() {
		//Maybe Increase a Timer how long a button has been pressed
	}
}
