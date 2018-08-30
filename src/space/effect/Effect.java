package space.effect;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import interfaces.DrawingObject;
import interfaces.RemovableObject;
import interfaces.TimerObject;
import interfaces.UpdatingObject;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import logic.EffectManager;
import space.core.MovingSpaceObject;
import space.core.SpaceObject;

public abstract class Effect implements UpdatingObject, DrawingObject, RemovableObject, TimerObject {
	
	protected String name;
	protected int x,y;
	protected int duration;
	protected int size;
	
	public Effect(String name, int x, int y, int duration, int size){
		
		this.name = name;
		this.x = x;
		this.y = y;
		this.duration = duration;
		this.size = size;
		
	}
	
	public void remove() {
		System.out.println("Explosion removed");
		EffectManager.getInstance().removeEffect(this);
	}
	
	public void update() {
	}
	
	public void draw(GraphicsContext gc) {
	}
	
	@Override
	public String toString() {return name+"@"+x+"|"+y;}
	public int getX() {return x;}
	public int getY() {return y;}
	public String getName() {return name;}
	
	public int getSize() {
		return size;
	}
	
}
