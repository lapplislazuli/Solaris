package logic.manager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.pmw.tinylog.Logger;

import config.interfaces.Config;
import interfaces.drawing.DrawingContext;
import interfaces.drawing.DrawingObject;
import interfaces.logical.UpdatingManager;

public class DrawingManager implements UpdatingManager<DrawingObject> {

	public Set<DrawingObject> registeredItems;
	private DrawingContext context;
	private boolean running=true;
	
	public DrawingManager() {
		registeredItems=new HashSet<DrawingObject>();
		Logger.debug("Build DrawingManager");
	}
	
	private void drawAll() {
		for(DrawingObject drawable : registeredItems)
			drawable.draw(context);
		//ManagerRegistry.getEffectManager().drawEffects(context);
	}
	
	public void initDrawingManager(DrawingContext dc) {
		this.context = dc;
		Logger.debug("Initialised DrawingManager");
	}
	
	@Override
	public void update() {
		if(running)
			drawAll();	
	}

	public void init(Config c) {
		//Nothing?
		//Normal Init is done with DrawingContext, which is not good for a Config
	}

	@Override
	public void reset() {
		running=true;
		registeredItems=new HashSet<DrawingObject>();
	}

	public void toggleUpdate() {running=!running;}
	public boolean isRunning() {return running;}

	public Collection<DrawingObject> getRegisteredItems() {
		return registeredItems;
	}
	
}
