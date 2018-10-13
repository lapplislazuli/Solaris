package space.effect;

import geom.AbsolutePoint;
import interfaces.drawing.ComplexDrawingObject;
import interfaces.drawing.DrawingContext;
import interfaces.drawing.DrawingInformation;
import interfaces.geom.Point;
import interfaces.geom.Shape;
import interfaces.logical.RemovableObject;
import interfaces.logical.UpdatingObject;
import logic.manager.EffectManager;

public abstract class Effect implements UpdatingObject, ComplexDrawingObject, RemovableObject{
	
	String name;
	Point center;
	Shape shape;
	DrawingInformation dInfo;
	
	public Effect(String name,Point p, Shape s, DrawingInformation dInfo){
		this.name = name;
		center=p;
		shape=s;
		this.dInfo=dInfo;
		EffectManager.getInstance().addEffect(this);
	}
	
	public void remove() {
		EffectManager.getInstance().removeEffect(this);
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
	
	@Override
	public String toString() {return name+"@"+center.toString();}
}
