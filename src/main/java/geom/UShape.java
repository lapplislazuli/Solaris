package geom;

import interfaces.geom.Shape;

public class UShape extends CombinedShape{
	
	private int width,length,barthickness;
	
	public UShape(int width, int length, int barthickness) {
		super();
	
		this.width = width; 
		this.length = length;
		this.barthickness = barthickness;
		
		Rectangle rightStick = new Rectangle(new RelativePoint(center,-width/2,length/2), length, barthickness);
		Rectangle upperBar = new Rectangle(new RelativePoint(center,-barthickness/2,0), barthickness, width);
		Rectangle lowerBar = new Rectangle(new RelativePoint(center,barthickness/2-length,0), barthickness, width);
		
		parts.add(rightStick);
		parts.add(upperBar);
		parts.add(lowerBar);
	}
	
	@Override
	public Shape copy() {
		return new UShape(width,length,barthickness);
	}

}
