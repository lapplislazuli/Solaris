/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package space.shuttle
 */
package space.shuttle.missiles;

import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import space.effect.Explosion;
import space.shuttle.SpaceShuttle;

@SuppressWarnings("restriction")
public class Rocket extends Missile implements DestructibleObject {

	public Rocket(String name, SpaceShuttle emitter, int size) {
		super(name, emitter, size,emitter.rotation, emitter.speed);
		color=Color.FIREBRICK;
	}
	
	public Rocket(String name, SpaceShuttle emitter, int size,double rotation, int speed) {
		super(name, emitter, size,rotation,speed);
		color=Color.FIREBRICK;
	}
	
	@Override
	public void destruct(CollidingObject other) {
		System.out.println(name+" collided with " + other.toString());
		new Explosion("Explosion from "+name, center.x, center.y,1000, size*3, 1.02, Color.FIREBRICK);
		remove();
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillRect(center.x-size/2,center.y-size/2, size, size);
	}
}
