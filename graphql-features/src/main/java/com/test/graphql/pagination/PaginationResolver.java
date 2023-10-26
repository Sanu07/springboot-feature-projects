package com.test.graphql.pagination;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.test.graphql.generated.DgsConstants;
import com.test.graphql.generated.types.User;
import com.test.graphql.generated.types.UserConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@DgsComponent
public class PaginationResolver {

    @Autowired
    private PaginationUserService userService;

    @DgsQuery(field = DgsConstants.QUERY.OffsetPagination)
    public List<User> getUsersUsingOffset(@InputArgument("offset") Integer offset, @InputArgument("limit") Integer limit) {
        return userService.getUsers(offset, limit);
    }

    @DgsQuery(field = DgsConstants.QUERY.CursorPagination)
    public UserConnection getUsersUsingCursor(
            @InputArgument("first") Integer first,
            @InputArgument("last") Integer last,
            @InputArgument("before") String before,
            @InputArgument("after") String after) {
        return userService.getUserConnection(new CursorInfo(before, after, first, last));
    }
}
