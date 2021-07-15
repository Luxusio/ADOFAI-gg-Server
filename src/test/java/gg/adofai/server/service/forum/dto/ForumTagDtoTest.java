package gg.adofai.server.service.forum.dto;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForumTagDtoTest {

    @Test
    void testConvertNormalTag() throws Exception {
        // given
        String jsonStr_tag = "[null,{\"v\":\"#동시치기\"}]";
        JSONArray arr = toJsonArray(jsonStr_tag);

        // when
        ForumTagDto dto = ForumTagDto.createForumTagDto(arr);

        // then
        assertNotNull(dto);
        assertEquals("동시치기", dto.getName());
    }

    @Test
    void testConvertNullTag() throws Exception {
        // given
        String jsonStr_null = "[null, null]";
        JSONArray arr = toJsonArray(jsonStr_null);

        // when
        ForumTagDto dto = ForumTagDto.createForumTagDto(arr);

        // then
        assertNull(dto);

    }

    JSONArray toJsonArray(String str) throws ParseException {
        return (JSONArray) new JSONParser().parse(str);
    }

}