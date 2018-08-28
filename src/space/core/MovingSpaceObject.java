package space.core;

import interfaces.MovingObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class MovingSpaceObject extends SpaceObject implements MovingObject {
	
	
	protected int distance;
	protected double speed, relativePos; //Pos in radiant-degree to parent
	protected Color color;
	//Init Relative to parent
	//Add to parents Trabants
	@SuppressWarnings("restriction")
	public MovingSpaceObject(String name,SpaceObject parent,Color color, int size,int distance, double speed) {
		super(name,parent.getX(),parent.getY()+distance,size);
		
		this.distance=distance;
		this.speed=speed;
		this.color=color;
		relativePos=degreeTo(parent);
		//relativePos=0;
		parent.addTrabant(this);
	}
	public void move(int parentX, int parentY) {
		// rotate relative position
		relativePos+=speed;
		if (relativePos >= Math.PI*2)
			relativePos -=  Math.PI*2;
		
		x = (parentX+ (int)(Math.cos(relativePos)*distance));
		y = (parentY- (int)(Math.sin(relativePos)*distance));
		//Change my Koords, according to parent, distance and speed
	};
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		//change so the center is drawn, not the corner
		gc.fillOval(x-size/4, y-size/4, size/2, size/2);
		super.draw(gc);
	}
	
	public void setRelativePos(double newPos) {
		this.relativePos=newPos;
	}
}
