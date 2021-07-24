package gg.adofai.server.repository.level;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gg.adofai.server.domain.entity.level.QLevel;
import gg.adofai.server.dto.level.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static gg.adofai.server.domain.entity.level.QLevel.level;
import static gg.adofai.server.domain.entity.level.QLevelCreator.levelCreator;
import static gg.adofai.server.domain.entity.level.QSong.song;
import static gg.adofai.server.domain.entity.level.QSongArtist.songArtist;
import static gg.adofai.server.domain.entity.member.QPerson.person;
import static gg.adofai.server.domain.entity.tag.QTag.tag;
import static gg.adofai.server.domain.entity.tag.QTags.tags;

@Repository
@RequiredArgsConstructor
public class LevelQueryRepository {

    private final JPAQueryFactory queryFactory;

    public LevelSearchResultDto searchLevel(LevelSearchCondition condition) {
        QueryResults<LevelDto> result = queryFactory
                .select(new QLevelDto(
                        level.id, level.title, level.difficulty, song.minBpm, song.maxBpm,
                        level.tile, level.comments, level.likes))
                .from(level)
                .leftJoin(level.song, song)
                .where(level.isDeleted.eq(false),
                        levelQueryExpr(condition.getQuery()),
                        showSpecialLevel(condition.getShowNotVerified(), condition.getShowCensored()),
                        valueBetween(level.difficulty, condition.getMinDifficulty(), condition.getMaxDifficulty()),
                        valueBetween(song.minBpm, song.maxBpm, condition.getMinBpm(), condition.getMaxBpm()),
                        valueBetween(level.tile, condition.getMinTiles(), condition.getMaxTiles()),
                        hasTagsExpr(condition.getTags()))
                .offset(condition.getOffset())
                .limit(condition.getAmount())
                .fetchResults();

        long total = result.getTotal();
        List<LevelDto> levelDtoList = result.getResults();
        List<Long> idList = toIds(levelDtoList);

        Map<Long, List<LevelCreatorDto>> creatorMap = getCreatorMap(idList);
        levelDtoList.forEach(dto-> dto.setCreators(creatorMap.get(dto.getId())));

        Map<Long, List<LevelTagsDto>> tagsMap = getTagsMap(idList);
        levelDtoList.forEach(dto-> dto.setTags(tagsMap.get(dto.getId())));

        return new LevelSearchResultDto(levelDtoList, total);
    }

    public BooleanBuilder levelQueryExpr(String query) {
        if (!StringUtils.hasText(query)) return null;

        BooleanBuilder builder = new BooleanBuilder();
        QLevel sl = new QLevel("sl");

        builder.or(level.title.like("%" + query + "%"));
        builder.or(level.id.in(
                JPAExpressions.select(sl.id)
                        .from(levelCreator)
                        .join(levelCreator.level, sl)
                        .join(levelCreator.person, person)
                        .where(person.name.like("%" + query + "%"))));
        builder.or(level.id.in(
                JPAExpressions.select(sl.id)
                        .from(sl)
                        .join(sl.song, song)
                        .join(song.artists, songArtist)
                        .join(songArtist.person, person)
                        .where(person.name.like("%" + query + "%"))));
        return builder;
    }

    private BooleanBuilder showSpecialLevel(Boolean showNotVerified, Boolean showCensored) {
        BooleanBuilder builder = new BooleanBuilder();

        if (showNotVerified == null) showNotVerified = false;
        if (showCensored == null) showCensored = false;

        if (showNotVerified || showCensored) {
            if (showNotVerified) {
                builder.or(level.difficulty.gt(0)
                        .or(level.isCensored.isFalse()));
            }
            if (showCensored) {
                builder.or(level.difficulty.gt(0)
                        .or(level.isCensored.isTrue()));
            }
        }
        else {
            builder.and(level.difficulty.gt(0));
        }

        return builder;
    }

    private <T extends Number & Comparable<?>> BooleanBuilder valueBetween(NumberPath<T> value, T min, T max) {
        BooleanBuilder builder = new BooleanBuilder();
        if (min != null) builder.and(value.goe(min));
        if (max != null) builder.and(value.loe(max));
        return builder;
    }

    private <T extends Number & Comparable<?>> BooleanBuilder valueBetween(
            NumberPath<T> minValue, NumberPath<T> maxValue, T min, T max) {
        BooleanBuilder builder = new BooleanBuilder();
        if (min != null) builder.and(minValue.goe(min));
        if (max != null) builder.and(maxValue.loe(max));
        return builder;
    }

    private BooleanBuilder hasTagsExpr(List<String> tagsCond) {
        if (tagsCond == null) return null;
        BooleanBuilder builder = new BooleanBuilder();
        for (String tagCond : tagsCond) {
            builder.and(level.id.in(
                    JPAExpressions.select(tags.locationId)
                            .from(tags)
                            .join(tags.tag, tag)
                            .where(tag.location.eq("Level"),
                                    tag.name.eq(tagCond))
            ));
        }
        return builder;
    }

    private List<Long> toIds(List<LevelDto> result) {
        return result.stream()
                .map(LevelDto::getId)
                .collect(Collectors.toList());
    }

    private Map<Long, List<LevelCreatorDto>> getCreatorMap(List<Long> levelIdList) {
        QLevel sl = new QLevel("sl");
        return queryFactory
                .select(new QLevelCreatorDto(sl.id, person.name))
                .from(levelCreator)
                .join(levelCreator.level, sl)
                .join(levelCreator.person, person)
                .where(sl.id.in(levelIdList))
                .fetch().stream()
                .collect(Collectors.groupingBy(LevelCreatorDto::getLevelId));
    }


    private Map<Long, List<LevelTagsDto>> getTagsMap(List<Long> levelIdList) {
        QLevel sl = new QLevel("sl");
        return queryFactory
                .select(new QLevelTagsDto(sl.id, tag.name))
                .from(tags)
                .join(sl).on(tags.locationId.eq(sl.id))
                .join(tags.tag, tag)
                .where(tag.location.eq("Level"),
                        sl.id.in(levelIdList))
                .fetch().stream()
                .collect(Collectors.groupingBy(LevelTagsDto::getLevelId));
    }


}
