package geom;

import interfaces.geom.Shape;

public class TridentShape extends CombinedShape{
	
	private int width,length,barthickness;
	
	public TridentShape(int width, int length, int barthickness) {
		super();
		
		this.width = width; 
		this.length = length;
		this.barthickness = barthickness;
	
		Rectangle rightStick = new Rectangle(new RelativePoint(center,-width/2,length/2), length, barthickness);
		Rectangle upperBar = new Rectangle(new RelativePoint(center,-barthickness/2,0), barthickness, width);
		Rectangle lowerBar = new Rectangle(new RelativePoint(center,barthickness/2-length,0), barthickness, width);
		Rectangle middleBar = new Rectangle(new RelativePoint(center,barthickness/2-length/2,0), barthickness, (int)(width*1.2));
		
		parts.add(rightStick);
		parts.add(upperBar);
		parts.add(lowerBar);
		parts.add(middleBar);
	}
	@Override
	public Shape copy() {
		return new TridentShape(width,length,barthickness);
	}
}
