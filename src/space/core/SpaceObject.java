package space.core;
import java.util.LinkedList;
import java.util.List;

import org.pmw.tinylog.Logger;

import drawing.EmptyJFXDrawingInformation;
import geom.AbsolutePoint;
import interfaces.drawing.ComplexDrawingObject;
import interfaces.drawing.DrawingContext;
import interfaces.drawing.DrawingInformation;
import interfaces.geom.Shape;
import interfaces.interaction.ClickableObject;
import interfaces.logical.CollidingObject;
import interfaces.logical.UpdatingObject;

public abstract class SpaceObject implements UpdatingObject, ClickableObject, CollidingObject, ComplexDrawingObject{
	public Shape shape;
	public String name;
	public AbsolutePoint center;
	
	public DrawingInformation dInfo;
	
	public List<MovingSpaceObject> trabants=new LinkedList<MovingSpaceObject>();
	protected float rotation = 0; //in radiant-degree
	
	public SpaceObject(String name,AbsolutePoint center, Shape shape, DrawingInformation dInfo) {
		if(name==null||name.isEmpty())
			throw new IllegalArgumentException("Requires Name!");
		this.name=name;
		
		this.center=center;
		this.shape=shape;
		this.shape.setCenter(center); //To center the Area around this object - improvement possible
		if(dInfo==null)
			dInfo=new EmptyJFXDrawingInformation();
		else
			this.dInfo=dInfo;
		Logger.debug("Build " + name + center.toString());
	}
	
	public void update() {
		for (MovingSpaceObject trabant : trabants){
			trabant.move(center);
			trabant.update();
		}
	};
	
	public void draw(DrawingContext dc) {
		if(dInfo==null)
			throw new UnsupportedOperationException("Empty DrawingInformation");
		dc.saveContext();
		dInfo.applyDrawingInformation(dc);
		drawShape(dc);
		dc.resetContext();
		drawTrabants(dc);
	}
	
	public void drawShape(DrawingContext dc) {
		shape.draw(dc);
	};
	
	protected void drawTrabants(DrawingContext dc){
		for (MovingSpaceObject trabant : trabants) 
			trabant.draw(dc);
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
	
	public void click() {
		Logger.info("Clicked: " + toString());
	}
	
	public void updateHitbox() {
		shape.updateOrInitOutline();
	}
	
	@Override
	public String toString() {return name+"@"+center.toString();}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof SpaceObject)) 
			return false;
		SpaceObject sO = (SpaceObject) o;
		if(sO == this)
			return true;
		return
			name==sO.name 
			&& rotation==sO.rotation
			&& center.samePosition(sO.center)
			&& shape.sameShape(sO.shape);
	}
	
	@Override
	public int hashCode() {
		int	result=name.hashCode();
		result=31*result+Double.hashCode(rotation);
		result=31*result+center.hashCode();
	
		return result;
	}

	public DrawingInformation getDrawingInformation() {
		return dInfo;
	}
}
