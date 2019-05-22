package logic.manager;

import org.pmw.tinylog.Logger;

import interfaces.spacecraft.ArmedSpacecraft;
import space.spacecrafts.navigators.PlayerNavigator;

public class PlayerManager{

	private ArmedSpacecraft playerShuttle;
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
	
	public void registerPlayerShuttle(ArmedSpacecraft p) {
		if(playerShuttle!=null)
			Logger.info("Overwriting active playershuttle...");
		playerShuttle=p;
	}
	
	public ArmedSpacecraft getPlayerShuttle() {return playerShuttle;}
	public PlayerNavigator getPlayerNavigator() {return playerNavigator;}

	public void forceRespawn() {
		playerShuttle.destruct();
	}
	
	public void speedUp() {
	//	playerShuttle.setSpeed(playerShuttle.getSpeed()*1.1);
	}
	public void slowDown() {
	//	playerShuttle.setSpeed(playerShuttle.getSpeed()*0.9);
	}
	
	public int getPlayerDeaths() {
		return deathCount;
	}
}
