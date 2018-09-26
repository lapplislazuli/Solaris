package interfaces;

import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("restriction")
public interface DrawingObject {
	
	public void draw(GraphicsContext gc);
	
	//Tells whether XY is beneath me
	public boolean isCovered(int x, int y);
}
