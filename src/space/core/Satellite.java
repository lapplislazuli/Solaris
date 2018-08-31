package space.core;

import interfaces.CollidingObject;
import interfaces.DestructibleObject;
import javafx.scene.paint.Color;
import space.advanced.Asteroid;
import space.effect.Explosion;

public class Satellite extends MovingSpaceObject implements DestructibleObject{
	private SpaceObject parent;
	public Satellite(String name, SpaceObject parent, int size, int distance, double speed) {
		super(name, parent,null, size, distance, speed);
		this.parent=parent;
	}
	@Override
	public void move(int parentX, int parentY) {
		super.move(parentX, parentY);
		//ToDo: Change my Rotation
	}
	@Override
	public void remove() {
		parent.removeTrabant(this);
		parent=null;		
	}
	@Override
	public void destruct(CollidingObject other) {
		System.out.println("Satelite: " + name + " collided with " + other.toString() + " @" + x + "|" + y);
		Explosion explosion = new Explosion("Explosion from" + name,x,y,1000,20,Color.MEDIUMVIOLETRED);
		if(parent!=null)
			remove();
	}
}
