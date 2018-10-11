package space.effect;

import javafx.scene.paint.Color;
import drawing.JavaFXDrawingContext;
import geom.Circle;
import interfaces.drawing.DrawingContext;
import interfaces.geom.Point;


@SuppressWarnings("restriction")
public class Explosion extends TimerEffect {
	
	double growthRate=1;
	Color color;
	
	public Explosion(String name, Point p,int radious, int lifetime, Color color){
		super(name,p, new Circle(p,radious),lifetime);
		this.color = color;
	}
	public Explosion(String name, Point p, int radious, int lifetime, double increaseFactor, Color color){
		super(name,p, new Circle(p,radious),lifetime);
		this.growthRate = increaseFactor;
		this.color = color;
	}
	
	public void update(){
		Circle c = (Circle) shape;
		if(growthRate!=1)
			c.radious*=growthRate;
		else
			c.radious+=0.3;
	}
	
	@Override
	public void draw(DrawingContext gc) {
		if(gc instanceof JavaFXDrawingContext)
			((JavaFXDrawingContext)gc).getGraphicsContext().setFill(color);
		super.draw(gc);
	}

}
