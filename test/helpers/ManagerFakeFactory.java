package helpers;

import fakes.FakeDrawingContext;
import logic.manager.DrawingManager;
import logic.manager.EffectManager;
import logic.manager.ManagerRegistry;

public class ManagerFakeFactory {
	
	public static EffectManager fakeEfxManager() {
		ManagerRegistry.getInstance();
		EffectManager mngr = new EffectManager();
		ManagerRegistry.setEffectManager(mngr);
		mngr.init(null);
		return mngr;
	}

	public static DrawingManager fakeDrawingManager() {
		ManagerRegistry.getInstance();
		DrawingManager mnger = new DrawingManager();
		FakeDrawingContext fakeContext = new FakeDrawingContext();

		mnger.initDrawingManager(fakeContext);
		return mnger;
	}
	
}
