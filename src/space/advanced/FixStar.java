package space.advanced;

import javafx.scene.paint.Color;
import space.core.SpaceObject;
import space.core.Star;

import java.util.Timer;
import java.util.TimerTask;

import drawing.JavaFXDrawingContext;
import drawing.JavaFXDrawingInformation;
import geom.AbsolutePoint;
import interfaces.drawing.DrawingContext;
import interfaces.logical.CollidingObject;
import interfaces.logical.TimerObject;
import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("restriction")
public class FixStar extends Star implements TimerObject {
	private double relativeX,relativeY; //Shows Percentage of screen for resize-functions
	public boolean dead=false;
	public Timer timer;
	
	public FixStar(String name, double relX, double relY, int lifetime) {
		super(name, new JavaFXDrawingInformation(Color.WHITESMOKE), new AbsolutePoint(0, 0),1);
		shape.setLevelOfDetail(0);
		relativeX=relX;
		relativeY=relY;
		isCentered=false;
		setTimer(lifetime);
	}
	
	@Override
	public void drawShape(DrawingContext dc) {
		if(dc instanceof JavaFXDrawingContext) {
			GraphicsContext gc = ((JavaFXDrawingContext)dc).getGraphicsContext();
			fixPosition(gc);
		}
		
		shape.draw(dc);
	}
 
	protected void fixPosition(GraphicsContext gc) {
		center.setX((int) (relativeX*gc.getCanvas().getWidth()));
		center.setY((int) (relativeY*gc.getCanvas().getHeight()));
	}

	@Override
	public void setTimer(int lifetime) {
		timer = new Timer();
		timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	die();
            }
        }, lifetime);
	}
	
	public void die() {dead=true;}
	public boolean isDead() {return dead;}
	
	@Override
	public boolean collides(CollidingObject other) {return false;}
	@Override
	public double distanceTo(SpaceObject other) {return Double.MAX_VALUE;}
}
