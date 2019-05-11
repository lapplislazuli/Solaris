package interfaces.logical;

import java.util.Collection;

import config.Config;

public interface ManagerObject<T> extends UpdatingObject{
	
	public void registerItem(T item);
	public void init(Config c);
	public void reset();
	public Collection<T> getRegisteredItems();
}
