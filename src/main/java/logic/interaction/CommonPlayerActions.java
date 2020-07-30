package logic.interaction;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import interfaces.geom.Point;
import logic.manager.ManagerRegistry;
import space.core.SpaceObject;

public abstract class CommonPlayerActions {
	/*
	 * This class holds a number of common actions the player might invoke (see title)
	 * All of them gather information from the static elements of the program (such as the program itself or the managerregistry) 
	 * And then make an action of the public methods found. 
	 */
	
	private static Logger logger = LogManager.getLogger(CommonPlayerActions.class);

	public static void shootAtClickedPoint() {
		Point clicked = ManagerRegistry.getMouseManager().getMousePos();
		shootAtPoint(clicked);
	}
	
	public static void shootAtPoint(Point clickedPosition) {
		ManagerRegistry.getPlayerManager().getPlayerShuttle().attack(clickedPosition);
	}
	
	public static void registerSpaceObjectToPlayerRoute() {
		Point clicked = ManagerRegistry.getMouseManager().getMousePos();
		registerSpaceObjectToPlayerRoute(clicked);
	}
	
	public static void registerSpaceObjectToPlayerRoute(Point clickedPosition) {
		ManagerRegistry.getDrawingManager().registeredItems.
			stream()
			.filter( drawable -> drawable instanceof SpaceObject)
			.flatMap(space -> ((SpaceObject)space).getAllChildren().stream())
			.filter(t-> t instanceof SpaceObject)
			.map(t->(SpaceObject)t)
			.filter(item -> item.getShape().contains(clickedPosition))
			.forEach(clicked -> ManagerRegistry.getPlayerManager().getPlayerNavigator().getRoute().add(clicked));
		logger.info("New Route:" + ManagerRegistry.getPlayerManager().getPlayerNavigator().getRoute().toString());
	}
	
	public static void showInformationOnClick() {
		Point clicked = ManagerRegistry.getMouseManager().getMousePos();
		showInformationOnClick(clicked);
	}
	
	public static void showInformationOnClick(Point clickedPosition) {
		ManagerRegistry.getDrawingManager().registeredItems.
			stream()
			.filter( drawable -> drawable instanceof SpaceObject)
			.flatMap(space -> ((SpaceObject)space).getAllChildren().stream())
			.filter(t-> t instanceof SpaceObject)
			.map(t->(SpaceObject)t)
			.filter(item -> item.getShape().contains(clickedPosition))
			.forEach(clicked -> clicked.click());
	}
}
