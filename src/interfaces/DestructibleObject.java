package interfaces;

public interface DestructibleObject extends CollidingObject{
	public void destruct(CollidingObject other);
}
