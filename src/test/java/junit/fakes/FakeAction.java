package junit.fakes;



import logic.interaction.Action;

public class FakeAction implements Action {
	
	public boolean actionDone=false;
	public String name=null,description=null;
	public String exception=null;
	
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description=description;}
	public String getName() {return name;}

	public void doAction() {
		actionDone=true;
	}

}
