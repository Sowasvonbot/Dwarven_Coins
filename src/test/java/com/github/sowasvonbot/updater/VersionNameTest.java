package com.github.sowasvonbot.updater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VersionNameTest {

    List<VersionName> versionNameList;

    @BeforeEach void setUp() {
        versionNameList = new ArrayList<>(
                Arrays.asList(new VersionName(1, 1, 1), new VersionName(1, 1, 1),
                        new VersionName(0, 0, 20), new VersionName(2, 0, 0),
                        new VersionName(0, 0, 19), new VersionName(0, 1, 0)));
    }

    @Test public void testSort() {
        versionNameList.sort(VersionName::compareTo);

        assertEquals(new VersionName(2, 0, 0), versionNameList.get(versionNameList.size() - 1));
        assertEquals(new VersionName(0, 0, 19), versionNameList.get(0));

    }
}
