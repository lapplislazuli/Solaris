/**
 * @Author Tino Stachel
 * @Created 31.08.2018
 * @Package logic
 */

package logic;

import java.util.LinkedList;
import java.util.List;

import interfaces.DrawingObject;
import interfaces.logical.UpdatingObject;
import javafx.scene.canvas.GraphicsContext;
import space.effect.Effect;

@SuppressWarnings("restriction")
public class EffectManager implements UpdatingObject, DrawingObject {
	
	private List<Effect> effects = new LinkedList<Effect>();
	
	private static EffectManager INSTANCE;
	
	private EffectManager() {
		effects = new LinkedList<Effect>();
	}
	
	public static EffectManager getInstance() {
		if(INSTANCE==null)
			INSTANCE=new EffectManager();
		return INSTANCE;
	}
	
	
	public void addEffect(Effect e){
		effects.add(e);
	}
	
	public void emptyEffects() {
		effects = new LinkedList<Effect>();
	}
	
	@Override
	public void update() {
		for(Effect e : effects)
			e.update();
	}
	
	@Override
	public void draw(GraphicsContext gc){
		for(Effect e : effects)
			e.draw(gc);
	}
	
	public void removeEffect(Effect e){
		effects.remove(e);
	}

}
