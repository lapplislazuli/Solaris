package logic.manager;

import java.util.HashSet;
import java.util.Set;

import org.pmw.tinylog.Logger;

import interfaces.drawing.DrawingContext;
import interfaces.drawing.DrawingObject;
import interfaces.logical.UpdatingObject;

public class DrawingManager implements UpdatingObject {

	private static DrawingManager INSTANCE;
	
	public Set<DrawingObject> registeredItems;
	private DrawingContext context;
	
	private DrawingManager() {
		registeredItems=new HashSet<DrawingObject>();
		Logger.debug("Build DrawingManager");
	}
	
	public static DrawingManager getInstance() {
		if(INSTANCE==null)
			INSTANCE=new DrawingManager();
		return INSTANCE;
	}
	
	private void drawAll() {
		for(DrawingObject drawable : registeredItems)
			drawable.draw(context);
		EffectManager.getInstance().drawEffects(context);
	}
	
	public void initDrawingManager(DrawingContext dc) {
		this.context = dc;
		Logger.debug("Initialised DrawingManager");
	}
	
	@Override
	public void update() {
		drawAll();	
	}
	
}
