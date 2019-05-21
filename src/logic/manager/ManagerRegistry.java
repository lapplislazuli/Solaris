package logic.manager;

import config.interfaces.Config;

public class ManagerRegistry {
	
	private static UpdateManager uptmng;
	private static EffectManager efxmng;
	private static DrawingManager drwmng;
	private static CollisionManager colmng;
	private static PlayerManager plrmng;
	
	private static ManagerRegistry INSTANCE;
	public static ManagerRegistry getInstance() {
		if(INSTANCE==null)
			INSTANCE= new ManagerRegistry();
		return INSTANCE;
	}
	
	private ManagerRegistry() {
		uptmng=new UpdateManager();
		efxmng=new EffectManager();
		drwmng=new DrawingManager();
		colmng=new CollisionManager();
		plrmng=new PlayerManager();
	}
	
	public void init(Config conf) {
		uptmng.init(conf);
		
		uptmng.registerItem(efxmng);
		uptmng.registerItem(drwmng);
		uptmng.registerItem(colmng);
		
		colmng.init(conf);
		efxmng.init(conf);
		//drwmng needs drawingcontext?
	}
	
	public static UpdateManager getUpdateManager() 			{return uptmng;}
	public static EffectManager getEffectManager() 			{return efxmng;}
	public static DrawingManager getDrawingManager() 		{return drwmng;}
	public static CollisionManager getCollisionManager() 	{return colmng;}
	public static PlayerManager getPlayerManager() 			{return plrmng;}
	
	
	public static void setUpdateManager(UpdateManager newmgr) 		{uptmng=newmgr;}
	public static void setEffectManager(EffectManager newmgr) 		{efxmng=newmgr;}
	public static void setDrawingManager(DrawingManager newmgr) 	{drwmng=newmgr;}
	public static void setCollisionManager(CollisionManager newmgr) {colmng=newmgr;}
	public static void setPlayerManager(PlayerManager newmgr) 		{plrmng=newmgr;}
	
	public static void reset() {
		INSTANCE= new ManagerRegistry();
	}
}
