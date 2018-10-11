package interfaces.drawing;

public interface ComplexDrawingObject extends DrawingObject{
	public void drawShape(DrawingContext dc);
	
	public DrawingInformation getDrawingInformation();
}
