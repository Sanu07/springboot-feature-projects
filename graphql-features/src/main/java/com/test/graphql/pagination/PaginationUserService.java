package com.test.graphql.pagination;

import com.test.graphql.generated.types.User;
import com.test.graphql.generated.types.UserConnection;
import com.test.graphql.generated.types.UserEdge;
import com.test.graphql.generated.types.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaginationUserService {

    @Autowired
    private PaginationUserRepository userRepository;

    public List<User> getUsers(Integer offset, Integer limit) {
        return userRepository.getUsers().stream().skip(offset).limit(limit).collect(Collectors.toList());
    }

    public UserConnection getUserConnection(CursorInfo cursorInfo) {

        int pageSize = cursorInfo.pageSize();

        var limit = pageSize + 1;
        List<User> userResults;
        boolean hasNextPage;
        boolean hasPreviousPage;
        String startCursor;
        String endCursor;

        if (cursorInfo.hasCursors() && cursorInfo.hasNextPageCursor()) {
            userResults =
                    userRepository.usersWithNextPageCursor(CursorInfo.decode(cursorInfo.getCursor()), limit);
            int resultSize = userResults.size();
            var firstResult = userResults.get(0);
            hasPreviousPage = CursorInfo.decode(cursorInfo.getCursor()) > 0;
            startCursor = CursorInfo.encode(firstResult.getId());
            int endCursorIndex = resultSize > pageSize ? pageSize - 1 : resultSize - 1;
            endCursor = CursorInfo.encode(userResults.get(endCursorIndex).getId());
            hasNextPage = resultSize > pageSize;

        } else if (cursorInfo.hasCursors() && cursorInfo.hasPrevPageCursor()) {
            userResults =
                    userRepository.usersWithPreviousPageCursor(
                            CursorInfo.decode(cursorInfo.getCursor()), limit);
            int resultSize = userResults.size();
            var firstResult = userResults.get(0);
            hasNextPage = CursorInfo.decode(cursorInfo.getCursor()) + limit < 10;
            startCursor = CursorInfo.encode(firstResult.getId());
            int endCursorIndex = resultSize > pageSize ? pageSize - 1 : resultSize - 1;
            endCursor = CursorInfo.encode(userResults.get(endCursorIndex).getId());
            hasPreviousPage = resultSize > pageSize;
        } else {
            userResults = userRepository.booksWithoutCursor(limit);
            int resultSize = userResults.size();
            hasPreviousPage = false;
            var firstResult = userResults.get(0);
            startCursor = CursorInfo.encode(firstResult.getId());
            int endCursorIndex = resultSize > pageSize ? pageSize - 1 : resultSize - 1;
            endCursor = CursorInfo.encode(userResults.get(endCursorIndex).getId());
            hasNextPage = resultSize > pageSize;
        }

        if (userResults.size() == 0) {
            return UserConnection.newBuilder().edges(null).pageInfo(UserInfo.newBuilder()
                    .endCursor(null)
                    .startCursor(null)
                    .hasNextPage(false)
                    .hasPreviousPage(false)
                    .build()).build();
        }

        List<UserEdge> userEdges =
                userResults.stream()
                        .limit(cursorInfo.pageSize())
                        .map(
                                userResult ->
                                        UserEdge.newBuilder()
                                                        .cursor(CursorInfo.encode(userResult.getId()))
                                                        .node(User.newBuilder()
                                                        .id(userResult.getId())
                                                        .name(userResult.getName())
                                                        .country(userResult.getCountry()).build()).build())

                        .toList();

        UserConnection userConnection = UserConnection.newBuilder()
                .edges(userEdges)
                .pageInfo(UserInfo.newBuilder()
                        .startCursor(startCursor)
                        .endCursor(endCursor)
                        .hasPreviousPage(hasPreviousPage)
                        .hasNextPage(hasNextPage)
                        .build())
                .build();

        log.info("Pages, {}", userConnection);
        return userConnection;
    }
}
