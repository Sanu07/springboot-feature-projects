package com.test.graphql.dataloader;

import com.netflix.graphql.dgs.DgsDataLoader;
import com.test.graphql.generated.types.Employee;
import org.dataloader.MappedBatchLoader;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

@DgsDataLoader(name = "establishedYear", caching = true)
public class EstablishedYearDataLoader implements MappedBatchLoader<Integer, OffsetDateTime> {

    /**
     * This method will be called once, even if multiple datafetchers use the load() method on the DataLoader.
     * This way reviews can be loaded for all the Shows in a single call instead of per individual Show.
     */
    @Override
    public CompletionStage<Map<Integer, OffsetDateTime>> load(Set<Integer> keys) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.supplyAsync(() -> Map.of(
                1, OffsetDateTime.now(),
                2, OffsetDateTime.now(),
                3, OffsetDateTime.now(),
                4, OffsetDateTime.now(),
                15, OffsetDateTime.now(),
                6, OffsetDateTime.now(),
                7, OffsetDateTime.now()));
    }
}