package space.shuttle;

import java.util.List;

import space.core.SpaceObject;

public class ArmedSpaceShuttle extends SpaceShuttle{

	int rocketsLeft;
	int laserCoolDown=0;
	
	public ArmedSpaceShuttle(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent, size, orbitingDistance, speed);
		rocketsLeft=6;
	}
	
	@Override
	public void update() {
		if(parent!=null) {
			shootLaser(parent);
		}
		laserCoolDown--;
		super.update();
	}
	
	public void shootLaser(SpaceObject target) {
		if(laserCoolDown<=0) {
			new Laserbeam("Rocket from " + name, this,degreeTo(target),5);
			
			//@UpdateRatio 25ms i would say every 3 Seconds:
			laserCoolDown= 3000/25;
		}
	}
	
	public void shootRocket(SpaceObject target) {
		if(rocketsLeft-- >= 0)
			new Rocket("Rocket from " + name, this,3,degreeTo(target),10);
	}

}
