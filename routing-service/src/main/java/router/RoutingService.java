package router;

public interface RoutingService {

    void register(String apiPath, String invocationFunction) throws RegistrationFailedException;

    /**
     * In case multipole requests match, returns the FIRST_AVAILABLE_ONE
     * @param apiPath
     * @return
     * @throws IllegalArgumentException
     */
    String routeToFunction(String apiPath) throws IllegalArgumentException, RouteNotFoundException;

}
