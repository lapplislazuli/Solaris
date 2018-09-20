/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package space.core
 */
package space.core;

import geom.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.effect.Glow;

@SuppressWarnings("restriction")
public class Star extends SpaceObject {
	protected Color color;
	public boolean isCentered=true;
	
	public Star(String name, Color color,Point center, int size) {
		super(name,center,size);
		this.color=color;
	}
	public Star(String name, Color color,int size) {
		super(name,new Point(0,0), size);
		this.color=color;
	}
	
	private void reCenter(GraphicsContext gc) {
		center.x=(int) (gc.getCanvas().getWidth()-size)/2;
		center.y=(int) (gc.getCanvas().getHeight()-size)/2;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		if(isCentered)
			reCenter(gc);
		super.draw(gc);
	}
	
	void drawGlowingCircle(GraphicsContext gc) {
		gc.setFill(new LinearGradient(0, 0, 0.8, 0.5, true, CycleMethod.NO_CYCLE, 
				new Stop(0.0, color),
				new Stop(1.0, color.darker())));
		gc.setEffect(new Glow(0.6));
		gc.fillOval(center.x-size/2, center.y-size/2, size, size);
	}
	/* (non-Javadoc)
	 * @see space.core.SpaceObject#drawThisItem(javafx.scene.canvas.GraphicsContext)
	 */
	@Override
	public void drawThisItem(GraphicsContext gc) {
		drawGlowingCircle(gc);
	}
}
