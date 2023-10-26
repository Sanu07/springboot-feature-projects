package com.test.graphql.pagination;

import com.test.graphql.generated.types.Country;
import com.test.graphql.generated.types.User;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Pagination
@Repository
public class PaginationUserRepository {

    private List<User> users;

    public List<User> getUsers() {
        return this.users;
    }

    @PostConstruct
    public void initialize() {
        users = Arrays.asList(
                User.newBuilder().id(1).name("Name1").country(Country.US).build(),
                User.newBuilder().id(2).name("Name2").country(Country.US).build(),
                User.newBuilder().id(3).name("Name3").country(Country.US).build(),
                User.newBuilder().id(4).name("Name4").country(Country.US).build(),
                User.newBuilder().id(5).name("Name5").country(Country.US).build(),
                User.newBuilder().id(6).name("Name6").country(Country.US).build(),
                User.newBuilder().id(7).name("Name7").country(Country.US).build(),
                User.newBuilder().id(8).name("Name8").country(Country.US).build(),
                User.newBuilder().id(9).name("Name9").country(Country.US).build(),
                User.newBuilder().id(10).name("Name10").country(Country.US).build()
                );
    }

    public List<User> usersWithNextPageCursor(Integer id, int limit) {
        return users.stream().skip(id).limit(limit).collect(Collectors.toList());
    }

    public List<User> usersWithPreviousPageCursor(Integer id, int limit) {
        return users.stream().skip(id - limit).limit(limit).collect(Collectors.toList());
    }

    public List<User> booksWithoutCursor(int limit) {
        return users.stream().limit(limit).collect(Collectors.toList());
    }
}
