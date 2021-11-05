package demolition.misc;

public abstract class Event implements Runnable {

	public void begin() {
		new Thread(this::run).start();
	}

	public void begin(boolean condition) {
		if(condition) {
			new Thread(this::run).start();
		}
	}

	@Override 
	public void run() {};

}
