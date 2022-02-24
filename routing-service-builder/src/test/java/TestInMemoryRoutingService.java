import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestInMemoryRoutingService {

    @Test
    public void testInMemRouting_basic() throws RouteNotFoundException {
        RoutingService routingService = InMemoryRoutingService.RouteBuilder.builder()
                .withRoute("/foo", "foo")
                .withRoute("/bar", "bar")
                .withRoute("/foo/bar", "foobar")
                .build();

        Assertions.assertEquals("foo", routingService.route("/foo"));
        Assertions.assertEquals("bar", routingService.route("/bar"));
        Assertions.assertEquals("foobar", routingService.route("/foo/bar"));
    }

    @Test
    public void testInMemRouting_throwsForNonExistentPath() throws RouteNotFoundException {
        RoutingService routingService = InMemoryRoutingService.RouteBuilder.builder()
                .withRoute("/foo", "foo")
                .withRoute("/bar", "bar")
                .withRoute("/foo/bar", "foobar")
                .build();

        Assertions.assertThrows(RouteNotFoundException.class, () -> routingService.route("/bar/foo"));
    }

    @Test
    public void testInMemRouting_PatternBasic() throws RouteNotFoundException {
        RoutingService routingService = InMemoryRoutingService.RouteBuilder.builder()
                .withRoute("/foo", "foo")
                .withRoute("/bar", "bar")
                .withRoute("/foo/bar", "foobar")
                .withRoute("/foo/*/bar", "fooer")
                .withRoute("/foo/*/bar/*", "fooer-2")
                .withRoute("/bar/*/foo/*", "anotherfoo")
                .build();

        Assertions.assertEquals("fooer", routingService.route("/foo/food/bar"));
        Assertions.assertEquals("fooer-2", routingService.route("/foo/food/bar/food"));
    }

    @Test
    public void testInMemRouting_PathParam() throws RouteNotFoundException {
        RoutingService routingService = InMemoryRoutingService.RouteBuilder.builder()
                .withRoute("/foo", "foo")
                .withRoute("/bar", "bar")
                .withRoute("/foo/bar", "foobar")
//                .withRoute("/foo/*/bar" , "fooer")
//                .withRoute("/foo/*/bar/*" , "fooer-2")
                .withRoute("/foo/:bar/baz/:temp", "foorbar-2")
                .build();

//        Assertions.assertEquals("fooer" , routingService.route("/foo/food/bar"));
        Assertions.assertEquals("foorbar-2", routingService.route("/foo/food/baz/food"));
    }
}
