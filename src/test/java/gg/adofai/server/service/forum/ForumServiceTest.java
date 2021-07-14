package gg.adofai.server.service.forum;

import gg.adofai.server.service.forum.dto.ForumTagDto;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForumServiceTest {

    @Test
    void testGetData() throws IOException {
        // given
        ForumService forumService = new ForumService(
                null, null, null, null, null, WebClient.builder());

        // when
        List<ForumTagDto> forumTagDtoList = forumService.getData(ForumService.GID_ADMIN_TAG, ForumTagDto::createForumTagDto).block();

        // then
        assertNotNull(forumTagDtoList);
        assertNotEquals(0, forumTagDtoList.size());
    }



}