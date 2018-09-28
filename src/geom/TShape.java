package geom;

public class TShape extends CombinedShape{
	
	public TShape(int barWidth, int barSpan, int stickWidth, int stickLength) {
		super();
		Rectangle stick = new Rectangle(stickLength, stickWidth);
		Rectangle bar = new Rectangle(barWidth, barSpan);
		
		parts.add(stick);
		parts.add(bar);
		
	}

}
