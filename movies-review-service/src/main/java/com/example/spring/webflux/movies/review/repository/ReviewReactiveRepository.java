package com.example.spring.webflux.movies.review.repository;

import com.example.spring.webflux.movies.review.domain.Review;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ReviewReactiveRepository extends ReactiveMongoRepository<Review, String> {

    //Flux<Review> findReviewsByMovieInfoId(String reviewId);

    Flux<Review> findReviewsByMovieInfoId(Long movieInfoId);
}
