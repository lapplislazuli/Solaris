package logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import interfaces.logical.CollidingObject;
import interfaces.logical.DestructibleObject;
import interfaces.logical.TimerObject;
import javafx.application.Platform;
import space.core.SpaceObject;

@SuppressWarnings("restriction")
public class CollisionManager implements TimerObject{
	
	public final List<CollidingObject> collidables;
	public final List<DestructibleObject> destructibles;
	
	private Timer timer;
	private UpdateManager parent;
	private boolean running=true;
	
	private static CollisionManager INSTANCE;
	
	private CollisionManager() {
		collidables = new LinkedList<CollidingObject>();
		destructibles = new LinkedList<DestructibleObject>();
	}
	
	public static CollisionManager getInstance() {
		if(INSTANCE==null)
			INSTANCE=new CollisionManager();
		return INSTANCE;
	}
	
	public void initCollisionManager(int updateIntervall, UpdateManager parent) {
		setTimer(updateIntervall);
		this.parent=parent;
	}
	
	public void update() {
		if(running) {
			collidables.stream().forEach(e->e.updateHitbox());
			
			List<DestructibleObject> destroyed=new LinkedList<DestructibleObject>();
			for(DestructibleObject destructible : destructibles) {
				for(CollidingObject collider: collidables) {
					//if(destructible.collides(collider)&&collider.collides(destructible)) {
					if(collider.collides(destructible)) {
						destructible.destruct(collider);
						destroyed.add(destructible);
					}	
				}
			}
			destructibles.removeAll(destroyed);
			collidables.removeAll(destroyed);
		}	
	}
	
	public void empty() {
		collidables.clear();
		destructibles.clear();
	}
	
	public void add(CollidingObject o){
		collidables.add(o);
		if(o instanceof DestructibleObject)
			destructibles.add((DestructibleObject)o);
	}
	
	public void refresh() {
		if(running) {
			empty();
			for(SpaceObject o : getAllActiveSpaceObjects())
				add(o);
		}	
	}
	
	public List<SpaceObject> getAllActiveSpaceObjects() {	
		return parent.toUpdate.stream()
				.filter(updatingObject -> updatingObject instanceof SpaceObject)
				.map(spaceObject -> (SpaceObject)spaceObject)
				.flatMap(spaceObject -> spaceObject.getAllChildrenFlat().stream())
				.collect(Collectors.toList());
	}
	
	@Override
	public void setTimer(int updateIntervall) {
		timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
			@Override
            public void run() {
            	Platform.runLater ( () ->refresh()); 
            }
        }, 0, updateIntervall);
	}

	public void pause() {
		running=false;
	}

	public void start() {
		running=true;
	}
}
