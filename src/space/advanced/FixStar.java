package space.advanced;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import space.core.SpaceObject;
import space.core.Star;

import java.util.Timer;
import java.util.TimerTask;

import drawing.JavaFXDrawingContext;
import geom.AbsolutePoint;
import interfaces.drawing.DrawingContext;
import interfaces.logical.CollidingObject;
import interfaces.logical.TimerObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;

@SuppressWarnings("restriction")
public class FixStar extends Star implements TimerObject {
	private double relativeX,relativeY; //Shows Percentage of screen for resize-functions
	public boolean dead=false;
	
	public FixStar(String name, double relX, double relY, int lifetime) {
		super(name, Color.WHITESMOKE, new AbsolutePoint(0, 0),1);
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
			gc.setFill(color);
		}
		
		shape.draw(dc);
	}
 
	protected void fixPosition(GraphicsContext gc) {
		center.setX((int) (relativeX*gc.getCanvas().getWidth()));
		center.setY((int) (relativeY*gc.getCanvas().getHeight()));
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
	public boolean collides(CollidingObject other) {return false;}
	@Override
	public double distanceTo(SpaceObject other) {return Double.MAX_VALUE;}
}
