package logic.manager;

import space.shuttle.ArmedSpaceShuttle;
import space.shuttle.ShuttleNavigator;

public class PlayerManager {

	private static PlayerManager INSTANCE;

	private ArmedSpaceShuttle playerShuttle;
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
			System.out.println("Overwriting active playerNavigator...");
		playerNavigator=pN;
	}
	
	public void registerPlayerShuttle(ArmedSpaceShuttle p) {
		if(playerShuttle!=null)
			System.out.println("Overwriting active playershuttle...");
		playerShuttle=p;
	}
	
	public ArmedSpaceShuttle getPlayerShuttle() {return playerShuttle;}
	public ShuttleNavigator getPlayerNavigator() {return playerNavigator;}
}
