package interfaces;

import logic.CollisionManager;

public interface DestructibleObject extends CollidingObject{
	public void destruct(CollidingObject other);
}
