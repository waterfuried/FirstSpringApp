package ru.geekbrains.context;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.context.config.AppConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainApp {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ProductService productService = context.getBean(ProductService.class);

        productService.write();



    }

}
