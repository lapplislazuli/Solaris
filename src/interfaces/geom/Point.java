package interfaces.geom;

import geom.AbsolutePoint;

public interface Point {
	public int getX() ;
	public int getY() ;
	public int getZ() ;
	
	public void setX(int val);
	public void setY(int val);
	public void setZ(int val);
	
	default public double degreeTo(Point other) {
		double degree;
		if(distanceTo(other)==0) 
			degree= 0;
		else if(getY()<other.getY())
			degree=Math.acos((other.getX()-getX())/distanceTo(other));
		else 
			degree=2*Math.PI-Math.acos((other.getX()-getX())/distanceTo(other));
		
		if(degree>=2*Math.PI)
			degree-=2*Math.PI;
		return degree;
	}
	
	default public double distanceTo(Point other) {
		return Math.sqrt((getX()-other.getX())*(getX()-other.getX())+(getY()-other.getY())*(getY()-other.getY())+(getZ()-other.getZ())*(getZ()-other.getZ()));
	}
	
	default public boolean samePosition(Point other) {
		return
				   getX()==other.getX()
				&& getY()==other.getY()
				&& getZ()==other.getZ();
	}
	
	public Point clone();
	
	default public AbsolutePoint absoluteClone() {
		return 
			new AbsolutePoint(getX(),getY());
	}
}
