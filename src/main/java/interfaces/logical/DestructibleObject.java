package interfaces.logical;

public interface DestructibleObject extends CollidingObject,RemovableObject{
	void destruct();
}
