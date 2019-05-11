package interfaces.logical;

import java.util.Collection;

import config.Config;

public interface ManagerObject<T> extends UpdatingObject{
	
	public default void registerItem(T item)  {
		if(!getRegisteredItems().contains(item))
			getRegisteredItems().add(item);
	};
	public void init(Config c);
	public void reset();
	public Collection<T> getRegisteredItems();
	
	public void toggleUpdate();
	public boolean isRunning();
}
