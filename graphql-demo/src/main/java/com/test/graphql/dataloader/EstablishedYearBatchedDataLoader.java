package com.test.graphql.dataloader;

import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.BatchLoader;
import org.dataloader.MappedBatchLoader;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@DgsDataLoader(name = "establishedYear")
public class EstablishedYearBatchedDataLoader implements BatchLoader<Integer, OffsetDateTime> {

    /**
     * This method will be called once, even if multiple datafetchers use the load() method on the DataLoader.
     * This way reviews can be loaded for all the Shows in a single call instead of per individual Show.
     */
    @Override
    public CompletionStage<List<OffsetDateTime>> load(List<Integer> keys) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.supplyAsync(() -> keys.stream().map(i -> OffsetDateTime.now()).collect(Collectors.toList()));
    }
}