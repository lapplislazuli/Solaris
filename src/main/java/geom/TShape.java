package geom;

import interfaces.geom.Shape;

public class TShape extends CombinedShape{

	private int width,length,barthickness;
	
	public TShape(int width, int length, int barthickness) {
		super();

		this.width = width; 
		this.length = length;
		this.barthickness = barthickness;
		
		Rectangle stick = new Rectangle(new RelativePoint(center,barthickness/2,0), length, barthickness);
		Rectangle bar = new Rectangle(new RelativePoint(center,-barthickness/2,0), barthickness, width);
		
		parts.add(stick);
		parts.add(bar);	
	}

	@Override
	public Shape copy() {
		return new TShape(width,length,barthickness);
	}

}
