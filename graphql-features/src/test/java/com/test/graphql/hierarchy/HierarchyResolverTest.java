package com.test.graphql.hierarchy;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import com.netflix.graphql.dgs.autoconfig.DgsExtendedScalarsAutoConfiguration;
import com.test.graphql.customscalardir.MyCustomISBN;
import com.test.graphql.generated.types.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {DgsAutoConfiguration.class, HierarchyResolver.class, DgsExtendedScalarsAutoConfiguration.class, MyCustomISBN.class})
public class HierarchyResolverTest {

    @Autowired
    private DgsQueryExecutor queryExecutor;

    @MockBean
    private UserService userService;

    @Test
    public void testGetSingleUser() {

        when(userService.getUser(1)).thenReturn(User.newBuilder().id(1).name("user-1").build());

        String graphqlQuery = "query { user(id: 1) { id name } }";
        final String name = queryExecutor.executeAndExtractJsonPath(graphqlQuery, "data.user.name");

        // Assert the results
        assertEquals("user-1", name);
    }
}