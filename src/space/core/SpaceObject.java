package space.core;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import interfaces.UpdatingObject;
import javafx.scene.canvas.GraphicsContext;

public abstract class SpaceObject {
	
	protected int x,y,size;
	String name;
	
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
	
	public void update(GraphicsContext gc) {
		draw(gc);
		for (MovingSpaceObject trabant : trabants){
			trabant.update(gc,x,y);
		}
	};
	protected void draw(GraphicsContext gc) {
		//Mockup for failed/missing implementation:
		System.out.println("I am " + name + " chilling at [" + x + "," + y + "]" );
	}
	public int getX() {return x;}
	public int getY() {return y;}
	
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
	public boolean collidesWith(SpaceObject other) {
		return (distanceTo(other)<=size/2+other.size/2);
	}
}
