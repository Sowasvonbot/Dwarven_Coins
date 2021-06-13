package com.github.sowasvonbot.updater;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VersionComparatorTest {


    @Test public void testLocalFileOutput() {
        try {
            String test = VersionComparator.getLocalVersion();
            assertEquals("0.0.1", test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}