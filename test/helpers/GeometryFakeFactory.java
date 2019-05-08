package helpers;

import geom.AbsolutePoint;
import geom.Circle;
import geom.RelativePoint;
import interfaces.geom.Point;

public abstract class GeometryFakeFactory {
	
	public static AbsolutePoint fakeAbsolutePoint() {
		return new AbsolutePoint(0,0);
	}
	
	public static AbsolutePoint fakeAbsolutePoint(int x, int y) {
		return new AbsolutePoint(x,y);
	}

	public static RelativePoint fakeRelativePoint(Point center) {
		return new RelativePoint(center,0,0);
	}
	
	public static RelativePoint fakeRelativePoint(Point center,int xdiff,int ydiff) {
		return new RelativePoint(center,xdiff,ydiff);
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
