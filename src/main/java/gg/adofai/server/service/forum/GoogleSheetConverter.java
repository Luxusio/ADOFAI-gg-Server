package gg.adofai.server.service.forum;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class GoogleSheetConverter {

    public static String toSafeJsonString(String str) {
        if (str == null) return null;
        int from = str.indexOf('{');
        int to = str.lastIndexOf('}');
        if (from != -1 && to > from) {
            return str.substring(from, to + 1);
        }
        return str;
    }

    public static List<JSONArray> toRowDataList(String jsonStr) throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonStr);

        JSONObject table = (JSONObject) jsonObject.get("table");
        JSONArray rows = (JSONArray) table.get("rows");

        List<JSONArray> result = new ArrayList<>(rows.size());
        for (Object o : rows) {
            result.add((JSONArray) ((JSONObject) o).get("c"));
        }
        return result;
    }

}
