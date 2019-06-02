package interfaces.logical;

public interface UpdatingManager<T> extends Manager<T>,UpdatingObject{
	public void toggleUpdate();
	public boolean isRunning();
}
