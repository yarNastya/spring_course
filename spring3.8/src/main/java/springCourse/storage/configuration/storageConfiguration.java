package springCourse.storage.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import springCourse.storage.domain.Good;

@Configuration
public class storageConfiguration {

    @Bean
    public QueueChannel goodChannel() {
        return MessageChannels.queue(50).getObject();
    }

    @Bean
    public PublishSubscribeChannel packageChannel() {
        return MessageChannels.publishSubscribe().getObject();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(10).maxMessagesPerPoll(2).getObject();
    }

    @Bean
    public IntegrationFlow storageFlow() {
        return IntegrationFlow.from("goodChannel")
                .split()
                .<Good, Boolean>route(Good::isPackInBox, m -> m
                        .subFlowMapping(true, sf -> sf
                                .<Good>handle("packService", "packInBox"))
                        .subFlowMapping(false, sf -> sf
                                .<Good>handle("packService", "packInMembrane")))
                .aggregate()
                .handle("packService", "collect")
                .channel("packageChannel")
                .get();
    }
}
