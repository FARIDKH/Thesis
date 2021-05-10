package com.example.agro.rest.rating;


import com.example.agro.message.request.RateRequest;
import com.example.agro.models.Ratings;
import com.example.agro.service.rating.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/rate")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class RatingController {

    @Autowired
    private RatingService ratingService;


    @GetMapping("/rater/{raterId}/seller/{sellerID}")
    @ResponseBody
    public Ratings getUserRatingForSeller(
            @PathVariable Long raterId,
            @PathVariable Long sellerID
    ){
        return ratingService.getUserRatingForSeller(raterId,sellerID);
    }

    @GetMapping("/getSellerRating/{sellerID}")
    @ResponseBody
    public float getSellerRating(
            @PathVariable Long sellerID
    ){
        return ratingService.getSellerRatingValue(sellerID);
    }

    @PostMapping("")
    @ResponseBody
    public Ratings rateUser(
            @RequestBody RateRequest rateRequest
            ){
        return ratingService.rateUser(rateRequest);
    }

}
