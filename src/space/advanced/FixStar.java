package space.advanced;

import javafx.scene.paint.Color;
import space.core.Star;

import java.util.Timer;
import java.util.TimerTask;

import interfaces.CollidingObject;
import interfaces.TimerObject;
import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("restriction")
public class FixStar extends Star implements TimerObject {
	//Shows Percentage of screen
	private double relativeX,relativeY;
	public boolean dead=false;
	
	public FixStar(String name, int x, int y,int lifetime) {
		super(name, Color.WHITESMOKE, x, y, 2);
		isCenter=false;
		setTimer(lifetime);
	}
	
	public FixStar(String name, double relX, double relY, int lifetime) {
		super(name, Color.WHITESMOKE, 0, 0, 2);
		relativeX=relX;
		relativeY=relY;
		isCenter=false;
		setTimer(lifetime);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		fixPosition(gc);
		gc.setFill(color);
		gc.fillOval(x-size/2, y-size/2, size, size);
	}
	
	@Override
	public boolean collides(CollidingObject other) {
		return false;
	}
	
	//if window is resized, put me in the correct position again
	protected void fixPosition(GraphicsContext gc) {
		x=(int) (relativeX*gc.getCanvas().getWidth());
		y=(int) (relativeY*gc.getCanvas().getHeight());
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
}
