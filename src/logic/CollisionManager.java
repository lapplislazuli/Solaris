/**
 * @Author Leonhard Applis
 * @Created 31.08.2018
 * @Package logic
 */
package logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import interfaces.CollidingObject;
import interfaces.DestructibleObject;
import interfaces.DrawingObject;
import interfaces.TimerObject;
import interfaces.UpdatingObject;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import space.core.SpaceObject;

public class CollisionManager implements TimerObject {
	
	public List<CollidingObject> collidables = new LinkedList<CollidingObject>();
	public List<DestructibleObject> destructibles = new LinkedList<DestructibleObject>();
	
	private Timer timer;
	private UpdateManager parent;
	
	private static final CollisionManager INSTANCE = new CollisionManager();
	
	private CollisionManager() {
		collidables = new LinkedList<CollidingObject>();
		destructibles = new LinkedList<DestructibleObject>();
	}
	
	public static CollisionManager getInstance() {return INSTANCE;}
	
	public void initCollisionManager(int updateIntervall, UpdateManager parent) {
		setTimer(updateIntervall);
		this.parent=parent;
	}
	
	public void update() {
		List<DestructibleObject> destroyed=new LinkedList<DestructibleObject>();
		for(DestructibleObject destructible : destructibles) {
			for(CollidingObject collider: collidables) {
				if(destructible!=collider && destructible.collides(collider)) {
					destructible.destruct(collider);
					destroyed.add(destructible);
				}	
			}
		}
		destructibles.removeAll(destroyed);
		collidables.removeAll(destroyed);
	}
	
	public void emptyCollisions() {
		collidables = new LinkedList<CollidingObject>();
		destructibles = new LinkedList<DestructibleObject>();
	}
	
	public void addCollidable(CollidingObject o){
		collidables.add(o);
		if(o instanceof DestructibleObject)
			destructibles.add((DestructibleObject)o);
	}
	
	public void refresh() {
		collidables = new LinkedList<CollidingObject>();
		destructibles = new LinkedList<DestructibleObject>();
		
		for(SpaceObject o : getCurrentObjects())
			addCollidable(o);
	}
	
	public List<SpaceObject> getCurrentObjects() {
		//Read current toUpdate Objects, whether new ones are spawned somewhere
		List<SpaceObject> collisionItems=new LinkedList<SpaceObject>();
		for(UpdatingObject uO : parent.toUpdate)
			if(uO instanceof SpaceObject)
				collisionItems.addAll(((SpaceObject) uO).getChildren());
		return collisionItems;
	}
	
	@Override
	public void setTimer(int updateIntervall) {
		timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @SuppressWarnings("restriction")
			@Override
            public void run() {
            	Platform.runLater ( () ->refresh()); 
            }
        }, 0, updateIntervall);
	}
}
