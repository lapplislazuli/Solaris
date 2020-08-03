package logic.manager;

import java.util.LinkedList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.interfaces.Config;
import interfaces.logical.Effect;

public class EffectManager extends BaseManager<Effect>{
	
	private static Logger logger = LogManager.getLogger(EffectManager.class);
	
	public EffectManager() {
		registeredItems = new LinkedList<Effect>();
		scheduledRemovals = new LinkedList<Effect>();
		scheduledRegistrations = new LinkedList<Effect>();
		logger.debug("Build EffectManager");
	}
	
	public void update() {
		if(running) {
			for(Effect e : registeredItems) {
				e.update();
			}
		}
		//The base-refresh works through the scheduled removals and registrations
		refresh();
	}
	
	public void init(Config c) {}

	public void reset() {
		registeredItems = new LinkedList<Effect>();
		scheduledRemovals = new LinkedList<Effect>();
		scheduledRegistrations = new LinkedList<Effect>();
		running = true;
		logger.debug("EffectManager reset");
	}

}
