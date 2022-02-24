package router;

import router.handler.RouteHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRoutingService implements RoutingService {
    private static final String MATCH_ALL = "*";

    private final Map<String, String> routeMap;
    private final RouteHandler routeHandler;

    public InMemoryRoutingService(RouteHandler handler) {
        this.routeHandler = handler;
        this.routeMap = new HashMap<>();
    }

    @Override
    public void register(String apiPath, String invocationFunction) throws RegistrationFailedException {
        routeMap.put(apiPath, invocationFunction);
    }

    @Override
    public String routeToFunction(String apiPath) throws RouteNotFoundException {
        return Optional.ofNullable(routeMap.get(apiPath))
                .orElseThrow(() -> new RouteNotFoundException(String.format("Unable to Find Route for : %s", apiPath)));
    }


}
