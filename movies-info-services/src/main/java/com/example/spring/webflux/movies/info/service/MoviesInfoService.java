package com.example.spring.webflux.movies.info.service;


import com.example.spring.webflux.movies.info.domain.MovieInfo;
import com.example.spring.webflux.movies.info.repository.MovieInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class MoviesInfoService {


    private MovieInfoRepository movieInfoRepository;

    public MoviesInfoService(MovieInfoRepository movieInfoRepository) {
        this.movieInfoRepository = movieInfoRepository;
    }

    public Flux<MovieInfo> getAllMovieInfos(){

        return  movieInfoRepository.findAll();
    }

    public Flux<MovieInfo> getMovieInfoByYear(Integer year){

        return movieInfoRepository.findByYear(year);
    }

    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {
        log.info("addMovieInfo : {} " , movieInfo );
        return movieInfoRepository.save(movieInfo)
                .log();
    }

    public Mono<MovieInfo> getMovieInfoById(String id) {
        return movieInfoRepository.findById(id);
    }

    public Mono<MovieInfo> updateMovieInfo(MovieInfo movieInfo, String id) {
        return movieInfoRepository.findById(id)
                .flatMap(movieInfo1 -> {
                    movieInfo1.setCast(movieInfo.getCast());
                    movieInfo1.setName(movieInfo.getName());
                    movieInfo1.setRelease_date(movieInfo.getRelease_date());
                    movieInfo1.setYear(movieInfo.getYear());
                    return movieInfoRepository.save(movieInfo1);
                });


    }

    public Mono<Void> deleteMovieInfoById(String id) {
        return movieInfoRepository.deleteById(id);
    }
}
