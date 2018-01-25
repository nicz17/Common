package common;

public class SingletonTemplate {
	
	/** the singleton instance */
	private static SingletonTemplate _instance = null;
	
	/** Gets the singleton instance. */
	public static SingletonTemplate getInstance() {
		if (_instance == null)
			_instance = new SingletonTemplate();
		return _instance;
	}
	
	/** Private singleton constructor */
	private SingletonTemplate() {
	}

}
