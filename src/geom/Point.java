package geom;

public interface Point {
	public int getX() ;
	public int getY() ;
	public int getZ() ;
	
	public void setX(int val);
	public void setY(int val);
	public void setZ(int val);
	
	default public double degreeTo(Point other) {
		if(distanceTo(other)==0) 
			return 0;
		if(getY()<other.getY())
			return Math.acos((getX()-other.getX())/distanceTo(other));
		else
			return 2*Math.PI-Math.acos((getX()-other.getX())/distanceTo(other));
	}
	
	default public double distanceTo(Point other) {
		return Math.sqrt((getX()-other.getX())*(getX()-other.getX())+(getY()-other.getY())*(getY()-other.getY())+(getZ()-other.getZ())*(getZ()-other.getZ()));
	}
	
	public Point clone();
}
