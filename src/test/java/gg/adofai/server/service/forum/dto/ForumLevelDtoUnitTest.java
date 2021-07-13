package gg.adofai.server.service.forum.dto;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ForumLevelDtoUnitTest {

    @Test
    void testNormalConvert() throws Exception {
        // given
        String jsonStr_ns = "[{\"v\":1,\"f\":\"1\"},{\"v\":\"[ns]\"},{\"v\":\"かめりあ(Camellia)\"},{\"v\":20,\"f\":\"20\"},{\"v\":\"Ruren\"},{\"v\":\"Link\"},{\"v\":\"X\"},{\"v\":\"Youtube\"},{\"v\":\"X\"},null,{\"v\":2542,\"f\":\"2542\"},{\"v\":\"#동시치기\"},{\"v\":\"#셋잇단\"},{\"v\":\"#개박\"},{\"v\":\"#4분이상\"},null,{\"v\":20,\"f\":\"20\"},null,null,null,null,{\"v\":null}]";
        JSONArray arr = toJsonArray(jsonStr_ns);

        // when
        ForumLevelDto dto = ForumLevelDto.createForumLevelDto(arr);

        // then
        assertNotNull(dto);
        assertEquals(1, dto.getId());
        assertEquals("[ns]", dto.getSong());
        assertIterableEquals(List.of("かめりあ(Camellia)"), dto.getArtists());
        assertEquals(20.0, dto.getLevel());
        assertIterableEquals(List.of("Ruren"), dto.getCreators());
        assertNull(dto.getDownload());
        assertNull(dto.getWorkshop());
        assertNull(dto.getVideo());
        assertEquals(false, dto.getEpilepsyWarning());
        assertNull(dto.getMinBpm());
        assertNull(dto.getMaxBpm());
        assertEquals(2542, dto.getTiles());
        assertIterableEquals(List.of("동시치기", "셋잇단", "개박", "4분이상"), dto.getTags());
    }

    @Test
    void convertToNullTest() throws Exception {
        // given
        String jsonStr_no28Empty = "[null,null,null,{\"v\":-2,\"f\":\"-2\"},null,null,{\"v\":\"X\"},null,null,null,null,null,null,null,null,null,{\"v\":-2,\"f\":\"-2\"},null,null,null,null,{\"v\":null}]";
        JSONArray arr = toJsonArray(jsonStr_no28Empty);

        // when
        ForumLevelDto dto = ForumLevelDto.createForumLevelDto(arr);

        // then
        assertNull(dto);
    }

    JSONArray toJsonArray(String str) throws ParseException {
        return (JSONArray) new JSONParser().parse(str);
    }

}