/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package interfaces
 */
package interfaces.logical;

public interface DestructibleObject extends CollidingObject,RemovableObject{
	public void destruct(CollidingObject other);
}
