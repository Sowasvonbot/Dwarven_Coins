package com.github.sowasvonbot.updater;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GitHubApiGrabberTest {

    @Test public void testGetReleasesFromGithub() {
        JsonElement jsonElement = GitHubApiGrabber.getNewestReleaseElementFromGithub();

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        assertTrue(jsonObject.has("name"));
    }

}
