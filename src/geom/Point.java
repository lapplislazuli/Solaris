package geom;

public class Point {
	public int x,y,z;
	
	public Point(int x, int y) {
		this.x=x;
		this.y=y;
		z=0;
	}
	
	public Point(int x,int y,int z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public void move(int xDif, int yDif) {
		x+=xDif;
		y+=yDif;
	}
	public void move(int xDif, int yDif,int zDif) {
		move(xDif,yDif);
		z+=zDif;
	}
	
	public double degreeTo(Point other) {
		if(distanceTo(other)==0) 
			return 0;
		if(y<other.y)
			return Math.acos((x-other.x)/distanceTo(other));
		else
			return 2*Math.PI-Math.acos((x-other.x)/distanceTo(other));
	}
	
	public double distanceTo(Point other) {
		return Math.sqrt((x-other.x)*(x-other.x)+(y-other.y)*(y-other.y)+(z-other.z)*(z-other.z));
	}
	
	@Override
	public String toString() {
		return ("[" + x + "|" + y +"|"+z+"]");
	}
	
	@Override
	public Point clone() {
		return new Point(x,y,z);
	}
}
