package com.quarkus.controller;

import com.quarkus.model.UserTodo;
import com.quarkus.proxy.TodoProxy;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("todos")
@Slf4j
public class UserTodoResource {

    @RestClient
    private TodoProxy todoProxy;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Fallback(fallbackMethod = "fallBackFindTodo")
    @Timeout(20)
    @Retry(maxRetries = 2, maxDuration = 500)
    @CircuitBreaker(requestVolumeThreshold = 4, delay = 2000, failureRatio = 0.6)
    public Response findTodo(@PathParam("id") Integer id) {
        log.info("**** Executing `findTodo` method ****");
        UserTodo userTodo = todoProxy.findById(id);
        log.info("UserTodo with userId: {}, status: {}", userTodo.getUserId(), userTodo.isCompleted());
        return Response.ok(userTodo).build();
    }

    private Response fallBackFindTodo(Integer id) {
        log.info("Executing FallBack for id:{}", id);
        return Response.ok().build();
    }
}
