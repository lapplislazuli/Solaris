package logic.manager;

import java.util.LinkedList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.interfaces.Config;
import interfaces.logical.CollidingObject;
import interfaces.logical.RecursiveObject;
import interfaces.logical.TimerObject;
import interfaces.logical.UpdatingObject;
import javafx.application.Platform;
import space.core.SpaceObject;

public class UpdateManager extends BaseManager<UpdatingObject> implements TimerObject{

	private Timer timer;
	
	private static Logger logger = LogManager.getLogger(UpdateManager.class);
	
	public UpdateManager() {
		registeredItems = new LinkedList<UpdatingObject>();
		scheduledRemovals = new LinkedList<UpdatingObject>();
		scheduledRegistrations = new LinkedList<UpdatingObject>();
		logger.info("Build UpdateManager");
	}
	
	public void init(Config config) {
		setTimer(config.getSettings().getUpdateIntervall());
		running = !config.getSettings().isPaused();
	}
	
	public void registerItem(UpdatingObject o) {
		if(!registeredItems.contains(o)) {
			registeredItems.add(o);
		}
	}
	
	public void update() {
		if(running) {
			for(UpdatingObject updateMe : registeredItems) {
				updateMe.update();	
			}
		}
		//The base-refresh works through the scheduled removals and registrations
		refresh();
	}
	
	public void addSpaceObject(SpaceObject o) {
		registerItem(o);
		//ManagerRegistry.getDrawingManager().registerItem(o);
		for(RecursiveObject child : o.getAllChildren()) {
			if(child instanceof CollidingObject) {
				ManagerRegistry.getCollisionManager().registerItem((CollidingObject)child);
				if(child instanceof UpdatingObject) {
					registerItem((UpdatingObject)child);
				}
			}
		}
	}
	
	public Set<CollidingObject> getAllActiveColliders() {	
		Set<CollidingObject> kids = getRegisteredItems().stream()
			.filter(updatingObject -> updatingObject instanceof RecursiveObject)
			.map(spaceObject -> (RecursiveObject)spaceObject)
			.flatMap(recursive -> recursive.getAllChildren().stream())
			.filter(o -> o instanceof CollidingObject)
			.map(o->(CollidingObject)o)
			.collect(Collectors.toSet());
			
		Set<CollidingObject> roots = getRegisteredItems().stream()
				.filter(t->t instanceof CollidingObject)
				.map(o->(CollidingObject)o)
				.collect(Collectors.toSet());
		roots.addAll(kids);
		return roots;
	}
	
	@Override
	public void setTimer(int updateIntervall) {
		// The timer runs the update method of UpdateManager-Instance, which invokes all the other Managers
		// The only exception is the DrawingManager, which has its own timer (to separate fps from update timing).
		timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	Platform.runLater ( () ->update()); 
            }
        }, 0, updateIntervall);
	}
	
	public void reset() {
		registeredItems = new LinkedList<UpdatingObject>();
		scheduledRemovals = new LinkedList<UpdatingObject>();
		scheduledRegistrations = new LinkedList<UpdatingObject>();
		running = true;
	}
	

	
}
