package logic.interaction;

import java.util.HashMap;
import java.util.Map;

import logic.Program;
import logic.manager.ManagerRegistry;

public class ActionManager{
	
	private Map<String,Action> registeredActions;
	
	public ActionManager(boolean defaultActions) {
		registeredActions=new HashMap<String,Action> ();
		if(defaultActions)
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

	public void registerAction(Action a) {
		registeredActions.put(a.getName(),a);
	}
	
	public int countRegisteredActions(){
		return registeredActions.size();
	}
	
	public Action getActionByName(String name) {
		return registeredActions.get(name);
	}

	public void reset() {
		registeredActions.clear();
	}
}