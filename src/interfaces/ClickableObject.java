/**
 * @Author Leonhard Applis
 * @Created 01.09.2018
 * @Package interfaces
 */
package interfaces;

//Maybe Change Extends: Right at the moment only visible Items are Clickable, maybe change that when GUI is coming
public interface ClickableObject extends DrawingObject{
	public void click();
}
