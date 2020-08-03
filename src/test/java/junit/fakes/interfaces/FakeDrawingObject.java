package junit.fakes.interfaces;

import interfaces.drawing.*;
import interfaces.logical.UpdatingObject;

public class FakeDrawingObject implements ComplexDrawingObject, UpdatingObject{

	public boolean drawn;
	public boolean shapeDrawn;
	public boolean drawable;
	public boolean updated;
	
	public FakeDrawingObject(){
		drawn=false;
		shapeDrawn=false;
		drawable=true;
	}
	
	@Override
	public void draw(DrawingContext dc) {
		fakeDraw(dc);
	}

	private void fakeDraw(DrawingContext dc){
		FakeDrawingContext fc = (FakeDrawingContext) dc;
		if(drawable) {
			if(fc.error!=null)
				//throw new Exception(fc.error);
				return;
			else {
				fc.drawnItems.add(this);
				drawn = true;
			}
		}
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

	@Override
	public void update() {
		updated = true;
	}

}
