package space;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.canvas.GraphicsContext;

public class SpaceObject {
	
	protected int x,y;
	String name;
	
	protected List<MovingSpaceObject> trabants;
	
	protected int size;
	
	protected float rotation; //in radiant-degree
	
	//img?
	
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
	
	public void update(GraphicsContext gc) {
		draw(gc);
		for (MovingSpaceObject trabant : trabants){
			trabant.update(gc,x,y);
		}
	};
	protected void draw(GraphicsContext gc) {
		//Draw me?
		
		//Mockup:
		System.out.println("I am " + name + " chilling at [" + x + "," + y + "]" );
	}
	public int getX() {return x;}
	public int getY() {return y;}
}
