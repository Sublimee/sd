package org.example;

public class Main {
    public static void main(String[] args) {
        Storage storage = new DatabaseStorage();

        storage.save("First one!");
        storage.save("Second one!");
        storage.save("Another one!");

        System.out.println(storage.retrieve(1)); // First one!
        System.out.println(storage.retrieve(2)); // Second one!
        System.out.println(storage.retrieve(3)); // Another one!
    }
}