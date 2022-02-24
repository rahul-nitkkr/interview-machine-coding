package router.handler;

import router.RouteNotFoundException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RouteHandler {
    private static final String WILDCARD_CHAR = Pattern.quote("*");
    private static final String API_PARAM_PATTERN = Pattern.quote("(\\{[a-zA-Z0-9]+\\})");

    private final Map<String, String> routes;
    private final Map<Pattern, String> patternRoutes;

    public RouteHandler() {
        routes = new HashMap<>();
        patternRoutes = new HashMap<>();
    }

    public String handleRoute(String route) throws RouteNotFoundException {
        if (routes.get(route) != null) {
            return handleStaticRoute(route);
        }
        return handlePathParamRoute(route);
    }

    public void addRoute(String route, String routingFunction) {
        if (!route.contains(WILDCARD_CHAR) && !route.contains("{")) {
            routes.put(route, routingFunction);
        } else if (route.contains(WILDCARD_CHAR)) {
            String regex = Arrays.stream(route.split(WILDCARD_CHAR, -1))
                    .map(Pattern::quote)
                    .collect(Collectors.joining("[^/].+[^/]"));
            patternRoutes.put(Pattern.compile(regex), routingFunction);
        } else if (route.contains("{")) {
            String regex = Arrays.stream(route.split(API_PARAM_PATTERN, -1))
                    .map(Pattern::quote)
                    .collect(Collectors.joining("[^/].+[^/]"));
            patternRoutes.put(Pattern.compile(regex), routingFunction);
        }
    }

    private String handleStaticRoute(String route) throws RouteNotFoundException {
        return Optional.ofNullable(routes.get(route)).orElseThrow(() -> new RouteNotFoundException(""));
    }

    private String handlePatternRoute(final String route) throws RouteNotFoundException {
        Pattern routePattern = patternRoutes.keySet()
                .stream()
                .filter(pattern -> pattern.matcher(route).matches())
                .findFirst()
                .orElseThrow(() -> new RouteNotFoundException(""));

        return patternRoutes.get(routePattern);
    }

    private String handlePathParamRoute(final String route) throws RouteNotFoundException {
        List<String> params = new ArrayList<>();
        String fn = "";
        for (Pattern pattern : patternRoutes.keySet()) {
            if (pattern.matcher(route).matches()) {
                Matcher matcher = pattern.matcher(route);
                params.addAll(IntStream.range(1, matcher.groupCount() + 1)
                        .mapToObj(matcher::group)
                        .collect(Collectors.toList()));
                fn = patternRoutes.get(pattern);
                break;
            }
        }
        return String.format("fn = %s , params = %s", fn, params.toString());
    }

}
