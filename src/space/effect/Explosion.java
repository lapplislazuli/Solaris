/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package space.effect
 */
package space.effect;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;


@SuppressWarnings("restriction")
public class Explosion extends TimerEffect {
	
	public double growthRate=1;
	protected Color color;
	
	public Explosion(String name, int x, int y, int lifetime, double size, Color color){
		super(name,x,y,size,lifetime);
		this.color = color;
	}
	public Explosion(String name, int x, int y, int lifetime, double size, double increaseFactor, Color color){
		super(name,x,y,size,lifetime);
		this.growthRate = increaseFactor;
		this.color = color;
	}
	
	public void update(){
		if(growthRate!=1)
			size*=growthRate;
		else
			size++;
	}
	
	public void draw(GraphicsContext gc) {
		gc.setFill(color);
		gc.fillOval(x-size/2, y-size/2, size, size);
		super.draw(gc);
	}

}
