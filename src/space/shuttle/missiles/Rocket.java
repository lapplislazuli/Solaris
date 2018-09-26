package space.shuttle.missiles;

import geom.Rectangle;
import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import space.effect.Explosion;
import space.shuttle.SpaceShuttle;

@SuppressWarnings("restriction")
public class Rocket extends Missile implements DestructibleObject {

	public Rocket(String name, SpaceShuttle emitter, int size) {
		super(name, emitter,  new Rectangle(emitter.center,size,size*2),emitter.rotation, emitter.speed);
		color=Color.FIREBRICK;
	}
	
	public Rocket(String name, SpaceShuttle emitter, int size,double rotation, int speed) {
		super(name, emitter, new Rectangle(emitter.center,size,size*2),rotation,speed);
		color=Color.FIREBRICK;
	}
	
	@Override
	public void destruct(CollidingObject other) {
		System.out.println(name+" collided with " + other.toString());
		new Explosion("Explosion from "+name, center.x, center.y,1000, 5, 1.02, Color.FIREBRICK);
		remove();
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		drawThisItem(gc);
	}
}
