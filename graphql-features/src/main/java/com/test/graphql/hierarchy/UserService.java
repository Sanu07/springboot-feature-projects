package com.test.graphql.hierarchy;

import com.test.graphql.generated.types.User;
import com.test.graphql.generated.types.UserHierarchy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User getUser(Integer id) {
        return repository.getUsers().stream().filter(user -> user.getId() == id).peek(user -> {
            user.setProfileImage1(repository.getProfileImage1(user.getId()));
            user.setProfileImage2(repository.getProfileImage2(user.getId()));
            user.setProfileImage3(repository.getProfileImage3(user.getId()));
        }).findAny().orElse(null);
    }

    public UserHierarchy getUserDetailsInHierarchy(Integer id) {
        return repository.getUsers().stream().filter(user -> user.getId() == id)
                .map(user -> UserHierarchy.newBuilder()
                        .name(user.getName())
                        .country(user.getCountry())
                        .id(user.getId())
                        .build())
                .findAny()
                .orElse(null);
    }

    public List<UserHierarchy> getUsersHierarchyList() {
        return repository.getUsers().stream().map(user -> UserHierarchy.newBuilder()
                .name(user.getName())
                .country(user.getCountry())
                .id(user.getId())
                .build())
                .collect(Collectors.toList());
    }

    public String getUserProfileImage1(Integer id) {
        return repository.getProfileImage1(id);
    }

    public String getUserProfileImage2(Integer id) {
        return repository.getProfileImage2(id);
    }

    public String getUserProfileImage3(Integer id) {
        return repository.getProfileImage3(id);
    }
}
