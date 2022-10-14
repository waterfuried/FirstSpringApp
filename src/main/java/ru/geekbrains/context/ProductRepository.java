// 1. Товары хранятся в бине ProductRepository...
// надо полагать, что этот компонент является своеобразным складом продуктов,
// однако условие "при старте в него нужно добавить 5 любых товаров" говорит
// о том, что в какой-то момент со склада могут быть доступны только определенные
// товары, определяемые неким списком
package ru.geekbrains.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Primary
//аннотация используется при реализации нескольких классов для одного интерфейса
//и ей помечается тот класс, который должен использоваться "по умолчанию"
public class ProductRepository implements Repository {
    private final String[] PRODUCT_TYPE = { "Вода", "Хлеб", "Картофель", "Молоко", "Яйцо куриное",
            "Мясо", "Соль", "Сахар", "Помидоры", "Лук" };
    private final int[] PRODUCT_PRICE = { 20, 25, 30, 50, 55,
            200, 35, 70, 100, 40 };

    // ...в виде List<Product>
    private List<Product> products;

    // TODO: если передать число товаров в виде аргумента (а Autowired помечаются методы с аргументом(-ами)),
    //  выпадет следующее исключение (как его избежать, я так и не понял):
    // Exception encountered during context initialization - cancelling refresh attempt:
    // org.springframework.beans.factory.UnsatisfiedDependencyException:
    // Error creating bean with name 'productRepository':
    // Unsatisfied dependency expressed through method 'fillList' parameter 0;
    // nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException:
    // No qualifying bean of type 'int' available:
    // expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}
    // Exception in thread "main" org.springframework.beans.factory.UnsatisfiedDependencyException:
    // Error creating bean with name 'productRepository':
    // Unsatisfied dependency expressed through method 'fillList' parameter 0;
    // nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException:
    // No qualifying bean of type 'int' available:
    // expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: {}
    @Autowired
    public List<Product> fillList() {
        // 1 ...при старте в него нужно добавить 5 любых товаров.
        final int MAX_PROD = 5;
        products = new ArrayList<>();
        int id;
        for (int i = 0; i < MAX_PROD; i++) {
            boolean already;
            do {
                id = Product.randomNumber(0, PRODUCT_TYPE.length - 1);
                already = false;
                for (int j = 0; j < i && !already; j++)
                    // сравнивать методом equals можно только объектные типы данных (с учетом автоупаковки)
                    already = products.get(j).getId().equals(id);
            } while (already);
            products.add(new Product(id, PRODUCT_TYPE[id], PRODUCT_PRICE[id]));
        }
        return products;
    }

    public List<Product> getList() { return products; }

    public void showList() {
        for (Product product : products)
            System.out.println(product.getId() + " " + product.getTitle());
        System.out.println();
    }

    // TODO: если указан недопустимый id, исключение не выбрасывается
    @Override
    public Product findById(Integer id) throws RuntimeException {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst()
                .orElseThrow(() -> new RuntimeException("Товар не найден"));
    }
}