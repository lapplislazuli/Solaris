package logic.interaction;

public interface Action {

	String getDescription();

	void setDescription(String description);

	String getName();

	void doAction();

}