package ru.geekbrains.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.*;

// 2. Создаем бин Cart, в который можно добавлять и удалять товары по id.
@Component
// 3. При каждом запросе корзины из контекста, должна создаваться новая корзина.
@Scope("prototype")
public class Cart {
	private List<Product> cart, avail;
	// @Autowired
	// аннотация инициализрует репозиторий при первом к нему обращении,
	// однако не рекомендуется использовать ее для полей -
	// вместо этого нужно указывать ее для сеттера (см.ниже)
	private ProductRepository repo;

	// 3. При каждом запросе корзины из контекста, должна создаваться новая корзина.
	@PostConstruct
	public void init() { this.cart = new ArrayList<>(); }

	// вернуть весь список допустимых товаров
	public List<Product> getAvail() { return avail; }

	// задать список допустимых товаров
	public void setAvail(List<Product> avail) { this.avail = avail; }

	// вернуть все содержимое корзины
	public List<Product> getAll() { return cart; }

	// заполнить корзину по списку
	public void setAll(List<Product> list) { this.cart = list; }

	public void add(Product product) throws RuntimeException {
		if (!avail.contains(product))
			throw new RuntimeException("Недопустимый товар!");
		cart.add(product);
	}

	public void remove(Product product) throws RuntimeException {
		if (!cart.contains(product))
			throw new RuntimeException("Товара нет в корзине!");
		cart.remove(product);
	}

	// TODO: при добавлении того товара, который уже есть в корзине, он иногда(!) может не добавиться в нее
	public void addById(int id) { cart.add(repo.findById(id)); }

	// TODO: при удалении товара из корзины он иногда(!) удаляется и из списка товаров ProductRepository
	public void removeById(int id) { cart.removeIf(p -> p.getId() == id); }

	public void show() {
		if (cart.size() == 0)
			System.out.println("Корзина пуста");
		else {
			cart.forEach(p -> System.out.println(p.getId()+" "+p.getTitle()+" "+p.getPrice()));
			System.out.println("\nСумма товаров в корзине = "+cart.stream().mapToInt(Product::getPrice).sum());
		}
		System.out.println();
	}

	// TODO: при очистке корзины иногда(!) обнуляется и список товаров ProductRepository
	public void clean() { cart.clear(); }

	@Autowired
	public void setRepository(ProductRepository repo) { this.repo = repo; }
}