package lingmod.util;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import lingmod.cards.VerseStrings;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static lingmod.ModCore.getStringPathI18N;

public class VerseLoader {
    public static Map<String, VerseStrings> strings = new HashMap<>();
    public static String DEFAULT_PATH = getStringPathI18N() + "/VerseStrings.json";

    public static void init() {
        Gson gson = new Gson();
        String json = loadJson(DEFAULT_PATH);
        Map<String, Object> map = new HashMap<>();
        map = gson.fromJson(json, map.getClass());
        map.forEach((k, v) -> {
            strings.put(k, new VerseStrings((LinkedTreeMap<String, Object>) v));
        });
    }

    public static VerseStrings getStr(String id) {
        if (strings.isEmpty()) {
            init();
        }
        return strings.get(id);
    }

    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }
}
