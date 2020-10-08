package helper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;

public class Translator {
    private static final String subscriptionKey = "122fd3a8c18a47f3ac77153b522c779a";
    private static final String endpoint = "https://api.cognitive.microsofttranslator.com";
    private static final String url = endpoint + "/translate?api-version=3.0&to=vi";
    // Instantiates the OkHttpClient.
    private static final OkHttpClient client = new OkHttpClient();

    // This function performs a POST request.
    public static String translate(String word) {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "[{\n\t\"Text\": \"" + word + "\"\n}]");
        Request request = new Request.Builder()
                .url(url).post(body)
                .addHeader("Ocp-Apim-Subscription-Key", subscriptionKey)
                .addHeader("Ocp-Apim-Subscription-Region", "eastasia")
                .addHeader("Content-type", "application/json").build();
        try {
            Response response = client.newCall(request).execute();
            return prettify(response.body().string());
        } catch (IOException e) {
            return "";
        }
    }

    // This function prettifies the json response.
    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(json_text);
        if (!jsonElement.isJsonArray()) return "";
        JsonObject json = jsonElement.getAsJsonArray().get(0).getAsJsonObject();
        JsonObject translateJson = json.get("translations").getAsJsonArray().get(0).getAsJsonObject();
        return translateJson.get("text").toString().replaceAll("\"", "");
    }
}
