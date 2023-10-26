package com.test.graphql.hierarchy;

import com.test.graphql.generated.types.Country;
import com.test.graphql.generated.types.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepository {

    private List<User> users;

    public List<User> getUsers() {
        return this.users;
    }

    public String getProfileImage1(Integer id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return String.format("s3://my-aws-s3-bucket/%s/image1", id);
    }

    public String getProfileImage2(Integer id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return String.format("s3://my-aws-s3-bucket/%s/image2", id);
    }

    public String getProfileImage3(Integer id) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return String.format("s3://my-aws-s3-bucket/%s/image3", id);
    }

    @PostConstruct
    public void initialize() {
        users = Arrays.asList(User.newBuilder().id(1).name("Name1").country(Country.US).build(),
                User.newBuilder().id(2).name("Name2").country(Country.US).build(),
                User.newBuilder().id(3).name("Name3").country(Country.US).build());
    }
}
