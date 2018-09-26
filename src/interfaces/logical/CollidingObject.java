package interfaces.logical;

public interface CollidingObject {
	public boolean collides(CollidingObject other);
	public void updateHitbox();
}
