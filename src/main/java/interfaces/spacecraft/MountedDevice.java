package interfaces.spacecraft;

public interface MountedDevice {
	
	/*
	 * Interface so the Device can be launched/shot/started.
	 * requires sometimes some kind of setup before (such as: "setTarget")
	 */
	public void activate();
	
	public Spacecraft getParent();
	
}
