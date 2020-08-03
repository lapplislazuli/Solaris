package logic.manager;

import java.util.Collection;
import interfaces.logical.UpdatingManager;

public abstract class BaseManager<T> implements UpdatingManager<T> {

	protected boolean running;
	protected Collection<T> registeredItems, scheduledRemovals, scheduledRegistrations;


	public Collection<T> getRegisteredItems() {
		return registeredItems;
	}

	
	public void toggleUpdate() {running =! running;}
	public boolean isRunning() {return running;}

	public void scheduleRegistration(T item) {
		scheduledRegistrations.add(item);
	}

	public void scheduleRemoval(T item) {		
		// This method is a little helper to avoid "ConcurrentModificationExceptions"
		// Some items want to remove themselves, and sometimes do so in their update. 
		// An example of this behaviour is the Spaceshuttle:
		// In its update, a spaceshuttle checks for destruction. If it is destroyed, it removes itself from the update manager.
		// If the spaceshuttle would alter the registeredItems at runtime, this would throw an error. 
		scheduledRemovals.add(item);
	}
	
	protected void refresh() {
		for(T t:scheduledRegistrations) {
			registerItem(t);
		}
		//registeredItems.addAll(scheduledRegistrations);
		registeredItems.removeAll(scheduledRemovals);
		
		scheduledRemovals.clear();
		scheduledRegistrations.clear();
	}
	
}
