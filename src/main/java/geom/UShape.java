package geom;

import java.util.stream.Collectors;

import interfaces.geom.Shape;

public class UShape extends CombinedShape{
	
	private int width,length,barThickness;
	
	public UShape(int width, int length, int barThickness) {
		super();
	
		this.width=width; this.length=length;this.barThickness=barThickness;
		
		Rectangle rightStick = new Rectangle(new RelativePoint(center,-width/2,length/2), length, barThickness);
		Rectangle upperBar = new Rectangle(new RelativePoint(center,-barThickness/2,0), barThickness, width);
		Rectangle lowerBar = new Rectangle(new RelativePoint(center,barThickness/2-length,0), barThickness, width);
		
		parts.add(rightStick);
		parts.add(upperBar);
		parts.add(lowerBar);
	}
	
	@Override
	public Shape copy() {
		return new UShape(width,length,barThickness);
	}

}
