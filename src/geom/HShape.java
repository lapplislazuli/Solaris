package geom;

public class HShape extends CombinedShape{
	
	public HShape(int width, int height, int barthickness) {
		super();
		
		Rectangle midbar = new Rectangle(new RelativePoint(center,0,0), width, barthickness);
		Rectangle leftbar = new Rectangle(new RelativePoint(center,width/2,0), barthickness,height);
		Rectangle rightbar = new Rectangle(new RelativePoint(center,-width/2,0), barthickness, height);
		
		parts.add(midbar);
		parts.add(leftbar);
		parts.add(rightbar);
	}
	
}
