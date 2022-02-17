package com.javastart.repositories;

import com.javastart.entities.Auction;
import org.springframework.data.repository.CrudRepository;

public interface AuctionRepository extends CrudRepository<Auction,Long> {
}
