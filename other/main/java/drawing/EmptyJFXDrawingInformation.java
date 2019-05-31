package drawing;

import interfaces.drawing.DrawingContext;
import javafx.scene.paint.Color;

/*
 * This is a Specialcase for my JavaFXDrawingInformations
 * it acts like a normal Information, but has no behaviour to avoid errors
 */
public class EmptyJFXDrawingInformation extends JavaFXDrawingInformation{
	
	public EmptyJFXDrawingInformation() {
		super(null);
	}
	public EmptyJFXDrawingInformation(Color color) {
		super(color);
	}
	
	@Override
	public void applyDrawingInformation(DrawingContext dc){
		//DoNothing, i am an Empty Specialcase
	}
}
