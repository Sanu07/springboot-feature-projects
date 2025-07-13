package com.quarkus.proxy;

import com.quarkus.model.UserTodo;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("todos")
@RegisterRestClient(baseUri = "https://jsonplaceholder.typicode.com/")
@Produces(MediaType.APPLICATION_JSON)
public interface TodoProxy {

    @GET
    @Path("WWW/{id}")
    UserTodo findById(@PathParam("id") Integer id);
}
