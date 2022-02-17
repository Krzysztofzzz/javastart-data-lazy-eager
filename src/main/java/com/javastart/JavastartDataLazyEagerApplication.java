package com.javastart;

import com.javastart.entities.Auction;
import com.javastart.entities.Category;
import com.javastart.repositories.AuctionRepository;
import com.javastart.repositories.CategoryRepository;
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
        printCategoryAvgAuctionPrice(1L, categoryRepository);
        printCategoryAvgAuctionPrice(2L, categoryRepository);
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

    private static void printCategoryAvgAuctionPrice(Long categoryId, CategoryRepository categoryRepository) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            Category existingCategory = category.get();
            List<Auction> auctions = existingCategory.getAuctions();
            double priceSum = auctions.stream().mapToDouble(Auction::getBuyNowPrice).sum();
            double avgPrice = priceSum / auctions.size();
            System.out.printf("Średnia cena w kategorii %s to %.2f\n", existingCategory.getName(), avgPrice);
        } else {
            System.out.println("Brak kategorii o wskazanym id");
        }
    }

}
