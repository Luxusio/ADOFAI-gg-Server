package gg.adofai.server.service.forum;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoogleSheetConverterTest {

    @Test
    void testToSafeJsonString() {
        // given
        String unsafeJsonStr = "// OwO\nsome.method.call({data:\"data\"});";
        String safeJsonStr = "{data:\"data\"}";
        String wrongBracketJsonStr1 = "data:\"data\"";
        String wrongBracketJsonStr2 = "{data:\"data\"";
        String wrongBracketJsonStr3 = "data:\"data\"}";

        // when
        String result1 = GoogleSheetConverter.toSafeJsonString(unsafeJsonStr);
        String result2 = GoogleSheetConverter.toSafeJsonString(safeJsonStr);
        String result3 = GoogleSheetConverter.toSafeJsonString(wrongBracketJsonStr1);
        String result4 = GoogleSheetConverter.toSafeJsonString(wrongBracketJsonStr2);
        String result5 = GoogleSheetConverter.toSafeJsonString(wrongBracketJsonStr3);

        // then
        assertEquals("{data:\"data\"}", result1);
        assertEquals("{data:\"data\"}", result2);
        assertEquals("data:\"data\"", result3);
        assertEquals("{data:\"data\"", result4);
        assertEquals("data:\"data\"}", result5);
    }
    
    @Test
    void toRowDataList() throws Exception {
        // given
        String jsonStr = "{\"version\":\"0.6\",\"reqId\":\"0\",\"status\":\"ok\",\"sig\":\"472408843\",\"table\":{\"cols\":[{\"id\":\"A\",\"label\":\"\",\"type\":\"string\"},{\"id\":\"B\",\"label\":\"\",\"type\":\"string\"}],\"rows\":[{\"c\":[null,{\"v\":\"#개인차\"}]},{\"c\":[null,{\"v\":\"#동시치기\"}]},{\"c\":[null,{\"v\":\"#2+동타\"}]},{\"c\":[null,{\"v\":\"#셋잇단\"}]},{\"c\":[null,{\"v\":\"#다섯잇단\"}]},{\"c\":[null,{\"v\":\"#일곱잇단\"}]},{\"c\":[null,{\"v\":\"#폴리리듬\"}]},{\"c\":[null,{\"v\":\"#스윙\"}]},{\"c\":[null,{\"v\":\"#트레실로\"}]},{\"c\":[null,{\"v\":\"#개박\"}]},{\"c\":[null,{\"v\":\"#64+비트\"}]},{\"c\":[null,{\"v\":\"#가감속\"}]},{\"c\":[null,{\"v\":\"#질주\"}]},{\"c\":[null,{\"v\":\"#마법진\"}]},{\"c\":[null,{\"v\":\"#암기\"}]},{\"c\":[null,{\"v\":\"#1분이하\"}]},{\"c\":[null,{\"v\":\"#4분이상\"}]},{\"c\":[null,{\"v\":\"#흰토끼\"}]},{\"c\":[null,{\"v\":\"#배속변경X\"}]},{\"c\":[null,{\"v\":\"#소용돌이X\"}]}],\"parsedNumHeaders\":0}}";

        // when
        List<JSONArray> jsonArrays = GoogleSheetConverter.toRowDataList(jsonStr);

        // then
        assertNotNull(jsonArrays);
        assertEquals(20, jsonArrays.size());
    }

}
