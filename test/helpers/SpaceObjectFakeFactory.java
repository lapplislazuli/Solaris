package helpers;

import geom.AbsolutePoint;
import space.core.SpaceObject;
import space.core.Star;

public abstract class SpaceObjectFakeFactory {

	public static Star fakeStar(int xpos,int ypos,int size) {
		Star anchor = new Star("Anker", null, new AbsolutePoint(xpos,ypos),size);
		anchor.shape.updateOrInitOutline();
		return anchor;
	}
	
	public static SpaceObject fakeStar(String name,int xpos,int ypos,int size) {
		Star anchor = new Star(name, null, new AbsolutePoint(xpos,ypos),size);
		anchor.shape.updateOrInitOutline();
		return anchor;
	}
}
