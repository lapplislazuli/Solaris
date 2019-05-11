package logic.manager;

import config.Config;

public class ManagerRegistry {
	
	private static UpdateManager uptmng;
	private static EffectManager efxmng;
	private static DrawingManager drwmng;
	private static CollisionManager colmng;
	

	private static ManagerRegistry INSTANCE;
	public static ManagerRegistry getInstance() {
		if(INSTANCE==null)
			INSTANCE= new ManagerRegistry();
		return INSTANCE;
	}
	
	private ManagerRegistry() {
		uptmng=UpdateManager.getInstance();
		efxmng=EffectManager.getInstance();
		drwmng=DrawingManager.getInstance();
		colmng=CollisionManager.getInstance();
	}
	
	public void init(Config conf) {
		uptmng.init(conf);
		//Efx does not need init
		//drwmng needs drawingcontext?
	}
	
	public static UpdateManager getUpdateManager() {return uptmng;}
	public static EffectManager getEffectManager() {return efxmng;}
	public static DrawingManager getDrawingManager() {return drwmng;}
	public static CollisionManager getCollisionManager() {return colmng;}
}
