package Singletons;

public abstract class SingletonBase {
    private static SingletonBase instance;

    protected SingletonBase() {
        // Prevent instantiation from outside the class
    }

    public static SingletonBase getInstance() {
        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }

    protected static synchronized SingletonBase createInstance() {
        // Concrete subclasses must implement this method to create their own instances
        return null;
    }
}