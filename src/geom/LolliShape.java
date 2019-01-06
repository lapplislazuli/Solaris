package geom;

public class LolliShape extends CombinedShape {
	
	public LolliShape(int radious, int barlength, int barThickness) {
		super();
		
		Circle lolli = new Circle(new RelativePoint(center, barlength/2,0), radious);
		Rectangle stick = new Rectangle(center, barlength, barThickness);
		
		parts.add(stick);
		parts.add(lolli);
	}
}
