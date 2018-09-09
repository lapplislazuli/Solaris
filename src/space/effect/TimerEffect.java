/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package space.effect
 */
package space.effect;

import java.util.Timer;
import java.util.TimerTask;

import interfaces.logical.TimerObject;
import javafx.application.Platform;

@SuppressWarnings("restriction")
public abstract class TimerEffect extends Effect implements TimerObject{
	
	private Timer timer;
	private boolean oneTimeCycled;
	
	public TimerEffect(String name, int x, int y, double size, int lifetime) {
		super(name, x, y, size);
		setTimer(lifetime);
	}
	
	@Override
	public void remove(){
		if(oneTimeCycled){
			super.remove();
			if(timer!=null){
				timer.cancel();
				timer = null;
				}
		}
		else
			oneTimeCycled=true;
	}
	
	public void setTimer(int lifetime) {
		timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
			@Override
            public void run() {
            	Platform.runLater ( () ->remove()); 
            }
        }, 0, lifetime);
	}
}
