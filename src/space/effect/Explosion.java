package space.effect;

import javafx.scene.paint.Color;
import geom.Circle;
import interfaces.geom.Point;
import javafx.scene.canvas.GraphicsContext;


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
	
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		super.draw(gc);
	}

}
