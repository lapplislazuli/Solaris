package space.core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.effect.Glow;

@SuppressWarnings("restriction")
public class Star extends SpaceObject {
	//i am a sun, bright and shining
	protected Color color;
	protected boolean isCenter=true;
	
	public Star(String name, Color color, int x, int y, int size) {
		super(name, x, y, size);
		this.color=color;
	}
	public Star(String name, Color color,int size) {
		super(name, 0, 0, size);
		this.color=color;
	}
	
	public void setCenter(boolean isCenter) {
		this.isCenter=isCenter;
	}
	
	private void reCenter(GraphicsContext gc) {
		x=(int) (gc.getCanvas().getWidth()-size)/2;
		y=(int) (gc.getCanvas().getHeight()-size)/2;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		if(isCenter)
			reCenter(gc);
		if (color !=null) {
			gc.setFill(new LinearGradient(0, 0, 0.8, 0.5, true, CycleMethod.NO_CYCLE, 
					new Stop(0.0, color),
					new Stop(1.0, color.darker())));
			gc.setEffect(new Glow(0.6));
		}
		gc.fillOval(x-size/2, y-size/2, size, size);
		super.draw(gc);
	}
}
