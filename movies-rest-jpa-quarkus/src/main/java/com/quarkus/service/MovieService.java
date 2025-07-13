package com.quarkus.service;


import com.quarkus.model.Movie;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
@Named("movieService")
public class MovieService {

    private final List<Movie> movies;

    public MovieService() {
        this.movies = Arrays.asList(
                Movie.builder().id(1L).name("movie-1").genre("COMEDY").build(),
                Movie.builder().id(2L).name("movie-2").genre("THRILLER").build(),
                Movie.builder().id(3L).name("movie-3").genre("SAD").build(),
                Movie.builder().id(4L).name("movie-4").genre("SUSPENSE").build()
        );
    }

    public List<Movie> findAll() {
        return new ArrayList<>(this.movies);
    }

    public Set<Movie> findMovieByGenre(String genre) {
        return this.movies.stream()
                .filter(movie -> movie.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toUnmodifiableSet());
    }

    public Movie findMovieByName(String name) {
        return movies.stream()
                .filter(movie -> movie.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Movie not found: " + name));
    }
}
