package gg.adofai.server.service.forum.dto;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ForumLevelDtoTest {

    private String jsonStr_ns = "[{\"v\":1,\"f\":\"1\"},{\"v\":\"[ns]\"},{\"v\":\"かめりあ(Camellia)\"},{\"v\":20,\"f\":\"20\"},{\"v\":\"Ruren\"},{\"v\":\"Link\"},{\"v\":\"X\"},{\"v\":\"Youtube\"},{\"v\":\"X\"},null,{\"v\":2542,\"f\":\"2542\"},{\"v\":\"#동시치기\"},{\"v\":\"#셋잇단\"},{\"v\":\"#개박\"},{\"v\":\"#4분이상\"},null,{\"v\":20,\"f\":\"20\"},null,null,null,null,{\"v\":null}]";
    private String jsonStr_no28Empty = "[null,null,null,{\"v\":-2,\"f\":\"-2\"},null,null,{\"v\":\"X\"},null,null,null,null,null,null,null,null,null,{\"v\":-2,\"f\":\"-2\"},null,null,null,null,{\"v\":null}]";

    @Test
    void testNormalConvert() throws Exception {
        // given
        JSONArray arr = toJsonArray(jsonStr_ns);

        // when
        ForumLevelDto dto = ForumLevelDto.createForumLevelDto(arr);

        // then
        assertNotNull(dto);
        assertEquals(dto.getId(), 1);



    }

    @Test
    void convertToNullTest() throws Exception {
        // given
        

        // when

        // then

    }

    JSONArray toJsonArray(String str) throws ParseException {
        return (JSONArray) new JSONParser().parse(str);
    }

}