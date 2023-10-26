package com.test.graphql.dataloader;

import com.test.graphql.generated.types.Director;
import com.test.graphql.generated.types.Movie;
import lombok.Getter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Getter
public class MovieService {

    private final List<Movie> movies = new ArrayList<>();
    private final List<Director> directors = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        for (int i = 1; i <= 5; i++) {
            directors.add(Director.newBuilder().id(i).name("director-" + i).build());
        }
        for (int i = 1; i <= 50; i++) {
            movies.add(Movie.newBuilder().id(i).title("title-" + i).build());
        }

    }

    public Map<Integer, Director> loadDirectors(Set<Integer> keys) {
        Map<Integer, Director> map = new HashMap<>();
        for (int i = 1; i <= 50; i++) {
            map.put(i, directors.get(i % 5));
        }
        return map;
    }
}
