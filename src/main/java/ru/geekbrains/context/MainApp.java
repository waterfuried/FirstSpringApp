package ru.geekbrains.context;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.context.config.AppConfig;

import java.io.*;
import java.util.*;

public class MainApp {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // добавление репозитория товаров в контекст автоматически вызовет его Autowired-метод
        ProductRepository repo = context.getBean(ProductRepository.class);
        List<Product> products = repo.getList();

        Cart cart = context.getBean(Cart.class);
        cart.show();

        // 4. ***Написать консольное приложение, позволяющее управлять корзиной.
        Scanner scanner = new Scanner(System.in);
        String[] cmd = null;
        do {
            System.out.println("Для управления корзиной используются следующие команды:\n"+
                    "newlist - создать новый список возможных товаров (при первом запуске создается автоматически)\n"+
                    "showlist - показать список возможных товаров\n"+
                    "showcart - отобразить содержимое корзины\n"+
                    "addall - добавить в корзину все товары по списку\n"+
                    "add X - добавить в корзину товар X\n"+
                    "del X - удалить из корзины товар X\n"+
                    "clean - очистить корзину\n"+
                    "exit - выход");
            try {
                cmd = scanner.nextLine().toLowerCase().split(" ");
                switch (cmd[0]) {
                    case "showlist": repo.showList(); break;
                    case "showcart": cart.show(); break;
                    case "newlist": products = repo.fillList(); break;
                    case "addall": cart.setAll(products); break;
                    case "add": cart.addById(Integer.parseInt(cmd[1])); break;
                    case "del": cart.removeById(Integer.parseInt(cmd[1])); break;
                    case "clean": cart.clean();
                }
            } catch (Exception ex) {}
        } while (cmd != null && !cmd[0].equals("exit"));

        context.close();
    }
}