package com.example.spring.webflux.movies.service.util;

import com.example.spring.webflux.movies.service.exception.MoviesInfoServerException;
import com.example.spring.webflux.movies.service.exception.ReviewsServerException;
import reactor.core.Exceptions;
import reactor.util.retry.Retry;
import reactor.util.retry.RetrySpec;

import java.time.Duration;

public class RetryUtil {


    public static Retry retrySpec() {
        return RetrySpec.fixedDelay(3, Duration.ofSeconds(1))
                .filter((ex) -> ex instanceof MoviesInfoServerException || ex instanceof ReviewsServerException)
                .onRetryExhaustedThrow(((retryBackoffSpec, retrySignal) -> Exceptions.propagate(retrySignal.failure())));

    }
}
