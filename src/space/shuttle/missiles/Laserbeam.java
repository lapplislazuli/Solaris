package space.shuttle.missiles;

import java.util.LinkedList;
import java.util.List;

import drawing.JavaFXDrawingContext;
import geom.Circle;
import interfaces.drawing.DrawingContext;
import geom.AbsolutePoint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import space.shuttle.SpaceShuttle;

@SuppressWarnings("restriction")
public class Laserbeam extends Missile{

	private List<AbsolutePoint> trail=new LinkedList<AbsolutePoint>();
	
	public Laserbeam(String name, SpaceShuttle emitter) {
		super(name, emitter, new Circle(emitter.center,2), emitter.rotation, 3);
		color = Color.LIGHTGREEN;
	}
	
	public Laserbeam(String name, SpaceShuttle emitter,  double direction, int speed) {
		super(name, emitter, new Circle(emitter.center,2),direction, speed);
		color = Color.LIGHTGREEN;
	}

	@Override
	public void move(AbsolutePoint oldPosition) {
	    trail.add(center.clone());
		super.move(oldPosition);
	}
	
	@Override 
	public void draw(DrawingContext gc) {
		if(gc instanceof JavaFXDrawingContext) {
			((JavaFXDrawingContext)gc).getGraphicsContext().setFill(color);
			for(int i=0; i<trail.size(); i++) {
				((JavaFXDrawingContext)gc).getGraphicsContext().fillOval(trail.get(i).getX(),trail.get(i).getY(), 2, 2);
			}
		}
	}
}
