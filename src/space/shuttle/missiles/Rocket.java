package space.shuttle.missiles;

import drawing.JavaFXDrawingContext;
import drawing.JavaFXDrawingInformation;
import geom.Rectangle;
import interfaces.drawing.DrawingContext;
import interfaces.logical.DestructibleObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import space.effect.Explosion;
import space.shuttle.SpaceShuttle;

@SuppressWarnings("restriction")
public class Rocket extends Missile implements DestructibleObject {

	public Rocket(String name, SpaceShuttle emitter, int size) {
		super(name, emitter,  new Rectangle(emitter.center,size,size*2),new JavaFXDrawingInformation(Color.FIREBRICK),emitter.rotation, emitter.speed);
		shape.setLevelOfDetail(2);
	}
	
	public Rocket(String name, SpaceShuttle emitter, int size,double rotation, int speed) {
		super(name, emitter, new Rectangle(emitter.center,size,size*2),new JavaFXDrawingInformation(Color.FIREBRICK),rotation,speed);
		shape.setLevelOfDetail(2);
	}
	
	@Override
	public void destruct() {
		new Explosion("Explosion from "+name, center, 5,1000, 1.02, new JavaFXDrawingInformation(Color.FIREBRICK));
		remove();
	}
	
}
