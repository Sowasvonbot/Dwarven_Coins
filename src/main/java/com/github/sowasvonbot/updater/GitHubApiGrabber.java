package com.github.sowasvonbot.updater;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GitHubApiGrabber {

    protected static JsonElement getNewestReleaseElementFromGithub() {
        String content = "nothing";

        try {
            URLConnection connection =
                    new URL("https://api.github.com/repos/sowasvonbot/Dwarven_Coins/releases")
                            .openConnection();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();

            while (reader.ready()) {
                builder.append(reader.readLine()).append("\n");
            }

            JsonElement jsonObject = new JsonParser().parse(builder.toString());
            if (jsonObject.isJsonArray())
                jsonObject = jsonObject.getAsJsonArray().get(0);
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new VersionNotFoundException();
    }

    private static String getReleaseNameFromJSONElement(JsonElement releaseElement) {
        JsonObject jsonObject = releaseElement.getAsJsonObject();

        //Removing the v at the start of the version name
        return jsonObject.get("name").getAsString().substring(1);
    }

    protected static String getVersionNameOnGithub() {
        return getReleaseNameFromJSONElement(getNewestReleaseElementFromGithub());
    }
}
