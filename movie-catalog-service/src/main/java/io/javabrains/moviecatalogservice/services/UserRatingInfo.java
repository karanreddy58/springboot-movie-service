package io.javabrains.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfo {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://RATING-DATA-SERVICE/ratingsdata/users/" + userId, UserRating.class);
    }

    private UserRating getFallbackUserRating(String userId) {
        UserRating userRating= new UserRating();
        userRating.setUserId(userId);
        Rating rating = new Rating("0",0);
        userRating.setUserRatings(Arrays.asList(rating));
        return userRating;
    }
}
