package interfaces.logical;

import java.util.Collection;

public interface RecursiveObject {
	public Collection<RecursiveObject> getAllChildren();
}
