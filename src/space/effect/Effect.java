package space.effect;

import geom.AbsolutePoint;
import interfaces.drawing.DrawingContext;
import interfaces.drawing.DrawingObject;
import interfaces.geom.Point;
import interfaces.geom.Shape;
import interfaces.logical.RemovableObject;
import interfaces.logical.UpdatingObject;
import logic.EffectManager;

public abstract class Effect implements UpdatingObject, DrawingObject, RemovableObject{
	
	String name;
	Point center;
	Shape shape;
	
	public Effect(String name,Point p, Shape s){
		this.name = name;
		center=p;
		shape=s;
		EffectManager.getInstance().addEffect(this);
	}
	
	public void remove() {
		EffectManager.getInstance().removeEffect(this);
	}
	
	public void update() {}
	
	public void draw(DrawingContext dc) {
		shape.draw(dc);
	}
	
	
	public boolean isCovered(int x, int y) {
		return shape.contains(new AbsolutePoint(x,y));
	}
	
	@Override
	public String toString() {return name+"@"+center.toString();}
}
