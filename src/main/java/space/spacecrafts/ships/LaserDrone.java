package space.spacecrafts.ships;

import drawing.JavaFXDrawingInformation;
import geom.UShape;
import interfaces.logical.CollidingObject;
import interfaces.spacecraft.CarrierDrone;
import interfaces.spacecraft.SpacecraftState;
import javafx.scene.paint.Color;
import space.core.SpaceObject;
import space.effect.Explosion;

public class LaserDrone extends Spaceshuttle implements CarrierDrone{
	
	public static final double STANDARD_REBUILD_TIME = 10000/25; //That is ~10 seconds with an update rate of 25ms
	
	public LaserDrone(String name, SpaceObject parent, int size, int orbitingDistance, double speed) {
		super(name, parent,new JavaFXDrawingInformation(Color.BLANCHEDALMOND), new UShape(size*2,size*2,size/2), size, orbitingDistance, speed);
		this.shape.setLevelOfDetail(2);
	}

	
	@Override
	public boolean collides(CollidingObject other) {
		if(super.collides(other)) {
			if(other instanceof Spaceshuttle && ((Spaceshuttle)other).getDrones().contains(this))
				return false;
			if(other instanceof LaserDrone)
				return false;	
			return true;
		}
		return false;
	}

	@Override
	public LaserDrone rebuildAt(String name,SpaceObject at) {
		return new LaserDrone(name,at,this.size,(int)this.orbitingDistance,this.speed);	
	}
	
	@Override
	public void destruct() {
		if(!isDead()) {
			state = SpacecraftState.DEAD;
			// No SpaceTrash!
			new Explosion("Explosion from" + name,center,5,1500,1.02,new JavaFXDrawingInformation(Color.MEDIUMVIOLETRED));
			remove();
		}
	}
}
