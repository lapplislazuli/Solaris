package interfaces;

public interface DestructibleObject extends CollidingObject,RemovableObject{
	public void destruct(CollidingObject other);
}
