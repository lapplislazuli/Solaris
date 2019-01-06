package space.shuttle;

import drawing.JavaFXDrawingInformation;
import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import javafx.scene.paint.Color;
import space.advanced.Asteroid;
import space.core.SpaceObject;
import space.effect.Explosion;
import space.shuttle.missiles.Laserbeam;

public class CarrierShip extends ArmedSpaceShuttle{

	public CarrierShip(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent, size, orbitingDistance, speed);
		rocketsLeft=0; //No Rockets for CarrierShips
	}
	
	@Override
	public void shootLaser(SpaceObject targetSpaceObject) {
		if(laserCoolDown<=0) {
			new Laserbeam("Laser from " + name, this,degreeTo(targetSpaceObject),4);
			//@UpdateRatio 25ms its every 1.5 Seconds:
			laserCoolDown= 1500/25;
		}
	}
	
	@Override
	public boolean collides(CollidingObject other) {
		if(other instanceof Carrier && ((Carrier)other).getCurrentShips().contains(this))
			return false;
		if(other instanceof CarrierShip)
			return false;
		return super.collides(other);
	}
	@Override
	public void shootNextDestructible() {
		if(!sensor.detectedItems.isEmpty())
			sensor.detectedItems.stream()
				.filter(c->c instanceof DestructibleObject)
				.filter(c -> c instanceof SpaceObject)
				.filter(c -> ! (c instanceof Carrier)) // Papa i shot a man
				.filter(c -> ! (c instanceof CarrierShip)) 
				.forEach(c-> shootLaser((SpaceObject)c));
	}
	
	@Override
	public void destruct() {
		// NO SPACETRASH!!!
		System.out.println(toString() + " hit!");
		if(!isDead()) {
			new Explosion("Explosion from" + name,center,5,1500,1.02,new JavaFXDrawingInformation(Color.MEDIUMVIOLETRED));
			remove();
		}
	}
}
