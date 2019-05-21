package logic.manager;

import org.pmw.tinylog.Logger;

import space.spacecrafts.navigators.PlayerNavigator;
import space.spacecrafts.ships.PlayerSpaceShuttle;

public class PlayerManager{

	private PlayerSpaceShuttle playerShuttle;
	private PlayerNavigator playerNavigator;
	
	public int deathCount=0;
	
	public PlayerManager() {}
	
	
	public void registerPlayerNavigator(PlayerNavigator pN){
		if(playerNavigator!=null)
			Logger.info("Overwriting active playerNavigator...");
		playerNavigator=pN;
	}
	
	public void reset() {
		playerShuttle=null;
		playerNavigator=null;
		deathCount=0;
	}
	
	public void registerPlayerShuttle(PlayerSpaceShuttle p) {
		if(playerShuttle!=null)
			Logger.info("Overwriting active playershuttle...");
		playerShuttle=p;
	}
	
	public PlayerSpaceShuttle getPlayerShuttle() {return playerShuttle;}
	public PlayerNavigator getPlayerNavigator() {return playerNavigator;}

	public void forceRespawn() {
		playerShuttle.destruct();
	}
	
	public void speedUp() {
		playerShuttle.setSpeed(playerShuttle.getSpeed()*1.1);
	}
	public void slowDown() {
		playerShuttle.setSpeed(playerShuttle.getSpeed()*0.9);
	}
	
	public int getPlayerDeaths() {
		return deathCount;
	}
}
