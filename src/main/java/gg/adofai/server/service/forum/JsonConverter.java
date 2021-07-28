package gg.adofai.server.service.forum;

import org.json.simple.JSONObject;

public class JsonConverter {

    @SuppressWarnings("unchecked")
    public static<T> T safe(Object obj) {
        JSONObject jsonObject = (JSONObject) obj;
        if (jsonObject == null) return null;
        return (T) jsonObject.get("v");
    }

}
