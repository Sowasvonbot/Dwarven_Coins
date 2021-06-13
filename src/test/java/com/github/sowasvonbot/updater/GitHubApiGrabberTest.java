package com.github.sowasvonbot.updater;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GitHubApiGrabberTest {

    @Test public void testGetReleasesFromGithub() {
        GitHubApiGrabber.getInstance().getReleasesFromGithub();
        JsonObject jsonObject =
                GitHubApiGrabber.getInstance().getGitReleases().get(0).getAsJsonObject();
        assertTrue(jsonObject.has("name"));
    }

    @Test public void testDownloadURL() {
        assertNotNull(
                GitHubApiGrabber.getInstance().getLatestReleaseDownloadURL().toExternalForm());
    }

}
