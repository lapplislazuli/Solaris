package space.core;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import interfaces.CollidingObject;
import interfaces.DrawingObject;
import interfaces.NamedObject;
import interfaces.UpdatingObject;
import javafx.scene.canvas.GraphicsContext;

public abstract class SpaceObject implements UpdatingObject, DrawingObject, CollidingObject, NamedObject{
	
	protected int x,y,size;
	protected String name;
	
	protected List<MovingSpaceObject> trabants;
	protected float rotation; //in radiant-degree
	
	public SpaceObject(String name,int x, int y, int size) {
		this.name=name;
		this.x=x;
		this.y=y;
		this.size=size;
		
		trabants= new LinkedList<MovingSpaceObject>();
		rotation=0;
	}
	
	public void addTrabant(MovingSpaceObject newTrabant) {
		trabants.add(newTrabant);
	}
	
	public MovingSpaceObject getTrabant(String name) {
		return trabants.stream()
				.filter(t -> t.name==name)
				.collect(Collectors.toList())
				.get(0);
	}
	
	public boolean removeTrabant(MovingSpaceObject trabant) {
		return trabants.remove(trabant);
	}
	
	public void update() {
		for (MovingSpaceObject trabant : trabants){
			trabant.move(x,y);
			trabant.update();
		}
	};
	
	public void draw(GraphicsContext gc) {
		for (MovingSpaceObject trabant : trabants) 
			trabant.draw(gc);
	}
	
	public int getX() {return x;}
	public int getY() {return y;}
	
	public String getName() {return name;}
	
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
		if(other instanceof SpaceObject){
			return (distanceTo((SpaceObject)other)<=size/2+((SpaceObject)other).size/2);
		}
		return false;
			
	}
	public int getSize() {
		return size;
	}
	public List<SpaceObject> getChildren(){
		List<SpaceObject> flatChildren = new LinkedList<SpaceObject>();
		for(SpaceObject trabant : trabants)
			flatChildren.addAll(trabant.getChildren());
		flatChildren.add(this);
		return flatChildren;
	}
}
