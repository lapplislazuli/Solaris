package logic.manager;

import java.util.HashSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.interfaces.Config;
import interfaces.drawing.DrawingContext;
import interfaces.drawing.DrawingObject;

public class DrawingManager extends BaseManager<DrawingObject> {
	/*
	 * The DrawingManager draws items on the canvas. 
	 * on every update, it looks up the current drawable items from the update manager, and then draws them.
	 * There is one important "bug": The background needs to be drawn first! Otherwise, it looks like items are vanishing. 
	 * This is done with an drawing-priority. Items with the lowest priority are drawn first.
	 */

	private DrawingContext context;
	private boolean running = true; //If this is set to false, the items are not drawn but are still running and colliding. 

	private static Logger logger = LogManager.getLogger(DrawingManager.class);
	
	
	public DrawingManager() {
		registeredItems = new HashSet<DrawingObject>();
		scheduledRemovals = new HashSet<DrawingObject>();
		scheduledRegistrations = new HashSet<DrawingObject>();
		logger.info("Build DrawingManager");
	}
	
	private void drawAll() {
		registeredItems.stream()
			.sorted((a,b) -> {
				return Integer.compare(a.drawingPriority(), b.drawingPriority());
			})
			.forEach(d -> d.draw(context));
	}
	
	public void initDrawingManager(DrawingContext dc) {
		this.context = dc;
		logger.debug("Initialised DrawingManager");
	}
	
	@Override
	public void update() {
		if(running) {
			drawAll();
		}
		//The base-refresh works through the scheduled removals and registrations
		refresh();
	}

	public void init(Config c) {
		//Nothing?
		//Normal Init is done with DrawingContext, which is not good for a Config
	}
	
	@Override
	public void reset() {
		running = true;
		registeredItems = new HashSet<DrawingObject>();
		scheduledRemovals = new HashSet<DrawingObject>();
		scheduledRegistrations = new HashSet<DrawingObject>();
	}

	protected void refresh() {
		registeredItems = new HashSet<DrawingObject>();
		
		ManagerRegistry.getUpdateManager().getRegisteredItems()
			.stream()
			.filter(t -> t instanceof DrawingObject)
			.map(t -> (DrawingObject) t)
			.forEach(u -> registeredItems.add(u));
		
		super.refresh();
	}


}
