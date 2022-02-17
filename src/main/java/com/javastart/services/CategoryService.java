package com.javastart.services;

import com.javastart.entities.Auction;
import com.javastart.entities.Category;
import com.javastart.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Optional<Double> getAvgPriceForCategory(long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            Category existingCategory = category.get();
            List<Auction> auctions = existingCategory.getAuctions();
            double priceSum = auctions.stream().mapToDouble(Auction::getBuyNowPrice).sum();
            double avgPrice = priceSum / auctions.size();
            return Optional.of(avgPrice);
        } else {
            return Optional.empty();
        }
    }
}