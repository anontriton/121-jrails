package jrails;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

public class JRouterTest {

    private JRouter jRouter;

    @Before
    public void setUp() throws Exception {
        jRouter = new JRouter();
    }

    @Test
    public void addRoute() {
        jRouter.addRoute("GET", "/", String.class, "index");
        assertThat(jRouter.getRoute("GET", "/"), is("java.lang.String#index"));
    }

    @Test
    public void testGetRouteNotFound() {
        assertThat(jRouter.getRoute("POST", "/nonexistent"), is((String) null));
    }

    @Test
    public void testRouteInvocation() {
        jRouter.addRoute("GET", "/test", TestController.class, "testMethod");
        Map<String, String> params = new HashMap<>();
        params.put("param", "value");
        Html result = jRouter.route("GET", "/test", params);
        assertThat(result.toString(), is("test method invoked with param: value"));
    }

    public static class TestController {
        public static Html testMethod(Map<String, String> params) {
            return new Html("test method invoked with param: " + params.get("param"));
        }
    }
}