package geom;

import interfaces.geom.Shape;

public class LolliShape extends CombinedShape {
	
	private int radious,length,barThickness;
	
	public LolliShape(int radious, int barlength, int barThickness) {
		super();

		this.radious=radious; this.length=length;this.barThickness=barThickness;
		
		Circle lolli = new Circle(new RelativePoint(center, barlength/2,0), radious);
		Rectangle stick = new Rectangle(center, barlength, barThickness);
		
		parts.add(stick);
		parts.add(lolli);
	}
	
	@Override
	public Shape copy() {
		return new LolliShape(radious,length,barThickness);
	}
}
