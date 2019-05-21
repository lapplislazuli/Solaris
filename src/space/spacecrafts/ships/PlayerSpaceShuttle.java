package space.spacecrafts.ships;

import org.pmw.tinylog.Logger;

import interfaces.drawing.DrawingInformation;
import interfaces.geom.Shape;
import logic.manager.ManagerRegistry;
import logic.manager.PlayerManager;
import space.core.SpaceObject;

public class PlayerSpaceShuttle extends ArmedSpaceShuttle {
	
	public PlayerSpaceShuttle(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent, size, orbitingDistance, speed);
		ManagerRegistry.getPlayerManager().registerPlayerShuttle(this);
	}
	public PlayerSpaceShuttle(String name, SpaceObject parent,DrawingInformation dinfo,Shape s, int size, int orbitingDistance, double speed) {
		super(name, parent,dinfo,s, size, orbitingDistance, speed);
		ManagerRegistry.getPlayerManager().registerPlayerShuttle(this);
	}
	
	@Override
	public void destruct() {
		if(isPlayer) {
			ManagerRegistry.getPlayerManager().deathCount++;
			Logger.info("PlayerDeath #"+ManagerRegistry.getPlayerManager().deathCount);
		}
		super.destruct();
	}
	

	@Override
	public PlayerSpaceShuttle rebuildAt(String name, SpaceObject at) {
		PlayerSpaceShuttle copy = new PlayerSpaceShuttle(name,at,dInfo,shape,size,(int) orbitingDistance,speed);
		return copy;
	}
}
