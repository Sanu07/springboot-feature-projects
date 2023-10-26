package com.test.graphql.subscription;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsSubscription;
import com.test.graphql.generated.DgsConstants;
import com.test.graphql.generated.types.User;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.time.Duration;

@DgsComponent
public class SubscriptionResolver {

    @DgsSubscription(field = DgsConstants.SUBSCRIPTION.UserSubscription)
    public Publisher<User> users() {
        return Flux.interval(Duration.ofSeconds(2)).map(t -> User.newBuilder().id(t.intValue()).name("name-" + t).build());
    }
}
