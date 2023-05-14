package me.ponlawat.domain.ticket.publisher;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import me.ponlawat.domain.ticket.dto.TicketSubmitContentPubSub;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class SubmitContentPublisher {
    private static final Logger LOG = Logger.getLogger(SubmitContentPublisher.class);
    private static final String TOPIC_NAME_STR = "general-backend.submit-content";
    private TopicName topicName;

    @ConfigProperty(name = "quarkus.google.cloud.project-id")
    String projectId;

    @Inject
    CredentialsProvider credentialsProvider;

    @PostConstruct
    void init() {
        topicName = TopicName.of(projectId, TOPIC_NAME_STR);
    }

    public void publish(TicketSubmitContentPubSub message) throws IOException, InterruptedException {
        Publisher publisher = Publisher.newBuilder(topicName)
                .setCredentialsProvider(credentialsProvider)
                .build();

        try {
            ByteString data = ByteString.copyFromUtf8(message.serializeJson());
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
            ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
            ApiFutures.addCallback(messageIdFuture, new ApiFutureCallback<String>() {
                @Override
                public void onFailure(Throwable t) {
                    LOG.warnv("failed to publish: {0}", t);
                }

                @Override
                public void onSuccess(String messageId) {
                    LOG.infov("published with message id {0}", messageId);
                }
            });
        } finally {
            publisher.shutdown();
            publisher.awaitTermination(1, TimeUnit.MINUTES);
        }
    }
}
