/**
 * @Author Leonhard Applis
 * @Created 20.09.2018
 * @Package geom
 */
package geom;

public abstract class BaseArea implements Area{
	public Point center;
	protected BaseArea(Point center) {
		this.center=center;
	}
	
	protected BaseArea() {
		center= new Point(0,0);
	}
}
