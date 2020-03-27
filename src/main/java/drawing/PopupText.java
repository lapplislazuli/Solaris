package drawing;

import java.util.Timer;
import java.util.TimerTask;

import interfaces.drawing.DrawingContext;
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
	
	public PopupText(String text, Point position,int lifeTime) {
		this.text=text;
		this.position=position;
		
		ManagerRegistry.getDrawingManager().registerItem(this);
		
		setTimer(lifeTime);
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
		// TODO Auto-generated method stub
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
			gc.setLineWidth(1.0);
			// Set fill color
			gc.setFill(Color.RED);
			 
			// Draw a filled Text
			gc.fillText(text, position.getX(), position.getY());
		}
	}

}
