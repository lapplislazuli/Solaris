/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package space.shuttle
 */
package space.shuttle.missiles;


import java.util.LinkedList;
import java.util.List;

import geom.Circle;
import geom.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import space.shuttle.SpaceShuttle;

@SuppressWarnings("restriction")
public class Laserbeam extends Missile{

	private List<Point> trail=new LinkedList<Point>();
	
	public Laserbeam(String name, SpaceShuttle emitter) {
		super(name, emitter, new Circle(emitter.center,2), emitter.rotation, 3);
		color = Color.LIGHTGREEN;
	}
	
	public Laserbeam(String name, SpaceShuttle emitter,  double direction, int speed) {
		super(name, emitter, new Circle(emitter.center,2),direction, speed);
		color = Color.LIGHTGREEN;
	}

	@Override
	public void move(Point oldPosition) {
	    trail.add(center.clone());
		super.move(oldPosition);
	}
	
	@Override 
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		for(int i=0; i<trail.size(); i++) {
			gc.fillOval(trail.get(i).x,trail.get(i).y, 2, 2);
		}
	}
}
