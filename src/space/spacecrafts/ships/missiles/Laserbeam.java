package space.spacecrafts.ships.missiles;

import java.util.LinkedList;
import java.util.List;

import drawing.JavaFXDrawingContext;
import drawing.JavaFXDrawingInformation;
import geom.Circle;
import interfaces.drawing.DrawingContext;
import geom.AbsolutePoint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import space.spacecrafts.ships.SpaceShuttle;

@SuppressWarnings("restriction")
public class Laserbeam extends Missile{

	private List<AbsolutePoint> trail=new LinkedList<AbsolutePoint>();
	
	public Laserbeam(String name, SpaceShuttle emitter) {
		super(name, emitter,new Circle(emitter.center,2), new JavaFXDrawingInformation(Color.LIGHTGREEN), emitter.rotation, 3);
		
	}
	
	public Laserbeam(String name, SpaceShuttle emitter,  double direction, int speed) {
		super(name, emitter, new Circle(emitter.center,2),new JavaFXDrawingInformation(Color.LIGHTGREEN),direction, speed);
	}

	@Override
	public void move(AbsolutePoint oldPosition) {
	    trail.add(center.clone());
		super.move(oldPosition);
	}
	
	@Override
	public void drawShape(DrawingContext dc) {
		if(dc instanceof JavaFXDrawingContext) 
			for(int i=0; i<trail.size(); i++) 
				((JavaFXDrawingContext)dc).getGraphicsContext().fillOval(trail.get(i).getX(),trail.get(i).getY(), 2, 2);
	}
}
