package drawing;

import interfaces.drawing.DrawingContext;
import interfaces.geom.Shape;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class JavaFXDrawingContext implements DrawingContext {
	
	private Canvas canvas;
	private GraphicsContext gc;
	
	public JavaFXDrawingContext(Group root) {
        canvas = new Canvas();
        root.getChildren().add(canvas);
        
        gc = canvas.getGraphicsContext2D();
	}
	
	public Canvas getCanvas() {return canvas;}
	public GraphicsContext getGraphicsContext() {return gc;}

	
	public final void drawShape(Shape s) {
		s.draw(this);
	}
	
	public final void bindSizeProperties(Scene scene) {
		canvas.widthProperty().bind(scene.widthProperty());
        canvas.heightProperty().bind(scene.heightProperty());
	};
	
	public final void resetContext() {
		gc.restore();
		gc.setEffect(null);
		gc.setFill(null);
		gc.setEffect(null);
	}

	@Override
	public final void saveContext() {
		gc.save();
	}
	
}
