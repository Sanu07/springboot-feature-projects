//package com.test.graphql.resolver;
//
//import com.netflix.graphql.dgs.DgsComponent;
//import com.netflix.graphql.dgs.DgsData;
//import com.netflix.graphql.dgs.InputArgument;
//import com.test.graphql.generated.DgsConstants;
//import com.test.graphql.generated.types.Show;
//import com.test.graphql.generated.types.ShowInput;
//import com.test.graphql.service.ShowsService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@DgsComponent
//public class ShowsDataFetcher {
//    private final ShowsService showsService;
//
//
//    public ShowsDataFetcher(ShowsService showsService) {
//        this.showsService = showsService;
//    }
//
//    /**
//     * This datafetcher resolves the shows field on Query.
//     * It uses an @InputArgument to get the titleFilter from the Query if one is defined.
//     */
//    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.Shows)
//    public List<Show> shows(@InputArgument("titleFilter") String titleFilter) {
//        if (titleFilter == null) {
//            return showsService.shows();
//        }
//
//        return showsService.shows().stream().filter(s -> s.getTitle().contains(titleFilter)).collect(Collectors.toList());
//    }
//
//    @DgsData(parentType = DgsConstants.SHOW.TYPE_NAME, field = DgsConstants.SHOW.ReleaseYear)
//    public Integer getReleaseYear() {
//        return 0;
//    }
//
//    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.CreateShow)
//    public Show createShow(@InputArgument("showInput") ShowInput showInput) {
//        return showsService.add(showInput);
//    }
//}