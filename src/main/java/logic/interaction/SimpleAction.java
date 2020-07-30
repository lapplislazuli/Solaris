package logic.interaction;

public final class SimpleAction implements Action {
	
	private final String name;
	private String description;
	private final Runnable action;
	
	public SimpleAction(String name, Runnable action){
		this.name = name;
		this.action = action;
	}
	
	public SimpleAction(String name,String description, Runnable action){
		this.name = name;
		this.action = action;
		this.description = description;
	}
	
	public String getDescription() {return description;}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {return name;}
	
	public void doAction() {
		action.run();
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Action) {
			return ((Action)o).hashCode() == hashCode();
		}
		return false;
	}
}
