package space.shuttle;

import interfaces.CollidingObject;
import interfaces.DestructibleObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

@SuppressWarnings("restriction")
public class Rocket extends Missile implements DestructibleObject {

	public Rocket(String name, SpaceShuttle emitter, int size) {
		super(name, emitter, size);
		color=Color.FIREBRICK;
		System.out.println("Rocket shot! " + toString());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void destruct(CollidingObject other) {
		System.out.println(name+" collided with " + other.toString());
		//ToDo: Explosion here
		remove();
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillRect(x-size/2,y-size/2, size, size);
	}
}
