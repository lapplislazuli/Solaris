package interfaces;

public interface DestructibleObject extends CollidingObject{
	//public void destruct();
	
	public void destruct(CollidingObject other);
}
