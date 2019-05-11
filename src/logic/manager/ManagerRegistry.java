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
		uptmng=new UpdateManager();
		efxmng=EffectManager.getInstance();
		drwmng=DrawingManager.getInstance();
		colmng=new CollisionManager();
	}
	
	public void init(Config conf) {
		uptmng.init(conf);
		
		uptmng.registerItem(efxmng);
		uptmng.registerItem(drwmng);
		uptmng.registerItem(colmng);
		
		colmng.init(conf);
		//Efx does not need init
		//drwmng needs drawingcontext?
	}
	
	public static UpdateManager getUpdateManager() {return uptmng;}
	public static EffectManager getEffectManager() {return efxmng;}
	public static DrawingManager getDrawingManager() {return drwmng;}
	public static CollisionManager getCollisionManager() {return colmng;}
	
	public static void setUpdateManager(UpdateManager newmgr) 		{uptmng=newmgr;}
	public static void setEffectManager(EffectManager newmgr) 		{efxmng=newmgr;}
	public static void setDrawingManager(DrawingManager newmgr) 	{drwmng=newmgr;}
	public static void setCollisionManager(CollisionManager newmgr) {colmng=newmgr;}
}
