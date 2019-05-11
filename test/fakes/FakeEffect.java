package fakes;

import interfaces.drawing.DrawingContext;
import interfaces.drawing.DrawingInformation;
import interfaces.logical.Effect;

public class FakeEffect implements Effect{
	
	public boolean removed;
	public boolean drawn;
	public boolean updated;
	
	public FakeEffect() {
		removed=false;
		drawn=false;
		updated=false;
	}
	
	public void update() {
		updated=true;
	}

	public void drawShape(DrawingContext dc) {
		drawn=true;
	}

	public DrawingInformation getDrawingInformation() {return null;}

	public void draw(DrawingContext dc) {
		drawn=true;
	}

	public void remove() {
		removed=true;
	}
	
}
