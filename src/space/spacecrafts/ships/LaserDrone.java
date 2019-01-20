package space.spacecrafts.ships;

import java.util.Optional;

import drawing.JavaFXDrawingInformation;
import geom.UShape;
import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import interfaces.spacecraft.CarrierDrone;
import interfaces.spacecraft.SpacecraftState;
import javafx.scene.paint.Color;
import space.core.SpaceObject;
import space.effect.Explosion;
import space.spacecrafts.ships.missiles.Laserbeam;

@SuppressWarnings("restriction")
public class LaserDrone extends ArmedSpaceShuttle implements CarrierDrone{

	public LaserDrone(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent,new JavaFXDrawingInformation(Color.BLANCHEDALMOND), new UShape(size*2,size*2,size/2), size, orbitingDistance, speed);
		rocketsLeft=0; //No Rockets for CarrierShips
		System.out.println("Drone " + toString()  + " build");
		this.shape.setLevelOfDetail(2);
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
		if(super.collides(other)) {
			if(other instanceof Carrier && ((Carrier)other).getCurrentShips().contains(this))
				return false;
			if(other instanceof LaserDrone)
				return false;	
			return true;
		}
		return false;
	}

	@Override
	public Optional<SpaceObject> getNearestPossibleTarget() {
		Optional<SpaceObject> possibleTarget = Optional.empty();
		if(!sensor.detectedItems.isEmpty())
			possibleTarget=sensor.detectedItems.stream()
				.filter(c->c instanceof DestructibleObject)
				.filter(c -> c instanceof SpaceObject)
				.map(c-> (SpaceObject)c)
				.filter(c -> ! (c instanceof Carrier)) // Papa i shot a man
				.filter(c -> ! (c instanceof LaserDrone)) 
				.findFirst();
		return possibleTarget;
	}
	
	@Override
	public void destruct() {
		if(!isDead()) {
			state = SpacecraftState.DEAD;
			System.out.println(toString() + " died!");
			// No SpaceTrash!
			new Explosion("Explosion from" + name,center,5,1500,1.02,new JavaFXDrawingInformation(Color.MEDIUMVIOLETRED));
			remove();
		}
	}
}
