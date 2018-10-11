package interfaces;

import interfaces.drawing.DrawingObject;

//Maybe Change Extends: Right at the moment only visible Items are Clickable, maybe change that when GUI is coming
public interface ClickableObject extends DrawingObject{
	public void click();
}
