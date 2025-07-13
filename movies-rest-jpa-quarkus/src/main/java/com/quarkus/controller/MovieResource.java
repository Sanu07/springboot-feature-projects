package com.quarkus.controller;

import com.quarkus.model.Movie;
import com.quarkus.service.MovieService;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

@Slf4j
@Path("api/v1")
public class MovieResource {

    @Inject
    @Named("movieService")
    private MovieService movieService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findMovies(@QueryParam("genre") String genre) {
        if (genre == null || genre.isEmpty()) {
            List<Movie> movies = movieService.findAll();
            log.info("Total movies retrieved: {}", movies);
            return Response.ok(movies).build();
        } else {
            Set<Movie> movies = movieService.findMovieByGenre(genre);
            log.info("Total movies retrieved with genre {} and size {}", genre, movies.size());
            return Response.ok(movies).build();
        }
    }

    @GET
    @Path("name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findMovieByName(@PathParam("name") String name) {
        Movie movie = movieService.findMovieByName(name);
        log.info("Movie with name: {} id: {}", movie.getName(), movie.getId());
        return Response.ok(movie).build();
    }
}
