package gg.adofai.server.controller.scheduler;

import gg.adofai.server.service.forum.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ForumSyncScheduler {

    private final ForumService forumService;

    /**
     * 10s later from start & every 10m scheduler
     */
    @Scheduled(fixedDelay = 10 * 60 * 1000, initialDelay = 10 * 1000)
    public void forumSyncJob() {
        forumService.updateAllData();
    }

}
