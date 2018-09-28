package space.core;
import java.util.LinkedList;
import java.util.List;

import geom.BaseShape;
import geom.Point;
import geom.AbsolutePoint;
import geom.Shape;
import interfaces.ClickableObject;
import interfaces.logical.CollidingObject;
import interfaces.logical.UpdatingObject;
import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("restriction")
public abstract class SpaceObject implements UpdatingObject, ClickableObject, CollidingObject{
	public Shape shape;
	public String name;
	public AbsolutePoint center;
	
	public List<MovingSpaceObject> trabants=new LinkedList<MovingSpaceObject>();
	protected float rotation = 0; //in radiant-degree
	
	public SpaceObject(String name,AbsolutePoint center, Shape shape) {
		this.name=name;
		this.center=center;
		this.shape=shape;
		this.shape.setCenter(center); //To center the Area around this object - improvement possible
	}
	
	public void update() {
		for (MovingSpaceObject trabant : trabants){
			trabant.move(center);
			trabant.update();
		}
	};
	
	public void draw(GraphicsContext gc) {
		drawThisItem(gc);
		resetGraphicsContext(gc);
		drawTrabants(gc);
	}
	
	public void drawThisItem(GraphicsContext gc) {
		shape.draw(gc);
	};
	
	protected void resetGraphicsContext(GraphicsContext gc) {
		gc.setEffect(null);
		gc.setFill(null);
	}
	
	protected void drawTrabants(GraphicsContext gc){
		for (MovingSpaceObject trabant : trabants) 
			trabant.draw(gc);
	}
	
	public double distanceTo(SpaceObject other) {
		return center.distanceTo(other.center);
	}
	
	public double degreeTo(SpaceObject other) {
		return center.degreeTo(other.center);
	}
	public boolean collides(CollidingObject other) {
		if(other instanceof SpaceObject && other!=this)
				return shape.intersects(((SpaceObject)other).shape);
		return false;
	}
	
	public List<SpaceObject> getAllChildrenFlat(){
		List<SpaceObject> flatChildren = new LinkedList<SpaceObject>();
		for(SpaceObject trabant : trabants)
			flatChildren.addAll(trabant.getAllChildrenFlat());
		flatChildren.add(this);
		return flatChildren;
	}
	
	public boolean isCovered(int x, int y) {
		return shape.isCovered(x, y);
	}
	
	public void click() {
		System.out.println("Clicked: " + toString());
	}
	
	public void updateHitbox() {
		shape.initOutline();
	}
	
	@Override
	public String toString() {return name+"@"+center.toString();}
	
}
