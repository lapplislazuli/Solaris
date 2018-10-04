package space.advanced;

import geom.AbsolutePoint;
import geom.Rectangle;
import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import javafx.scene.paint.Color;
import space.core.MovingSpaceObject;
import space.core.SpaceObject;

@SuppressWarnings("restriction")
public class Asteroid extends MovingSpaceObject implements DestructibleObject{
	
	SpaceObject parent;
	public enum Type{ORE, ROCK, TRASH;}
	Type type;
	public Asteroid(String name, SpaceObject parent,int distance, double speed) {
		super(name,parent,null,new Rectangle(3,3),distance,speed);
		//Asteroid Types are chosen randomly if not in the Constructor
		int typeHelper=((int)((Math.random()+1)*3)%3);
		if(typeHelper==0)
			type=Type.ORE;
		else if (typeHelper==1)
			type=Type.ROCK;
		else
			type=Type.TRASH;
		setColorFromType();

		shape.setLevelOfDetail(2);
	}
	//Constructor for only one type of Asteroids in the belt
	public Asteroid(String name, SpaceObject parent,int distance, double speed,Type type) {
		super(name,parent,null,new Rectangle(4,5),distance,speed);
		this.type=type;
		this.parent=parent;
	}
	
	private void setColorFromType() {
		switch(type) {
		case ORE:
			color=Color.DARKSLATEGRAY;
			break;
		case ROCK:
			color=Color.LIGHTGRAY;
			break;
		case TRASH:
			color=Color.GRAY;
			break;
		default:
			color=Color.BLACK;
			break;
		}
	}
	
	@Override 
	public void move(AbsolutePoint parentCenter) {
		super.move(parentCenter);
		shake();
	}
	
	//Change my koords by a little noise, so its not a perfect circle
	private void shake() {
		center.move((int)(Math.random()+1)*3,(int)(Math.random()+1)*3);
	}
	
	public void destruct() {
		System.out.println(toString() + " got destroyed");
		if(parent!=null)
			remove();
	}	
	
	public void remove() {
		parent.trabants.remove(this);
		parent=null;
	}
}
