package com.example.agro.repositories.rating;


import com.example.agro.models.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Ratings, Long> {

    List<Ratings> findBySellerId(Long SellerId);
    Optional<Ratings> findByRaterIdAndSellerId(Long RaterId, Long SellerId);
}
