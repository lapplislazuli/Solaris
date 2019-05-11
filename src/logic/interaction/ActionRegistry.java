package logic.interaction;

import java.util.HashMap;
import java.util.Map;

import logic.Program;
import logic.manager.CollisionManager;
import logic.manager.ManagerRegistry;
import logic.manager.UpdateManager;

public class ActionRegistry {
	private static ActionRegistry INSTANCE;
	private Map<String,Action> registeredActions;
	
	private ActionRegistry() {
		registeredActions=new HashMap<String,Action> ();
		initStandardActions();
	};
	
	private void initStandardActions() {
		registerAction(
				new Action("TogglePause" ,"Pauses or Starts the update-Cycle",
						()->ManagerRegistry.getUpdateManager().togglePause()));
		registerAction(
				new Action("ToggleCollision" ,"Pauses or Starts the Collision" ,
						()->ManagerRegistry.getCollisionManager().togglePause()));
		registerAction(
				new Action("RouteClear" ,"Cleares all current Items in the Players Route, leaving only current Parent",
						()->PlayerManager.getInstance().getPlayerNavigator().clearRoute()));
		registerAction(
				new Action("ForceSpawn" ,"Kills the playerShip to restart at Parent. Deathcount rises" ,
						()->PlayerManager.getInstance().forceRespawn()));
		registerAction(
				new Action("Quit","Quits the Game" ,
						()->Program.invokeStop()));
		registerAction(
				new Action("Speed+","Speeds up the players ship by 10%" ,
						()->PlayerManager.getInstance().speedUp()));
		registerAction(
				new Action("Speed-" ,"Slows players Ship by 10%",
						()->PlayerManager.getInstance().slowDown()));
		registerAction(
				new Action("Shoot", "Shoots at MousePosition",
						()->MouseManager.getInstance().shootAtMousePos()));
		registerAction(
				new Action("AddToRoute", "Adds Planet at MousePosition to Player-Route",
						()->MouseManager.getInstance().registerSpaceObjectToPlayerRoute()));
		registerAction(
				new Action("ItemInfo", "Shows Itemname and Koords at MousePos",
						()->MouseManager.getInstance().showInformation()));
	}

	public static ActionRegistry getInstance() {
		if(INSTANCE==null)
			INSTANCE=new ActionRegistry();
		return INSTANCE;
	}
	
	public void registerAction(Action a) {
		registeredActions.put(a.getName(),a);
	}
	
	public Action getActionByName(String name) {
		return registeredActions.get(name);
	}
	
	public Action getAction(Action a) {
		return registeredActions.get(a.getName());
	}
}
