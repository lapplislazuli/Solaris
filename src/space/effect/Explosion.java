package space.effect;

import javafx.scene.paint.Color;
import logic.EffectManager;

import java.util.Timer;
import java.util.TimerTask;

import interfaces.IncreasingObject;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;


public class Explosion extends Effect implements IncreasingObject {
	
	public double increaseFactor;
	protected Color color;
	Timer timer;
	boolean oneTimeCycled=false;
	
	public Explosion(String name, int x, int y, int duration, double size, double increaseFactor, Color color){
		super(name,x,y,duration,size);
		System.out.println("Explosion Object created for " + duration + "seconds!");
		this.increaseFactor = increaseFactor;
		this.color = color;
		EffectManager.getInstance().addEffect(this);
		//remove();
		setTimer(duration);
	}
	public Explosion(String name, int x, int y, int duration, double size, Color color){
		super(name,x,y,duration,size);
		System.out.println("Explosion Object created for " + duration + "seconds!");
		this.color = color;
		EffectManager.getInstance().addEffect(this);
		//remove();
		setTimer(duration);
	}

	@Override
	public void increase(double z) {
		System.out.println("increase old size" + this.size + " Duration" + this.duration);
		this.size++;
		System.out.println("increase new size" + size );
	}
	
	public void update(){
		increase(size);
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
	
	@SuppressWarnings("restriction")
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillOval(x-size/2, y-size/2, size, size);
		System.out.println("Explosion drawed");
		super.draw(gc);
	}

	@Override
	public void setTimer(int updateIntervall) {
		timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @SuppressWarnings("restriction")
			@Override
            public void run() {
            	Platform.runLater ( () ->remove()); 
            }
        }, 0, updateIntervall);
	}

}
