package space.core;

import java.util.LinkedList;
import java.util.List;

import drawing.JavaFXDrawingContext;
import drawing.JavaFXDrawingInformation;
import geom.Circle;
import interfaces.drawing.DrawingContext;
import interfaces.drawing.DrawingInformation;
import interfaces.logical.MovingUpdatingObject;
import geom.AbsolutePoint;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import javafx.scene.effect.Glow;

public class Star extends SpaceObject {
	private int size;
	public boolean isCentered=true;
	
	public Star(String name, DrawingInformation dInfo,AbsolutePoint center, int size) {
		super(name,center,new Circle(center,size),dInfo);
		shape.setLevelOfDetail(100);
		this.size=size;
		checkAndAddSunEffects();
	}
	
	private Star(Builder builder) {
		super(builder.name,builder.center,new Circle(builder.radious),new JavaFXDrawingInformation(builder.color));
		shape.setLevelOfDetail(builder.levelOfDetail);
		trabants=builder.trabants;
		isCentered=builder.reCentering;
		size=builder.radious;
		
		checkAndAddSunEffects();
	}
	
	private void reCenter(GraphicsContext gc) {
		center.setX((int) (gc.getCanvas().getWidth()-size)/2);
		center.setY((int) (gc.getCanvas().getHeight()-size)/2);
	}
	
	@Override
	public void draw(DrawingContext dc) {
		if(isCentered && dc instanceof JavaFXDrawingContext)
			reCenter(((JavaFXDrawingContext)dc).getGraphicsContext());
		super.draw(dc);
	}
	
	
	private void checkAndAddSunEffects() {
		if(dInfo instanceof JavaFXDrawingInformation) {
			((JavaFXDrawingInformation)dInfo).hasColorEffect=true;
			((JavaFXDrawingInformation)dInfo).effects.add(new Glow(0.6));
		}
			
	}
	
	public static class Builder {
		private final String name;
		private Color color= Color.ORANGE;
		private int levelOfDetail=50, radious=0;
		private AbsolutePoint center=new AbsolutePoint(0,0);
		private boolean reCentering=false;
		
		private List<MovingUpdatingObject> trabants = new LinkedList<MovingUpdatingObject>();
		
		public Builder(String name) throws IllegalArgumentException{
			if(name==null||name.isEmpty())
				throw new IllegalArgumentException("Name cannot be null or empty");
			this.name=name;
		}
		
		public Builder color(Color val){ 
			color= val; 
			return this;
		}
		
		public Builder center(int xCoord, int yCoord){
			center=new AbsolutePoint(xCoord,yCoord);
			return this;
		}
		
		public Builder center(AbsolutePoint val) {
			center=val;
			return this;
		}
		public Builder radious(int val) {
			if(val<0)
				throw new IllegalArgumentException("Radious cannot be negative");
			radious= val; 
			return this;
		}
		public Builder reCentering(boolean val) {
			reCentering=val;
			return this;
		}
		
		public Builder levelOfDetail(int val){ 
			if(val<0)
				throw new IllegalArgumentException("LoD cannot be negative");
			levelOfDetail= val; 
			return this;
		}
		
		public Builder trabant(MovingUpdatingObject val){ 
			trabants.add(val); 
			return this;
		}
		
		public Star build() {
			return new Star(this);
		}
	}
}
