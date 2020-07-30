package space.effect;

import geom.AbsolutePoint;
import interfaces.drawing.DrawingContext;
import interfaces.drawing.DrawingInformation;
import interfaces.geom.Point;
import interfaces.geom.Shape;
import interfaces.logical.Effect;
import logic.manager.ManagerRegistry;

public abstract class LocalizedEffect implements Effect{
	
	protected String name;
	protected Point center;
	protected Shape shape;
	protected DrawingInformation dInfo;
	
	public LocalizedEffect(String name,Point p, Shape s, DrawingInformation dInfo){
		this.name = name;
		center = p;
		shape = s;
		this.dInfo = dInfo;
		ManagerRegistry.getEffectManager().registerItem(this);
	}
	
	public void remove() {
		ManagerRegistry.getEffectManager().removeEffect(this);
	}
	
	public void update() {}
	
	public void draw(DrawingContext dc) {
		dInfo.applyDrawingInformation(dc);
		drawShape(dc);
		dc.resetContext();
	}
	
	public void drawShape(DrawingContext dc) {
		shape.draw(dc);
	}
	
	public boolean isCovered(int x, int y) {
		return shape.contains(new AbsolutePoint(x,y));
	}
	
	public DrawingInformation getDrawingInformation() {
		return dInfo;
	}
	
	public Shape getShape() {
		return shape;
	}
	
	@Override
	public String toString() {
		return name + "@" + center.toString();
	}
	
	// I am orphaned if i am not in the effect Manager
	public boolean isOrphan() {
		return !ManagerRegistry.getEffectManager().getRegisteredItems().contains(this);
	}
}
