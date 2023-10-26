package com.test.graphql.dataloader;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.test.graphql.generated.DgsConstants;
import com.test.graphql.generated.types.Director;
import com.test.graphql.generated.types.Movie;
import graphql.schema.DataFetchingEnvironment;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
public class DataLoaderResolver {

    @Autowired
    private MovieService movieService;

    @DgsQuery(field = DgsConstants.QUERY.Movies)
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }

    @DgsData(parentType = DgsConstants.MOVIE.TYPE_NAME, field = DgsConstants.MOVIE.Director)
    public CompletableFuture<Director> director(DataFetchingEnvironment dfe) {

        DataLoader<Integer, Director> dataLoader = dfe.getDataLoader("directors");
        final Movie movie = dfe.getSource();

        return dataLoader.load(movie.getId());
    }
}
