package space.core;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import drawing.EmptyJFXDrawingInformation;
import interfaces.drawing.ComplexDrawingObject;
import interfaces.drawing.DrawingContext;
import interfaces.drawing.DrawingInformation;
import interfaces.drawing.DrawingObject;
import interfaces.geom.Point;
import interfaces.geom.Shape;
import interfaces.interaction.ClickableObject;
import interfaces.logical.CollidingObject;
import interfaces.logical.MovingUpdatingObject;
import interfaces.logical.RecursiveObject;
import interfaces.logical.UpdatingObject;

public abstract class SpaceObject implements UpdatingObject, ClickableObject, CollidingObject, ComplexDrawingObject,RecursiveObject{
	protected Shape shape;
	protected String name;
	protected Point center;

	protected static Logger logger = LogManager.getLogger(SpaceObject.class);
	
	protected DrawingInformation dInfo;
	
	protected List<MovingUpdatingObject> trabants=new LinkedList<MovingUpdatingObject>();
	protected float rotation = 0; //in radiant-degree
	
	public SpaceObject(String name,Point center, Shape shape, DrawingInformation dInfo) {
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
		logger.debug("Build " + name + center.toString());
	}
	
	public void update() {
		for (MovingUpdatingObject trabant : trabants){
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
		trabants.stream()
			.filter(t-> t instanceof DrawingObject)
			.map(t -> (DrawingObject) t)
			.forEach(trabant->trabant.draw(dc));
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
	
	public Collection<RecursiveObject> getAllChildren(){
		Collection<RecursiveObject> flatChildren = new LinkedList<RecursiveObject>();
		for(MovingUpdatingObject trabant : trabants)
			if(trabant instanceof RecursiveObject)
				flatChildren.addAll(((RecursiveObject)trabant).getAllChildren());
		flatChildren.add(this);
		return flatChildren;
	}
	
	public void click() {
		logger.info("Clicked: " + toString());
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
	
	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public List<MovingUpdatingObject> getTrabants() {
		return trabants;
	}

	public void setTrabants(List<MovingUpdatingObject> trabants) {
		this.trabants = trabants;
	}

}
