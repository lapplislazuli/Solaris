package fakes;

import interfaces.drawing.*;

public class FakeDrawingObject implements ComplexDrawingObject{

	public boolean drawn;
	public boolean shapeDrawn;
	public boolean drawable;
	
	FakeDrawingObject(){
		drawn=false;
		shapeDrawn=false;
		drawable=true;
	}
	
	@Override
	public void draw(DrawingContext dc) {
		if(drawable)
			drawn=true;
	}

	@Override
	public void drawShape(DrawingContext dc) {
		if(drawable) {
			shapeDrawn=true;
			
		}
			
	}

	@Override
	public DrawingInformation getDrawingInformation() {
		return null;
	}

}
