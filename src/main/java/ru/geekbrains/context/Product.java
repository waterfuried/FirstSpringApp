package ru.geekbrains.context;

// 1. Есть класс Product (id, название, цена).
public class Product {
    private final Integer id;
    private final String title;
    private final int price;

    public Product(int id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public int getPrice() { return price; }

    static int randomNumber(int min, int max) {
        return min + Math.round((float)Math.random()*(max - min));
    }
}