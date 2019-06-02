package geom;

public class TridentShape extends CombinedShape{
	
	public TridentShape(int width, int length, int barThickness) {
		super();
	
		Rectangle rightStick = new Rectangle(new RelativePoint(center,-width/2,length/2), length, barThickness);
		Rectangle upperBar = new Rectangle(new RelativePoint(center,-barThickness/2,0), barThickness, width);
		Rectangle lowerBar = new Rectangle(new RelativePoint(center,barThickness/2-length,0), barThickness, width);
		Rectangle middleBar = new Rectangle(new RelativePoint(center,barThickness/2-length/2,0), barThickness, (int)(width*1.2));
		
		parts.add(rightStick);
		parts.add(upperBar);
		parts.add(lowerBar);
		parts.add(middleBar);
	}
}
