package com.javastart;

import com.javastart.entities.Auction;
import com.javastart.entities.Category;
import com.javastart.repositories.AuctionRepository;
import com.javastart.repositories.CategoryRepository;
import com.javastart.services.CategoryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class JavastartDataLazyEagerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(JavastartDataLazyEagerApplication.class, args);
        AuctionRepository auctionRepository = context.getBean(AuctionRepository.class);
        CategoryRepository categoryRepository = context.getBean(CategoryRepository.class);
        saveData(auctionRepository, categoryRepository);
        CategoryService categoryService = context.getBean(CategoryService.class);
        categoryService.getAvgPriceForCategory(1L)
                .ifPresent(avgPrice -> System.out.println("Średnia cena kategorii to " + avgPrice));
    }

    private static void saveData(AuctionRepository auctionRepository, CategoryRepository categoryRepository) {
        Auction auction1 = new Auction("Laptop Dell Inspirion", "Niezawodny i szybki komputer do biura i domu", 1500.0, 4999.0);
        Auction auction2 = new Auction("Macbook Pro 15 2020", "Komputer uwielbiany przez miliony", 5000.0, 7999.0);
        Auction auction3 = new Auction("Lenovo Probook 15", "Laptop, który zapewni Ci wygodną pracę", 2999.0, 5999.0);
        auctionRepository.save(auction1);
        auctionRepository.save(auction2);
        auctionRepository.save(auction3);
        Category category1 = new Category("Laptopy", "Znajdź komputer idealny dla siebie");
        category1.addAuction(auction1);
        category1.addAuction(auction2);
        category1.addAuction(auction3);
        categoryRepository.save(category1);
    }

}
