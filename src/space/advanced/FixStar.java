package space.advanced;

import javafx.scene.paint.Color;
import space.core.SpaceObject;
import space.core.Star;

import java.util.Timer;
import java.util.TimerTask;

import geom.Point;
import interfaces.logical.CollidingObject;
import interfaces.logical.TimerObject;
import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("restriction")
public class FixStar extends Star implements TimerObject {
	private double relativeX,relativeY; //Shows Percentage of screen for resize-functions
	public boolean dead=false;
	
	public FixStar(String name, double relX, double relY, int lifetime) {
		super(name, Color.WHITESMOKE, new Point(0, 0),1);
		area.levelOfDetail=0;
		relativeX=relX;
		relativeY=relY;
		isCentered=false;
		setTimer(lifetime);
	}
	
	@Override
	public void drawThisItem(GraphicsContext gc) {
		fixPosition(gc);
		gc.setFill(color);
		area.draw(gc);
	}
 
	protected void fixPosition(GraphicsContext gc) {
		center.x=(int) (relativeX*gc.getCanvas().getWidth());
		center.y=(int) (relativeY*gc.getCanvas().getHeight());
	}

	@Override
	public void setTimer(int lifetime) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	dead = true;
            }
        }, lifetime);
	}

	@Override
	public boolean isCovered(int x, int y) {return false;}
	@Override
	public boolean collides(CollidingObject other) {return false;}
	@Override
	public double distanceTo(SpaceObject other) {return Double.MAX_VALUE;}
}
