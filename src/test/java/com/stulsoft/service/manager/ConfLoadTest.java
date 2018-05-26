/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.service.manager;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotNull;

/**
 * @author Yuriy Stul
 */
public class ConfLoadTest {
    @Test
    public void parseFileTest() {
        Config config = ConfigFactory.parseFile(new File("application-test.conf"));
        assertNotNull(config);

        Config kafkaConfig = config.getConfig("kafka");
        assertNotNull(kafkaConfig);
    }
}
