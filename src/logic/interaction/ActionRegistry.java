package logic.interaction;

import java.util.HashMap;
import java.util.Map;

import logic.Program;
import logic.manager.ManagerRegistry;

public class ActionRegistry {
	private static ActionRegistry INSTANCE;
	private Map<String,SimpleAction> registeredActions;
	
	private ActionRegistry() {
		registeredActions=new HashMap<String,SimpleAction> ();
		initStandardActions();
	};
	
	private void initStandardActions() {
		registerAction(
				new SimpleAction("TogglePause" ,"Pauses or Starts the update-Cycle",
						()->ManagerRegistry.getUpdateManager().toggleUpdate()));
		registerAction(
				new SimpleAction("ToggleCollision" ,"Pauses or Starts the Collision" ,
						()->ManagerRegistry.getCollisionManager().toggleUpdate()));
		registerAction(
				new SimpleAction("RouteClear" ,"Cleares all current Items in the Players Route, leaving only current Parent",
						()->ManagerRegistry.getPlayerManager().getPlayerNavigator().clearRoute()));
		registerAction(
				new SimpleAction("ForceSpawn" ,"Kills the playerShip to restart at Parent. Deathcount rises" ,
						()->ManagerRegistry.getPlayerManager().forceRespawn()));
		registerAction(
				new SimpleAction("Quit","Quits the Game" ,
						()->Program.invokeStop()));
		registerAction(
				new SimpleAction("Speed+","Speeds up the players ship by 10%" ,
						()->ManagerRegistry.getPlayerManager().speedUp()));
		registerAction(
				new SimpleAction("Speed-" ,"Slows players Ship by 10%",
						()->ManagerRegistry.getPlayerManager().slowDown()));
		registerAction(
				new SimpleAction("Shoot", "Shoots at MousePosition",
						()->MouseManager.getInstance().shootAtMousePos()));
		registerAction(
				new SimpleAction("AddToRoute", "Adds Planet at MousePosition to Player-Route",
						()->MouseManager.getInstance().registerSpaceObjectToPlayerRoute()));
		registerAction(
				new SimpleAction("ItemInfo", "Shows Itemname and Koords at MousePos",
						()->MouseManager.getInstance().showInformation()));
	}

	public static ActionRegistry getInstance() {
		if(INSTANCE==null)
			INSTANCE=new ActionRegistry();
		return INSTANCE;
	}
	
	public void registerAction(SimpleAction a) {
		registeredActions.put(a.getName(),a);
	}
	
	public SimpleAction getActionByName(String name) {
		return registeredActions.get(name);
	}
	
	public Action getAction(Action a) {
		return registeredActions.get(a.getName());
	}
}
