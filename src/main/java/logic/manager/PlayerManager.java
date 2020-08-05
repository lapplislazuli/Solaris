package logic.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import interfaces.spacecraft.Navigator;
import interfaces.spacecraft.Spacecraft;
import space.spacecrafts.navigators.BaseNavigator;
import space.spacecrafts.ships.Spaceshuttle;

public class PlayerManager{
	/*
	 * This class holds a player-navigator and a spaceshuttle and is used to manage actions issued by the player.
	 * It is mostly invoked by the keyboard and mouse actions, therefore many actions are public. 
	 * 
	 * As these can be invoked in a static context, many of the public methods have checks for null-values.
	 */
	
	private Spacecraft playerShuttle;
	private Navigator playerNavigator;

	private static Logger logger = LogManager.getLogger(EffectManager.class);
	
	private int deathCount = 0;
	
	public PlayerManager() {}
	
	
	public void registerPlayerNavigator(Navigator pN){
		if(playerNavigator != null) {
			logger.info("Overwriting active playerNavigator");	
		}
		playerNavigator = pN;
	}
	
	public void reset() {
		playerShuttle = null;
		playerNavigator = null;
		deathCount = 0;
	}
	
	public void registerPlayerShuttle(Spacecraft p) {
		if(playerShuttle != null) {
			logger.info("Overwriting active playershuttle");	
		}
		playerShuttle = p;
	}
	
	public Spacecraft getPlayerShuttle() {return playerShuttle;}
	public Navigator getPlayerNavigator() {return playerNavigator;}

	public void forceRespawn() {
		if(playerShuttle != null) {
			//TODO: Remove after debugging
			if(BaseNavigator.playerNavRebuildCounter>1) {
				logger.debug("Respawning more than second time!");
			}
			playerShuttle.destruct();	
		}
	}
	
	public void speedUp() {
		if(playerShuttle != null) {
			playerShuttle.setSpeed(playerShuttle.getSpeed() * 1.1);
		}
	}
	
	public void slowDown() {
		if(playerShuttle != null) {
			playerShuttle.setSpeed(playerShuttle.getSpeed() * 0.9);
		}
	}
	
	public int getPlayerDeaths() {
		return deathCount;
	}
	
	public void increasePlayerDeaths() {
		deathCount ++;
	}
}
