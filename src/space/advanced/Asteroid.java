package space.advanced;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import space.core.MovingSpaceObject;
import space.core.SpaceObject;

@SuppressWarnings("restriction")
public class Asteroid extends MovingSpaceObject{
	
	enum Type            
	{
	   ORE, ROCK, TRASH;  
	}
	Type type;
	public Asteroid(String name, SpaceObject parent,int distance, double speed) {
		super(name,parent,null,5,distance,speed);
		int typeHelper=((int)((Math.random()+1)*3)%3);
		if(typeHelper==0)
			type=Type.ORE;
		else if (typeHelper==1)
			type=Type.ROCK;
		else
			type=Type.TRASH;
		//Asteroid Types are chosen randomly
		
	}
	//Constructor for only one type of Asteroids in the belt
	public Asteroid(String name, SpaceObject parent,int distance, double speed,Type type) {
		super(name,parent,null,5,distance,speed);
		this.type=type;
	}
	
	
	@Override
	public void draw(GraphicsContext gc) {
		switch(type) {
		case ORE:
			gc.setFill(Color.DARKSLATEGRAY);
			gc.fillOval(x, y, size/1, size/3);
			break;
		case ROCK:
			gc.setFill(Color.LIGHTGRAY);
			gc.fillOval(x, y, size/3, size/1);
			break;
		case TRASH:
			gc.setFill(Color.GRAY);
			gc.fillRect(x, y, size/2, size/2);
			break;
		default:
			gc.setFill(Color.BLACK);
			gc.fillRect(x, y, size/2, size/2);
			break;
		}
	}
	@Override
	public void update() {
		//i do not have children, i do not need to update children
	}
	
	@Override 
	public void move(int parentX, int parentY) {
		super.move(parentX, parentY);
		shake();
	}
	
	//Change my koords by a little noise, so its not a perfect circle
	private void shake() {
		x+=(Math.random()+1)*3;
		y+=(Math.random()+1)*3;
	}
}
