package ru.geekbrains.context;

// 1. Есть класс Product (id, название, цена).
public class Product {
    private final int id;
    private final String title;
    private final int price;

    public Product(int id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public int getPrice() { return price; }

    static int randomNumber(int min, int max) {
        return min + Math.round((float)Math.random()*(max - min));
    }
}