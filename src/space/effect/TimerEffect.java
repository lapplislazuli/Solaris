package space.effect;

import java.util.Timer;
import java.util.TimerTask;

import interfaces.geom.Point;
import interfaces.geom.Shape;
import interfaces.logical.TimerObject;
import javafx.application.Platform;

@SuppressWarnings("restriction")
public abstract class TimerEffect extends Effect implements TimerObject{
	
	public TimerEffect(String name, Point p, Shape s, int lifetime) {
		super(name, p, s);
		setTimer(lifetime);
	}
	
	public void setTimer(int lifetime) {
		Timer timer = new Timer();
        timer.schedule(new TimerTask() {
			@Override
            public void run() {
            	Platform.runLater ( () ->remove()); 
            }
        }, lifetime);
	}
}
