/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package interfaces
 */
package interfaces;

import javafx.scene.canvas.GraphicsContext;

@SuppressWarnings("restriction")
public interface DrawingObject {
	
	public void draw(GraphicsContext gc);
}
