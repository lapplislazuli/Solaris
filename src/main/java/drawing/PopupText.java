package drawing;

import java.util.Timer;
import java.util.TimerTask;

import geom.AbsolutePoint;
import interfaces.drawing.DrawingContext;
import interfaces.drawing.DrawingInformation;
import interfaces.drawing.DrawingObject;
import interfaces.geom.Point;
import interfaces.logical.RemovableObject;
import interfaces.logical.TimerObject;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import logic.manager.ManagerRegistry;

public class PopupText implements DrawingObject, TimerObject, RemovableObject {
	
	private String text;
	private Point position;
	private int size,lifetime;
	private DrawingInformation drawingInfo;
	
	private PopupText(Builder builder) {
		this.text=builder.text;
		this.size=builder.size;
		this.position=builder.position;
		this.drawingInfo = builder.drawingInfo;
		this.lifetime = builder.lifetime;
		
		ManagerRegistry.getDrawingManager().registerItem(this);
		
		setTimer(this.lifetime);
	}
	
	@Override
	public void update() {
		// Nothing? Just inherited from DrawingObject
	}

	@Override
	public void remove() {
		ManagerRegistry.getDrawingManager().getRegisteredItems().remove(this);
	}

	@Override
	public boolean isOrphan() {
		return false;
	}

	@Override
	public void setTimer(int updateIntervall) {
		Timer timer = new Timer();
        timer.schedule(new TimerTask() {
			@Override
            public void run() {
            	Platform.runLater ( () ->remove()); 
            }
        }, updateIntervall);
	}

	@Override
	public void draw(DrawingContext dc) {
		if (dc instanceof JavaFXDrawingContext) {
			var dccasted = (JavaFXDrawingContext) dc;
			var gc = dccasted.getGraphicsContext();
			
			gc.setLineWidth(size);
			
			if (drawingInfo instanceof JavaFXDrawingInformation) {
				var dinfocasted = (JavaFXDrawingInformation) drawingInfo;
				gc.setFill(dinfocasted.color);
			}
			else 
				gc.setFill(Color.BLACK);
			
			gc.fillText(text, position.getX(), position.getY());
		}
	}
	
	public static class Builder {
		private final String text;
		private Color color= Color.BLACK;
		private int size = 0,lifetime=100;
		private Point position = new AbsolutePoint(0,0);
		private DrawingInformation drawingInfo;

		public Builder(String name) throws IllegalArgumentException{
			if(name==null||name.isEmpty())
				throw new IllegalArgumentException("Text cannot be null or empty");
			this.text=name;
		}
		
		public Builder color(Color val){ 
			color= val; 
			return this;
		}
		
		public Builder size(int val){
			if(val<0)
				throw new IllegalArgumentException("Size cannot be smaller than 0");
			size= val; 
			return this;
		}
		
		public Builder position(Point p) {
			position=p;
			return this;
		}
		
		public Builder lifetime(int ms) {
			lifetime=ms;
			return this;
		}
		
		public PopupText build() {
			if(color!=Color.BLACK) {
				drawingInfo = new JavaFXDrawingInformation(color);
			} else {
				drawingInfo = new EmptyJFXDrawingInformation();
			}
			return new PopupText(this);
		}
	}

}
