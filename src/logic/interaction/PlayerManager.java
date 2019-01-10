package logic.interaction;

import org.pmw.tinylog.Logger;

import space.spacecrafts.navigators.ShuttleNavigator;
import space.spacecrafts.ships.ArmedSpaceShuttle;
import space.spacecrafts.ships.PlayerSpaceShuttle;

public class PlayerManager {

	private static PlayerManager INSTANCE;

	private PlayerSpaceShuttle playerShuttle;
	private ShuttleNavigator playerNavigator;
	
	public int deathCount=0;
	
	private PlayerManager() {}
	
	public static PlayerManager getInstance() {
		if(INSTANCE==null)
			INSTANCE=new PlayerManager();
		return INSTANCE;
	}
	
	public void registerPlayerNavigator(ShuttleNavigator pN){
		if(playerNavigator!=null)
			Logger.info("Overwriting active playerNavigator...");
		playerNavigator=pN;
	}
	
	public void registerPlayerShuttle(PlayerSpaceShuttle p) {
		if(playerShuttle!=null)
			Logger.info("Overwriting active playershuttle...");
		playerShuttle=p;
	}
	
	public PlayerSpaceShuttle getPlayerShuttle() {return playerShuttle;}
	public ShuttleNavigator getPlayerNavigator() {return playerNavigator;}

	public void forceRespawn() {
		playerShuttle.destruct();
	}
	
	public void speedUp() {
		playerShuttle.speed*=1.1;
	}
	public void slowDown() {
		playerShuttle.speed*=0.9;
	}
}
