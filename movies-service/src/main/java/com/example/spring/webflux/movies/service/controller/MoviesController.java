package com.example.spring.webflux.movies.service.controller;

import com.example.spring.webflux.movies.service.client.MoviesInfoRestClient;
import com.example.spring.webflux.movies.service.client.ReviewsRestClient;
import com.example.spring.webflux.movies.service.domain.Movie;
import com.example.spring.webflux.movies.service.domain.MovieInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/movies")
public class MoviesController {

    private MoviesInfoRestClient moviesInfoRestClient;
    private ReviewsRestClient reviewsRestClient;

    public MoviesController(MoviesInfoRestClient moviesInfoRestClient, ReviewsRestClient reviewsRestClient) {
        this.moviesInfoRestClient = moviesInfoRestClient;
        this.reviewsRestClient = reviewsRestClient;
    }

    @GetMapping("/{id}")
    public Mono<Movie> retrieveMovieById(@PathVariable("id") String movieId){

        return moviesInfoRestClient.retrieveMovieInfo(movieId)
                //moviesInfoRestClient.retrieveMovieInfo_exchange(movieId)
                .flatMap(movieInfo -> {
                    var reviewList = reviewsRestClient.retrieveReviews(movieId)
                            .collectList();
                   return reviewList.map(reviews -> new Movie(movieInfo, reviews));
                });

    }

    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<MovieInfo> retrieveMovieInfos(){

        return moviesInfoRestClient.retrieveMovieInfoStream();

    }
}
