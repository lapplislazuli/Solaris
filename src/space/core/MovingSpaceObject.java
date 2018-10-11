package space.core;

import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.util.Random;

import drawing.JavaFXDrawingContext;
import geom.TShape;
import geom.BaseShape;
import geom.AbsolutePoint;
import interfaces.drawing.DrawingContext;
import interfaces.geom.Shape;
import interfaces.logical.MovingObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;
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
		center.setY(parentCenter.getY()+(int)(Math.sin(relativePos)*distance));
		rotate();
	};
	
	public void moveRelativePos() {
		relativePos+=speed;
		if (relativePos >= Math.PI*2)
			relativePos -=  Math.PI*2;
		else if(relativePos< 0 )
			relativePos+= Math.PI*2;
	}
	
	public void rotate() {
		rotation+=rotationSpeed;
		if (rotation >= Math.PI*2)
			rotation -=  Math.PI*2;
		else if (rotation < 0) {
			rotation += Math.PI*2;
		}
	}
	
	public boolean isFasterThanMe(SpaceObject other) {
		if(other instanceof MovingSpaceObject) {
			MovingSpaceObject otherCasted= (MovingSpaceObject) other;
			return 	speed!=0
					&&otherCasted.speed!=0
					&&Math.abs(otherCasted.speed)>Math.abs(speed);
		}
		return false;
	}
	
	public boolean movesInSameDirection(SpaceObject other) {
		if(other instanceof MovingSpaceObject) {
			MovingSpaceObject otherCasted= (MovingSpaceObject) other;
			return 	otherCasted.speed>0&&speed>0 || otherCasted.speed<0&&speed<0;
		}
		return false;
	}
	
	@Override
	public void drawShape(DrawingContext dc) {
		
		if(dc instanceof JavaFXDrawingContext) {
			GraphicsContext gc = ((JavaFXDrawingContext)dc).getGraphicsContext();
			gc.save();
			Affine transformRotation= new Affine();
			transformRotation.appendRotation(Math.toDegrees(rotation), center.getX() ,center.getY());	
			if (color != null) {
				gc.setFill(new LinearGradient(0, 0, 0.8, 0.5, true, CycleMethod.NO_CYCLE, 
						new Stop(0.0, color),
						new Stop(1.0, color.darker())));
			}
			gc.transform(transformRotation);	
		}
		
		super.drawShape(dc);
	}
	
}
