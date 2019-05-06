package geom;

import interfaces.geom.Point;

public class RelativePoint implements Point{
	
	protected Point anker;
	protected int xDif,yDif,zDif;
	
	public RelativePoint(Point p,int xDif, int yDif) {
		this.anker=p;
		this.xDif=xDif;
		this.yDif=yDif;
		zDif=0;
	}
	
	public RelativePoint(Point p,int xDif, int yDif, int zDif) {
		this.anker=p;
		this.xDif=xDif;
		this.yDif=yDif;
		this.zDif=zDif;
	}
	
	public int getX() {return anker.getX()+xDif;}
	public int getY() {return anker.getY()+yDif;}
	public int getZ() {return anker.getZ()+zDif;}
	
	public void setX(int val) {xDif=val-anker.getX();}
	public void setY(int val) {yDif=val-anker.getY();}
	public void setZ(int val) {zDif=val-anker.getZ();}
	
	public void setXDif(int val) {xDif=val;}
	public void setYDif(int val) {yDif=val;}
	public void setZDif(int val) {zDif=val;}

	@Override
	public String toString() {
		return ("[" + getX() + "|" + getY() +"|"+getZ()+"]");
	}
	
	@Override
	public RelativePoint clone() {
		return new RelativePoint(anker,xDif,yDif,zDif);
	}

	@Override
	public int hashCode() {
		int result= Integer.hashCode(getX());
		result = 31 * result + Integer.hashCode(getY());
		result = 31 * result + Integer.hashCode(getZ());
		return result;
	}
}
