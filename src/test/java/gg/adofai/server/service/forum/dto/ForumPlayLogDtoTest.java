package gg.adofai.server.service.forum.dto;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForumPlayLogDtoTest {

    @Test
    void testNormalConvert() throws Exception{
        // given
        String jsonStr_normalPlay = "[{\"v\":1,\"f\":\"1\"},{\"v\":\"Date(2021,2,21,22,47,8)\",\"f\":\"2021. 3. 21 오후 10:47:08\"},{\"v\":\"유수찬\"},{\"v\":1,\"f\":\"1\"},{\"v\":330,\"f\":\"330\"},{\"v\":\"Blackmagik Blazing\"},{\"v\":\"かめりあ(Camellia)\"},{\"v\":\"헤르니\"},null,{\"v\":3158,\"f\":\"3158\"},{\"v\":\"Video\"},{\"v\":128.66,\"f\":\"128.66\"},{\"v\":0.9778081775345797,\"f\":\"97.78%\"},{\"v\":1,\"f\":\"100%\"},{\"v\":4179.794687936825,\"f\":\"4179.79\"},{\"v\":6,\"f\":\"6\"},{\"v\":3,\"f\":\"3\"},{\"v\":46,\"f\":\"46\"},{\"v\":100330,\"f\":\"100330\"},{\"v\":0,\"f\":\"0\"},{\"v\":1,\"f\":\"1\"},{\"v\":3234.245452189275,\"f\":\"3234.25\"},null,{\"v\":\"https://youtu.be/oYGiRIxLUvk\"}]";
        JSONArray arr = toJsonArray(jsonStr_normalPlay);

        // when
        ForumPlayLogDto dto = ForumPlayLogDto.createForumPlayLogDto(arr);

        // then
        assertNotNull(dto);
        assertEquals(1, dto.getId());
        assertEquals(LocalDateTime.of(2021, 3, 21, 22, 47, 8), dto.getTimeStamp());
        assertEquals("유수찬", dto.getName());
        assertEquals(1, dto.getUserCode());
        assertEquals(330, dto.getMapId());
        assertEquals("Blackmagik Blazing", dto.getSong());
        assertIterableEquals(List.of("かめりあ(Camellia)"), dto.getArtists());
        assertIterableEquals(List.of("헤르니"), dto.getCreators());
        assertNull(dto.getLevel());
        assertEquals(3158, dto.getTiles());
        assertEquals(128.66, dto.getRa());
        assertEquals(97.78081775345797, dto.getAccuracy());
        assertEquals(100, dto.getSpeed());
        assertEquals(4179.794687936825, dto.getPp());
        assertEquals(6, dto.getLocalRank());
        assertEquals(46, dto.getTotalRank());
        assertEquals(100330, dto.getRecordCode());
        assertEquals(false, dto.getIsOverlapped());
        assertEquals(1, dto.getIsNew());
        assertEquals(3234.245452189275, dto.getWeighted());
        assertEquals("https://youtu.be/oYGiRIxLUvk", dto.getUrl());
    }

    @Test
    void testNullConvert() throws Exception {
        // given
        String jsonStr_null = "[null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null]";
        JSONArray arr = toJsonArray(jsonStr_null);

        // when
        ForumPlayLogDto dto = ForumPlayLogDto.createForumPlayLogDto(arr);

        // then
        assertNull(dto);
    }

    JSONArray toJsonArray(String str) throws ParseException {
        return (JSONArray) new JSONParser().parse(str);
    }

}
