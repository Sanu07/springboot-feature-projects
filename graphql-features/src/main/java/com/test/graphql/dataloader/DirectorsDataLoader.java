package com.test.graphql.dataloader;

import com.netflix.graphql.dgs.DgsDataLoader;
import com.test.graphql.generated.types.Director;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(name = "directors", maxBatchSize = 10)
public class DirectorsDataLoader implements MappedBatchLoader<Integer, Director> {

    @Autowired
    MovieService movieService;

    @Override
    public CompletionStage<Map<Integer, Director>> load(Set<Integer> keys) {
        return CompletableFuture.supplyAsync(() -> movieService.loadDirectors(keys));
    }
}