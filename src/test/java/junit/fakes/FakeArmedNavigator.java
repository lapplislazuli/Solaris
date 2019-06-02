package junit.fakes;



import interfaces.geom.Point;
import interfaces.spacecraft.AggressiveNavigator;
import space.core.SpaceObject;

public class FakeArmedNavigator extends FakeNavigator implements AggressiveNavigator{
	
	boolean attacked=false,autoattacked=false;
	boolean doesAutoAttack = false;
	boolean isPlayer = false;
	
	public void attack(Point p) {
		attacked=true;
	}

	public void attack(SpaceObject o) {
		attacked=true;
	}

	public void autoAttack() {
		if(doesAutoAttack)
			autoattacked=true;
	}

	public boolean isPlayer() {
		return isPlayer;
	}

}
