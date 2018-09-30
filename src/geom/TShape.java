package geom;

public class TShape extends CombinedShape{
	
	public TShape(int width, int length, int barThickness) {
		super();
		
		Rectangle stick = new Rectangle(new RelativePoint(center,barThickness/2,0), length, barThickness);
		Rectangle bar = new Rectangle(new RelativePoint(center,-barThickness/2,0), barThickness, width);
		
		parts.add(stick);
		parts.add(bar);	
	}

}
