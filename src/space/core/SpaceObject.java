/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package space.core
 */
package space.core;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import interfaces.ClickableObject;
import interfaces.logical.CollidingObject;
import interfaces.logical.UpdatingObject;
import javafx.scene.canvas.GraphicsContext;
import space.advanced.FixStar;

@SuppressWarnings("restriction")
public abstract class SpaceObject implements UpdatingObject, ClickableObject, CollidingObject{
	
	public int x,y,size;
	public String name;
	
	public List<MovingSpaceObject> trabants;
	protected float rotation; //in radiant-degree
	
	public SpaceObject(String name,int x, int y, int size) {
		this.name=name;
		this.x=x;
		this.y=y;
		this.size=size;
		
		trabants= new LinkedList<MovingSpaceObject>();
		rotation=0;
	}
	
	public void update() {
		for (MovingSpaceObject trabant : trabants){
			trabant.move(x,y);
			trabant.update();
		}
	};
	
	public void draw(GraphicsContext gc) {
		gc.setEffect(null);
		gc.setFill(null);
		for (MovingSpaceObject trabant : trabants) 
			trabant.draw(gc);
	}
	@Override
	public String toString() {return name+"@"+x+"|"+y;}
	
	public double distanceTo(SpaceObject other) {
		return Math.sqrt((x-other.x)*(x-other.x)+(y-other.y)*(y-other.y));
	}
	
	public double degreeTo(SpaceObject other) {
		if(distanceTo(other)==0) 
			return 0;
		if(y<other.y)
			return Math.acos((x-other.x)/distanceTo(other));
		else
			return 2*Math.PI-Math.acos((x-other.x)/distanceTo(other));
	}
	
	public boolean collides(CollidingObject other) {
		if(other instanceof SpaceObject)	
			if(other!=this)
				if(distanceTo((SpaceObject)other)<size/2+((SpaceObject)other).size/2)
					return true;
		return false;
	}
	
	public List<SpaceObject> getChildren(){
		List<SpaceObject> flatChildren = new LinkedList<SpaceObject>();
		for(SpaceObject trabant : trabants)
			flatChildren.addAll(trabant.getChildren());
		flatChildren.add(this);
		return flatChildren;
	}
	
	public void click() {
		System.out.println("Clicked: " + toString());
	}
	
	public boolean isCovered(int x, int y) {
		return
				y>=this.y-size && y<=this.y+size
			&&	x>=this.x-size && x<=this.x+size;
	}
}
