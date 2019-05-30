package logic.manager;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.pmw.tinylog.Logger;

import config.interfaces.Config;
import interfaces.drawing.DrawingContext;
import interfaces.drawing.DrawingObject;
import interfaces.logical.Effect;
import interfaces.logical.UpdatingManager;

public class EffectManager implements UpdatingManager<Effect>,DrawingObject {
	
	private List<Effect> registeredItems = new LinkedList<Effect>();
	private boolean running=true;
	
	public EffectManager() {
		registeredItems = new LinkedList<Effect>();
		running=true;
		Logger.debug("Build EffectManager");
	}
	
	public void update() {
		if(running)
			for(Effect e : registeredItems)
				e.update();
	}
	
	public void removeEffect(Effect e){
		registeredItems.remove(e);
	}

	public void init(Config c) {
		ManagerRegistry.getDrawingManager().registerItem(this);
	}

	public void reset() {
		 registeredItems = new LinkedList<Effect>();
		 running=true;
		 ManagerRegistry.getDrawingManager().registerItem(this);
	}

	public Collection<Effect> getRegisteredItems() {return registeredItems;}
	public void toggleUpdate() {running=!running;}
	public boolean isRunning() {return running;}

	public void draw(DrawingContext dc) {
		//if(running) // Does not Work as intended?
			for(Effect e : registeredItems)
				e.draw(dc);
	}

}
