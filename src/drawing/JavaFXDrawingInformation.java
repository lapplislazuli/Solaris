package drawing;

import java.util.LinkedList;
import java.util.List;

import interfaces.drawing.DrawingContext;
import interfaces.drawing.DrawingInformation;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.transform.Affine;

public class JavaFXDrawingInformation implements DrawingInformation{
	
	public boolean hasColorEffect=false;
	public Color color;
	
	public List<Effect> effects = new LinkedList<Effect>();
	public List<Affine> transformations = new LinkedList<Affine>();
	
	public JavaFXDrawingInformation(Color color) {
		this.color=color;
	}
	
	@Override
	public void applyDrawingInformation(DrawingContext dc) {
		if(dc instanceof JavaFXDrawingContext) {
			GraphicsContext gc = ((JavaFXDrawingContext)dc).getGraphicsContext();
			applyFill(gc);
			applyEffects(gc);
			applyTransformations(gc);
		}
	}

	private void applyTransformations(GraphicsContext gc) {
		for(Affine a : transformations)
			gc.transform(a);
	}
	
	private void applyEffects(GraphicsContext gc) {
		for(Effect e : effects)
			gc.setEffect(e);
	}

	private void applyFill(GraphicsContext gc) {
		if (hasColorEffect && color!=null) {
			gc.setFill(new LinearGradient(0, 0, 0.8, 0.5, true, CycleMethod.NO_CYCLE, 
					new Stop(0.0, color),
					new Stop(1.0, color.darker())));
		}
		else
			gc.setFill(color);
	}

}
