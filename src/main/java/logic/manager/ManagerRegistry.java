package logic.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.interfaces.Config;
import logic.interaction.ActionManager;
import logic.interaction.KeyBoardManager;
import logic.interaction.MouseManager;

public class ManagerRegistry {
	
	private static UpdateManager uptmng;
	private static EffectManager efxmng;
	private static DrawingManager drwmng;
	private static CollisionManager colmng;
	private static PlayerManager plrmng;
	private static ActionManager actmng;
	private static MouseManager moumng;
	private static KeyBoardManager keymng;
	
	private static Logger logger = LogManager.getLogger(ManagerRegistry.class);
	
	private static ManagerRegistry INSTANCE;
	
	public final static ManagerRegistry getInstance() {
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
		actmng=new ActionManager(true);
		moumng=new MouseManager();
		keymng=new KeyBoardManager();
		
		logger.info("ManagerRegistry (freshly) build");
	}
	
	public final void init(Config conf) {
		uptmng.init(conf);
		
		uptmng.registerItem(efxmng);
		uptmng.registerItem(drwmng);
		uptmng.registerItem(colmng);
		
		colmng.init(conf);
		efxmng.init(conf);
		
		moumng.init(conf);
		keymng.init(conf);
		//drwmng needs drawingcontext?
		
		logger.info("ManagerRegistry initialized with config");
	}
	
	public static UpdateManager getUpdateManager() 			{return uptmng;}
	public static EffectManager getEffectManager() 			{return efxmng;}
	public static DrawingManager getDrawingManager() 		{return drwmng;}
	public static CollisionManager getCollisionManager() 	{return colmng;}
	public static PlayerManager getPlayerManager() 			{return plrmng;}
	public static ActionManager getActionManager()			{return actmng;}
	public static MouseManager getMouseManager()			{return moumng;}
	public static KeyBoardManager getKeyBoardManager()		{return keymng;}
	
	public static void setUpdateManager(UpdateManager newmgr) 		{uptmng=newmgr;}
	public static void setEffectManager(EffectManager newmgr) 		{efxmng=newmgr;}
	public static void setDrawingManager(DrawingManager newmgr) 	{drwmng=newmgr;}
	public static void setCollisionManager(CollisionManager newmgr) {colmng=newmgr;}
	public static void setPlayerManager(PlayerManager newmgr) 		{plrmng=newmgr;}
	public static void setActionManager(ActionManager newmgr)		{actmng=newmgr;}
	public static void setMouseManager(MouseManager newmgr)			{moumng=newmgr;}
	public static void setKeyboardManager(KeyBoardManager newmgr)	{keymng=newmgr;}
	
	public final static void reset() {
		logger.info("Reset ManagerRegistry...");
		INSTANCE= new ManagerRegistry();
	}
}
