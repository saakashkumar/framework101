package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonReader {
    private static JSONObject locators;

    static {
        try {
            InputStream inputStream = JsonReader.class.getClassLoader()
                    .getResourceAsStream("locators.json");
            locators = (JSONObject) new JSONParser().parse(new InputStreamReader(inputStream));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load locators from JSON file", e);
        }
    }

    public static String getLocator(String page, String element, String type) {
        JSONObject pageObject = (JSONObject) locators.get(page);
        JSONObject elementObject = (JSONObject) pageObject.get(element);
        return (String) elementObject.get(type);
    }
}