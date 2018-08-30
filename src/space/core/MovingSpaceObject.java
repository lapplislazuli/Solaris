package space.core;

import interfaces.MovingObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

@SuppressWarnings("restriction")
public abstract class MovingSpaceObject extends SpaceObject implements MovingObject {
	
	
	protected int distance;
	protected double speed, relativePos,rotation; //Pos in radiant-degree to parent
	protected Color color;
	//Init Relative to parent
	//Add to parents Trabants
	public MovingSpaceObject(String name,SpaceObject parent,Color color, int size,int distance, double speed) {
		super(name,parent.getX(),parent.getY()+distance,size);
		rotation=0;
		this.distance=distance;
		this.speed=speed;
		this.color=color;
		relativePos=degreeTo(parent);
		parent.addTrabant(this);
	}
	public void move(int parentX, int parentY) {
		// rotate relative position
		relativePos+=speed;
		if (relativePos >= Math.PI*2)
			relativePos -=  Math.PI*2;
		//Change my Koords, according to parent, distance and speed
		x = (parentX+ (int)(Math.cos(relativePos)*distance));
		y = (parentY- (int)(Math.sin(relativePos)*distance));
		
		rotate();
	};
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		//change so the center is drawn, not the corner
		gc.fillOval(x-size/2, y-size/2, size, size);
		super.draw(gc);
	}
	
	public void rotate() {
		rotation+=speed;
		if (rotation >= Math.PI*2)
			rotation -=  Math.PI*2;
		else if (rotation <= -Math.PI*2) {
			rotation += Math.PI*2;
		}
	}
	
	public void setRelativePos(double newPos) {
		this.relativePos=newPos;
	}
	
	public double getRelativePos() {return relativePos;}
	public double getRotation() {return rotation;}
	public int getDistance() {return distance;}
	public double getSpeed() {return speed;}
}
