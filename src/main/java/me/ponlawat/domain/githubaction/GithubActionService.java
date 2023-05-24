package me.ponlawat.domain.githubaction;

import me.ponlawat.domain.ai.dto.TriggerGithubActionRequest;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;

@RegisterRestClient(configKey = "github-action-api")
@RegisterClientHeaders(RequestAuthHeaderFactory.class)
public interface GithubActionService {
    @POST
    void triggerBuildAndDeploy(TriggerGithubActionRequest triggerGithubActionRequest);
}
