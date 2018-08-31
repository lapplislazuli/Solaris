package logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import interfaces.DrawingObject;
import interfaces.TimerObject;
import interfaces.UpdatingObject;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import space.core.SpaceObject;

@SuppressWarnings("restriction")
public class UpdateManager implements TimerObject{

	public List<UpdatingObject> toUpdate;
	public List<DrawingObject> toDraw;
	private GraphicsContext gc;
	private Timer timer;
	
	private static final UpdateManager INSTANCE = new UpdateManager();
	
	private UpdateManager() {
		toUpdate=new LinkedList<UpdatingObject>();
		toDraw=new LinkedList<DrawingObject>();
	}
	public static UpdateManager getInstance() {return INSTANCE;}
	
	public void initUpdateManager(int updateIntervall, GraphicsContext gc) {
		this.gc = gc;
		CollisionManager.getInstance().initCollisionManager(1000, this);
		toUpdate.add(CollisionManager.getInstance());
		toUpdate.add(EffectManager.getInstance());
		setTimer(updateIntervall);
	}
	
	public void update() {
		updateAll();
		drawAll();
	}
	
	private void updateAll() {
		for(UpdatingObject updateMe : toUpdate)
			updateMe.update();
	}
	private void drawAll() {
		for(DrawingObject drawMe : toDraw)
			drawMe.draw(gc);
		EffectManager.getInstance().draw(gc);
	}
	
	public void addSpaceObject(SpaceObject o) {
		toUpdate.add(o);
		toDraw.add(o);
		for(SpaceObject child : o.getChildren())
			CollisionManager.getInstance().addCollidable(child);
	}
	
	@Override
	public void setTimer(int updateIntervall) {
		timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	Platform.runLater ( () ->update()); 
            }
        }, 0, updateIntervall);
	}

}
