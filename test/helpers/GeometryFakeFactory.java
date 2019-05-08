package helpers;

import geom.AbsolutePoint;
import geom.Circle;
import interfaces.geom.Point;

public abstract class GeometryFakeFactory {
	
	public static AbsolutePoint fakeAbsolutePoint() {
		return new AbsolutePoint(0,0);
	}
	
	public static AbsolutePoint fakeAbsolutePoint(int x, int y) {
		return new AbsolutePoint(x,y);
	}
	
	public static Circle fakeCircle(int radious) {
		Circle fake = new Circle(fakeAbsolutePoint(),radious);
		fake.setLevelOfDetail(30);
		fake.initOutline();
		return fake;
	}
	
	public static Circle fakeCircle(Point p, int radious) {
		Circle fake = new Circle(p,radious);
		fake.setLevelOfDetail(30);
		fake.initOutline();
		return fake;
	}
}
