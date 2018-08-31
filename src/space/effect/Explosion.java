package space.effect;

import javafx.scene.paint.Color;
import logic.EffectManager;


import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;


@SuppressWarnings("restriction")
public class Explosion extends TimerEffect {
	
	public double increaseFactor=1;
	protected Color color;
	
	public Explosion(String name, int x, int y, int lifetime, double size, double increaseFactor, Color color){
		super(name,x,y,size,lifetime);
		this.increaseFactor = increaseFactor;
		this.color = color;
		
	}
	public Explosion(String name, int x, int y, int lifetime, double size, Color color){
		super(name,x,y,size,lifetime);
		this.color = color;
	}
	
	public void update(){
		if(increaseFactor!=1)
			size*=increaseFactor;
		else
			size++;
	}
	
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillOval(x-size/2, y-size/2, size, size);
		super.draw(gc);
	}

}
