package space.shuttle;


import java.util.LinkedList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

@SuppressWarnings("restriction")
public class Laserbeam extends Missile{

	private List<Integer> xTrail,yTrail;
	
	public Laserbeam(String name, SpaceShuttle emitter) {
		super(name, emitter, 2, 3);
		color = Color.LIGHTGREEN;
		xTrail=new LinkedList<Integer>();
		yTrail=new LinkedList<Integer>();
	}
	
	public Laserbeam(String name, SpaceShuttle emitter,  double direction, int speed) {
		super(name, emitter, 2,direction, speed);
		color = Color.LIGHTGREEN;
		xTrail=new LinkedList<Integer>();
		yTrail=new LinkedList<Integer>();
	}

	@Override
	public void move(int parentX, int parentY) {
		xTrail.add(x);yTrail.add(y);
		super.move(parentX, parentY);
	}
	
	@Override 
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		for(int i=0; i<xTrail.size(); i++) {
			gc.fillOval(xTrail.get(i),yTrail.get(i), size, size);
		}
	}
}
