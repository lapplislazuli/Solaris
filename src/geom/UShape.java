package geom;

public class UShape extends CombinedShape{
	
	public UShape(int width, int length, int barThickness) {
		super();
	
		Rectangle rightStick = new Rectangle(new RelativePoint(center,-width/2,length/2), length, barThickness);
		Rectangle upperBar = new Rectangle(new RelativePoint(center,-barThickness/2,0), barThickness, width);
		Rectangle lowerBar = new Rectangle(new RelativePoint(center,barThickness/2-length,0), barThickness, width);
		
		parts.add(rightStick);
		parts.add(upperBar);
		parts.add(lowerBar);
	}

}
