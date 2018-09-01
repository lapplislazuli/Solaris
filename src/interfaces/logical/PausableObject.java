/**
 * @Author Leonhard Applis
 * @Created 01.09.2018
 * @Package interfaces
 */
package interfaces.logical;

public interface PausableObject extends UpdatingObject {
	public void pause();
	public void start();
}
