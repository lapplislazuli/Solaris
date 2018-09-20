/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package space.core
 */
package space.core;
import java.util.LinkedList;
import java.util.List;

import geom.BaseArea;
import geom.Point;
import interfaces.ClickableObject;
import interfaces.logical.CollidingObject;
import interfaces.logical.UpdatingObject;
import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("restriction")
public abstract class SpaceObject implements UpdatingObject, ClickableObject, CollidingObject{
	public BaseArea area;
	public String name;
	public Point center;
	
	public List<MovingSpaceObject> trabants=new LinkedList<MovingSpaceObject>();
	protected float rotation = 0; //in radiant-degree
	
	public SpaceObject(String name,Point center, BaseArea area) {
		this.name=name;
		this.center=center;
		this.area=area;
		area.center=center;
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
		area.draw(gc);
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
				return area.intersects(((SpaceObject)other).area);
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
		return area.isCovered(x, y);
	}
	
	public void click() {
		System.out.println("Clicked: " + toString());
	}
	
	public void updateHitbox() {
		area.initOutline();
	}
	
	@Override
	public String toString() {return name+"@"+center.toString();}
	
	
}
