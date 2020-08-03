package interfaces.logical;

import java.util.Collection;

import config.interfaces.Config;

public interface Manager <T>{
	
	@Deprecated
	public default void registerItem(T item)  {
		if(!getRegisteredItems().contains(item)) {
			getRegisteredItems().add(item);
		}
	};
	
	public void scheduleRegistration(T item);
	public void scheduleRemoval(T item);
	
	public void init(Config c);
	public void reset();
	public Collection<T> getRegisteredItems();
	
}
