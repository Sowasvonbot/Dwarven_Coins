package com.github.sowasvonbot.updater;

import com.github.sowasvonbot.Main;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GitHubApiGrabber {

    private static GitHubApiGrabber instance;
    private JsonArray gitReleases;
    private final String repoUrl =
            "https://api.github.com/repos/sowasvonbot/Dwarven_Coins/releases";
    private final List<VersionName> versionNameList = new ArrayList<>();

    private VersionName firstRelease;


    public static GitHubApiGrabber getInstance() {
        return Objects.isNull(instance) ? instance = new GitHubApiGrabber() : instance;
    }

    protected JsonArray getGitReleases() {
        return gitReleases;
    }

    protected void getReleasesFromGithub() {
        String content = "nothing";

        try {
            URLConnection connection = new URL(repoUrl).openConnection();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();

            while (reader.ready()) {
                builder.append(reader.readLine()).append("\n");
            }

            gitReleases = new JsonParser().parse(builder.toString()).getAsJsonArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new VersionNotFoundException();
        }
    }

    private void findFirstRelease() {
        if (Objects.isNull(gitReleases))
            throw new GitVersionException("Error retrieving release from Github");

        for (JsonElement element : gitReleases) {
            String versionName = element.getAsJsonObject().get("name").getAsString();

            try {
                versionNameList.add(VersionName.fromString(versionName));
            } catch (IllegalArgumentException e) {
                Main.getMainLogger().warning(e.getMessage());
            }
        }

        versionNameList.sort(VersionName::compareTo);
        firstRelease = versionNameList.get(versionNameList.size() - 1);

    }

    protected VersionName getLatestReleaseNameOnGithub() {
        if (Objects.isNull(firstRelease)) {
            getReleasesFromGithub();
            findFirstRelease();
        }
        return firstRelease;
    }

    public URL getLatestReleaseDownloadURL() {
        getLatestReleaseNameOnGithub();
        try {
            for (JsonElement element : gitReleases) {

                JsonObject toCheck = element.getAsJsonObject();

                if (VersionName.fromString(toCheck.get("name").getAsString())
                        .equals(firstRelease)) {

                    JsonArray assetsArray = toCheck.getAsJsonArray("assets");

                    for (JsonElement jsonElement : assetsArray) {
                        JsonObject temp = jsonElement.getAsJsonObject();

                        if (temp.get("name").getAsString().contains(".jar")) {
                            return new URL(temp.get("browser_download_url").getAsString());
                        }
                    }
                }

            }
        } catch (MalformedURLException e) {
            Main.getInstance().getLogger().warning(e.getMessage());
        }
        throw new IllegalArgumentException("could not find release on github");
    }

}
