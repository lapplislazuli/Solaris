package space.core;

import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

import drawing.JavaFXDrawingInformation;
import geom.AbsolutePoint;
import geom.Circle;

@SuppressWarnings("restriction")
public class Planet extends MovingSpaceObject {

	public static class Builder {
		private final String name;
		private SpaceObject parent;
		private Color color= Color.BLACK;
		private int distance = 0,size = 0, levelOfDetail=2;
		private double speed = 0,rotationSpeed=0;
		private List<MovingSpaceObject> trabants = new LinkedList<MovingSpaceObject>();
		
		public Builder(String name,SpaceObject parent) throws IllegalArgumentException{
			if(name==null||name.isEmpty())
				throw new IllegalArgumentException("Name cannot be null or empty");
			if(parent==null)
				throw new IllegalArgumentException("Parent cannot be null");
			this.name=name;
			this.parent=parent;
		}
		
		public Builder color(Color val){ 
			color= val; 
			return this;
		}
		
		public Builder distance(int val){
			if(val<0)
				throw new IllegalArgumentException("Distance cannot be smaller than 0");
			distance= val; 
			return this;
		}
		
		public Builder size(int val){
			if(val<0)
				throw new IllegalArgumentException("Size cannot be smaller than 0");
			size= val; 
			return this;
		}
		
		public Builder speed(double val){
			speed= val; 
			return this;
		}
		public Builder rotationSpeed(double val){
			rotationSpeed= val; 
			return this;
		}
		public Builder levelOfDetail(int val){ 
			if(val<0)
				throw new IllegalArgumentException("LoD cannot be smaller than 0");
			levelOfDetail= val; 
			return this;
		}
		
		public Builder trabant(MovingSpaceObject val){ 
			trabants.add(val); 
			return this;
		}
		
		public Planet build() {
			return new Planet(this);
		}
	}
	
	private Planet(Builder builder) {
		super(builder.name,builder.parent,new JavaFXDrawingInformation(builder.color),new Circle(builder.size),builder.distance,builder.speed);
		trabants=builder.trabants;
		shape.setLevelOfDetail(builder.levelOfDetail);
		rotationSpeed=builder.rotationSpeed;
		if(getDrawingInformation() instanceof JavaFXDrawingInformation)
			((JavaFXDrawingInformation)getDrawingInformation()).hasColorEffect=true;
	}
	
}
