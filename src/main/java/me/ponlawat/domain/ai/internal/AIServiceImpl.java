package me.ponlawat.domain.ai.internal;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import me.ponlawat.domain.ai.AIService;
import me.ponlawat.domain.ai.dto.TriggerGithubActionRequest;
import me.ponlawat.domain.githubaction.GithubActionService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.InputStream;

@ApplicationScoped
public class AIServiceImpl implements AIService {

    @ConfigProperty(name = "google.cloud.storage.ai-modal")
    String aiBucket;
    @Inject
    Storage storage;
    @Inject
    @RestClient
    GithubActionService githubActionService;

    private static final String AI_TRIGGER_BUILD_KEY = "trigger-build";
    private static final String AI_MODAL_FILE_NAME = "ai-modal.joblib";

    @Override
    public void uploadModal(InputStream file) {
        BlobId blobId = BlobId.of(aiBucket, AI_MODAL_FILE_NAME);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        storage.create(blobInfo, file);

        githubActionService.triggerBuildAndDeploy(new TriggerGithubActionRequest(AI_TRIGGER_BUILD_KEY));
    }
}
