package space.advanced;

import javafx.scene.paint.Color;
import space.core.Star;
import javafx.scene.canvas.GraphicsContext;

public class FixStar extends Star{
	
	//Shows Percentage of screen
	private double relativeX,relativeY;
	
	public FixStar(String name, int x, int y) {
		super(name, Color.WHITESMOKE, x, y, 2);
		isCenter=false;
	}
	
	public FixStar(String name, double relX, double relY) {
		super(name, Color.WHITESMOKE, 0, 0, 4);
		relativeX=relX;
		relativeY=relY;
		isCenter=false;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		fixPosition(gc);
		super.draw(gc);
	}
	
	//if window is resized, put me in the correct position again
	protected void fixPosition(GraphicsContext gc) {
		x=(int) (relativeX*gc.getCanvas().getWidth());
		y=(int) (relativeY*gc.getCanvas().getHeight());
	}
	
}
