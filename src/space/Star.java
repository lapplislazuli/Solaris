package space;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

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
	
	@Override
	public void update(GraphicsContext gc) {
		if(isCenter)
			reCenter(gc);
		super.update(gc);
	}
	
	private void reCenter(GraphicsContext gc) {
		x=(int) (gc.getCanvas().getWidth()-size)/2;
		y=(int) (gc.getCanvas().getHeight()-size)/2;
	}
	
	@Override
	protected void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillOval(x-size/4, y-size/4, size/2, size/2);
	}
}
