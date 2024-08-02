package jrails;

import java.util.Map;
import java.util.HashMap;

public class JRouter {

    // private map to store routes
    private final Map<String, String> routes = new HashMap<>();

    public void addRoute(String verb, String path, Class clazz, String method) {
        routes.put(verb + path, clazz.getName() + "#" + method);
    }

    // Returns "clazz#method" corresponding to verb+URN
    // Null if no such route
    public String getRoute(String verb, String path) {
        return routes.get(verb + path);
    }

    // Call the appropriate controller method and
    // return the result
    public Html route(String verb, String path, Map<String, String> params) {
        String route = getRoute(verb, path);

        if (route == null) {
            throw new UnsupportedOperationException("route not found: " + verb + path);
        }

        String[] parts = route.split("#");
        String className = parts[0];
        String methodName = parts[1];

        // load class and method, invoke method, return result
        try {
            Class<?> clazz = Class.forName(className);
            java.lang.reflect.Method method = clazz.getMethod(methodName, Map.class);
            return (Html) method.invoke(null, params);
        } catch (Exception e) {
            throw new RuntimeException("error invoking controller method", e);
        }
    }
}
