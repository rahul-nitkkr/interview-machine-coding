public class RouteNotFoundException extends Exception {

    public RouteNotFoundException(String missingRoute) {
        super(String.format("Route : %s not found in registered routes", missingRoute));
    }
}
