package org.example;

public interface Storage {
    void save(String data);

    String retrieve(int id);
}