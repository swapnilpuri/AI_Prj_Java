package com.learning.adk.designpatterns;

public class Singleton {
    private static Singleton instance;

    // Private constructor to prevent direct instantiation
    private Singleton() {
        System.out.println("Singleton instance created.");
    }

    // Public static method to get the single instance of the class
    public static Singleton getInstance() {
        if (instance == null) {
            // Thread-safe way to create the instance (Double-checked locking)
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello from Singleton!");
    }

    // Optional: Example of how to use it
    public static void main(String[] args) {
        Singleton s1 = Singleton.getInstance();
        s1.showMessage();

        Singleton s2 = Singleton.getInstance();
        s2.showMessage();

        // Both s1 and s2 refer to the same instance
        System.out.println("Are s1 and s2 the same instance? " + (s1 == s2));
    }
}
