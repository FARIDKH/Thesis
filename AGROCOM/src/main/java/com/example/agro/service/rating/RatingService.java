package com.example.agro.service.rating;


import com.example.agro.message.request.RateRequest;
import com.example.agro.models.Ratings;
import com.example.agro.repositories.rating.RatingRepository;
import com.example.agro.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    public Ratings getUserRatingForSeller(Long raterID, Long sellerID){
        Optional<Ratings> ratingsOptional = ratingRepository.findByRaterIdAndSellerId(raterID,sellerID);
        if (ratingsOptional.isEmpty()){
            return null;
        } else {
            return ratingsOptional.get();
        }
    }


    public float getSellerRatingValue(Long sellerId){
        List<Ratings> ratingsList = ratingRepository.findBySellerId(sellerId);

        if (ratingsList.isEmpty()){
            return 0;
        } else {
            float sum = 0;
            for(int i=0;i<ratingsList.size();i++){
                sum = sum + ratingsList.get(i).getValue();
            }
            return sum/ (ratingsList.size());
        }


    }

    public Ratings rateUser(RateRequest rateRequest){
        System.out.println(rateRequest.toString());
        Long raterId = rateRequest.getRaterId();
        Long sellerId = rateRequest.getSellerId();
        Long value = rateRequest.getValue();

        Optional<Ratings> optionalRating = ratingRepository.findByRaterIdAndSellerId(raterId,sellerId);
        Ratings r;
        if(optionalRating.isEmpty()){
            r = new Ratings();
        } else {
            r = optionalRating.get();
        }

        r.setRater(userRepository.findById(raterId).get());
        r.setSeller(userRepository.findById(sellerId).get());
        r.setValue(value);
        ratingRepository.save(r);
        return r;
    }

}
