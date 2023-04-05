package me.ponlawat.domain.content;

import me.ponlawat.domain.content.dto.ContentDetailResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("contents")
@Produces(MediaType.APPLICATION_JSON)
public class ContentResource {
    @Inject
    ContentService contentService;

    @GET
    @Path("/{id}")
    public ContentDetailResponse getContentById(@PathParam("id") Long id) {
        return ContentDetailResponse.fromContent(contentService.getContentById(id));
    }
}
