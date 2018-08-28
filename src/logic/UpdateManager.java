package logic;

import java.util.LinkedList;
import java.util.List;

import interfaces.DrawingObject;
import interfaces.UpdatingObject;

import javafx.scene.canvas.GraphicsContext;
import space.core.SpaceObject;

public class UpdateManager implements UpdatingObject{

	public List<UpdatingObject> toUpdate;
	public List<DrawingObject> toDraw;
	private GraphicsContext gc;
	
	public UpdateManager(GraphicsContext gc) {
		toUpdate=new LinkedList<UpdatingObject>();
		toDraw=new LinkedList<DrawingObject>();
		this.gc = gc;
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
	}
	
	public void addSpaceObject(SpaceObject o) {
		toUpdate.add(o);
		toDraw.add(o);
	}
}
