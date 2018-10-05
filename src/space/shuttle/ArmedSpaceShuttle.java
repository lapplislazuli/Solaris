package space.shuttle;

import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import logic.MouseManager;
import space.core.SpaceObject;
import space.shuttle.missiles.Laserbeam;
import space.shuttle.missiles.Missile;
import space.shuttle.missiles.Rocket;

public class ArmedSpaceShuttle extends SpaceShuttle{
	
	public int rocketsLeft=6, laserCoolDown=0;

	protected boolean isPlayer;
	
	public ArmedSpaceShuttle(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent, size, orbitingDistance, speed);
		
	}
	
	@Override
	public void update() {
		if(parent!=null)
			shootNextDestructible();
		laserCoolDown--;
		super.update();
	}
	
	public void shootNextDestructible() {
		if(!sensor.detectedItems.isEmpty())
			sensor.detectedItems.stream()
				.filter(c->c instanceof DestructibleObject)
				.filter(c -> c instanceof SpaceObject)
				.forEach(c-> shootLaser((SpaceObject)c));
	}
	
	public void shootLaser(SpaceObject target) {
		if(laserCoolDown<=0) {
			new Laserbeam("Laser from " + name, this,degreeTo(target),5);
			//@UpdateRatio 25ms its every 3 Seconds:
			laserCoolDown= 3000/25;
		}
	}
	
	public void shootRocket(SpaceObject target) {
		if(rocketsLeft-- >= 0)
			new Rocket("Rocket from " + name, this,3,degreeTo(target),10);
	}

	@Override
	public boolean collides(CollidingObject other) {
		if(other instanceof Missile && trabants.contains((Missile)other))
			return false;
		return super.collides(other);
	}
	
	public void setPlayer(boolean val) { 
		isPlayer=val;
		if(val)
			MouseManager.getInstance().signupPlayer(this);
	}
	public boolean isPlayer() {return isPlayer;}
}
