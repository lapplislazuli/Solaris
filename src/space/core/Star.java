/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package space.core
 */
package space.core;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.effect.Glow;

@SuppressWarnings("restriction")
public class Star extends SpaceObject {
	protected Color color;
	public boolean isCenter=true;
	
	public Star(String name, Color color, int x, int y, int size) {
		super(name, x, y, size);
		this.color=color;
	}
	public Star(String name, Color color,int size) {
		super(name, 0, 0, size);
		this.color=color;
	}
	
	private void reCenter(GraphicsContext gc) {
		x=(int) (gc.getCanvas().getWidth()-size)/2;
		y=(int) (gc.getCanvas().getHeight()-size)/2;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		if(isCenter)
			reCenter(gc); 
		drawGlowingCircle(gc);
		super.draw(gc);
	}
	
	void drawGlowingCircle(GraphicsContext gc) {
		gc.setFill(new LinearGradient(0, 0, 0.8, 0.5, true, CycleMethod.NO_CYCLE, 
				new Stop(0.0, color),
				new Stop(1.0, color.darker())));
		gc.setEffect(new Glow(0.6));
		gc.fillOval(x-size/2, y-size/2, size, size);
	}
}
