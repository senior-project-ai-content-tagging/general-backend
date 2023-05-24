package me.ponlawat.infrastructure.provider.http;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Provider
@Priority(0)
public class CorsFilter implements ContainerResponseFilter {
    private static final List<String> ALLOWED_ORIGINS = new ArrayList<>();
    static {
        ALLOWED_ORIGINS.add("http://karnkarn.local:3000");
        ALLOWED_ORIGINS.add("http://localhost:3000");
    }

    private static final String ALLOWED_METHODS = "GET, POST, PUT, DELETE, OPTIONS, HEAD";
    private static final String ALLOWED_HEADERS = "Origin, X-Requested-With, Content-Type, Accept, Authorization";

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();

        String origin = requestContext.getHeaderString("Origin");
        if (origin != null && isOriginAllowed(origin)) {
            headers.add("Access-Control-Allow-Origin", origin);
            headers.add("Access-Control-Allow-Credentials", "true");
        }

        headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
        headers.add("Access-Control-Allow-Methods", ALLOWED_METHODS);
        headers.add("Access-Control-Max-Age", "100000");
    }

    private boolean isOriginAllowed(String origin) {
        for (String allowedOrigin : ALLOWED_ORIGINS) {
            if (allowedOrigin.equalsIgnoreCase(origin)) {
                return true;
            }
        }
        return false;
    }
}
