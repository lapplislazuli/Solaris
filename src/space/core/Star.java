package space.core;

import java.util.LinkedList;
import java.util.List;

import geom.Circle;
import geom.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.effect.Glow;

@SuppressWarnings("restriction")
public class Star extends SpaceObject {
	protected Color color;
	private int size;
	public boolean isCentered=true;
	
	public Star(String name, Color color,Point center, int size) {
		super(name,center,new Circle(center,size));
		area.levelOfDetail=100;
		this.size=size;
		this.color=color;
	}
	
	public Star(String name, Color color,int size) {
		super(name,new Point(0,0), new Circle(size));
		area.levelOfDetail=100;
		this.size=size;
		this.color=color;
	}
	
	private Star(Builder builder) {
		super(builder.name,builder.center,new Circle(builder.radious));
		area.levelOfDetail=builder.levelOfDetail;
		color=builder.color;
		trabants=builder.trabants;
		isCentered=builder.reCentering;
		size=builder.radious;
	}
	
	private void reCenter(GraphicsContext gc) {
		center.x=(int) (gc.getCanvas().getWidth()-size)/2;
		center.y=(int) (gc.getCanvas().getHeight()-size)/2;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		if(isCentered)
			reCenter(gc);
		super.draw(gc);
	}
	
	void drawGlowingCircle(GraphicsContext gc) {
		gc.setFill(new LinearGradient(0, 0, 0.8, 0.5, true, CycleMethod.NO_CYCLE, 
				new Stop(0.0, color),
				new Stop(1.0, color.darker())));
		gc.setEffect(new Glow(0.6));
		area.draw(gc);
	}
	@Override
	public void drawThisItem(GraphicsContext gc) {
		drawGlowingCircle(gc);
	}
	
	public static class Builder {
		private final String name;
		private Color color= Color.ORANGE;
		private int levelOfDetail=50, radious=0;
		private Point center=new Point(0,0);
		private boolean reCentering=false;
		
		private List<MovingSpaceObject> trabants = new LinkedList<MovingSpaceObject>();
		
		public Builder(String name,SpaceObject parent) throws IllegalArgumentException{
			if(name==null||name.isEmpty())
				throw new IllegalArgumentException("Name cannot be null or empty");
			this.name=name;
		}
		
		public Builder color(Color val){ 
			color= val; 
			return this;
		}
		
		public Builder center(int xCoord, int yCoord){
			center=new Point(xCoord,yCoord);
			return this;
		}
		
		public Builder center(Point val) {
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
		
		public Builder trabant(MovingSpaceObject val){ 
			trabants.add(val); 
			return this;
		}
		
		public Star build() {
			return new Star(this);
		}
	}
}
