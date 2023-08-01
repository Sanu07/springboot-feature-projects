package com.test.graphql.resolver;

import com.netflix.graphql.dgs.*;
import com.test.graphql.dataloader.EmployeesDataLoaderWithContext;
import com.test.graphql.dataloader.EstablishedYearBatchedDataLoader;
import com.test.graphql.generated.DgsConstants;
import com.test.graphql.generated.types.Employee;
import com.test.graphql.generated.types.EmployeeInput;
import com.test.graphql.generated.types.Organisation;
import com.test.graphql.model.MyCustom;
import com.test.graphql.model.Stock;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.RequestHeader;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@DgsComponent
public class OrganisationResolver {


    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Organisation)
    public Organisation getOrganisation() {
        return Organisation.newBuilder().id(1).name("testOrg").myScalar(MyCustom.builder().value(1).name("hey!!").build()).build();
    }

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Organisation2)
    public List<Organisation> getOrganisation2() {
        return Arrays.asList(
                Organisation.newBuilder().id(1).name("testOrg").myScalar(MyCustom.builder().value(1).name("hey!!").build()).build(),
                Organisation.newBuilder().id(2).name("testOrg2").myScalar(MyCustom.builder().value(1).name("hey!!").build()).build(),
                Organisation.newBuilder().id(3).name("testOrg3").myScalar(MyCustom.builder().value(1).name("hey!!").build()).build(),
                Organisation.newBuilder().id(4).name("testOrg4").myScalar(MyCustom.builder().value(1).name("hey!!").build()).build(),
                Organisation.newBuilder().id(5).name("testOrg5").myScalar(MyCustom.builder().value(1).name("hey!!").build()).build(),
                Organisation.newBuilder().id(6).name("testOrg6").myScalar(MyCustom.builder().value(1).name("hey!!").build()).build(),
                Organisation.newBuilder().id(7).name("testOrg7").myScalar(MyCustom.builder().value(1).name("hey!!").build()).build());
    }

    /**
     *
     * the below method uses Mapped Data Loader
     *
     * @param dfe
     * @return
     */
//    @DgsData(parentType = DgsConstants.ORGANISATION.TYPE_NAME, field = DgsConstants.ORGANISATION.EstablishedYear)
//    public CompletableFuture<OffsetDateTime> getEmployees(DgsDataFetchingEnvironment dfe) {
//        final DataLoader<Integer, OffsetDateTime> dataLoader = dfe.getDataLoader("establishedYear");
//
//        //Because the reviews field is on Show, the getSource() method will return the Show instance.
//        Organisation organisation = dfe.getSource();
//        log.info(organisation.toString());
//
//        //Load the reviews from the DataLoader. This call is async and will be batched by the DataLoader mechanism.
//        return dataLoader.load(organisation.getId());
//    }

    /**
     *
     * the below method uses Batched data loader
     *
     * @param dfe
     * @return
     */
    @DgsData(parentType = DgsConstants.ORGANISATION.TYPE_NAME, field = DgsConstants.ORGANISATION.EstablishedYear)
    public CompletableFuture<OffsetDateTime> getEmployees(DgsDataFetchingEnvironment dfe) {
        final DataLoader<Integer, OffsetDateTime> dataLoader = dfe.getDataLoader(EstablishedYearBatchedDataLoader.class);

        //Because the reviews field is on Show, the getSource() method will return the Show instance.
        Organisation organisation = dfe.getSource();
        log.info(organisation.toString());

        //Load the reviews from the DataLoader. This call is async and will be batched by the DataLoader mechanism.
        return dataLoader.load(organisation.getId());
    }

    @DgsData(parentType = DgsConstants.ORGANISATION.TYPE_NAME, field = DgsConstants.ORGANISATION.Employees)
    public List<Employee> getEmployees2(DgsDataFetchingEnvironment dfe) {
        return getEmployeesFromDb();
    }


    private List<Employee> getEmployeesFromDb() {
        return Arrays.asList(Employee.newBuilder().id(1).name("emp1").build(),
                Employee.newBuilder().id(1).name("emp2").build());
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.SaveEmployee)
    public Employee saveEmployee(@InputArgument("employee")EmployeeInput input, @RequestHeader("my-header") String header) {
        log.info("My-header :: " + header);
        return Employee.newBuilder()
                .id(11)
                .age(input.getAge())
                .name(input.getName())
                .joiningDate(OffsetDateTime.now().toString())
                .build();
    }

    @DgsSubscription
    public Publisher<Stock> stocks() {
        return Flux.interval(Duration.ofSeconds(0), Duration.ofSeconds(1)).map(t -> new Stock("NFLX", 500 + t));
    }
}
