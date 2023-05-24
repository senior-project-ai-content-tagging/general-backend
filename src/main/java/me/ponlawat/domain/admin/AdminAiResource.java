package me.ponlawat.domain.admin;

import me.ponlawat.domain.ai.AIService;
import me.ponlawat.domain.ai.dto.UploadAIRequest;
import me.ponlawat.infrastructure.auth.AdminRequired;
import me.ponlawat.infrastructure.auth.JwtRequired;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/admin/ai")
@JwtRequired
@AdminRequired
public class AdminAiResource {
    @Inject
    AIService aiService;

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadModel(@MultipartForm UploadAIRequest uploadAIRequest) {
        aiService.uploadModal(uploadAIRequest.getFile());
        return Response.ok().build();
    }
}
