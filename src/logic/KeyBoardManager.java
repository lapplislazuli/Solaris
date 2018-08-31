/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package logic
 */
package logic;

import interfaces.UpdatingObject;

public class KeyBoardManager implements UpdatingObject {


	private static final KeyBoardManager INSTANCE = new KeyBoardManager();
	
	private KeyBoardManager() {};
	
	public KeyBoardManager getInstance() {return INSTANCE;}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
