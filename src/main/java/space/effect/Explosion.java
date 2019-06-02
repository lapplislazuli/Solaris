package space.effect;

import geom.Circle;
import interfaces.drawing.DrawingInformation;
import interfaces.geom.Point;


public class Explosion extends TimerEffect {
	
	public static double standardGrowth=0.3;
	
	double growthRate=1;
	static double growthAlternative=0.3;
	
	public Explosion(String name, Point p,int radious, int lifetime,DrawingInformation dInfo){
		super(name,p, new Circle(p,radious),lifetime,dInfo);
	}
	
	public Explosion(String name, Point p, int radious, int lifetime, double increaseFactor, DrawingInformation dInfo){
		super(name,p, new Circle(p,radious),lifetime,dInfo);
		this.growthRate = increaseFactor;
	}
	
	public void update(){
		Circle c = (Circle) shape;
		if(growthRate!=1)
			c.radious*=growthRate;
		else
			c.radious+=standardGrowth;
	}
	
}
