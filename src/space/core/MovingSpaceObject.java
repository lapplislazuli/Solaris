package space.core;

import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.util.Random;

import geom.TShape;
import geom.BaseShape;
import geom.AbsolutePoint;
import interfaces.geom.Shape;
import interfaces.logical.MovingObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.transform.Affine;

@SuppressWarnings({ "restriction", "unused" })
public abstract class MovingSpaceObject extends SpaceObject implements MovingObject {	
	public int distance;
	public double speed, relativePos,rotation, rotationSpeed; //Everything in Radians
	protected Color color;
	
	public MovingSpaceObject(String name,SpaceObject parent,Color color, Shape shape,int distance, double speed) {
		super(name,parent.center.clone(),shape);
		this.distance=distance;
		this.speed=speed;
		rotationSpeed=speed*2;
		this.color=color;
		relativePos=degreeTo(parent);
		parent.trabants.add(this);
		center.move(0, distance);;
	}
	
	public void move(AbsolutePoint parentCenter) {
		moveRelativePos();
		center.setX(parentCenter.getX()+(int)(Math.cos(relativePos)*distance));
		center.setY(parentCenter.getY()-(int)(Math.sin(relativePos)*distance));
		rotate();
	};
	
	public void moveRelativePos() {
		relativePos+=speed;
		if (relativePos >= Math.PI*2)
			relativePos -=  Math.PI*2;
		else if(relativePos<= Math.PI*2)
			relativePos+= Math.PI*2;
	}
	
	public void rotate() {
		rotation+=rotationSpeed;
		if (rotation >= Math.PI*2)
			rotation -=  Math.PI*2;
		else if (rotation <= -Math.PI*2) {
			rotation += Math.PI*2;
		}
	}

	@Override
	public void drawThisItem(GraphicsContext gc) {
		gc.save();
		Affine transformRotation= new Affine();
		transformRotation.appendRotation(Math.toDegrees(rotation), center.getX() ,center.getY());	
		if (color != null) {
			gc.setFill(new LinearGradient(0, 0, 0.8, 0.5, true, CycleMethod.NO_CYCLE, 
					new Stop(0.0, color),
					new Stop(1.0, color.darker())));
		}
		gc.transform(transformRotation);	
		super.drawThisItem(gc);
		gc.restore();
	}
	
}
