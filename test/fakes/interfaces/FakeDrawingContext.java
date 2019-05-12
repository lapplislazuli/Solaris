package fakes.interfaces;
import java.util.LinkedList;
import java.util.List;

import interfaces.drawing.*;
public class FakeDrawingContext implements DrawingContext{
	
	public String error;
	public List<DrawingObject> drawnItems;
	
	public FakeDrawingContext(){
		drawnItems = new LinkedList<DrawingObject>();
	}

	public void resetContext() {
		error=null;
		drawnItems = new LinkedList<DrawingObject>();	
	}

	public void saveContext() {
		// Nothing?
	}

	
}
