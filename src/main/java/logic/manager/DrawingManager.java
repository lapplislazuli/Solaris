package logic.manager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.interfaces.Config;
import interfaces.drawing.DrawingContext;
import interfaces.drawing.DrawingObject;
import interfaces.logical.UpdatingManager;

public class DrawingManager implements UpdatingManager<DrawingObject> {

	public Set<DrawingObject> registeredItems;
	private DrawingContext context;
	private boolean running = true; //If this is set to false, the items are not drawn but are still running and colliding. 

	private static Logger logger = LogManager.getLogger(DrawingManager.class);
	
	public DrawingManager() {
		registeredItems = new HashSet<DrawingObject>();
		logger.info("Build DrawingManager");
	}
	
	private void drawAll() {
		for(DrawingObject drawable : registeredItems) {
			drawable.draw(context);	
		}
	}
	
	public void initDrawingManager(DrawingContext dc) {
		this.context = dc;
		logger.debug("Initialised DrawingManager");
	}
	
	@Override
	public void update() {
		if(running) {
			refresh();
			drawAll();
		}
	}

	public void init(Config c) {
		//Nothing?
		//Normal Init is done with DrawingContext, which is not good for a Config
	}

	@Override
	public void reset() {
		running = true;
		registeredItems = new HashSet<DrawingObject>();
	}

	protected void refresh() {
		registeredItems = new HashSet<DrawingObject>();
		
		ManagerRegistry.getUpdateManager().getRegisteredItems()
			.stream()
			.filter(t -> t instanceof DrawingObject)
			.map(t -> (DrawingObject) t)
			.forEach(u -> registeredItems.add(u));
	}
	
	public void toggleUpdate() {running =! running;}
	public boolean isRunning() {return running;}

	public Collection<DrawingObject> getRegisteredItems() {
		return registeredItems;
	}
	
}
