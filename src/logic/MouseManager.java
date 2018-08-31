/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package logic
 */
package logic;

import interfaces.UpdatingObject;

public class MouseManager implements UpdatingObject {
	
	private static final MouseManager INSTANCE = new MouseManager();
	
	private MouseManager() {};
	
	public MouseManager getInstance() {return INSTANCE;}

	@Override
	public void update() {
		
	}
}
