package gg.adofai.server.api;

import gg.adofai.server.domain.vo.level.LevelSearchCondition;
import gg.adofai.server.dto.level.LevelSearchResultDto;
import gg.adofai.server.repository.level.LevelQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LevelApiController {

    private final LevelQueryRepository levelQueryRepository;

    @GetMapping("/api/v1/levels")
    public LevelSearchResultDto levelsV1(LevelSearchCondition condition) {

        if (condition.getOffset() < 0) throw new IllegalStateException("offset should be bigger than 0");
        if (condition.getAmount() < 0) throw new IllegalStateException("amount should be bigger than 0");
        if (condition.getAmount() > 50) throw new IllegalStateException("amount should be smaller than 50");

        return levelQueryRepository.searchLevel(condition);
    }

}