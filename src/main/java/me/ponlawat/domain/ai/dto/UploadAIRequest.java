package me.ponlawat.domain.ai.dto;


import io.quarkus.arc.All;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadAIRequest {
    @FormParam("file")
    private InputStream file;
}
