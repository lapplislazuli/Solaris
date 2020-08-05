package geom;

import java.util.LinkedList;
import java.util.List;

import drawing.EmptyJFXDrawingInformation;
import drawing.JavaFXDrawingInformation;
import interfaces.drawing.DrawingInformation;
import interfaces.geom.Point;
import interfaces.geom.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EdgyPolylineShape extends PolylineShape {
	
	private EdgyPolylineShape(Builder builder) {
		super(builder.center,builder.points);
		this.drawingInfo = builder.drawingInfo;
		this.fillBorder = builder.fillBorder;
		this.fillShape = builder.fillShape;
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.beginPath();
		gc.moveTo(outLine.get(0).getX(), outLine.get(0).getY());
		
		for(Point p : outLine)
			gc.lineTo(p.getX(), p.getY());
		
		// Close the Path to the first value.
		// Fill works without this, but the stroked border does not
		gc.lineTo(outLine.get(0).getX(), outLine.get(0).getY());
		
		if (drawingInfo instanceof JavaFXDrawingInformation) {
			var drawingInfoCasted = (JavaFXDrawingInformation) drawingInfo;
			if(this.fillShape) {
				gc.setFill(drawingInfoCasted.primarycolor);
				gc.fill();
			}
			if(this.fillBorder) {			
				gc.setStroke(drawingInfoCasted.secondaryColor);
				gc.stroke();
			}

		}
		
		gc.closePath();
	}
	
	public static class Builder {
		
		private Color fillcolor= Color.BLACK, edgecolor;
		
		private Point center = new AbsolutePoint(0,0);
		private boolean centerManuallySet = false;
		private boolean fillShape = true, fillBorder = true;
		private final List<Point> points;
		private DrawingInformation drawingInfo;

		public Builder(Point firstPoint) throws IllegalArgumentException{
			points = new LinkedList<>();
			points.add(firstPoint);
		}
		
		public Builder fillcolor(Color val){ 
			fillcolor = val; 
			return this;
		}
		
		public Builder edgecolor(Color val){ 
			edgecolor = val; 
			return this;
		}
		
		public Builder center(Point p) {
			center = p;
			centerManuallySet = true;
			return this;
		}
		
		public Builder fillShape(boolean val) {
			fillShape = val;
			return this;
		}
		
		public Builder fillBorder(boolean val) {
			fillBorder = val;
			return this;
		}
		
		public Builder center(int x,int y) {
			center = new AbsolutePoint(x,y);
			centerManuallySet = true;
			return this;
		}
		
		
		public Builder nextPoint(Point p) {
			points.add(p);
			return this;
		}
		
		public Builder nextPoint(int x, int y) {
			points.add(new AbsolutePoint(x,y));
			return this;
		}
		
		
		public EdgyPolylineShape build() {
			if(fillcolor != Color.BLACK) {
				var info = new JavaFXDrawingInformation(fillcolor);
				if(edgecolor != null)
					info.secondaryColor = edgecolor;
				else 
					info.secondaryColor = fillcolor; // If no edge color is given, but a primary color, use the primary color
				
				drawingInfo = info;
			} else {
				var info = new EmptyJFXDrawingInformation();
				if(edgecolor!=null)
					info.secondaryColor = edgecolor; 
				else 
					info.secondaryColor = fillcolor;// If no edge color is given, but a primary color, use the primary color
				
				drawingInfo = info;
			}
			var shape = new EdgyPolylineShape(this);
			if (! centerManuallySet) {
				shape.setCenter(shape.getBoundingBox().getCenter());
			}
			shape.initOutline();
			return shape;
		}
	}

	@Override
	public Shape copy() {
		JavaFXDrawingInformation dInfo = (JavaFXDrawingInformation) this.drawingInfo;
		
		var shapeBuilder = new Builder(outLine.get(0).clone())
				.center(this.center.clone())
				.fillcolor(dInfo.primarycolor)
				.edgecolor(dInfo.secondaryColor)
				.fillBorder(this.fillBorder)
				.fillShape(this.fillShape);
		
		for(int i = 1; i < outLine.size();i ++) {
			shapeBuilder = shapeBuilder.nextPoint(outLine.get(i).clone());
		}
		
		return shapeBuilder.build();
	}
	
}
