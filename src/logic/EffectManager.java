/**
 * @Author Tino Stachel
 * @Created 31.08.2018
 * @Package logic
 */

package logic;

import java.util.LinkedList;
import java.util.List;

import interfaces.drawing.DrawingContext;
import interfaces.logical.UpdatingObject;
import space.effect.Effect;

@SuppressWarnings("restriction")
public class EffectManager implements UpdatingObject {
	
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
	
	public void removeEffect(Effect e){
		effects.remove(e);
	}

	public void drawEffects(DrawingContext dc) {
		for(Effect e : effects)
			e.draw(dc);
	}

}
