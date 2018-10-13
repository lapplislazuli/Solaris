package logic.interaction;

public final class Action {
	
	private final String name;
	private String description;
	private final Runnable action;
	
	public Action(String name, Runnable action){
		this.name = name;
		this.action=action;
	}
	
	public Action(String name,String description, Runnable action){
		this.name = name;
		this.action=action;
		this.description=description;
	}
	
	public String getDescription() {return description;}
	public void setDescription(String description) {
		this.description=description;
	}
	
	public String getName() {return name;}
	
	public void doAction() {
		action.run();
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
