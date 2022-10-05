package ru.geekbrains.context;

public interface Repository {
    Product findById(int id) throws RuntimeException;
}