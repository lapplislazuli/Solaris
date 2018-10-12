package logic.manager;

import java.util.LinkedList;
import java.util.List;

import interfaces.drawing.DrawingContext;
import interfaces.logical.UpdatingObject;
import space.effect.Effect;

@SuppressWarnings("restriction")
public class EffectManager implements UpdatingObject {
	
	private List<Effect> registeredItems = new LinkedList<Effect>();
	
	private static EffectManager INSTANCE;
	
	private EffectManager() {
		registeredItems = new LinkedList<Effect>();
	}
	
	public static EffectManager getInstance() {
		if(INSTANCE==null)
			INSTANCE=new EffectManager();
		return INSTANCE;
	}
	
	
	public void addEffect(Effect e){
		registeredItems.add(e);
	}
	
	public void emptyEffects() {
		registeredItems = new LinkedList<Effect>();
	}
	
	@Override
	public void update() {
		for(Effect e : registeredItems)
			e.update();
	}
	
	public void removeEffect(Effect e){
		registeredItems.remove(e);
	}

	public void drawEffects(DrawingContext dc) {
		for(Effect e : registeredItems)
			e.draw(dc);
	}

}
