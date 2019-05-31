package space.core;

import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.util.Random;

import drawing.JavaFXDrawingContext;
import drawing.JavaFXDrawingInformation;
import geom.TShape;
import geom.BaseShape;
import geom.AbsolutePoint;
import interfaces.drawing.DrawingContext;
import interfaces.drawing.DrawingInformation;
import interfaces.geom.Point;
import interfaces.geom.Shape;
import interfaces.logical.MovingObject;
import interfaces.logical.MovingUpdatingObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.transform.Affine;

@SuppressWarnings({ "restriction", "unused" })
public abstract class MovingSpaceObject extends SpaceObject implements MovingUpdatingObject {	
	protected int distance;
	protected double speed, relativePos,rotation, rotationSpeed; //Everything in Radians
	
	public MovingSpaceObject(String name,SpaceObject parent,DrawingInformation dInfo, Shape shape,int distance, double speed) {
		super(name,parent.center.clone(),shape,dInfo);
		
		this.distance=distance;
		this.speed=speed;
		rotationSpeed=speed*2;
		
		relativePos=degreeTo(parent);
		parent.trabants.add(this);
		center.setY(parent.center.getY()+distance);
	}
	
	public void move(Point parentCenter) {
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
	public void draw(DrawingContext dc) {
		updateDrawingInformation();
		super.draw(dc);
	}
	
	protected void updateDrawingInformation() {
		DrawingInformation dInfo=getDrawingInformation() ;
		if(dInfo  instanceof JavaFXDrawingInformation) {
			Affine transformRotation= new Affine();
			transformRotation.appendRotation(Math.toDegrees(rotation), center.getX() ,center.getY());	
			((JavaFXDrawingInformation)dInfo).transformations.clear();
			((JavaFXDrawingInformation)dInfo).transformations.add(transformRotation);
		}
	}

	public int getDistance() {return distance;}
	public void setDistance(int distance) {this.distance = distance;}
	public double getSpeed() {return speed;}
	public void setSpeed(double speed) {this.speed = speed;}
	public double getRelativePos() {return relativePos;}
	public void setRelativePos(double relativePos) {this.relativePos = relativePos;}
	public double getRotation() {return rotation;}
	public void setRotation(double rotation) {this.rotation = rotation;}
	public double getRotationSpeed() {return rotationSpeed;}
	public void setRotationSpeed(double rotationSpeed) {this.rotationSpeed = rotationSpeed;}

	
}
