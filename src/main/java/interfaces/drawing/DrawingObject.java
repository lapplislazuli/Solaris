package interfaces.drawing;

public interface DrawingObject {
	
	public void draw(DrawingContext dc);
	
	/*
	 * It is important for some items to be drawn in a certain order (background first, planets second, missiles pretty late)
	 * This is done with an drawing-priority. Items with the lowest priority are drawn first.
	 * In case someone forgets to set drawingPriority explicit, the default is to set it to max, so the person will see its new item. 
	 */
	default public int drawingPriority() {return Integer.MAX_VALUE;}
}
