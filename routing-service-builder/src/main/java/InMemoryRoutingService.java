import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InMemoryRoutingService implements RoutingService {
    private static final String WILDCHAR_PATTERN = "\\*";
    private static final String PARAM_PATTERN = ":";

    private final Map<String, String> staticRoutes;
    private final Map<Pattern, String> patternRoutes;

    private InMemoryRoutingService(Map<String, String> staticRoutes, Map<Pattern, String> patternRoutes) {
        this.staticRoutes = staticRoutes;
        this.patternRoutes = patternRoutes;
    }

    @Override
    public String route(String api) throws RouteNotFoundException {
        Optional<String> staticEntry = Optional.ofNullable(staticRoutes.get(api));
        if (staticEntry.isPresent()) {
            return staticEntry.get();
        } else {
            Pattern matchedPattern = patternRoutes.keySet()
                    .stream()
                    .filter(pattern -> pattern.matcher(api).matches())
                    .findFirst()
                    .orElseThrow(() -> new RouteNotFoundException(api));

            return extractPathParams(api);
        }
    }

    private String extractPathParams(String api) {
        List<String> params = new ArrayList<>();
        String invocation = null;
        for (Pattern pattern : patternRoutes.keySet()) {
            Matcher matcher = pattern.matcher(api);
            if (matcher.matches()) {
                params.addAll(IntStream.range(1, matcher.groupCount() + 1)
                        .mapToObj(m -> matcher.group(m))
                        .collect(Collectors.toList()));
                invocation = patternRoutes.get(pattern);
                break;
            }
        }
        return params.toString() + invocation;
    }

    static class RouteBuilder {
        private final Map<String, String> staticRoutes;
        private final Map<Pattern, String> patternRoutes;

        public RouteBuilder() {
            this.staticRoutes = new HashMap<>();
            this.patternRoutes = new HashMap<>();
        }

        public static RouteBuilder builder() {
            return new RouteBuilder();
        }

        public RouteBuilder withRoute(String route, String invocation) {
            if (!route.contains("*") && !route.contains(PARAM_PATTERN))
                staticRoutes.put(route, invocation);
            else if (route.contains("*")) {
                String sanitizedRoute = route.replaceAll(WILDCHAR_PATTERN, ".+"); //"**"
                patternRoutes.put(Pattern.compile(sanitizedRoute), invocation);
            } else if (route.contains(":")) {
                String pattern = Arrays.stream(route.split(":[^/]*", -1))
                        .map(Pattern::quote)
                        .collect(Collectors.joining("([^/]+)"));
                patternRoutes.put(Pattern.compile(pattern), invocation);
            }
            return this;
        }

        public InMemoryRoutingService build() {
            return new InMemoryRoutingService(staticRoutes, patternRoutes);
        }
    }
}
