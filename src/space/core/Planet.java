package space.core;

import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("restriction")
public class Planet extends MovingSpaceObject {

	public Planet(String name, SpaceObject parent, Color color, int size, int distance, double speed) {
		super(name, parent, color, size, distance, speed);
	}
	
	public static class Builder {
		private final String name;
		private Color color= Color.BLACK;
		private int distance = 0;
		private int size = 0;
		private double speed = 0;
		private SpaceObject parent;
		private List<MovingSpaceObject> trabants;
		
		public Builder(String name,SpaceObject parent) throws IllegalArgumentException{
			if(name==null||name.isEmpty())
				throw new IllegalArgumentException("Name cannot be null or empty");
			if(parent==null)
				throw new IllegalArgumentException("Parent cannot be null");
			this.name=name;
			this.parent=parent;
			trabants = new LinkedList<MovingSpaceObject>();
		}
		
		public Builder color(Color val)
		{ color= val; return this;}
		
		public Builder distance(int val)
		{ distance= val; return this;}
		
		public Builder size(int val)
		{ size= val; return this;}
		
		public Builder speed(double val)
		{ speed= val; return this;}
		
		public Builder trabant(MovingSpaceObject val)
		{ trabants.add(val); return this;}
		
		public Planet build() {
			return new Planet(this);
		}
	}
	
	private Planet(Builder builder) {
		super(builder.name,builder.parent,builder.color,builder.size,builder.distance,builder.speed);
		trabants=builder.trabants;
	}
	
	
}
