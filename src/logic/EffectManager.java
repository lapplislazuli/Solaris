package logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import interfaces.CollidingObject;
import interfaces.DestructibleObject;
import interfaces.TimerObject;
import interfaces.UpdatingObject;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import space.core.SpaceObject;
import space.effect.Effect;

public class EffectManager implements TimerObject {
	
	public List<Effect> effects = new LinkedList<Effect>();
	
	private Timer timer;
	private UpdateManager parent;
	
	private static final EffectManager INSTANCE = new EffectManager();
	
	private EffectManager() {
		effects = new LinkedList<Effect>();
	}
	
	public static EffectManager getInstance() {return INSTANCE;}
	
	public void initEffectManager(int updateIntervall, UpdateManager parent) {
		setTimer(updateIntervall);
		this.parent=parent;
	}
	
	public void addEffect(Effect e){
		effects.add(e);
	}
	
	public void emptyEffects() {
		effects = new LinkedList<Effect>();
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
	for(Effect e : effects)
		e.update();
	}
	
	public void draw(GraphicsContext gc){
		for(Effect e : effects)
			e.draw(gc);
	}
	
	public void removeEffect(Effect e){
		effects.remove(e);
	}

	@Override
	public void setTimer(int updateIntervall) {
		// TODO Auto-generated method stub
		
	}

}
