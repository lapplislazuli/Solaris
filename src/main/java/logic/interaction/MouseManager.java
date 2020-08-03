package logic.interaction;


import logic.manager.ManagerRegistry;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import config.interfaces.Config;
import geom.AbsolutePoint;
import interfaces.geom.Point;
import interfaces.logical.UpdatingManager;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MouseManager implements UpdatingManager<Action> {
	
	private Scene scene;
	private AbsolutePoint mousePos;
	
	private boolean running=true;

	private static Logger logger = LogManager.getLogger(MouseManager.class);
	
	private ActionManager actions;
	private Map<MouseButton,Action> mouseBindings;
	

	public MouseManager() {
		actions = ManagerRegistry.getActionManager();
		mouseBindings = new HashMap<MouseButton,Action>();
	};

	
	public void initScene(Scene scene) {
		this.scene = scene;
		//this.scene.addEventHandler(MouseEvent.MOUSE_MOVED, evt -> mouseMoved(evt));
        this.scene.addEventHandler(MouseEvent.MOUSE_PRESSED, evt -> mouseClicked(evt));
    }
	
	public void registerMouseBindings(MouseButton button, Action action) {
		if(mouseBindings.get(button) != null) {
			logger.info("Overrwrite Keybinding for " + button.toString() + " with Action " + action.getName());
		}
		mouseBindings.put(button,action);
	}
	
	/*
	private void mouseMoved(MouseEvent evt) {
		
	}
	*/
	
	public void mouseClicked(MouseEvent evt) {
		mousePos = new AbsolutePoint((int)evt.getSceneX(),(int)evt.getSceneY());
		if(mouseBindings.get(evt.getButton()) != null) {
			logger.debug("Player pressed " + evt.getButton().toString() + " and did " + mouseBindings.get(evt.getButton()).getName());
			mouseBindings.get(evt.getButton()).doAction();
		} else {
			logger.debug("No MouseBinding for this Action registered!");
		}
	}
	
	public Point getMousePos() {return mousePos;}
	
	public void update() {
		if(running) {
			//Maybe Increase a Timer how long a button has been pressed
		}
	}

	public void init(Config c) {
		for(Entry<MouseButton,String> binding : c.getMouseConfig().getKeyBindings().entrySet()) {
			if(actions.getActionByName(binding.getValue()) != null) {
				mouseBindings.put(binding.getKey(),actions.getActionByName(binding.getValue()));
			}
		}
	}

	public void reset() {
		actions = ManagerRegistry.getActionManager();
		mouseBindings = new HashMap<MouseButton,Action>();
	}

	public Collection<Action> getRegisteredItems() {
		return mouseBindings.entrySet().stream().map(t->t.getValue()).collect(Collectors.toList());
	}
	
	public Map<MouseButton, Action> getMouseBindings(){
		return mouseBindings;
	}
	
	public void toggleUpdate() {
		running =! running;
	}

	public boolean isRunning() {
		return running;
	}


	public void scheduleRegistration(Action item) {
		// Nothing to do at the moment?
	}

	public void scheduleRemoval(Action item) {
		// Nothing to do at the moment?
	}
}
