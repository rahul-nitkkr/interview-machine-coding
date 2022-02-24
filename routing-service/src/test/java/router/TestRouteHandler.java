package router;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import router.handler.RouteHandler;

public class TestRouteHandler {

    @Test
    public void testRouteHandler_Simple() throws RouteNotFoundException {
        RouteHandler handler = new RouteHandler();

        handler.addRoute("/foo", "bar");
        handler.addRoute("/foo/bar", "foobar");
        handler.addRoute("/foo/baz", "baz");

        Assertions.assertEquals("bar", handler.handleRoute("/foo"));
        Assertions.assertEquals("foobar", handler.handleRoute("/foo/bar"));
        Assertions.assertEquals("baz", handler.handleRoute("/foo/baz"));
    }

    @Test
    public void testRouteHandler_WildChar() throws RouteNotFoundException {
        RouteHandler handler = new RouteHandler();

        handler.addRoute("/foo", "bar");
        handler.addRoute("/foo/*", "foobar");
        handler.addRoute("/foo/baz", "baz");
        handler.addRoute("/foo/*/baz", "brr");

        Assertions.assertEquals("bar", handler.handleRoute("/foo"));
        Assertions.assertEquals("foobar", handler.handleRoute("/foo/bar"));
        Assertions.assertEquals("baz", handler.handleRoute("/foo/baz"));
        Assertions.assertEquals("brr", handler.handleRoute("/foo/par/baz"));
    }

    @Test
    public void testRouteHandler_PathParam() throws RouteNotFoundException {
        RouteHandler handler = new RouteHandler();

        handler.addRoute("/foo/{id}", "bar");


        Assertions.assertEquals("bar", handler.handleRoute("/foo/{abc}"));

    }
}
