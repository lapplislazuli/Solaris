package geom;

import java.util.List;

import interfaces.geom.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EdgyPolylineShape extends PolylineShape {

	public EdgyPolylineShape(Point center, List<Point> outline) {
		super(center, outline);
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.beginPath();
		gc.moveTo(outLine.get(0).getX(), outLine.get(0).getY());
		
		for(Point p : outLine)
			gc.lineTo(p.getX(), p.getY());

		gc.setFill(Color.ORANGERED);
		gc.fill();
		
		gc.closePath();
	}

}
