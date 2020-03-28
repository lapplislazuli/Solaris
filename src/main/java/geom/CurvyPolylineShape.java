package geom;

import java.util.LinkedList;
import java.util.List;

import drawing.EmptyJFXDrawingInformation;
import drawing.JavaFXDrawingInformation;
import interfaces.drawing.DrawingInformation;
import interfaces.geom.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CurvyPolylineShape extends PolylineShape {
	
	List<Point> curvePoints;
	
	private CurvyPolylineShape(Builder builder) {
		super(builder.center,builder.points);
		this.curvePoints = builder.curvepoints;
		
		this.drawingInfo = builder.drawingInfo;
		this.fillBorder = builder.fillBorder;
		this.fillShape = builder.fillShape;
	}

	@Override
	public void draw(GraphicsContext gc) {
		gc.beginPath();
		gc.moveTo(outLine.get(0).getX(), outLine.get(0).getY());
		
		int size = outLine.size();
		
		for(int i = 0; i<size-1;i++)
			gc.quadraticCurveTo(curvePoints.get(i).getX(), curvePoints.get(i).getY(), outLine.get(i+1).getX(), outLine.get(i+1).getY());
		
		// Close the Path to the first value.
		// Fill works without this, but the stroked border does not
		gc.quadraticCurveTo(curvePoints.get(size-1).getX(), curvePoints.get(size-1).getY(),outLine.get(0).getX(), outLine.get(0).getY());
		
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
		private final List<Point> points, curvepoints;
		private DrawingInformation drawingInfo;

		public Builder(Point firstPoint) throws IllegalArgumentException{
			points = new LinkedList<>();
			points.add(firstPoint);
			curvepoints = new LinkedList<>();
		}
		
		public Builder fillcolor(Color val){ 
			fillcolor= val; 
			return this;
		}
		
		public Builder edgecolor(Color val){ 
			edgecolor= val; 
			return this;
		}
		
		public Builder center(Point p) {
			center=p;
			centerManuallySet=true;
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
			centerManuallySet=true;
			return this;
		}
		
		
		public Builder nextPoint(Point p,Point c) {
			points.add(p);
			curvepoints.add(c);
			return this;
		}
		
		public Builder nextPoint(int x, int y, int curve_x, int curve_y) {
			points.add(new AbsolutePoint(x,y));
			curvepoints.add(new AbsolutePoint(curve_x,curve_y));
			return this;
		}
		
		
		public CurvyPolylineShape build(Point lastCurvePoint) throws IllegalArgumentException {
			this.curvepoints.add(lastCurvePoint);
			
			if(curvepoints.size() != points.size())
				throw new IllegalArgumentException("Missmatch in point sizes - every shape-point needs a curvepoint!");
			
			if(fillcolor!=Color.BLACK) {
				var info = new JavaFXDrawingInformation(fillcolor);
				if(edgecolor!=null)
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
			var shape = new CurvyPolylineShape(this);
			if (!centerManuallySet) {
				shape.setCenter(shape.getBoundingBox().getCenter());
			}
			shape.initOutline();
			return shape;
		}
	}
	
}
