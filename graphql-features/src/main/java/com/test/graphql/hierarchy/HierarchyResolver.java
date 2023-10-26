package com.test.graphql.hierarchy;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.InputArgument;
import com.test.graphql.generated.DgsConstants;
import com.test.graphql.generated.types.User;
import com.test.graphql.generated.types.UserHierarchy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@DgsComponent
public class HierarchyResolver {

    @Autowired
    private UserService userService;

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.User)
    public User getSingleUser(@InputArgument("id") Integer id) {
        return userService.getUser(id);
    }

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.UserHierarchy)
    public UserHierarchy getSingleUserInHierarchy(@InputArgument("id") Integer id) {
        return userService.getUserDetailsInHierarchy(id);
    }

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.UserNPlus1Problem)
    public List<UserHierarchy> getSingleUserInHierarchy() {
        return userService.getUsersHierarchyList();
    }

    @DgsData(parentType = DgsConstants.USERHIERARCHY.TYPE_NAME, field = DgsConstants.USERHIERARCHY.ProfileImage1)
    public String getSingleUserProfileImage1(DgsDataFetchingEnvironment dfe) {
        log.info("******* Arguments ******* , {}", dfe.getArguments());
        final UserHierarchy user = dfe.getSource();
        log.info("******* Source ******* , {}", dfe.getSource().toString());
        return userService.getUserProfileImage1(user.getId());
    }

    @DgsData(parentType = DgsConstants.USERHIERARCHY.TYPE_NAME, field = DgsConstants.USERHIERARCHY.ProfileImage2)
    public String getSingleUserProfileImage2(DgsDataFetchingEnvironment dfe) {
        log.info("******* Arguments ******* , {}", dfe.getArguments());
        final UserHierarchy user = dfe.getSource();
        log.info("******* Source ******* , {}", dfe.getSource().toString());
        return userService.getUserProfileImage2(user.getId());
    }

    @DgsData(parentType = DgsConstants.USERHIERARCHY.TYPE_NAME, field = DgsConstants.USERHIERARCHY.ProfileImage3)
    public String getSingleUserProfileImage3(DgsDataFetchingEnvironment dfe) {
        log.info("******* Arguments ******* , {}", dfe.getArguments());
        final UserHierarchy user = dfe.getSource();
        log.info("******* Source ******* , {}", dfe.getSource().toString());
        return userService.getUserProfileImage3(user.getId());
    }
}
