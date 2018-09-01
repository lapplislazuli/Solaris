/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package space.core
 */
package space.core;

import java.awt.LinearGradientPaint;
import java.util.Random;

import interfaces.logical.MovingObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

@SuppressWarnings({ "restriction", "unused" })
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
		if (color != null) {
			gc.setFill(new LinearGradient(0, 0, 0.8, 0.5, true, CycleMethod.NO_CYCLE, 
					new Stop(0.0, color),
					new Stop(1.0, color.darker())));
		}

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
