package ru.geekbrains.context;

public interface Repository {
    Product findById(Integer id) throws RuntimeException;
}