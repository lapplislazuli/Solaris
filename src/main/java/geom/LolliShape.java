package geom;

import interfaces.geom.Shape;

public class LolliShape extends CombinedShape {
	
	private int radious,length,barthickness;
	
	public LolliShape(int radious, int barlength, int barthickness) {
		super();

		this.radious = radious; 
		this.length = barlength;
		this.barthickness = barthickness;
		
		Circle lolli = new Circle(new RelativePoint(center, barlength/2,0), radious);
		Rectangle stick = new Rectangle(center, barlength, barthickness);
		
		parts.add(stick);
		parts.add(lolli);
	}
	
	@Override
	public Shape copy() {
		return new LolliShape(radious,length,barthickness);
	}
}
