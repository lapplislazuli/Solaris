package space.advanced;

import org.pmw.tinylog.Logger;

import drawing.EmptyJFXDrawingInformation;
import drawing.JavaFXDrawingInformation;
import geom.AbsolutePoint;
import geom.Rectangle;
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
		super(name,parent,new EmptyJFXDrawingInformation(null),new Rectangle(3,3),distance,speed);
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
		super(name,parent,new EmptyJFXDrawingInformation(null),new Rectangle(4,5),distance,speed);
		this.type=type;
		this.parent=parent;
		setColorFromType();
	}
	
	private void setColorFromType() {
		switch(type) {
		case ORE:
			dInfo=new JavaFXDrawingInformation(Color.DARKSLATEGRAY);
			break;
		case ROCK:
			dInfo=new JavaFXDrawingInformation(Color.LIGHTGRAY);
			break;
		case TRASH:
			dInfo=new JavaFXDrawingInformation(Color.GRAY);
			break;
		default:
			dInfo=new JavaFXDrawingInformation(Color.BLACK);
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
		Logger.trace(toString() + " got destroyed");
		if(parent!=null)
			remove();
	}	
	
	public void remove() {
		parent.trabants.remove(this);
		parent=null;
	}
}
