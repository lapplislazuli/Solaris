package logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import interfaces.DrawingObject;
import interfaces.UpdatingObject;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import space.core.SpaceObject;

public class UpdateManager implements UpdatingObject{

	public List<UpdatingObject> toUpdate;
	public List<DrawingObject> toDraw;
	private GraphicsContext gc;
	private CollisionManager cm;
	
	public UpdateManager(GraphicsContext gc) {
		toUpdate=new LinkedList<UpdatingObject>();
		toDraw=new LinkedList<DrawingObject>();
		this.gc = gc;
		cm= new CollisionManager();
		/*
		Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	Platform.runLater ( () ->cm.update() ); 
            }
        }, 0, 1000);*/
	}
	
	public void update() {
		updateAll();
		drawAll();
	}
	
	private void updateAll() {
		for(UpdatingObject updateMe : toUpdate)
			updateMe.update();
		cm.update();
	}
	private void drawAll() {
		for(DrawingObject drawMe : toDraw)
			drawMe.draw(gc);
	}
	
	public void addSpaceObject(SpaceObject o) {
		toUpdate.add(o);
		toDraw.add(o);
		
		for(SpaceObject child : ((SpaceObject)o).getChildren())
			cm.addCollidable(child);
	}
	/*
	private void refreshCollisionManager() {
		//System.out.println(x);
		for(UpdatingObject o: toUpdate)
			if(o instanceof SpaceObject)
				for(SpaceObject child : ((SpaceObject)o).getChildren())
						cm.addCollidable(child);
	}
	*/
}
