package space.advanced;

import javafx.scene.paint.Color;
import space.core.Star;

import java.util.Timer;
import java.util.TimerTask;

import interfaces.CollidingObject;
import interfaces.TimerObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;

@SuppressWarnings("restriction")
public class FixStar extends Star implements TimerObject {
	
	//Shows Percentage of screen
	private double relativeX,relativeY;
	public boolean dead;
	
	public FixStar(String name, int x, int y) {
		super(name, Color.WHITESMOKE, x, y, 2);
		isCenter=false;
		dead = false;
	}
	
	public FixStar(String name, double relX, double relY) {
		super(name, Color.WHITESMOKE, 0, 0, 2);
		relativeX=relX;
		relativeY=relY;
		isCenter=false;
		dead = false;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		fixPosition(gc);
		super.draw(gc);
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
	public void setTimer(int updateIntervall) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	dead = true;
            }
        }, updateIntervall*1000);
	}
}
